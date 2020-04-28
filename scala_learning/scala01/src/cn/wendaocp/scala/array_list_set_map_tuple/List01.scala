package cn.wendaocp.scala.array_list_set_map_tuple

import scala.collection.mutable.ListBuffer

/**
 * val list1: List[Any] = List(1, 2, 3, "a", true)
 *
 * val stringses: List[Array[String]] = list2.map(str => {
 *       str.split(" ")
 * })
 *
 * val strings: List[String] = list2.flatMap(s => {
 *       s.split(" ")
 * })
 *
 * filter
 * count
 *
 * ListBuffer[T](1,2,3)
 *
 * @author liuqiang
 * @since 2020/4/28 14:10
 */
object List01 {

  def main(args: Array[String]): Unit = {

    val list0 = List
    println(list0) // immutable

    val list1: List[Any] = List(1, 2, 3, "a", true)
    println(list1)
    // create list
    val list = list1
    list.foreach(println)

    println()
    val list2: List[String] = List("hello scala", "hello java", "hello world", "a", "bc")
    println(list2)
    val str: String = list2.+("aaa") // list2会当成字符串，和aaa进行拼接
    str.foreach(println)

    /**
     * map
     */
    println()
    val stringses: List[Array[String]] = list2.map(str => {
      str.split(" ")
    })
    stringses.foreach(e=>{println("new Array"); e.foreach(e1 => print(e1+" ")); println()})

    println("===")
    /**
     * flatMap。是在map的基础上，进行了偏平化
     */
    val strings: List[String] = list2.flatMap(s => {
      s.split(" ")
    })
    strings.foreach(e=>{print(e+" "); })

    println("\n===")
    /**
     *
     */
    val list3: List[Int] = List(11, 22, 33, 44)
    list3.foreach(println)

    /**
     * filter
     */
    println("\n===")
    val strings1: List[String] = list2.filter(s => {
      "hello scala".equals(s)
    })
    strings1.foreach(println)


    /**
     * count 满足条件的条数
     */
    println("\n===")
    val i: Int = list2.count(s => {
      s.length < 4
    })
    println(i)

    /**
     * mutable list
     *
     */
      println("\n===")
    val ints: ListBuffer[Int] = ListBuffer[Int](1, 2, 33)
    ints.append(4,55,6)
    ints.+=:(100)
    ints.foreach(println)




  }

}
