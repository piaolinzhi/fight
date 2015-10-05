package com.plz.scala.traitandtypeconversion.decoration

/**
 * @author lover
 */
trait CreditCheck extends Check{
  
  override def check():String="Checked Credit..."+super.check()
  
}