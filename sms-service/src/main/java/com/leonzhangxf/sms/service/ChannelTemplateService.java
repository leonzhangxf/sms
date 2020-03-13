package com.leonzhangxf.sms.service;

import com.leonzhangxf.sms.domain.dto.ChannelTemplateDTO;
import com.leonzhangxf.sms.util.Page;

import java.util.List;

/**
 * 渠道模板 Service
 *
 * @author leonzhangxf 20180904
 */
public interface ChannelTemplateService {

    void saveTemplate(ChannelTemplateDTO channelTemplateDTO);


    void deleteTemplate(Integer id);


    void updateTemplate(ChannelTemplateDTO channelTemplateDTO);


    void updateTemplateStatusByChannelConfigId(Integer channelConfigId, Integer targetStatus);


    Page<ChannelTemplateDTO> templates(Integer channelConfigId, String name, String code, Integer status,
                                       Integer currentPage, Integer pageSize);


    List<ChannelTemplateDTO> templates();


    ChannelTemplateDTO template(Integer id);
}
