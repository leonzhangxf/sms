package com.leonzhangxf.sms.domain.dto;

import com.leonzhangxf.sms.domain.SendLogDO;
import com.leonzhangxf.sms.enumeration.SendResponseStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@ApiModel("短信发送记录")
public class SendLogDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("ID")
    private Long id;

    @ApiModelProperty("短信服务模板ID")
    private TemplateDTO template;

    @ApiModelProperty("发送手机号")
    private String mobile;

    @ApiModelProperty("请求参数列表，可能为空")
    private String params;

    @ApiModelProperty("调用响应状态，200-正常，具体参见相关枚举")
    private SendResponseStatus status;

    @ApiModelProperty("响应消息，成功默认\"OK\"，其他可直接返回渠道方信息")
    private String message;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty("发送时间")
    private Date sendTime;

    public static SendLogDTO convertSendLogDO(SendLogDO sendLogDO, TemplateDTO template) {
        if (null == sendLogDO || null == template) return null;
        SendLogDTO sendLogDTO = new SendLogDTO();
        BeanUtils.copyProperties(sendLogDO, sendLogDTO);
        sendLogDTO.setTemplate(template);
        sendLogDTO.setStatus(SendResponseStatus.getSendResponseStatus(sendLogDO.getStatus()));
        return sendLogDTO;
    }

    public static SendLogDO convertToSendLogDO(SendLogDTO sendLogDTO) {
        if (null == sendLogDTO) return null;
        SendLogDO sendLogDO = new SendLogDO();
        BeanUtils.copyProperties(sendLogDTO, sendLogDO);
        sendLogDO.setTemplateId(sendLogDTO.getTemplate().getId());
        sendLogDO.setStatus(sendLogDTO.getStatus().value());
        return sendLogDO;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TemplateDTO getTemplate() {
        return template;
    }

    public void setTemplate(TemplateDTO template) {
        this.template = template;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public SendResponseStatus getStatus() {
        return status;
    }

    public void setStatus(SendResponseStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SendLogDTO that = (SendLogDTO) o;
        return Objects.equals(id, that.id) &&
            Objects.equals(template, that.template) &&
            Objects.equals(mobile, that.mobile) &&
            Objects.equals(params, that.params) &&
            Objects.equals(status, that.status) &&
            Objects.equals(message, that.message) &&
            Objects.equals(sendTime, that.sendTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, template, mobile, params, status, message, sendTime);
    }

    @Override
    public String toString() {
        return "SendLogDTO{" +
            "id=" + id +
            ", template=" + template +
            ", mobile='" + mobile + '\'' +
            ", params='" + params + '\'' +
            ", status=" + status +
            ", message='" + message + '\'' +
            ", sendTime=" + sendTime +
            '}';
    }
}
