package com.plz.scala.usingcollections

/**
 * @author lover
 */
class UsingSet {

}
object UsingSet {
  def main(args: Array[String]): Unit = {
    // 注意初始化的方式，Set，List，Map都是有工厂方法的，并且在伴生对象中定义，X.apply()方法。
    val colors1 = Set("Blue", "Green", "Red")
    val colors2 = Set("Blue", "Green", "Red", "Black", "Yellow")
    var colors = colors1
    colors += "black"
    println("colors:" + colors)
    println("colors1:" + colors1)

    // 过滤方法 filter
    val blue = colors1 filter (_ contains "Blue")

    // 集合的并集
    val mergedColor = colors1 ++ colors
    // 集合的交集 **
    //    val commonColor = colors1 ** colors2 //  why?

    // mergedColor += "" error ,result is a immutable Set

    println("merged colors is :" + mergedColor)
    // map() 对每个元素应用给定的函数值，将结果收集到一个新的Set中并返回这上Set
    val colorDesc = colors1 map ("color:" + _)
    // foreach() 迭代
    colors1 foreach { color => println(" print color iteratively " + color) }
  }

}