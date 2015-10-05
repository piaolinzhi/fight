package com.plz.scala.traitandtypeconversion.implicitconversion

import DateHelper._

/**
 * @author lover
 */
object main {
  def main(args: Array[String]): Unit = {

    // 定义隐式类型转换,可以在用的时候定义，也可以写在DateHelper类中，建议写在DateHelper类的伴生对象中
    //    implicit def convertIntDateHelper(number: Int) = new DateHelper(number)
    val ago = "ago"
    val from_now = "from_now"
    // 隐式类型转换，要保证隐式类型转换函数在当前范围可见
    // Scala 一次至多应用一个隐式转换
    // 什么时候会进行类型转换？ Scala判断通过类型转换有助于操作、方法调用或类型转换的成功完成，就会进行转换.
    val past = 2 days ago
    val appointment = 5 days from_now

    println(past)
    println(appointment)

  }

}