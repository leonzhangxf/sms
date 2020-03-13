package com.leonzhangxf.sms.message;

import com.leonzhangxf.sms.enumeration.ChannelConfigParam;
import com.leonzhangxf.sms.enumeration.ChannelConfigType;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.*;

/**
 * 发送短信请求
 *
 * @author leonzhangxf 20181115
 */
public class MessageRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 渠道短信签名
     */
    private String signName;

    /**
     * 渠道短信模板ID
     */
    private String templateId;

    /**
     * 渠道短信模板内容
     */
    private String templateContent;

    /**
     * 手机请求单元列表
     */
    private List<MessageRequestMobileUnit> mobileUnits;

    /**
     * 渠道类型
     */
    private ChannelConfigType channelConfigType;

    /**
     * 渠道配置参数
     */
    private Map<ChannelConfigParam, String> configParams;

    private MessageRequest() {
    }

    private MessageRequest(ChannelConfigType channelConfigType,
                           Map<ChannelConfigParam, String> configParams,
                           String signName, String templateId, String templateContent,
                           List<MessageRequestMobileUnit> mobileUnits) {
        super();
        this.channelConfigType = channelConfigType;
        this.configParams = configParams;
        this.signName = signName;
        this.templateId = templateId;
        this.templateContent = templateContent;
        this.mobileUnits = mobileUnits;
    }

    public static MessageRequest newMessageRequest(ChannelConfigType channelConfigType,
                                                   Map<ChannelConfigParam, String> configParams,
                                                   String signName, String templateId, String templateContent,
                                                   String mobile, Map<String, String> params) {
        if (StringUtils.hasText(mobile)) {
            MessageRequestMobileUnit mobileUnit = new MessageRequestMobileUnit();
            mobileUnit.setMobile(mobile);
            mobileUnit.setParams(params);
            return newMessageRequest(channelConfigType, configParams, signName,
                    templateId, templateContent, mobileUnit);
        } else {
            return newMessageRequest(channelConfigType, configParams, signName, templateId, templateContent);
        }
    }

    public static MessageRequest newMessageRequest(ChannelConfigType channelConfigType,
                                                   Map<ChannelConfigParam, String> configParams,
                                                   String signName, String templateId, String templateContent,
                                                   MessageRequestMobileUnit... mobileUnits) {
        List<MessageRequestMobileUnit> list = null;
        if (null != mobileUnits && mobileUnits.length > 0) {
            list = new ArrayList<>(Arrays.asList(mobileUnits));
        }
        return newMessageRequest(channelConfigType, configParams, signName, templateId,
                templateContent, list);
    }

    public static MessageRequest newMessageRequest(ChannelConfigType channelConfigType,
                                                   Map<ChannelConfigParam, String> configParams,
                                                   String signName, String templateId, String templateContent,
                                                   List<MessageRequestMobileUnit> mobileUnits) {
        return new MessageRequest(channelConfigType, configParams, signName, templateId, templateContent, mobileUnits);
    }

    public String getSignName() {
        return signName;
    }

    public void setSignName(String signName) {
        this.signName = signName;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getTemplateContent() {
        return templateContent;
    }

    public void setTemplateContent(String templateContent) {
        this.templateContent = templateContent;
    }

    public List<MessageRequestMobileUnit> getMobileUnits() {
        return mobileUnits;
    }

    public void setMobileUnits(List<MessageRequestMobileUnit> mobileUnits) {
        this.mobileUnits = mobileUnits;
    }

    public ChannelConfigType getChannelConfigType() {
        return channelConfigType;
    }

    public void setChannelConfigType(ChannelConfigType channelConfigType) {
        this.channelConfigType = channelConfigType;
    }

    public Map<ChannelConfigParam, String> getConfigParams() {
        return configParams;
    }

    public void setConfigParams(Map<ChannelConfigParam, String> configParams) {
        this.configParams = configParams;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MessageRequest that = (MessageRequest) o;
        return Objects.equals(signName, that.signName) &&
                Objects.equals(templateId, that.templateId) &&
                Objects.equals(templateContent, that.templateContent) &&
                Objects.equals(mobileUnits, that.mobileUnits) &&
                channelConfigType == that.channelConfigType &&
                Objects.equals(configParams, that.configParams);
    }

    @Override
    public int hashCode() {
        return Objects.hash(signName, templateId, templateContent, mobileUnits, channelConfigType, configParams);
    }

    @Override
    public String toString() {
        return "MessageRequest{" +
                "signName='" + signName + '\'' +
                ", templateId='" + templateId + '\'' +
                ", templateContent='" + templateContent + '\'' +
                ", mobileUnits=" + mobileUnits +
                ", channelConfigType=" + channelConfigType +
                ", configParams=" + configParams +
                '}';
    }
}
