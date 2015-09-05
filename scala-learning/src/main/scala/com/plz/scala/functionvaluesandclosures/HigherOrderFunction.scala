package com.plz.scala.functionvaluesandclosures

/**
 * @author lover
 */
class HigherOrderFunction {

}
object HigherOrderFunction {

  // 定义一个多参数的 function value
  def inject(arr: Array[Int], initial: Int, operation: (Int, Int) => Int): Int = {
    var carryOver = initial;
    arr.foreach(element => carryOver = operation(carryOver, element))
    carryOver
  }

  def main(args: Array[String]): Unit = {

    println("Hello, world");
    //定义一个高阶函数
    def totalResultOverRange(number: Int, codeBlock: Int => Int): Int = {
      var result = 0;
      for (i <- 1 to number) {
        result += codeBlock(i);
      }
      result;
    }
    //调用
    println(totalResultOverRange(11, i => i))
    println(totalResultOverRange(11, i => if (i % 2 == 0) 1 else 0))
    // 调用多参数的 function value
    val array = Array(2, 3, 5, 1, 6, 4)
    val sum = inject(array, 0, (carryOver, elem) => carryOver + elem);
    println("Sum of elements in array" + array.toString() + " is " + sum)

    val max = inject(array, 0, (carryOver, elem) => Math.max(carryOver, elem))
    println("Max of elements in array" + array.toString() + " is " + max)

    // foldLeft() 即 /: 方法 
    val arraySum = (0 /: array) { (sum, elem) => sum + elem };
    val arrayMax = (Integer.MIN_VALUE /: array) {
      (large, elem) => Math.max(large, elem);
    }

    println("Sum of elements in array" + array.toString() + " is " + sum)
    println("Max of elements in array" + array.toString() + " is " + max)

  }
}