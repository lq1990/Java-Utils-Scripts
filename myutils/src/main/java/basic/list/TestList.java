package basic.list;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liuqiang
 * @since 2020-11-19 09:56
 */
public class TestList {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("a1");
        list.add("a2");
        list.add(0, "a3");
        System.out.println(list);
    }
}
