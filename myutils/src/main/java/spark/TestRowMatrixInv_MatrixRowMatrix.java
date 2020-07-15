package spark;

import com.exceeddata.ac.common.exception.EngineException;
import com.google.gson.internal.$Gson$Preconditions;
import org.apache.ivy.ant.PackageMapping;
import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.mllib.linalg.*;
import org.apache.spark.mllib.linalg.distributed.RowMatrix;
import org.apache.spark.rdd.RDD;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.SQLContext;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.Metadata;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;
import scala.Function1;
import scala.collection.GenTraversableOnce;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * inverse of matrix in spark
 *
 * @author liuqiang
 * @since 7/13/20 2:12 PM
 */
public class TestRowMatrixInv_MatrixRowMatrix {

    public static void main(String[] args) throws EngineException {
        SparkConf conf = new SparkConf().setAppName("test").setMaster("local");
        JavaSparkContext sc = new JavaSparkContext(conf);
        sc.setLogLevel("WARN");

        testCalcUsingRowMatrix_Matrix(sc);


    }

    /*
    import org.apache.spark.mllib.linalg.{Vectors,Vector,Matrix,SingularValueDecomposition,DenseMatrix,DenseVector}
import org.apache.spark.mllib.linalg.distributed.RowMatrix

def computeInverse(X: RowMatrix): DenseMatrix = {
  val nCoef = X.numCols.toInt
  val svd = X.computeSVD(nCoef, computeU = true)
  if (svd.s.size < nCoef) {
    sys.error(s"RowMatrix.computeInverse called on singular matrix.")
  }

  // Create the inv diagonal matrix from S
  val invS = DenseMatrix.diag(new DenseVector(svd.s.toArray.map(x => math.pow(x,-1))))

  // U cannot be a RowMatrix
  val U = new DenseMatrix(svd.U.numRows().toInt,svd.U.numCols().toInt,svd.U.rows.collect.flatMap(x => x.toArray))

  // If you could make V distributed, then this may be better. However its alreadly local...so maybe this is fine.
  val V = svd.V
  // inv(X) = V*inv(S)*transpose(U)  --- the U is already transposed.
  (V.multiply(invS)).multiply(U)
  }
     */

    public static DenseMatrix computeInv(RowMatrix X) throws EngineException {
        int nCoef = (int) X.numCols();
        SingularValueDecomposition<RowMatrix, Matrix> svd = X.computeSVD(nCoef, true, Math.pow(10, -9));
        if (svd.s().size() < nCoef) throw new EngineException("SINGULAR_MATRIX");

        // create the inv diag matrix from S
        double[] array0 = svd.s().toArray();
        double[] array = new double[array0.length];
        for (int i = 0; i < array0.length; ++i) {
            array[i] = Math.pow(array0[i], -1);
        }
        DenseMatrix invS = DenseMatrix.diag(new DenseVector(array));

        // U can not be a RowMatrix
//        JavaRDD<double[]> javaRDD = svd.U().rows().toJavaRDD().flatMap(new FlatMapFunction<Vector, double[]>() {
//            private static final long serialVersionUID = 1L;
//
//            @Override
//            public Iterator<double[]> call(Vector vector) throws Exception {
//                List<double[]> results = new ArrayList<>();
//
//                results.add(vector.toArray());
//                return results.iterator();
//            }
//        });
        final RowMatrix u = svd.U();
        List<Vector> collect = u.rows().toJavaRDD().collect();
        System.out.println("list size: "+collect.size());
        System.out.println("lise[0] size: "+collect.get(0).size());

//        List<double[]> collect = javaRDD.collect();

        final int nRows = (int) u.numRows();
        final int nCols = (int) u.numCols();
        double[] Uarray = new double[nRows * nCoef];
        for (int i = 0, len = collect.size(); i < len; ++i) {
            Vector doubles = collect.get(i);
            System.arraycopy(doubles.toArray(), 0, Uarray, i * len, len);
        }

        DenseMatrix U = new DenseMatrix(nRows, nCols, Uarray); // row/col major ???
        Matrix V = svd.V();

        // inv(X) = V * inv(S) * transpose(U)
        return V.multiply(invS).multiply(U);
    }

    public static void rowMatrix_Inv() throws EngineException {
        SparkConf conf = new SparkConf().setAppName("test").setMaster("local");
        JavaSparkContext sc = new JavaSparkContext(conf);
        sc.setLogLevel("WARN");
        SQLContext sqlContext = new SQLContext(sc);

        List<Row> data = Arrays.asList(
                RowFactory.create(0, Vectors.dense(1.0, 2d, 1d)),
                RowFactory.create(1, Vectors.dense(3.0, 4d, 5d)),
                RowFactory.create(2, Vectors.dense(5d, 6d, 7d))
        );
        StructType schema = new StructType(new StructField[]{
                new StructField("id", DataTypes.IntegerType, false, Metadata.empty()),
                new StructField("features", new VectorUDT(), false, Metadata.empty())
        });
        Dataset<Row> dataFrame = sqlContext.createDataFrame(data, schema);
        JavaRDD<Vector> vectorJavaRDD = dataFrame.toJavaRDD().mapPartitions(new FlatMapFunction<Iterator<Row>, Vector>() {
            public Iterator<Vector> call(Iterator<Row> iterator) throws Exception {

                List<Vector> results = new ArrayList<Vector>();

                while (iterator.hasNext()) {
                    Row row = iterator.next();
                    Vector vector = (Vector) row.getAs("features");

                    results.add(new org.apache.spark.mllib.linalg.DenseVector(vector.toArray()));
                }

                return results.iterator();
            }
        });

        RowMatrix rowMatrix = new RowMatrix(vectorJavaRDD.rdd());
        DenseMatrix denseMatrix = computeInv(rowMatrix);
        System.out.println(Arrays.toString(denseMatrix.toArray())); // array is in col-major order
    }

    public static void testCalcUsingRowMatrix_Matrix(JavaSparkContext sc) throws EngineException {
        // to Matrix
        DenseMatrix Pmat = new DenseMatrix(2, 2, new double[]{1.1, 2.1, 1.2, 2.2}); // Note: colmajor
        DenseMatrix Hmat = new DenseMatrix(2, 2, new double[]{1, 2, 3, 4}); // Note: colmajor
        DenseMatrix Rmat = new DenseMatrix(2, 2, new double[]{1, 2, 5, 6}); // Note: colmajor

        // to List<Vector>  // to JavaRDD // to RowMatrix
//        List<Vector> listP = new ArrayList<>();
//        Vector vector0 = new DenseVector(new double[]{1.1, 1.2});
//        Vector vector1 = new DenseVector(new double[]{2.1, 2.2});
//        listP.add(vector0);
//        listP.add(vector1);
//        JavaRDD<Vector> Prdd = sc.parallelize(listP);
//        RowMatrix Prowmat = new RowMatrix(Prdd.rdd());
//
//        List<Vector> listH = new ArrayList<>();
//        vector0 = new DenseVector(new double[]{1, 3});
//        vector1 = new DenseVector(new double[]{2,4});
//        listH.add(vector0);
//        listH.add(vector1);
//        JavaRDD<Vector> Hrdd = sc.parallelize(listH);
//        RowMatrix Hrowmat = new RowMatrix(Hrdd.rdd());
//
//        List<Vector> listR = new ArrayList<>();
//        vector0 = new DenseVector(new double[]{1, 5});
//        vector1 = new DenseVector(new double[]{2,6});
//        listR.add(vector0);
//        listR.add(vector1);
//        JavaRDD<Vector> Rrdd = sc.parallelize(listR);
//        RowMatrix Rrowmat = new RowMatrix(Rrdd.rdd());


        // + - * inv T, all use RowMatrix/ Matrxi

        RowMatrix P = matrix2RowMatrix(sc, Pmat);
        RowMatrix H = matrix2RowMatrix(sc, Hmat);
        RowMatrix R = matrix2RowMatrix(sc, Rmat);

        DenseMatrix denseMatrix = computeInv(P);
        System.out.println(Arrays.toString(denseMatrix.toArray()));

        DenseMatrix inner =
                        plus(
                                Hmat.multiply(Pmat).multiply(Hmat.transpose()), Rmat
                        );

        DenseMatrix multiply = Pmat.multiply(Hmat.transpose()).multiply(computeInv(matrix2RowMatrix( sc, inner))); // inv

        System.out.println("===");
        System.out.println("inner: "+Arrays.toString(inner.toArray()));
        System.out.println("mul: "+Arrays.toString(multiply.toArray()));

        /*
        inner: [31.800000000000004, 46.2, 51.0, 72.0]
        mul: [-0.22522522522551114, -0.38738738738788747, 0.2567567567569524, 0.4549549549552978]
         */

    }

    public static DenseMatrix plus(DenseMatrix matrix, DenseMatrix other) {
        double[] array = matrix.toArray();
        double[] oArray = other.toArray();

        final int len = array.length;
        double[] res = new double[len];

        for (int i = 0; i < len; ++i) {
            res[i] = array[i] + oArray[i];
        }

        return new DenseMatrix(matrix.numRows(), matrix.numCols(), res);
    }

    /**
     * double[] => RowMatrix
     */
    public static void rawData2RowMatrix() throws EngineException {
        SparkConf conf = new SparkConf().setAppName("test").setMaster("local");
        JavaSparkContext sc = new JavaSparkContext(conf);
        sc.setLogLevel("WARN");

        List<Vector> list = new ArrayList<>();
        Vector vector0 = new DenseVector(new double[]{1, 2}); // row0
        Vector vector1 = new DenseVector(new double[]{4, 5}); // row1
        list.add(vector0);
        list.add(vector1);

        JavaRDD<Vector> parallelize = sc.parallelize(list);

        DenseMatrix denseMatrix = computeInv(new RowMatrix(parallelize.rdd()));
        System.out.println(Arrays.toString(denseMatrix.toArray()));

    }


    /**
     * Matrix
     * to
     * JavaRDD\<Vector\>
     */
    public static JavaRDD<Vector> matrix2RDD(JavaSparkContext sc, Matrix matrix) {
        scala.collection.Iterator<Vector> rowIter = matrix.rowIter();
        List<Vector> list = new ArrayList<>();
        while (rowIter.hasNext()) {
            Vector next = rowIter.next();
            list.add(Vectors.dense(next.toArray()));
        }

        return sc.parallelize(list);
    }

    public static RowMatrix matrix2RowMatrix(JavaSparkContext sc, Matrix matrix) {
        scala.collection.Iterator<Vector> rowIter = matrix.rowIter();
        List<Vector> list = new ArrayList<>();
        while (rowIter.hasNext()) {
            Vector next = rowIter.next();
            list.add(Vectors.dense(next.toArray()));
        }

        JavaRDD<Vector> parallelize = sc.parallelize(list);
        return new RowMatrix(parallelize.rdd());
    }

}


