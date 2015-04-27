package com.fighthard.staticmethod;

import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;
import static org.powermock.api.mockito.PowerMockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * 静态方法Mock测试. 1.类之前加两个注释
 * 
 * @author plz
 *
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(IdGenerator.class)
public class StaticMethodTest {
    @Test
    public void demoStaticMethodMocking() throws Exception {
        mockStatic(IdGenerator.class);
        /*
         * Setup the expectation using the standard Mockito syntax,
         * generateNewId() will now return 2 everytime it's invoked in this
         * test.
         */
        when(IdGenerator.generateNewId()).thenReturn("mock static");

        String result = new ClassUnderTest().methodToTest();
        System.out.println(result);
        // Optionally verify that the static method was actually called
        verifyStatic();
        String s=IdGenerator.generateNewId();
        System.out.println(s);
    }
}
