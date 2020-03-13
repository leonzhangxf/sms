package com.leonzhangxf.sms.domain.dto;

import com.leonzhangxf.sms.domain.ChannelConfigDO;
import com.leonzhangxf.sms.enumeration.ChannelConfigType;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * 渠道配置
 *
 * @author leonzhangxf 20180903
 */
@ApiModel("渠道配置")
public class ChannelConfigDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("ID")
    private Integer id;

    @ApiModelProperty(value = "名称", required = true)
    private String name;

    @ApiModelProperty("描述")
    private String desc;

    @ApiModelProperty(value = "渠道配置类型", required = true)
    private ChannelConfigType type;

    @ApiModelProperty(value = "启禁用，0-禁用，1-启用", required = true)
    private Integer status = 0;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(hidden = true)
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(hidden = true)
    private Date updateTime;

    @ApiModelProperty(value = "0-未删除，1-已删除", hidden = true)
    private Integer deleteFlag;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public ChannelConfigType getType() {
        return type;
    }

    public void setType(ChannelConfigType type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public static ChannelConfigDTO convertChannelConfigDO(ChannelConfigDO channelConfigDO) {
        if (null == channelConfigDO) return null;
        ChannelConfigDTO channelConfigDTO = new ChannelConfigDTO();
        BeanUtils.copyProperties(channelConfigDO, channelConfigDTO);
        channelConfigDTO.setType(ChannelConfigType.getConfigType(channelConfigDO.getType()));
        return channelConfigDTO;
    }

    public static ChannelConfigDO convertToChannelConfigDO(ChannelConfigDTO channelConfigDTO) {
        if (null == channelConfigDTO) return null;
        ChannelConfigDO channelConfigDO = new ChannelConfigDO();
        BeanUtils.copyProperties(channelConfigDTO, channelConfigDO);
        channelConfigDO.setType(channelConfigDTO.getType().getType());
        channelConfigDO.setCreateTime(null);
        channelConfigDO.setUpdateTime(null);
        return channelConfigDO;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChannelConfigDTO that = (ChannelConfigDTO) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(desc, that.desc) &&
                type == that.type &&
                Objects.equals(status, that.status) &&
                Objects.equals(deleteFlag, that.deleteFlag);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, desc, type, status, deleteFlag);
    }

    @Override
    public String toString() {
        return "ChannelConfigDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                ", type=" + type +
                ", status=" + status +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", deleteFlag=" + deleteFlag +
                '}';
    }
}
