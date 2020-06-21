import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaPairDStream;
import org.apache.spark.streaming.api.java.JavaReceiverInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import scala.Tuple2;

import java.util.Arrays;
import java.util.Iterator;

/**
 * @author liuqiang
 * @since 6/21/20 10:14 AM
 */
public class SparkStreamingTest {
    public static void main(String[] args) throws InterruptedException {
        SparkConf conf = new SparkConf().setMaster("local").setAppName("SparkStreamingTest");
        JavaSparkContext sc = new JavaSparkContext(conf);
        JavaStreamingContext jsc = new JavaStreamingContext(sc, Durations.seconds(5));

        /*
        设置5s之后，
        从此处开始，
          接受5s内的数据，一次处理
         */

        JavaReceiverInputDStream<String> socketTextStream = jsc.socketTextStream("192.168.101.130", 9999);
        // 在指定的ip机器上，$ nc -lk 9999

        JavaDStream<String> words = socketTextStream.flatMap(new FlatMapFunction<String, String>() {
            // flatMap: one to many

            private static final long serialVersionUID = 1L;

            @Override
            public Iterator<String> call(String lines) throws Exception {
                return Arrays.asList(lines.split(" ")).iterator();
            }
        });

        JavaPairDStream<String, Integer> pairWords = words.mapToPair(new PairFunction<String, String, Integer>() {

            private static final long serialVersionUID = 1L;

            @Override
            public Tuple2<String, Integer> call(String word) throws Exception {
                return new Tuple2<String, Integer>(word, 1);
            }
        });

        JavaPairDStream<String, Integer> reduceByKey = pairWords.reduceByKey(new Function2<Integer, Integer, Integer>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Integer call(Integer v1, Integer v2) throws Exception {
                return v1 + v2;
            }
        });

        // outputOperator to trigger
        reduceByKey.print(); // 某一个 outputOp算子

        jsc.start(); // 因为SS是7*24h, 需要启动
        jsc.awaitTermination(); // 等待被终结

        /*
        一直循环这段代码
         */

    }
}
