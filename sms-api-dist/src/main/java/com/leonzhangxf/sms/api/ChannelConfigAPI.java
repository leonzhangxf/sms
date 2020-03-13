package com.leonzhangxf.sms.api;

import com.leonzhangxf.sms.constant.CacheConstant;
import com.leonzhangxf.sms.constant.SmsConstant;
import com.leonzhangxf.sms.domain.dto.ChannelConfigDTO;
import com.leonzhangxf.sms.domain.dto.ChannelConfigDetailDTO;
import com.leonzhangxf.sms.domain.dto.ChannelConfigParamsDTO;
import com.leonzhangxf.sms.enumeration.ChannelConfigParam;
import com.leonzhangxf.sms.enumeration.ChannelConfigType;
import com.leonzhangxf.sms.enumeration.CommonStatus;
import com.leonzhangxf.sms.service.ChannelConfigService;
import com.leonzhangxf.sms.util.Page;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * 渠道配置管理接口列表
 *
 * @author leonzhangxf 20180903
 */
@Api(tags = {"渠道配置管理"})
@ApiResponses({
        @ApiResponse(code = 401, message = "Un Authorized", response = String.class),
        @ApiResponse(code = 403, message = "No Permission", response = String.class),
        @ApiResponse(code = 500, message = "Internal Server Error", response = String.class)
})
@RestController
@RequestMapping("api/channel")
public class ChannelConfigAPI {

    private ChannelConfigService channelConfigService;

    @Autowired
    public void setChannelConfigService(ChannelConfigService channelConfigService) {
        this.channelConfigService = channelConfigService;
    }

    @ApiOperation(value = "保存渠道配置")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK", response = String.class)
    })
    @PostMapping("/configs")
    public ResponseEntity<String> saveConfig(@ModelAttribute ChannelConfigDetailDTO channelConfigDetailDTO) {
        ResponseEntity<String> res = validateConfig(channelConfigDetailDTO, true);
        if (res != null) {
            return res;
        }

        channelConfigService.saveConfig(channelConfigDetailDTO);
        return ResponseEntity.ok("OK");
    }


    /**
     * 删除渠道配置，需要同时禁用其下对应的所有的渠道模板、渠道签名、短信服务模板
     */
    @ApiOperation(value = "删除渠道配置")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK", response = String.class)
    })
    @DeleteMapping("/configs/{id}")
    @CacheEvict(value = CacheConstant.TEMPLATE_SERVICE_TEMPLATE_BY_TEMPLATE_ID,
            allEntries = true, beforeInvocation = true)
    public ResponseEntity<String> deleteConfig(@PathVariable("id") Integer id) {
        if (null == id) {
            return ResponseEntity.badRequest().body("缺少指定删除参数");
        }
        channelConfigService.deleteConfig(id);
        return ResponseEntity.ok("OK");
    }


    /**
     * 禁用渠道配置，需要同时禁用其下对应的所有的渠道模板、渠道签名、短信服务模板
     */
    @ApiOperation(value = "修改渠道配置")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK", response = String.class)
    })
    @PutMapping("/configs/{id}")
    @CacheEvict(value = CacheConstant.TEMPLATE_SERVICE_TEMPLATE_BY_TEMPLATE_ID,
            allEntries = true, beforeInvocation = true)
    public ResponseEntity<String> updateConfig(@PathVariable("id") Integer id,
                                               @ModelAttribute ChannelConfigDetailDTO channelConfigDetailDTO) {
        if (null == id) {
            return ResponseEntity.badRequest().body("缺少指定更新参数!");
        }
        ResponseEntity<String> res = validateConfig(channelConfigDetailDTO, false);
        if (res != null) {
            return res;
        }

        channelConfigDetailDTO.getConfig().setId(id);
        channelConfigService.updateConfig(channelConfigDetailDTO);
        return ResponseEntity.ok("OK");
    }


    @ApiOperation(value = "渠道配置分页列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "名称", dataTypeClass = String.class, paramType = "query"),
            @ApiImplicitParam(name = "type", value = "渠道账号配置类型", dataTypeClass = ChannelConfigType.class,
                    paramType = "query"),
            @ApiImplicitParam(name = "status", value = "启禁用，0-禁用，1-启用", dataTypeClass = Integer.class,
                    paramType = "query", allowableValues = "0,1"),
            @ApiImplicitParam(name = "currentPage", value = "当前页", dataTypeClass = Integer.class, paramType = "path",
                    required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页数", dataTypeClass = Integer.class, paramType = "path",
                    required = true),
    })
    @GetMapping("/configs/{currentPage}/{pageSize}")
    public ResponseEntity<Page<ChannelConfigDTO>> configs(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) ChannelConfigType type,
            @RequestParam(required = false) Integer status,
            @PathVariable("currentPage") Integer currentPage,
            @PathVariable("pageSize") Integer pageSize) {
        currentPage = null == currentPage ? SmsConstant.DEFAULT_CURRENT_PAGE : currentPage;
        pageSize = null == pageSize ? SmsConstant.DEFAULT_PAGE_SIZE : pageSize;

        if (!CommonStatus.inStatus(status)) {
            status = null;
        }

        Page<ChannelConfigDTO> channelConfigDTOPage = channelConfigService.configs(name, type, status, currentPage, pageSize);

        return ResponseEntity.ok(channelConfigDTOPage);
    }


    @ApiOperation(value = "渠道配置详情")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK", response = ChannelConfigDetailDTO.class)
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "渠道配置ID", dataTypeClass = Integer.class, paramType = "path"),
    })
    @GetMapping("/configs/detail/{id}")
    public ResponseEntity<ChannelConfigDetailDTO> configDetail(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(channelConfigService.channelConfigDetail(id));
    }


    @ApiOperation(value = "渠道配置类型")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK", response = ChannelConfigType.class, responseContainer = "List")
    })
    @GetMapping("/configs/types")
    public ResponseEntity<List<ChannelConfigType>> configTypes() {
        return ResponseEntity.ok(Arrays.asList(ChannelConfigType.values()));
    }


    @ApiOperation(value = "渠道配置类型参数")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "渠道配置类型", dataTypeClass = ChannelConfigType.class, paramType = "query"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK", response = ChannelConfigParam.class, responseContainer = "List")
    })
    @GetMapping("/configs/types/params")
    public ResponseEntity<List<ChannelConfigParam>> configTypeParams(@RequestParam("type") ChannelConfigType type) {
        return ResponseEntity.ok(ChannelConfigParam.getChannelConfigParams(type));
    }


    /**
     * 对新增，或更新的入口参数进行校验
     *
     * @param channelConfigDetailDTO 渠道配置详情
     * @param creation               是否是新增操作校验
     * @return API响应信息，如果校验通过则返回null
     */
    private ResponseEntity<String> validateConfig(ChannelConfigDetailDTO channelConfigDetailDTO,
                                                  boolean creation) {
        if (null == channelConfigDetailDTO) {
            return ResponseEntity.badRequest().body("参数不能为空！");
        }

        ChannelConfigDTO channelConfigDTO = channelConfigDetailDTO.getConfig();
        if (null == channelConfigDTO) {
            return ResponseEntity.badRequest().body("渠道配置不能为空！");
        }

        if (!StringUtils.hasText(channelConfigDTO.getName())) {
            return ResponseEntity.badRequest().body("渠道配置名称参数不能为空！");
        }
        if (!ChannelConfigType.contains(channelConfigDTO.getType())) {
            return ResponseEntity.badRequest().body("渠道配置类型参数不能为空或不支持的渠道配置类型！");
        }
        if (!CommonStatus.inStatus(channelConfigDTO.getStatus())) {
            return ResponseEntity.badRequest().body("渠道配置状态不能为空！");
        }

        //渠道配置名称全局唯一
        List<ChannelConfigDTO> existChannelConfigDTOList =
                channelConfigService.configsWithinDeletedByName(channelConfigDTO.getName());
        if (creation) {
            if (!CollectionUtils.isEmpty(existChannelConfigDTOList)) {
                return ResponseEntity.badRequest().body("渠道配置名称已经存在！");
            }
        } else {
            if (!CollectionUtils.isEmpty(existChannelConfigDTOList) && existChannelConfigDTOList.size() > 1) {
                return ResponseEntity.badRequest().body("渠道配置名称已经存在！");
            }
        }


        // 启用检查，对应渠道参数必须配置完全
        if (channelConfigDTO.getStatus().equals(1)) {
            switch (channelConfigDTO.getType()) {
                case ALIYUN: {
                    List<ChannelConfigParamsDTO> params = channelConfigDetailDTO.getParams();
                    if (CollectionUtils.isEmpty(params)) {
                        return ResponseEntity.badRequest().body("启用前需要配置完全对应渠道所需要的参数！");
                    }

                    //参数类型不完全匹配，或去重后参数个数不匹配都不算参数配置完全，或有参数的值仍然为空。
                    if (!params.stream().allMatch((channelConfigParams) -> ChannelConfigParam
                            .getChannelConfigParams(ChannelConfigType.ALIYUN).contains(channelConfigParams.getKey()))
                            ||
                            Long.valueOf(params.stream().distinct().count()).intValue() != ChannelConfigParam
                                    .getChannelConfigParams(ChannelConfigType.ALIYUN).size()
                            || !params.stream().allMatch((channelConfigParams) ->
                            StringUtils.hasText(channelConfigParams.getValue()))) {
                        return ResponseEntity.badRequest().body("启用前需要配置完全对应渠道所需要的参数！");
                    }

                    break;
                }
                default: {
                    return ResponseEntity.badRequest().body("不支持的渠道配置类型！");
                }
            }
        }

        return null;
    }
}
