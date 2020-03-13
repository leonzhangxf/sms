package com.leonzhangxf.sms.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SendLogDOCriteria {
    /**
     * send_log
     */
    protected String orderByClause;

    /**
     * send_log
     */
    protected boolean distinct;

    /**
     * send_log
     */
    protected List<Criteria> oredCriteria;

    /**
     *
     * @mbg.generated 2018-08-28
     */
    public SendLogDOCriteria() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     *
     * @mbg.generated 2018-08-28
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     *
     * @mbg.generated 2018-08-28
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     *
     * @mbg.generated 2018-08-28
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     *
     * @mbg.generated 2018-08-28
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     *
     * @mbg.generated 2018-08-28
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     *
     * @mbg.generated 2018-08-28
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     *
     * @mbg.generated 2018-08-28
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     *
     * @mbg.generated 2018-08-28
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     *
     * @mbg.generated 2018-08-28
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     *
     * @mbg.generated 2018-08-28
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * send_log 2018-08-28
     */
    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Long value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andTemplateIdIsNull() {
            addCriterion("template_id is null");
            return (Criteria) this;
        }

        public Criteria andTemplateIdIsNotNull() {
            addCriterion("template_id is not null");
            return (Criteria) this;
        }

        public Criteria andTemplateIdEqualTo(Integer value) {
            addCriterion("template_id =", value, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdNotEqualTo(Integer value) {
            addCriterion("template_id <>", value, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdGreaterThan(Integer value) {
            addCriterion("template_id >", value, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("template_id >=", value, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdLessThan(Integer value) {
            addCriterion("template_id <", value, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdLessThanOrEqualTo(Integer value) {
            addCriterion("template_id <=", value, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdIn(List<Integer> values) {
            addCriterion("template_id in", values, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdNotIn(List<Integer> values) {
            addCriterion("template_id not in", values, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdBetween(Integer value1, Integer value2) {
            addCriterion("template_id between", value1, value2, "templateId");
            return (Criteria) this;
        }

        public Criteria andTemplateIdNotBetween(Integer value1, Integer value2) {
            addCriterion("template_id not between", value1, value2, "templateId");
            return (Criteria) this;
        }

        public Criteria andMobileIsNull() {
            addCriterion("mobile is null");
            return (Criteria) this;
        }

        public Criteria andMobileIsNotNull() {
            addCriterion("mobile is not null");
            return (Criteria) this;
        }

        public Criteria andMobileEqualTo(String value) {
            addCriterion("mobile =", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileNotEqualTo(String value) {
            addCriterion("mobile <>", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileGreaterThan(String value) {
            addCriterion("mobile >", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileGreaterThanOrEqualTo(String value) {
            addCriterion("mobile >=", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileLessThan(String value) {
            addCriterion("mobile <", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileLessThanOrEqualTo(String value) {
            addCriterion("mobile <=", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileLike(String value) {
            addCriterion("mobile like", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileNotLike(String value) {
            addCriterion("mobile not like", value, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileIn(List<String> values) {
            addCriterion("mobile in", values, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileNotIn(List<String> values) {
            addCriterion("mobile not in", values, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileBetween(String value1, String value2) {
            addCriterion("mobile between", value1, value2, "mobile");
            return (Criteria) this;
        }

        public Criteria andMobileNotBetween(String value1, String value2) {
            addCriterion("mobile not between", value1, value2, "mobile");
            return (Criteria) this;
        }

        public Criteria andParamsIsNull() {
            addCriterion("params is null");
            return (Criteria) this;
        }

        public Criteria andParamsIsNotNull() {
            addCriterion("params is not null");
            return (Criteria) this;
        }

        public Criteria andParamsEqualTo(String value) {
            addCriterion("params =", value, "params");
            return (Criteria) this;
        }

        public Criteria andParamsNotEqualTo(String value) {
            addCriterion("params <>", value, "params");
            return (Criteria) this;
        }

        public Criteria andParamsGreaterThan(String value) {
            addCriterion("params >", value, "params");
            return (Criteria) this;
        }

        public Criteria andParamsGreaterThanOrEqualTo(String value) {
            addCriterion("params >=", value, "params");
            return (Criteria) this;
        }

        public Criteria andParamsLessThan(String value) {
            addCriterion("params <", value, "params");
            return (Criteria) this;
        }

        public Criteria andParamsLessThanOrEqualTo(String value) {
            addCriterion("params <=", value, "params");
            return (Criteria) this;
        }

        public Criteria andParamsLike(String value) {
            addCriterion("params like", value, "params");
            return (Criteria) this;
        }

        public Criteria andParamsNotLike(String value) {
            addCriterion("params not like", value, "params");
            return (Criteria) this;
        }

        public Criteria andParamsIn(List<String> values) {
            addCriterion("params in", values, "params");
            return (Criteria) this;
        }

        public Criteria andParamsNotIn(List<String> values) {
            addCriterion("params not in", values, "params");
            return (Criteria) this;
        }

        public Criteria andParamsBetween(String value1, String value2) {
            addCriterion("params between", value1, value2, "params");
            return (Criteria) this;
        }

        public Criteria andParamsNotBetween(String value1, String value2) {
            addCriterion("params not between", value1, value2, "params");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("`status` is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("`status` is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(Integer value) {
            addCriterion("`status` =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Integer value) {
            addCriterion("`status` <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Integer value) {
            addCriterion("`status` >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("`status` >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Integer value) {
            addCriterion("`status` <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Integer value) {
            addCriterion("`status` <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<Integer> values) {
            addCriterion("`status` in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<Integer> values) {
            addCriterion("`status` not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(Integer value1, Integer value2) {
            addCriterion("`status` between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("`status` not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andMessageIsNull() {
            addCriterion("message is null");
            return (Criteria) this;
        }

        public Criteria andMessageIsNotNull() {
            addCriterion("message is not null");
            return (Criteria) this;
        }

        public Criteria andMessageEqualTo(String value) {
            addCriterion("message =", value, "message");
            return (Criteria) this;
        }

        public Criteria andMessageNotEqualTo(String value) {
            addCriterion("message <>", value, "message");
            return (Criteria) this;
        }

        public Criteria andMessageGreaterThan(String value) {
            addCriterion("message >", value, "message");
            return (Criteria) this;
        }

        public Criteria andMessageGreaterThanOrEqualTo(String value) {
            addCriterion("message >=", value, "message");
            return (Criteria) this;
        }

        public Criteria andMessageLessThan(String value) {
            addCriterion("message <", value, "message");
            return (Criteria) this;
        }

        public Criteria andMessageLessThanOrEqualTo(String value) {
            addCriterion("message <=", value, "message");
            return (Criteria) this;
        }

        public Criteria andMessageLike(String value) {
            addCriterion("message like", value, "message");
            return (Criteria) this;
        }

        public Criteria andMessageNotLike(String value) {
            addCriterion("message not like", value, "message");
            return (Criteria) this;
        }

        public Criteria andMessageIn(List<String> values) {
            addCriterion("message in", values, "message");
            return (Criteria) this;
        }

        public Criteria andMessageNotIn(List<String> values) {
            addCriterion("message not in", values, "message");
            return (Criteria) this;
        }

        public Criteria andMessageBetween(String value1, String value2) {
            addCriterion("message between", value1, value2, "message");
            return (Criteria) this;
        }

        public Criteria andMessageNotBetween(String value1, String value2) {
            addCriterion("message not between", value1, value2, "message");
            return (Criteria) this;
        }

        public Criteria andSendTimeIsNull() {
            addCriterion("send_time is null");
            return (Criteria) this;
        }

        public Criteria andSendTimeIsNotNull() {
            addCriterion("send_time is not null");
            return (Criteria) this;
        }

        public Criteria andSendTimeEqualTo(Date value) {
            addCriterion("send_time =", value, "sendTime");
            return (Criteria) this;
        }

        public Criteria andSendTimeNotEqualTo(Date value) {
            addCriterion("send_time <>", value, "sendTime");
            return (Criteria) this;
        }

        public Criteria andSendTimeGreaterThan(Date value) {
            addCriterion("send_time >", value, "sendTime");
            return (Criteria) this;
        }

        public Criteria andSendTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("send_time >=", value, "sendTime");
            return (Criteria) this;
        }

        public Criteria andSendTimeLessThan(Date value) {
            addCriterion("send_time <", value, "sendTime");
            return (Criteria) this;
        }

        public Criteria andSendTimeLessThanOrEqualTo(Date value) {
            addCriterion("send_time <=", value, "sendTime");
            return (Criteria) this;
        }

        public Criteria andSendTimeIn(List<Date> values) {
            addCriterion("send_time in", values, "sendTime");
            return (Criteria) this;
        }

        public Criteria andSendTimeNotIn(List<Date> values) {
            addCriterion("send_time not in", values, "sendTime");
            return (Criteria) this;
        }

        public Criteria andSendTimeBetween(Date value1, Date value2) {
            addCriterion("send_time between", value1, value2, "sendTime");
            return (Criteria) this;
        }

        public Criteria andSendTimeNotBetween(Date value1, Date value2) {
            addCriterion("send_time not between", value1, value2, "sendTime");
            return (Criteria) this;
        }
    }

    /**
     * send_log
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * send_log 2018-08-28
     */
    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}