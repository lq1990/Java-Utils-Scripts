package cn.wendaocp.java.spark;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.api.java.function.VoidFunction;
import scala.Tuple2;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * @author liuqiang
 * @since 2020/4/29 12:59
 */
public class WordCount {

    public static void main(String[] args) {

    }

    public static void wc(String[] args) {
        SparkConf conf = new SparkConf();
        conf.setMaster("local");
        conf.setAppName("wc");

        JavaSparkContext sc = new JavaSparkContext(conf);
        sc.setLogLevel("WARN");

        JavaRDD<String> lines = sc.textFile("./data/words");
        JavaRDD<String> words = lines.flatMap((FlatMapFunction<String, String>) line -> {
            String[] s = line.split(" ");
            List<String> strings = Arrays.asList(s);
            return strings.iterator();
        });

        JavaPairRDD<Object, Integer> pairWords = words.mapToPair(new PairFunction<String, Object, Integer>() {
            @Override
            public Tuple2<Object, Integer> call(String s) throws Exception {
                return new Tuple2<>(s, 1);
            }
        });

        JavaPairRDD<Object, Integer> result = pairWords.reduceByKey(new Function2<Integer, Integer, Integer>() {
            @Override
            public Integer call(Integer v1, Integer v2) throws Exception {
                return v1 + v2;
            }
        });


        result.foreach(new VoidFunction<Tuple2<Object, Integer>>() {
            @Override
            public void call(Tuple2<Object, Integer> t) throws Exception {
                System.out.println(t);
            }
        });




        sc.stop();
        System.out.println("over");

    }

}
