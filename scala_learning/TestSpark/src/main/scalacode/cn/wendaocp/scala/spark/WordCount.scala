package cn.wendaocp.scala.spark

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 * @author liuqiang
 * @since 2020/4/29 11:27
 */
object WordCount {

  def main(args: Array[String]): Unit = {


  }

  def wc(args: Array[String]): Unit = {
    // conf设置
    val conf = new SparkConf()
    conf.setAppName("wordcount")
    conf.setMaster("local")

    // sc 是通往spark集群的唯一通道。 Spark底层操作的就是RDD，只有通过sc才可以创建出RDD
    val sc = new SparkContext(conf)
    sc.setLogLevel("WARN")
    val lines: RDD[String] = sc.textFile("./data/words")

    val words: RDD[String] = lines.flatMap(line => {
      line.split(" ")
    })

    val pairWords: RDD[(String, Int)] = words.map(word => {
      new Tuple2(word, 1)
    }) // (hello, 1) (xxx, 1)

    // 按照key进行分组，每一组的value进行匿名函数指定的方式计算
//    val v: RDD[(String, Int)] = pairWords.reduceByKey((v1: Int, v2: Int) => {
//      v1 + v2
//    })
    val v: RDD[(String, Int)] = pairWords.reduceByKey(_+_) // 因为v1 v2都各自只用了一次，可用 _ 表示

    v.foreach(println)

    println("===")
    // 简化代码
    sc.textFile("./data/words")
      .flatMap(_.split(" "))
      .map((_,1))
      .reduceByKey(_+_)
        .foreach(println)



    sc.stop()

  }


}
