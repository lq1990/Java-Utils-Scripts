package hadoop_mr_spark.mr.weather;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Partitioner;

///**
// * @author liuqiang
// * @since 2020-11-11 19:12
// */
//public class TPartitioner extends Partitioner<TQ, IntWritable> {
//
//
//    @Override
//    public int getPartition(TQ key, IntWritable intWritable, int numPartitions) {
//
//        return key.hashCode() % numPartitions; // problem: may cause un-balanced data distribution
//    }
//
//}
