package matrix_vector_lib.ojalgo;

import org.ojalgo.OjAlgoUtils;
import org.ojalgo.array.Array1D;
import org.ojalgo.array.Primitive64Array;
import org.ojalgo.matrix.decomposition.Eigenvalue;
import org.ojalgo.matrix.store.MatrixStore;
import org.ojalgo.matrix.store.Primitive64Store;
import org.ojalgo.netio.BasicLogger;
import org.ojalgo.scalar.ComplexNumber;

import java.util.Arrays;


/**
 * ojalgo is col-major
 *
 * @author liuqiang
 * @since 6/11/20 11:31 AM
 */
public class TestOjalgo01 {
    public static void main(String[] args) {

        testPrimitive64Array();

    }

    public static void testPrimitive64Array() {
        Primitive64Array wrap = Primitive64Array.wrap(new double[]{1, 2, 3, 4, 5, 6});

        System.out.println(wrap);
    }

    public static void multiply() {
        Primitive64Store mat = Primitive64Store.FACTORY.rows(new double[]{1, 2, 3}, new double[]{4, 5, 6});
        System.out.println(mat);

        Primitive64Store mat1 = Primitive64Store.FACTORY.rows(new double[]{1}, new double[]{1}, new double[]{1});

        System.out.println(mat.multiply(mat1));

    }

    public static void myCreate() {
        // rows
        Primitive64Store rows = Primitive64Store.FACTORY.rows(new double[]{1, 2, 3}, new double[]{4,5,6});
        System.out.println(rows);

        /*
        1  2  3
        4  5  6
         */

        double[] data = rows.data;
        System.out.println(Arrays.toString(data)); // col-major

        // cols.
        Primitive64Store columns = Primitive64Store.FACTORY.columns(new double[]{1, 2}, new double[]{3, 4}, new double[]{5, 6});
        System.out.println(columns);
        /*
        1  3  5
        2  4  6
         */
        System.out.println(Arrays.toString(columns.data)); // col-major

        System.out.println(columns.get(0, 0)); // 1
        System.out.println(columns.get(1, 0)); // 2


        // make
        Primitive64Store matrix = Primitive64Store.FACTORY.make(3, 2);

        System.out.println("wrap: ");

        // wrap
        Primitive64Store wrap = Primitive64Store.wrap(new double[]{1, 2, 3, 4, 5, 6}, 2); // rows = 3, col-major
        System.out.println(wrap);


    }

    public static void eig0() {
        Primitive64Store matrix = Primitive64Store.FACTORY.rows(new double[]{1d, 2, 3}, new double[]{4, 5, 6});
        System.out.println(matrix);
        System.out.println(matrix.transpose());

        MatrixStore<Double> multiply = matrix.transpose().multiply(matrix);
        System.out.println(multiply);

        // V, D
        Eigenvalue.Generalised<Double> generalised = Eigenvalue.PRIMITIVE.makeGeneralised(multiply);
        generalised.prepare(multiply);

//        generalised.computeValuesOnly(multiply); // only compute eigen values
        generalised.decompose(multiply);// compute eigenvalues / eigenvectors

        MatrixStore<Double> eigenvectors = generalised.getV();
        MatrixStore<Double> eigenvalues = generalised.getD();

        System.out.println("===\neigen vectors:");
        System.out.println(eigenvectors);

        System.out.println("---\neigen values:");
        System.out.println(eigenvalues);

        System.out.println("=======================");
        // to double[]
        int size = (int) eigenvalues.countColumns();

        double[] D = new double[size];
        for(int i=0; i<size; ++i) {
            D[i] = eigenvalues.get(i, i);
        }
        System.out.println(Arrays.toString(D));

        double[] V = eigenvectors.toRawCopy1D(); // col-major
        System.out.println(Arrays.toString(V));

    }

    public static void eig1() {
        Primitive64Store matrix = Primitive64Store.FACTORY.rows(new double[]{1d, 2, 3}, new double[]{4d, 5, 6}, new double[]{7d,8,9});
        System.out.println(matrix);
        System.out.println(matrix.transpose());

//        MatrixStore<Double> multiply = matrix.transpose().multiply(matrix);
        MatrixStore<Double> multiply = matrix;
        System.out.println(multiply);

        // V, D
        Eigenvalue.Generalised<Double> generalised = Eigenvalue.PRIMITIVE.makeGeneralised(multiply); // ??? not this fn
        generalised.prepare(multiply);

//        generalised.computeValuesOnly(multiply); // only compute eigen values
        generalised.decompose(multiply);// compute eigenvalues / eigenvectors

        MatrixStore<Double> eigenvectors = generalised.getV();
        MatrixStore<Double> eigenvalues = generalised.getD();

        System.out.println("===\neigen vectors:");
        System.out.println(eigenvectors);

        System.out.println("---\neigen values:");
        System.out.println(eigenvalues);

        System.out.println("=======================");
        // to double[]
        int size = (int) eigenvalues.countColumns();

        double[] D = new double[size];
        for(int i=0; i<size; ++i) {
            D[i] = eigenvalues.get(i, i);
        }
        System.out.println(Arrays.toString(D));

        double[][] V = eigenvectors.transpose().toRawCopy2D();
        System.out.println(Arrays.deepToString(V));

    }

    public static void eig11() {
        Primitive64Store matrix = Primitive64Store.FACTORY.rows(new double[]{1d, 2, 3}, new double[]{4d, 5, 6}, new double[]{7d,8,9});

        Eigenvalue<Double> make = Eigenvalue.PRIMITIVE.make(matrix);
        make.decompose(matrix);


        System.out.println(make.getEigenvalues());
        System.out.println(make.getEigenvectors());

        System.out.println(make.getD());
        System.out.println(make.getV());

    }

    public static void eig() {


        Primitive64Store matrix = Primitive64Store.FACTORY.rows(new double[] {1,2,3,4,5,6});


        matrix.set(0, 0, 99d);

        System.out.println(matrix);

    }

    public static void fn01() {
        BasicLogger.debug();
        BasicLogger.debug(OjAlgoUtils.getTitle());
        BasicLogger.debug(OjAlgoUtils.getDate());
        BasicLogger.debug();

        int dim = 5;

        /*
         * Create 2 random Symmetric Positive Definite matrices
         */
        Primitive64Store mtrxA = Primitive64Store.FACTORY.makeSPD(dim);
        Primitive64Store mtrxB = Primitive64Store.FACTORY.makeSPD(dim);

        /*
         * There are several generalisations. 3 are supported by ojAlgo, specified by the enum:
         * Eigenvalue.Generalisation This factory method returns the most common alternative.
         */
        Eigenvalue.Generalised<Double> generalisedEvD = Eigenvalue.PRIMITIVE.makeGeneralised(mtrxA);
        // Generalisation: [A][V] = [B][V][D]

        // Use 2-args alternative

        generalisedEvD.computeValuesOnly(mtrxA, mtrxB);
        generalisedEvD.decompose(mtrxA, mtrxB); // Use one of these methods, not both

        // Alternatively, prepare and then use the usual 1-arg methods

        generalisedEvD.prepare(mtrxB);

        generalisedEvD.computeValuesOnly(mtrxA); // either
        generalisedEvD.decompose(mtrxA); // or
        // Can be called repeatedly with each "preparation"
        // Useful if the B matrix doesn't change but A does

        MatrixStore<Double> mtrxV = generalisedEvD.getV();
        // Eigenvectors in the columns

        MatrixStore<Double> mtrxD = generalisedEvD.getD();
        // Eigenvalues on the diagonal

        /*
         * Reconstruct the left and right hand sides of the eigenproblem
         */
        MatrixStore<Double> left = mtrxA.multiply(mtrxV);
        MatrixStore<Double> right = mtrxB.multiply(mtrxV).multiply(mtrxD);

        BasicLogger.debug();
        BasicLogger.debug("Generalised Eigenproblem [A][V] = [B][V][D]");
        BasicLogger.debug("[A]", mtrxA);
        BasicLogger.debug("[B]", mtrxB);
        BasicLogger.debug("Eigenvectors - [V]", mtrxV);
        BasicLogger.debug("Eigenvalues - [D]", mtrxD);
        BasicLogger.debug("Left - [A][V]", left);
        BasicLogger.debug("Right - [B][V][D]", right);


    }

}
