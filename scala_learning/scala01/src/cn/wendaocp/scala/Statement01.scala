package cn.wendaocp.scala

/**
 * if/else
 * for
 * while
 * do/while
 * yield
 *
 * s"$n"
 *
 * @author liuqiang
 * @since 2020/4/28 9:30
 */
object Statement01 {

  def main(args: Array[String]): Unit = {
    println("test statement")

    mydowhile

  }

  def mydowhile = {
    var i=0
    while(i<100) {
      println(s"第$i 次求婚")
      i = i+1
    }

    i=0
    do {
      println(s"第$i 次求婚")
      i += 1
    }while(i<100)
  }


  def ifelse = {
    val age = 200
    if (age<=20) {
      print("age<=20")
    } else if(age > 20 && age<=30) {
      print("age>20 && age<=30")
    } else {
      println("age > 30")
    }
  }

  def myfor = {

    val r = 1.to(10, 2) // Range(1, 3, 5, 7, 9)
    val r1 = 1 until 10 // Range(1,2,3,..., 9)
    println(r)
    println(r1)

    for (i <- 1 to 10) {
      println(i)
    }

    println()
    // 9x9 乘法口诀
    for (i <- 1 to 9) {
      for (j <- 1 to i) {
//        print(i+"*" + j + "=" + i*j+"  ")
        print(s"$i*$j="+i*j+"   ")
      }
      println()
    }

    // 上面的2层for，另种写法
    for (i <- 1 to 9; j <- 1 to i) {
        //        print(i+"*" + j + "=" + i*j+"  ")
        print(s"$i*$j="+i*j+"   ")

      if (i==j) {
        println()
      }
    }

    println()
    for(i <- 1 to 1000; if(i>500); if(i%2==0)) {
      print(i+"  ")
    }

    println()
    val result = for(i <- 1 to 100; if(i>50); if(i%2==0)) yield i;
    println(result) // Vector(,,,,)


  }

}
