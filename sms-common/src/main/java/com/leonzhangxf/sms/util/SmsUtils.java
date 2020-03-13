package com.leonzhangxf.sms.util;

import com.leonzhangxf.sms.exception.SignException;
import org.springframework.util.StringUtils;

/**
 * 短信服务签名工具类
 *
 * @author leonzhangxf 20181024
 */
public class SmsUtils {

    private static final String SEPARATOR = "_";

    /**
     * V1 版本签名
     * <p>
     * mobile_templateId_timestamp
     * <p>
     * 采用 HmacSHA256 算法生成签名
     *
     * @return 签名
     */
    public static String signV1(String key, String mobile, String templateId, String timestamp) throws SignException {
        if (!StringUtils.hasText(key) || !StringUtils.hasText(mobile) || !StringUtils.hasText(templateId)
                || !StringUtils.hasText(timestamp)) {
            throw new SignException("签名参数不完备，无法签名！");
        }
        String plainString = concatEncryptString(mobile, templateId, timestamp);
        return EncryptUtils.hmacSha256(plainString, key);
    }

    /**
     * 按照签名规则拼接待签名字符串
     */
    private static String concatEncryptString(String... elements) throws SignException {
        if (null == elements || elements.length <= 0) throw new SignException("没有检测到待签名参数！");

        StringBuilder encryptString = new StringBuilder();
        for (String element : elements) {
            encryptString.append(element).append(SEPARATOR);
        }
        return encryptString.substring(0, encryptString.length() - 1);
    }
}
