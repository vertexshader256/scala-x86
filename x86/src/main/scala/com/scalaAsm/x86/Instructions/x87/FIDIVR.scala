package com.scalaAsm.x86
package Instructions
package x87

import com.scalaAsm.x86.Operands._
import com.scalaAsm.x86.Operands.Memory._

// Description: Reverse Divide
// Category: general/arith

object FIDIVR extends InstructionDefinition[OneOpcode]("FIDIVR") with FIDIVRImpl

trait FIDIVRImpl {
  implicit object FIDIVR_0 extends FIDIVR._1[m32] {
    def opcode = 0xDA /+ 7
    override def hasImplicitOperand = true
  }

  implicit object FIDIVR_1 extends FIDIVR._1[m16] {
    def opcode = 0xDE /+ 7
    override def hasImplicitOperand = true
  }
}