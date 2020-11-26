package hadoop_mr_spark.mr.pagerank;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * @author liuqiang
 * @since 2020-11-23 19:39
 */
public class PageRankMapper extends Mapper<Object, Text, Text, Text> {

    @Override
    protected void map(Object key, Text value, Context context) throws IOException, InterruptedException {
       /*
          A       B D
          B       C
          C       A B
          D       B C
        */

        // A    B D 1    current relations and score
        // B    0.5      calc during map
        // D    0.5

        // B    C 1
        // C    1

        // C    A B 1
        // A    0.5
        // B    0.5

        // D    B C 1
        // B    0.5
        // C    0.5

        int runCount = context.getConfiguration().getInt("runCount", 1);

    }


    /* reduce:
        A   B D 1    score=1
        A   0.5   newScore=0.5   => A    B D newScore,    diff=|score-newScore|

        B   C 1
        B   0.5+0.5+0.5   => B  C 1,  diff=|1-1.5|

        C   A B 1
        C   1+0.5       => C    A B 1, diff=|1-1.5|

        D   B C 1
        D   0.5     => D    B C 0.5,  diff=|1.0.5|
     */
}
