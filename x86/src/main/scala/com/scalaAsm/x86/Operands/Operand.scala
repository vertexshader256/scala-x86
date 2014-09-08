package com.scalaAsm.x86
package Operands

import com.scalaAsm.x86.Instructions.InstructionField
import com.scalaAsm.x86.Operands.Memory.AbsoluteAddress

trait One extends Operand[One,One]{
  def size = 1
  def get = this
}

case class addr(name: String) extends Operand[String, AbsoluteAddress[Constant32]] {
  var variables: Map[String, Int] = _
  var baseOffset: Int = _
  var parserPosition: Int = 0
  def get = new AbsoluteAddress[Constant32] {
    var offset = {println("variable: " + name + " loc: " + variables(name)); variables(name)}
    def getRelative = null
    def displacement = {println("variable: " + name + " loc: " + variables(name)); Constant32(variables(name) - 0x1000 - parserPosition - 7)}
  }
}

trait Operand[+From, +To] {
  def get: To
}

trait Constant[Self] extends InstructionField {
  type Size <: OperandSize
  def value: Size#primitiveType
  def getBytes: Array[Byte]
  def asInt: Int
  def asLong: Long
  def negate: Self
  def size: Int
}

case class Constant8(value: Byte) extends Constant[Constant8] {
  self =>
  type Size = ByteOperand
  def getBytes: Array[Byte] = Array(value)
  def size = 1
  def asInt = value.toInt
  def asLong = value.toLong
  def negate = this.copy(value = (-this.value).toByte)
}

case class Constant16(value: Short) extends Constant[Constant16] {
  self =>
  type Size = WordOperand
  def getBytes: Array[Byte] = Array((value & 0x00FF).toByte, ((value & 0xFF00) >> 8).toByte)
  def size = 2
  def asInt = value.toInt
  def asLong = value.toLong
  def negate = this.copy(value = (-this.value).toShort)
}

case class Constant32(value: Int) extends Constant[Constant32] {
  self =>
  type Size = DwordOperand
  def getBytes: Array[Byte] = Array((value & 0x000000FF).toByte, ((value & 0x0000FF00) >> 8).toByte, ((value & 0x00FF0000) >> 16).toByte, ((value & 0xFF000000) >> 24).toByte)
  def size = 4
  def asInt = value.toInt
  def asLong = value.toLong
  def negate = this.copy(value = -this.value)
}

case class Constant64(value: Long) extends Constant[Constant64] {
  self =>
  type Size = QwordOperand
  def getBytes: Array[Byte] = {
    import java.nio.{ByteBuffer, ByteOrder}
    val buffer = ByteBuffer.allocate(8)
    buffer.order(ByteOrder.LITTLE_ENDIAN)
      buffer.putLong(value)
      buffer.array()
  }
  def size = 8
  def asInt = value.toInt
  def asLong = value.toLong
  def negate: Constant64 = this.copy(value = -this.value)
}

trait RegisterOrMemory[Size <: OperandSize]



