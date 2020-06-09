package matrix_vector_lib.sparkmllib;

import org.apache.spark.ml.linalg.DenseMatrix;

/**
 * spark DenseMatrix:
 *   col-major, default isTransposed: false
 *   row-major, if isTransposed: true
 *
 * apply(i,j) 取值 第i行 第j列。0-based index
 *
 * @author liuqiang
 * @since 6/9/20 12:52 PM
 */
public class testSparkDenseMatrix {
    public static void main(String[] args) {
        System.out.println("test spark DenseMatrix");

//        DenseMatrix denseMatrix = new DenseMatrix(3,2, new double[] {1,2,3,4,5,6}, false); // col-major, default
        DenseMatrix denseMatrix = new DenseMatrix(3,2, new double[] {1,2,3,4,5,6}, true); // row-major

        System.out.println(denseMatrix);

        System.out.println("===");

        for (int i=0; i<3; ++i) {
            for (int j=0; j<2; ++j) {
                System.out.println(i+","+j+ ": " + denseMatrix.apply(i,j));
            }
        }
    }
}
