package math0.matrix_vector_lib.ejml;

import org.ejml.data.DMatrixRMaj;
import org.ejml.data.DMatrixSparseCSC;
import org.ejml.dense.row.CommonOps_DDRM;
import org.ejml.dense.row.NormOps_DDRM;
import org.ejml.ops.ConvertDMatrixStruct;

import java.util.Arrays;

/**
 * @author liuqiang
 * @since 6/16/20 11:40 AM
 */
public class TestEJMLDenseMatrix {
    public static void main(String[] args) {
//        testMajor();


        testDivBy0();


    }

    public static void testDivBy0() {
        DMatrixRMaj mat1 = new DMatrixRMaj(3,2,true, 1,2,3,4,5,6);
        DMatrixRMaj maj = new DMatrixRMaj(3, 2, true, 0, 0, 0, 1, 1, 1);

        DMatrixRMaj res = new DMatrixRMaj(3, 2);

        CommonOps_DDRM.elementDiv(mat1, maj, res);

        mat1.print();
        maj.print();
        System.out.println("res: ");
        res.print();

    }



    public static void testMajor() {
        DMatrixRMaj matrixRMaj = new DMatrixRMaj(3, 2, false, 1, 2, 3, 4, 5, 6);
        matrixRMaj.print();

        System.out.println(Arrays.toString(matrixRMaj.data)); // 2D => 1D, rowMajor encoded

        // always rowMajor encoded, no matter true/false in input for mat.data

        matrixRMaj = new DMatrixRMaj(3, 2, true, 1, 2, 3, 4, 5, 6);
        matrixRMaj.print();
        System.out.println(Arrays.toString(matrixRMaj.data)); // rowMajor out
    }

    public static void testDense2Sparse() {
        DMatrixRMaj mat = new DMatrixRMaj(3,2, true, 1,2,3,4,5,6);
        System.out.println("matRMaj: ");
        mat.print();
        System.out.println(Arrays.toString(mat.data)); // rowMajor encoded

        DMatrixSparseCSC csc = ConvertDMatrixStruct.convert(mat, (DMatrixSparseCSC) null, 0d);
        System.out.println("csc: ");
        csc.print();
        System.out.println(Arrays.toString(csc.nz_values)); // colMajor encoded

    }

    public static void testNorm() {
        DMatrixRMaj mat = new DMatrixRMaj(3,1, true, 1,2,3);
        System.out.println(NormOps_DDRM.normP(mat, 1));
        System.out.println(NormOps_DDRM.normP(mat, 2));



    }

    public static void test() {
        DMatrixRMaj matrixRMaj = new DMatrixRMaj(3,2, true, 0,0,0,0,1,0);
        matrixRMaj.set(1,0,100);
        System.out.println("ori: ");
        matrixRMaj.print();
        System.out.println(Arrays.toString(matrixRMaj.getData())); // print rowMjor

        DMatrixRMaj transpose = CommonOps_DDRM.transpose(matrixRMaj, null);
        System.out.println("trans: ");
        transpose.print();
        System.out.println(Arrays.toString(transpose.getData())); // print in rowMajor。无论转置与否，都 rowMajor print

    }
}
