package cn.wendaocp.scala.fn

/**
 * @author liuqiang
 * @since 2020/4/28 10:14
 */
object Function01 {


  def main(args: Array[String]): Unit = {
    println("test main")
    println(max(1, 20))
    println(min(5,6))
    println(max1(4,5))
    println(max2(22,23))
  }

  /**
   * 方法定义：
   * 1. 方法体中，最后返回值可以使用return。则返回类型必须指定
   * 2. 方法体中的返回值可以忽略，会自动推断。最后一行计算的结果当成返回值返回
   * 3. 方法的参数必须指定类型
   * 4. 方法体中，如果可以一行搞定，则方法体 {} 可以省略
   * 5. 如果定义方法时，省略了方法名称和方法体之间的 = ，那么无论方法体最后一行计算的结果是什么，都会被丢弃，返回Unit
   * 6. def 定义方法
   *
   *
   * @param a
   * @param b
   * @return
   */
  def max(a: Int, b: Int) : Int = {
    if (a>b) {
      return a
    } else {
      return b
    }
  }

  def min(a: Int, b: Int) = {
    if (a>b) {
       b
    } else {
       a
    }
  }

  def max1(a: Int, b:Int)  {
    println("this is max1 begin")
    if( a > b) a else b
    println("this is max1 end")
  }


  def max2(a: Int, b: Int) = if(a>b) a else b



}
