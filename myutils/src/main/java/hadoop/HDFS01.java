package hadoop;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

/**
 * this node has no hadoop.
 * can: single node for hadoop with NN and DN.
 *
 * @author liuqiang
 * @since 2020-10-10 17:04
 */
public class HDFS01 {

    private static Configuration conf;
    private static FileSystem fs;

    @Before
    public void before() throws IOException {
        System.out.println("before");
        conf = new Configuration(true);
        fs = FileSystem.get(conf);
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
    public void upload() {


    }

    @After
    public void close() throws IOException {
        fs.close();
        System.out.println("close");
    }


}
