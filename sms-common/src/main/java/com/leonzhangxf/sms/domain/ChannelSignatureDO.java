package com.leonzhangxf.sms.domain;

import java.io.Serializable;
import java.util.Date;

public class ChannelSignatureDO implements Serializable {
    /**
     * 
     */
    private Integer id;

    /**
     * 
     */
    private Integer chnlConfigId;

    /**
     * 签名
     */
    private String signature;

    /**
     * 描述
     */
    private String desc;

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
     * chnl_signature
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
     * 签名
     * @return signature 签名
     */
    public String getSignature() {
        return signature;
    }

    /**
     * 签名
     * @param signature 签名
     */
    public void setSignature(String signature) {
        this.signature = signature == null ? null : signature.trim();
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
        ChannelSignatureDO other = (ChannelSignatureDO) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getChnlConfigId() == null ? other.getChnlConfigId() == null : this.getChnlConfigId().equals(other.getChnlConfigId()))
            && (this.getSignature() == null ? other.getSignature() == null : this.getSignature().equals(other.getSignature()))
            && (this.getDesc() == null ? other.getDesc() == null : this.getDesc().equals(other.getDesc()))
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
        result = prime * result + ((getChnlConfigId() == null) ? 0 : getChnlConfigId().hashCode());
        result = prime * result + ((getSignature() == null) ? 0 : getSignature().hashCode());
        result = prime * result + ((getDesc() == null) ? 0 : getDesc().hashCode());
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
        sb.append(", chnlConfigId=").append(chnlConfigId);
        sb.append(", signature=").append(signature);
        sb.append(", desc=").append(desc);
        sb.append(", status=").append(status);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", deleteFlag=").append(deleteFlag);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}