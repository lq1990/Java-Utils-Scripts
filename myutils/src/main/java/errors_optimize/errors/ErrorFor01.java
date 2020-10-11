package errors_optimize.errors;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liuqiang
 * @since 2020-09-26 10:00
 */
public class ErrorFor01 {
    public static void main(String[] args) {
        List<String> list = null;
        list = new ArrayList<>();
        for (String s : list) { // list.size can be 0.
        }
        System.out.println("print over 1");


        list = null;
        for (String s : list) { // but, list can not be null
        }
        System.out.println("print over 2");

    }
}
