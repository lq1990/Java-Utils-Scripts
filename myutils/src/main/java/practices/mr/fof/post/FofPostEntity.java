package practices.mr.fof.post;

import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Objects;
import java.util.StringTokenizer;

/**
 *
 * cat  hadoop:2
 * cat  hive:1
 *
 * @author liuqiang
 * @since 2020-11-20 10:19
 */
public class FofPostEntity implements WritableComparable<FofPostEntity> {
    String left;
    String right;
    String rightNum;

    public FofPostEntity() {
    }

    public FofPostEntity(String left, String right, String rightNum) {
        this.left = left;
        this.right = right;
        this.rightNum = rightNum;
    }

    public void set(String left, String right, String rightNum) {
        this.left = left;
        this.right = right;
        this.rightNum = rightNum;
    }

    @Override
    public int compareTo(FofPostEntity o) {
        if(this.left.compareTo(o.left) == 0) {
            int num = Integer.parseInt(this.rightNum);
            int onum = Integer.parseInt(o.rightNum);

            return -num + onum; // num in desc sort
        } else {
            return this.left.compareTo(o.left);
        }
    }

    @Override
    public void write(DataOutput out) throws IOException {
        out.writeUTF(left);
        out.writeUTF(right);
        out.writeUTF(rightNum);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        left = in.readUTF();
        right = in.readUTF();
        rightNum = in.readUTF();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FofPostEntity that = (FofPostEntity) o;
        return Objects.equals(left, that.left) &&
                Objects.equals(right, that.right) &&
                Objects.equals(rightNum, that.rightNum);
    }

    @Override
    public int hashCode() {
        return Objects.hash(left, right, rightNum);
    }

}
