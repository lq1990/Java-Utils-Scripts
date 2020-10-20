package test.testFor;

import org.apache.spark.mllib.linalg.DenseVector;

/**
 * @author liuqiang
 * @since 7/15/20 1:29 PM
 */
public class TestFor01 {
    public static void main(String[] args) {
        System.out.println("test for");
        DenseVector arr = new DenseVector(new double[5]);

        for (double v : arr.toArray()) { // toArray() 只进行了一次
            System.out.println(v);
        }
    }
}
