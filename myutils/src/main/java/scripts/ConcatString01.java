package scripts;

/**
 * @author liuqiang
 * @since 2020-09-29 15:24
 */
public class ConcatString01 {
    public static void main(String[] args) {
        final int num = 1000;

        System.out.print("id,");
        for (int i=1; i<=num; ++i) {
            System.out.print("f"+i+",");
        }
        System.out.print("target");

        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();


        System.out.print("100, ");
        for (int i=1; i<=num; ++i) {
            System.out.print((i+Math.random()+"").substring(0, 4)+",");
        }
        System.out.print("2");

        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();


        // f with def
        System.out.print("id int,");
        for (int i=1; i<=num; ++i) {
            System.out.print("f"+i+" double"+",");
        }
        System.out.print("target double");
        System.out.println();
    }
}
