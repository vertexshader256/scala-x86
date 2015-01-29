package com.scalaAsm.x86
package Instructions
package General

import com.scalaAsm.x86.Operands._
import com.scalaAsm.x86.Operands.Memory._

// Description: Store String
// Category: general/datamovstring

object STOS extends InstructionDefinition[OneOpcode]("STOS") with STOSImpl

trait STOSImpl {
  implicit object STOS_0 extends STOS._0 {
    def opcode = 0xAA
    override def hasImplicitOperand = true
  }
}