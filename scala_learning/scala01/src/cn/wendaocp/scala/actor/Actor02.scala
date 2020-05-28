package cn.wendaocp.scala.actor

import scala.actors.Actor
import scala.io.StdIn

case class Msg(actor: Actor, message: String)

class MyActor1 extends Actor {
  override def act(): Unit = {
    while (true) {
      receive {
        case msg: Msg => {
          if ("hello".equals(msg.message)) {
            println(msg.message)
            // 再回复消息
            msg.actor ! "hi"

          } else if ("Could we have a date?".equals(msg.message)) {
            println(msg.message)
            msg.actor ! "no problem"
          }

        }
        case _ => println("no match")
      }
    }
  }
}

class MyActor2(actor: Actor) extends Actor {

  actor ! Msg(this, "hello")

  override def act(): Unit = {
    while (true) {
      receive {
        case s: String => {
          if ("hi".equals(s)) {
            println(s"type is String, value is $s")
            actor ! Msg(this, "Could we have a date?")
          } else if("no problem".equals(s)) {
            println(s)
//            StdIn.readLine()
          }

        }
        case _ => println("no match")
      }
    }
  }
}

/**
 * actor 之间通信
 *
 * @author liuqiang
 * @since 2020/4/29 9:53
 */
object Actor02 {
  def main(args: Array[String]): Unit = {
    val actor1 = new MyActor1()
    val actor2 = new MyActor2(actor1)

    actor1.start()
    actor2.start()


  }

}
