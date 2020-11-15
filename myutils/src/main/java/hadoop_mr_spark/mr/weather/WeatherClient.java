package hadoop_mr_spark.mr.weather;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

/**
 * find the hottest two days of a month
 *
 * @author liuqiang
 * @since 2020-11-02 19:58
 */
public class WeatherClient {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        System.out.println("client");

        Configuration conf = new Configuration(true);
        Job job = Job.getInstance(conf);
        job.setJarByClass(WeatherClient.class);

        // conf
//        job.setInputFormatClass();
        job.setMapperClass(TMapper.class);

        // ========= map phase =======================================
        // map, output, key
        job.setMapOutputKeyClass(TQ.class);
        // map, output, value
        job.setMapOutputValueClass(IntWritable.class);
        // map, output, partition
//        job.setPartitionerClass(TPartitioner.class);
        // map, sorter
        job.setSortComparatorClass(TSortComparator.class); // sort comparator
        // map, combiner
//        job.setCombinerClass(TCombiner.class);
        // ==== map end ==============================================

        // shuffle internal


        // === reduce phase ===============================================
        // reduce group
        job.setGroupingComparatorClass(TGroupingComparator.class); // group comparator
        // reducer
        job.setReducerClass(TReducer.class);
        // === reduce end =================================================

        // inout
        Path input = new Path("/data/in/"); // path in hdfs
        FileInputFormat.addInputPath(job, input);

        Path output = new Path("/data/out/");
        FileOutputFormat.setOutputPath(job, output);

        // reduce tasks
        job.setNumReduceTasks(2);

        // wait
        job.waitForCompletion(true);
    }
}
