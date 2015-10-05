package com.plz.scala.traitandtypeconversion.decoration

/**
 * 继承了Check，给了我们两个能力。
 * 1. 这些trait只能混入继承自Check的类。
 * 2. 在这些trait里可以使用Check的方法
 *
 * 当混入类调用 super.check() 的方法时，会经历一个延迟绑定的过程。
 * 这个调用不是基数方法的调用，而是对其左边混入的trait的调用，如果这个trait是混入的最左trait，那么这个调用就会解析成混入这个trait的类的方法。
 *
 * @author lover
 */
abstract class Check {
  def check(): String = "Checked Application Deatails..."

}