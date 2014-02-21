package com.scalaAsm.x86.Instructions

import com.scalaAsm.x86.Operands._
import com.scalaAsm.x86.OperandEncoding._
import com.scalaAsm.x86.{ModRM, Instruction, OperandSize, OneOpcode, Immediate, DwordOperand, WordOperand, ByteOperand}
import com.scalaAsm.x86.AddressingFormSpecifier
import com.scalaAsm.x86.x86Registers._

trait SHL


trait SHL_2[-O1, -O2] extends SHL {
  def get(x: O1, y: O2): Instruction
}

object SHL extends ModRM {
  
  implicit object shl1 extends SHL_2[rm8, One] {
    def get(x: rm8, y: One) = new Instruction {
      val operands = M1(x)
      val opcode = OneOpcode(0xD0)
      val opcodeExtension: Option[Byte] = Some(4)
      val modRM: Option[AddressingFormSpecifier] = Some(getAddressingFormExtended1(operands, opcodeExtension.get))
     }
  }
}
