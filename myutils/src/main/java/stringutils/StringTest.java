package stringutils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liuqiang
 * @since 5/29/20 6:03 PM
 */
public class StringTest {
    public static void main(String[] args) {
        List<Object> list = new ArrayList<Object>();
        list.add("abc1");
        list.add("abc2");
        list.add("abc3");


        for (Object o : list) {
            String str = o.toString();

            System.out.println(str);
            System.out.println(str.getClass());
        }



    }
}
