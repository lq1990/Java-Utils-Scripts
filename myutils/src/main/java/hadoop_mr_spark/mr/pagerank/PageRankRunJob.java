package hadoop_mr_spark.mr.pagerank;

import javafx.scene.text.Text;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.KeyValueTextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/**
 * A    B D
 *
 *
 * split, jar, conf
 *
 * @author liuqiang
 * @since 2020-11-23 08:29
 */
public class PageRankRunJob {

    public static enum Mycounter {
        my
    }

    public static void main(String[] args) {
        System.out.println("page rank main");

        Configuration conf = new Configuration();
        conf.set("mapreduce.app-submission.cross-platform", "true"); // cross linux/windows
        conf.set("mapreduce.framework.name", "local"); // local (but also use hdfs), not on cluster(yarn mode). default mr on yarn

        // if runs on cluster, needs jar and setLocationOfJar using setJar()


        double d = 0.001;
        int i = 0;

        while (i<=10000) {
            i++;
            try {
                conf.setInt("runCount", i); // pass params by conf. and get value by  context.getConfiguration().getInt("runCount", default)

                FileSystem fs = FileSystem.get(conf);
                Job job = Job.getInstance(conf); // get instance on each step
                job.setJarByClass(PageRankRunJob.class);
                job.setJobName("pr" + i);
                job.setMapperClass(PageRankMapper.class);
                job.setReducerClass(PageRankReducer.class);
                job.setMapOutputKeyClass(Text.class);
                job.setMapOutputValueClass(Text.class);
//                job.setJar("");
                job.setInputFormatClass(KeyValueTextInputFormat.class); // default Text.class. use new input format class. default use \t to seperate key/value

                Path inputPath = new Path("/data/pagerank/input/");
                if (i > 1) {
                    inputPath = new Path("/data/pagerank/output/pr" + (i - 1));
                }

                FileInputFormat.addInputPath(job, inputPath);

                Path output = new Path("/data/pagerank/output/pr" + i);
                if (fs.exists(output)) {
                    fs.delete(output, true);
                }
                FileOutputFormat.setOutputPath(job, output);

                boolean b = job.waitForCompletion(true);
                if (b) {
                    System.out.println("success");
                    long sum = job.getCounters().findCounter(Mycounter.my).getValue(); // sum saves diff between cur pr and prev pr value.
                    System.out.println(sum);

                    double avgd = sum/4000.0; // assume 4000 web pages
                    if (avgd < d) {
                        break;
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }
}
