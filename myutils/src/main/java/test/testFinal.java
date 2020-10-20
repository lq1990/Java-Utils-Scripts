package test;

/**
 * @author liuqiang
 * @since 6/3/20 7:51 PM
 */
public class testFinal {
    public static void main(String[] args) {
        System.out.println("test final");

        int i; // 只能赋值一次
        i = 10;
        System.out.println(i);

        i = 100;
        System.out.println(i);
    }
}
