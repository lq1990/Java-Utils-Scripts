package test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liuqiang
 * @check nick
 * @since 6/8/20 6:21 PM
 */
public class TestFinalInFor {
    public static void main(String[] args) {
        for (int i=0; i<3; ++i) {
            final List<String> list = new ArrayList<>();
            list.add("abc"+i);
            System.out.println(list);
            System.out.println(list.hashCode());
        }
    }
}
