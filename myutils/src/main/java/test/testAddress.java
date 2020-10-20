package test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liuqiang
 * @since 6/1/20 9:21 AM
 */
public class testAddress {
    public static void main(String[] args) {
        List<String> list;

        list = new ArrayList<String>(); list.add("1");
        System.out.println(list.hashCode());

        list = new ArrayList<String>(); list.add("abc");
        System.out.println(list.hashCode());

        list = new ArrayList<String>();
        System.out.println(list.hashCode());

        list = new ArrayList<String>();
        System.out.println(list.hashCode());
    }
}
