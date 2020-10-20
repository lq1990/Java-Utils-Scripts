package hadoop_mr_spark.mr;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * @author liuqiang
 * @since 2020-10-19 19:11
 */
public class MyMapper extends Mapper<Object, Text, Text, IntWritable> {

    @Override
    protected void map(Object key, Text value, Context context) throws IOException, InterruptedException {
        // scenario: word count. e.g.  hello a hello b
        // key: stores offset of char. used not for our code.


        super.map(key, value, context);
    }

}
