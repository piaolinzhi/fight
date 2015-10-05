package com.plz.scala.traitandtypeconversion.decoration

/**
 * @author lover
 */
trait EmploymentCheck extends Check{
  override def check() :String ="Employment check..."+super.check()
  
}