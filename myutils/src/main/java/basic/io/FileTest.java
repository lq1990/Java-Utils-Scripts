package basic.io;

import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.*;

/**
 * @author liuqiang
 * @since 6/4/20 3:50 PM
 */
public class FileTest {
    public static void main(String[] args) throws IOException {
//        FileUtils.write("/atest22/abc/abcd.txt", "555");

    }
}

final class FileUtils {
    private FileUtils() {
    }

//    public static void write(final String path, final String content) throws IOException {
//        File file = new File(path);
//
//        if (file.isDirectory()) {
//            throw new IOException("FILE_IS_FOLDER: " + path);
//        }
//
//        String dir = XFileUtils.getPosixFileDirectory(path);
//        if (XStringUtils.isNotBlank(dir)) {
//            File parentFile = new File(dir);
//            System.out.println("dir: " + dir);
//
//            if (!parentFile.exists()) {
//                parentFile.mkdirs();
//            }
//        }
//
//        if (file.exists()) {
//            file.delete();
//        } else {
//            try {
//                file.createNewFile();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//
//        FileOutputStream fileOutputStream = null;
//        try {
//            fileOutputStream = new FileOutputStream(file);
//            fileOutputStream.write(content.getBytes());
//            fileOutputStream.flush();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (fileOutputStream != null) {
//                    fileOutputStream.close();
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
}
