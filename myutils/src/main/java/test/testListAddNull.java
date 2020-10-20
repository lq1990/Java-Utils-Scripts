package test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author liuqiang
 * @since 6/5/20 7:50 PM
 */
public class testListAddNull {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        System.out.println(list);
        System.out.println(list.size());

        list.add(null);


        System.out.println(list);
        System.out.println(list.size());
    }
}
