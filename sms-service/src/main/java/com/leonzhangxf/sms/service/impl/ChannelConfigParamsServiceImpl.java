package com.leonzhangxf.sms.service.impl;

import com.leonzhangxf.sms.constant.CacheConstant;
import com.leonzhangxf.sms.dao.ChannelConfigParamsDao;
import com.leonzhangxf.sms.domain.ChannelConfigParamsDO;
import com.leonzhangxf.sms.domain.dto.ChannelConfigParamsDTO;
import com.leonzhangxf.sms.enumeration.ChannelConfigParam;
import com.leonzhangxf.sms.exception.DeleteException;
import com.leonzhangxf.sms.service.ChannelConfigParamsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 渠道账号配置参数 Service
 *
 * @author leonzhangxf 20180903
 */
@Service
public class ChannelConfigParamsServiceImpl implements ChannelConfigParamsService {

    private ChannelConfigParamsDao channelConfigParamsDao;

    @Autowired
    public void setChannelConfigParamsDao(ChannelConfigParamsDao channelConfigParamsDao) {
        this.channelConfigParamsDao = channelConfigParamsDao;
    }

    @Override
    @Transactional
    @Cacheable(value = CacheConstant.CHANNEL_CONFIG_PARAMS_BY_CHANNEL_CONFIG_ID,
        key = CacheConstant.CHANNEL_CONFIG_PARAMS_BY_CHANNEL_CONFIG_ID + " + #root.args[0]")
    public List<ChannelConfigParamsDTO> configParams(Integer channelConfigId) {

        List<ChannelConfigParamsDO> channelConfigParamsDOList =
            channelConfigParamsDao.channelConfigParams(channelConfigId);
        if (CollectionUtils.isEmpty(channelConfigParamsDOList)) return null;

        List<ChannelConfigParamsDTO> list = new ArrayList<>();
        for (ChannelConfigParamsDO channelConfigParamDO : channelConfigParamsDOList) {
            ChannelConfigParam key = ChannelConfigParam.getChannelConfigParam(channelConfigParamDO.getKey());
            if (null == key) continue;

            ChannelConfigParamsDTO channelConfigParamsDTO = new ChannelConfigParamsDTO();
            channelConfigParamsDTO.setKey(key);
            channelConfigParamsDTO.setValue(channelConfigParamDO.getValue());
            list.add(channelConfigParamsDTO);
        }
        return list;
    }

    @Override
    @Transactional
    @CacheEvict(value = CacheConstant.CHANNEL_CONFIG_PARAMS_BY_CHANNEL_CONFIG_ID,
        key = CacheConstant.CHANNEL_CONFIG_PARAMS_BY_CHANNEL_CONFIG_ID + " + #root.args[0]",
        beforeInvocation = true)
    public void deleteConfigParams(Integer channelConfigId) {
        if (null == channelConfigId) throw new DeleteException("删除渠道配置参数缺少必要参数！");
        channelConfigParamsDao.deleteChannelConfigParams(channelConfigId);
    }

    @Override
    @Transactional
    @CacheEvict(value = CacheConstant.CHANNEL_CONFIG_PARAMS_BY_CHANNEL_CONFIG_ID,
        key = CacheConstant.CHANNEL_CONFIG_PARAMS_BY_CHANNEL_CONFIG_ID + " + #root.args[0][0].getChnlConfigId()",
        beforeInvocation = true)
    public void saveConfigParamsBatch(List<ChannelConfigParamsDO> channelConfigParamsDOList) {
        if (CollectionUtils.isEmpty(channelConfigParamsDOList)) return;
        channelConfigParamsDao.saveChannelConfigParamsBatch(channelConfigParamsDOList);
    }

    @Override
    @Transactional
    @CacheEvict(value = CacheConstant.CHANNEL_CONFIG_PARAMS_BY_CHANNEL_CONFIG_ID,
        key = CacheConstant.CHANNEL_CONFIG_PARAMS_BY_CHANNEL_CONFIG_ID + " + #root.args[0][0].getChnlConfigId()",
        beforeInvocation = true)
    public void updateConfigParamsBatch(List<ChannelConfigParamsDO> channelConfigParamsDOList) {
        if (CollectionUtils.isEmpty(channelConfigParamsDOList)) return;
        channelConfigParamsDao.updateChannelConfigParamsBatch(channelConfigParamsDOList);
    }

    @Override
    @Transactional
    @CacheEvict(value = CacheConstant.CHANNEL_CONFIG_PARAMS_BY_CHANNEL_CONFIG_ID,
        key = CacheConstant.CHANNEL_CONFIG_PARAMS_BY_CHANNEL_CONFIG_ID + " + #root.args[0][0].getChnlConfigId()",
        beforeInvocation = true)
    public void deleteConfigParamsBatch(List<ChannelConfigParamsDO> channelConfigParamsDOList) {
        if (CollectionUtils.isEmpty(channelConfigParamsDOList)) return;
        channelConfigParamsDao.deleteChannelConfigParamsBatch(channelConfigParamsDOList);
    }
}
