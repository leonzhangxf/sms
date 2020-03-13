package com.leonzhangxf.sms.service.impl;

import com.leonzhangxf.sms.constant.SmsConstant;
import com.leonzhangxf.sms.dao.ChannelConfigDao;
import com.leonzhangxf.sms.domain.ChannelConfigDO;
import com.leonzhangxf.sms.domain.ChannelConfigParamsDO;
import com.leonzhangxf.sms.domain.dto.ChannelConfigDTO;
import com.leonzhangxf.sms.domain.dto.ChannelConfigDetailDTO;
import com.leonzhangxf.sms.domain.dto.ChannelConfigParamsDTO;
import com.leonzhangxf.sms.enumeration.ChannelConfigParam;
import com.leonzhangxf.sms.enumeration.ChannelConfigType;
import com.leonzhangxf.sms.enumeration.CommonStatus;
import com.leonzhangxf.sms.exception.DeleteException;
import com.leonzhangxf.sms.exception.SaveException;
import com.leonzhangxf.sms.exception.UpdateException;
import com.leonzhangxf.sms.service.ChannelConfigParamsService;
import com.leonzhangxf.sms.service.ChannelConfigService;
import com.leonzhangxf.sms.service.ChannelSignatureService;
import com.leonzhangxf.sms.service.ChannelTemplateService;
import com.leonzhangxf.sms.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 渠道配置Service 实现
 *
 * @author leonzhangxf 201890903
 */
@Service
public class ChannelConfigServiceImpl implements ChannelConfigService {

    private ChannelConfigDao channelConfigDao;

    private ChannelConfigParamsService channelConfigParamsService;

    private ChannelTemplateService channelTemplateService;

    private ChannelSignatureService channelSignatureService;

    @Autowired
    public void setChannelConfigDao(ChannelConfigDao channelConfigDao) {
        this.channelConfigDao = channelConfigDao;
    }

    @Autowired
    public void setChannelConfigParamsService(ChannelConfigParamsService channelConfigParamsService) {
        this.channelConfigParamsService = channelConfigParamsService;
    }

    @Autowired
    public void setChannelTemplateService(ChannelTemplateService channelTemplateService) {
        this.channelTemplateService = channelTemplateService;
    }

    @Autowired
    public void setChannelSignatureService(ChannelSignatureService channelSignatureService) {
        this.channelSignatureService = channelSignatureService;
    }


    @Override
    @Transactional
    public void deleteConfig(Integer id) {
        if (null == id) throw new DeleteException("删除渠道配置缺少必要参数！");

        //1.删除渠道配置
        ChannelConfigDO channelConfigDO = channelConfigDao.config(id);
        if (null == channelConfigDO) throw new DeleteException("根据ID未找到渠道配置！");
        channelConfigDao.deleteConfig(channelConfigDO.getId());

        //2.删除渠道配置参数
        channelConfigParamsService.deleteConfigParams(channelConfigDO.getId());

        //3.删除渠道配置，需要同时禁用其下对应的所有的渠道模板、渠道签名、短信服务模板（通过渠道模板、以及渠道签名的禁用来级联禁用）
        //禁用所属的渠道模板
        channelTemplateService.updateTemplateStatusByChannelConfigId(channelConfigDO.getId(), CommonStatus.DISABLE.getStatus());
        //禁用所属的渠道签名
        channelSignatureService.updateSignatureStatusByChannelConfigId(channelConfigDO.getId(), CommonStatus.DISABLE.getStatus());
    }


    @Override
    @Transactional
    public void saveConfig(ChannelConfigDetailDTO channelConfigDetailDTO) {
        if (null == channelConfigDetailDTO) throw new SaveException("渠道配置不能为空！");
        //1.保存主配置
        ChannelConfigDTO channelConfigDTO = channelConfigDetailDTO.getConfig();
        if (null == channelConfigDTO) throw new SaveException("渠道配置主配置不能为空！");

        ChannelConfigDO channelConfigDO = ChannelConfigDTO.convertToChannelConfigDO(channelConfigDTO);
        if (null == channelConfigDO) throw new SaveException("转换渠道配置主配置异常，请检查参数！");
        channelConfigDao.saveConfig(channelConfigDO);
        if (null == channelConfigDO.getId()) throw new SaveException("保存渠道配置主配置失败！");

        //2.保存参数配置
        List<ChannelConfigParamsDTO> params = channelConfigDetailDTO.getParams();
        if (CollectionUtils.isEmpty(params)) throw new SaveException("渠道配置配置参数不能为空！");
        List<ChannelConfigParamsDO> channelConfigParamsDOList =
                ChannelConfigParamsDTO.convertToChannelConfigParamsDOBatch(params, channelConfigDO.getId());
        channelConfigParamsService.saveConfigParamsBatch(channelConfigParamsDOList);
    }


    @Override
    @Transactional
    public void updateConfig(ChannelConfigDetailDTO channelConfigDetailDTO) {
        if (null == channelConfigDetailDTO) throw new UpdateException("更新渠道配置不能为空！");

        //1.更新主配置
        ChannelConfigDTO channelConfigDTO = channelConfigDetailDTO.getConfig();
        if (null == channelConfigDTO) throw new UpdateException("更新渠道配置，主配置不能为空！");

        ChannelConfigDO existChannelConfigDO = channelConfigDao.config(channelConfigDTO.getId());
        if (null == existChannelConfigDO) throw new UpdateException("更新渠道配置，根据ID没有找到渠道额皮质！");
        ChannelConfigDO channelConfigDO = ChannelConfigDTO.convertToChannelConfigDO(channelConfigDTO);
        if (null == channelConfigDO) throw new UpdateException("更新渠道配置，转换渠道配置异常，请检查参数！");

        if (!compareValuableValue(existChannelConfigDO, channelConfigDO)) {
            channelConfigDao.updateConfig(channelConfigDO);
        }

        //2.更新参数配置
        List<ChannelConfigParamsDTO> params = channelConfigDetailDTO.getParams();
        if (CollectionUtils.isEmpty(params)) throw new UpdateException("更新渠道配置，渠道配置参数不能为空！");

        List<ChannelConfigParamsDTO> existParams = channelConfigParamsService.configParams(channelConfigDO.getId());
        if (CollectionUtils.isEmpty(existParams)) {
            //之前没有，全部是新增的
            List<ChannelConfigParamsDO> channelConfigParamsDOList =
                    ChannelConfigParamsDTO.convertToChannelConfigParamsDOBatch(params, channelConfigDO.getId());
            channelConfigParamsService.saveConfigParamsBatch(channelConfigParamsDOList);
        } else {
            //之前有，分成新增、要更新的、以及要删除的 分开处理
            Map<ChannelConfigParam, String> existMap = existParams.stream()
                    .collect(Collectors.toMap(ChannelConfigParamsDTO::getKey, ChannelConfigParamsDTO::getValue));
            List<ChannelConfigParamsDTO> newParams = new ArrayList<>();
            List<ChannelConfigParamsDTO> differentParams = new ArrayList<>();

            for (ChannelConfigParamsDTO channelConfigParamsDTO : params) {
                String existValue = existMap.remove(channelConfigParamsDTO.getKey());
                if (!StringUtils.hasText(existValue)) {
                    newParams.add(channelConfigParamsDTO);
                } else if (!existValue.equals(channelConfigParamsDTO.getValue())) {
                    differentParams.add(channelConfigParamsDTO);
                }
            }

            List<ChannelConfigParamsDTO> deleteParams = new ArrayList<>();
            if (!CollectionUtils.isEmpty(existMap)) {
                for (Map.Entry<ChannelConfigParam, String> entry : existMap.entrySet()) {
                    deleteParams.add(new ChannelConfigParamsDTO(entry.getKey(), entry.getValue()));
                }
            }

            //Dispose
            if (!CollectionUtils.isEmpty(newParams)) {
                List<ChannelConfigParamsDO> newParamsDOList =
                        ChannelConfigParamsDTO.convertToChannelConfigParamsDOBatch(newParams, channelConfigDO.getId());
                channelConfigParamsService.saveConfigParamsBatch(newParamsDOList);
            }

            if (!CollectionUtils.isEmpty(differentParams)) {
                List<ChannelConfigParamsDO> differentParamsDOList =
                        ChannelConfigParamsDTO.convertToChannelConfigParamsDOBatch(differentParams, channelConfigDO.getId());
                channelConfigParamsService.updateConfigParamsBatch(differentParamsDOList);
            }

            if (!CollectionUtils.isEmpty(deleteParams)) {
                List<ChannelConfigParamsDO> deleteParamsDOList =
                        ChannelConfigParamsDTO.convertToChannelConfigParamsDOBatch(deleteParams, channelConfigDO.getId());
                channelConfigParamsService.deleteConfigParamsBatch(deleteParamsDOList);
            }
        }

        //3.禁用渠道配置，需要同时禁用其下对应的所有的渠道模板、渠道签名、短信服务模板（通过渠道模板、以及渠道签名的禁用来级联禁用短信服务模板）
        //禁用所属的渠道模板
        channelTemplateService.updateTemplateStatusByChannelConfigId(channelConfigDTO.getId(), CommonStatus.DISABLE.getStatus());
        //禁用所属的渠道签名
        channelSignatureService.updateSignatureStatusByChannelConfigId(channelConfigDO.getId(), CommonStatus.DISABLE.getStatus());
    }


    @Override
    public Page<ChannelConfigDTO> configs(String name, ChannelConfigType channelConfigType, Integer status,
                                          Integer currentPage, Integer pageSize) {
        currentPage = null == currentPage ? SmsConstant.DEFAULT_CURRENT_PAGE : currentPage;
        pageSize = null == pageSize ? SmsConstant.DEFAULT_PAGE_SIZE : pageSize;

        Integer type = null;
        if (null != channelConfigType) {
            type = channelConfigType.getType();
        }

        if (!CommonStatus.inStatus(status)) status = null;

        Page<ChannelConfigDO> channelConfigDOPage = channelConfigDao.configs(name, type, status, currentPage, pageSize);

        List<ChannelConfigDTO> list = new ArrayList<>();
        if (null != channelConfigDOPage && !CollectionUtils.isEmpty(channelConfigDOPage.getItems())) {
            for (ChannelConfigDO channelConfigDO : channelConfigDOPage.getItems()) {
                ChannelConfigDTO channelConfigDTO = ChannelConfigDTO.convertChannelConfigDO(channelConfigDO);
                list.add(channelConfigDTO);
            }
            return new Page<>(list, channelConfigDOPage.getTotalCount(), pageSize, currentPage);
        }
        return new Page<>(list, 0, pageSize, currentPage);
    }


    @Override
    public List<ChannelConfigDTO> configs() {
        return configs(null, null);
    }


    @Override
    public List<ChannelConfigDTO> configs(String name, ChannelConfigType channelConfigType) {
        Integer type = null;
        if (null != channelConfigType) {
            type = channelConfigType.getType();
        }

        List<ChannelConfigDO> channelConfigDOList = channelConfigDao.configs(name, type);
        List<ChannelConfigDTO> list = new ArrayList<>();
        if (CollectionUtils.isEmpty(channelConfigDOList)) return list;

        for (ChannelConfigDO channelConfigDO : channelConfigDOList) {
            ChannelConfigDTO channelConfigDTO = ChannelConfigDTO.convertChannelConfigDO(channelConfigDO);
            list.add(channelConfigDTO);
        }
        return list;
    }


    @Override
    @Transactional
    public ChannelConfigDetailDTO channelConfigDetail(Integer id) {
        if (null == id) return null;

        ChannelConfigDetailDTO channelConfigDetailDTO = new ChannelConfigDetailDTO();

        ChannelConfigDTO channelConfigDTO = config(id);
        if (null == channelConfigDTO) return null;
        channelConfigDetailDTO.setConfig(channelConfigDTO);

        List<ChannelConfigParamsDTO> channelConfigParamsDTOList = channelConfigParamsService.configParams(id);
        channelConfigDetailDTO.setParams(channelConfigParamsDTOList);
        return channelConfigDetailDTO;
    }


    @Override
    public ChannelConfigDTO config(Integer id) {
        if (null == id) return null;
        ChannelConfigDO channelConfigDO = channelConfigDao.config(id);
        if (null == channelConfigDO) return null;
        return ChannelConfigDTO.convertChannelConfigDO(channelConfigDO);
    }

    @Override
    public ChannelConfigDTO configWithinDeleted(Integer id) {
        if (null == id) return null;
        ChannelConfigDO channelConfigDO = channelConfigDao.configWithinDeleted(id);
        if (null == channelConfigDO) return null;
        return ChannelConfigDTO.convertChannelConfigDO(channelConfigDO);
    }

    @Override
    public List<ChannelConfigDTO> configsWithinDeletedByName(String name) {
        if (!StringUtils.hasText(name)) return null;

        List<ChannelConfigDO> channelConfigDOList = channelConfigDao.configsWithinDeletedByName(name);
        if (CollectionUtils.isEmpty(channelConfigDOList)) return null;

        List<ChannelConfigDTO> list = new ArrayList<>();
        for (ChannelConfigDO channelConfigDO : channelConfigDOList) {
            ChannelConfigDTO channelConfigDTO = ChannelConfigDTO.convertChannelConfigDO(channelConfigDO);
            if (null != channelConfigDTO) list.add(channelConfigDTO);
        }

        return list;
    }


    private boolean compareValuableValue(ChannelConfigDO existChannelConfigDO, ChannelConfigDO channelConfigDO) {
        if (existChannelConfigDO == channelConfigDO) return true;

        return existChannelConfigDO.getName().equals(channelConfigDO.getName())
                && existChannelConfigDO.getDesc().equals(channelConfigDO.getDesc())
                && existChannelConfigDO.getType().equals(channelConfigDO.getType())
                && existChannelConfigDO.getStatus().equals(channelConfigDO.getStatus());
    }
}
