package com.leonzhangxf.sms.dao;

import com.leonzhangxf.sms.domain.SendLogDO;
import com.leonzhangxf.sms.domain.SendStatisticsSummeryDO;
import com.leonzhangxf.sms.enumeration.SendResponseStatus;
import com.leonzhangxf.sms.enumeration.TemplateUsage;
import com.leonzhangxf.sms.util.Page;

import java.util.List;

/**
 * 发送记录 Dao
 *
 * @author leonzhangxf 20181009
 */
public interface SendLogDao {


    Page<SendLogDO> logs(Integer clientId, Integer channelSignatureId, Integer channelTemplateId, Integer templateId, TemplateUsage usage, SendResponseStatus status, Integer currentPage, Integer pageSize);


    SendStatisticsSummeryDO statisticsSummery(List<Integer> hourList);

    void saveSendLog(SendLogDO sendLog);
}
