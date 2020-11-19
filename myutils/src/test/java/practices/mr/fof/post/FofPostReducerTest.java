package practices.mr.fof.post;

import org.apache.avro.generic.GenericData;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.lib.reduce.WrappedReducer;
import org.junit.Test;
import org.apache.hadoop.mapreduce.Reducer.Context;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author liuqiang
 * @since 2020-11-19 09:06
 */
public class FofPostReducerTest {

    @Test
    public void reduce() throws IOException, InterruptedException {

        FofPostReducer reducer = new FofPostReducer();

        Text key = new Text("hello");
        List<Text> list = new ArrayList<>();
        list.add(new Text("world:2"));
        list.add(new Text("hive:1"));
        list.add(new Text("cat:3"));

//        reducer.reduce(key, list, context);

    }
}