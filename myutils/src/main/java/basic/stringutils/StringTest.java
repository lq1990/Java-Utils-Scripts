package basic.stringutils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liuqiang
 * @since 5/29/20 6:03 PM
 */
public class StringTest {
    public static void main(String[] args) {
        String name = "a.b.c";
        String replace = name.replace(".", "_");
        System.out.println(name);
        System.out.println(replace);

        List<Object> list = new ArrayList<Object>();
        list.add("abc1");
        list.add("abc2");
        list.add("abc3");

        list.clear();
        for (Object o : list) {
            String str = o.toString();

            System.out.println(str);
            System.out.println(str.getClass());
        }
        System.out.println("1");

        list = null;
        for (Object o : list) {
            String str = o.toString();

            System.out.println(str);
            System.out.println(str.getClass());
        }
        System.out.println("2");



    }
}
