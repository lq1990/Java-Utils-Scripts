package practices.mr.fof.post;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

/**
 * @author liuqiang
 * @since 2020-11-17 16:07
 */
public class FofPostSortComparator extends WritableComparator {

    public FofPostSortComparator() {
        super(Text.class, true);
    }

    @Override
    public int compare(WritableComparable a, WritableComparable b) {
        Text t1 = (Text) a;
        Text t2 = (Text) b;

        // text is:
        return t1.toString().compareTo(t2.toString());
    }

}
