package arrays;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author liuqiang
 * @since 6/10/20 12:40 PM
 */
public class Sort {
    public static void main(String[] args) {
        double[] doubles = new double[] {11,2,3,44,5};

        List<Double> doubles1 = Arrays.asList(11d,2d,33d,4d);

        Collections.reverse(doubles1);
        System.out.println(doubles1); // error

    }
}
