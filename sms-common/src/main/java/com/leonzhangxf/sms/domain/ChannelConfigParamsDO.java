package com.leonzhangxf.sms.domain;

import java.io.Serializable;
import java.util.Date;

public class ChannelConfigParamsDO implements Serializable {
    /**
     * 
     */
    private Integer id;

    /**
     * 
     */
    private Integer chnlConfigId;

    /**
     * 配置参数键，参见相关枚举
     */
    private String key;

    /**
     * 配置键对应值
     */
    private String value;

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
     * chnl_config_params
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
     * 配置参数键，参见相关枚举
     * @return key 配置参数键，参见相关枚举
     */
    public String getKey() {
        return key;
    }

    /**
     * 配置参数键，参见相关枚举
     * @param key 配置参数键，参见相关枚举
     */
    public void setKey(String key) {
        this.key = key == null ? null : key.trim();
    }

    /**
     * 配置键对应值
     * @return value 配置键对应值
     */
    public String getValue() {
        return value;
    }

    /**
     * 配置键对应值
     * @param value 配置键对应值
     */
    public void setValue(String value) {
        this.value = value == null ? null : value.trim();
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
        ChannelConfigParamsDO other = (ChannelConfigParamsDO) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getChnlConfigId() == null ? other.getChnlConfigId() == null : this.getChnlConfigId().equals(other.getChnlConfigId()))
            && (this.getKey() == null ? other.getKey() == null : this.getKey().equals(other.getKey()))
            && (this.getValue() == null ? other.getValue() == null : this.getValue().equals(other.getValue()))
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
        result = prime * result + ((getKey() == null) ? 0 : getKey().hashCode());
        result = prime * result + ((getValue() == null) ? 0 : getValue().hashCode());
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
        sb.append(", key=").append(key);
        sb.append(", value=").append(value);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", deleteFlag=").append(deleteFlag);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}