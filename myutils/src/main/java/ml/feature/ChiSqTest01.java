package ml.feature;

import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.ml.feature.ChiSqSelector;
import org.apache.spark.ml.feature.ChiSqSelectorModel;
import org.apache.spark.ml.linalg.VectorUDT;
import org.apache.spark.ml.linalg.Vectors;
import org.apache.spark.sql.*;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.Metadata;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;

import java.util.Arrays;
import java.util.List;

/**
 * @author liuqiang
 * @since 2020-10-12 10:54
 */
public class ChiSqTest01 {
    public static void main(String[] args) {
        SparkConf sparkConf = new SparkConf().setAppName("chisq").setMaster("local");
        SparkContext sc = new SparkContext(sparkConf);
        sc.setLogLevel("WARN");
        SQLContext sqlContext = new SQLContext(sc);

//        SparkSession sqlContext = SparkSession.builder().appName("chisq").master("local").getOrCreate();

        List<Row> rows = Arrays.asList(
                RowFactory.create(1, Vectors.dense(0.0, 0.0, 18.0, 1.0), 1),
                RowFactory.create(2, Vectors.dense(0.0, 1.0, 12.0, 0.0), 0),
                RowFactory.create(3, Vectors.dense(1.0, 0.0, 15.0, 0.1), 0));

        StructType schema = new StructType(new StructField[]{
                new StructField("id", DataTypes.IntegerType, false, Metadata.empty()),
                new StructField("features", new VectorUDT(), false, Metadata.empty()),
                new StructField("label", DataTypes.IntegerType, false, Metadata.empty())
        });

        Dataset<Row> df = sqlContext.createDataFrame(rows, schema);
        df.show();

        // chisq
        ChiSqSelector chiSqSelector = new ChiSqSelector()
//                .setNumTopFeatures(2)
                .setSelectorType("percentile")// numTopFeatures as default, percentile, fpr, fdr, fwe
                .setPercentile(0.5)
//                .setSelectorType("fpr").setFpr(0.3)
//                .setSelectorType("fdr").setFdr(0.4)
//                .setSelectorType("fwe").setFwe(0)
                .setFeaturesCol("features")
                .setLabelCol("label");
//                .setOutputCol("selected");

        ChiSqSelectorModel model = chiSqSelector.fit(df);
        int[] ints = model.selectedFeatures();// 0-based index of features
        System.out.println(Arrays.toString(ints));

        Dataset<Row> transform = model.transform(df);
        transform.show();

    }
}
