package com.fighthard.staticmethod;

import java.util.UUID;
/**
 * 返回一个随机的UUID做为Id.
 * @author plz
 *
 */
public class IdGenerator {
    /**
     * 生成Id.
     * @return id.
     */
    public static String generateNewId() {
        return UUID.randomUUID().toString();
    }

}
