package com.leonzhangxf.sms.enumeration;

/**
 * 渠道账号配置类型
 *
 * @author leonzhangxf 20180903
 */
public enum ChannelConfigType {

    /**
     * ALIYUN
     */
    ALIYUN("ALIYUN", 0);

    private String name;

    private int type;

    ChannelConfigType(String name, int type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public int getType() {
        return type;
    }

    public static ChannelConfigType getConfigType(int params) {
        ChannelConfigType[] values = ChannelConfigType.values();
        if (values.length > 0) {
            for (ChannelConfigType value : values) {
                if (value.getType() == params) {
                    return value;
                }
            }
        }
        return null;
    }

    public static boolean contains(ChannelConfigType param) {
        if (null == param) return false;
        ChannelConfigType[] values = ChannelConfigType.values();
        if (values.length > 0) {
            for (ChannelConfigType value : values) {
                if (value.getType() == param.getType()) {
                    return true;
                }
            }
        }
        return false;
    }
}
