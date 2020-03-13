package com.leonzhangxf.sms.domain;

import java.io.Serializable;
import java.util.Date;

public class SendLogDO implements Serializable {
    /**
     * 
     */
    private Long id;

    /**
     * 
     */
    private Integer templateId;

    /**
     * 发送手机号
     */
    private String mobile;

    /**
     * 请求参数列表，可能为空
     */
    private String params;

    /**
     * 调用响应状态，200-正常，具体参见相关枚举
     */
    private Integer status;

    /**
     * 响应消息，成功默认"OK"，其他可直接返回渠道方信息
     */
    private String message;

    /**
     * 发送时间
     */
    private Date sendTime;

    /**
     * send_log
     */
    private static final long serialVersionUID = 1L;

    /**
     * 
     * @return id 
     */
    public Long getId() {
        return id;
    }

    /**
     * 
     * @param id 
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 
     * @return template_id 
     */
    public Integer getTemplateId() {
        return templateId;
    }

    /**
     * 
     * @param templateId 
     */
    public void setTemplateId(Integer templateId) {
        this.templateId = templateId;
    }

    /**
     * 发送手机号
     * @return mobile 发送手机号
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * 发送手机号
     * @param mobile 发送手机号
     */
    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    /**
     * 请求参数列表，可能为空
     * @return params 请求参数列表，可能为空
     */
    public String getParams() {
        return params;
    }

    /**
     * 请求参数列表，可能为空
     * @param params 请求参数列表，可能为空
     */
    public void setParams(String params) {
        this.params = params == null ? null : params.trim();
    }

    /**
     * 调用响应状态，200-正常，具体参见相关枚举
     * @return status 调用响应状态，200-正常，具体参见相关枚举
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 调用响应状态，200-正常，具体参见相关枚举
     * @param status 调用响应状态，200-正常，具体参见相关枚举
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 响应消息，成功默认"OK"，其他可直接返回渠道方信息
     * @return message 响应消息，成功默认"OK"，其他可直接返回渠道方信息
     */
    public String getMessage() {
        return message;
    }

    /**
     * 响应消息，成功默认"OK"，其他可直接返回渠道方信息
     * @param message 响应消息，成功默认"OK"，其他可直接返回渠道方信息
     */
    public void setMessage(String message) {
        this.message = message == null ? null : message.trim();
    }

    /**
     * 发送时间
     * @return send_time 发送时间
     */
    public Date getSendTime() {
        return sendTime;
    }

    /**
     * 发送时间
     * @param sendTime 发送时间
     */
    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    /**
     *
     * @mbg.generated 2018-08-28
     */
    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        SendLogDO other = (SendLogDO) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getTemplateId() == null ? other.getTemplateId() == null : this.getTemplateId().equals(other.getTemplateId()))
            && (this.getMobile() == null ? other.getMobile() == null : this.getMobile().equals(other.getMobile()))
            && (this.getParams() == null ? other.getParams() == null : this.getParams().equals(other.getParams()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getMessage() == null ? other.getMessage() == null : this.getMessage().equals(other.getMessage()))
            && (this.getSendTime() == null ? other.getSendTime() == null : this.getSendTime().equals(other.getSendTime()));
    }

    /**
     *
     * @mbg.generated 2018-08-28
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getTemplateId() == null) ? 0 : getTemplateId().hashCode());
        result = prime * result + ((getMobile() == null) ? 0 : getMobile().hashCode());
        result = prime * result + ((getParams() == null) ? 0 : getParams().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getMessage() == null) ? 0 : getMessage().hashCode());
        result = prime * result + ((getSendTime() == null) ? 0 : getSendTime().hashCode());
        return result;
    }

    /**
     *
     * @mbg.generated 2018-08-28
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", templateId=").append(templateId);
        sb.append(", mobile=").append(mobile);
        sb.append(", params=").append(params);
        sb.append(", status=").append(status);
        sb.append(", message=").append(message);
        sb.append(", sendTime=").append(sendTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}