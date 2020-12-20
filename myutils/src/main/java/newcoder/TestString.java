package newcoder;

import java.util.ArrayList;

/**
 * @author liuqiang
 * @since 2020-12-19 20:21
 */
public class TestString {
    public static void main(String[] args) {
        StringBuilder sb = new StringBuilder();
        sb.append("a");
        sb.append('1');

        String a = "";

        System.out.println(sb.toString());
        System.out.println(sb.length());

        System.out.println(Long.parseLong("123"));

        System.out.println(isPrime(4));
        System.out.println(isPrime(5));
        System.out.println(isPrime(6));

        System.out.println(find(11));
    }

    private static ArrayList<Integer> find(final int num) {
        final ArrayList<Integer> list = new ArrayList<>();

        for(int i=2; i<= num; ++i) {
            if(isPrime(i)) {
                list.add(i);
            }
        }

        return list;
    }

    private static boolean isPrime(final int num) {
        for(int i=2; i<num; ++i) {
            if((double)num / i == num/i) {
                return false;
            }
        }

        return true;
    }
}
