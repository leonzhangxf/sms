package com.leonzhangxf.sms.service.impl;

import com.leonzhangxf.sms.constant.SmsConstant;
import com.leonzhangxf.sms.dao.ClientDao;
import com.leonzhangxf.sms.domain.ClientDO;
import com.leonzhangxf.sms.domain.dto.ClientDTO;
import com.leonzhangxf.sms.enumeration.CommonStatus;
import com.leonzhangxf.sms.exception.SaveException;
import com.leonzhangxf.sms.exception.UpdateException;
import com.leonzhangxf.sms.service.ClientService;
import com.leonzhangxf.sms.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 服务计入方 Service
 *
 * @author leonzhangxf 20180906
 */
@Service
public class ClientServiceImpl implements ClientService {

    private ClientDao clientDao;

    @Autowired
    public void setClientDao(ClientDao clientDao) {
        this.clientDao = clientDao;
    }

    @Override
    @Transactional
    public void saveClient(ClientDTO clientDTO) {
        if (null == clientDTO || !StringUtils.hasText(clientDTO.getCode())
                || !StringUtils.hasText(clientDTO.getCode())
                || !StringUtils.hasText(clientDTO.getName())
                || !StringUtils.hasText(clientDTO.getKey()))
            throw new SaveException("保存调用方缺少参数！");

        if (!CommonStatus.inStatus(clientDTO.getStatus())) clientDTO.setStatus(CommonStatus.DISABLE.getStatus());

        ClientDO clientDO = ClientDTO.convertToClientDO(clientDTO);
        if (null == clientDO) throw new SaveException("调用方转换异常，请检查参数！");

        clientDao.saveClient(clientDO);
    }

    @Override
    @Transactional
    public void deleteClient(Integer id) {
        if (null == id) return;

        ClientDO clientDO = clientDao.client(id);
        if (null == clientDO) return;

        clientDao.deleteClient(id);
    }

    @Override
    @Transactional
    public void updateClient(ClientDTO clientDTO) {
        if (null == clientDTO || null == clientDTO.getId()
                || !StringUtils.hasText(clientDTO.getCode())
                || !StringUtils.hasText(clientDTO.getName())
                || !StringUtils.hasText(clientDTO.getKey()))
            throw new UpdateException("更新调用方，必要参数不能为空！");

        if (!CommonStatus.inStatus(clientDTO.getStatus())) clientDTO.setStatus(CommonStatus.DISABLE.getStatus());

        ClientDO clientDO = ClientDTO.convertToClientDO(clientDTO);
        if (null == clientDO) throw new UpdateException("更新渠道配置，转换异常，请检查参数！");

        clientDao.updateClient(clientDO);
    }

    @Override
    public Page<ClientDTO> clients(String code, String name, Integer status, Integer currentPage, Integer pageSize) {
        currentPage = null == currentPage ? SmsConstant.DEFAULT_CURRENT_PAGE : currentPage;
        pageSize = null == pageSize ? SmsConstant.DEFAULT_PAGE_SIZE : pageSize;

        if (!CommonStatus.inStatus(status)) status = null;

        Page<ClientDO> clientDOPage = clientDao.clients(code, name, status, currentPage, pageSize);

        List<ClientDTO> list = new ArrayList<>();
        if (null == clientDOPage || CollectionUtils.isEmpty(clientDOPage.getItems()))
            return new Page<>(list, 0, pageSize, currentPage);

        for (ClientDO clientDO : clientDOPage.getItems()) {
            ClientDTO clientDTO = ClientDTO.convertClientDO(clientDO);
            list.add(clientDTO);
        }

        return new Page<>(list, clientDOPage.getTotalCount(), pageSize, currentPage);
    }

    @Override
    public List<ClientDTO> clients() {
        List<ClientDO> clientDOList = clientDao.clients();
        if (CollectionUtils.isEmpty(clientDOList)) return null;

        List<ClientDTO> list = new ArrayList<>();
        for (ClientDO clientDO : clientDOList) {
            ClientDTO clientDTO = ClientDTO.convertClientDO(clientDO);
            if (null != clientDTO) list.add(clientDTO);
        }
        return list;
    }

    @Override
    public List<ClientDTO> clientsWithinDeletedByCode(String code) {
        if (!StringUtils.hasText(code)) return null;

        List<ClientDO> clientDOList = clientDao.clientsWithinDeletedByCode(code);
        if (CollectionUtils.isEmpty(clientDOList)) return null;

        List<ClientDTO> list = new ArrayList<>();
        for (ClientDO clientDO : clientDOList) {
            ClientDTO clientDTO = ClientDTO.convertClientDO(clientDO);
            if (null != clientDTO) list.add(clientDTO);
        }
        return list;
    }

    @Override
    public ClientDTO client(Integer id) {
        if (null == id) return null;
        ClientDO clientDO = clientDao.client(id);
        if (null == clientDO) return null;
        return ClientDTO.convertClientDO(clientDO);
    }

    @Override
    public ClientDTO clientByCode(String code) {
        if (StringUtils.hasText(code)) return null;
        ClientDO clientDO = clientDao.clientByCode(code);
        if (null == clientDO) return null;
        return ClientDTO.convertClientDO(clientDO);
    }
}
