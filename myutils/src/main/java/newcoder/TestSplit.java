package newcoder;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author liuqiang
 * @since 2020-12-21 20:56
 */
public class TestSplit {
    public static void main(String[] args) {
        String str = "123 456";
        String[] s = str.split(" ");
        System.out.println(Integer.parseInt(s[0]));
        System.out.println(Integer.parseInt(s[1]));

        int[][] mat = new int[3][2];
        System.out.println(Arrays.deepToString(mat));

        double d = 1.2;
        System.out.println((int)d/2+1);

        List<String> list = null;
        Map<String, String> map = null;

    }
}
