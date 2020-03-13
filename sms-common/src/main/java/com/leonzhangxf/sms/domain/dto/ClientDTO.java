package com.leonzhangxf.sms.domain.dto;

import com.leonzhangxf.sms.domain.ClientDO;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * 接入方
 *
 * @author leonzhangxf 20180905
 */
@ApiModel("接入方")
public class ClientDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("ID")
    private Integer id;

    @ApiModelProperty(value = "业务方标识编号", required = true)
    private String code;

    @ApiModelProperty(value = "名称", required = true)
    private String name;

    @ApiModelProperty(value = "密钥", required = true)
    private String key;

    @ApiModelProperty("描述")
    private String desc;

    @ApiModelProperty(value = "启禁用，0-禁用，1-启用", required = true)
    private Integer status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(hidden = true)
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(hidden = true)
    private Date updateTime;

    @ApiModelProperty(value = "0-未删除，1-已删除", hidden = true)
    private Integer deleteFlag;

    public static ClientDTO convertClientDO(ClientDO clientDO) {
        if (null == clientDO) return null;
        ClientDTO clientDTO = new ClientDTO();
        BeanUtils.copyProperties(clientDO, clientDTO);
        return clientDTO;
    }

    public static ClientDO convertToClientDO(ClientDTO clientDTO) {
        if (null == clientDTO) return null;
        ClientDO clientDO = new ClientDO();
        BeanUtils.copyProperties(clientDTO, clientDO);
        clientDO.setCreateTime(null);
        clientDO.setUpdateTime(null);
        return clientDO;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientDTO clientDTO = (ClientDTO) o;
        return Objects.equals(id, clientDTO.id) &&
                Objects.equals(code, clientDTO.code) &&
                Objects.equals(name, clientDTO.name) &&
                Objects.equals(key, clientDTO.key) &&
                Objects.equals(desc, clientDTO.desc) &&
                Objects.equals(status, clientDTO.status) &&
                Objects.equals(deleteFlag, clientDTO.deleteFlag);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, code, name, key, desc, status, deleteFlag);
    }

    @Override
    public String toString() {
        return "ClientDTO{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", key='" + key + '\'' +
                ", desc='" + desc + '\'' +
                ", status=" + status +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", deleteFlag=" + deleteFlag +
                '}';
    }
}
