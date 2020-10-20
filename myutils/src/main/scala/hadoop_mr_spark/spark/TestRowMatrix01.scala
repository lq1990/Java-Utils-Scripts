package hadoop_mr_spark.spark

import org.apache.spark.{SparkConf, SparkContext}

/**
 * @author liuqiang
 * @since 7/13/20 3:54 PM
 */
object TestRowMatrix01 {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local").setAppName("test")
    val sc = new SparkContext(conf)

    val paramatrix1 = sc.parallelize(List(Vector(2, 2, 4), Vector(3, 2, 1), Vector(1, 3, 2)))

    print(paramatrix1)
  }

}
