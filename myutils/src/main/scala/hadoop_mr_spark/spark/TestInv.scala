package hadoop_mr_spark.spark

import org.apache.spark.mllib.linalg.{DenseMatrix, DenseVector, Matrix, SingularValueDecomposition, Vector, Vectors}
import org.apache.spark.mllib.linalg.distributed.RowMatrix
import org.apache.spark.sql.RowFactory

/**
 * @author liuqiang
 * @since 7/13/20 2:28 PM
 */
object TestInv {

  def main(args: Array[String]): Unit = {
    RowFactory.create()


  }

  def computeInverse(X: RowMatrix): DenseMatrix = {
    val nCoef = X.numCols.toInt
    val svd = X.computeSVD(nCoef, computeU = true)
    if (svd.s.size < nCoef) {
      sys.error(s"RowMatrix.computeInverse called on singular matrix.")
    }

    // Create the inv diagonal matrix from S
    val invS: DenseMatrix = DenseMatrix.diag(new DenseVector(svd.s.toArray.map(x => Math.pow(x,-1))))

    // U cannot be a RowMatrix
    val U: DenseMatrix = new DenseMatrix(svd.U.numRows().toInt,svd.U.numCols().toInt,svd.U.rows.collect.flatMap(x => x.toArray))

    // If you could make V distributed, then this may be better. However its alreadly local...so maybe this is fine.
    val V = svd.V
    // inv(X) = V*inv(S)*transpose(U)  --- the U is already transposed.
    (V.multiply(invS)).multiply(U)
  }

}
