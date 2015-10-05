import com.plz.scala.traitandtypeconversion.introwithfriend.Animal


import com.plz.scala.traitandtypeconversion.introwithfriend.Friend

/**
 * @author lover
 */
class Dog(val name: String) extends Animal with Friend {
  // override trait method.(optional)
  override def listen = println(name + "'s listening quietly")
}