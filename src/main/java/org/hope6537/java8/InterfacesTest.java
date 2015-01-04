package org.hope6537.java8;

import org.junit.Test;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Created by threegrand1 on 14-12-31.
 */
public class InterfacesTest {

    @Test
    public void testPredicate() {
        Predicate<String> predicate = (s) -> s.length() > 0;
        predicate.test("foo");              // true
        predicate.negate().test("foo");     // false
        Predicate<Boolean> nonNull = Objects::nonNull;
        Predicate<Boolean> isNull = Objects::isNull;
        Predicate<String> isEmpty = String::isEmpty;
        Predicate<String> isNotEmpty = isEmpty.negate();
    }

    @Test
    public void testFunction() {
        Function<String, Integer> toInteger = Integer::valueOf; //这一步是设置一个转成Integer的函数
        Function<String, String> backToString = toInteger.andThen(String::valueOf);//这一步是转回来
        System.out.println(backToString.apply("123"));     // 结果输出是"123"
    }

    @Test
    public void testSupplier() {
        Supplier<Person> personSupplier = Person::new;
        System.out.println(personSupplier.get());
    }

    @Test
    public void testConsumer() {
        Consumer<Person> greeter = (p) -> System.out.println("Hello, " + p.firstName);
        greeter.accept(new Person("Kobe", "Bryant"));
    }

    @Test
    public void testComparator() {
        Comparator<Person> comparator = (p1, p2) -> p1.firstName.compareTo(p2.firstName);
        Person p1 = new Person("John", "Doe");
        Person p2 = new Person("Alice", "Wonderland");
        System.out.println(comparator.compare(p1, p2));             // > 0
        System.out.println(comparator.reversed().compare(p1, p2));  // 反转判定表达式 < 0
    }

    @Test
    public void testOptional() {
        Optional<String> optional = Optional.of("bam");
        optional.isPresent();           // true
        optional.get();                 // "bam"
        //value是否为空 不为空就输出数据 为空就输出参数
        System.out.println(optional.orElse(""));    // "bam"
        optional.ifPresent((s) -> System.out.println(s.charAt(0)));     // "b"
    }

    public List<String> getStringCollection() {
        List<String> stringCollection = new ArrayList<>();
        stringCollection.add("ddd2");
        stringCollection.add("aaa2");
        stringCollection.add("bbb1");
        stringCollection.add("aaa1");
        stringCollection.add("bbb3");
        stringCollection.add("ccc");
        stringCollection.add("bbb2");
        stringCollection.add("ddd1");
        return stringCollection;
    }

    @Test
    public void testFilter() {
        getStringCollection()
                .stream()
                        //这里是定义过滤规则 传递一个返回布尔值的函数
                .filter((s) -> s.startsWith("a"))
                        //这部将仅仅保留以a为开头的变量
                .forEach(System.out::println);
        // "aaa2", "aaa1"
    }

    @Test
    public void testSort() {
        getStringCollection()
                .stream()
                        //直接使用Comparable进行比较
                .sorted()
                .filter((s) -> s.startsWith("a"))
                .forEach(System.out::println);
        // "aaa1", "aaa2"
    }

    @Test
    public void testMapping() {
        getStringCollection()
                .stream()//流化
                .map(String::toUpperCase)//映射成大写字符串
                        //这里是IDE抽风
                .sorted((a, b) -> b.compareTo(a))//同时定义Comparable进行排序
                .forEach(System.out::println);//最后迭代输出
        // "DDD2", "DDD1", "CCC", "BBB3", "BBB2", "AAA2", "AAA1"
    }

    @Test
    public void testMatch() {
        /*第十七 Match 匹配
        Stream提供了多种匹配操作，允许检测指定的Predicate是否匹配整个Stream。
        所有的匹配操作都是最终操作，并返回一个boolean类型的值。*/
        //存在操作 存在某项匹配判定表达式
        boolean anyStartsWithA =
                getStringCollection()
                        .stream()
                        .anyMatch((s) -> s.startsWith("a"));
        System.out.println(anyStartsWithA);      // true
        //全部操作 所有元素都匹配判定表达式
        boolean allStartsWithA =
                getStringCollection()
                        .stream()
                        .allMatch((s) -> s.startsWith("a"));
        System.out.println(allStartsWithA);      // false
        //没有操作 没有元素匹配判定表达式
        boolean noneStartsWithZ =
                getStringCollection()
                        .stream()
                        .noneMatch((s) -> s.startsWith("z"));
        System.out.println(noneStartsWithZ);      // true
    }


    @Test
    public void testCount() {
        long startsWithB =
                getStringCollection()
                        .stream()
                                //过滤一下
                        .filter((s) -> s.startsWith("b"))
                        .count();
        System.out.println(startsWithB);    // 3
    }

    @Test
    public void testReduce() {
        Optional<String> reduced =
                getStringCollection()
                        .stream()
                        .sorted()
                                //存在字符串s1和s2 将其用#合并
                        .reduce((s1, s2) -> s1 + "#" + s2);
        reduced.ifPresent(System.out::println);
        // "aaa1#aaa2#bbb1#bbb2#bbb3#ccc#ddd1#ddd2"
    }

    @Test
    public void testThreadStream() {
        int max = 1000000;
        List<String> values = new ArrayList<>(max);
        for (int i = 0; i < max; i++) {
            UUID uuid = UUID.randomUUID();
            values.add(uuid.toString());
        }
        {
            //串行排序 注意【】中的不同
            long t0 = System.nanoTime();
            long count = values.stream().sorted().count();
            System.out.println(count);
            long t1 = System.nanoTime();
            long millis = TimeUnit.NANOSECONDS.toMillis(t1 - t0);
            System.out.println(String.format("串行排序: %d ms", millis));
            //899ms
        }
        {
            //并行排序
            long t0 = System.nanoTime();
            long count = values.parallelStream().sorted().count();
            System.out.println(count);
            long t1 = System.nanoTime();
            long millis = TimeUnit.NANOSECONDS.toMillis(t1 - t0);
            System.out.println(String.format("并行排序: %d ms", millis));
        }
    }

    @Test
    public void testMap() {

        Map<Integer, String> map = new HashMap<>();
        for (int i = 0; i < 10; i++) {
            //我们不需要进行额外行检查 如果不为空就会添加，或者说合法就会添加
            map.putIfAbsent(i, "val" + i);
        }
        map.forEach((id, val) -> System.out.println(val));

        //以上代码很容易理解， putIfAbsent 不需要我们做额外的存在性检查，而forEach则接收一个Consumer接口来对map里的每一个键值对进行操作。
        //下面的例子展示了map上的其他有用的函数：
        //computeIfPresent的作用就是put
        //只不过第二个参数根据所定义的函数进行相关的计算从而进行赋值
        map.computeIfPresent(3, (num, val) -> val + num);
        map.get(3);             // val33

        map.computeIfPresent(9, (num, val) -> null);
        map.containsKey(9);     // false 不存在

        map.computeIfAbsent(23, num -> "val" + num);
        map.containsKey(23);    // true 存在值 不为null

        map.computeIfAbsent(3, num -> "bam");
        map.get(3);             // val33 因为IfAbsent的作用是自身进行额外性检查，如果检查不通过是不会添加值的


        //接下来展示如何在Map里删除一个【键和值】【全都匹配】的项：

        map.remove(3, "val3");
        map.get(3);             // val33
        map.remove(3, "val33");
        map.get(3);             // null

        //另外一个有用的方法：
        //如果key -> 42有对应的value就输出value  没有就输出not found
        map.getOrDefault(42, "not found");  // not found

        //对Map的元素做合并也变得很容易了
        map.merge(9, "val9", (value, newValue) -> value.concat(newValue)); //concat 字符串连接
        map.get(9);             // val9
        map.merge(9, "concat", (value, newValue) -> value.concat(newValue));
        map.get(9);             // val9concat
        //Merge做的事情是如果【键名不存在则插入】，否则则对【原键对应的值】做【合并】操作并【重新插入】到map中。

    }

}
