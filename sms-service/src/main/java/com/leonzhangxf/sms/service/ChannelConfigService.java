package com.leonzhangxf.sms.service;

import com.leonzhangxf.sms.domain.dto.ChannelConfigDTO;
import com.leonzhangxf.sms.domain.dto.ChannelConfigDetailDTO;
import com.leonzhangxf.sms.enumeration.ChannelConfigType;
import com.leonzhangxf.sms.util.Page;

import java.util.List;

/**
 * 渠道配置Service
 *
 * @author leonzhangxf 20180903
 */
public interface ChannelConfigService {

    void deleteConfig(Integer id);

    void saveConfig(ChannelConfigDetailDTO channelConfigDetailDTO);

    void updateConfig(ChannelConfigDetailDTO channelConfigDetailDTO);

    Page<ChannelConfigDTO> configs(String name, ChannelConfigType channelConfigType, Integer status, Integer currentPage, Integer pageSize);

    List<ChannelConfigDTO> configs();

    List<ChannelConfigDTO> configs(String name, ChannelConfigType channelConfigType);

    ChannelConfigDetailDTO channelConfigDetail(Integer id);

    ChannelConfigDTO config(Integer id);

    ChannelConfigDTO configWithinDeleted(Integer id);

    List<ChannelConfigDTO> configsWithinDeletedByName(String name);
}
