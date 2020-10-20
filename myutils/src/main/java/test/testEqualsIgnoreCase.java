package test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liuqiang
 * @since 6/1/20 9:39 AM
 */
public class testEqualsIgnoreCase {
    public static void main(String[] args) {
        List<String> list = new ArrayList<String>();
        list.add("abc");
        list.add("abc1");


        System.out.println(list.contains("abc"));  // false
        System.out.println(list.contains("abc1"));  // false
        System.out.println(list.contains("ABC"));  // false
    }
}
