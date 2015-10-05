package com.plz.scala.traitandtypeconversion.implicitconversion

import java.util.Calendar
import java.util.Date

/**
 * @author lover
 */
class DateHelper(number: Int) {
  def days(when: String): Date = {
    var date = Calendar.getInstance()
    when match {
      case "ago"      => date.add(Calendar.DAY_OF_MONTH, -number)
      case "from_now" => date.add(Calendar.DAY_OF_MONTH, number)
      case _          => date
    }
    date.getTime()
  }

}
// 将隐式类型转换函数在伴生对象中定义
object DateHelper {
  val ago = "ago"
  val from_now = "from_now"
  implicit def convertInt2DateHelper(number: Int) = new DateHelper(number)
}