package com.leonzhangxf.sms.dao.impl;

import com.leonzhangxf.sms.constant.SmsConstant;
import com.leonzhangxf.sms.dao.ChannelSignatureDao;
import com.leonzhangxf.sms.dao.mapper.ChannelSignatureMapper;
import com.leonzhangxf.sms.domain.ChannelSignatureDO;
import com.leonzhangxf.sms.domain.ChannelSignatureDOCriteria;
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
 * 渠道签名 Dao
 *
 * @author leonzhangxf 20180906
 */
@Repository
public class ChannelSignatureDaoImpl implements ChannelSignatureDao {

    private ChannelSignatureMapper channelSignatureMapper;

    @Autowired
    public void setChannelSignatureMapper(ChannelSignatureMapper channelSignatureMapper) {
        this.channelSignatureMapper = channelSignatureMapper;
    }


    @Override
    public ChannelSignatureDO saveSignature(ChannelSignatureDO channelSignatureDO) {
        if (null == channelSignatureDO || null == channelSignatureDO.getChnlConfigId()
                || null == channelSignatureDO.getSignature())
            throw new SaveException("保存渠道签名缺少必要参数！");

        if (!CommonStatus.inStatus(channelSignatureDO.getStatus()))
            channelSignatureDO.setStatus(CommonStatus.DISABLE.getStatus());

        int result = channelSignatureMapper.insertSelective(channelSignatureDO);
        if (result <= 0) throw new SaveException("保存渠道签名失败！");
        return channelSignatureDO;
    }

    @Override
    public void deleteSignature(Integer id) {
        if (null == id) throw new DeleteException("删除渠道签名缺少必要参数！");

        ChannelSignatureDO channelSignatureDO = new ChannelSignatureDO();
        channelSignatureDO.setId(id);
        channelSignatureDO.setStatus(CommonStatus.DISABLE.getStatus());
        channelSignatureDO.setDeleteFlag(DeleteFlag.DELETED.getDeleteFlag());
        channelSignatureMapper.updateByPrimaryKeySelective(channelSignatureDO);
    }

    @Override
    public void updateSignature(ChannelSignatureDO channelSignatureDO) {
        if (null == channelSignatureDO || null == channelSignatureDO.getId()
                || null == channelSignatureDO.getChnlConfigId()
                || !StringUtils.hasText(channelSignatureDO.getSignature()))
            return;

        if (!CommonStatus.inStatus(channelSignatureDO.getStatus()))
            channelSignatureDO.setStatus(CommonStatus.DISABLE.getStatus());

        channelSignatureMapper.updateByPrimaryKeySelective(channelSignatureDO);
    }

    @Override
    public void updateSignatureStatusByChannelConfigId(Integer channelConfigId, Integer targetStatus) {
        if (null == channelConfigId || !CommonStatus.inStatus(targetStatus)) return;

        ChannelSignatureDOCriteria criteria = new ChannelSignatureDOCriteria();
        criteria.createCriteria().andDeleteFlagEqualTo(DeleteFlag.NO_DELETED.getDeleteFlag())
                .andChnlConfigIdEqualTo(channelConfigId);

        ChannelSignatureDO channelSignatureDO = new ChannelSignatureDO();
        channelSignatureDO.setStatus(targetStatus);

        channelSignatureMapper.updateByExampleSelective(channelSignatureDO, criteria);
    }

    @Override
    public Page<ChannelSignatureDO> signatures(Integer channelConfigId, String signature, Integer status,
                                               Integer currentPage, Integer pageSize) {
        currentPage = null == currentPage ? SmsConstant.DEFAULT_CURRENT_PAGE : currentPage;
        pageSize = null == pageSize ? SmsConstant.DEFAULT_PAGE_SIZE : pageSize;

        ChannelSignatureDOCriteria criterion = new ChannelSignatureDOCriteria();

        ChannelSignatureDOCriteria.Criteria criteria = criterion.createCriteria()
                .andDeleteFlagEqualTo(DeleteFlag.NO_DELETED.getDeleteFlag());

        if (null != channelConfigId) criteria.andChnlConfigIdEqualTo(channelConfigId);
        if (StringUtils.hasText(signature)) criteria.andSignatureLike("%" + signature + "%");
        if (CommonStatus.inStatus(status)) criteria.andStatusEqualTo(status);

        criterion.setOrderByClause("create_time desc");

        com.github.pagehelper.Page<ChannelSignatureDO> page = PageHelper.startPage(currentPage, pageSize);
        List<ChannelSignatureDO> channelSignatureDOList = channelSignatureMapper.selectByExample(criterion);

        if (CollectionUtils.isEmpty(channelSignatureDOList))
            return new Page<>(channelSignatureDOList, 0, pageSize, currentPage);

        return new Page<>(channelSignatureDOList, Long.valueOf(page.getTotal()).intValue(), pageSize, currentPage);
    }

    @Override
    public List<ChannelSignatureDO> signatures(Integer channelConfigId) {
        if (null == channelConfigId) return null;

        ChannelSignatureDOCriteria criteria = new ChannelSignatureDOCriteria();
        criteria.createCriteria().andDeleteFlagEqualTo(DeleteFlag.NO_DELETED.getDeleteFlag())
                .andChnlConfigIdEqualTo(channelConfigId);
        return channelSignatureMapper.selectByExample(criteria);
    }

    @Override
    public List<ChannelSignatureDO> signatures() {
        ChannelSignatureDOCriteria criteria = new ChannelSignatureDOCriteria();
        criteria.createCriteria().andDeleteFlagEqualTo(DeleteFlag.NO_DELETED.getDeleteFlag());
        return channelSignatureMapper.selectByExample(criteria);
    }

    @Override
    public ChannelSignatureDO signature(Integer id) {
        if (null == id) return null;

        ChannelSignatureDO channelSignatureDO = channelSignatureMapper.selectByPrimaryKey(id);

        return null == channelSignatureDO
                || channelSignatureDO.getDeleteFlag().equals(DeleteFlag.DELETED.getDeleteFlag())
                ? null : channelSignatureDO;
    }
}
