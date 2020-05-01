package cn.wendaocp.spark

import org.apache.spark.{SparkConf, SparkContext}

/**
 * @author liuqiang
 * @since 2020/5/1 16:14
 */
object Cache01 {
  def main(args: Array[String]): Unit = {
    println("test cache01")
    val conf = new SparkConf().setMaster("local").setAppName("cacheTest")
    val sc = new SparkContext(conf)

    sc.textFile("E:\\pressureTestData\\ecommerce-behavior\\ecommerce-behavior-data-from-multi-category-store\\split_200MB\\")


    sc.stop()

  }

}
