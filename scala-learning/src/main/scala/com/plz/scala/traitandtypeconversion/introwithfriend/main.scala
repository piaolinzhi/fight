import com.plz.scala.traitandtypeconversion.introwithfriend.Woman
import com.plz.scala.traitandtypeconversion.introwithfriend.Friend


/**
 * @author lover
 */
object main {
  def main(args: Array[String]): Unit = {
    val john = new Man("John")
    val sara = new Woman("sara")
    val comet = new Dog("Comet")
    john.listen()
    sara.listen()
    comet.listen()

    val mansBestFriend: Friend = comet
    mansBestFriend.listen()

    def helpAsFriend(friend: Friend) = friend listen

    helpAsFriend(sara)
    helpAsFriend(comet)

    //Cat 类没有混入Friend trait, 所以只能在实例级别混入.这样不会破坏封装性吗？ 可不可以用混入的操作来获取类中隐蔽的属性？
    val snowy = new Cat("Snowy") with Friend
    val tom = new Cat("tom") // 没有混入

    helpAsFriend(snowy)
//    helpAsFriend(tom) error
  }

}