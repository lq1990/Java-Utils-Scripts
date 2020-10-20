package basic.collections.set;

import java.util.HashSet;

/**
 * @author liuqiang
 * @since 2020-10-12 14:21
 */
public class SetTest01 {
    private static HashSet<String> methods = new HashSet<String>() {{
        add("numTopFeatures");
        add("percentile");
        add("fpr");
        add("fdr");
        add("fwe");
    }};

    public static void main(String[] args) {
        System.out.println(methods.contains("fpd"));
        System.out.println(methods.contains("fpr"));
    }
}
