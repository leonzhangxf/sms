package com.leonzhangxf.sms.domain;

import java.io.Serializable;
import java.util.Date;

public class ChannelConfigDO implements Serializable {
    /**
     *
     */
    private Integer id;

    /**
     * 名称
     */
    private String name;

    /**
     * 描述
     */
    private String desc;

    /**
     * 渠道类型，参见具体枚举。0-阿里云
     */
    private Integer type;

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
     * chnl_config
     */
    private static final long serialVersionUID = 1L;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 名称
     *
     * @return name 名称
     */
    public String getName() {
        return name;
    }

    /**
     * 名称
     *
     * @param name 名称
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 描述
     *
     * @return desc 描述
     */
    public String getDesc() {
        return desc;
    }

    /**
     * 描述
     *
     * @param desc 描述
     */
    public void setDesc(String desc) {
        this.desc = desc == null ? null : desc.trim();
    }

    /**
     * 渠道类型，参见具体枚举。0-阿里云
     *
     * @return type 渠道类型，参见具体枚举。0-阿里云
     */
    public Integer getType() {
        return type;
    }

    /**
     * 渠道类型，参见具体枚举。0-阿里云
     *
     * @param type 渠道类型，参见具体枚举。0-阿里云
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 启禁用，0-禁用，1-启用
     *
     * @return status 启禁用，0-禁用，1-启用
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 启禁用，0-禁用，1-启用
     *
     * @param status 启禁用，0-禁用，1-启用
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * @return create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * @return update_time
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * @param updateTime
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 0-未删除，1-已删除
     *
     * @return delete_flag 0-未删除，1-已删除
     */
    public Integer getDeleteFlag() {
        return deleteFlag;
    }

    /**
     * 0-未删除，1-已删除
     *
     * @param deleteFlag 0-未删除，1-已删除
     */
    public void setDeleteFlag(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    /**
     * @mbg.generated 2018-09-03
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
        ChannelConfigDO other = (ChannelConfigDO) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
                && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
                && (this.getDesc() == null ? other.getDesc() == null : this.getDesc().equals(other.getDesc()))
                && (this.getType() == null ? other.getType() == null : this.getType().equals(other.getType()))
                && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
                && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
                && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
                && (this.getDeleteFlag() == null ? other.getDeleteFlag() == null : this.getDeleteFlag().equals(other.getDeleteFlag()));
    }

    /**
     * @mbg.generated 2018-09-03
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getDesc() == null) ? 0 : getDesc().hashCode());
        result = prime * result + ((getType() == null) ? 0 : getType().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getDeleteFlag() == null) ? 0 : getDeleteFlag().hashCode());
        return result;
    }

    /**
     * @mbg.generated 2018-09-03
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", name=").append(name);
        sb.append(", desc=").append(desc);
        sb.append(", type=").append(type);
        sb.append(", status=").append(status);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", deleteFlag=").append(deleteFlag);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}