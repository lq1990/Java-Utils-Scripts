package cn.wendaocp.scala

trait IsEqual {

  def isEqu(o: Any): Boolean
  def isNotEqu(o: Any): Boolean = {
    !isEqu(o)
  }

}

/**
 * @author liuqiang
 * @since 2020/4/28 18:58
 */
object Trait02 {
  def main(args: Array[String]): Unit = {
    val p1 = new Point(1,2)
    val p2 = new Point(1, 22)
    println(p1.isEqu(p2))
    println(p1.isNotEqu(p2))
    
  }
}

class Point(xx: Int, xy:Int ) extends IsEqual {

  val x = xx
  val y = xy

  override def isEqu(o: Any): Boolean = {
    o.isInstanceOf[Point]&&o.asInstanceOf[Point].x == this.x
  }

}
