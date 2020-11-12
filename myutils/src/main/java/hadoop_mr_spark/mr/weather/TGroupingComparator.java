package hadoop_mr_spark.mr.weather;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

/**
 * @author liuqiang
 * @since 2020-11-11 19:45
 */
public class TGroupingComparator extends WritableComparator {
    public TGroupingComparator() {
        super(TQ.class, true);
    }

    @Override
    public int compare(WritableComparable a, WritableComparable b) {
        TQ t1 = (TQ) a;
        TQ t2 = (TQ) b;

        int c1 = Integer.compare(t1.getYear(), t2.getYear());
        if (c1 == 0) {
            return Integer.compare(t1.getMonth(), t2.getMonth());
        }
        return c1;
    }
}
