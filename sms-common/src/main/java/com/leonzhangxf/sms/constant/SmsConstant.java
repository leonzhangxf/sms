package com.leonzhangxf.sms.constant;

/**
 * 全局常量
 * 特性常量可以在同包下另外定义
 *
 * @author leonzhangxf 20180906
 */
public interface SmsConstant {

    /**
     * 默认分页当前页
     */
    Integer DEFAULT_CURRENT_PAGE = 1;

    /**
     * 默认分页每页数
     */
    Integer DEFAULT_PAGE_SIZE = 10;

    /**
     * 渠道短信模板参数分隔符
     */
    String CHANNEL_TEMPLATE_PARAMS_DELIMITER = ",";
}
