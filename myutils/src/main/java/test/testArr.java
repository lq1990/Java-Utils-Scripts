package test;

import java.util.Arrays;

/**
 * @author liuqiang
 * @since 6/5/20 12:55 PM
 */
public class testArr {
    public static void main(String[] args) {
        double[] ds = new double[5];

        for (double d : ds) {
            System.out.println(d);
        }

        System.out.println();

        ds[0] = 99;
        for (double d : ds) {
            System.out.println(d);
        }

        System.out.println();
        ds = new double[5];
        for (double d : ds) {
            System.out.println(d);
        }

    }
}
