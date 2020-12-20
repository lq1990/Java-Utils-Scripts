package newcoder.dynamicprogramming;

import java.util.Arrays;

/**
 * @author liuqiang
 * @since 2020-12-20 17:22
 */
public class TestBag01 {
    public static void main(String[] args) {
        int[] spaces = new int[]{0, 2, 3, 4, 5};
        int[] values = new int[]{0, 3, 4, 5, 6};
        int spaceTotal = 8;

        // in theory, need to sort according to desc value/space

//        get(spaces, values, spaceTotal);
        System.out.println(f(4, spaceTotal, spaces, values));

        System.out.println("bag:");
        int[][] bag = dp_bag(spaces, values, spaceTotal);
        for (int[] ints : bag) {
            System.out.println(Arrays.toString(ints));
        }
    }


    private static int[][] dp_bag(int[] spaces, int[] values, int spaceTotal) {
        // init f[][]
        int[][] f = new int[5][9];

        for (int k = 1; k <= 4; ++k) {
            for (int w = 1; w <= 8; ++w) {
                if (w >= spaces[k]) {
                    f[k][w] = Math.max(f[k - 1][w], f[k - 1][w - spaces[k]] + values[k]);
                }
            }
        }

        return f;
    }

    // recursive
    private static int f(int k, int w, int[] weights, int[] values) {
        if (k == 0 || w == 0) return 0;

        if (weights[k - 1] > w) return f(k - 1, w, weights, values);

        int A = f(k - 1, w - weights[k - 1], weights, values) + values[k - 1];
        int B = f(k - 1, w, weights, values);
        return Math.max(A, B);

    }

}
