package test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * iterator 被多个funciton 使用的话，iter会不会 每次从头开始
 *
 * A1: 每个fn，都会从iter的头开始  :)
 *
 * @author liuqiang
 * @since 6/2/20 4:41 PM
 */
public class testIterator {

    public static void main(String[] args) {
        List<String> list = new ArrayList<String>();
        list.add("aaaa");
        list.add("bbb");
        list.add("ccc");
        list.add("DDD");
        list.add("eee");

        Iterator<String> iterator = list.iterator();

        System.out.println(iterator.next());
        System.out.println(iterator.next());
        System.out.println();

        fn1(list.iterator()); // 每次 basic.list.iterator() 方法之后，都是生成新的 iter
        System.out.println();

        fn2(list.iterator());
        System.out.println();

        fn3(list.iterator());
        System.out.println();

        fn3(list.iterator());

        System.out.println("=1==");
        // 传递的是iter
        Iterator<String> iterator1 = list.iterator();
        fn1(iterator1);  // 当传递的是同一个 iter时，保持不变
        fn2(iterator1);
        fn3(iterator1);
        System.out.println(iterator1.next());
        System.out.println(iterator1.next());
        System.out.println(iterator1.next());


        System.out.println("=2==");
        Iterator<String> iterator2 = list.iterator();;




    }

    public static void fn1(Iterator<String> iterator) {
        System.out.println(iterator.next());
        System.out.println(iterator.next());
    }

    public static void fn2(Iterator<String> iterator) {
        System.out.println(iterator.next());
    }

    public static void fn3(Iterator<String> iterator) {
        System.out.println(iterator.next());
    }

}
