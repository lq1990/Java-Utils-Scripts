package newcoder.dynamicprogramming;

import basic.stringutils.StringUtils;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * @author liuqiang
 * @since 2020-12-20 15:33
 */
public class TestDP2 {
    public static void main(String[] args) {
        final int[] arr = new int[]{
                3, 34, 4, 12, 5, 2
        };

        System.out.println(subset(arr, arr.length - 1, 7));
        System.out.println(subset(arr, arr.length - 1, 1));
        System.out.println(subset(arr, arr.length - 1, 8));
        System.out.println(subset(arr, arr.length - 1, 9));

        System.out.println("===");
        System.out.println(db_subset(arr, 7));
        System.out.println(db_subset(arr, 1));
        System.out.println(db_subset(arr, 8));
        System.out.println(db_subset(arr, 9));
    }


    private static boolean db_subset(final int[] arr, final int targetSum) {
        final int nrows = arr.length;
        final boolean[][] mat = new boolean[nrows][];
        for (int i = 0; i < nrows; ++i) {
            mat[i] = new boolean[targetSum + 1];
        }

        // init mat
        for (int i = 0; i < nrows; ++i) {
            mat[i][0] = true;
        }

        if (arr[0] <= targetSum) {
            mat[0][targetSum] = true;
        }

        for (int row = 1; row < nrows; ++row) {
            for (int col = 1; col <= targetSum; ++col) {
                if (arr[row] > col) {
                    mat[row][col] = mat[row - 1][col];
                }

                if (col - arr[row] >= 0)
                    mat[row][col] = mat[row - 1][col - arr[row]] || mat[row - 1][col];
            }
        }

        return mat[nrows - 1][targetSum];
    }

    // recursive
    private static boolean subset(final int[] arr, final int i, final int targetSum) {
        if (targetSum == 0) {
            return true;
        }

        if (i == 0) {
            return arr[i] == targetSum;
        }

        if (targetSum < 0) {
            return subset(arr, i - 1, targetSum);
        }

        boolean sel = subset(arr, i - 1, targetSum - arr[i]); // select the i-idx num of array
        boolean notsel = subset(arr, i - 1, targetSum);

        return sel || notsel;
    }

}
