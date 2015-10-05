package com.plz.scala.traitandtypeconversion.introwithfriend

/**
 * 1. 一个类被混入trait后，能过它的实例可以调用到到trait的方法，也可以把它的引用当做trait的引用
 *    不仅可以在类一级混入，还可以在实例一级混入
 * 2. 构造器不能有任何参数
 * 3. trait会被编译成java接口和对应的实现类（包含trait实现的方法）
 * 4. 多重继承通常会带来方法冲突的问题，trait通过延迟绑定混入类的方法，有效的回避了这一点。如此一来，在trait中调用super可能解析成另一个trait的方法，也可能会解析成混入类的方法。
 * @author lover
 */
trait Friend {

  // name 没有实现，被当做abstract对待，需在混入的类中实现
  val name: String
  
  def listen() = println("Your friend " + name + "is listening");

}