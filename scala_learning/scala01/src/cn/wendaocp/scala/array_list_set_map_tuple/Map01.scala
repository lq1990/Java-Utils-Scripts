package cn.wendaocp.scala.array_list_set_map_tuple

import scala.collection.mutable

/**
 * val map = Map[String, Int]("a" -> 100, "b"-> 200, ("c", 300), ("c", 400)) // Map中元素是 元组Tuple2
 * val option: Option[Int] = map.get("aa")
 * map.getOrElse("aa", 0)
 *
 * @author liuqiang
 * @since 2020/4/28 16:45
 */
object Map01 {
  def main(args: Array[String]): Unit = {
    /**
     * create map, immutable
     */
    val map = Map[String, Int]("a" -> 100, "b"-> 200, ("c", 300), ("c", 400)) // Map中元素是 元组Tuple2
    println(map)
    map.foreach(println)

    println("--get---")
    val option: Option[Int] = map.get("aa") // Option 有两个子类：Some/None，用于安全的函数返回
    println(option)

//    val value: Int = map.get("aa").get // map("aa") 直接拿到值，不是Option。
//    println(value)

    val i: Int = map.get("aa").getOrElse(0)  // map.getOrElse("aa", 0)
    println(i)

    println("---iterable---")
    val keys: Iterable[String] = map.keys  // Iterable 是所有集合的父接口
    keys.foreach(k=> {
      val value = map(k) // scala中()如同python的[]   是index操作
      println(s"key=$k, value=$value")

    })


    map.values.foreach(println)

    /**
     *
     */
    println("---")
    val map1: Map[String, Int] = Map[String, Int]("a" -> 1, "aa" -> 2, "aaa" -> 3)
    val map2: Map[String, Int] = Map[String, Int]("abc" -> 100, "aa" -> 200, "aaa" -> 30)
    val unionmap: Map[String, Int] = map1.++(map2) // map2的会覆盖map1中重复的
    unionmap.foreach(println)


    /**
     * mutable Map
     */
      println("---mutable map---")
    val map3: mutable.Map[String, Int] = mutable.Map[String, Int]()
    map3.put("a", 1)
    map3.put("aa", 11)
    map3.put("aaa", 111)
    map3.foreach(println)

    /**
     * set
     */
    println("---set---")
    map3("a") = 123
    for (elem <- map3) {println(elem)}

    println("---filter---")
    val fi: mutable.Map[String, Int] = map3.filter(e => {
      val e1: String = e._1
      val e2: Int = e._2
      e2 == 111
    })

    fi.foreach(println)





  }

}
