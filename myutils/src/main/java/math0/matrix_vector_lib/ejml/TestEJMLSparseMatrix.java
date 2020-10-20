package math0.matrix_vector_lib.ejml;

import org.ejml.data.*;
import org.ejml.dense.row.CommonOps_DDRM;
import org.ejml.dense.row.NormOps_DDRM;
import org.ejml.ops.ConvertDMatrixStruct;
import org.ejml.simple.ops.SimpleOperations_DSCC;
import org.ejml.sparse.csc.CommonOps_DSCC;
import org.ejml.sparse.csc.NormOps_DSCC;
import org.ejml.sparse.csc.RandomMatrices_DSCC;

import java.util.Arrays;
import java.util.Random;

/**
 * sparse matrix
 *
 * @author liuqiang
 * @since 6/12/20 3:22 PM
 */
public class TestEJMLSparseMatrix {
    public static int ROWS = 3;
    public static int COLS = 2;
    public static int XCOLS = 1;

    public static void main(String[] args) {
        testSet0();
    }

    public static void testInv() {
        SimpleOperations_DSCC dscc = new SimpleOperations_DSCC();
        DMatrixSparseCSC csc = new DMatrixSparseCSC(2, 2);
        csc.set(0,0,1.1);
        csc.set(0,1,1.1);
        csc.set(1,1,2);
        DMatrixSparseCSC res = new DMatrixSparseCSC(2,2);
        dscc.invert(csc, res);
        res.print();
    }

    public static void testNoArrayLen() {
        DMatrixSparseCSC csc = new DMatrixSparseCSC(3, 3, 0);
        csc.set(0,0,1);
        csc.set(1,0,11);
        csc.set(1,0,11);
        csc.set(1,0,12);
        csc.set(1,1,12);
        csc.set(1,1,0);
        csc.print();

        System.out.println(Arrays.toString(csc.nz_values));
        csc.printNonZero();
        System.out.println(csc.getNonZeroLength());

    }

    public static void testPermutationMatrix() {
        // vector => matrix ???
        DMatrixSparseCSC csc = CommonOps_DSCC.permutationMatrix(new int[]{1, 2, 3, 4, 5, 6}, false, 6, null);
        csc.print();

        // matrix => vector

    }

    public static void testSet0() {
        DMatrixSparseCSC sparseCSC = new DMatrixSparseCSC(3, 2, 6);
        sparseCSC.set(0,1 ,10);
        sparseCSC.print();
        System.out.println(Arrays.toString(sparseCSC.nz_values)); // nz_values also contains 0
        System.out.println(sparseCSC.nz_length);

        sparseCSC.set(0,0,0);
        sparseCSC.print();
        System.out.println(Arrays.toString(sparseCSC.nz_values));
        System.out.println(sparseCSC.nz_length);


    }

    public static void testCSC2CSR() {
        // test DMatrixSparseCSC
        DMatrixSparseCSC sparseCSC = new DMatrixSparseCSC(3,4,12);
        sparseCSC.set(0, 1, 1.1);
        sparseCSC.set(1,2, 2.2);
        sparseCSC.print();

        sparseCSC.remove(1,2);
        sparseCSC.print();

        sparseCSC.set(0, 1, 11);
        sparseCSC.print();

        System.out.println(sparseCSC.get(0, 1));

        System.out.println("===");
        // create using colPtrs, rowIndex
        double[] values = new double[]{11,23,13, 19, 27, 17};
        int[] row_index = new int[]{0,2,0,1,2,0};
        int[] col_ptr = new int[]{0,1,2,5,6};

        DMatrixSparseCSC csc = new DMatrixSparseCSC(3, 4, 12);
        csc.nz_values = values;
        csc.col_idx = col_ptr;
        csc.nz_rows = row_index;

        csc.print();
        System.out.println(Arrays.toString(csc.nz_values)); // colmajor

        System.out.println("to rowMajor: ");
        // to rowMajor
        DMatrixSparseCSC transpose = CommonOps_DSCC.transpose(csc, null, null);
        transpose.print();
        transpose.printNonZero();
        System.out.println(Arrays.toString(transpose.nz_rows)); // => col_index
        System.out.println(Arrays.toString(transpose.col_idx)); // => row_ptr

        System.out.println("==========================================");
        // CSR。当传入的数据是有CSR模式下的。需要转置。
        int[] rowptrs = new int[]{0,3,4,6};
        int[] col_index = new int[]{0,2,3,2,1,2};
        double[] data = new double[]{11,13,17,19,23,27};
        // => CSC
        DMatrixSparseCSC csc1 = new DMatrixSparseCSC(4,3,12);
        csc1.nz_rows = col_index; // colindex pass to rowindex
        csc1.col_idx = rowptrs; // rowsptrs 传给 colptrs
        csc1.nz_values = data;
        csc1.print();
        System.out.println(Arrays.toString(csc1.col_idx));
        // 转置之后，就变成 colmajor了。
        DMatrixSparseCSC transpose1 = CommonOps_DSCC.transpose(csc1, null, null);
        transpose1.print();
        System.out.println(Arrays.toString(transpose1.col_idx));


    }

    public static void test2() {
        DMatrixSparseTriplet triplet = new DMatrixSparseTriplet(5,4,10);
        triplet.addItem(0, 1, 1.1);
        triplet.addItem(1, 1, 3.1);
        triplet.addItem(0, 2, 2.1);
        triplet.addItem(3, 2, 6);
        triplet.addItem(1, 0, 1);

        triplet.print();
        System.out.println(Arrays.toString(triplet.nz_value.data)); // in add order print array
        System.out.println(triplet.getNonZeroLength());
        // triplet的 nz_value里面有 0值。


        DMatrixSparseCSC csc = ConvertDMatrixStruct.convert(triplet, (DMatrixSparseCSC) null);
        System.out.println("csc: ");
        csc.print();
        System.out.println(Arrays.toString(csc.nz_values)); // csc.nz_values里面没有0值。 col-major print

        System.out.println("transpose");
        DMatrixSparseCSC transpose = CommonOps_DSCC.transpose(csc, null, null);
        transpose.print();
        System.out.println(Arrays.toString(transpose.nz_values)); // col-major print array. 无论转置与否，都是col-major
    }

    // reshape don't work
    public static void test1() {
        DMatrixSparseTriplet mat1 = new DMatrixSparseTriplet(4, 5, 5);// note: give initLength

        /*
         *  0  1  21  31  0
         *  0  3  0   0   0
         *  0  0  0   4   0
         *  2  0  0   0   0
         */

        // 在 DMatrixSparseTriplet.print() 中，即使对于后add的数据，也可以找到它的index，从而获取它，按照row/col index打印出来。

        mat1.addItem(0, 1, 1);
        mat1.addItem(0, 2, 21);
        mat1.addItem(0, 3, 31);
        mat1.addItem(3, 0, 2);
        mat1.addItem(1,1, 3);
        mat1.addItem(2,3,4);
        mat1.print();

        mat1.remove(0,1);
        mat1.print();

        System.out.println(mat1.isAssigned(3,0));
        System.out.println(mat1.isAssigned(0,1));

        DMatrixSparseCSC csc = ConvertDMatrixStruct.convert(mat1, (DMatrixSparseCSC) null);
        DMatrixSparseCSC transpose = CommonOps_DSCC.transpose(csc, null, null);
        transpose.print();




    }

    public static void test0() {
        Random rand = new Random(234);

        // create
        DMatrixSparseTriplet work = new DMatrixSparseTriplet(5, 4, 5);// note: give initLength
        work.addItem(0, 1, 1.2);
        work.addItem(3, 0, 3);
        work.addItem(1,1, 22.21234);
        work.addItem(2,3,6);
//        work.addItem(2,2,6);

        // convert
        DMatrixSparseCSC Z = ConvertDMatrixStruct.convert(work, (DMatrixSparseCSC) null);

        Z.print();
        System.out.println();
        Z.printNonZero(); // row col  value
        System.out.println();

        // create a large matrix that is 5% filled
        DMatrixSparseCSC A = RandomMatrices_DSCC.rectangle(ROWS, COLS, (int) (ROWS * COLS * 0.5), rand);

        // large vector that is 70% filled
        DMatrixSparseCSC x = RandomMatrices_DSCC.rectangle(COLS, XCOLS, (int) (XCOLS * COLS * 0.7), rand);

        System.out.println("done");

        // storage for the initial solution
        DMatrixSparseCSC y = new DMatrixSparseCSC(ROWS, XCOLS, 0);
        DMatrixSparseCSC z = new DMatrixSparseCSC(ROWS, XCOLS, 0);

        long before = System.currentTimeMillis();
        IGrowArray workA = new IGrowArray(A.numRows); // integer array
        DGrowArray workB = new DGrowArray(A.numRows); // double array

        for (int i=0; i<100; ++i) {
            CommonOps_DSCC.mult(A, x, y, workA, workB);
            CommonOps_DSCC.add(1.5, y, 0.75, y, z, workA, workB);
        }
        long after = System.currentTimeMillis();

        System.out.println("norm = " + NormOps_DSCC.fastNormF(y) + ", sparse basic.time = "+(after-before)+ " ms");

        DMatrixRMaj Ad = ConvertDMatrixStruct.convert(A, (DMatrixRMaj) null);
        DMatrixRMaj xd = ConvertDMatrixStruct.convert(x, (DMatrixRMaj) null);
        DMatrixRMaj yd = new DMatrixRMaj(y.numRows, y.numCols);
        DMatrixRMaj zd = new DMatrixRMaj(y.numRows, y.numCols);

        System.out.println("A: ");
        A.print();
        System.out.println("Ad: ");
        Ad.print();

        before = System.currentTimeMillis();
        for (int i=0; i<100; ++i) {
            CommonOps_DDRM.mult(Ad, xd, yd);
            CommonOps_DDRM.add(1.5, yd, 0.75, yd, zd);
        }
        after = System.currentTimeMillis();
        System.out.println("norm = " + NormOps_DDRM.fastNormF(yd) + ", dense basic.time = " + (after - before) + " ms");


        System.out.println("================");
        System.out.println("================");
        System.out.println("A:");
        A.print();
        A.printNonZero();

        System.out.println("Ad:");
        Ad.print();
        System.out.println(Arrays.toString(Ad.data));

    }

    public static void add() {
        // create
        DMatrixSparseTriplet mat1 = new DMatrixSparseTriplet(5, 5, 5);// note: give initLength

        /*
         *  0  1  0  0  0
         *  0  3  0  0  0
         *  0  0  0  4  0
         *  2  0  0  0  0
         *  0  0  0  0  0
         */

        // 在 DMatrixSparseTriplet.print() 中，即使对于后add的数据，也可以找到它的index，从而获取它，按照row/col index打印出来。

        mat1.addItem(0, 1, 1);
        mat1.addItem(3, 0, 2);
        mat1.addItem(1,1, 3);
        mat1.addItem(2,3,4);
        mat1.print();
        System.exit(0);


        DMatrixSparseCSC csc1 = ConvertDMatrixStruct.convert(mat1, (DMatrixSparseCSC) null);
        System.out.println("csc1: ");
        csc1.print();

        DMatrixSparseTriplet triplet = ConvertDMatrixStruct.convert(csc1, (DMatrixSparseTriplet) null);
        System.out.println("back to triplet");
        triplet.print();


        DMatrixSparseTriplet mat2 = new DMatrixSparseTriplet(5, 5, 5);// note: give initLength
        mat2.addItem(0, 1, 1);
        mat2.addItem(3, 0, 2);
        mat2.addItem(1,1, 3);
        mat2.addItem(2,2,4);
        DMatrixSparseCSC csc2 = ConvertDMatrixStruct.convert(mat2, (DMatrixSparseCSC) null);
        System.out.println("csc2: ");
        csc2.print();



        DMatrixSparseCSC res = new DMatrixSparseCSC(5,4, 0);
        CommonOps_DSCC.mult( csc1,  csc2, res, null, null);
        System.out.println("res: ");
        res.print();





    }

}
