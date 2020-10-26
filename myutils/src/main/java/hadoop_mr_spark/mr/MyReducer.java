package hadoop_mr_spark.mr;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * word count
 *
 * @author liuqiang
 * @since 2020-10-19 19:22
 */
public class MyReducer extends Reducer<Text, IntWritable, Text, IntWritable> {

    private IntWritable result = new IntWritable();

    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        // hello 1
        // hello 1
        // ...

        // key: hello
        // value: {1,1,...}

        int sum = 0;
        for (final IntWritable value : values) {
            sum += value.get();
        }

        result.set(sum);
        context.write(key, result);
    }

}
