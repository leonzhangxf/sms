package com.leonzhangxf.sms.service.impl;

import com.leonzhangxf.sms.dao.SendLogDao;
import com.leonzhangxf.sms.domain.SendLogDO;
import com.leonzhangxf.sms.domain.SendStatisticsSummeryDO;
import com.leonzhangxf.sms.domain.dto.SendLogDTO;
import com.leonzhangxf.sms.domain.dto.SendStatisticsSummeryDTO;
import com.leonzhangxf.sms.domain.dto.TemplateDTO;
import com.leonzhangxf.sms.enumeration.SendResponseStatus;
import com.leonzhangxf.sms.enumeration.TemplateUsage;
import com.leonzhangxf.sms.service.SendLogService;
import com.leonzhangxf.sms.service.TemplateService;
import com.leonzhangxf.sms.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 发送记录Service
 *
 * @author leonzhangxf 20181009
 */
@Service
public class SendLogServiceImpl implements SendLogService {

    private static final String MOBILE_SECURITY_REGEX = "(\\d{3})(\\d{4})(\\d{4})";

    private static final String MOBILE_SECURITY_REGEX_REPLACEMENT = "$1****$3";

    private static final String PARAMS_SECURITY_REGEX = ":\"[\\S&&[^\\\"]]+";

    private static final String PARAMS_SECURITY_REGEX_REPLACEMENT = ":\"****";

    private SendLogDao sendLogDao;

    private TemplateService templateService;

    @Autowired
    public void setSendLogDao(SendLogDao sendLogDao) {
        this.sendLogDao = sendLogDao;
    }

    @Autowired
    public void setTemplateService(TemplateService templateService) {
        this.templateService = templateService;
    }

    @Override
    @Transactional
    public Page<SendLogDTO> logs(Integer clientId, Integer channelSignatureId, Integer channelTemplateId,
                                 Integer templateId, TemplateUsage usage, SendResponseStatus status,
                                 Integer currentPage, Integer pageSize) {
        Page<SendLogDO> page = sendLogDao
            .logs(clientId, channelSignatureId, channelTemplateId, templateId, usage, status, currentPage, pageSize);

        if (null == page || CollectionUtils.isEmpty(page.getItems()))
            return new Page<>(Collections.emptyList(), 0, pageSize, currentPage);

        List<SendLogDTO> list = new ArrayList<>();
        for (SendLogDO sendLogDO : page.getItems()) {
            TemplateDTO templateDTO = templateService.template(sendLogDO.getTemplateId());
            SendLogDTO sendLogDTO = SendLogDTO.convertSendLogDO(sendLogDO, templateDTO);
            if (null != sendLogDTO) {
                // 手机号和参数列表脱敏处理
                /*if (StringUtils.hasText(sendLogDTO.getMobile())) {
                    sendLogDTO.setMobile(sendLogDTO.getMobile()
                        .replaceAll(MOBILE_SECURITY_REGEX, MOBILE_SECURITY_REGEX_REPLACEMENT));
                }*/

                if (StringUtils.hasText(sendLogDTO.getParams())) {
                    sendLogDTO.setParams(sendLogDTO.getParams()
                        .replaceAll(PARAMS_SECURITY_REGEX, PARAMS_SECURITY_REGEX_REPLACEMENT));
                }

                list.add(sendLogDTO);
            }
        }
        return new Page<>(list, page.getTotalCount(), pageSize, currentPage);
    }


    @Override
    @Transactional
    public SendStatisticsSummeryDTO statisticsSummery(List<Integer> hourList) {
        SendStatisticsSummeryDO sendStatisticsSummeryDO = sendLogDao.statisticsSummery(hourList);
        return SendStatisticsSummeryDTO.convertSendStatisticsSummeryDO(sendStatisticsSummeryDO);
    }

    @Override
    @Async
    @Transactional
    public void saveSendLog(SendLogDO sendLog) {
        sendLogDao.saveSendLog(sendLog);
    }
}
