package newcoder.dynamicprogramming;

import java.util.Arrays;

/**
 *
 * select nums that are not neighbors, whose sum are the biggest.
 *
 * @author liuqiang
 * @since 2020-12-20 14:44
 */
public class TestDP1 {
    public static void main(String[] args) {
        final int[] arr =  new int[]{
                4,1,1,9,1
//                1,2,4,1,7,8,3
        };


        for (int i=0; i<arr.length; ++i) {
            System.out.println(optimize(i, arr));
        }

        System.out.println(Arrays.toString(opt(arr)));
    }

    // good way
    private static int[] opt(final int[] arr) {
        int[] out = new int[arr.length];

        out[0] = arr[0];
        out[1] = Math.max(arr[0], arr[1]);

        for (int i=2; i<arr.length; ++i) {
            out[i] = Math.max(out[i-2] + arr[i], out[i-1]);
        }

        return out;
    }

    /**
     * recursive way.
     *
     * shortcome: overlap of sub-problem
     *
     * @param i
     * @param arr
     * @return
     */
    private static int optimize(final int i, final int[] arr) {
        if (i==0) {
            return arr[0];
        }
        if (i==1) {
            return Math.max(arr[0], arr[1]);
        }

        int sel = optimize(i-2, arr) + arr[i];
        int notsel = optimize(i-1, arr);

        return Math.max(sel, notsel);
    }
}
