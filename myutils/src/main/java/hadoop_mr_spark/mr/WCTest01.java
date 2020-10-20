package hadoop_mr_spark.mr;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.mapreduce.Job;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * client
 * of
 * word count
 *
 * @author liuqiang
 * @since 2020-10-19 16:35
 */
public class WCTest01 {
    public static void main(String[] args) throws Exception{
        Configuration conf = new Configuration(true);
        FileSystem fs = FileSystem.get(new URI("hdfs://192.168.101.123:8020"), conf);

        // create a new job
        Job job = Job.getInstance(conf);

        job.setJarByClass(WCTest01.class);

        // specify various job-specific params
        job.setJobName("mywordcount");

        job.setMapperClass(MyMapper.class);
        job.setReducerClass(MyReducer.class);


        // submit the job
        job.waitForCompletion(true);

    }
}
