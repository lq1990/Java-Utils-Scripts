package basic.stringutils;

/**
 * @author liuqiang
 * @since 2020/4/29 17:11
 */
public class StringUtils {

    public static void main(String[] args) {
        String p = "abc/";
        String s = ensurePathEndsWithSlash(p);
        System.out.println(s);
    }

    public static String ensurePathEndsWithSlash(String path) {
        if (path.endsWith("/")) {
            return path;
        } else {
            return path + "/";
        }
    }

}
