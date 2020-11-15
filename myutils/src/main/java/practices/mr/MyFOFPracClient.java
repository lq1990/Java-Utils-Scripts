package practices.mr;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/**

 tom hello hadoop cat
 world hadoop hello hive
 cat tom hive
 mr hive hello
 hive cat hadoop world hello mr
 hadoop tom hive world
 hello tom world hive mr

 * @author liuqiang
 * @since 2020-11-15 10:39
 */
public class MyFOFPracClient {
    public static void main(String[] args) throws Exception {

        // conf
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);
        job.setJarByClass(MyFOFPracClient.class);

        // map
        job.setMapperClass(FPracMapper.class);
        /*
         tom:hello  0
         hello:hadoop 1
         hello:cat  1
         world:hadoop 0

         */

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class); // 0: direct, 1: indirect
//        job.setPartitionerClass(FPPartitioner.class);
//        job.setSortComparatorClass(FPSortComparator.class);

        // reduce
//        job.setGroupingComparatorClass(FPGroupingComparator.class);
        job.setReducerClass(FPReducer.class); // count only if 1


        // in, out
        Path input = new Path("/data/prac/fof/input");
        FileInputFormat.addInputPath(job, input);

        Path output = new Path("/data/prac/fof/output");
        if (output.getFileSystem(conf).exists(output)) {
            output.getFileSystem(conf).delete(output,true);
        }
        FileOutputFormat.setOutputPath(job, output);

        // wait
        job.waitForCompletion(true);

    }
}
