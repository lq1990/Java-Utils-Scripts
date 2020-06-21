package math.power;

import java.util.Arrays;

/**
 * @author liuqiang
 * @since 6/13/20 1:56 PM
 */
public class TestPower {
    public static void main(String[] args) {

        double pow = Math.pow(4, 2);
        System.out.println(pow);

        double exp = Math.exp(1);
        System.out.println(exp);

        double log = Math.log10(0);
        System.out.println(log);

        Double[] res = new Double[3];
        for (int i=0, val=-1; i<2; ++i,++val) {
            res[i] = Math.log(val);
        }

        System.out.println(Arrays.toString(res));

        Double re = res[1];
        System.out.println(re.isInfinite());

        System.out.println(Math.sqrt(-1));
        System.out.println(Math.sqrt(0));

    }
}
