package hadoop_mr_spark.mr.basic;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.util.StringTokenizer;

/**
 * @author liuqiang
 * @since 2020-10-19 19:11
 */
public class MyMapper extends Mapper<Object, Text, Text, IntWritable> {

    private final static IntWritable one = new IntWritable(1); // data type which is serializable for MR
    private Text word = new Text(); // same as String

    /**
     * for coder, only focus on map() and reduce(),
     * do not need to worry about the inner work of mr, e.g. compute moves to data.
     *
     * @param key
     * @param value
     * @param context
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    protected void map(Object key, Text value, Context context) throws IOException, InterruptedException {
        // scenario: word count. e.g.  hello a hello b
        // key: stores offset of char. used not for our code.
        StringTokenizer iter = new StringTokenizer(value.toString()); // split string
        while (iter.hasMoreTokens()) {
            word.set(iter.nextToken());
            context.write(word, one);
            // hello 1
            // a     1
            // hello 1
            // b     1

            // blocked, and serialize the k-v to array.
            // so word can be only one instance, and set()
        }

    }

}
