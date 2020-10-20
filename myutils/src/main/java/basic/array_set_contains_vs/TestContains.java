package basic.array_set_contains_vs;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * basic.list.contains() 很耗时，因为会loop
 * 替代方案，使用set
 *
 *
 * @author liuqiang
 * @since 6/9/20 2:45 PM
 */
public class TestContains {
    public static void main(String[] args) {
        System.out.println("test contains");

        long timeList = testListContains();
        long timeSet = testSetContains();

        System.out.println("basic.list: "+timeList);
        System.out.println("basic.set: "+ timeSet);

        System.out.println("basic/list/basic.set: " +(timeList / timeSet));

    }

    private static long testListContains() {
        long begin = System.currentTimeMillis();

        List<String> list = new ArrayList<>();
        for (int i=0; i<10000; ++i) {
            list.add("test"+i);
        }

        // contains
        for (int i=0; i<30000; ++i) {
            list.contains("test"+i);
        }

        long end = System.currentTimeMillis();
        return (end - begin);
    }

    private static long testSetContains() {
        long begin = System.currentTimeMillis();

        Set<String> set = new HashSet<>();
        for (int i=0; i<10000; ++i) {
            set.add("test"+i);
        }

        // contains
        for (int i=0; i<30000; ++i) {
            set.contains("test"+i);
        }

        long end = System.currentTimeMillis();
        return (end - begin);
    }

}
