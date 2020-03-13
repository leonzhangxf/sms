package com.leonzhangxf.sms.dao.mapper;

import com.leonzhangxf.sms.domain.SendLogDO;
import com.leonzhangxf.sms.domain.SendLogDOCriteria;
import com.leonzhangxf.sms.domain.SendStatisticsSummeryDO;
import com.leonzhangxf.sms.enumeration.SendResponseStatus;
import com.leonzhangxf.sms.enumeration.TemplateUsage;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface SendLogMapper {
    /**
     * @mbg.generated 2018-08-28
     */
    @SelectProvider(type = SendLogSqlProvider.class, method = "countByExample")
    long countByExample(SendLogDOCriteria example);

    /**
     * @mbg.generated 2018-08-28
     */
    @DeleteProvider(type = SendLogSqlProvider.class, method = "deleteByExample")
    int deleteByExample(SendLogDOCriteria example);

    /**
     * @mbg.generated 2018-08-28
     */
    @Delete({
            "delete from send_log",
            "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    /**
     * @mbg.generated 2018-08-28
     */
    @Insert({
            "insert into send_log (id, template_id, ",
            "mobile, params, ",
            "`status`, message, ",
            "send_time)",
            "values (#{id,jdbcType=BIGINT}, #{templateId,jdbcType=INTEGER}, ",
            "#{mobile,jdbcType=VARCHAR}, #{params,jdbcType=VARCHAR}, ",
            "#{status,jdbcType=SMALLINT}, #{message,jdbcType=VARCHAR}, ",
            "#{sendTime,jdbcType=TIMESTAMP})"
    })
    int insert(SendLogDO record);

    /**
     * @mbg.generated 2018-08-28
     */
    @InsertProvider(type = SendLogSqlProvider.class, method = "insertSelective")
    int insertSelective(SendLogDO record);

    /**
     * @mbg.generated 2018-08-28
     */
    @SelectProvider(type = SendLogSqlProvider.class, method = "selectByExample")
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "template_id", property = "templateId", jdbcType = JdbcType.INTEGER),
            @Result(column = "mobile", property = "mobile", jdbcType = JdbcType.VARCHAR),
            @Result(column = "params", property = "params", jdbcType = JdbcType.VARCHAR),
            @Result(column = "status", property = "status", jdbcType = JdbcType.SMALLINT),
            @Result(column = "message", property = "message", jdbcType = JdbcType.VARCHAR),
            @Result(column = "send_time", property = "sendTime", jdbcType = JdbcType.TIMESTAMP)
    })
    List<SendLogDO> selectByExample(SendLogDOCriteria example);

    /**
     * @mbg.generated 2018-08-28
     */
    @Select({
            "select",
            "id, template_id, mobile, params, `status`, message, send_time",
            "from send_log",
            "where id = #{id,jdbcType=BIGINT}"
    })
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "template_id", property = "templateId", jdbcType = JdbcType.INTEGER),
            @Result(column = "mobile", property = "mobile", jdbcType = JdbcType.VARCHAR),
            @Result(column = "params", property = "params", jdbcType = JdbcType.VARCHAR),
            @Result(column = "status", property = "status", jdbcType = JdbcType.SMALLINT),
            @Result(column = "message", property = "message", jdbcType = JdbcType.VARCHAR),
            @Result(column = "send_time", property = "sendTime", jdbcType = JdbcType.TIMESTAMP)
    })
    SendLogDO selectByPrimaryKey(Long id);

    /**
     * @mbg.generated 2018-08-28
     */
    @UpdateProvider(type = SendLogSqlProvider.class, method = "updateByExampleSelective")
    int updateByExampleSelective(@Param("record") SendLogDO record, @Param("example") SendLogDOCriteria example);

    /**
     * @mbg.generated 2018-08-28
     */
    @UpdateProvider(type = SendLogSqlProvider.class, method = "updateByExample")
    int updateByExample(@Param("record") SendLogDO record, @Param("example") SendLogDOCriteria example);

    /**
     * @mbg.generated 2018-08-28
     */
    @UpdateProvider(type = SendLogSqlProvider.class, method = "updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(SendLogDO record);

    /**
     * @mbg.generated 2018-08-28
     */
    @Update({
            "update send_log",
            "set template_id = #{templateId,jdbcType=INTEGER},",
            "mobile = #{mobile,jdbcType=VARCHAR},",
            "params = #{params,jdbcType=VARCHAR},",
            "`status` = #{status,jdbcType=SMALLINT},",
            "message = #{message,jdbcType=VARCHAR},",
            "send_time = #{sendTime,jdbcType=TIMESTAMP}",
            "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(SendLogDO record);


    @SelectProvider(type = SendLogSqlProvider.class, method = "logsPage")
    @Results({
            @Result(column = "id", property = "id", jdbcType = JdbcType.BIGINT, id = true),
            @Result(column = "template_id", property = "templateId", jdbcType = JdbcType.INTEGER),
            @Result(column = "mobile", property = "mobile", jdbcType = JdbcType.VARCHAR),
            @Result(column = "params", property = "params", jdbcType = JdbcType.VARCHAR),
            @Result(column = "status", property = "status", jdbcType = JdbcType.SMALLINT),
            @Result(column = "message", property = "message", jdbcType = JdbcType.VARCHAR),
            @Result(column = "send_time", property = "sendTime", jdbcType = JdbcType.TIMESTAMP)
    })
    List<SendLogDO> logsPage(Integer clientId, Integer channelSignatureId, Integer channelTemplateId,
                             Integer templateId, TemplateUsage usage, SendResponseStatus status,
                             Integer currentPage, Integer pageSize);

    @SelectProvider(type = SendLogSqlProvider.class, method = "logsCount")
    Integer logsCount(Integer clientId, Integer channelSignatureId, Integer channelTemplateId,
                      Integer templateId, TemplateUsage usage, SendResponseStatus status);

    @SelectProvider(type = SendLogSqlProvider.class, method = "statisticsSummery")
    SendStatisticsSummeryDO statisticsSummery(@Param("hourList") List<Integer> hourList);
}