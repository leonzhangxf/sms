package com.leonzhangxf.sms.message;

import com.leonzhangxf.sms.enumeration.SendResponseStatus;

import java.io.Serializable;
import java.util.Objects;

/**
 * 短信发送请求响应
 *
 * @author leonzhangxf
 */
public class MessageResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private SendResponseStatus responseStatus;

    private String responseMessage;

    private MessageResponse() {
    }

    private MessageResponse(SendResponseStatus responseStatus, String responseMessage) {
        super();
        this.responseStatus = responseStatus;
        this.responseMessage = responseMessage;
    }

    public static MessageResponse newMessageResponse(SendResponseStatus responseStatus,
                                                     String responseMessage) {
        return new MessageResponse(responseStatus, responseMessage);
    }

    public SendResponseStatus getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(SendResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MessageResponse that = (MessageResponse) o;
        return responseStatus == that.responseStatus &&
                Objects.equals(responseMessage, that.responseMessage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(responseStatus, responseMessage);
    }

    @Override
    public String toString() {
        return "MessageResponse{" +
                "responseStatus=" + responseStatus +
                ", responseMessage='" + responseMessage + '\'' +
                '}';
    }
}
