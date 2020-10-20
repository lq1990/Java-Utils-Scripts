package math0.ml.minmaxscale;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.ml.feature.MinMaxScaler;
import org.apache.spark.ml.feature.MinMaxScalerModel;
import org.apache.spark.ml.linalg.DenseVector;
import org.apache.spark.ml.linalg.VectorUDT;
import org.apache.spark.mllib.linalg.Vector;
import org.apache.spark.mllib.stat.MultivariateStatisticalSummary;
import org.apache.spark.mllib.stat.Statistics;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.SQLContext;
import org.apache.spark.sql.types.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * 使用spark的model，
 * 当transform时，如果test数据大于或小于bound，
 * 如何？
 *
 * in source code, 和我的预期一样，会超过min/max bound
 *
 * @author liuqiang
 * @since 5/27/20 2:39 PM
 */
public class TestMinMaxScaleSummary {
    static SparkConf conf = new SparkConf().setAppName("test").setMaster("local");
    static JavaSparkContext sc = new JavaSparkContext(conf);
    static SQLContext sqlContext = new SQLContext(sc);
    static String name = "fs";

    public static void main(String[] args) {
        sc.setLogLevel("ERROR");

        String path  = "/root/lq/myBitBucket/myjava/myutils/src/test/resources/operation/feature/minmaxscale/minmaxscale_basic.csv";
        Dataset<Row> df = file2df(path);

//        Dataset<Row> describe = df.describe(name);
//        Dataset<Row> summaryMax = describe.filter("summary = 'max'");
//        Dataset<Row> nameRow = summaryMax.select(name);
//
//        System.out.println("");


    }

    public static void test01 () {
        String path  = "/root/lq/myBitBucket/myjava/myutils/src/test/resources/operation/feature/minmaxscale/minmaxscale_basic.csv";
        Dataset<Row> df = file2df(path);

        MinMaxScaler scaler = new MinMaxScaler().setInputCol(name).setMin(0d).setMax(1.);
        MinMaxScalerModel model = scaler.fit(df);

        System.out.println("mins: "+Arrays.toString(model.originalMin().toArray()));
        System.out.println("maxs: " + Arrays.toString(model.originalMax().toArray()));

        // test
        String path2  = "/root/lq/myBitBucket/myjava/myutils/src/test/resources/operation/feature/minmaxscale/minmaxscale_basic_test.csv";
        Dataset<Row> df_test = file2df(path2);
        Dataset<Row> transform = model.transform(df_test);
        transform.show();
    }

    private static Dataset<Row> file2df (String filepath) {
        Dataset<Row> dataset = sqlContext.read().csv(filepath);
        final StructField[] fields = new StructField[1];
        fields[0] = new StructField(name, new VectorUDT(), true, Metadata.empty());
        StructType structType = new StructType(fields);


        JavaRDD<Row> rowJavaRDD = dataset.javaRDD().mapPartitions(new FlatMapFunction<Iterator<Row>, Row>() {

            public Iterator<Row> call(Iterator<Row> iterator) throws Exception {
                List<Row> results = new ArrayList<Row>();

                while (iterator.hasNext()) {
                    Row row = iterator.next();
                    double val0 = Double.parseDouble(row.getString(0));
                    double val1 = Double.parseDouble(row.getString(1));
                    double val2 = Double.parseDouble(row.getString(2));
                    double val3 = Double.parseDouble(row.getString(3));

                    results.add(RowFactory.create(new DenseVector(new double[] {val0, val1, val2, val3})));
                }

                return results.iterator();
            }
        });

        //  summary
        JavaRDD<org.apache.spark.mllib.linalg.Vector> vectorJavaRDD = rowJavaRDD.mapPartitions(new FlatMapFunction<Iterator<Row>, Vector>() {

            public Iterator<Vector> call(Iterator<Row> iterator) throws Exception {

                List<Vector> results = new ArrayList<Vector>();

                while (iterator.hasNext()) {
                    Row row = iterator.next();
                    org.apache.spark.ml.linalg.Vector vector = (org.apache.spark.ml.linalg.Vector) row.getAs(0);

                    results.add(new org.apache.spark.mllib.linalg.DenseVector(vector.toArray()));
                }

                return results.iterator();
            }
        });

        MultivariateStatisticalSummary summary = Statistics.colStats(vectorJavaRDD.rdd());
        Vector max = summary.max();
        System.out.println("max: "+Arrays.toString(max.toArray()));
        System.out.println("min: "+Arrays.toString(summary.min().toArray()));
        System.out.println("count:" +summary.count());
        System.out.println("mean: "+Arrays.toString(summary.mean().toArray()));
        System.out.println("var: "+Arrays.toString(summary.variance().toArray()));


        // === end ===

        return sqlContext.createDataFrame(rowJavaRDD, structType);
    }


}
