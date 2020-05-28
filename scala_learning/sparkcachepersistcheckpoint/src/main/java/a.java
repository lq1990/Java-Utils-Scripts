/**
 * @author liuqiang
 * @since 2020/5/2 15:30
 */
public class a
{
    public static void main(String[] args) {
        Expression featuresExp = ExpressionBuilder.build(features);
        DenseVectorData featuresVector;
        while (iter.hasNext()) {
            record= iter.next()._2;
            featuresVector = DataConv.toDenseVectorData(featuresExp.evaluate(record));
            Vector vector = new Vector(featuresVector.unsafeItems())

        }

        //

        mapPartitionsToPair(new ApplyPCA(broacasted matrix))
        while (iter.hasNext()) {
            record= iter.next()._2;
            featuresVector = DataConv.toDenseVectorData(featuresExp.evaluate(record));
            Vector vector = new Vector(featuresVector.unsafeItems()):
            value.add("features", new DenseVectorData(tranposed_matrix..(vector).array())):
            value.add("label", record.get(label))
            results.add new Tuple2<>(EmptyIdent.instance, value)
        }

        return results;
    }
}
