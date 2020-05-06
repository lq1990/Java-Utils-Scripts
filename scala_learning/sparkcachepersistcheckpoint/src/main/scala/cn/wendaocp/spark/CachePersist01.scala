package cn.wendaocp.spark

import org.apache.spark.storage.StorageLevel
import org.apache.spark.{SparkConf, SparkContext}

/**
 * 持久化算子：cache,
 *  cache() 默认把RDD中的数据存在内存中，懒执行
 *
 * 持久化算子：persist。懒执行的，需要Action算子触发执行。
   * rdd.cache() => rdd.persist(StorageLevel.MEMORY_ONLY) => rdd.persist()
   * StorageLevel分为：
     * NONE
     * DISK_ONLY
     * DISK_ONLY_2
     * MEMORY_ONLY   常用。当数据量超出内存空间时，则数据不被持久化
     * MEMORY_ONLY_2
     * MEMORY_ONLY_SER  常用
     * MEMORY_ONLY_SER_2
     * MEMORY_AND_DISK  常用。指：当内存不够时，放磁盘中
     * MEMORY_AND_DISK_2
     * MEMORY_AND_DISK_SER  常用
     * MEMORY_AND_DISK_SER_2
 *
 * cache/persist注意：
   * 1. cache和persist 都是懒执行，需要Action算子触发
   * 2. 对一个RDD .cache or .persist之后可以赋值给一个变量，下次直接使用这个变量，就是使用的持久化的rdd
   * 3. 如果赋值给一个变量，那么不要紧跟: res = rdd.cache().collect()
 *
 * 持久化算子：checkpoint
 *   1. 如同复活点。会持久化到磁盘。
 *     当链条上最新的点数据丢失时，会在DAG往前找checkpoint. 而不用一直往前找。
       因为checkpoint是持久化到磁盘，所以不应该每个rdd都用。

     2. checkpoint 有切断的功能，切断与DAG之前rdd的依赖。
 *
 *   3. 对比 persist(Disk_only): 这个会在当前app运行完了后，磁盘数据清除。
 *     而checkpoint的不会清除，即其他app也可以用。
 *
 *   4. 在某些特定场景，必须用checkpoint。否则报错。
 *
 *   使用checkpoint小技巧：
 *   + 若计划对某个rdd进行 checkpoint，在此之前可以先对rdd cache。这样checkpoint时，就会直接用cache的值，而不用重复计算了
 *
 *
 *
 * @author liuqiang
 * @since 2020/5/1 16:14
 */
object Cache01 {
  def main(args: Array[String]): Unit = {
    println("test cache01")
    val conf = new SparkConf().setMaster("local").setAppName("cacheTest")
    val sc = new SparkContext(conf)
    sc.setLogLevel("WARN")

    val rdd = sc.textFile("E:\\pressureTestData\\ecommerce-behavior\\ecommerce-behavior-data-from-multi-category-store\\split_200MB\\ecom_200mb_0.csv")

//    rdd.cache() // 对rdd进行cache，优点：后面当rdd被多次使用的时候，rdd不会重新加载
    rdd.persist(StorageLevel.MEMORY_ONLY) // StorageLevel.MEMORY_ONLY 等价于cache


    val start1 = System.currentTimeMillis()
    val res1 = rdd.count() // 第一次的Action算子，会触发懒执行算子的执行。
    val end1 = System.currentTimeMillis()
    println(s"count = $res1,  time = ${end1 - start1}")

    val start2 = System.currentTimeMillis()
    val res2 = rdd.count() // 第二次使用到rdd时，由于rdd被cache了，所以直接从内存中读取rdd。速度要快40倍。
    val end2 = System.currentTimeMillis()
    println(s"count = $res2,  time = ${end2 - start2}")

    sc.stop()
  }

}
