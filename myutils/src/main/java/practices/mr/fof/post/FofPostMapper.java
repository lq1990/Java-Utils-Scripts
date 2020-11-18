package practices.mr.fof.post;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.util.StringUtils;

import java.io.IOException;

/**
 * @author liuqiang
 * @since 2020-11-17 08:34
 */
public class FofPostMapper extends Mapper<Object, Text, Text, Text> {

    Text mkey = new Text();
    Text mval = new Text();

    @Override
    protected void map(Object key, Text value, Context context) throws IOException, InterruptedException {
        // cat:hadoop   2
        String valueString = value.toString();
        String[] strs = StringUtils.split(valueString, '\t');
        String namesString = strs[0];
        String num = strs[1];

        String[] names = StringUtils.split(namesString, ':');
        String name1 = names[0];
        String name2 = names[1];

        mkey.set(name1);
        mval.set(name2+":"+num);
        context.write(mkey, mval);

        mkey.set(name2);
        mval.set(name1+":"+num);
        context.write(mkey, mval);

        // map output:
        // cat      hadoop:2
        // hadoop   cat:2


        // for reduce phase, cat is one group
    }
}
