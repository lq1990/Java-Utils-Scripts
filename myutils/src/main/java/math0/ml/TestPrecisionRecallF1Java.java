package math0.ml;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.mllib.evaluation.BinaryClassificationMetrics;
import org.apache.spark.rdd.RDD;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.SQLContext;
import org.apache.spark.sql.types.*;
import scala.Tuple2;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author liuqiang
 * @since 5/23/20 4:45 PM
 */
public class TestPrecisionRecallF1Java {
    public static void main(final String[] args) {
        System.out.println("test in java");

        SparkConf conf = new SparkConf().setAppName("test in java").setMaster("local");
        JavaSparkContext jsc = new JavaSparkContext(conf);
        jsc.setLogLevel("WARN");

        /**
         * TP=1,   FP=0
         * FN=10,  TN=89
         */

        // schema
        List<StructField> fields = new ArrayList<StructField>();
        fields.add(DataTypes.createStructField("pred", DataTypes.DoubleType, true));
        fields.add(DataTypes.createStructField("label", DataTypes.DoubleType, true));
        final StructType schema = DataTypes.createStructType(fields);

        SQLContext sqlContext = new SQLContext(jsc);
        final JavaRDD<Row> rowJavaRDD = sqlContext.read().csv("/root/lq/myBitBucket/myjava/myutils/src/main/java/math0.ml/data.csv").javaRDD();
        // String 2 Double
        final JavaRDD<Row> rowJavaRDD1 = rowJavaRDD.mapPartitions(new FlatMapFunction<Iterator<Row>, Row>() {

            private static final long serialVersionUID = 1L;

            public Iterator<Row> call(Iterator<Row> iterator) throws Exception {
                List<Row> result = new ArrayList<Row>();
                Row row;

                while(iterator.hasNext()) {
                    row = iterator.next();
                    Double aDouble0 = Double.valueOf(row.getString(0));
                    Double aDouble1 = Double.valueOf(row.getString(1));
                    result.add(RowFactory.create(aDouble0, aDouble1));
                }
                return result.iterator();
            }
        });

        Dataset<Row> df = sqlContext.createDataFrame(rowJavaRDD1, schema);

        BinaryClassificationMetrics metrics = new BinaryClassificationMetrics(df);
        RDD<Tuple2<Object, Object>> precision = metrics.precisionByThreshold();
        RDD<Tuple2<Object, Object>> recall = metrics.recallByThreshold();
        RDD<Tuple2<Object, Object>> f1 = metrics.fMeasureByThreshold();

        System.out.println("precision: "+precision.toJavaRDD().collect());
        System.out.println("recall   : "+recall.toJavaRDD().collect());
        System.out.println("f1       : "+f1.toJavaRDD().collect());


    }
}
