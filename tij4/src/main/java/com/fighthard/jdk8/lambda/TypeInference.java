package com.fighthard.jdk8.lambda;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BinaryOperator;

/**
 * Created by piaolinzhi on 2016/11/27. Java8 类型推断 type inference
 */
public class TypeInference {

    public static void main(String[] args) {
        // 这里不用写 new HashMap<String,Object>(),Java 编译器会做类型推断 ，from java7
        Map<String, Object> stringMaps = new HashMap<>();

        BinaryOperator<Long> addLongs = (x, y) -> x + y;

    }

}
