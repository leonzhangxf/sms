package com.leonzhangxf.sms.service;

import com.leonzhangxf.sms.domain.dto.ChannelSignatureDTO;
import com.leonzhangxf.sms.util.Page;

import java.util.List;

/**
 * 渠道签名 Service
 *
 * @author leonzhangxf 20180905
 */
public interface ChannelSignatureService {

    void saveSignature(ChannelSignatureDTO channelSignatureDTO);


    void deleteSignature(Integer id);


    void updateSignature(ChannelSignatureDTO channelSignatureDTO);


    void updateSignatureStatusByChannelConfigId(Integer channelConfigId, Integer targetStatus);


    Page<ChannelSignatureDTO> signatures(Integer channelConfigId, String signature, Integer status, Integer currentPage, Integer pageSize);


    List<ChannelSignatureDTO> signatures();


    ChannelSignatureDTO signature(Integer id);
}
