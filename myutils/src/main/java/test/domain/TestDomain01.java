package test.domain;

import java.util.Arrays;

/**
 * @author liuqiang
 * @since 7/18/20 12:03 PM
 */
public class TestDomain01 {
    public static void main(String[] args) {
        Double obj = null;

        if (obj == null) {
            System.out.println(obj);
        }


        Double[] ds = new Double[]{0d, null};

        System.out.println(Arrays.toString(ds));
    }
}
