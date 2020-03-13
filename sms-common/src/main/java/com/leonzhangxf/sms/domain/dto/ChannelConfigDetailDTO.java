package com.leonzhangxf.sms.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * @author leonzhangxf 20180903
 */
@ApiModel("渠道配置详情")
public class ChannelConfigDetailDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("渠道配置")
    private ChannelConfigDTO config;

    @ApiModelProperty("渠道配置参数列表")
    private List<ChannelConfigParamsDTO> params;

    public ChannelConfigDTO getConfig() {
        return config;
    }

    public void setConfig(ChannelConfigDTO config) {
        this.config = config;
    }

    public List<ChannelConfigParamsDTO> getParams() {
        return params;
    }

    public void setParams(List<ChannelConfigParamsDTO> params) {
        this.params = params;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChannelConfigDetailDTO that = (ChannelConfigDetailDTO) o;
        return Objects.equals(config, that.config) &&
                Objects.equals(params, that.params);
    }

    @Override
    public int hashCode() {
        return Objects.hash(config, params);
    }

    @Override
    public String toString() {
        return "ChannelConfigDetailDTO{" +
                "config=" + config +
                ", params=" + params +
                '}';
    }
}
