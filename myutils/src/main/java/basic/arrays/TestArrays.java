package basic.arrays;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author liuqiang
 * @since 6/10/20 11:39 AM
 */
public class TestArrays {
    public static void main(String[] args) {
        ceshi.fn();

    }

    public static void sort() {
        double[] doubles = {10.1, 2.2, 30, 4};
        Arrays.sort(doubles);

        for (double aDouble : doubles) {
            System.out.println(aDouble);
        }
    }
}


class ceshi {
    public static void fn(){
        int n = 5;
        int[] a = {8, 5, 4, 6, 2, 1, 7, 9, 3};
        HashMap map = new HashMap();
        for (int i = 0; i < a.length; i++) {
            map.put(a[i], i); // 将值和下标存入Map
        }
        // 排列
        List list = new ArrayList();
        Arrays.sort(a); // 升序排列
        for (int i = 0; i < a.length; i++) {
            list.add(a[i]);
        }

        for (Object object : list) {
            System.out.print(object + ",");
        }

        System.out.println();
        // 查找原始下标
        for (int i = 0; i < n; i++) {
            System.out.print(map.get(a[i]) + ",");
        }
    }
}
