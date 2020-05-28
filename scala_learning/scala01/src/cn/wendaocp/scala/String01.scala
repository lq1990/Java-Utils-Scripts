package cn.wendaocp.scala

/**
 * @author liuqiang
 * @since 2020/4/28 13:16
 */
object String01 {

  def main(args: Array[String]): Unit = {
    val s = "jbsxt"
    val s1 = "BJSXT"

    println(s.equals(s1))
    println(s.equalsIgnoreCase(s1))
    println(s.indexOf(98)) // ascii码


  }

}
