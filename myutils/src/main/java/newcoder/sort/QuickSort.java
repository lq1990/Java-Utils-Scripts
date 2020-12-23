package newcoder.sort;

import java.util.Arrays;

/**
 * review sort
 * @author liuqiang
 * @since 2020-12-23 20:06
 */
public class QuickSort {
    public static void main(String[] args) {
        final int[] arr = new int[] {
                4,3,1,2,5,6,10, -1
        };

        sort(arr, 0, arr.length-1);
        System.out.println(Arrays.toString(arr));

    }

    private static void sort(final int[] arr, int left, int right) {
        if (left > right) return;

        int left0 = left;
        int right0 = right;

        int pivot = arr[left];

        while(left < right) {
            while (left < right && right>=0 && arr[right] > pivot) {
                right--;
            }

            // now: arr[right] < pivot
            arr[left] = arr[right];

            while(left < right && left >=0 && arr[left] <= pivot) {
                left++;
            }

            // now: arr[left] > pivot
            arr[right] = arr[left];
        }

        // now: left=right
        arr[left] = pivot;

        // recursive
        sort(arr, left0, left-1);
        sort(arr, left+1, right0);
    }



}
