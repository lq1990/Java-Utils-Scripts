package cn.wendaocp.scala.match_partialfn_case

/**
 * 由match/case 引出的偏函数, 很像java的switch/case
 *
 * 偏函数：只能匹配一个值，匹配上了返回某个值
 *
 * @author liuqiang
 * @since 2020/4/28 19:26
 */
object PartialFn01 {
  def main(args: Array[String]): Unit = {

    println(MyTest("abc"))

  }

  def MyTest: PartialFunction[String, Int] = {
    case "abc" => 2
    case "a" => 1
    case _ => 200 // default

  }

}
