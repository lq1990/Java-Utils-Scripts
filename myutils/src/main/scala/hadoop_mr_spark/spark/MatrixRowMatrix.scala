package hadoop_mr_spark.spark

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.rdd._
import org.apache.spark.mllib.linalg._
import org.apache.spark.sql.SQLContext

/**
 *
 * Matrix 2 RowMatrix
 *
 * @author liuqiang
 * @since 7/13/20 4:45 PM
 */
object MatrixRowMatrix {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local").setAppName("test")
    val sc = new SparkContext(conf)
    sc.setLogLevel("WARN")
    val sqlContext = new SQLContext(sc)

    val denseData = Seq(
      Vectors.dense(0.0, 1.0, 2.0),
      Vectors.dense(3.0, 4.0, 5.0),
      Vectors.dense(6.0, 7.0, 8.0),
      Vectors.dense(9.0, 0.0, 1.0)
    )

    val dm: Matrix = Matrices.dense(3, 2, Array(1.0, 3.0, 5.0, 2.0, 4.0, 6.0))

    /*
    1 2
    3 4
    5 6
     */

    val rdd: RDD[Vector] = matrixToRDD(sc, dm)

    print(rdd.collect())



  }

  def matrixToRDD(sc: SparkContext, mat: Matrix): RDD[Vector] = {
    val columns: Iterator[Array[Double]] = mat.toArray.grouped(mat.numRows)
    val rows: Seq[Seq[Double]] = columns.toSeq.transpose // Skip this if you want a column-major RDD.
    val vectors: Seq[Vector] = rows.map(row =>  Vectors.dense(row.toArray))
    sc.parallelize(vectors)
  }




}
