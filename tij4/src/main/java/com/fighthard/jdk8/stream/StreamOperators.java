package com.fighthard.jdk8.stream;

import static java.lang.Character.isDigit;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static org.apache.http.util.Asserts.check;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;

import com.fighthard.dtodomain.dto.UserDto;
/**
 * @formatter:off
 * 对外暴露Stream，它可以很好的封装内部实现的数据结构
 * 重构 ： 从循环里向外重构
 * 用链式调用风格：
 *  可读性好
 *  效率高，在有需要的时候才会去求值
 *  没有显示的中间变量，结构清晰
 *  便于自动并行操作处理
 * Created by piaolinzhi on
 * 2016/11/28.
 * @formatter:on
 */
public class StreamOperators {
    /**
     * 准备的集合
     */
    List<UserDto> users = new ArrayList<>();

    public StreamOperators() {
        UserDto u1 = new UserDto();
        u1.setName("name1");
        UserDto u2 = new UserDto();
        u2.setName("name2");
        UserDto u3 = new UserDto();
        u3.setName("name3");
        UserDto u4 = new UserDto();

        users.add(u1);
        users.add(u2);
        users.add(u3);
        users.add(u4);
    }

    public StreamOperators(List<UserDto> users) {
        this.users = users;
    }

    public static void main(String[] args) {

        StreamOperators it = new StreamOperators();

        // accumulator
        BinaryOperator<Integer> accumulator = (acc, element) -> acc
                + element;
        int count = accumulator
                .apply(accumulator.apply(accumulator.apply(0, 1), 2), 3);
        System.out.println(count);

        // filter
        Stream<UserDto> uHasNames = it.hasNames();

        long hasNameCount = uHasNames.count(); // Print here!
        System.out.println(
                "There is " + hasNameCount + " users with valid name");

        // filter test
        it.filterTest();

        // flatMapTest
        System.out.println("flat map test");
        it.flatMapTest();

        // max test
        it.maxTest();
        // reduce test
        it.reduceTest();
    }

    /**
     * 获得用户中名字不为空的用户stream
     * @return 名字不为空的用户stream
     */
    public Stream<UserDto> hasNames() {
        return users.parallelStream().filter(u -> {
            // 由于惰性求值，这行的打印时间是在求值是打印的，调用这个函数的时候是不会被打印出来的
            System.out.println("Handle user :" + u.toString());
            return StringUtils.isNotBlank(u.getName());
        });
    }

    /**
     * collect(Collectors.toList()) 由Stream里的值生成一个列表，是一个及早求值操作
     */
    public void collectorToList() {
        List<String> collected = Stream.of("a", "b", "c")
                .collect(toList());
    }

    /**
     * 传递给map的 Lambda表达式必须是 Function 接口的一个实例 Function 接口是只包含一个参数的普通函数接口
     */
    public void mapTest() {
        // Old fashion.
        List<String> collected = new ArrayList<>();
        for(String string : asList("a", "b", "hello")) {
            String uppercaseString = string.toUpperCase();
            collected.add(uppercaseString);
        }
        // Map fashion.
        List<String> mapCollected = Stream.of("", "", "")
                .map(string -> string.toUpperCase()).collect(toList());
    }

    /**
     * filter
     */
    public void filterTest() {

        // Old fashion
        List<String> beginningWithNumbers = new ArrayList<>();
        for(String value : asList("a", "1abc", "abc1")) {
            if(isDigit(value.charAt(0))) {
                beginningWithNumbers.add(value);
            }
        }
        // Filter fashion
        beginningWithNumbers = Stream.of("a", "1abc", "abc1")
                .filter(value -> isDigit(value.charAt(0)))
                .collect(toList());
    }

    /**
     * flatMap
     */
    public void flatMapTest() {
        List<Integer> togeter = Stream.of(asList(1, 2), asList(3, 4))
                .flatMap(numbers -> numbers.stream()).collect(toList());
        check(asList(1, 2, 3, 4).equals(togeter), "结果错误");
    }

    /**
     * max.  List 的 stream() 方法可以从一个 集合中获得 stream对象
     */
    public void maxTest() {
        List<String> strings = asList("1", "12", "123", "1234");
        String shortestString = strings.stream()
                .min(Comparator.comparing(s -> s.length())).get();
        check("1".equals(shortestString),
                "Wrong, do not find shortest string in the list.");
    }

    /**
     * reduce pattern 实现从一组值中生成一个值 Object accumulator = initialValue; for(Object
     * element : collection) { accumulator = combine(accumulator, element); }
     */
    public void reduceTest() {
        int count = Stream.of(1, 2, 3).reduce(0,
                (acc, element) -> acc + element);
        check(6 == count, "Wrong, summary of integer list is not six");
    }

}
