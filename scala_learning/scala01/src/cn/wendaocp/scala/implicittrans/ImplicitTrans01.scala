package cn.wendaocp.scala.implicittrans

/**
 * @author liuqiang
 * @since 2020/4/28 20:23
 */
object ImplicitTrans01 {


  // 若只有个别参数希望是 隐式转换，需要用柯里化
  def sayName(age: Int)(implicit name: String): Unit = {
    println(s"age is $age, $name is a student")

  }

  def main(args: Array[String]): Unit = {
    implicit val name: String = "abc"

    sayName(18) // 缺少的参数，会隐式寻找
  }


}
