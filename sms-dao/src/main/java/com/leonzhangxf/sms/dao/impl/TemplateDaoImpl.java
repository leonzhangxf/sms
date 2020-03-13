package com.leonzhangxf.sms.dao.impl;

import com.leonzhangxf.sms.dao.TemplateDao;
import com.leonzhangxf.sms.dao.mapper.TemplateMapper;
import com.leonzhangxf.sms.domain.TemplateDO;
import com.leonzhangxf.sms.domain.TemplateDOCriteria;
import com.leonzhangxf.sms.enumeration.CommonStatus;
import com.leonzhangxf.sms.enumeration.DeleteFlag;
import com.leonzhangxf.sms.enumeration.TemplateUsage;
import com.leonzhangxf.sms.exception.DeleteException;
import com.leonzhangxf.sms.exception.SaveException;
import com.leonzhangxf.sms.exception.UpdateException;
import com.leonzhangxf.sms.util.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 短信服务模板
 *
 * @author leonzhangxf 20180906
 */
@Repository
public class TemplateDaoImpl implements TemplateDao {

    private TemplateMapper templateMapper;

    @Autowired
    public void setTemplateMapper(TemplateMapper templateMapper) {
        this.templateMapper = templateMapper;
    }

    @Override
    public void updateTemplateStatusByChannelSignatureId(Integer channelSignatureId, Integer targetStatus) {
        if (null == channelSignatureId || !CommonStatus.inStatus(targetStatus)) return;

        TemplateDOCriteria criteria = new TemplateDOCriteria();
        criteria.createCriteria().andDeleteFlagEqualTo(DeleteFlag.NO_DELETED.getDeleteFlag())
                .andChnlSignatureIdEqualTo(channelSignatureId);

        TemplateDO templateDO = new TemplateDO();
        templateDO.setStatus(targetStatus);
        templateMapper.updateByExampleSelective(templateDO, criteria);
    }

    @Override
    public void updateTemplateStatusByChannelSignatureIdList(List<Integer> channelSignatureDOIdList, Integer targetStatus) {
        if (CollectionUtils.isEmpty(channelSignatureDOIdList) || !CommonStatus.inStatus(targetStatus)) return;

        TemplateDOCriteria criteria = new TemplateDOCriteria();
        criteria.createCriteria().andDeleteFlagEqualTo(DeleteFlag.NO_DELETED.getDeleteFlag())
                .andChnlSignatureIdIn(channelSignatureDOIdList);

        TemplateDO templateDO = new TemplateDO();
        templateDO.setStatus(targetStatus);
        templateMapper.updateByExampleSelective(templateDO, criteria);
    }

    @Override
    public void updateTemplateStatusByChannelTemplateId(Integer channelTemplateId, Integer targetStatus) {
        if (null == channelTemplateId || !CommonStatus.inStatus(targetStatus)) return;

        TemplateDOCriteria criteria = new TemplateDOCriteria();
        criteria.createCriteria().andDeleteFlagEqualTo(DeleteFlag.NO_DELETED.getDeleteFlag())
                .andChnlTemplateIdEqualTo(channelTemplateId);

        TemplateDO templateDO = new TemplateDO();
        templateDO.setStatus(targetStatus);
        templateMapper.updateByExampleSelective(templateDO, criteria);
    }

    @Override
    public void updateTemplateStatusByChannelTemplateIdList(List<Integer> channelTemplateIdList, Integer targetStatus) {
        if (CollectionUtils.isEmpty(channelTemplateIdList) || !CommonStatus.inStatus(targetStatus)) return;

        TemplateDOCriteria criteria = new TemplateDOCriteria();
        criteria.createCriteria().andDeleteFlagEqualTo(DeleteFlag.NO_DELETED.getDeleteFlag())
                .andChnlTemplateIdIn(channelTemplateIdList);

        TemplateDO templateDO = new TemplateDO();
        templateDO.setStatus(targetStatus);
        templateMapper.updateByExampleSelective(templateDO, criteria);
    }

    @Override
    public List<TemplateDO> templatesWithinDeletedByTemplateId(String templateId) {
        if (!StringUtils.hasText(templateId)) return null;

        TemplateDOCriteria criteria = new TemplateDOCriteria();
        criteria.createCriteria().andTemplateIdEqualTo(templateId);
        return templateMapper.selectByExample(criteria);
    }

    @Override
    public Page<TemplateDO> templates(Integer clientId, Integer channelSignatureId, Integer channelTemplateId,
                                      String name, String templateId, TemplateUsage usage, Integer status,
                                      Integer currentPage, Integer pageSize) {
        com.github.pagehelper.Page<TemplateDO> page = PageHelper.startPage(currentPage, pageSize);

        TemplateDOCriteria criterion = new TemplateDOCriteria();
        TemplateDOCriteria.Criteria criteria = criterion.createCriteria()
                .andDeleteFlagEqualTo(DeleteFlag.NO_DELETED.getDeleteFlag());

        if (null != clientId)
            criteria.andClientIdEqualTo(clientId);

        if (null != channelSignatureId)
            criteria.andChnlSignatureIdEqualTo(channelSignatureId);

        if (null != channelTemplateId)
            criteria.andChnlTemplateIdEqualTo(channelTemplateId);

        if (StringUtils.hasText(name))
            criteria.andNameLike("%" + name + "%");

        if (StringUtils.hasText(templateId))
            criteria.andTemplateIdLike("%" + templateId + "%");

        if (null != usage)
            criteria.andUsageEqualTo(usage.getCode());

        if (CommonStatus.inStatus(status))
            criteria.andStatusEqualTo(status);

        List<TemplateDO> templateDOList = templateMapper.selectByExample(criterion);
        if (CollectionUtils.isEmpty(templateDOList))
            return new Page<>(templateDOList, 0, pageSize, currentPage);

        return new Page<>(templateDOList, Long.valueOf(page.getTotal()).intValue(), pageSize, currentPage);
    }

    @Override
    public void saveTemplate(TemplateDO templateDO) {
        if (null == templateDO
                || null == templateDO.getChnlTemplateId() || null == templateDO.getChnlSignatureId()
                || null == templateDO.getClientId() || null == templateDO.getTemplateId()
                || null == templateDO.getUsage() || !TemplateUsage.hasTemplateUsage(templateDO.getUsage()))
            throw new SaveException("保存短信服务模板缺少必要参数！");

        int result = templateMapper.insertSelective(templateDO);
        if (result < 0) throw new SaveException("保存短信服务模板失败！");
    }

    @Override
    public void deleteTemplate(Integer id) {
        if (null == id) throw new DeleteException("删除短信服务模板缺少必要参数！");

        TemplateDOCriteria criteria = new TemplateDOCriteria();
        criteria.createCriteria().andDeleteFlagEqualTo(DeleteFlag.NO_DELETED.getDeleteFlag())
                .andIdEqualTo(id);

        TemplateDO templateDO = new TemplateDO();
        templateDO.setDeleteFlag(DeleteFlag.DELETED.getDeleteFlag());
        templateMapper.updateByExampleSelective(templateDO, criteria);
    }

    @Override
    public TemplateDO template(Integer id) {
        if (null == id) return null;

        TemplateDO templateDO = templateMapper.selectByPrimaryKey(id);
        return null == templateDO || templateDO.getDeleteFlag().equals(DeleteFlag.DELETED.getDeleteFlag()) ?
                null : templateDO;
    }

    @Override
    public TemplateDO templateByTemplateId(String templateId) {
        if (!StringUtils.hasText(templateId)) return null;

        TemplateDOCriteria criteria = new TemplateDOCriteria();
        criteria.createCriteria().andDeleteFlagEqualTo(DeleteFlag.NO_DELETED.getDeleteFlag())
                .andTemplateIdEqualTo(templateId);

        List<TemplateDO> templateDOs = templateMapper.selectByExample(criteria);
        if (CollectionUtils.isEmpty(templateDOs)) return null;
        return templateDOs.get(0);
    }

    @Override
    public void updateTemplate(TemplateDO templateDO) {
        if (null == templateDO
                || null == templateDO.getChnlTemplateId() || null == templateDO.getChnlSignatureId()
                || null == templateDO.getClientId() || null == templateDO.getTemplateId()
                || null == templateDO.getUsage() || !TemplateUsage.hasTemplateUsage(templateDO.getUsage()))
            throw new SaveException("更新短信服务模板缺少必要参数！");

        int result = templateMapper.updateByPrimaryKeySelective(templateDO);
        if (result < 0) throw new UpdateException("更新短信服务模板失败！");
    }

    @Override
    public List<TemplateDO> templates() {
        TemplateDOCriteria criteria = new TemplateDOCriteria();
        criteria.createCriteria().andDeleteFlagEqualTo(DeleteFlag.NO_DELETED.getDeleteFlag());
        return templateMapper.selectByExample(criteria);
    }
}
