package hadoop_mr_spark.mr.friend_recom;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * @author liuqiang
 * @since 2020-11-14 08:30
 */
public class FReducer extends Reducer<Text, IntWritable, Text, IntWritable> {

    IntWritable rval = new IntWritable();


    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        // hello:hadoop 0
        // hello:hadoop 1
        // hello:hadoop 0

        int flag = 0;
        int sum = 0;

        for (IntWritable v : values) {
            if (v.get() == 0) {
                flag = 1;
            }

            sum += v.get();
        }

        if (flag == 0) {
            // only indirect is used
            rval.set(sum);
            context.write(key, rval);
        }

    }
}
