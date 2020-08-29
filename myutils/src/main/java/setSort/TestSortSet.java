package setSort;

import java.util.*;

/**
 * linked hashset: 按照 插入的顺序 维护顺序。
 * treeset 默认升序排列，可以自定义排序
 *
 * @author liuqiang
 * @since 6/10/20 9:53 AM
 */
public class TestSortSet {
    public static void main(String[] args) {
        String[] str = new String[]{"111a", "b22", "gdsga2", "dfadg", "e"};

        Set<String> set = new LinkedHashSet<>(Arrays.asList(str)); // linked hashset 可以维护顺序
        System.out.println(set);

        String[] strDest = set.toArray(new String[0]);
        System.out.println(Arrays.asList(strDest));

    }
}
