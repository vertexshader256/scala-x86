package com.scalaAsm.x86
package Instructions

abstract class NOT extends x86Instruction("NOT")

trait NOT_1[-O1] extends NOT with OneOperand[O1] with InstructionFormat

object NOT {
  
  implicit object not1 extends NOT_1[rm32] {
      def operands = M(op1)
      val opcode = 0xF7 /+ 2
  }
}