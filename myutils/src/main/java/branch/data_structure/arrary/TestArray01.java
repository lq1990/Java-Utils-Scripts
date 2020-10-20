package branch.data_structure.arrary;

import java.util.Arrays;

/**
 * @author liuqiang
 * @since 7/6/20 1:32 PM
 */
public class TestArray01 {
    public static void main(String[] args) {
        double[] arr = new double[5];

        System.out.println(Arrays.toString(arr));
        fn(arr);
        System.out.println(Arrays.toString(arr));

        arr = null;
        System.out.println(arr == null);
    }

    public static void fn(double[] arr) {
        arr[0] = 111;
    }
}
