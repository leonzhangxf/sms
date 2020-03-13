package com.leonzhangxf.sms.message;

import java.io.Serializable;
import java.util.Objects;

/**
 * 短信发送请求上下文
 *
 * @author leonzhangxf
 */
public class MessageContext implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 短信发送请求
     */
    private MessageRequest request;

    /**
     * 短信发送请求响应
     */
    private MessageResponse response;

    public MessageRequest getRequest() {
        return request;
    }

    public void setRequest(MessageRequest request) {
        this.request = request;
    }

    public MessageResponse getResponse() {
        return response;
    }

    public void setResponse(MessageResponse response) {
        this.response = response;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MessageContext context = (MessageContext) o;
        return Objects.equals(request, context.request) &&
                Objects.equals(response, context.response);
    }

    @Override
    public int hashCode() {
        return Objects.hash(request, response);
    }

    @Override
    public String toString() {
        return "MessageContext{" +
                "request=" + request +
                ", response=" + response +
                '}';
    }
}
