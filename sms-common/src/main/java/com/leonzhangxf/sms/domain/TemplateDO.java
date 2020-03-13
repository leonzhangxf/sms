package com.leonzhangxf.sms.domain;

import java.io.Serializable;
import java.util.Date;

public class TemplateDO implements Serializable {
    /**
     * 
     */
    private Integer id;

    /**
     * 
     */
    private Integer clientId;

    /**
     * 
     */
    private Integer chnlSignatureId;

    /**
     * 
     */
    private Integer chnlTemplateId;

    /**
     * 名称
     */
    private String name;

    /**
     * 短信服务模板ID
     */
    private String templateId;

    /**
     * 描述
     */
    private String desc;

    /**
     * 用途：1-验证，2-通知，3-推广
     */
    private Integer usage;

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
     * template
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
     * @return client_id 
     */
    public Integer getClientId() {
        return clientId;
    }

    /**
     * 
     * @param clientId 
     */
    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    /**
     * 
     * @return chnl_signature_id 
     */
    public Integer getChnlSignatureId() {
        return chnlSignatureId;
    }

    /**
     * 
     * @param chnlSignatureId 
     */
    public void setChnlSignatureId(Integer chnlSignatureId) {
        this.chnlSignatureId = chnlSignatureId;
    }

    /**
     * 
     * @return chnl_template_id 
     */
    public Integer getChnlTemplateId() {
        return chnlTemplateId;
    }

    /**
     * 
     * @param chnlTemplateId 
     */
    public void setChnlTemplateId(Integer chnlTemplateId) {
        this.chnlTemplateId = chnlTemplateId;
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
     * 短信服务模板ID
     * @return template_id 短信服务模板ID
     */
    public String getTemplateId() {
        return templateId;
    }

    /**
     * 短信服务模板ID
     * @param templateId 短信服务模板ID
     */
    public void setTemplateId(String templateId) {
        this.templateId = templateId == null ? null : templateId.trim();
    }

    /**
     * 描述
     * @return desc 描述
     */
    public String getDesc() {
        return desc;
    }

    /**
     * 描述
     * @param desc 描述
     */
    public void setDesc(String desc) {
        this.desc = desc == null ? null : desc.trim();
    }

    /**
     * 用途：1-验证，2-通知，3-推广
     * @return usage 用途：1-验证，2-通知，3-推广
     */
    public Integer getUsage() {
        return usage;
    }

    /**
     * 用途：1-验证，2-通知，3-推广
     * @param usage 用途：1-验证，2-通知，3-推广
     */
    public void setUsage(Integer usage) {
        this.usage = usage;
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
     * @mbg.generated 2018-09-05
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
        TemplateDO other = (TemplateDO) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getClientId() == null ? other.getClientId() == null : this.getClientId().equals(other.getClientId()))
            && (this.getChnlSignatureId() == null ? other.getChnlSignatureId() == null : this.getChnlSignatureId().equals(other.getChnlSignatureId()))
            && (this.getChnlTemplateId() == null ? other.getChnlTemplateId() == null : this.getChnlTemplateId().equals(other.getChnlTemplateId()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getTemplateId() == null ? other.getTemplateId() == null : this.getTemplateId().equals(other.getTemplateId()))
            && (this.getDesc() == null ? other.getDesc() == null : this.getDesc().equals(other.getDesc()))
            && (this.getUsage() == null ? other.getUsage() == null : this.getUsage().equals(other.getUsage()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getDeleteFlag() == null ? other.getDeleteFlag() == null : this.getDeleteFlag().equals(other.getDeleteFlag()));
    }

    /**
     *
     * @mbg.generated 2018-09-05
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getClientId() == null) ? 0 : getClientId().hashCode());
        result = prime * result + ((getChnlSignatureId() == null) ? 0 : getChnlSignatureId().hashCode());
        result = prime * result + ((getChnlTemplateId() == null) ? 0 : getChnlTemplateId().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getTemplateId() == null) ? 0 : getTemplateId().hashCode());
        result = prime * result + ((getDesc() == null) ? 0 : getDesc().hashCode());
        result = prime * result + ((getUsage() == null) ? 0 : getUsage().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getDeleteFlag() == null) ? 0 : getDeleteFlag().hashCode());
        return result;
    }

    /**
     *
     * @mbg.generated 2018-09-05
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", clientId=").append(clientId);
        sb.append(", chnlSignatureId=").append(chnlSignatureId);
        sb.append(", chnlTemplateId=").append(chnlTemplateId);
        sb.append(", name=").append(name);
        sb.append(", templateId=").append(templateId);
        sb.append(", desc=").append(desc);
        sb.append(", usage=").append(usage);
        sb.append(", status=").append(status);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", deleteFlag=").append(deleteFlag);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}