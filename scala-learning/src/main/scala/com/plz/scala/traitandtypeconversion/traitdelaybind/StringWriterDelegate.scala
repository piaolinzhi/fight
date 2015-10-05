package com.plz.scala.traitandtypeconversion.traitdelaybind

import java.io.StringWriter

/**
 * @author lover
 */
class StringWriterDelegate extends Writer {
  val writer = new StringWriter()
  def writeMessage(message: String) = writer.write(message)
  override def toString(): String = writer.toString
}
object StringWriterDelegate {
  def main(args: Array[String]): Unit = {
    val myWriterProfanityFirst = new StringWriterDelegate with UpperCaseWriter with ProfanityFilteredWriter
    val myWriterProfanityLast = new StringWriterDelegate with ProfanityFilteredWriter with UpperCaseWriter

    myWriterProfanityFirst writeMessage ("There is no sin except stupidity")
    myWriterProfanityLast writeMessage ("There is no sin except stupidity")

    println(myWriterProfanityFirst)
    println(myWriterProfanityLast)
  }
}