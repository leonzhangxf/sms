package com.leonzhangxf.sms.enumeration;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@ApiModel("渠道配置参数KEY")
public enum ChannelConfigParam {

    /**
     * ACCESS_KEY_ID
     */
    @ApiModelProperty("ACCESS_KEY_ID")
    ACCESS_KEY_ID("ACCESS_KEY_ID", "阿里云短信服务Access Key ID 唯一标识", ChannelConfigType.ALIYUN),

    /**
     * ACCESS_KEY_SECRET
     */
    @ApiModelProperty("ACCESS_KEY_SECRET")
    ACCESS_KEY_SECRET("ACCESS_KEY_SECRET", "阿里云短信服务 Access Key Secret 密钥", ChannelConfigType.ALIYUN);

    private String key;

    private String desc;

    private ChannelConfigType type;

    ChannelConfigParam(String key, String desc, ChannelConfigType type) {
        this.key = key;
        this.desc = desc;
        this.type = type;
    }

    public String getKey() {
        return key;
    }

    public String getDesc() {
        return desc;
    }

    public ChannelConfigType getType() {
        return type;
    }

    public static ChannelConfigParam getChannelConfigParam(String keyParams) {
        if (!StringUtils.hasText(keyParams)) return null;

        ChannelConfigParam[] values = ChannelConfigParam.values();
        if (values.length > 0) {
            for (ChannelConfigParam value : values) {
                if (value.getKey().equals(keyParams)) {
                    return value;
                }
            }
        }
        return null;
    }

    public static List<ChannelConfigParam> getChannelConfigParams(ChannelConfigType params) {
        if (null == params) return null;

        List<ChannelConfigParam> list = new ArrayList<>();
        ChannelConfigParam[] values = ChannelConfigParam.values();
        if (values.length > 0) {
            for (ChannelConfigParam value : values) {
                if (value.getType().getType() == params.getType()) {
                    list.add(value);
                }
            }
        }
        return list;
    }
}
