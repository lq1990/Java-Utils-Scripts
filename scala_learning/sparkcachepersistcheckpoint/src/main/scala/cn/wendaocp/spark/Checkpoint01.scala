package cn.wendaocp.spark

import org.apache.spark.{SparkConf, SparkContext}

/**
 * cache/persist/checkpoint 持久化的单位都是 partition
 *
 * * 持久化算子：checkpoint
 * *   1. 如同复活点。会持久化到磁盘。
 * *     当链条上最新的点数据丢失时，会在DAG往前找checkpoint. 而不用一直往前找。
 * 因为checkpoint是持久化到磁盘，所以不应该每个rdd都用。
 *
 *      2. checkpoint 有切断的功能，切断与DAG之前rdd的依赖。
 * *
 * *   3. 对比 persist(Disk_only): 这个会在当前app运行完了后，磁盘数据清除。
 * *     而checkpoint的不会清除，即其他app也可以用。
 * *
 * *   4. 在某些特定场景，必须用checkpoint。否则报错。
 * *
 * *   使用checkpoint小技巧：
 * *   + 若计划对某个rdd进行 checkpoint，在此之前可以先对rdd cache。这样checkpoint时，就会直接用cache的值，而不用重复计算了
 * *
 *
 *
 * @author liuqiang
 * @since 2020/5/2 11:22
 */
object Checkpoint01 {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local").setAppName("checkpointTest")
    val sc = new SparkContext(conf)
    sc.setLogLevel("WARN")

    sc.setCheckpointDir("./checkpoint") // 设置checkpoint 目录

    val rdd = sc.textFile("./words")
    rdd.checkpoint() // lazy。会把rdd存放到dir下

    rdd.collect() // action. 触发所有lazy


    sc.stop()
  }

}
