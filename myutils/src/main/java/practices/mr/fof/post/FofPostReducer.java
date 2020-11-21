package practices.mr.fof.post;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.util.StringUtils;

import java.io.IOException;

/**
 * @author liuqiang
 * @since 2020-11-21 15:58
 */
public class FofPostReducer extends Reducer<FofPostEntity, Text, Text, Text> {

    Text rkey = new Text();
    Text rval = new Text();

    @Override
    protected void reduce(FofPostEntity key, Iterable<Text> values, Context context) throws IOException, InterruptedException {

        // key: left, right, num
        // value: {right:num, ...}
        rkey.set(key.left);

        StringBuilder sb = new StringBuilder();
        int count = 0;
        for (final Text value : values) {
            String[] split = StringUtils.split(value.toString(), ':');
            String fname = split[0].trim();

            sb.append(fname).append(",");

            if (++count == 2) break;
        }

        sb.deleteCharAt(sb.length() - 1);

        rval.set(sb.toString());
        context.write(rkey, rval);
    }
}
