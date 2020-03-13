package com.leonzhangxf.sms.service;

import com.leonzhangxf.sms.domain.dto.TemplateDTO;
import com.leonzhangxf.sms.enumeration.TemplateUsage;
import com.leonzhangxf.sms.util.Page;

import java.util.List;

/**
 * 短信服务模板
 *
 * @author leonzhangxf 20180906
 */
public interface TemplateService {


    void updateTemplateStatusByChannelSignatureId(Integer channelSignatureId, Integer targetStatus);


    void updateTemplateStatusByChannelSignatureIdList(List<Integer> channelSignatureIdList, Integer targetStatus);


    void updateTemplateStatusByChannelTemplateId(Integer channelTemplateId, Integer targetStatus);


    void updateTemplateStatusByChannelTemplateIdList(List<Integer> channelTemplateIdList, Integer targetStatus);


    List<TemplateDTO> templatesWithinDeletedByTemplateId(String templateId);


    Page<TemplateDTO> templates(Integer clientId, Integer channelSignatureId, Integer channelTemplateId, String name,
                                String templateId, TemplateUsage usage, Integer status, Integer currentPage, Integer pageSize);

    List<TemplateDTO> templates();


    void saveTemplate(TemplateDTO templateDTO);


    void deleteTemplate(Integer id);


    void updateTemplate(TemplateDTO templateDTO);


    TemplateDTO template(Integer id);

    TemplateDTO templateByTemplateId(String templateId);
}
