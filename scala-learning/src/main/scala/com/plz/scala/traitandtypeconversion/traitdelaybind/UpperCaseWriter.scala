package com.plz.scala.traitandtypeconversion.traitdelaybind

/**
 * @author lover
 */
trait UpperCaseWriter extends Writer {

  abstract override def writeMessage(message: String) = super.writeMessage(message.toUpperCase())
}