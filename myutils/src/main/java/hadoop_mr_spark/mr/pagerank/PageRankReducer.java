package hadoop_mr_spark.mr.pagerank;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

/**
 * @author liuqiang
 * @since 2020-11-24 19:19
 */
public class PageRankReducer extends Reducer<Text, Text, Text, Text> {

}
