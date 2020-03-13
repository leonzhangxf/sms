package com.leonzhangxf.sms.dao.mapper;

import com.leonzhangxf.sms.domain.SendLogDO;
import com.leonzhangxf.sms.domain.SendLogDOCriteria;
import com.leonzhangxf.sms.domain.SendLogDOCriteria.Criteria;
import com.leonzhangxf.sms.domain.SendLogDOCriteria.Criterion;
import com.leonzhangxf.sms.enumeration.SendResponseStatus;
import com.leonzhangxf.sms.enumeration.TemplateUsage;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

public class SendLogSqlProvider {

    public String statisticsSummery(Map<String, Object> parameter) {
        List<Integer> hourList = (List<Integer>) parameter.get("hourList");
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ");

        if (!CollectionUtils.isEmpty(hourList)) {
            for (int i = 0; i < hourList.size(); i++) {
                sql.append(" ( ");
                sql.append(" 	( ");
                sql.append(" 		SELECT count(1) ");
                sql.append(" 		FROM `send_log` ls ");
                sql.append(" 		WHERE ls.`status` = ").append(SendResponseStatus.OK.value());
                sql.append(" 		AND ls.send_time <= NOW() ");
                sql.append(" 		AND ls.send_time > DATE_SUB(NOW(),INTERVAL ").append(hourList.get(i)).append(" HOUR) ");
                sql.append(" 	) ");
                sql.append(" 	/ ");
                sql.append(" 	( ");
                sql.append(" 		SELECT if(count(1) > 0, count(1), 1) ");
                sql.append(" 		FROM `send_log` ls ");
                sql.append(" 		WHERE ls.`status` <> ").append(SendResponseStatus.FORBIDDEN.value());
                sql.append(" 		AND ls.send_time <= NOW() ");
                sql.append(" 		AND ls.send_time > DATE_SUB(NOW(),INTERVAL ").append(hourList.get(i)).append(" HOUR) ");
                sql.append(" 	) ");
                sql.append(" ) *100 as successRateFor").append(hourList.get(i)).append("Hour ");

                if (i < hourList.size() - 1) {
                    sql.append(",");
                }
            }
        }
        sql.append(" FROM dual ");
        return sql.toString();
    }

    public String logsPage(Integer clientId, Integer channelSignatureId, Integer channelTemplateId,
                           Integer templateId, TemplateUsage usage, SendResponseStatus status,
                           Integer currentPage, Integer pageSize) {
        StringBuilder sql = new StringBuilder();
        logsPageCommonSql(sql, true, clientId, channelSignatureId, channelTemplateId, templateId, usage, status,
                currentPage, pageSize);
        return sql.toString();
    }

    public String logsCount(Integer clientId, Integer channelSignatureId, Integer channelTemplateId,
                            Integer templateId, TemplateUsage usage, SendResponseStatus status) {
        StringBuilder sql = new StringBuilder();
        logsPageCommonSql(sql, false, clientId, channelSignatureId, channelTemplateId, templateId, usage, status,
                null, null);
        return sql.toString();
    }

    /**
     * @param flag true-查询列表，false-统计总数
     */
    private void logsPageCommonSql(StringBuilder sql, boolean flag, Integer clientId, Integer channelSignatureId,
                                   Integer channelTemplateId, Integer templateId, TemplateUsage usage,
                                   SendResponseStatus status, Integer currentPage, Integer pageSize) {
        sql.append(" SELECT ");

        if (flag) {
            sql.append(" * ");
        } else {
            sql.append(" count(1) ");
        }

        sql.append(" FROM `send_log` ls ");
        sql.append(" LEFT JOIN `template` tm on tm.id = ls.template_id ");
        sql.append(" WHERE 1=1 ");

        if (null != clientId)
            sql.append(" AND tm.client_id = #{clientId} ");

        if (null != channelSignatureId)
            sql.append(" AND tm.chnl_signature_id = #{channelSignatureId} ");

        if (null != channelTemplateId)
            sql.append(" AND tm.chnl_template_id = #{channelTemplateId} ");

        if (null != templateId)
            sql.append(" AND ls.template_id = #{templateId} ");

        if (null != usage)
            sql.append(" AND tm.`usage` = ").append(usage.getCode());

        if (null != status)
            sql.append(" AND ls.`status` = ").append(status.value());

        if (flag && null != currentPage && null != pageSize)
            sql.append(" LIMIT ").append((currentPage - 1) >= 0 ? (currentPage - 1) : 0).append(",").append(pageSize);
    }

    /**
     * @mbg.generated 2018-08-28
     */
    public String countByExample(SendLogDOCriteria example) {
        SQL sql = new SQL();
        sql.SELECT("count(*)").FROM("send_log");
        applyWhere(sql, example, false);
        return sql.toString();
    }

    /**
     * @mbg.generated 2018-08-28
     */
    public String deleteByExample(SendLogDOCriteria example) {
        SQL sql = new SQL();
        sql.DELETE_FROM("send_log");
        applyWhere(sql, example, false);
        return sql.toString();
    }

    /**
     * @mbg.generated 2018-08-28
     */
    public String insertSelective(SendLogDO record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("send_log");

        if (record.getId() != null) {
            sql.VALUES("id", "#{id,jdbcType=BIGINT}");
        }

        if (record.getTemplateId() != null) {
            sql.VALUES("template_id", "#{templateId,jdbcType=INTEGER}");
        }

        if (record.getMobile() != null) {
            sql.VALUES("mobile", "#{mobile,jdbcType=VARCHAR}");
        }

        if (record.getParams() != null) {
            sql.VALUES("params", "#{params,jdbcType=VARCHAR}");
        }

        if (record.getStatus() != null) {
            sql.VALUES("`status`", "#{status,jdbcType=SMALLINT}");
        }

        if (record.getMessage() != null) {
            sql.VALUES("message", "#{message,jdbcType=VARCHAR}");
        }

        if (record.getSendTime() != null) {
            sql.VALUES("send_time", "#{sendTime,jdbcType=TIMESTAMP}");
        }

        return sql.toString();
    }

    /**
     * @mbg.generated 2018-08-28
     */
    public String selectByExample(SendLogDOCriteria example) {
        SQL sql = new SQL();
        if (example != null && example.isDistinct()) {
            sql.SELECT_DISTINCT("id");
        } else {
            sql.SELECT("id");
        }
        sql.SELECT("template_id");
        sql.SELECT("mobile");
        sql.SELECT("params");
        sql.SELECT("`status`");
        sql.SELECT("message");
        sql.SELECT("send_time");
        sql.FROM("send_log");
        applyWhere(sql, example, false);

        if (example != null && example.getOrderByClause() != null) {
            sql.ORDER_BY(example.getOrderByClause());
        }

        return sql.toString();
    }

    /**
     * @mbg.generated 2018-08-28
     */
    public String updateByExampleSelective(Map<String, Object> parameter) {
        SendLogDO record = (SendLogDO) parameter.get("record");
        SendLogDOCriteria example = (SendLogDOCriteria) parameter.get("example");

        SQL sql = new SQL();
        sql.UPDATE("send_log");

        if (record.getId() != null) {
            sql.SET("id = #{record.id,jdbcType=BIGINT}");
        }

        if (record.getTemplateId() != null) {
            sql.SET("template_id = #{record.templateId,jdbcType=INTEGER}");
        }

        if (record.getMobile() != null) {
            sql.SET("mobile = #{record.mobile,jdbcType=VARCHAR}");
        }

        if (record.getParams() != null) {
            sql.SET("params = #{record.params,jdbcType=VARCHAR}");
        }

        if (record.getStatus() != null) {
            sql.SET("`status` = #{record.status,jdbcType=SMALLINT}");
        }

        if (record.getMessage() != null) {
            sql.SET("message = #{record.message,jdbcType=VARCHAR}");
        }

        if (record.getSendTime() != null) {
            sql.SET("send_time = #{record.sendTime,jdbcType=TIMESTAMP}");
        }

        applyWhere(sql, example, true);
        return sql.toString();
    }

    /**
     * @mbg.generated 2018-08-28
     */
    public String updateByExample(Map<String, Object> parameter) {
        SQL sql = new SQL();
        sql.UPDATE("send_log");

        sql.SET("id = #{record.id,jdbcType=BIGINT}");
        sql.SET("template_id = #{record.templateId,jdbcType=INTEGER}");
        sql.SET("mobile = #{record.mobile,jdbcType=VARCHAR}");
        sql.SET("params = #{record.params,jdbcType=VARCHAR}");
        sql.SET("`status` = #{record.status,jdbcType=SMALLINT}");
        sql.SET("message = #{record.message,jdbcType=VARCHAR}");
        sql.SET("send_time = #{record.sendTime,jdbcType=TIMESTAMP}");

        SendLogDOCriteria example = (SendLogDOCriteria) parameter.get("example");
        applyWhere(sql, example, true);
        return sql.toString();
    }

    /**
     * @mbg.generated 2018-08-28
     */
    public String updateByPrimaryKeySelective(SendLogDO record) {
        SQL sql = new SQL();
        sql.UPDATE("send_log");

        if (record.getTemplateId() != null) {
            sql.SET("template_id = #{templateId,jdbcType=INTEGER}");
        }

        if (record.getMobile() != null) {
            sql.SET("mobile = #{mobile,jdbcType=VARCHAR}");
        }

        if (record.getParams() != null) {
            sql.SET("params = #{params,jdbcType=VARCHAR}");
        }

        if (record.getStatus() != null) {
            sql.SET("`status` = #{status,jdbcType=SMALLINT}");
        }

        if (record.getMessage() != null) {
            sql.SET("message = #{message,jdbcType=VARCHAR}");
        }

        if (record.getSendTime() != null) {
            sql.SET("send_time = #{sendTime,jdbcType=TIMESTAMP}");
        }

        sql.WHERE("id = #{id,jdbcType=BIGINT}");

        return sql.toString();
    }

    /**
     * @mbg.generated 2018-08-28
     */
    protected void applyWhere(SQL sql, SendLogDOCriteria example, boolean includeExamplePhrase) {
        if (example == null) {
            return;
        }

        String parmPhrase1;
        String parmPhrase1_th;
        String parmPhrase2;
        String parmPhrase2_th;
        String parmPhrase3;
        String parmPhrase3_th;
        if (includeExamplePhrase) {
            parmPhrase1 = "%s #{example.oredCriteria[%d].allCriteria[%d].value}";
            parmPhrase1_th = "%s #{example.oredCriteria[%d].allCriteria[%d].value,typeHandler=%s}";
            parmPhrase2 = "%s #{example.oredCriteria[%d].allCriteria[%d].value} and #{example.oredCriteria[%d].criteria[%d].secondValue}";
            parmPhrase2_th = "%s #{example.oredCriteria[%d].allCriteria[%d].value,typeHandler=%s} and #{example.oredCriteria[%d].criteria[%d].secondValue,typeHandler=%s}";
            parmPhrase3 = "#{example.oredCriteria[%d].allCriteria[%d].value[%d]}";
            parmPhrase3_th = "#{example.oredCriteria[%d].allCriteria[%d].value[%d],typeHandler=%s}";
        } else {
            parmPhrase1 = "%s #{oredCriteria[%d].allCriteria[%d].value}";
            parmPhrase1_th = "%s #{oredCriteria[%d].allCriteria[%d].value,typeHandler=%s}";
            parmPhrase2 = "%s #{oredCriteria[%d].allCriteria[%d].value} and #{oredCriteria[%d].criteria[%d].secondValue}";
            parmPhrase2_th = "%s #{oredCriteria[%d].allCriteria[%d].value,typeHandler=%s} and #{oredCriteria[%d].criteria[%d].secondValue,typeHandler=%s}";
            parmPhrase3 = "#{oredCriteria[%d].allCriteria[%d].value[%d]}";
            parmPhrase3_th = "#{oredCriteria[%d].allCriteria[%d].value[%d],typeHandler=%s}";
        }

        StringBuilder sb = new StringBuilder();
        List<Criteria> oredCriteria = example.getOredCriteria();
        boolean firstCriteria = true;
        for (int i = 0; i < oredCriteria.size(); i++) {
            Criteria criteria = oredCriteria.get(i);
            if (criteria.isValid()) {
                if (firstCriteria) {
                    firstCriteria = false;
                } else {
                    sb.append(" or ");
                }

                sb.append('(');
                List<Criterion> criterions = criteria.getAllCriteria();
                boolean firstCriterion = true;
                for (int j = 0; j < criterions.size(); j++) {
                    Criterion criterion = criterions.get(j);
                    if (firstCriterion) {
                        firstCriterion = false;
                    } else {
                        sb.append(" and ");
                    }

                    if (criterion.isNoValue()) {
                        sb.append(criterion.getCondition());
                    } else if (criterion.isSingleValue()) {
                        if (criterion.getTypeHandler() == null) {
                            sb.append(String.format(parmPhrase1, criterion.getCondition(), i, j));
                        } else {
                            sb.append(String.format(parmPhrase1_th, criterion.getCondition(), i, j, criterion.getTypeHandler()));
                        }
                    } else if (criterion.isBetweenValue()) {
                        if (criterion.getTypeHandler() == null) {
                            sb.append(String.format(parmPhrase2, criterion.getCondition(), i, j, i, j));
                        } else {
                            sb.append(String.format(parmPhrase2_th, criterion.getCondition(), i, j, criterion.getTypeHandler(), i, j, criterion.getTypeHandler()));
                        }
                    } else if (criterion.isListValue()) {
                        sb.append(criterion.getCondition());
                        sb.append(" (");
                        List<?> listItems = (List<?>) criterion.getValue();
                        boolean comma = false;
                        for (int k = 0; k < listItems.size(); k++) {
                            if (comma) {
                                sb.append(", ");
                            } else {
                                comma = true;
                            }
                            if (criterion.getTypeHandler() == null) {
                                sb.append(String.format(parmPhrase3, i, j, k));
                            } else {
                                sb.append(String.format(parmPhrase3_th, i, j, k, criterion.getTypeHandler()));
                            }
                        }
                        sb.append(')');
                    }
                }
                sb.append(')');
            }
        }

        if (sb.length() > 0) {
            sql.WHERE(sb.toString());
        }
    }
}