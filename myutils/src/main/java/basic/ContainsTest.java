package basic;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * @author liuqiang
 * @since 2020-10-17 09:07
 */
public class ContainsTest {
    public static void main(String[] args) {
        HashSet<String> set = new HashSet<>();
        set.add("abc");
        set.add("mn");
        set.add("mn1");
        set.add("mn2");
        set.add("mn3");
        set.add("mn4");
        System.out.println(set);

        System.out.println(set.contains("abc1"));

        List<String> list = new ArrayList<>();
        list.add("mn");
        list.add("mn1");

        set.removeAll(list);
        System.out.println(set);
    }
}
