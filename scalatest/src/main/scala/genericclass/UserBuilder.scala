package genericclass

/**
  * Created by root on 17-4-19.
  * 这是一个构造例子
  *
  */
//这是构造类
trait UserBuilder[T <: UserBuilder[T]] {

  var id: String = ""

  //UserBuilder[T] 实际返回的是T
  def withId(id: String): UserBuilder[T] = {
    this.id = id
    this
  }

}

//这是复杂构造类
class ComplexUserBuilder extends UserBuilder[ComplexUserBuilder] {
  var userName: String = ""

  def withUserName(userName: String): ComplexUserBuilder = {
    this.userName = userName
    this
  }

  def build = new Complexuser(id, userName)
}

class Complexuser(id: String, userName: String) {

  override def toString: String = {
    "userid is : " + id + "-------userName is " + userName
  }
}

object Begin {
  def main(args: Array[String]) {
    val builder: ComplexUserBuilder = new ComplexUserBuilder
    //返回的useruilder 实际是ComplexUserBuilder 对象的引用
    val userBuilder: UserBuilder[ComplexUserBuilder] = builder.withId("123")
    builder.withUserName("jack")
    val complexuser1: Complexuser = builder.build
    println(complexuser1)

  }
}