import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.mllib
import org.apache.spark.mllib.evaluation.BinaryClassificationMetrics

/**
 * precision, recall, F1
 *
 *                      Target = True      False
 * predict Position         TP = 1        FP = 0
 * predict Negative         FN = 10       TN = 89
 *
 * @author liuqiang
 * @since 5/23/20 3:22 PM
 */
object TestPrecisionRecallF1 {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("test").setMaster("local")
    val sc = new SparkContext(conf)
    sc.setLogLevel("WARN")

    val TP = Array((1.0, 1.0))  // pred=100%, label=1。注意：第一个是 预测为1的 概率

    val FP = new Array[(Double, Double)](0)  // size=0

    val TN = new Array[(Double, Double)](89)
    for (i <- TN.indices) {
      TN(i) = (0.0, 0.0)  // pred=0, label=0
    }

    val FN = new Array[(Double, Double)](10)
    for (i <- FN.indices) {
      FN(i) = (0.0, 1) // pred=0, label=1
    }

    val all = TP ++ TN ++ FN

    val scoreAndLabels: RDD[(Double, Double)] = sc.parallelize(all)


    // metrics
    val metrics = new BinaryClassificationMetrics(scoreAndLabels)

    /**
     * precision
     */
    println("precision: ")
    metrics.precisionByThreshold().collect().foreach(println)
    // (1.0, 1.0)  right, precision  ==> (1.0, 1/1)
    // (0.0, 0.11)


    /**
     * recall
     */
    println("recall: ")
    metrics.recallByThreshold().collect().foreach(println)
    // (1.0, 0.09090909)  ==> (1.0, 1/11)
    // (0.0, 1.0)

    /**
     * f1 = 2 * precision * recall / (precision + recall)
     *    = 2TP / (2TP + FP + FN)
     *
     */
    println("f1: ")
    metrics.fMeasureByThreshold().collect().foreach(println)
    // (1.0, 0.1666666)   ==> (1, 2*1*0.0.0909/(1+0.0909)=  )
    // (0.0, 0.198198)




  }
}
