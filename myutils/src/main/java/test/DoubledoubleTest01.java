package test;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;

/**
 * @author liuqiang
 * @since 2020/5/9 17:48
 */
public class DoubledoubleTest01 {
    public static void main(String[] args) {
        double[] dbs = new double[3];
        Double[] Dbs = new Double[3];

        dbs[0] = 10;
        dbs[1] = 11;
        dbs[2] = 12;


        double d0 = 1.234567;
        DecimalFormat df = new DecimalFormat("#.000");
        System.out.println(df.format(d0));


    }
}
