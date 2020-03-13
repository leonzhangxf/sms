package com.leonzhangxf.sms.dao.impl;

import com.leonzhangxf.sms.dao.SendLogDao;
import com.leonzhangxf.sms.dao.mapper.SendLogMapper;
import com.leonzhangxf.sms.domain.SendLogDO;
import com.leonzhangxf.sms.domain.SendStatisticsSummeryDO;
import com.leonzhangxf.sms.enumeration.SendResponseStatus;
import com.leonzhangxf.sms.enumeration.TemplateUsage;
import com.leonzhangxf.sms.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

/**
 * 发送记录 Dao
 *
 * @author leonzhangxf 20181009
 */
@Repository
public class SendLogDaoImpl implements SendLogDao {

    private SendLogMapper sendLogMapper;

    @Autowired
    public void setSendLogMapper(SendLogMapper sendLogMapper) {
        this.sendLogMapper = sendLogMapper;
    }

    @Override
    public Page<SendLogDO> logs(Integer clientId, Integer channelSignatureId, Integer channelTemplateId,
                                Integer templateId, TemplateUsage usage,
                                SendResponseStatus status, Integer currentPage, Integer pageSize) {
        List<SendLogDO> list = sendLogMapper
            .logsPage(clientId, channelSignatureId, channelTemplateId, templateId, usage, status,
                currentPage, pageSize);

        Integer count = sendLogMapper.logsCount(clientId, channelSignatureId, channelTemplateId,
            templateId, usage, status);

        if (null == count || count <= 0) return new Page<>(Collections.emptyList(), 0,
            pageSize, currentPage);
        return new Page<>(list, count, pageSize, currentPage);
    }

    @Override
    public SendStatisticsSummeryDO statisticsSummery(List<Integer> hourList) {
        return sendLogMapper.statisticsSummery(hourList);
    }

    @Override
    public void saveSendLog(SendLogDO sendLog) {
        sendLogMapper.insertSelective(sendLog);
    }
}
