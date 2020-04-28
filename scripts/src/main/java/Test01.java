import sun.dc.path.PathError;

/**
 * @author liuqiang
 * @since 2020/4/27 16:08
 */
public class Test01 {
    public static void main(String[] args) {

        System.out.println("main");
        Person person = new Person();
        test(person); // 传来的是引用，test() 中会修改person的属性。

        System.out.println(person);

    }

    private static void test(Person person) {
        person.id = 11;
        person.name = "anna";
    }

}

class Person {
    int id;

    String name;

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
