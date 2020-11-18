package practices.mr.fof.post;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/**
 * input of map:
 *      cat:hadoop	2
 *      cat:hello	2
 *      cat:mr	1
 *      cat:world	1
 *      hadoop:hello	3
 *      hadoop:mr	1
 *      hive:tom	3
 *      mr:tom	1
 *      mr:world	2
 *      tom:world	2
 *
 * @author liuqiang
 * @since 2020-11-16 09:58
 */
public class FofPostClient {

    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);

        job.setJarByClass(FofPostClient.class);

        // map
        job.setMapperClass(FofPostMapper.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(Text.class);
//        job.setPartitionerClass();
        job.setSortComparatorClass(FofPostSortComparator.class);
//        job.setCombinerClass();


        /*
        mkey:       mval:
         hello      world:2
         hello      hive:1
         hadoop     cat:3
         hadoop     tom:2
         */

        // reduce
//        job.setGroupingComparatorClass();
        job.setReducerClass(FofPostReducer.class);

        // in, out
        Path input = new Path("/data/fof/post/input");
        FileInputFormat.addInputPath(job, input);

        Path output = new Path("/data/fof/post/output");
        if (output.getFileSystem(conf).exists(output)) {
            output.getFileSystem(conf).delete(output, true);
        }
        FileOutputFormat.setOutputPath(job, output);


        // wait
        job.waitForCompletion(true);

    }

}
