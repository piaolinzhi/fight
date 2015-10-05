package com.plz.scala.traitandtypeconversion.traitdelaybind

/**
 * @author lover
 */
trait ProfanityFilteredWriter extends Writer {
  
  /**
   * 为了调用super.writeMessage,scala 做了两件事情
   * 1. 对调用进行了延迟绑定
   * 2. 要求混入这个trait的类提供该方法的实现
 * @param message
 */
abstract override def writeMessage(message: String) = super.writeMessage(message.replace("stupid", "s_____"))

}