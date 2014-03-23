package com.scalaAsm.x86
package Instructions

abstract class DEC extends x86Instruction("DEC")

trait DEC_1[-O1] extends DEC with OneOperand[O1] with InstructionFormat

object DEC {
  
  implicit object dec1 extends DEC_1[r32] {
     def operands = O(op1)
     val opcode = OpcodePlusRd(0x48, op1)
  }
  
  implicit object dec2 extends DEC_1[r16] {
     def operands = O(op1)
	 def opcode = OpcodePlusRd(0x48, op1)
  }
}