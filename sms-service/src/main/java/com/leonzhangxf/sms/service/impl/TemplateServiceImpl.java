package com.leonzhangxf.sms.service.impl;

import com.leonzhangxf.sms.constant.CacheConstant;
import com.leonzhangxf.sms.constant.SmsConstant;
import com.leonzhangxf.sms.dao.TemplateDao;
import com.leonzhangxf.sms.domain.TemplateDO;
import com.leonzhangxf.sms.domain.dto.ChannelSignatureDTO;
import com.leonzhangxf.sms.domain.dto.ChannelTemplateDTO;
import com.leonzhangxf.sms.domain.dto.ClientDTO;
import com.leonzhangxf.sms.domain.dto.TemplateDTO;
import com.leonzhangxf.sms.enumeration.CommonStatus;
import com.leonzhangxf.sms.enumeration.TemplateUsage;
import com.leonzhangxf.sms.exception.DeleteException;
import com.leonzhangxf.sms.exception.SaveException;
import com.leonzhangxf.sms.exception.UpdateException;
import com.leonzhangxf.sms.service.ChannelSignatureService;
import com.leonzhangxf.sms.service.ChannelTemplateService;
import com.leonzhangxf.sms.service.ClientService;
import com.leonzhangxf.sms.service.TemplateService;
import com.leonzhangxf.sms.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 短信服务模板
 *
 * @author leonzhangxf 20180906
 */
@Service
public class TemplateServiceImpl implements TemplateService {

    private TemplateDao templateDao;

    private ClientService clientService;

    private ChannelSignatureService channelSignatureService;

    private ChannelTemplateService channelTemplateService;

    @Autowired
    public void setTemplateDao(TemplateDao templateDao) {
        this.templateDao = templateDao;
    }

    @Autowired
    public void setClientService(ClientService clientService) {
        this.clientService = clientService;
    }

    @Autowired
    public void setChannelSignatureService(ChannelSignatureService channelSignatureService) {
        this.channelSignatureService = channelSignatureService;
    }

    @Autowired
    public void setChannelTemplateService(ChannelTemplateService channelTemplateService) {
        this.channelTemplateService = channelTemplateService;
    }

    @Override
    @Transactional
    public void updateTemplateStatusByChannelSignatureId(Integer channelSignatureId, Integer targetStatus) {
        if (null == channelSignatureId || !CommonStatus.inStatus(targetStatus)) return;
        templateDao.updateTemplateStatusByChannelSignatureId(channelSignatureId, targetStatus);
    }

    @Override
    @Transactional
    public void updateTemplateStatusByChannelSignatureIdList(List<Integer> channelSignatureDOIdList, Integer targetStatus) {
        if (CollectionUtils.isEmpty(channelSignatureDOIdList) || !CommonStatus.inStatus(targetStatus)) return;
        templateDao.updateTemplateStatusByChannelSignatureIdList(channelSignatureDOIdList, targetStatus);
    }

    @Override
    @Transactional
    public void updateTemplateStatusByChannelTemplateId(Integer channelTemplateId, Integer targetStatus) {
        if (null == channelTemplateId || !CommonStatus.inStatus(targetStatus)) return;
        templateDao.updateTemplateStatusByChannelTemplateId(channelTemplateId, targetStatus);
    }

    @Override
    @Transactional
    public void updateTemplateStatusByChannelTemplateIdList(List<Integer> channelTemplateIdList, Integer targetStatus) {
        if (CollectionUtils.isEmpty(channelTemplateIdList) || !CommonStatus.inStatus(targetStatus)) return;
        templateDao.updateTemplateStatusByChannelTemplateIdList(channelTemplateIdList, targetStatus);
    }

    @Override
    @Transactional
    public List<TemplateDTO> templatesWithinDeletedByTemplateId(String templateId) {
        if (!StringUtils.hasText(templateId)) return null;

        List<TemplateDO> templateDOList = templateDao.templatesWithinDeletedByTemplateId(templateId);
        if (CollectionUtils.isEmpty(templateDOList)) return null;

        return transToTemplateDTOs(templateDOList);
    }

    @Override
    @Transactional
    public Page<TemplateDTO> templates(Integer clientId, Integer channelSignatureId, Integer channelTemplateId,
                                       String name, String templateId, TemplateUsage usage, Integer status,
                                       Integer currentPage, Integer pageSize) {
        currentPage = null == currentPage ? SmsConstant.DEFAULT_CURRENT_PAGE : currentPage;
        pageSize = null == pageSize ? SmsConstant.DEFAULT_PAGE_SIZE : pageSize;

        if (!CommonStatus.inStatus(status)) status = null;

        Page<TemplateDO> page = templateDao.templates(clientId, channelSignatureId, channelTemplateId, name,
            templateId, usage, status, currentPage, pageSize);

        if (null == page || CollectionUtils.isEmpty(page.getItems()))
            return new Page<>(Collections.emptyList(), 0, pageSize, currentPage);

        List<TemplateDTO> list = transToTemplateDTOs(page.getItems());
        return new Page<>(list, page.getTotalCount(), pageSize, currentPage);
    }

    @Override
    @Transactional
    public List<TemplateDTO> templates() {
        List<TemplateDO> templateDOList = templateDao.templates();
        if (CollectionUtils.isEmpty(templateDOList)) return Collections.emptyList();
        return transToTemplateDTOs(templateDOList);
    }

    @Override
    @Transactional
    public void saveTemplate(TemplateDTO templateDTO) {
        if (null == templateDTO
            || null == templateDTO.getChannelTemplate() || null == templateDTO.getChannelTemplate().getId()
            || null == templateDTO.getChannelSignature() || null == templateDTO.getChannelSignature().getId()
            || null == templateDTO.getClient() || null == templateDTO.getClient().getId()
            || null == templateDTO.getTemplateId() || null == templateDTO.getUsage())
            throw new SaveException("保存短信服务模板参数缺少！");

        if (!CommonStatus.inStatus(templateDTO.getStatus())) templateDTO.setStatus(CommonStatus.DISABLE.getStatus());

        TemplateDO templateDO = TemplateDTO.convertToTemplateDO(templateDTO);
        if (null == templateDO) throw new SaveException("短信模板转换异常，请检查参数！");

        templateDao.saveTemplate(templateDO);
    }

    @Override
    @Transactional
    public void deleteTemplate(Integer id) {
        if (null == id) throw new DeleteException("删除短信服务模板缺少必要参数！");

        TemplateDO templateDO = templateDao.template(id);
        if (null == templateDO) throw new DeleteException("根据ID未找到短信服务模板！");

        templateDao.deleteTemplate(id);
    }

    @Override
    @Transactional
    public void updateTemplate(TemplateDTO templateDTO) {
        if (null == templateDTO
            || null == templateDTO.getChannelTemplate() || null == templateDTO.getChannelTemplate().getId()
            || null == templateDTO.getChannelSignature() || null == templateDTO.getChannelSignature().getId()
            || null == templateDTO.getClient() || null == templateDTO.getClient().getId()
            || null == templateDTO.getTemplateId() || null == templateDTO.getUsage())
            throw new UpdateException("修改短信服务模板参数缺少！");

        if (!CommonStatus.inStatus(templateDTO.getStatus())) templateDTO.setStatus(CommonStatus.DISABLE.getStatus());

        TemplateDO templateDO = TemplateDTO.convertToTemplateDO(templateDTO);
        if (null == templateDO) throw new UpdateException("短信模板更新时转换异常，请检查参数！");

        TemplateDO existTemplateDO = templateDao.template(templateDO.getId());
        if (null == existTemplateDO) throw new UpdateException("根据ID未查询到短信模板！");

        //启用短信模板，需要对应的接入方，渠道签名，渠道模板都为启用状态
        if (existTemplateDO.getStatus().equals(CommonStatus.DISABLE.getStatus())
            && templateDO.getStatus().equals(CommonStatus.ENABLE.getStatus())) {
            ClientDTO client = clientService.client(templateDO.getClientId());
            if (null == client || client.getStatus().equals(CommonStatus.DISABLE.getStatus()))
                throw new UpdateException("所关联的接入方需要启用！");

            ChannelSignatureDTO signature = channelSignatureService.signature(templateDO.getChnlSignatureId());
            if (null == signature || signature.getStatus().equals(CommonStatus.DISABLE.getStatus()))
                throw new UpdateException("所关联的渠道签名需要启用！");

            ChannelTemplateDTO channelTemplate = channelTemplateService.template(templateDO.getChnlTemplateId());
            if (null == channelTemplate || channelTemplate.getStatus().equals(CommonStatus.DISABLE.getStatus()))
                throw new UpdateException("所关联的渠道模板需要启用！");
        }

        templateDao.updateTemplate(templateDO);
    }


    @Override
    @Transactional
    public TemplateDTO template(Integer id) {
        if (null == id) return null;
        TemplateDO templateDO = templateDao.template(id);
        if (null == templateDO) return null;
        ClientDTO clientDTO = clientService.client(templateDO.getClientId());
        ChannelSignatureDTO channelSignatureDTO = channelSignatureService.signature(templateDO.getChnlSignatureId());
        ChannelTemplateDTO channelTemplateDTO = channelTemplateService.template(templateDO.getChnlTemplateId());
        return TemplateDTO.convertTemplateDO(templateDO, clientDTO, channelSignatureDTO, channelTemplateDTO);
    }


    /**
     * 此接口会被前台短信发送API调用，加上缓存。在后台修改缓存中保存的相关数据时会清除缓存。
     */
    @Override
    @Transactional
    @Cacheable(value = CacheConstant.TEMPLATE_SERVICE_TEMPLATE_BY_TEMPLATE_ID,
        key = CacheConstant.TEMPLATE_SERVICE_TEMPLATE_BY_TEMPLATE_ID + " + #root.args[0]")
    public TemplateDTO templateByTemplateId(String templateId) {
        if (!StringUtils.hasText(templateId)) return null;
        TemplateDO templateDO = templateDao.templateByTemplateId(templateId);
        if (null == templateDO) return null;
        ClientDTO clientDTO = clientService.client(templateDO.getClientId());
        ChannelSignatureDTO channelSignatureDTO = channelSignatureService.signature(templateDO.getChnlSignatureId());
        ChannelTemplateDTO channelTemplateDTO = channelTemplateService.template(templateDO.getChnlTemplateId());
        return TemplateDTO.convertTemplateDO(templateDO, clientDTO, channelSignatureDTO, channelTemplateDTO);
    }

    private List<TemplateDTO> transToTemplateDTOs(List<TemplateDO> items) {
        List<TemplateDTO> list = new ArrayList<>();
        for (TemplateDO templateDO : items) {
            ClientDTO clientDTO = clientService.client(templateDO.getClientId());
            ChannelSignatureDTO channelSignatureDTO = channelSignatureService.signature(templateDO.getChnlSignatureId());
            ChannelTemplateDTO channelTemplateDTO = channelTemplateService.template(templateDO.getChnlTemplateId());
            TemplateDTO templateDTO = TemplateDTO.convertTemplateDO(templateDO, clientDTO, channelSignatureDTO, channelTemplateDTO);
            if (null != templateDTO) list.add(templateDTO);
        }
        return list;
    }
}
