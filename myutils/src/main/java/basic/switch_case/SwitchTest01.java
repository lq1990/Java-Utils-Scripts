package basic.switch_case;

/**
 * @author liuqiang
 * @since 2020-10-16 16:42
 */
public class SwitchTest01 {
    public static void main(String[] args) {

        String name = "abc";

        switch (name) {
            case "anna":
                break;
            case "beta":
                break;
            default:
                fn1(name);
        }

    }

    public static void fn1(String name) {
        System.out.println("fn1 "+name);
    }
}
