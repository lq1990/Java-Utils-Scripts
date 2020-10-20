package basic.set;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * 测试 set乱序的问题。
 * 在add时，是乱序继续的。
 *
 * @author liuqiang
 * @since 6/9/20 7:45 PM
 */
public class TestSetOrder {
    public static void main(String[] args) {
        Set<String> set = new HashSet<>();
        set.add("abc1");
        set.add("abc2");
        set.add("abc3");
        set.add("abc4");
        set.add("abc5");

        System.out.println("original: ");
        System.out.println(set);


        String[] strings = set.toArray(new String[0]);
        System.out.println("basic.set to String[]: ");
        System.out.println(Arrays.toString(strings));

        HashSet<Object> set1 = new HashSet<>(Arrays.asList(strings));
        System.out.println("String[] to basic.set: ");
        System.out.println(set1);

    }
}
