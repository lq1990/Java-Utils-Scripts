package test;

/**
 * @author liuqiang
 * @since 5/22/20 4:35 PM
 */
public class testArrayCompare {
    public static void main(String[] args) {
        System.out.println("test compute");

        double[] doubles1 = new double[] {1,2,3};
        double[] doubles2 = new double[] {2,4,6.0};

        System.out.println(Double.compare(1, 2)); // -1
        System.out.println(Double.compare(1, 1)); // 0
        System.out.println(Double.compare(1, -1)); // 1


    }
}
