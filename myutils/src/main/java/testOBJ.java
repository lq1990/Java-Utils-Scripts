import java.util.ArrayList;
import java.util.List;

/**
 * @author liuqiang
 * @since 6/4/20 7:30 PM
 */
public class testOBJ {
    public static void main(String[] args) {
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
