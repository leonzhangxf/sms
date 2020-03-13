package com.leonzhangxf.sms.service;

import com.leonzhangxf.sms.domain.dto.ClientDTO;
import com.leonzhangxf.sms.util.Page;

import java.util.List;

/**
 * 服务计入方 Service
 *
 * @author leonzhangxf 20180906
 */
public interface ClientService {


    void saveClient(ClientDTO clientDTO);


    void deleteClient(Integer id);


    void updateClient(ClientDTO clientDTO);


    Page<ClientDTO> clients(String code, String name, Integer status, Integer currentPage, Integer pageSize);


    List<ClientDTO> clients();


    List<ClientDTO> clientsWithinDeletedByCode(String code);

    ClientDTO client(Integer id);

    ClientDTO clientByCode(String code);
}
