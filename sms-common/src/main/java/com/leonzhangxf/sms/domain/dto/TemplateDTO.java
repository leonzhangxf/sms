package com.leonzhangxf.sms.domain.dto;

import com.leonzhangxf.sms.domain.TemplateDO;
import com.leonzhangxf.sms.enumeration.TemplateUsage;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@ApiModel("短信服务模板")
public class TemplateDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("ID")
    private Integer id;

    @ApiModelProperty("接入方")
    private ClientDTO client;

    @ApiModelProperty("渠道签名")
    private ChannelSignatureDTO channelSignature;

    @ApiModelProperty("渠道模板")
    private ChannelTemplateDTO channelTemplate;

    @ApiModelProperty(value = "名称", required = true)
    private String name;

    @ApiModelProperty(value = "短信服务模板ID", required = true)
    private String templateId;

    @ApiModelProperty("描述")
    private String desc;

    @ApiModelProperty(value = "用途：1-验证，2-通知，3-推广", required = true)
    private TemplateUsage usage;

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ClientDTO getClient() {
        return client;
    }

    public void setClient(ClientDTO client) {
        this.client = client;
    }

    public ChannelSignatureDTO getChannelSignature() {
        return channelSignature;
    }

    public void setChannelSignature(ChannelSignatureDTO channelSignature) {
        this.channelSignature = channelSignature;
    }

    public ChannelTemplateDTO getChannelTemplate() {
        return channelTemplate;
    }

    public void setChannelTemplate(ChannelTemplateDTO channelTemplate) {
        this.channelTemplate = channelTemplate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public TemplateUsage getUsage() {
        return usage;
    }

    public void setUsage(TemplateUsage usage) {
        this.usage = usage;
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

    public static TemplateDTO convertTemplateDO(TemplateDO templateDO, ClientDTO clientDTO,
                                                ChannelSignatureDTO channelSignatureDTO,
                                                ChannelTemplateDTO channelTemplateDTO) {
        if (null == templateDO || null == clientDTO || null == clientDTO.getId()
                || null == channelSignatureDTO || null == channelSignatureDTO.getId()
                || null == channelTemplateDTO || null == channelTemplateDTO.getId())
            return null;

        TemplateDTO templateDTO = new TemplateDTO();
        BeanUtils.copyProperties(templateDO, templateDTO);
        templateDTO.setClient(clientDTO);
        templateDTO.setChannelSignature(channelSignatureDTO);
        templateDTO.setChannelTemplate(channelTemplateDTO);
        templateDTO.setUsage(TemplateUsage.getTemplateUsage(templateDO.getUsage()));
        return templateDTO;
    }

    public static TemplateDO convertToTemplateDO(TemplateDTO templateDTO) {
        if (null == templateDTO || null == templateDTO.getClient() || null == templateDTO.getClient().getId()
                || null == templateDTO.getChannelSignature() || null == templateDTO.getChannelSignature().getId()
                || null == templateDTO.getChannelTemplate() || null == templateDTO.getChannelTemplate().getId())
            return null;

        TemplateDO templateDO = new TemplateDO();
        BeanUtils.copyProperties(templateDTO, templateDO);
        templateDO.setClientId(templateDTO.getClient().getId());
        templateDO.setChnlSignatureId(templateDTO.getChannelSignature().getId());
        templateDO.setChnlTemplateId(templateDTO.getChannelTemplate().getId());
        templateDO.setUsage(templateDTO.getUsage().getCode());
        templateDO.setCreateTime(null);
        templateDO.setUpdateTime(null);
        return templateDO;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TemplateDTO that = (TemplateDTO) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(client, that.client) &&
                Objects.equals(channelSignature, that.channelSignature) &&
                Objects.equals(channelTemplate, that.channelTemplate) &&
                Objects.equals(name, that.name) &&
                Objects.equals(templateId, that.templateId) &&
                Objects.equals(desc, that.desc) &&
                usage == that.usage &&
                Objects.equals(status, that.status) &&
                Objects.equals(deleteFlag, that.deleteFlag);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, client, channelSignature, channelTemplate, name, templateId, desc, usage, status, deleteFlag);
    }

    @Override
    public String toString() {
        return "TemplateDTO{" +
                "id=" + id +
                ", client=" + client +
                ", channelSignature=" + channelSignature +
                ", channelTemplate=" + channelTemplate +
                ", name='" + name + '\'' +
                ", templateId='" + templateId + '\'' +
                ", desc='" + desc + '\'' +
                ", usage=" + usage +
                ", status=" + status +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", deleteFlag=" + deleteFlag +
                '}';
    }
}
