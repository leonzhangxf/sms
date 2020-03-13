package com.leonzhangxf.sms.domain.dto;

import com.leonzhangxf.sms.domain.SendStatisticsSummeryDO;
import com.leonzhangxf.sms.util.json.BigDecimalJsonSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * 包含：
 * 1小时，3小时，24小时
 * 短信发送成功率汇总统计
 *
 * @author leonzhangxf 20181009
 */
@ApiModel("短信发送统计-汇总数据")
public class SendStatisticsSummeryDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("最近1小时发送成功率")
    @JsonSerialize(using = BigDecimalJsonSerializer.class)
    private BigDecimal successRateFor1Hour;

    @ApiModelProperty("最近3小时发送成功率")
    @JsonSerialize(using = BigDecimalJsonSerializer.class)
    private BigDecimal successRateFor3Hour;

    @ApiModelProperty("最近24小时发送成功率")
    @JsonSerialize(using = BigDecimalJsonSerializer.class)
    private BigDecimal successRateFor24Hour;

    public static SendStatisticsSummeryDTO convertSendStatisticsSummeryDO(SendStatisticsSummeryDO sendStatisticsSummeryDO) {
        if (null == sendStatisticsSummeryDO) return null;
        SendStatisticsSummeryDTO sendStatisticsSummeryDTO = new SendStatisticsSummeryDTO();
        BeanUtils.copyProperties(sendStatisticsSummeryDO, sendStatisticsSummeryDTO);
        return sendStatisticsSummeryDTO;
    }

    public BigDecimal getSuccessRateFor1Hour() {
        return successRateFor1Hour;
    }

    public void setSuccessRateFor1Hour(BigDecimal successRateFor1Hour) {
        this.successRateFor1Hour = successRateFor1Hour;
    }

    public BigDecimal getSuccessRateFor3Hour() {
        return successRateFor3Hour;
    }

    public void setSuccessRateFor3Hour(BigDecimal successRateFor3Hour) {
        this.successRateFor3Hour = successRateFor3Hour;
    }

    public BigDecimal getSuccessRateFor24Hour() {
        return successRateFor24Hour;
    }

    public void setSuccessRateFor24Hour(BigDecimal successRateFor24Hour) {
        this.successRateFor24Hour = successRateFor24Hour;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SendStatisticsSummeryDTO that = (SendStatisticsSummeryDTO) o;
        return Objects.equals(successRateFor1Hour, that.successRateFor1Hour) &&
                Objects.equals(successRateFor3Hour, that.successRateFor3Hour) &&
                Objects.equals(successRateFor24Hour, that.successRateFor24Hour);
    }

    @Override
    public int hashCode() {
        return Objects.hash(successRateFor1Hour, successRateFor3Hour, successRateFor24Hour);
    }

    @Override
    public String toString() {
        return "SendStatisticsSummeryDTO{" +
                "successRateFor1Hour=" + successRateFor1Hour +
                ", successRateFor3Hour=" + successRateFor3Hour +
                ", successRateFor24Hour=" + successRateFor24Hour +
                '}';
    }
}
