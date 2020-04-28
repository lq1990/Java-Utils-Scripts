package cn.wendaocp.scala.array_list_set_map_tuple

/**
 * 元组最多支持22个元素。
 * 使用 t._n 来取出来第n个元素，从1开始计数。
 *
 * 3中方式创建：
 * new Tuple1("hello")
 * Tuple4(1, 4, "abc", false)
 * (1, 2, 3, 4, 5, "ac")
 *
 *
 * Tuple2的特殊性，有swap 方法
 *
 *
 * 遍历：需要借助iterator
 * val iter: Iterator[Any] = t6.productIterator
 * iter.foreach(println)
 * or
 * while(iter.hasNext) {
 *   println(iter.next())
 * }
 *
 * @author liuqiang
 * @since 2020/4/28 18:20
 */
object Tuple01 {

  def main(args: Array[String]): Unit = {
    println("test tuple")

    val t1: Tuple1[String] = new Tuple1("hello")
    println(t1)

    println("---tuple2---")
    val t2: (String, Int) = new Tuple2("a", 100)
    println(t2)
    val t2s = t2.swap
    println(t2s)

    println("---tuple3---")
    println()
    val t3: (Int, Boolean, String) = new Tuple3(1, true, "a")
    println(t3)

    val t4 = Tuple4(1, 4, "abc", false)
    println(t4)

    val t6: (Int, Int, Int, Int, Int, String) = (1, 2, 3, 4, 5, "ac")
    println(t6)

    val iter: Iterator[Any] = t6.productIterator
//    iter.foreach(println)  or

    while(iter.hasNext) {
      println(iter.next())
    }




  }


}
