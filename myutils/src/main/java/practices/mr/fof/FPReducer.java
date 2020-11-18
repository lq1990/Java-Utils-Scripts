package practices.mr.fof;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * @author liuqiang
 * @since 2020-11-15 11:04
 */
public class FPReducer extends Reducer<Text, IntWritable, Text, IntWritable> {

    Text rkey = new Text(); // name1:name2
    IntWritable rval = new IntWritable(); // num of fof

    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        // hello:world {1,0,1}

        int sum = 0;
        boolean has0 = false;

        for (final IntWritable value : values) {
            if (value.get() != 0) {
                sum += value.get();
            } else {
                has0 = true;
                break;
            }
        }

        if (!has0) {
            rkey.set(key);
            rval.set(sum);
            context.write(rkey, rval);
        }

         /*
         cat:hadoop	2
        cat:hello	2
        cat:mr	1
        cat:world	1
        hadoop:hello	3
        hadoop:mr	1
        hive:tom	3
        mr:tom	1
        mr:world	2
        tom:world	2
         */


    }

    private void addEntryAndStoreTop2(final Text rkeyin, final int sum, final Context context) {
        // split rkeyin, e.g. split cat:hadoop


        // write: rkeyout: rvalout
        // cat: hadoop,hello;   hadoop: hello,cat

    }


}
