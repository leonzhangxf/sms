package com.leonzhangxf.sms.util;

import java.util.Random;

/**
 * 密钥生成工具类，可选，可以自己填写。
 *
 * @author leonzhangxf 20180906
 */
public class KeyGenerateUtils {

    private static final Random random = new Random();

    private static final String[] elements = {
            "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m",
            "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z",
            "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M",
            "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"
    };

    /**
     * 生成密钥
     */
    public static String generateKey() {
        StringBuilder elementStr = new StringBuilder();
        for (int i = 0; i < 32; i++) {
            elementStr.append(elements[random.nextInt(52)]);
        }
        return elementStr.toString();
    }

    public static void main(String[] args) {
        System.out.println(generateKey());
    }
}
