package branch.data_structure;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author liuqiang
 * @since 7/2/20 9:18 AM
 */
public class QueueTest {
    public static void main(String[] args) {
        Queue<Double> queue = new LinkedList<>();

        queue.offer(1.1);
        queue.offer(1.2);
        queue.offer(1.3);
        queue.offer(1.4);

        Double out;
        while ((out = queue.poll()) != null) {
            System.out.println(out);
        }

        System.exit(0);

        System.out.println(queue.size());

        System.out.println(queue);

        System.out.println(queue.poll());
        System.out.println(queue);

        System.out.println(queue.poll());
        System.out.println(queue);

        System.out.println(queue.poll());
        System.out.println(queue);

        System.out.println(queue.poll());
        System.out.println(queue.poll());
        System.out.println(queue.poll());

    }
}
