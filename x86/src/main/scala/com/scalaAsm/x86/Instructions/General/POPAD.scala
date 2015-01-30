package com.scalaAsm.x86
package Instructions
package General

import com.scalaAsm.x86.Operands._
import com.scalaAsm.x86.Operands.Memory._

// Description: Pop All General-Purpose Registers
// Category: general/stack

object POPAD extends InstructionDefinition[OneOpcode]("POPAD") with POPADImpl

trait POPADImpl {
  implicit object POPAD_0 extends POPAD._0 {
    def opcode = 0x61
    override def hasImplicitOperand = true
  }
}