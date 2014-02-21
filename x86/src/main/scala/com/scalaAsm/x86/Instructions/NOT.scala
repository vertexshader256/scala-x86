package com.scalaAsm.x86.Instructions

import com.scalaAsm.x86.Operands._
import com.scalaAsm.x86.OperandEncoding._
import com.scalaAsm.x86.{ModRM, Instruction, OperandSize, Instruction1, Instruction2, Immediate, DwordOperand, WordOperand}
import com.scalaAsm.x86.AddressingFormSpecifier

trait NOT extends ModRM

trait NOT_1[-O1] extends NOT {
  def get(p: O1): Instruction
}

object NOT {
  
  implicit object not1 extends NOT_1[rm32] {
    def get(x: rm32) = new Instruction {
      val operands = M(x)
      val opcode = 0xF7.toByte
      val opcodeExtension = Some(2.toByte)
      val opcode2 = None
      val modRM: Option[AddressingFormSpecifier] = Some(getAddressingFormExtended1(operands, opcodeExtension.get))
     }
  }
}