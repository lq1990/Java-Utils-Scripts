package cn.wendaocp.scala.fn

import java.util.Date

/**
 * 偏应用函数的定义。在原有函数的基础上，给定固定的参数值，变化的参数用 _
 *
 * @author liuqiang
 * @since 2020/4/28 10:34
 */
object Function02 {

  def main(args: Array[String]): Unit = {
    println("test main 02")

    println(myrecursive(5))
    println()
    println(funWithDefaultVal())
    println(funWithDefaultVal(b = 200)) // 可指定某个参数的值
    println()
    funWithChangable("abc", "a", "c")


    println(funAnony(1,2))

    println()
    println(funNested(4))


    val date = new Date()
    showLog(date, log = "abc") // 正常函数调用。
    // 场景：当方法有很多参数，但大部分是固定值，少数参数是变化的。

    // 定义 偏应用函数。不变的参数写在前面，变化的写在后面
    def pianApp = showLog(date, _: String)
    // 使用 偏应用函数时，只需要传入变化的参数值
    pianApp("aaafdasfasdgdas")
    pianApp("东方大厦")
    pianApp("qqqqqqqqqqq")

    println()
    /**
     * 高阶函数
     */
    val str: String = funAdvanced1(funAdvanced, "scala")
    println(str)

    val str1: String = funAdvanced1((a: Int, b: Int) => {
      a * b
    }, "scala2")
    println(str1)

    // return fn。注意调用时的写法
    println(funAdReturnF2(s = "kkk")("bbb", "ccc"))

//    val function: (Int, Int) => Int = funAdReturnF2 _


    val str2: String = funAdParamReturn((a: Int, b: Int) => {
      a * b
    })("hello", "world")

    println(str2)


    /**
     *
     */
    val i: Int = funKelihua(1, 2)(1, 2)
    println(i)


  }

  /**
   * 递归方法要 显示声明返回值类型
   * @param num
   * @return
   */
  def myrecursive(num: Int): Int = {
    if (num==1) {
      1
    } else {
      num * myrecursive(num-1)
    }
  }

  // 参数有默认值
  def funWithDefaultVal(a: Int=10, b:Int=30) = {
    a+b
  }

  // 可变长参数的方法
  def funWithChangable(s: String*) = {
//    for (elem <- s) {
//      println(elem)
//    }

    s.foreach(s1 => {println(s1)}) // 进来一个s1，想干嘛呢？想去打印s1

    println()
    // 当匿名函数的参数，在匿名函数体内只使用一次的话，可以省略s1，用_ 替代
    s.foreach(println(_)) // 甚至_ 也可以省略

    s.foreach(println)

  }

  /**
   * anonymos fn
   * () => {} 多用于：方法的参数是 函数时，常用匿名函数
   *
   */
  def funAnony = (a: Int, b: Int) => { // = 是把匿名函数 传给一个变量，和js一样
    a+b
  }

  /**
   * 嵌套方法
   */
  def funNested(num: Int) = {

    def funN1(a: Int): Int = {
      if(a==1) {
        1
      } else {
        a*funN1(a-1)
      }
    }

    funN1(num)
  }

  /**
   * 偏应用函数。
   * 当方法参数中很多，但大部分是不变的，
   * 使用偏应用函数可以 只写那么变化的参数。
   *
   * 把 不变的参数 写在前，变化的写 _: 类型
   *
   */
    def showLog(date: Date, log: String) = {
      println(s"date is $date, log is $log")
    }


  /**
   * 高阶函数
   * 1. 方法的 参数是函数
   * 2. 方法的返回是函数。需要显示的写出方法的返回值类型，加 _ 可以不必手写返回值类型
   * 3. 方法的参数和返回都是fn
   */
  def funAdvanced(a: Int, b: Int) = {
    a+b
  }


  def funAdvanced1(f: (Int, Int) => Int, s: String): String = {
    val i: Int = f(100, 101) // 这里的100 101相当于是spark中的数据。如何处理，需要我们在使用的时候 定义匿名函数

    i+"#"+s

  }

  // 返回类型是函数。需要写出来返回类型
  def funAdReturnF(s: String): (String, String) => String = {
    def fun1(s1: String ,s2: String) :String ={
      s1+"`"+s2 +"#"+ s
    }

    fun1
  }

  // 当不想写返回值类型时
  def funAdReturnF2(s: String) = {
    def fun11(s1: String ,s2: String) :String ={
      s1+"`"+s2 +"#"+ s
    }

    fun11 _
  }

  // 方法的参数和返回都是fn
  def funAdParamReturn(f: (Int, Int) => Int ): (String, String) => String = {
    val i: Int = f(1, 2)
    def fun1(s1: String, s2: String): String = {
      s1+"@"+s2 + "#" + i
    }
    fun1
  }

  /**
   * 9. 柯里化函数
   */
  def funKelihua(a: Int, b: Int)(c: Int, d: Int) = {
    a+b+c+d
  }


}
