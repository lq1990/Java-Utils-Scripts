package hadoop_mr_spark.mr.friend_recom;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.util.StringUtils;

import java.io.IOException;

/**
 * out:
 *  text: p1:p2
 *  int:  0|1
 *
 * @author liuqiang
 * @since 2020-11-12 20:38
 */
public class FMapper extends Mapper<LongWritable, Text, Text, IntWritable> {

    Text mkey = new Text();
    IntWritable mval = new IntWritable();


    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        // tom hello hadoop cat

        String[] strs = StringUtils.split(value.toString(), ' ');

        for (int i = 1; i < strs.length; i++) {
            mkey.set(getFof(strs[0], strs[i]));
            mval.set(0); // 0: direct
            context.write(mkey, mval);

            for (int i1 = i+1; i1 < strs.length; i1++) {
                mkey.set(getFof(strs[i], strs[i1]));
                mval.set(1); // indirect. note: this may contain direct
                context.write(mkey, mval);
            }
        }

    }

    public static String getFof(String s1, String s2) {
        if (s1.compareTo(s2)<0) {
            return s1 +":"+s2;
        }
        return s2+":"+s1;
    }


}
