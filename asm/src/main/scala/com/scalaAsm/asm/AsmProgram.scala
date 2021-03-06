package com.scalaAsm.asm

import scala.collection.mutable.ListBuffer
import com.scalaAsm.asm.Tokens.Token
import scala.collection.mutable.ListBuffer
import com.scalaAsm.asm.Tokens._
import com.scalaAsm.x86.Operands._
import com.scalaAsm.x86.Operands.Memory.AbsoluteAddress

import scala.language.implicitConversions
import java.nio.ByteBuffer
import com.scalaAsm.x86.Instructions.General._

import com.scalaAsm.x86.Operands._
import com.scalaAsm.x86._



trait x86Mode
trait x86_64 extends x86_32
trait x86_32 extends x86Mode

trait AsmSection
 trait HighLevel

trait AsmProgram {

  val sections = new ListBuffer[AsmSection]()
  
  lazy val codeTokens = sections.collect { case (x: AsmProgram#CodeSection) => x }.flatMap { seg => seg.build(seg.builder.toSeq) }
  def varTokens = sections.collect { case (x: DataSection) => x }.flatMap { seg => seg.tokens }
  def variableNames = varTokens.collect { case Variable(name,_) => name }
  
 
  
  val universe: scala.reflect.runtime.universe.type = scala.reflect.runtime.universe
  import universe._
  

  
  trait CodeSection extends Registers with AsmSection {

    val builder = new ListBuffer[HighLevel]()

    implicit def toByte(x: Int) = x.toByte

    def procedure(name: String, innerCode: List[InstructionResult]*) = {
      builder += ProcedureToken(name, innerCode.reduce{_ ++ _})
    }
    
    case class Code(code: List[InstructionResult]*) extends HighLevel with InstructionResult {
      def mnemonic = ""
      def apply = Array()
    }

    def build(code: Seq[HighLevel]): Seq[InstructionResult] = {
      
      code flatMap {
        case ProcedureToken(name, code) => BeginProc(name) +: code
        case Code(codes @ _*)           => codes.reduce{_ ++ _}
        case align @ Align(_,_,_)       => List(align)
      }
    }

    def getRawBytes: Array[Byte] = {
      build(builder.toSeq) collect { case x: InstructionResult => x } map { x => x.apply } reduce (_ ++ _)
    }

    private def procRef(procName: String) = ProcRef(procName)
    
    def addr(varName: String) = AbsoluteAddress(dword(0), varName)  
    def addr64(varName: String) = AbsoluteAddress(qword(0), varName)

    def align(to: Int, filler: Byte = 0xCC.toByte) = Align(to, filler, (parserPos) => (to - (parserPos % to)) % to)

    def repeat(numTimes: Int, code: List[InstructionResult]*): List[Code] = {
      (0 until numTimes).map{x => Code(code.reduce{_ ++ _})}.toList
    }
  }
}