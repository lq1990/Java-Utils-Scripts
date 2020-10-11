package errors_optimize.errors;

/**
 * @author liuqiang
 * @since 2020-09-26 10:03
 */
public class ErrorField01 {
    public static void main(String[] args) {
        Person p = new Person();
        System.out.println(p.name);
        System.out.println("print over 1");

        p = null;
        System.out.println(p.name); // must check null before
        System.out.println("print over 2");

    }
}

class Person {
    String name;
    String home;
}
