package com.plz.scala.usingcollections

/**
 * @author lover
 */
class UsingMap {

}
object UsingMap {
  def main(args: Array[String]): Unit = {

    val feeds = Map("Andy Hunt" -> "blog.toolshed.com", "Dave Thomas" -> "pragdave.pragprog.com", "Dan Steinberg" -> "dimsumthinking.com/blog")

    val filterNamesStartWithD = feeds filterKeys (_ startsWith "D")
    println("# of Filtered :" + filterNamesStartWithD)
    // shit! 代码能不能通过编译还和代码格式有关的……写在一行就通不过
    val filterNamesStartWithDAndBlogInFeed = feeds filter { element =>
      val (key, value: String) = element
      (key startsWith "D") && (value contains "blog")
    }

  }
}