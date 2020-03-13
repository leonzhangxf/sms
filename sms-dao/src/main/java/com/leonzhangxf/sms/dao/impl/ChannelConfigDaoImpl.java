package com.leonzhangxf.sms.dao.impl;

import com.leonzhangxf.sms.constant.SmsConstant;
import com.leonzhangxf.sms.dao.ChannelConfigDao;
import com.leonzhangxf.sms.dao.mapper.ChannelConfigMapper;
import com.leonzhangxf.sms.domain.ChannelConfigDO;
import com.leonzhangxf.sms.domain.ChannelConfigDOCriteria;
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
 * 渠道配置 Dao 实现
 *
 * @author leonzhangxf 20180903
 */
@Repository
public class ChannelConfigDaoImpl implements ChannelConfigDao {

    private ChannelConfigMapper channelConfigMapper;

    @Autowired
    public void setChannelConfigMapper(ChannelConfigMapper channelConfigMapper) {
        this.channelConfigMapper = channelConfigMapper;
    }


    @Override
    public ChannelConfigDO saveConfig(ChannelConfigDO channelConfigDO) {
        if (null == channelConfigDO
                || !StringUtils.hasText(channelConfigDO.getName())
                || null == channelConfigDO.getType())
            throw new SaveException("缺少渠道配置必要参数！");

        if (!CommonStatus.inStatus(channelConfigDO.getStatus()))
            channelConfigDO.setStatus(CommonStatus.DISABLE.getStatus());

        int result = channelConfigMapper.insertSelective(channelConfigDO);
        if (result <= 0) throw new SaveException("保存渠道配置失败！");
        return channelConfigDO;
    }


    @Override
    public void deleteConfig(Integer id) {
        if (null == id) throw new DeleteException("删除渠道配置缺少必要参数！");
        ChannelConfigDO channelConfigDO = new ChannelConfigDO();
        channelConfigDO.setId(id);
        channelConfigDO.setStatus(CommonStatus.DISABLE.getStatus());//禁用
        channelConfigDO.setDeleteFlag(DeleteFlag.DELETED.getDeleteFlag());//删除
        channelConfigMapper.updateByPrimaryKeySelective(channelConfigDO);
    }


    @Override
    public void updateConfig(ChannelConfigDO channelConfigDO) {
        if (null == channelConfigDO) return;
        channelConfigMapper.updateByPrimaryKeySelective(channelConfigDO);
    }

    @Override
    public Page<ChannelConfigDO> configs(String name, Integer type, Integer status,
                                         Integer currentPage, Integer pageSize) {
        currentPage = null == currentPage ? SmsConstant.DEFAULT_CURRENT_PAGE : currentPage;
        pageSize = null == pageSize ? SmsConstant.DEFAULT_PAGE_SIZE : pageSize;

        ChannelConfigDOCriteria criterion = new ChannelConfigDOCriteria();
        ChannelConfigDOCriteria.Criteria criteria = criterion.createCriteria()
                .andDeleteFlagEqualTo(DeleteFlag.NO_DELETED.getDeleteFlag());
        if (StringUtils.hasText(name)) {
            criteria.andNameLike("%" + name + "%");
        }
        if (null != type) {
            criteria.andTypeEqualTo(type);
        }
        if (null != status) {
            criteria.andStatusEqualTo(status);
        }
        criterion.setOrderByClause("create_time desc");

        com.github.pagehelper.Page<ChannelConfigDO> page = PageHelper.startPage(currentPage, pageSize);
        List<ChannelConfigDO> channelConfigDOList = channelConfigMapper.selectByExample(criterion);

        return new Page<>(channelConfigDOList, Long.valueOf(page.getTotal()).intValue(), pageSize, currentPage);
    }


    @Override
    public List<ChannelConfigDO> configs(String name, Integer type) {
        ChannelConfigDOCriteria criterion = new ChannelConfigDOCriteria();
        ChannelConfigDOCriteria.Criteria criteria = criterion.createCriteria()
                .andDeleteFlagEqualTo(DeleteFlag.NO_DELETED.getDeleteFlag());
        if (StringUtils.hasText(name)) {
            criteria.andNameLike("%" + name + "%");
        }
        if (null != type) {
            criteria.andTypeEqualTo(type);
        }
        criterion.setOrderByClause("create_time desc");

        return channelConfigMapper.selectByExample(criterion);
    }


    @Override
    public ChannelConfigDO config(Integer id) {
        if (null == id) return null;
        ChannelConfigDO channelConfigDO = channelConfigMapper.selectByPrimaryKey(id);
        return null == channelConfigDO || channelConfigDO.getDeleteFlag().equals(1) ? null : channelConfigDO;
    }


    @Override
    public ChannelConfigDO configWithinDeleted(Integer id) {
        if (null == id) return null;
        return channelConfigMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<ChannelConfigDO> configsWithinDeletedByName(String name) {
        if (!StringUtils.hasText(name)) return null;

        ChannelConfigDOCriteria criterion = new ChannelConfigDOCriteria();
        criterion.createCriteria().andNameEqualTo(name);

        List<ChannelConfigDO> channelConfigDOList = channelConfigMapper.selectByExample(criterion);
        if (CollectionUtils.isEmpty(channelConfigDOList)) return null;
        return channelConfigDOList;
    }
}
