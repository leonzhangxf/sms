package com.leonzhangxf.sms.constant;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("sms.auth")
public class SmsAuthConstant {

    /**
     * Default time difference.
     * 30000ms
     */
    private static final Integer DEFAULT_TIME_DIFFERENCE = 30000;

    /**
     * Time difference limit between request time and the time server resolve request.
     */
    private Integer timeDifference = DEFAULT_TIME_DIFFERENCE;

    public Integer getTimeDifference() {
        return timeDifference;
    }

    public void setTimeDifference(Integer timeDifference) {
        this.timeDifference = timeDifference;
    }
}
