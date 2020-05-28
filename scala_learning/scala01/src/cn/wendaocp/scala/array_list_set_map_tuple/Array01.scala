package cn.wendaocp.scala.array_list_set_map_tuple

import scala.collection.mutable.ArrayBuffer

/**
 * Array, List, Set, Map   可以定义可变或 不可变的
 *
 *
 * val arr = Array[String](xs="a", "b", "c", "d")
 * arr(index) = xxx  // set
 * Array.concat(arr1, arr2)
 * Array.fill(5)("aaa") // 每个元素都是aaa
 *
 * val arr10: ArrayBuffer[Int] = ArrayBuffer[Int](1, 2, 3)  // mutable
 * arr10.+=(4) // append
 * arr10.+=:(100) // insert into head
 * arr10.append(44,55,66)
 *
 * 默认导入的是不可变的，不能CRUD。
 * 可以使用ArrayBuffer，是mutable
 *
 * @author liuqiang
 * @since 2020/4/28 13:22
 */
object Array01 {
  def main(args: Array[String]): Unit = {


    val arr = Array[String](xs="a", "b", "c", "d")
    arr.foreach(s=>{println(s)})
    arr.foreach(println(_))

    println()
    val arr1 = new Array[Int](3) // 3 size
    arr1(0) = 100 // set
    arr1(1) = 101
    arr1(2) = 102
    arr1.foreach(println)

    println()

    val array = new Array[Array[Int]](3)
    array(0) = Array[Int](1,2,3)
    array(1) = Array[Int](4,5,6)
    array(2) = Array[Int](7,8,9)

    array.foreach(elem => {elem.foreach(e => print(e+"  ")); println()})

    println("---")

    val array1= Array.concat(arr, arr)
    array1.foreach(e => print(e+" "))

    println("\n===")

    val strings: Array[String] = Array.fill(5)("aaa") // 每个元素都是aaa
    strings.foreach(println)

    println("---")
    /**
     * 可变的mutable
     *
     *
     */
    val arr10: ArrayBuffer[Int] = ArrayBuffer[Int](1, 2, 3)
    arr10.+=(4) // append
    arr10.+=:(100) // insert into head
    arr10.append(44,55,66)
    arr10.foreach(println)



  }

}
