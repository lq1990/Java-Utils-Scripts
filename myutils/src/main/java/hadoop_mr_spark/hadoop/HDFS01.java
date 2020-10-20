package hadoop_mr_spark.hadoop;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.IOUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * this node has no hadoop_mr_spark.hadoop.
 * can: single node for hadoop_mr_spark.hadoop with NN and DN.
 *
 * @author liuqiang
 * @since 2020-10-10 17:04
 */
public class HDFS01 {

    private static Configuration conf;
    private static FileSystem fs;

    @Before
    public void before() throws IOException, URISyntaxException {
        System.out.println("before\n===");

        conf = new Configuration(true);
//        fs = FileSystem.get(conf);
        fs = FileSystem.get(new URI("hdfs://192.168.101.123:8020"), conf);

//        fs = FileSystem.get(new URI("hdfs://ip:port"), conf);

    }

    @Test
    public void mkdir() throws IOException {

        Path ifile = new Path("/abcdef");
        if (fs.exists(ifile)) {
            fs.delete(ifile, true);
        }

        fs.mkdirs(ifile);
    }

    @Test
    public void upload() throws IOException {
        Path f = new Path("/abcdef/hello.txt"); // target hdfs
        FSDataOutputStream outputStream = fs.create(f);

        // input stream, input local file
        InputStream inputStream = new BufferedInputStream(
                new FileInputStream(new File("/root/data/abc.csv"))); // src local

        // in => out
        IOUtils.copyBytes(inputStream, outputStream, conf, true);

    }

    @Test
    public void download() throws IOException {
        // src hdfs
        FSDataInputStream inputStream = fs.open(new Path("/abcdef/hello.txt"));

        // target local
        OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(
                new File("/root/data/abc2.csv")
        ));

        // hdfs => local
        IOUtils.copyBytes(inputStream, outputStream, conf, true);

    }

    /*
    blocks
     */
    @Test
    public void blks() throws Exception {
        fs = FileSystem.get(new URI("hdfs://192.168.101.123:8020"), conf);

        FileStatus fileStatus = fs.getFileStatus(new Path("/user/root/abc.csv"));
//        FileStatus fileStatus = fs.getFileStatus(new Path("/user/root/upload/admin/a.csv"));
        System.out.println("len: "+fileStatus.getLen());

        BlockLocation[] blks =
                fs.getFileBlockLocations(
                        fileStatus, 0, fileStatus.getLen());

        if (blks == null || blks.length == 0) return;

        for (BlockLocation blk : blks) {
            System.out.println(blk);
        }

        FSDataInputStream inputStream = fs.open(new Path("/user/root/abc.csv"));
        System.out.print((char) inputStream.readByte());
        System.out.print((char) inputStream.readByte());
        System.out.print((char) inputStream.readByte());
        System.out.print((char) inputStream.readByte());
        System.out.print((char) inputStream.readByte());
        System.out.print((char) inputStream.readByte());
        System.out.print((char) inputStream.readByte());
        System.out.print((char) inputStream.readByte());
        System.out.print((char) inputStream.readByte());
        System.out.print((char) inputStream.readByte());
        System.out.print((char) inputStream.readByte());
        System.out.print((char) inputStream.readByte());
        System.out.print((char) inputStream.readByte());

    }

    @After
    public void close() throws IOException {
        fs.close();
        System.out.println("===\nclose");
    }


}
