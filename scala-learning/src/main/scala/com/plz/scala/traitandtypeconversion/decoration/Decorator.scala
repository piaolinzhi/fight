package com.plz.scala.traitandtypeconversion.decoration

/**
 * @author lover
 */
class Decorator {

}
object Decorator {
  def main(args: Array[String]): Unit = {
    // abstrack Check 是如何实例化的？
    val apartmentApplication = new Check with CreditCheck with CriminalRecordCheck

    println(apartmentApplication check)

    val employmentApplication = new Check with CriminalRecordCheck with EmploymentCheck
    println(employmentApplication check)

  }
}