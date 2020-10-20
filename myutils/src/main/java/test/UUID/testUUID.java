package test.UUID;

import java.util.UUID;

/**
 * @author liuqiang
 * @since 6/16/20 1:30 PM
 */
public class testUUID {
    public static void main(String[] args) {
        for (int i = 0; i < 10; ++i) {
            System.out.println(UUID.randomUUID().toString());
        }
    }


}

