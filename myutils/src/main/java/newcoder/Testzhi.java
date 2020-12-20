package newcoder;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author liuqiang
 * @since 2020-12-19 21:30
 */
public class Testzhi {
    static ArrayList<Long> primes = null;
    static long maxVal = -Long.MAX_VALUE;

    public static void main(String[] args) {
        final Scanner sc = new Scanner(System.in);
        String line;

        while(sc.hasNextLine()) {
            line = sc.nextLine();
            long num = Long.parseLong(line);
            if(maxVal < num) {
                maxVal = num;
                primes = find(num);
            }

            for(int i=0; i<primes.size();) {
                long p = primes.get(i);
                if((double)num / p == num/p) {
                    num /= p;
                    i=0; // reset i
                    System.out.print(p+" ");
                    if (num == 1) break;
                } else {
                    ++i;
                }
            }

        }
    }

    private static ArrayList<Long> find(final long num) {
        final ArrayList<Long> list = new ArrayList<>();

        if(isPrime(num)) {
            list.add(num);
            return list;
        }

        for(long i=2; i<= num; ++i) {
            if(isPrime(i)) {
                list.add(i);
            }
        }

        return list;
    }

    private static boolean isPrime(final long num) {
        for(long i=2; i<num; ++i) {
            if((double)num / i == num/i) {
                return false;
            }
        }

        return true;
    }
}
