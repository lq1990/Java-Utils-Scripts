import java.util.ArrayList;
import java.util.List;

/**
 * @author liuqiang
 * @since 6/4/20 7:30 PM
 */
public class testOBJ {
    public static void main(String[] args) {
        System.out.println(5 & 2); // 101 & 10 = 0
        System.out.println(4 & 2); // 100 & 10 = 0
        System.out.println(3 & 2); // 11 & 10 = 10 = 2
        System.out.println(5 & 1); // 101 & 1 = 1


        System.exit(0);

        List<String> list = new ArrayList<>();
        list.add("aaa");
        System.out.println(list);

        fn(list);

        System.out.println(list);

        System.out.println("===");
        double[] ds =null;
        System.out.println(ds);

    }

    public static void fn(List<String> list) {
        list.add("bbb");
        list.add("ccc");
        list.add("ddd");
    }
}
