package newcoder;

import java.util.*;

/**
 * @author liuqiang
 * @since 2020-12-19 09:26
 */
public class TestRandom {
    public static void main(String[] args) {
        final Scanner sc = new Scanner(System.in);

        // store unique values
        ArrayList<Integer> list = null;

        while (sc.hasNextLine()) {
            String nextStr = sc.nextLine();
            if (nextStr.isEmpty()) break;

            HashSet<Integer> set = new HashSet<>();
            for (int i = 0; i < Integer.parseInt(nextStr); ++i) {
                set.add(Integer.parseInt(sc.nextLine()));
            }

            list = new ArrayList<>(set);
            Collections.sort(list); // sort in asc order
            for (int i = 0; i < list.size(); ++i) {
                System.out.println(list.get(i));
            }
        }

    }
}
