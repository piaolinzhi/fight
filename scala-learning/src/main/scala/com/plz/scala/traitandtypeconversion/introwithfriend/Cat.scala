import com.plz.scala.traitandtypeconversion.introwithfriend.Animal



/**
 * Cat  没有混入Friend trait，所以Cat类实例不能做为 Friend 
 * @author lover
 */
class Cat(val name: String) extends Animal {

}