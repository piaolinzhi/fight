package com.fighthard.staticmethod;

public class ClassUnderTest {
    /**
     * 需测试的方法.
     * @return 测试返回生成的id.
     */
    public String methodToTest() {
        final String id = IdGenerator.generateNewId();
        return id;
    }

}
