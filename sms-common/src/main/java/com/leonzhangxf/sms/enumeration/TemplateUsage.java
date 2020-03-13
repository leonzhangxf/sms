package com.leonzhangxf.sms.enumeration;

/**
 * 短信服务模板用途枚举
 *
 * @author leonzhangxf 20180906
 */
public enum TemplateUsage {

    /**
     * VERIFICATION 验证
     */
    VERIFICATION("VERIFICATION", 1, "验证"),

    /**
     * NOTIFICATION 通知
     */
    NOTIFICATION("NOTIFICATION", 2, "通知"),

    /**
     * PROMOTION 推广
     */
    PROMOTION("PROMOTION", 3, "推广");

    private String name;

    private int code;

    private String desc;

    TemplateUsage(String name, int code, String desc) {
        this.name = name;
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public static TemplateUsage getTemplateUsage(Integer params) {
        if (null == params) return null;

        TemplateUsage[] values = TemplateUsage.values();
        if (values.length > 0) {
            for (TemplateUsage value : values) {
                if (value.getCode() == params) {
                    return value;
                }
            }
        }
        return null;
    }

    public static TemplateUsage getTemplateUsage(String params) {
        if (null == params) return null;

        TemplateUsage[] values = TemplateUsage.values();
        if (values.length > 0) {
            for (TemplateUsage value : values) {
                if (value.getName().equals(params)) {
                    return value;
                }
            }
        }
        return null;
    }

    public static boolean hasTemplateUsage(Integer params) {
        if (null == params) return false;

        TemplateUsage[] values = TemplateUsage.values();
        if (values.length > 0) {
            for (TemplateUsage value : values) {
                if (value.getCode() == params) {
                    return true;
                }
            }
        }
        return false;
    }
}
