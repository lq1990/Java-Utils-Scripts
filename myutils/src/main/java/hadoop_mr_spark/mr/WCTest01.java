package hadoop_mr_spark.mr;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * client
 * of
 * word count
 *
 * build jar, and run: $ hadoop jar ...
 *
 * @author liuqiang
 * @since 2020-10-19 16:35
 */
public class WCTest01 {
    public static void main(String[] args) throws Exception{
        Configuration conf = new Configuration(true);
//        conf.addResource(new URL("hdfs://192.168.101.123:8020"));
//        FileSystem fs = FileSystem.get(new URI("hdfs://192.168.101.123:8020"), conf);


        // create a new job
        Job job = Job.getInstance(conf);

        job.setJarByClass(WCTest01.class);

        // specify various job-specific params
        job.setJobName("mywordcount");

        // in out
        FileInputFormat.addInputPath(job, new Path("/user/root/words.txt")); // hdfs path

        Path output = new Path("/data/wc/output");
        if (output.getFileSystem(conf).exists(output)) {
            output.getFileSystem(conf).delete(output,true);
        }
        FileOutputFormat.setOutputPath(job, output);

        job.setMapperClass(MyMapper.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);

        job.setReducerClass(MyReducer.class);


        // submit the job, then poll for progress until the job is complete
        job.waitForCompletion(true);

    }
}
