package com.leonzhangxf.sms.domain.dto;

import com.leonzhangxf.sms.domain.ChannelTemplateDO;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * 渠道模板
 *
 * @author leonzhangxf 20180904
 */
@ApiModel("渠道模板")
public class ChannelTemplateDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("ID")
    private Integer id;

    @ApiModelProperty(value = "渠道配置，在提交修改和保存时，只需要传递其ID即可", required = true)
    private ChannelConfigDTO channelConfig;

    @ApiModelProperty(value = "名称", required = true)
    private String name;

    @ApiModelProperty(value = "渠道短信模板code，如阿里云短信模板CODE", required = true)
    private String code;

    @ApiModelProperty(value = "渠道短信模板内容，携带变量使用“${code}”的格式", required = true)
    private String content;

    @ApiModelProperty("短信模板参数列表,多变量按顺序用英文逗号分隔,如\"name,vcode\"。无变量使用空字符。")
    private String params;

    @ApiModelProperty(value = "启禁用，0-禁用，1-启用")
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

    public ChannelConfigDTO getChannelConfig() {
        return channelConfig;
    }

    public void setChannelConfig(ChannelConfigDTO channelConfig) {
        this.channelConfig = channelConfig;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
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

    public static ChannelTemplateDTO convertChannelTemplateDO(ChannelTemplateDO channelTemplateDO,
                                                              ChannelConfigDTO channelConfig) {
        if (null == channelTemplateDO || null == channelConfig) return null;
        ChannelTemplateDTO channelTemplateDTO = new ChannelTemplateDTO();
        BeanUtils.copyProperties(channelTemplateDO, channelTemplateDTO);
        channelTemplateDTO.setChannelConfig(channelConfig);
        return channelTemplateDTO;
    }

    public static ChannelTemplateDO convertToChannelTemplateDO(ChannelTemplateDTO channelTemplateDTO) {
        if (null == channelTemplateDTO || null == channelTemplateDTO.getChannelConfig()
                || null == channelTemplateDTO.getChannelConfig().getId())
            return null;
        ChannelTemplateDO channelTemplateDO = new ChannelTemplateDO();
        BeanUtils.copyProperties(channelTemplateDTO, channelTemplateDO);
        channelTemplateDO.setChnlConfigId(channelTemplateDTO.getChannelConfig().getId());
        channelTemplateDO.setCreateTime(null);
        channelTemplateDO.setUpdateTime(null);
        return channelTemplateDO;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChannelTemplateDTO that = (ChannelTemplateDTO) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(channelConfig, that.channelConfig) &&
                Objects.equals(name, that.name) &&
                Objects.equals(code, that.code) &&
                Objects.equals(content, that.content) &&
                Objects.equals(params, that.params) &&
                Objects.equals(status, that.status) &&
                Objects.equals(deleteFlag, that.deleteFlag);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, channelConfig, name, code, content, params, status, deleteFlag);
    }

    @Override
    public String toString() {
        return "ChannelTemplateDTO{" +
                "id=" + id +
                ", channelConfig=" + channelConfig +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", content='" + content + '\'' +
                ", params='" + params + '\'' +
                ", status=" + status +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", deleteFlag=" + deleteFlag +
                '}';
    }
}
