package ml.summary;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.mllib.linalg.Vector;
import org.apache.spark.mllib.linalg.VectorUDT;
import org.apache.spark.mllib.linalg.Vectors;
import org.apache.spark.mllib.stat.MultivariateStatisticalSummary;
import org.apache.spark.mllib.stat.Statistics;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.SQLContext;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.Metadata;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * @author wendao
 * @since 5/28/20 4:29 PM
 */
public class TestSummaryPure {
    public static void main(String[] args) {
        SparkConf conf = new SparkConf().setAppName("test").setMaster("local");
        JavaSparkContext sc = new JavaSparkContext(conf);
        SQLContext sqlContext = new SQLContext(sc);

        List<Row> data = Arrays.asList(
                RowFactory.create(0, Vectors.dense(1.0, 0.1, -1.0)),
                RowFactory.create(1, Vectors.dense(2.0, 1.1, 1.0)),
                RowFactory.create(2, Vectors.dense(3.0, 10.1, 3.0))
        );
        StructType schema = new StructType(new StructField[]{
                new StructField("id", DataTypes.IntegerType, false, Metadata.empty()),
                new StructField("features", new VectorUDT(), false, Metadata.empty())
        });
        Dataset<Row> dataFrame = sqlContext.createDataFrame(data, schema);
        JavaRDD<Vector> vectorJavaRDD = dataFrame.toJavaRDD().mapPartitions(new FlatMapFunction<Iterator<Row>, Vector>() {
            public Iterator<Vector> call(Iterator<Row> iterator) throws Exception {

                List<Vector> results = new ArrayList<Vector>();

                while (iterator.hasNext()) {
                    Row row = iterator.next();
                    Vector vector = (Vector) row.getAs("features");

                    results.add(new org.apache.spark.mllib.linalg.DenseVector(vector.toArray()));
                }

                return results.iterator();
            }
        });

        //  summary
        MultivariateStatisticalSummary summary = Statistics.colStats(vectorJavaRDD.rdd());
        Vector max = summary.max();
        System.out.println("max: "+ Arrays.toString(max.toArray()));
        System.out.println("min: "+Arrays.toString(summary.min().toArray()));
        System.out.println("count:" +summary.count());
        System.out.println("mean: "+Arrays.toString(summary.mean().toArray()));
        System.out.println("var: "+Arrays.toString(summary.variance().toArray()));
    }
}
