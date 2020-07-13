//package fir1filter_streaming_bk;
//
//import com.exceeddata.ac.common.data.record.Ident;
//import com.exceeddata.ac.common.data.record.Record;
//import com.exceeddata.ac.common.data.typedata.DoubleData;
//import com.exceeddata.ac.common.evaluate.expression.Expression;
//import com.exceeddata.ac.common.evaluate.expression.ExpressionBuilder;
//import com.exceeddata.ac.common.exception.EngineException;
//import com.exceeddata.ac.common.util.signal.Fir1Helper;
//import com.exceeddata.ac.spark.core.function.PartitionFunction;
//import com.exceeddata.ac.spark.core.impl.ImplEnums;
//import scala.Tuple2;
//
//import java.util.ArrayList;
//import java.util.Iterator;
//import java.util.LinkedList;
//import java.util.Queue;
//
///**
// * A FIR1 Filter partitions function implementation.
// */
//public final class Fir1FilterPartitions extends PartitionFunction {
//    private static final long serialVersionUID = 1L;
//
//    private String filterName = null;
//
//    //internal variables
//    private Expression xExp = null;
//    private Fir1Helper helper = null;
//    private final int len;
//    private final Queue<Record> valueQueue;
//    private final Queue<Ident> identQueue;
//
//    public Fir1FilterPartitions(final String x, final double[] coefficients, final String prefixName) throws EngineException {
//        this.xExp = ExpressionBuilder.build(x);
////        this.helper = new Fir1Helper(DataConv.toDenseVectorData(ExpressionBuilder.build(coefficients).evaluate(new Record())).getUnsafeDoubles());
//        this.helper = new Fir1Helper(coefficients);
//        this.filterName = prefixName + "_" + this.xExp.getAlias();
//        this.len = coefficients.length;
//        this.valueQueue = new LinkedList<>();
//        this.identQueue = new LinkedList<>();
//    }
//
//    /**
//     * {@inheritDoc}
//     */
//    @Override
//    public Iterator<Tuple2<Ident, Record>> call(final Iterator<Tuple2<Ident, Record>> iter) throws Exception {
//        final ArrayList<Tuple2<Ident, Record>> results = new ArrayList<>(ImplEnums.INIT_CAPACITY);
//        boolean useTemplate = isSourceTemplateable(), hasTemplate = false;
//        Record templateable = null, record, value;
//        Tuple2<Ident, Record> tuple = null;
//        Double data, output;
//
//        while (iter.hasNext()) {
//            tuple = iter.next();
//            record = tuple._2;
//            data = xExp.evaluate(record).toDouble(); // src data, can be null。null值如何处理？ ++++++++++++++++++++++
//
//            helper.filterIntoQueue(data); // data into buffer, and output enqueue. Note: at the final step, not all outputs are enqueued
//
//            value = hasTemplate ? record.unsafeTemplateAdditionCopy(templateable) : record.additionCopy(1);
//            valueQueue.offer(value); // value enqueue, at the final step all values are enqueued
//            identQueue.offer(tuple._1);
//
////            value.add(filterName, data == null ? DoubleData.NULL : DoubleData.nonNullValueOf(helper.filter(data.doubleValue())));
////            results.add(new Tuple2<Ident, Record>(tuple._1, value));
//
//            // get output from queue
//            if ((output = helper.getOutput()) != null) {
//                value = valueQueue.poll();
//                if (value != null) {
//                    value.add(filterName, DoubleData.nonNullValueOf(output));
//                    results.add(new Tuple2<Ident, Record>(identQueue.poll(), value));
//
//                    if (useTemplate && iter.hasNext()) {
//                        templateable = value.templateCopy();
//                        hasTemplate = true;
//                        useTemplate = false;
//                    }
//                }
//            }
//        }
//
//        // so far, not all outputs are got. so use the for below:
//        for (int i = 0; i < len / 2; ++i) {
//            helper.filterIntoQueue(0d); // with this, all outputs are enqueued
//            output = helper.getOutput();
//            value = valueQueue.poll();
//            if (value != null) {
//                value.add(filterName, DoubleData.nonNullValueOf(output));
//                results.add(new Tuple2<Ident, Record>(identQueue.poll(), value));
//            }
//        }
//
//        // old
////        while (iter.hasNext()) {
////            tuple = iter.next();
////            record = tuple._2;
////            value = hasTemplate ? record.unsafeTemplateAdditionCopy(templateable) : record.additionCopy(1);
////            data = xExp.evaluate(record).toDouble();
////            value.add(filterName, data == null ? DoubleData.NULL : DoubleData.nonNullValueOf(helper.filter(data.doubleValue())));
////            results.add(new Tuple2<Ident, Record>(tuple._1, value));
////            if (useTemplate && iter.hasNext()) {
////                templateable = value.templateCopy();
////                hasTemplate = true;
////                useTemplate = false;
////            }
////        }
//
//        return results.iterator();
//    }
//}
