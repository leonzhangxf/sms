package com.leonzhangxf.sms.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Objects;

@ApiModel("短信发送V1参数列表")
public class MessageV1DTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "手机号", required = true)
    @NotEmpty(message = "手机号不能为空")
    @Pattern(regexp = "[0-9]{11}", message = "手机号格式至少为11位数字")
    private String mobile;

    @ApiModelProperty(value = "短信服务提供的短信模板ID", required = true)
    @NotEmpty(message = "短信服务模板ID不能为空")
    private String templateId;

    @ApiModelProperty(value = "短信模板参数映射，为映射的JSON格式字符串。" +
            "例: {\"name\":\"Leon\", \"age\":20, \"desc\": null, \"report\": false}")
    private String params;

    @ApiModelProperty(value = "时间戳，毫秒值", required = true)
    @NotEmpty(message = "时间戳参数不能为空")
    @Pattern(regexp = "[0-9]+", message = "时间戳参数格式异常")
    private String timestamp;

    @ApiModelProperty(value = "短信发送请求签名", required = true)
    @NotEmpty(message = "短信签名不能为空")
    private String sign;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MessageV1DTO that = (MessageV1DTO) o;
        return Objects.equals(mobile, that.mobile) &&
                Objects.equals(templateId, that.templateId) &&
                Objects.equals(params, that.params) &&
                Objects.equals(timestamp, that.timestamp) &&
                Objects.equals(sign, that.sign);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mobile, templateId, params, timestamp, sign);
    }

    @Override
    public String toString() {
        return "MessageV1DTO{" +
                "mobile='" + mobile + '\'' +
                ", templateId='" + templateId + '\'' +
                ", params='" + params + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", sign='" + sign + '\'' +
                '}';
    }
}
