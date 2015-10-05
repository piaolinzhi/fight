package com.plz.scala.traitandtypeconversion.decoration

/**
 * @author lover
 */
trait CriminalRecordCheck extends Check {
  override def check(): String = "Check Criminal Records..." + super.check()

}