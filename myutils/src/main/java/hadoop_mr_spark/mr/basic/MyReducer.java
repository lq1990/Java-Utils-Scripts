package hadoop_mr_spark.mr.basic;

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

    /**
     * for one group A, this reduce() is executed only once.
     * for other group B, this reduce() is executed also only once.
     *
     * outside this reduce(), this is a outside function, which managers iterations of data of all groups.
     *
     * 相同的key为一组，调度一次reduce方法，此reduce方法会迭代的对这一组的数据进行计算。
     * 原语中提到的recude方法，就是这个方法。
     *
     * @param key
     * @param values
     * @param context
     * @throws IOException
     * @throws InterruptedException
     */
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
