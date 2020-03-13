package com.leonzhangxf.sms.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * 短信发送统计-汇总数据
 * 包含：
 * 1小时，3小时，24小时
 * 短信发送成功率汇总统计
 *
 * @author leonzhangxf 20181009
 */
public class SendStatisticsSummeryDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 最近1小时发送成功率
     */
    private BigDecimal successRateFor1Hour;

    /**
     * 最近3小时发送成功率
     */
    private BigDecimal successRateFor3Hour;

    /**
     * 最近24小时发送成功率
     */
    private BigDecimal successRateFor24Hour;

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
        SendStatisticsSummeryDO that = (SendStatisticsSummeryDO) o;
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
        return "SendStatisticsSummeryDO{" +
                "successRateFor1Hour=" + successRateFor1Hour +
                ", successRateFor3Hour=" + successRateFor3Hour +
                ", successRateFor24Hour=" + successRateFor24Hour +
                '}';
    }
}
