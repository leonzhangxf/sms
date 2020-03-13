package com.leonzhangxf.sms.domain.dto;

import com.leonzhangxf.sms.domain.ChannelSignatureDO;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * 渠道签名
 *
 * @author leonzhangxf 20180905
 */
@ApiModel("渠道签名")
public class ChannelSignatureDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("ID")
    private Integer id;

    @ApiModelProperty(value = "渠道配置，在提交修改和保存时，只需要传递其ID即可", required = true)
    private ChannelConfigDTO channelConfig;

    @ApiModelProperty(value = "签名", required = true)
    private String signature;

    @ApiModelProperty("描述")
    private String desc;

    @ApiModelProperty(value = "启禁用，0-禁用，1-启用", required = true)
    private Integer status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(hidden = true)
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(hidden = true)
    private Date updateTime;

    @ApiModelProperty(value = "0-未删除，1-已删除", hidden = true)
    private Integer deleteFlag;

    public static ChannelSignatureDTO convertChannelSignatureDO(ChannelSignatureDO channelSignatureDO, ChannelConfigDTO channelConfig) {
        if (null == channelSignatureDO || null == channelConfig) return null;
        ChannelSignatureDTO channelSignatureDTO = new ChannelSignatureDTO();
        BeanUtils.copyProperties(channelSignatureDO, channelSignatureDTO);
        channelSignatureDTO.setChannelConfig(channelConfig);
        return channelSignatureDTO;
    }

    public static ChannelSignatureDO convertToChannelSignatureDO(ChannelSignatureDTO channelSignatureDTO) {
        if (null == channelSignatureDTO) return null;
        ChannelSignatureDO channelSignatureDO = new ChannelSignatureDO();
        BeanUtils.copyProperties(channelSignatureDTO, channelSignatureDO);
        channelSignatureDO.setChnlConfigId(channelSignatureDTO.getChannelConfig().getId());
        channelSignatureDO.setCreateTime(null);
        channelSignatureDO.setUpdateTime(null);
        return channelSignatureDO;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ChannelConfigDTO getChannelConfig() {
        return channelConfig;
    }

    public void setChannelConfig(ChannelConfigDTO channelConfig) {
        this.channelConfig = channelConfig;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChannelSignatureDTO that = (ChannelSignatureDTO) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(channelConfig, that.channelConfig) &&
                Objects.equals(signature, that.signature) &&
                Objects.equals(desc, that.desc) &&
                Objects.equals(status, that.status) &&
                Objects.equals(deleteFlag, that.deleteFlag);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, channelConfig, signature, desc, status, deleteFlag);
    }

    @Override
    public String toString() {
        return "ChannelSignatureDTO{" +
                "id=" + id +
                ", channelConfig=" + channelConfig +
                ", signature='" + signature + '\'' +
                ", desc='" + desc + '\'' +
                ", status=" + status +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", deleteFlag=" + deleteFlag +
                '}';
    }
}
