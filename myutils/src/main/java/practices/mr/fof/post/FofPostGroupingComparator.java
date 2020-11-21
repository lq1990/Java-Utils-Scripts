package practices.mr.fof.post;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

/**
 * @author liuqiang
 * @since 2020-11-21 15:55
 */
public class FofPostGroupingComparator extends WritableComparator {
    public FofPostGroupingComparator() {
        super(FofPostEntity.class, true);
    }

    @Override
    public int compare(WritableComparable a, WritableComparable b) {
        FofPostEntity e1 = (FofPostEntity) a;
        FofPostEntity e2 = (FofPostEntity) b;

        return e1.left.compareTo(e2.left);
    }
}
