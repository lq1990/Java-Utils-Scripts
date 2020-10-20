package hadoop_mr_spark.spark;

import com.exceeddata.ac.common.exception.EngineException;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.mllib.linalg.*;
import org.apache.spark.mllib.linalg.distributed.RowMatrix;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author liuqiang
 * @since 7/15/20 1:59 PM
 */
public class TestEXD01 {

    public static void main(String[] args) throws EngineException {
        SparkConf sparkConf = new SparkConf().setMaster("local").setAppName("test");
        JavaSparkContext sc = new JavaSparkContext(sparkConf);
        sc.setLogLevel("WARN");

        testComputeInv(sc);

    }

    public static void testPlus() {
        DenseMatrix matrix = new DenseMatrix(3, 2, new double[]{1, 2, 3, 4, 5, 6}, true);
        DenseMatrix other = new DenseMatrix(3, 2, new double[]{1, 1, 2, 2, 3, 4}, true);

        /*
        1  2     1  1      2  3
        3  4  +  2  2  =   5  6
        5  6     3  4      8  10

        1  2     1  1      0  1
        3  4  -  2  2  =   1  2
        5  6     3  4      2  2
         */

        DenseMatrix res = minus(matrix, other);
        System.out.println(Arrays.toString(res.toArray()));


    }

    public static void testComputeInv(JavaSparkContext sc) {
        List<Vector> list = new ArrayList<>();
        list.add(new DenseVector(new double[]{1,2, 3}));
        list.add(new DenseVector(new double[]{3,4, 4}));
        list.add(new DenseVector(new double[]{5,6, 7}));
        JavaRDD<Vector> vectorJavaRDD = sc.parallelize(list);

        DenseMatrix denseMatrix = computeInv(new RowMatrix(vectorJavaRDD.rdd()));
        System.out.println(Arrays.toString(denseMatrix.toArray()));
    }

    /**
     * pass
     * @param X
     * @return
     */
    private static DenseMatrix computeInv(final RowMatrix X) {
        final int nCoef = (int) X.numCols();
        final SingularValueDecomposition<RowMatrix, Matrix> svd = X.computeSVD(nCoef, true, Math.pow(10, -9));

        // create the inv diag matrix from S
        final double[] s0 = svd.s().toArray();
        final int lenS0 = s0.length;
        final double[] sigma = new double[lenS0];
        for (int i = 0; i < lenS0; ++i) {
            sigma[i] = Math.pow(s0[i], -1);
        }
        final DenseMatrix invS = DenseMatrix.diag(new DenseVector(sigma));

        // U can not be a RowMatrix
        final RowMatrix u = svd.U();
        final List<Vector> uListVector = u.rows().toJavaRDD().collect();
        final int nRows = (int) u.numRows();
        final int nCols = (int) u.numCols();
        final double[] Uarray = new double[nRows * nCols];
        for (int i = 0, len = uListVector.size(); i < len; ++i) {
            System.arraycopy(uListVector.get(i).toArray(), 0, Uarray, i * len, len);
        }

        final DenseMatrix U = new DenseMatrix(nRows, nCols, Uarray); // col major
        final Matrix V = svd.V();

        return V.multiply(invS).multiply(U);
    }

    private static DenseMatrix plus(final DenseMatrix matrix, final DenseMatrix other) {
        System.out.println("matrix: "+Arrays.toString(matrix.toArray()));
        System.out.println("other: "+Arrays.toString(other.toArray()));

        final double[] array = matrix.toArray(); // colmajor toarray
        final double[] oArray = other.toArray();

        final int len = array.length;
        final double[] res = new double[len];

        for (int i = 0; i < len; ++i) {
            res[i] = array[i] + oArray[i];
        }
        System.out.println("res: "+Arrays.toString(res));

        return new DenseMatrix(matrix.numRows(), matrix.numCols(), res);
    }

    private static DenseVector plus(final DenseVector vector, final DenseVector other) {
        final double[] array = vector.toArray();
        final double[] oArray = other.toArray();

        final int len = array.length;
        final double[] res = new double[len];

        for (int i = 0; i < len; ++i) {
            res[i] = array[i] + oArray[i];
        }

        return new DenseVector(res);
    }

    private static DenseMatrix minus(final DenseMatrix matrix, final DenseMatrix other) {
        final double[] array = matrix.toArray();
        final double[] oArray = other.toArray();

        final int len = array.length;
        final double[] res = new double[len];

        for (int i = 0; i < len; ++i) {
            res[i] = array[i] - oArray[i];
        }

        return new DenseMatrix(matrix.numRows(), matrix.numCols(), res);
    }

    private static DenseVector minus(final DenseVector vector, final DenseVector other) {
        final double[] array = vector.toArray();
        final double[] oArray = other.toArray();

        final int len = array.length;
        final double[] res = new double[len];

        for (int i = 0; i < len; ++i) {
            res[i] = array[i] - oArray[i];
        }

        return new DenseVector(res);
    }

}
