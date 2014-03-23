package com.scalaAsm.x86
package Instructions

import com.scalaAsm.x86.Operands._
import scala.annotation.implicitNotFound

abstract class PUSH extends x86Instruction("PUSH")

@implicitNotFound(msg = "Cannot find PUSH implementation for ${O1}")
trait PUSH_1[-O1] extends PUSH with OneOperand[O1] with InstructionFormat

trait POWLow {
    
  implicit object highPush extends PUSH_1[rm] {
      def operands = M(op1)
      val opcode = 0xFF /+ 6
  }
}

object PUSH extends POWLow {
  
  def push[O1](p1: O1)(implicit ev: PUSH_1[O1]): PUSH_1[O1] = {
    ev.set(p1)
    ev
  }
  
  implicit object push2 extends PUSH_1[r32] {
      def operands = O(op1)
      def opcode = OpcodePlusRd(0x50, op1)
  }
  
  implicit object push3 extends PUSH_1[r16] {
      def operands = O(op1)
      def opcode = OpcodePlusRd(0x50, op1)
  }
  
  implicit object push4 extends PUSH_1[imm8] {
      def operands = I[imm8](op1)
      val opcode: Opcodes = 0x6A
  }
  
  implicit object push5 extends PUSH_1[imm16] {
      def operands = I[imm16](op1)
      val opcode: Opcodes = 0x68
  }
  
  implicit object push6 extends PUSH_1[imm32] {
      def operands = I[imm32](op1)
      val opcode: Opcodes = 0x68
  }
  
  implicit object push7 extends PUSH_1[CS] {
      def operands = new OneOperandFormat[CS](op1) {def getAddressingForm = null}
      val opcode: Opcodes = 0x0E
  }
}