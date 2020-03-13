package com.leonzhangxf.sms.constant;

/**
 * 缓存相关常量
 *
 * @author leonzhanxf 220181024
 */
public interface CacheConstant {

    /**
     * 根据短信服务模板ID查询短信服务模板
     * <p>
     * 缓存清除场景：
     * 渠道配置更新/删除
     * 渠道签名更新/删除
     * 渠道模板更新/删除
     * 接入方更新/删除
     * 短信服务模板更新/删除
     */
    String TEMPLATE_SERVICE_TEMPLATE_BY_TEMPLATE_ID = "\"TemplateService_templateByTemplateId_\"";

    /**
     * 根据渠道配置ID查询渠道配置参数
     * <p>
     * 缓存清除场景：
     * 渠道配置参数保存
     * 渠道配置参数更新
     * 渠道配置参数删除
     */
    String CHANNEL_CONFIG_PARAMS_BY_CHANNEL_CONFIG_ID = "\"ChannelConfigParamsService_configParams_\"";
}
