package spark

import org.apache.spark.ml.{Pipeline, PipelineModel}
import org.apache.spark.ml.classification.LogisticRegression
import org.apache.spark.ml.feature.{HashingTF, Tokenizer}
import org.apache.spark.ml.linalg.Vectors
import org.apache.spark.ml.linalg.Vector
import org.apache.spark.ml.param.ParamMap
import org.apache.spark.sql.SQLContext
import org.apache.spark.sql.Row
import org.apache.spark.{SparkConf, SparkContext}

/**
 * @author liuqiang
 * @since 6/6/20 6:18 PM
 */
object TestPipeline {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local").setAppName("test")
    val sc = new SparkContext(conf)
    sc.setLogLevel("WARN")
    val sqlContext = new SQLContext(sc)

//    train_test(sqlContext)

//    pipelineFitSave(sqlContext)


    pipelineLoadTransform(sqlContext)


  }

  def train_test(sqlContext: SQLContext): Unit = {
    // training dataset with label/features
    val training = sqlContext.createDataFrame(Seq(
      (1.0, Vectors.dense(0.0, 1.1, 0.1)),
      (0.0, Vectors.dense(2.0, 1.0, -1.0)),
      (0.0, Vectors.dense(2.0, 1.3, -1.0)),
      (1.0, Vectors.dense(0.0, 1.2, -0.5))
    )).toDF("label", "features")

    // logreg instance
    val lr = new LogisticRegression() // instance
    lr.setMaxIter(10).setRegParam(0.01) // set params

    // fit
    val model1 = lr.fit(training)

    println("model1's params: " + model1.parent.extractParamMap)

    // set params using ParamMap
    val paramMap = ParamMap(lr.maxIter -> 20).put(lr.maxIter, 30).put(lr.regParam -> 0.1, lr.threshold -> 0.55)
    val paramMap2 = ParamMap(lr.probabilityCol -> "myProbability")
    val paramMapCombined = paramMap ++ paramMap2 // 结合

    // now, use combined paramMap to train a model
    val model2 = lr.fit(training, paramMapCombined)
    println("model2's params: "+ model2.parent.extractParamMap)


    // ===============================================================
    // ========================test=============================
    // ===============================================================

    val test = sqlContext.createDataFrame(Seq(
      (1.0, Vectors.dense(-1.0, 1.5, 1.3)),
      (0.0, Vectors.dense(3.0, 2.0, -0.1)),
      (1.0, Vectors.dense(0.0, 2.2, -1.5))
    )).toDF("label", "features")

    model2.transform(test).select("features", "label", "myProbability", "prediction")
      .collect()
      .foreach {
        case Row(features: Vector, label: Double, prob: Vector, prediction: Double) =>println(s"($features, $label) -> prob=$prob, prediction=$prediction")
      }
  }

  def pipelineFitSave(sqlContext: SQLContext): Unit = {

    // data
    val training = sqlContext.createDataFrame(Seq(
      (0L, "a b c d e spark", 1.0),
      (1L, "b d", 0.0),
      (2L, "spark f g h", 1.0),
      (3L, "hadoop mapreduce", 0.0)
    )).toDF("id", "text", "label")

    // 3 stages: tokenizer, hashingTF, lr。 此处直接set 参数了
    val tokenizer = new Tokenizer().setInputCol("text").setOutputCol("words")
    val hashingTF = new HashingTF().setNumFeatures(1000).setInputCol(tokenizer.getOutputCol).setOutputCol("features")
    val logisticRegression = new LogisticRegression().setMaxIter(10).setRegParam(0.001)

    val pipeline = new Pipeline().setStages(Array(tokenizer, hashingTF, logisticRegression)) // set stages in pipeline

    // fit
    val model = pipeline.fit(training) // 可以ParamMap 设置参数

    // save。此处使用 pipeline save的好处：只有一次 磁盘写入。 在exd中，每个算子都会有一次磁盘写入
    model.write.overwrite().save("/atest/spark/lrmodel") // after fit
    pipeline.write.overwrite().save("/atest/spark/unfit-lrmodel") // unfit pipeline

    println("over")

  }

  def pipelineLoadTransform(sqlContext: SQLContext): Unit = {
    val pipeline: Pipeline   = Pipeline.load("/atest/spark/unfit-lrmodel")
    val model: PipelineModel = PipelineModel.load("/atest/spark/lrmodel")

    val test = sqlContext.createDataFrame(Seq(
      (4L, "spark i j k"),
      (5L, "l m n"),
      (6L, "spark hadoop spark"),
      (7L, "apache hadoop")
    )).toDF("id", "text")

    model.transform(test)
      .select("id", "text", "probability", "prediction")
      .collect()
      .foreach {
        case Row(id: Long, text: String, prob: Vector, prediction: Double) =>println(s"($id, $text) --> prob=$prob, prediction=$prediction")}


  }

}
