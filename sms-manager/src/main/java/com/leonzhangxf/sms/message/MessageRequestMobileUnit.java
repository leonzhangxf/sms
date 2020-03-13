package com.leonzhangxf.sms.message;

import java.io.Serializable;
import java.util.Map;
import java.util.Objects;

/**
 * 发送短信请求一个手机请求单元
 *
 * @author leonzhangxf
 */
public class MessageRequestMobileUnit implements Serializable {

    private static final long serialVersionUID = 1L;

    private String mobile;

    private Map<String, String> params;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MessageRequestMobileUnit that = (MessageRequestMobileUnit) o;
        return Objects.equals(mobile, that.mobile) &&
                Objects.equals(params, that.params);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mobile, params);
    }

    @Override
    public String toString() {
        return "MessageRequestMobileUnit{" +
                "mobile='" + mobile + '\'' +
                ", params=" + params +
                '}';
    }
}
