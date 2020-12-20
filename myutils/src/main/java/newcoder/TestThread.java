package newcoder;

import java.util.Arrays;

/**
 * @author liuqiang
 * @since 2020-12-18 20:42
 */
public class TestThread {
    static int num = 355;
    static String[] array;

    public static void main(String[] args) throws InterruptedException {
        array = new String[4 * num];

        MyThread a = new MyThread("A", 0);
        MyThread b = new MyThread("B", 1);
        MyThread c = new MyThread("C", 2);
        MyThread d = new MyThread("D", 3);
        Thread ta = new Thread(a);
        ta.start();
        Thread tb = new Thread(b);
        tb.start();
        Thread tc = new Thread(c);
        tc.start();
        Thread td = new Thread(d);
        td.start();

        // join
        ta.join();
        tb.join();
        tc.join();
        td.join();

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            sb.append(array[i]);
        }

        System.out.println(sb.toString());
    }

}

class MyThread implements Runnable {
    private final String letter;
    private final int idx;

    public MyThread(final String letter, final int idx) {
        this.letter = letter;
        this.idx = idx;
    }

    @Override
    public void run() {
        for (int i = 0; i < TestThread.num; i++) {
            TestThread.array[i * 4 + idx] = this.letter;
        }
    }
}

class MyThread0 implements Runnable {
    @Override
    public void run() {
        for (int i = 0; i < TestThread.num; i++) {
            System.out.println(Thread.currentThread().getName() + " is running...");
        }
    }
}
