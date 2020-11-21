package practices.mr.fof.post;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * @author liuqiang
 * @since 2020-11-17 16:07
 */
public class FofPostReducer0 extends Reducer<Text, Text, Text, Text> {

    Text rkey = new Text();
    Text rval = new Text();

    @Override
    protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        // key:  hello
        // values:  {world:2, hive:1, cat:3}

        List<String> list = new ArrayList<>();

        for (final Text value : values) {
            // value: world:2
            list = addAndLeaveTop2(list, value.toString());
        }

//        for (final Text value : values) {
//            String[] strs = StringUtils.split(value.toString(), ':');
//            String name = strs[0];
//            String num = strs[1];
//
//            list.add(num + ":" + name);
//        }
//
//        list.sort(Collections.reverseOrder());
//
//        if (list.size() == 0) {
//            rval.set("");
//        } else if (list.size() == 1) {
//            String s1 = list.get(0).split(":")[1];
//            rval.set(s1);
//        } else {
//            String s1 = list.get(0).split(":")[1];
//            String s2 = list.get(1).split(":")[1];
//            rval.set(s1 + "," + s2);
//        }
//
//        rkey.set(key);

        rkey.set(key);
        StringBuilder sb = new StringBuilder();
        for (final String item : list) {
            sb.append(item.split(":")[0]).append(",");
        }
        sb.deleteCharAt(sb.length()-1);
        rval.set(sb.toString());
        context.write(rkey, rval);
    }

    /**
     * @param list    stores name:num
     * @param itemNew world:2
     * @return only top 2 items [item0 > item1]
     */
    private List<String> addAndLeaveTop2(List<String> list, String itemNew) {
        if (list == null) {
            list = new ArrayList<>();
        }

        if (list.size() == 0) {
            list.add(itemNew);
            return list;
        }

        if (list.size() == 1) {
            String item0 = list.get(0);
            if (compareItems(item0, itemNew) >= 0) {
                // item0 > itemNew
                list.add(itemNew);
            } else {
                list.add(0, itemNew);
            }

            return list;
        }

        // list.size() == 2
        String item0 = list.get(0);
        String item1 = list.get(1);

        if (compareItems(itemNew, item0) >= 0) {
            // itemNew > item0
            list.set(0, itemNew);
            list.set(1, item0);
        } else if (compareItems(itemNew, item1) >= 0) {
            list.set(1, itemNew);
        }

        return list;
    }

    private int compareItems(String item1, String item2) {
        Integer num1 = Integer.parseInt(item1.split(":")[1]);
        Integer num2 = Integer.parseInt(item2.split(":")[1]);

        if (num1 > num2) {
            return 1;
        } else if (num1.equals(num2)) {
            return 0;
        } else {
            return -1;
        }
    }

}
