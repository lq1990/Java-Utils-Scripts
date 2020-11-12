package hadoop_mr_spark.mr.weather;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

/**
 * @author liuqiang
 * @since 2020-11-11 19:24
 */
public class TSortComparator extends WritableComparator {

    public TSortComparator() {
        super(TQ.class, true); // should instance
    }

    @Override
    public int compare(WritableComparable a, WritableComparable b) {
        TQ t1 = (TQ) a;
        TQ t2 = (TQ) b;

        int c1 = Integer.compare(t1.getYear(), t2.getYear());
        if (c1 == 0) {
            int c2 = Integer.compare(t1.getMonth(), t2.getMonth());
            if (c2 == 0) {
                int compare = Integer.compare(t1.getTemperature(), t2.getTemperature());
                return -compare;
            }
            return c2;
        }
        return c1;
    }
}
