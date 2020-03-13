package com.leonzhangxf.sms.dao;

import com.leonzhangxf.sms.domain.ClientDO;
import com.leonzhangxf.sms.util.Page;

import java.util.List;

/**
 * 接入方 Dao
 *
 * @author leonzhangxf 20180906
 */
public interface ClientDao {


    ClientDO saveClient(ClientDO clientDO);


    void deleteClient(Integer id);


    void updateClient(ClientDO clientDO);


    Page<ClientDO> clients(String code, String name, Integer status, Integer currentPage, Integer pageSize);


    List<ClientDO> clients();


    ClientDO client(Integer id);


    ClientDO clientByCode(String code);


    List<ClientDO> clientsWithinDeletedByCode(String code);

}
