package practices.mr.fof;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.util.StringUtils;

import java.io.IOException;

/**
 * @author liuqiang
 * @since 2020-11-15 11:04
 */
public class FPracMapper extends Mapper<Object, Text, Text, IntWritable> {

    Text mkey = new Text();
    IntWritable mval = new IntWritable();

    /**
     * one map one line
     */
    @Override
    protected void map(Object key, Text value, Context context) throws IOException, InterruptedException {
        //  tom hello hadoop cat

        String valuesString = value.toString();
        String[] strs = StringUtils.split(valuesString, ' ');
        for (int i = 0; i < strs.length; i++) {
            for (int i1 = i+1; i1 < strs.length; i1++) {
                if (i == 0) {
                    mval.set(0);
                } else {
                    mval.set(1);
                }

                mkey.set(combineName(strs[i], strs[i1]));
                context.write(mkey, mval);
            }
        }
    }

    private String combineName(String left, String right) {
        if (left.compareTo(right) < 0) {
            return left+":"+right;
        } else {
            return right+":"+left;
        }
    }

}
