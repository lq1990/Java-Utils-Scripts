package matrix_vector_lib.ejml;

import org.ejml.data.Complex_F64;
import org.ejml.data.DMatrixRMaj;
import org.ejml.dense.row.factory.DecompositionFactory_DDRM;
import org.ejml.interfaces.decomposition.EigenDecomposition_F64;
import org.ejml.simple.SimpleEVD;
import org.ejml.simple.SimpleMatrix;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author liuqiang
 * @since 6/10/20 10:44 AM
 */
public class TestEJMLSimpleMatrix {
    public static void main(String[] args) {
        eig();

    }

    public static void plusMinusMultInvert() {
        SimpleMatrix H = new SimpleMatrix(3, 2, true, new double[]{1, 2, 3, 4, 5, 6});
        SimpleMatrix P = new SimpleMatrix(2, 1, true, new double[]{1, 1});
        SimpleMatrix R = new SimpleMatrix(3, 1, true, new double[]{1.1, 1.1, 1.1});
        System.out.println("H: \n" + H);
        System.out.println("P: \n" + P);
        System.out.println("R: \n" + R);


        // plut
        SimpleMatrix plus = H.mult(P).plus(R);
        System.out.println("H*P + R: \n" + plus);

        // minus
        SimpleMatrix minus = H.mult(P).minus(R);
        System.out.println("H*P - R: \n" + minus);

        // mult
        SimpleMatrix mult = H.mult(P);
        System.out.println("H*P: \n" + mult);

        // mult elemwise
        SimpleMatrix scale = H.scale(0.3);
        System.out.println("0.3 * H: \n" + scale);

        SimpleMatrix elementMult = H.elementMult(H);
        System.out.println("H .* H: \n" + elementMult);

        // div
        SimpleMatrix divide = H.divide(2);
        System.out.println("H / 2: \n" + divide);

        SimpleMatrix elementDiv = H.elementDiv(H);
        System.out.println("H ./ H : \n" + elementDiv);

        // invert
        SimpleMatrix A = new SimpleMatrix(2, 2, true, new double[]{1, 2, 3, 4});
        System.out.println("A.invert(): \n" + A.invert());

    }

    public static void index() {
        SimpleMatrix matrix = new SimpleMatrix(2, 3, true, new double[]{1.1, 2.2, 3.3, 4.4, 5.5, 6.6});
        System.out.println(matrix);

        double v00 = matrix.get(0, 0);
        double v01 = matrix.get(0, 1);
        double v02 = matrix.get(0, 2);
        double v12 = matrix.get(1, 2);

        System.out.println(v00 + ", " + v01 + ", " + v02 + ", " + v12);

    }

    public static void eig() {
        final int nrows = 100;
        final int ncols = 100;
        double[] vals = new double[nrows * ncols];
        for (int i=0; i<ncols * nrows; ++i) {
            vals[i] = i;
        }
        SimpleMatrix matrix = new SimpleMatrix(nrows, ncols, true, vals);
        SimpleEVD<SimpleMatrix> eig = matrix.eig(); // 需要添加到 exd.DenseMatrix

        List<Complex_F64> eigenvalues = eig.getEigenvalues();

//        System.out.println("eigenvalues: ");
//        for (Complex_F64 ew : eigenvalues) {
//            System.out.println(ew.getReal());
//            System.out.println(ew.getImaginary()); // 0
//        }

        // array eigenvals
        double[] eigenvals = new double[eigenvalues.size()];
        Map<Double, Integer> valIndx = new HashMap<>();
        for (int i = 0; i < eigenvals.length; ++i) {
            final double real = eigenvalues.get(i).getReal();
            eigenvals[i] = real;
            valIndx.put(real, i);
        }

        System.out.println("eigenvals:");
        for (double eigenval : eigenvals) {
            System.out.print(eigenval+", ");
        }
        System.out.println();

        Arrays.sort(eigenvals); // asc sort
        System.out.println("after sort: ");
        for (double eigenval : eigenvals) {
            System.out.print(eigenval+", ");
        }
        System.out.println();


        for (int i=0; i<eigenvals.length; ++i) {
            System.out.println("val: "+eigenvals[i] + ", original index: "+valIndx.get(eigenvals[i]));

        }

        System.out.println("num of eigenvalues :"+eig.getNumberOfEigenvalues());
        // V
        for (int i = 0; i < eig.getNumberOfEigenvalues(); ++i) {
            System.out.println("eigenvector " + i);
            SimpleMatrix eigenVector = eig.getEigenVector(i);
            System.out.println(eigenVector);
        }

        System.exit(0);

        System.out.println("===\nthe first n PCs: \n");

        // get the first nPCs
        final int K = 3;

        final int size = eigenvals.length;
        SimpleMatrix eigenVectors = eig.getEigenVector(valIndx.get(eigenvals[size - 1]));
        for (int i=1; i<K; ++i) {
            eigenVectors = eigenVectors.concatColumns(eig.getEigenVector(valIndx.get(eigenvals[size - i - 1])));
        }
        System.out.println(eigenVectors);

    }

    public static void fn1() {
        EigenDecomposition_F64<DMatrixRMaj> eig = DecompositionFactory_DDRM.eig(true);
        Complex_F64 eigenvalue = eig.getEigenvalue(0);
        System.out.println(eigenvalue.getReal());

    }

    public static void fn2() {

    }

}
