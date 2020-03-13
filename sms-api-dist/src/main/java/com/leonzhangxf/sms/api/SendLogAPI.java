package com.leonzhangxf.sms.api;

import com.leonzhangxf.sms.constant.SmsConstant;
import com.leonzhangxf.sms.domain.dto.*;
import com.leonzhangxf.sms.enumeration.SendResponseStatus;
import com.leonzhangxf.sms.enumeration.TemplateUsage;
import com.leonzhangxf.sms.util.Page;
import com.leonzhangxf.sms.service.*;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Api(tags = {"发送记录"})
@ApiResponses({
        @ApiResponse(code = 401, message = "Un Authorized", response = String.class),
        @ApiResponse(code = 403, message = "No Permission", response = String.class),
        @ApiResponse(code = 500, message = "Internal Server Error", response = String.class)
})
@RestController
@RequestMapping("api/service")
public class SendLogAPI {

    private SendLogService sendLogService;

    private ClientService clientService;

    private ChannelSignatureService channelSignatureService;

    private ChannelTemplateService channelTemplateService;

    private TemplateService templateService;

    @Autowired
    public void setSendLogService(SendLogService sendLogService) {
        this.sendLogService = sendLogService;
    }

    @Autowired
    public void setClientService(ClientService clientService) {
        this.clientService = clientService;
    }

    @Autowired
    public void setChannelSignatureService(ChannelSignatureService channelSignatureService) {
        this.channelSignatureService = channelSignatureService;
    }

    @Autowired
    public void setChannelTemplateService(ChannelTemplateService channelTemplateService) {
        this.channelTemplateService = channelTemplateService;
    }

    @Autowired
    public void setTemplateService(TemplateService templateService) {
        this.templateService = templateService;
    }

    @ApiOperation(value = "短信发送记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "clientId", value = "接入方ID", dataTypeClass = Integer.class, paramType = "query"),
            @ApiImplicitParam(name = "channelSignatureId", value = "渠道签名ID", dataTypeClass = Integer.class,
                    paramType = "query"),
            @ApiImplicitParam(name = "channelTemplateId", value = "渠道模板ID", dataTypeClass = Integer.class,
                    paramType = "query"),
            @ApiImplicitParam(name = "templateId", value = "短信服务模板ID（主键）", dataTypeClass = Integer.class,
                    paramType = "query"),
            @ApiImplicitParam(name = "usage", value = "用途：1-验证，2-通知，3-推广", dataTypeClass = TemplateUsage.class,
                    paramType = "query"),
            @ApiImplicitParam(name = "status", value = "发送响应状态", dataTypeClass = SendResponseStatus.class,
                    paramType = "query"),
            @ApiImplicitParam(name = "currentPage", value = "当前页", dataTypeClass = Integer.class, paramType = "path",
                    required = true),
            @ApiImplicitParam(name = "pageSize", value = "每页数", dataTypeClass = Integer.class, paramType = "path",
                    required = true),
    })
    @GetMapping("/logs/{currentPage}/{pageSize}")
    public ResponseEntity<Page<SendLogDTO>> logs(
            @RequestParam(required = false) Integer clientId,
            @RequestParam(required = false) Integer channelSignatureId,
            @RequestParam(required = false) Integer channelTemplateId,
            @RequestParam(required = false) Integer templateId,
            @RequestParam(required = false) TemplateUsage usage,
            @RequestParam(required = false) SendResponseStatus status,
            @PathVariable("currentPage") Integer currentPage,
            @PathVariable("pageSize") Integer pageSize) {
        currentPage = null == currentPage ? SmsConstant.DEFAULT_CURRENT_PAGE : currentPage;
        pageSize = null == pageSize ? SmsConstant.DEFAULT_PAGE_SIZE : pageSize;

        Page<SendLogDTO> page =
                sendLogService.logs(clientId, channelSignatureId, channelTemplateId, templateId, usage, status, currentPage, pageSize);
        return ResponseEntity.ok(page);
    }

    /**
     * 统计数据-汇总数据：
     * 1小时
     * 3小时
     * 24小时
     * 短信发送成功率
     */
    @ApiOperation(value = "接入方列表")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK", response = SendStatisticsSummeryDTO.class)
    })
    @GetMapping("logs/statistics/summery")
    public ResponseEntity<SendStatisticsSummeryDTO> statisticsSummery() {
        List<Integer> hourList = new ArrayList<>();
        hourList.add(1);
        hourList.add(3);
        hourList.add(24);
        SendStatisticsSummeryDTO sendStatisticsSummeryDTO = sendLogService.statisticsSummery(hourList);
        return ResponseEntity.ok(sendStatisticsSummeryDTO);
    }

    @ApiOperation(value = "接入方列表")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK", response = ClientDTO.class, responseContainer = "List")
    })
    @GetMapping("logs/service/clients")
    public ResponseEntity<List<ClientDTO>> serviceClients() {
        return ResponseEntity.ok(clientService.clients());
    }


    @ApiOperation(value = "渠道签名列表")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK", response = ChannelSignatureDTO.class, responseContainer = "List")
    })
    @GetMapping("/logs/channel/signatures")
    public ResponseEntity<List<ChannelSignatureDTO>> channelSignatures() {
        return ResponseEntity.ok(channelSignatureService.signatures());
    }


    @ApiOperation(value = "渠道模板列表")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK", response = ChannelTemplateDTO.class, responseContainer = "List")
    })
    @GetMapping("/logs/channel/templates")
    public ResponseEntity<List<ChannelTemplateDTO>> channelTemplates() {
        return ResponseEntity.ok(channelTemplateService.templates());
    }

    @ApiOperation(value = "短信服务模板列表")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK", response = TemplateDTO.class, responseContainer = "List")
    })
    @GetMapping("/logs/service/templates")
    public ResponseEntity<List<TemplateDTO>> templates() {
        return ResponseEntity.ok(templateService.templates());
    }

    @ApiOperation(value = "模板用途列表")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK", response = TemplateUsage.class, responseContainer = "List")
    })
    @GetMapping("/logs/templates/template-usages")
    public ResponseEntity<List<TemplateUsage>> templateUsages() {
        return ResponseEntity.ok(Arrays.asList(TemplateUsage.values()));
    }

    @ApiOperation(value = "响应状态列表")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK", response = SendResponseStatus.class, responseContainer = "List")
    })
    @GetMapping("/logs/send-response-status")
    public ResponseEntity<List<SendResponseStatus>> sendResponseStatusList() {
        return ResponseEntity.ok(Arrays.asList(SendResponseStatus.values()));
    }
}
