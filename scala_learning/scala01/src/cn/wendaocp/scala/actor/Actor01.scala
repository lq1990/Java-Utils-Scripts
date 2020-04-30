package cn.wendaocp.scala.actor

import scala.actors.Actor

class MyActor extends Actor {
  override def act(): Unit = {
    receive { // 对消息进行匹配。类似 偏函数、即switch/case
      case s:String => println(s"type is String, value = $s")
      case _=> println("no match")
    }
  }
}
/**
 * 给Actor发送消息
 *
 * @author liuqiang
 * @since 2020/4/29 9:42
 */
object Actor01 {
  def main(args: Array[String]): Unit = {

    val at = new MyActor
    at.start() // 启动actor了，如同启动了一个线程

    at ! 100   // 使用 ！ 给at发送消息

  }
}
