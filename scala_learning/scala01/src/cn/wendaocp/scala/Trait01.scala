package cn.wendaocp.scala

trait Read {
  def read(name: String) = {
    println(s"$name is reading...")
  }

}

trait Listen {
  def listen(name: String) = {
    println(s"$name is listening")
  }
}


class Human() extends Read with Listen {

}


/**
 * trait: 接口和抽象类的整合
 * trait 不可以传参
 *
 * @author liuqiang
 * @since 2020/4/28 18:46
 */
object Trait01 {

  def main(args: Array[String]): Unit = {
    println("test trait")

    val h = new Human()
    h.read("anna")
    h.listen("lisi")


  }

}

