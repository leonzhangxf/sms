package com.leonzhangxf.sms.dao;

import com.leonzhangxf.sms.domain.ChannelSignatureDO;
import com.leonzhangxf.sms.util.Page;

import java.util.List;

/**
 * 渠道签名 Dao
 *
 * @author leonzhangxf 20180906
 */
public interface ChannelSignatureDao {

    ChannelSignatureDO saveSignature(ChannelSignatureDO channelSignatureDO);


    void deleteSignature(Integer id);


    void updateSignature(ChannelSignatureDO channelSignatureDO);


    void updateSignatureStatusByChannelConfigId(Integer channelConfigId, Integer targetStatus);


    Page<ChannelSignatureDO> signatures(Integer channelConfigId, String signature, Integer status, Integer currentPage, Integer pageSize);


    List<ChannelSignatureDO> signatures(Integer channelConfigId);


    List<ChannelSignatureDO> signatures();


    ChannelSignatureDO signature(Integer id);

}
