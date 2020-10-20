package math0.ml.onehot;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.ml.feature.OneHotEncoder;
import java.util.Arrays;
import java.util.List;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.SQLContext;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.Metadata;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;

/**
 * test hadoop_mr_spark.spark.OneHotEncoder
 *
 * @author liuqiang
 * @since 5/29/20 12:04 PM
 */
public class OneHotEncoderTest {
    public static void main(String[] args) {
        SparkConf conf = new SparkConf().setAppName("test").setMaster("local");
        JavaSparkContext sc = new JavaSparkContext(conf);
        sc.setLogLevel("ERROR");
        SQLContext sqlContext = new SQLContext(sc);

        // fs 是散列的，还是vectorFeatures

        List<Row> data = Arrays.asList(
                RowFactory.create(0.0, 1.0),
                RowFactory.create(1.0, 0.0),
                RowFactory.create(2.0, 1.0),
                RowFactory.create(0.0, 2.0),
                RowFactory.create(0.0, 1.0),
                RowFactory.create(2.0, 0.0)
        );

        StructType schema = new StructType(new StructField[]{
                new StructField("categoryIndex1", DataTypes.DoubleType, false, Metadata.empty()),
                new StructField("categoryIndex2", DataTypes.DoubleType, false, Metadata.empty())
        });

        Dataset<Row> df = sqlContext.createDataFrame(data, schema);

        OneHotEncoder encoder = new OneHotEncoder();
        encoder.set(encoder.inputCol(), "categoryIndex1");
        encoder.set(encoder.outputCol(), "out1");
        encoder.setDropLast(false);

        Dataset<Row> encoded = encoder.transform(df);
        encoded.show();

    }

}
