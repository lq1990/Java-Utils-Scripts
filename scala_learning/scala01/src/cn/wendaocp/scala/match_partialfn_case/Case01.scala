package cn.wendaocp.scala.match_partialfn_case

case class Person1(var name: String, age: Int) {
}

/**
 * 定义样例类。
 * 1. 是种特殊的类
 * 2. 实现了参数的getter, 当参数是var类型时会有setter
 * 3. 样例类默认帮实现了 toString, equals, copy, hashCode 等方法
 * 4. 可以new，也可以不用new
 *
 * @author liuqiang
 * @since 2020/4/28 19:37
 */
object Case01 {
  def main(args: Array[String]): Unit = {
    val p1 = new Person1("zhangsan", 10)
    val p2 = new Person1("zhangsan", 10)
    println(p1.equals(p2)) // true。帮我们写了equals()
    println(p1) // 帮我们写了toString()

    println(p1.name) // 可以直接拿到。
    println(p1.age) // 在普通class中，age是拿不到的，因为默认是的私有的

  }


}
