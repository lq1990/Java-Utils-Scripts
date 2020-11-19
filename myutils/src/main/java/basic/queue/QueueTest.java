package basic.queue;

import java.util.LinkedList;

/**
 * @author liuqiang
 * @since 2020-11-19 08:42
 */
public class QueueTest {
    public static void main(String[] args) {
        LinkedList<String> queue = new LinkedList<String>();
        queue.offer("a1");
        queue.offer("a2");
        queue.offer("a3");

        System.out.println(queue);

        System.out.println(queue.peek());
        System.out.println(queue);

        String poll = queue.poll();
        System.out.println(queue);
    }
}
