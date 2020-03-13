package com.leonzhangxf.sms.dao;

import com.leonzhangxf.sms.domain.TemplateDO;
import com.leonzhangxf.sms.enumeration.TemplateUsage;
import com.leonzhangxf.sms.util.Page;

import java.util.List;

/**
 * 短信服务模板
 *
 * @author leonzhangxf 20180906
 */
public interface TemplateDao {


    void updateTemplateStatusByChannelSignatureId(Integer channelSignatureId, Integer targetStatus);


    void updateTemplateStatusByChannelSignatureIdList(List<Integer> channelSignatureDOIdList, Integer targetStatus);


    void updateTemplateStatusByChannelTemplateId(Integer channelTemplateId, Integer targetStatus);


    void updateTemplateStatusByChannelTemplateIdList(List<Integer> channelTemplateIdList, Integer targetStatus);


    List<TemplateDO> templatesWithinDeletedByTemplateId(String templateId);


    Page<TemplateDO> templates(Integer clientId, Integer channelSignatureId, Integer channelTemplateId, String name,
                               String templateId, TemplateUsage usage, Integer status, Integer currentPage, Integer pageSize);


    void saveTemplate(TemplateDO templateDO);


    void deleteTemplate(Integer id);


    TemplateDO template(Integer id);


    TemplateDO templateByTemplateId(String templateId);


    void updateTemplate(TemplateDO templateDO);


    List<TemplateDO> templates();
}
