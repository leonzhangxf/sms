package com.leonzhangxf.sms.dao.impl;

import com.leonzhangxf.sms.constant.SmsConstant;
import com.leonzhangxf.sms.dao.ChannelTemplateDao;
import com.leonzhangxf.sms.dao.mapper.ChannelTemplateMapper;
import com.leonzhangxf.sms.domain.ChannelTemplateDO;
import com.leonzhangxf.sms.domain.ChannelTemplateDOCriteria;
import com.leonzhangxf.sms.enumeration.CommonStatus;
import com.leonzhangxf.sms.enumeration.DeleteFlag;
import com.leonzhangxf.sms.exception.DeleteException;
import com.leonzhangxf.sms.exception.SaveException;
import com.leonzhangxf.sms.util.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 渠道模板 Dao
 *
 * @author leonzhangxf 20180904
 */
@Repository
public class ChannelTemplateDaoImpl implements ChannelTemplateDao {

    private ChannelTemplateMapper channelTemplateMapper;

    @Autowired
    public void setChannelTemplateMapper(ChannelTemplateMapper channelTemplateMapper) {
        this.channelTemplateMapper = channelTemplateMapper;
    }

    @Override
    public ChannelTemplateDO saveTemplate(ChannelTemplateDO channelTemplateDO) {
        if (null == channelTemplateDO || null == channelTemplateDO.getChnlConfigId()
                || !StringUtils.hasText(channelTemplateDO.getName())
                || !StringUtils.hasText(channelTemplateDO.getCode())
                || !StringUtils.hasText(channelTemplateDO.getContent()))
            throw new SaveException("保存渠道模板缺少必要参数！");

        if (!CommonStatus.inStatus(channelTemplateDO.getStatus()))
            channelTemplateDO.setStatus(CommonStatus.DISABLE.getStatus());

        int result = channelTemplateMapper.insertSelective(channelTemplateDO);
        if (result <= 0) throw new SaveException("保存渠道模板失败！");
        return channelTemplateDO;
    }

    @Override
    public void deleteTemplate(Integer id) {
        if (null == id) throw new DeleteException("删除渠道模板缺少必要参数！");

        ChannelTemplateDO channelTemplateDO = new ChannelTemplateDO();
        channelTemplateDO.setId(id);
        channelTemplateDO.setStatus(CommonStatus.DISABLE.getStatus());
        channelTemplateDO.setDeleteFlag(DeleteFlag.DELETED.getDeleteFlag());
        channelTemplateMapper.updateByPrimaryKeySelective(channelTemplateDO);
    }

    @Override
    public void updateTemplate(ChannelTemplateDO channelTemplateDO) {
        if (null == channelTemplateDO || null == channelTemplateDO.getId()
                || null == channelTemplateDO.getChnlConfigId()
                || !StringUtils.hasText(channelTemplateDO.getName())
                || !StringUtils.hasText(channelTemplateDO.getCode())
                || !StringUtils.hasText(channelTemplateDO.getContent()))
            return;

        if (!CommonStatus.inStatus(channelTemplateDO.getStatus()))
            channelTemplateDO.setStatus(CommonStatus.DISABLE.getStatus());

        channelTemplateMapper.updateByPrimaryKeySelective(channelTemplateDO);
    }

    @Override
    public void updateTemplateStatusByChannelConfigId(Integer channelConfigId, Integer targetStatus) {
        if (null == channelConfigId || !CommonStatus.inStatus(targetStatus)) return;

        ChannelTemplateDOCriteria criterion = new ChannelTemplateDOCriteria();
        criterion.createCriteria().andDeleteFlagEqualTo(DeleteFlag.NO_DELETED.getDeleteFlag())
                .andChnlConfigIdEqualTo(channelConfigId);

        ChannelTemplateDO channelTemplateDO = new ChannelTemplateDO();
        channelTemplateDO.setStatus(targetStatus);

        channelTemplateMapper.updateByExampleSelective(channelTemplateDO, criterion);
    }

    @Override
    public Page<ChannelTemplateDO> templates(Integer channelConfigId, String name, String code,
                                             Integer status, Integer currentPage, Integer pageSize) {
        currentPage = null == currentPage ? SmsConstant.DEFAULT_CURRENT_PAGE : currentPage;
        pageSize = null == pageSize ? SmsConstant.DEFAULT_PAGE_SIZE : pageSize;

        ChannelTemplateDOCriteria criterion = new ChannelTemplateDOCriteria();

        ChannelTemplateDOCriteria.Criteria criteria = criterion.createCriteria()
                .andDeleteFlagEqualTo(DeleteFlag.NO_DELETED.getDeleteFlag());

        if (null != channelConfigId) criteria.andChnlConfigIdEqualTo(channelConfigId);
        if (StringUtils.hasText(name)) criteria.andNameLike("%" + name + "%");
        if (StringUtils.hasText(code)) criteria.andCodeLike("%" + code + "%");
        if (null != status && CommonStatus.inStatus(status)) criteria.andStatusEqualTo(status);

        criterion.setOrderByClause("create_time desc");

        com.github.pagehelper.Page<ChannelTemplateDO> page = PageHelper.startPage(currentPage, pageSize);
        List<ChannelTemplateDO> channelTemplateDOList = channelTemplateMapper.selectByExample(criterion);

        if (CollectionUtils.isEmpty(channelTemplateDOList))
            return new Page<>(channelTemplateDOList, 0, pageSize, currentPage);

        return new Page<>(channelTemplateDOList, Long.valueOf(page.getTotal()).intValue(), pageSize, currentPage);
    }

    @Override
    public List<ChannelTemplateDO> templates(Integer channelConfigId) {
        if (null == channelConfigId) return null;

        ChannelTemplateDOCriteria criteria = new ChannelTemplateDOCriteria();
        criteria.createCriteria().andDeleteFlagEqualTo(DeleteFlag.NO_DELETED.getDeleteFlag())
                .andChnlConfigIdEqualTo(channelConfigId);
        return channelTemplateMapper.selectByExample(criteria);
    }

    @Override
    public List<ChannelTemplateDO> templates() {
        ChannelTemplateDOCriteria criteria = new ChannelTemplateDOCriteria();
        criteria.createCriteria().andDeleteFlagEqualTo(DeleteFlag.NO_DELETED.getDeleteFlag());
        return channelTemplateMapper.selectByExample(criteria);
    }

    @Override
    public ChannelTemplateDO template(Integer id) {
        if (null == id) return null;

        ChannelTemplateDO channelTemplateDO = channelTemplateMapper.selectByPrimaryKey(id);
        return null == channelTemplateDO
                || channelTemplateDO.getDeleteFlag().equals(DeleteFlag.DELETED.getDeleteFlag())
                ? null : channelTemplateDO;
    }
}
