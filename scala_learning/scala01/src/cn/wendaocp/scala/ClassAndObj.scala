package cn.wendaocp.scala

/**
 * Scala:
 * 1. object 相当于java的单例，object中定义的都是静态的，相当于java工具类
 * 2. var可变， val 不可变
 * 3. Scala中每行后面都会有分号自动推断机制，不用写 ;
 * 4. scala中建议使用驼峰
 * 5. scala类中可以传参，需要指定类型，有了参数就有了默认的构造。参数默认私有，可以用val var修饰变成公有。
 *    属性默认有getter/setter，当然对于var的属性才可以setter。
 * 6. scala重写构造，重写的构造里的第一行 必须 先调用默认的构造
 * 7. scala中，new一个Person实例时，Person会从头到尾都执行，除了方法之外。   =》 如同js中的function
 * 8. 可设置类中属性为private
 * 9. 在同一个scala文件中，class名称和object可以一样，则两者为伴生关系，两者可以互相访问私有属性。
 * 10. object不能传参，但可以定义apply 来做传参。实现构造的功能。可以定义多个apply，重载
 *
 *
 * @author liuqiang
 * @since 2020/4/27 20:01
 */
object ClassAndObj { // object 不支持直接加参数。但class可以

  println("top of obj") // 会执行。

  val name = " wangwang "

  // object默认不能传参，但可以手动写一个apply 就能传参了
  def apply(i: Int): Unit = { // function都有一个 = 传递
    println("score: "+i)
  }

  def apply(i: Int, name: String): Unit = (
    println(i+""+name)
  )

  def main(args: Array[String]): Unit = {

    // 测试object的传参
    ClassAndObj(100) // 肯定有apply
    ClassAndObj(100,"aaa")

    println("hello world, i am scala");
    val a: Int = 100
    val i1: Int = 200
    val i4: Int = i1
    val i5: Int = i4
    val i6: Int = i5
    println(a)

    val p = new Person("anan", 12);
//    p.age = 20;
//    println(p.name)
    println(p.age)
//
//    p.sayName()
//    println(p.gender)
//
//    val p1 = new Person("diaochan", 18, 'F')
//    println(p1.gender)


    println("bottom of obj")
  }
}

// Person
class Person(xname: String, xage: Int) {
  private val name = xname;
  var age = xage;
  var gender = 'M' // set default value


  println("****************in Person start ***************")

  // 重写构造
  def this(yname: String, yage: Int, ygender: Char) {
    this(yname, yage) // 首先调用默认的构造
    this.gender = ygender
  }

  def sayName(): Unit = {
    println("hello: "+name + ClassAndObj.name)
  }

  println("****************in Person end *=======================")

}