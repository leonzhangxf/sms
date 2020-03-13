package com.leonzhangxf.sms.message;

import com.leonzhangxf.sms.enumeration.ChannelConfigParam;
import com.leonzhangxf.sms.enumeration.ChannelConfigType;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 短信发送请求上下文构造器
 *
 * @author leonzhangxf
 */
public class MessageContextBuilder {

    /**
     * 短信发送请求
     */
    private MessageRequest request;

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
     * 短信发送手机号
     */
    private String mobile;

    /**
     * 短信发送参数
     */
    private Map<String, String> params;

    /**
     * 手机请求单元
     */
    private MessageRequestMobileUnit mobileUnit;

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

    private MessageContextBuilder() {
    }

    public static MessageContextBuilder newMessageContextBuilder() {
        return new MessageContextBuilder();
    }

    public MessageContextBuilder signName(String signName) {
        this.signName = signName;
        return this;
    }

    public MessageContextBuilder templateId(String templateId) {
        this.templateId = templateId;
        return this;
    }

    public MessageContextBuilder templateContent(String templateContent) {
        this.templateContent = templateContent;
        return this;
    }

    public MessageContextBuilder mobileAndParams(String mobile, Map<String, String> params) {
        this.mobile = mobile;
        this.params = params;
        return this;
    }

    public MessageContextBuilder mobileUnitList(List<MessageRequestMobileUnit> mobileUnits) {
        this.mobileUnits = mobileUnits;
        return this;
    }

    public MessageContextBuilder mobileUnit(MessageRequestMobileUnit mobileUnit) {
        this.mobileUnit = mobileUnit;
        return this;
    }

    public MessageContextBuilder channelConfigType(ChannelConfigType channelConfigType) {
        this.channelConfigType = channelConfigType;
        return this;
    }

    public MessageContextBuilder configParams(Map<ChannelConfigParam, String> configParams) {
        this.configParams = configParams;
        return this;
    }

    public MessageContextBuilder messageRequest(MessageRequest request) {
        this.request = request;
        return this;
    }

    public MessageContext build() {
        MessageContext context;

        if (null == request) {
            request = MessageRequest.newMessageRequest(null, null, null,
                    null, null, null, null);
        }
        if (!resolveMessageRequest()) {
            return null;
        }

        context = new MessageContext();
        context.setRequest(request);
        return context;
    }

    /**
     * 处理短信发送请求
     */
    private boolean resolveMessageRequest() {
        if (null == request.getChannelConfigType()) {
            if (null == channelConfigType) {
                return false;
            } else {
                request.setChannelConfigType(channelConfigType);
            }
        }

        if (null == request.getConfigParams() || request.getConfigParams().size() <= 0) {
            if (null == configParams || configParams.size() <= 0) {
                return false;
            } else {
                request.setConfigParams(configParams);
            }
        }

        if (!StringUtils.hasText(request.getSignName())) {
            if (StringUtils.hasText(signName)) {
                request.setSignName(signName);
            } else {
                return false;
            }
        }

        if (!StringUtils.hasText(request.getTemplateId())) {
            if (StringUtils.hasText(templateId)) {
                request.setTemplateId(templateId);
            } else {
                return false;
            }
        }

        if (!StringUtils.hasText(request.getTemplateContent())) {
            if (StringUtils.hasText(templateContent)) {
                request.setTemplateContent(templateContent);
            } else {
                return false;
            }
        }

        return resolveMobileUnits();
    }

    /**
     * 处理请求的手机号参数单元
     */
    private boolean resolveMobileUnits() {
        if (null == request.getMobileUnits()) {
            if (null == mobileUnit && !StringUtils.hasText(mobile)) {
                return false;
            }

            mobileUnits = addAdditionalMobileUnit(mobileUnits);

            if (null == mobileUnits || mobileUnits.size() <= 0) {
                return false;
            }

            request.setMobileUnits(mobileUnits);
        } else {
            request.setMobileUnits(addAdditionalMobileUnit(request.getMobileUnits()));
        }
        return true;
    }

    private List<MessageRequestMobileUnit> addAdditionalMobileUnit(List<MessageRequestMobileUnit> mobileUnits) {
        if (null == mobileUnits) {
            mobileUnits = new ArrayList<>();
        }

        if (null != mobileUnit && StringUtils.hasText(mobileUnit.getMobile())) {
            mobileUnits.add(mobileUnit);
        }

        if (StringUtils.hasText(mobile) && null != params && params.size() > 0) {
            MessageRequestMobileUnit unit = new MessageRequestMobileUnit();
            unit.setMobile(mobile);
            unit.setParams(params);
            mobileUnits.add(unit);
        }

        return mobileUnits;
    }
}
