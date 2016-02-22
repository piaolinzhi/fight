package com.fighthard.util;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.UUID;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * AES 加密工具类.
 * 
 * @author plz
 *
 */
public class IdHandler {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(IdHandler.class);
    // 进行AES加密时使用的密码.
    private static final String KEY = "ucmed";

    /**
     * AES算法加密
     * 
     * @param content
     *            需要加密的内容
     * @param password
     *            加密密码
     * @return 加密后的二进制数组或者null.
     */
    private static byte[] encrypt(String content, String password) {
        if(StringUtils.isBlank(content) || StringUtils.isBlank(password)) {
            return null;
        } else {
            content = content.trim();
            password = password.trim();
        }
        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            kgen.init(128, new SecureRandom(password.getBytes()));
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            Cipher cipher = Cipher.getInstance("AES");// 创建密码器
            byte[] byteContent = content.getBytes("utf-8");
            cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
            byte[] result = cipher.doFinal(byteContent);
            return result; // 加密
        } catch(NoSuchAlgorithmException e) {
            LOGGER.error(e.getMessage(), e);
        } catch(NoSuchPaddingException e) {
            LOGGER.error(e.getMessage(), e);
        } catch(InvalidKeyException e) {
            LOGGER.error(e.getMessage(), e);
        } catch(UnsupportedEncodingException e) {
            LOGGER.error(e.getMessage(), e);
        } catch(IllegalBlockSizeException e) {
            LOGGER.error(e.getMessage(), e);
        } catch(BadPaddingException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * AES解密.
     * 
     * @param content
     *            待解密内容
     * @param password
     *            解密密钥
     * @return 解密后的二进制数组或者null.
     */
    private static byte[] decrypt(byte[] content, String password) {
        if(null == content || StringUtils.isBlank(password)) {
            return null;
        }
        password = password.trim();
        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            kgen.init(128, new SecureRandom(password.getBytes()));
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            Cipher cipher = Cipher.getInstance("AES");// 创建密码器
            cipher.init(Cipher.DECRYPT_MODE, key);// 初始化
            byte[] result = cipher.doFinal(content);
            return result; // 加密
        } catch(NoSuchAlgorithmException e) {
            LOGGER.error(e.getMessage(), e);
        } catch(NoSuchPaddingException e) {
            LOGGER.error(e.getMessage(), e);
        } catch(InvalidKeyException e) {
            LOGGER.error(e.getMessage(), e);
        } catch(IllegalBlockSizeException e) {
            LOGGER.error(e.getMessage(), e);
        } catch(BadPaddingException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 将二进制数据转换成 url 友好的字符串，采用base64算法.
     * 
     * @param content
     *            待加密的二进制数据.
     * @return url友好的字符串或者null.
     */
    private static String encodeBase64URLSafe(byte[] content) {
        if(null == content || 0 >= content.length) {
            return null;
        }
        String result = Base64.encodeBase64URLSafeString(content);
        return result;
    }

    /**
     * 将base64编码的字符串还原成二进制数据.
     * 
     * @param content
     *            base64编码的字符串.
     * @return 还原后的二进制的数组数据或者null.
     */
    private static byte[] decodeBase64URLSafe(String content) {
        if(StringUtils.isBlank(content)) {
            return null;
        } else {
            content = content.trim();
        }
        byte[] result = Base64.decodeBase64(content);
        return result;
    }

    /**
     * 对id进行加密,加密后的数据是url友好的，长度根据id的长度而变化. uuid加密后的字符串长度为64.
     * 
     * @param id
     *            id
     * @return 加密后的字符串或者null.
     */
    public static String idEncrypt(String id) {
        if(StringUtils.isBlank(id)) {
            return null;
        }
        byte[] encryptResult = encrypt(id, KEY);
        String base64Encode = encodeBase64URLSafe(encryptResult);
        return base64Encode;
    }

    /**
     * 对加密的id信息进行解密.
     * 
     * @param dtoId
     *            加密后的id信息.
     * @return 解密后的数据或者空串.
     */
    public static String idDecrypt(String dtoId) {
        if(StringUtils.isBlank(dtoId)) {
            return null;
        }
        byte[] base64Decode = decodeBase64URLSafe(dtoId);
        byte[] decryptResult = decrypt(base64Decode, KEY);
        String decryptContent = null;
        if(null == decryptResult || 0 >= decryptResult.length) {
            return null;
        }
        decryptContent = new String(decryptResult);
        return decryptContent;
    }

    public static void main(String[] args) {
        Long start = System.currentTimeMillis();
        int itratorNumber = 1;
        for(int i = 0; i < itratorNumber; i++) {
            String content = UUID.randomUUID().toString();
            String idEncrypted = idEncrypt(content);
            // System.out.println(idEncrypted);
            // System.out.println("The length of the str is :"
            // + idEncrypted.length());
            String decryptContent = idDecrypt(idEncrypted);
            // System.out.println("解密后：" + decryptContent);
            if(!content.equals(decryptContent)) {
                System.out.println("加解密失败");
            }
        }
        Long end = System.currentTimeMillis();
        double time = end - start;
        System.out.println("共使用时间为：" + time + "ms,平均加密耗时为：" + time
                / itratorNumber + "ms");

        String errorString = "$%^&*(";
        String decryptedErrorString = idDecrypt(errorString);
        System.out
                .println("decrypted error string is :" + decryptedErrorString);

    }
}
