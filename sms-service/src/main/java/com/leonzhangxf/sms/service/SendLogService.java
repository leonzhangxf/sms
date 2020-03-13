package com.leonzhangxf.sms.service;

import com.leonzhangxf.sms.domain.SendLogDO;
import com.leonzhangxf.sms.domain.dto.SendLogDTO;
import com.leonzhangxf.sms.domain.dto.SendStatisticsSummeryDTO;
import com.leonzhangxf.sms.enumeration.SendResponseStatus;
import com.leonzhangxf.sms.enumeration.TemplateUsage;
import com.leonzhangxf.sms.util.Page;

import java.util.List;

/**
 * 发送记录Service
 *
 * @author leonzhangxf 20181009
 */
public interface SendLogService {


    Page<SendLogDTO> logs(Integer clientId, Integer channelSignatureId, Integer channelTemplateId,
                          Integer templateId, TemplateUsage usage, SendResponseStatus status, Integer currentPage, Integer pageSize);


    SendStatisticsSummeryDTO statisticsSummery(List<Integer> hourList);

    void saveSendLog(SendLogDO sendLog);
}
