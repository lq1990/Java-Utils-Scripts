package basic.pointer;

import java.util.*;

/**
 * @author liuqiang
 * @since 2020-10-31 15:42
 */
public class TestP01 {
    public static void main(String[] args) {
        final List<Map<String, Object>> res = new ArrayList<>();
        Map<String, Object> map = null;
        // 1.1
        for (int i = 0; i < 5; ++i) {
            map = new HashMap<>();
            map.put("projectId", ""+i);
            res.add(map);
        }

        System.out.println(res);
    }
}
