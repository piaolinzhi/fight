package com.plz.scala.functionvaluesandclosures

/**
 * 参数的位置标记法 “_" 可以表示函数值的参数。
 * 如果某个参数在函数里只使用一次，就可以用下划线表示。
 * 每次在函数里用下划线，都表示随后的参数。
 * _不仅可以表示一个参数，也可以表示参数列表。
 * @author lover
 *
 */
object Underscore {
  def main(args: Array[String]): Unit = {
    val arr = Array(1, 2, 3, 4, 5);
    println("Sum of all values in array is " + (0 /: arr) { (sum, element) => sum + element })
    // Do not do like that. It's not readable.
    println("Sum of all values in array is " + (0 /: arr) { _ + _ })

    def maxOf2(a: Int, b: Int): Int = {
      if (a > b) {
        a
      } else { b }
    }

    var max = (Integer.MIN_VALUE /: arr) {
      (large, elem) => maxOf2(large, elem);
    }

    max = (Integer.MIN_VALUE /: arr) { maxOf2(_, _) }

    max = (Integer.MIN_VALUE /: arr) { maxOf2 _ }

    max = (Integer.MIN_VALUE /: arr) { maxOf2 }

    // max =(Integer.MIN_VALUE /: arr) maxOf2 //这样为什么不行？

  }

}