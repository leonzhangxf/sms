package com.leonzhangxf.sms.domain.dto;

import com.leonzhangxf.sms.domain.ChannelConfigParamsDO;
import com.leonzhangxf.sms.enumeration.ChannelConfigParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@ApiModel("渠道配置参数")
public class ChannelConfigParamsDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "渠道配置参数KEY", required = true)
    private ChannelConfigParam key;

    @ApiModelProperty(value = "渠道配置参数VALUE", required = true)
    private String value;

    public ChannelConfigParamsDTO() {
    }

    public ChannelConfigParamsDTO(ChannelConfigParam key, String value) {
        this.key = key;
        this.value = value;
    }

    public ChannelConfigParam getKey() {
        return key;
    }

    public void setKey(ChannelConfigParam key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static ChannelConfigParamsDO convertToChannelConfigParamsDO(
            ChannelConfigParamsDTO channelConfigParamsDTO, Integer channelConfigId) {
        if (null == channelConfigParamsDTO) return null;
        if (null == channelConfigId) return null;
        ChannelConfigParamsDO channelConfigParamsDO = new ChannelConfigParamsDO();
        channelConfigParamsDO.setChnlConfigId(channelConfigId);

        if (null == channelConfigParamsDTO.getKey()) return null;
        channelConfigParamsDO.setKey(channelConfigParamsDTO.getKey().getKey());

        if (!StringUtils.hasText(channelConfigParamsDTO.getValue())) return null;
        channelConfigParamsDO.setValue(channelConfigParamsDTO.getValue());
        return channelConfigParamsDO;
    }

    public static List<ChannelConfigParamsDO> convertToChannelConfigParamsDOBatch(
            List<ChannelConfigParamsDTO> channelConfigParamsDTOList, Integer channelConfigId) {
        if (CollectionUtils.isEmpty(channelConfigParamsDTOList)) return null;
        if (null == channelConfigId) return null;

        List<ChannelConfigParamsDO> channelConfigParamsDOList = new ArrayList<>();
        for (ChannelConfigParamsDTO channelConfigParamsDTO : channelConfigParamsDTOList) {
            ChannelConfigParamsDO channelConfigParamsDO =
                    ChannelConfigParamsDTO.convertToChannelConfigParamsDO(channelConfigParamsDTO, channelConfigId);
            if (null != channelConfigParamsDO) {
                channelConfigParamsDOList.add(channelConfigParamsDO);
            }
        }
        return channelConfigParamsDOList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChannelConfigParamsDTO that = (ChannelConfigParamsDTO) o;
        return key == that.key && Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, value);
    }

    @Override
    public String toString() {
        return "ChannelConfigParamsDTO{" +
                "key=" + key +
                ", value='" + value + '\'' +
                '}';
    }
}
