package arrays;

import com.exceeddata.ac.common.data.typedata.TypeData;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author liuqiang
 * @since 7/17/20 3:48 PM
 */
public class TestArray02Constructor {

    public static void main(String[] args) {

        Double[] arr = new Double[3];
        arr[0]=99d;
        arr[1]=88d;
        arr[2]=77d;
        int len = arr.length;

        final ArrayList<Double> list = new ArrayList<Double>(Arrays.asList(arr).subList(2, len));

        System.out.println(list);

    }
}
