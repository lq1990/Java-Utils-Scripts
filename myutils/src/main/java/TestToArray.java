import scala.math.Ordering;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liuqiang
 * @since 6/9/20 6:25 PM
 */
public class TestToArray {
    public static void main(String[] args) {
        System.out.println("test toArray");

        List<String> list = new ArrayList<>();
        list.add("abc1");
        list.add("abc2");
        list.add("abc3");

        String[] strings = list.toArray(new String[0]);

        for (String string : strings) {
            System.out.println(string);
        }

    }
}
