package com.leonzhangxf.sms.domain;

import java.io.Serializable;
import java.util.Date;

public class ChannelTemplateDO implements Serializable {
    /**
     * 
     */
    private Integer id;

    /**
     * 
     */
    private Integer chnlConfigId;

    /**
     * 名称
     */
    private String name;

    /**
     * 渠道短信模板code，如阿里云短信模板CODE
     */
    private String code;

    /**
     * 渠道短信模板内容，携带变量使用“${code}”的格式
     */
    private String content;

    /**
     * 短信模板参数列表,多变量按顺序用英文逗号分隔,如"name,vcode"。无变量使用空字符。
     */
    private String params;

    /**
     * 启禁用，0-禁用，1-启用
     */
    private Integer status;

    /**
     * 
     */
    private Date createTime;

    /**
     * 
     */
    private Date updateTime;

    /**
     * 0-未删除，1-已删除
     */
    private Integer deleteFlag;

    /**
     * chnl_template
     */
    private static final long serialVersionUID = 1L;

    /**
     * 
     * @return id 
     */
    public Integer getId() {
        return id;
    }

    /**
     * 
     * @param id 
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 
     * @return chnl_config_id 
     */
    public Integer getChnlConfigId() {
        return chnlConfigId;
    }

    /**
     * 
     * @param chnlConfigId 
     */
    public void setChnlConfigId(Integer chnlConfigId) {
        this.chnlConfigId = chnlConfigId;
    }

    /**
     * 名称
     * @return name 名称
     */
    public String getName() {
        return name;
    }

    /**
     * 名称
     * @param name 名称
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 渠道短信模板code，如阿里云短信模板CODE
     * @return code 渠道短信模板code，如阿里云短信模板CODE
     */
    public String getCode() {
        return code;
    }

    /**
     * 渠道短信模板code，如阿里云短信模板CODE
     * @param code 渠道短信模板code，如阿里云短信模板CODE
     */
    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    /**
     * 渠道短信模板内容，携带变量使用“${code}”的格式
     * @return content 渠道短信模板内容，携带变量使用“${code}”的格式
     */
    public String getContent() {
        return content;
    }

    /**
     * 渠道短信模板内容，携带变量使用“${code}”的格式
     * @param content 渠道短信模板内容，携带变量使用“${code}”的格式
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    /**
     * 短信模板参数列表,多变量按顺序用英文逗号分隔,如"name,vcode"。无变量使用空字符。
     * @return params 短信模板参数列表,多变量按顺序用英文逗号分隔,如"name,vcode"。无变量使用空字符。
     */
    public String getParams() {
        return params;
    }

    /**
     * 短信模板参数列表,多变量按顺序用英文逗号分隔,如"name,vcode"。无变量使用空字符。
     * @param params 短信模板参数列表,多变量按顺序用英文逗号分隔,如"name,vcode"。无变量使用空字符。
     */
    public void setParams(String params) {
        this.params = params == null ? null : params.trim();
    }

    /**
     * 启禁用，0-禁用，1-启用
     * @return status 启禁用，0-禁用，1-启用
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 启禁用，0-禁用，1-启用
     * @param status 启禁用，0-禁用，1-启用
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 
     * @return create_time 
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 
     * @param createTime 
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 
     * @return update_time 
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 
     * @param updateTime 
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 0-未删除，1-已删除
     * @return delete_flag 0-未删除，1-已删除
     */
    public Integer getDeleteFlag() {
        return deleteFlag;
    }

    /**
     * 0-未删除，1-已删除
     * @param deleteFlag 0-未删除，1-已删除
     */
    public void setDeleteFlag(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
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
        ChannelTemplateDO other = (ChannelTemplateDO) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getChnlConfigId() == null ? other.getChnlConfigId() == null : this.getChnlConfigId().equals(other.getChnlConfigId()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getCode() == null ? other.getCode() == null : this.getCode().equals(other.getCode()))
            && (this.getContent() == null ? other.getContent() == null : this.getContent().equals(other.getContent()))
            && (this.getParams() == null ? other.getParams() == null : this.getParams().equals(other.getParams()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getDeleteFlag() == null ? other.getDeleteFlag() == null : this.getDeleteFlag().equals(other.getDeleteFlag()));
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
        result = prime * result + ((getChnlConfigId() == null) ? 0 : getChnlConfigId().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getCode() == null) ? 0 : getCode().hashCode());
        result = prime * result + ((getContent() == null) ? 0 : getContent().hashCode());
        result = prime * result + ((getParams() == null) ? 0 : getParams().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getDeleteFlag() == null) ? 0 : getDeleteFlag().hashCode());
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
        sb.append(", chnlConfigId=").append(chnlConfigId);
        sb.append(", name=").append(name);
        sb.append(", code=").append(code);
        sb.append(", content=").append(content);
        sb.append(", params=").append(params);
        sb.append(", status=").append(status);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", deleteFlag=").append(deleteFlag);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}