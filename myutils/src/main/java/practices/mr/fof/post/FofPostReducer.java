package practices.mr.fof.post;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author liuqiang
 * @since 2020-11-17 16:07
 */
public class FofPostReducer extends Reducer<Text, Text, Text, Text> {

    Text rkey = new Text();
    Text rval = new Text();

    @Override
    protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        // key:  hello
        // values:  {world:2, hive:1, cat:3}

        List<String> list = new ArrayList<>();

        for (final Text value : values) {
            String[] strs = StringUtils.split(value.toString(), ':');
            String name = strs[0];
            String num = strs[1];

            list.add(num+":"+name);
        } // need to optimize, cause list would store all data, it is crazy


        list.sort(Collections.reverseOrder());

        if (list.size() == 0) {
            rval.set("");
        } else if (list.size() == 1) {
            String s1 = list.get(0).split(":")[1];
            rval.set(s1);
        } else {
            String s1 = list.get(0).split(":")[1];
            String s2 = list.get(1).split(":")[1];
            rval.set(s1+","+s2);
        }

        rkey.set(key);
        context.write(rkey, rval);
    }
}
