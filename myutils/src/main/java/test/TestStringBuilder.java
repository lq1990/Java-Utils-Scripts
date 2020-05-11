package test;

/**
 * @author liuqiang
 * @since 2020/5/8 20:59
 */
public class TestStringBuilder {
    public static void main(String[] args) {

        StringBuilder sb = new StringBuilder();
        sb.append("a");
        sb.append("b");
        sb.append("c");
        sb.append("d");

        System.out.println(sb);

        sb.deleteCharAt(sb.length() - 1);
        System.out.println(sb);
    }
}
