package com.leonzhangxf.sms.util;

import com.leonzhangxf.sms.exception.SignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.util.Base64;

/**
 * 加密工具类
 *
 * @author leonzhangxf 20181024
 */
public class EncryptUtils {

    private static Logger logger = LoggerFactory.getLogger(EncryptUtils.class);

    private static final char[] HEX_STRINGS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
        'a', 'b', 'c', 'd', 'e', 'f'};

    private static final String MD5 = "MD5";

    private static final String SHA_1 = "SHA1";

    private static final String SHA_224 = "SHA-224";

    private static final String SHA_256 = "SHA-256";

    private static final String SHA_384 = "SHA-384";

    private static final String SHA_512 = "SHA-512";

    private static final String HMAC_SHA_1 = "HmacSHA1";

    private static final String HMAC_SHA_224 = "HmacSHA224";

    private static final String HMAC_SHA_256 = "HmacSHA256";

    private static final String HMAC_SHA_384 = "HmacSHA384";

    private static final String HMAC_SHA_512 = "HmacSHA512";

    private static final String HMAC_MD5 = "HmacMD5";

    /**
     * PBKDF2
     */
    private static final String PBKDF2_WITH_HMAC_SHA_1 = "PBKDF2WithHmacSHA1";
    private static final String PBKDF2_WITH_HMAC_SHA_256 = "PBKDF2WithHmacSHA256";

    /**
     * 默认迭代次数
     */
    private static final int DEFAULT_ITERATION_TIMES = 100;

    /**
     * key长度
     */
    public static final int KEY_SIZE_128 = 128;
    public static final int KEY_SIZE_256 = 256;
    public static final int KEY_SIZE_512 = 512;

    private static final String CHARSET = "UTF-8";

    public static String md5(String plain) throws SignException {
        return hashEncrypt(plain, MD5);
    }

    public static String sha1(String plain) throws SignException {
        return hashEncrypt(plain, SHA_1);
    }

    public static String sha224(String plain) throws SignException {
        return hashEncrypt(plain, SHA_224);
    }

    public static String sha256(String plain) throws SignException {
        return hashEncrypt(plain, SHA_256);
    }

    public static String sha384(String plain) throws SignException {
        return hashEncrypt(plain, SHA_384);
    }

    public static String sha512(String plain) throws SignException {
        return hashEncrypt(plain, SHA_512);
    }

    public static String hmacSha1(String plain, String key) throws SignException {
        return hmac(plain, key, HMAC_SHA_1);
    }

    public static String hmacSha224(String plain, String key) throws SignException {
        return hmac(plain, key, HMAC_SHA_224);
    }

    public static String hmacSha256(String plain, String key) throws SignException {
        return hmac(plain, key, HMAC_SHA_256);
    }

    public static String hmacSha384(String plain, String key) throws SignException {
        return hmac(plain, key, HMAC_SHA_384);
    }

    public static String hmacSha512(String plain, String key) throws SignException {
        return hmac(plain, key, HMAC_SHA_512);
    }

    public static String hmacMd5(String plain, String key) throws SignException {
        return hmac(plain, key, HMAC_MD5);
    }

    public static String pbkdf2WithHmacSha1(String key, String salt) throws SignException {
        byte[] hash = pbkdf2(key, salt, DEFAULT_ITERATION_TIMES, KEY_SIZE_256, PBKDF2_WITH_HMAC_SHA_1);
        return new String(Base64.getEncoder().encode(hash));
    }

    public static String pbkdf2WithHmacSha1(String key, String salt, int keySize) throws SignException {
        byte[] hash = pbkdf2(key, salt, DEFAULT_ITERATION_TIMES, keySize, PBKDF2_WITH_HMAC_SHA_1);
        return new String(Base64.getEncoder().encode(hash));
    }

    public static String pbkdf2WithHmacSha1(String key, String salt, int iterationTimes, int keySize) throws SignException {
        byte[] hash = pbkdf2(key, salt, iterationTimes, keySize, PBKDF2_WITH_HMAC_SHA_1);
        return new String(Base64.getEncoder().encode(hash));
    }

    public static String pbkdf2WithHmacSha256(String key, String salt) throws SignException {
        byte[] hash = pbkdf2(key, salt, DEFAULT_ITERATION_TIMES, KEY_SIZE_256, PBKDF2_WITH_HMAC_SHA_256);
        return new String(Base64.getEncoder().encode(hash));
    }

    public static String pbkdf2WithHmacSha256(String key, String salt, int keySize) throws SignException {
        byte[] hash = pbkdf2(key, salt, DEFAULT_ITERATION_TIMES, keySize, PBKDF2_WITH_HMAC_SHA_256);
        return new String(Base64.getEncoder().encode(hash));
    }

    public static String pbkdf2WithHmacSha256(String key, String salt, int iterationTimes, int keySize) throws SignException {
        byte[] hash = pbkdf2(key, salt, iterationTimes, keySize, PBKDF2_WITH_HMAC_SHA_256);
        return new String(Base64.getEncoder().encode(hash));
    }

    private static String hashEncrypt(String plain, String encryptType) throws SignException {
        try {
            MessageDigest mdInst = MessageDigest.getInstance(encryptType);

            byte[] plainBytes = plain.getBytes(CHARSET);
            mdInst.update(plainBytes);
            byte[] digests = mdInst.digest();

            return toHexString(digests, EncryptUtils.HEX_STRINGS);
        } catch (Exception ex) {
            logger.error("Signature generate exception.", ex);
            throw new SignException("签名生成异常");
        }
    }

    private static String hmac(String plain, String key, String encryptType) throws SignException {
        try {
            byte[] keyBytes = key.getBytes(CHARSET);
            byte[] text = plain.getBytes(CHARSET);

            //根据给定的字节数组构造一个密钥,第二参数指定一个密钥算法的名称
            SecretKey secretKey = new SecretKeySpec(keyBytes, encryptType);
            //生成一个指定 Mac 算法 的 Mac 对象
            Mac mac = Mac.getInstance(encryptType);

            //用给定密钥初始化 Mac 对象
            mac.init(secretKey);

            //完成 Mac 操作
            byte[] encryptBytes = mac.doFinal(text);
            return toHexString(encryptBytes, HEX_STRINGS);
        } catch (Exception ex) {
            logger.error("Signature generate exception.", ex);
            throw new SignException("签名生成异常");
        }
    }

    private static String toHexString(byte[] encryptBytes, char[] hexStrings) {
        char[] strArr = new char[encryptBytes.length * 2];
        int index = 0;
        for (byte digest : encryptBytes) {
            strArr[index++] = hexStrings[digest >>> 4 & 0xf];
            strArr[index++] = hexStrings[digest & 0xf];
        }

        return new String(strArr);
    }

    private static byte[] pbkdf2(String key, String salt, int iterationTimes, int keySize, String algorithm)
        throws SignException {
        byte[] saltByte = Base64.getEncoder().encode(salt.getBytes());
        try {
            PBEKeySpec spec = new PBEKeySpec(key.toCharArray(), saltByte, iterationTimes, keySize);
            SecretKeyFactory factory = SecretKeyFactory.getInstance(algorithm);
            return factory.generateSecret(spec).getEncoded();
        } catch (GeneralSecurityException ex) {
            logger.error("Signature generate exception.", ex);
            throw new SignException("PBKDF2加密异常");
        }
    }

    public static void main(String[] args) throws SignException {
        System.out.println(md5("123456"));

        System.out.println(sha1("123456"));
        System.out.println(sha224("123456"));
        System.out.println(sha256("123456"));
        System.out.println(sha384("123456"));
        System.out.println(sha512("123456"));

        System.out.println(hmacSha1("123456", "123456"));
        System.out.println(hmacSha224("123456", "123456"));
        System.out.println("HmacSHA256: " + hmacSha256("123456", "123456"));
        System.out.println(hmacSha384("123456", "123456"));
        System.out.println(hmacSha512("123456", "123456"));

        System.out.println(hmacMd5("123456", "123456"));

        System.out.println(pbkdf2WithHmacSha1("123456", "123456"));
        System.out.println(pbkdf2WithHmacSha1("123456", "123456", KEY_SIZE_128));
        System.out.println(pbkdf2WithHmacSha1("123456", "123456", 1000, KEY_SIZE_512));
        System.out.println(pbkdf2WithHmacSha256("123456", "123456"));
        System.out.println(pbkdf2WithHmacSha256("123456", "123456", KEY_SIZE_128));
        System.out.println(pbkdf2WithHmacSha256("123456", "123456", 500, KEY_SIZE_512));
    }
}
