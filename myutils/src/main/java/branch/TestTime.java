package branch;

/**
 * @author liuqiang
 * @since 6/13/20 5:07 PM
 */
public class TestTime {
    public static void main(String[] args) {
        long loops = 1000000000L;
        boolean trans = true;

        double sum = 0d;
        long begin1 = System.currentTimeMillis();
        for (long i=0; i<loops; ++i) {
            if (!trans) {
            } else {
                sum += i;
            }
        }
        System.out.println("has branch in else: "+(System.currentTimeMillis() - begin1));

        sum = 0d;
        long begin111 = System.currentTimeMillis();
        for (long i=0; i<loops; ++i) {
            sum += trans ? i : 0d;
        }
        System.out.println("has branch ? :: "+(System.currentTimeMillis() - begin111));

        sum = 0d;
        long begin11 = System.currentTimeMillis();
        for (long i=0; i<loops; ++i) {
            if (trans) {
                sum += i;
            } else {

            }
        }
        System.out.println("has branch in if: "+(System.currentTimeMillis() - begin11));

        sum = 0;
        long begin2 = System.currentTimeMillis();
        for (long i=0; i<loops; ++i) {
            sum += i;
        }
        System.out.println("no branch: "+(System.currentTimeMillis() - begin2));

    }
}
