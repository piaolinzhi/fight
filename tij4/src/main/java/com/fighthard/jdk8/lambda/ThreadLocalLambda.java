package com.fighthard.jdk8.lambda;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.function.Predicate;

import javax.swing.*;

/**
 * Created by piaolinzhi on 2016/11/27.
 */
public class ThreadLocalLambda {
    // Supplier<DateFormat> dateFormatSupplier = () -> new SimpleDateFormat();
    // ThreadLocal 的新的工厂方法，可以接受一个 Supplier Lambda 表达式,通过这种写法，DateFormat 是线程安全的。
    static ThreadLocal<DateFormat> safeDateFormat = ThreadLocal
            .withInitial(() -> new SimpleDateFormat());

    public static void main(String[] args) {
        Date date = new Date();
        System.out.println(safeDateFormat.get().format(date));

        Runnable helloworld = () -> System.out.println("Hello, world!");
        helloworld.run();

        JButton button = new JButton();
        button.addActionListener(
                event -> System.out.println(event.getActionCommand()));

        ThreadLocalLambda tll = new ThreadLocalLambda();
        /*
         * Ambiguous method!!!! boolean result = tll.check(x -> x > 5);
         */

    }

    boolean check(Predicate<Integer> predicate) {
        return true;
    }

    boolean check(IntPre predicate) {
        return false;
    }
}
