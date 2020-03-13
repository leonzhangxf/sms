package com.leonzhangxf.sms.api;

import com.leonzhangxf.sms.constant.CacheConstant;
import com.leonzhangxf.sms.constant.SmsConstant;
import com.leonzhangxf.sms.domain.dto.ClientDTO;
import com.leonzhangxf.sms.enumeration.CommonStatus;
import com.leonzhangxf.sms.service.ClientService;
import com.leonzhangxf.sms.util.KeyGenerateUtils;
import com.leonzhangxf.sms.util.Page;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 服务接入方管理接口列表
 *
 * @author leonzhangxf 20180906
 */
@Api(tags = {"接入方管理"})
@ApiResponses({
        @ApiResponse(code = 401, message = "Un Authorized", response = String.class),
        @ApiResponse(code = 403, message = "No Permission", response = String.class),
        @ApiResponse(code = 500, message = "Internal Server Error", response = String.class)
})
@RestController
@RequestMapping("api/service")
public class ClientAPI {

    private ClientService clientService;

    @Autowired
    public void setClientService(ClientService clientService) {
        this.clientService = clientService;
    }


    @ApiOperation(value = "保存接入方")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK", response = String.class)
    })
    @PostMapping("/clients")
    public ResponseEntity<String> saveClient(@ModelAttribute ClientDTO clientDTO) {
        ResponseEntity<String> res = validateClient(clientDTO, true);
        if (res != null) {
            return res;
        }

        clientService.saveClient(clientDTO);
        return ResponseEntity.ok("OK");
    }


    @ApiOperation(value = "删除接入方")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK", response = String.class)
    })
    @DeleteMapping("/clients/{id}")
    @CacheEvict(value = CacheConstant.TEMPLATE_SERVICE_TEMPLATE_BY_TEMPLATE_ID,
            allEntries = true, beforeInvocation = true)
    public ResponseEntity<String> deleteClient(@PathVariable("id") Integer id) {
        if (null == id) {
            return ResponseEntity.badRequest().body("缺少指定删除参数");
        }

        clientService.deleteClient(id);
        return ResponseEntity.ok("OK");
    }


    @ApiOperation(value = "修改接入方")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK", response = String.class)
    })
    @PutMapping("/clients/{id}")
    @CacheEvict(value = CacheConstant.TEMPLATE_SERVICE_TEMPLATE_BY_TEMPLATE_ID,
            allEntries = true, beforeInvocation = true)
    public ResponseEntity<String> updateClient(@PathVariable("id") Integer id,
                                               @ModelAttribute ClientDTO clientDTO) {
        if (null == id) {
            return ResponseEntity.badRequest().body("缺少指定更新参数");
        }
        clientDTO.setId(id);

        ResponseEntity<String> res = validateClient(clientDTO, false);
        if (res != null) {
            return res;
        }

        clientService.updateClient(clientDTO);
        return ResponseEntity.ok("OK");
    }

    @ApiOperation(value = "接入方分页列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "code", value = "接入方标识编号", dataTypeClass = String.class, paramType = "query"),
            @ApiImplicitParam(name = "name", value = "名称", dataTypeClass = String.class, paramType = "query"),
            @ApiImplicitParam(name = "status", value = "启禁用，0-禁用，1-启用", dataTypeClass = Integer.class,
                    paramType = "query", allowableValues = "0,1"),
            @ApiImplicitParam(name = "currentPage", value = "当前页", dataTypeClass = Integer.class, paramType = "path",
                    required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页数", dataTypeClass = Integer.class, paramType = "path",
                    required = true),
    })
    @GetMapping("/clients/{currentPage}/{pageSize}")
    public ResponseEntity<Page<ClientDTO>> clients(
            @RequestParam(required = false) String code,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer status,
            @PathVariable("currentPage") Integer currentPage,
            @PathVariable("pageSize") Integer pageSize) {
        currentPage = null == currentPage ? SmsConstant.DEFAULT_CURRENT_PAGE : currentPage;
        pageSize = null == pageSize ? SmsConstant.DEFAULT_PAGE_SIZE : pageSize;

        if (!CommonStatus.inStatus(status)) status = null;

        Page<ClientDTO> page = clientService.clients(code, name, status, currentPage, pageSize);
        return ResponseEntity.ok(page);
    }


    @ApiOperation(value = "生成接入方密钥")
    @GetMapping("/clients/key")
    public ResponseEntity<String> generateKey() {
        return ResponseEntity.ok(KeyGenerateUtils.generateKey());
    }


    /**
     * 对新增，或更新的入口参数进行校验
     *
     * @param clientDTO 接入方实体
     * @param creation  是否为新增操作校验
     * @return API响应信息，如果校验通过则返回null
     */
    private ResponseEntity<String> validateClient(ClientDTO clientDTO, boolean creation) {
        if (null == clientDTO) {
            return ResponseEntity.badRequest().body("参数不能为空！");
        }
        if (!StringUtils.hasText(clientDTO.getCode())) {
            return ResponseEntity.badRequest().body("接入方标识编号不能为空！");
        }
        if (!StringUtils.hasText(clientDTO.getName())) {
            return ResponseEntity.badRequest().body("接入方名称不能为空！");
        }
        if (!StringUtils.hasText(clientDTO.getKey())) {
            return ResponseEntity.badRequest().body("接入方密钥不能为空！");
        }
        if (!CommonStatus.inStatus(clientDTO.getStatus())) {
            return ResponseEntity.badRequest().body("接入方状态不能为空！");
        }

        //接入方标识编号code 全局唯一（包含删除）
        List<ClientDTO> existClientDTOList = clientService.clientsWithinDeletedByCode(clientDTO.getCode());
        if (creation) {
            if (!CollectionUtils.isEmpty(existClientDTOList)) {
                return ResponseEntity.badRequest().body("接入方名称已存在！接入方名称全局唯一，不能重复！");
            }
        } else {
            if (!CollectionUtils.isEmpty(existClientDTOList) && existClientDTOList.size() > 1) {
                return ResponseEntity.badRequest().body("接入方名称已存在！接入方名称全局唯一，不能重复！");
            }
        }

        return null;
    }
}
