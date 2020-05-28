package cn.wendaocp.scala.implicittrans

class Rabbit1(xname: String) {
  val name = xname
}
/**
 * @author liuqiang
 * @since 2020/4/28 20:46
 */
object ImplicitClass01 {

  implicit class Animal1(r: Rabbit1) { // rabbit作为参数输入。但注意：只能有一个参
    def showName() ={
      println(s"${r.name} is Rabbit...")

    }
  }

  def main(args: Array[String]): Unit = {
    val r = new
        Rabbit1("jkjkj")
    r.showName()
    // Rabbit1没有这个方法。
    // 但是，因为此对象在隐式类中作为参数输入了，所以可以使用隐式类中的方法


  }
}
