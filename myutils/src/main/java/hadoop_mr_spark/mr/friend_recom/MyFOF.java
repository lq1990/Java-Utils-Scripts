package hadoop_mr_spark.mr.friend_recom;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

/**
 * friends recommendation
 *
 * Friend Of Friend.
 *
 *
 * a b c d
 * b a m n
 * m b h
 *
 * means:
 *  a and b is direct
 *  a and m is indirect, only diff one person.
 *
 * @author liuqiang
 * @since 2020-11-12 20:17
 */
public class MyFOF {

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {

        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);
        job.setJarByClass(MyFOF.class);

        // conf

        // map
        job.setMapperClass(FMapper.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class); // 0: direct,   1: indirect

        // partition, sort comparator use default

        // reduce
        // grouping comparator use default
        job.setReducerClass(FReducer.class);

        // in, out
        Path input = new Path("/data/fof/input");
        FileInputFormat.addInputPath(job, input);

        Path output = new Path("/data/fof/output");
        if (output.getFileSystem(conf).exists(output)) {
            output.getFileSystem(conf).delete(output, true);
        }
        FileOutputFormat.setOutputPath(job, output);


        // submit
        job.waitForCompletion(true);


    }

}
