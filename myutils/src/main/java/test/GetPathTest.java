package test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author liuqiang
 * @since 2020/5/9 13:28
 */
public class GetPathTest {

    public static void main(String[] args) throws IOException {
        String path = getPath("C:\\Users\\liuqi\\myBitbucket\\myjava\\myutils\\src\\main\\java\\test\\/");

        System.out.println(path.toString());

        FileReader fileReader = new FileReader(path+"words.txt");
        System.out.println(fileReader.read());

    }

    public static String getPath(String p) {
        final StringBuilder sb = new StringBuilder();
        if (null != p) { // XStringUtils.
            final int len = p.length();
            if (p.charAt(len -1) == '/') {
                if (len > 1 && p.charAt(len - 2) == '\\') {
                    System.out.println("pos1");
//                    sb.append(p);
                    sb.append(p).append('/');
                } else {
                    System.out.println("pos2");
                    sb.append(p);
                }
            } else {
                System.out.println("pos3");
                sb.append(p).append('/');
            }
        }

        return sb.toString();
    }
}
