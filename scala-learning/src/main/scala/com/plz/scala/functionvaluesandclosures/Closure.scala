package com.plz.scala.functionvaluesandclosures

/**
 * 没有讲得很清楚，什么是闭包.
 * @author lover
 */
class Closure {

}
object Closure {
  def loopThrough(number: Int)(closure: Int => Unit) {
    for (i <- 1 to number) { closure(i) }
  }
  def main(args: Array[String]): Unit = {
    var result = 0;
    val addInt = { value: Int => result += value }
    loopThrough(10) { addInt }
    println("Total of values from 1 to 10 is " + result);
    result = 0
    loopThrough(5)(addInt)
    println("Total of values from 1 to 5 is " + result)

    var product = 1
    loopThrough(5)(product *= _)
    println("Product of values from 1 to 5 is " + product)

  }
}