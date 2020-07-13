package scripts;

import java.util.Arrays;

/**
 * @author liuqiang
 * @since 7/8/20 3:53 PM
 */
public class S01 {
    public static void main(String[] args) {
        double[] arr1 = new double[5];
        double[] arr2 = new double[5];

        arr1[0] = arr2[1] = 22;

        double a,b;
        a=b=2;

        System.out.println(Arrays.toString(arr1));
        System.out.println(Arrays.toString(arr2));
        System.out.println(a);
        System.out.println(b);
    }
}
