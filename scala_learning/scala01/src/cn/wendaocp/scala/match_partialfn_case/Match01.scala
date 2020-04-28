package cn.wendaocp.scala.match_partialfn_case

/**
 * 模式匹配
 * 1. case _ 类似于java的switch/case的default
 * 2. 还可以匹配类型
 * 3. 一旦匹配上，不会往下走了。
 * 4. 注意匹配时，会有数值类型的转换。1.0 => 1
 *
 * 由模式匹配衍生出 偏函数
 *
 * @author liuqiang
 * @since 2020/4/28 19:10
 */
object Match01 {
  def main(args: Array[String]): Unit = {
    println("test match")

    val t1 = (1, 1.0, "abc", "a", true)
    val it = t1.productIterator
//    it.foreach(s => MatchTest(s))
    it.foreach(MatchTest)

  }

  def MatchTest(o: Any): Unit = {
    o match {
      case 1=> println("value is 1")
      case i:Int => println(s"type is Int, value = $i")
      case d: Double => println(s"type is Double, value = $d")
      case s:String =>println(s"type is String, value = $s")
      case "a"=>println("value is a")

      case _=> println("default value")
    }
  }



}
