package com.leonzhangxf.sms.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * 短信服务模板ID生成器
 *
 * @author leonzhangxf 20180906
 */
public class TemplateIdUtils {

    /**
     * 前缀
     */
    private static final String prefix = "SMS";

    private static final Random random = new Random();

    private static final String[] elements = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
            "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m",
            "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z",
            "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M",
            "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};

    private static String getExtendsStr() {
        StringBuilder extendsStr = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            extendsStr.append(elements[random.nextInt(62)]);
        }
        return extendsStr.toString();
    }

    /**
     * 生成模板ID
     */
    public static String generateTemplateId() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        return prefix + format.format(new Date()) + getExtendsStr();
    }

    public static void main(String[] args) {
        long start = new Date().getTime();
        System.out.println(generateTemplateId());
        long end = new Date().getTime();
        System.out.println(end - start);
    }
}
