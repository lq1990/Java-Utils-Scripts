package hadoop_mr_spark.mr.weather;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * @author liuqiang
 * @since 2020-11-11 19:49
 */
public class TReducer extends Reducer<TQ, IntWritable, Text, IntWritable> {

    Text rkey = new Text();
    IntWritable rval = new IntWritable();

    @Override
    protected void reduce(TQ key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        // out of map:
        // 1970 01 01 88
        // 1970 01 11 78
        // 1970 01 09 77
        // 1970 01 21 68

        int flag=0, day=0;

        for (final IntWritable value : values) {

            // only the first record
            if (flag == 0) {
                String s = key.getYear() + "-" + key.getMonth() + "-" + key.getDay() + ":" + key.getTemperature();
                rkey.set(s);
                rval.set(key.getTemperature());

                flag++;
                day = key.getDay();
                context.write(rkey, rval);
            }

            if (flag != 0 && day != key.getDay()) {
                String s = key.getYear() + "-" + key.getMonth() + "-" + key.getDay() + ":" + key.getTemperature();
                rkey.set(s);
                rval.set(key.getTemperature());

                context.write(rkey, rval);
                break; // because only get the first 2 records
            }
        }

    }
}
