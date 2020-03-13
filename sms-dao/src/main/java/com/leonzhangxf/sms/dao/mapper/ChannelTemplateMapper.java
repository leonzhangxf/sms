package com.leonzhangxf.sms.dao.mapper;

import com.leonzhangxf.sms.domain.ChannelTemplateDO;
import com.leonzhangxf.sms.domain.ChannelTemplateDOCriteria;
import java.util.List;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface ChannelTemplateMapper {
    /**
     *
     * @mbg.generated 2018-08-28
     */
    @SelectProvider(type=ChannelTemplateSqlProvider.class, method="countByExample")
    long countByExample(ChannelTemplateDOCriteria example);

    /**
     *
     * @mbg.generated 2018-08-28
     */
    @DeleteProvider(type=ChannelTemplateSqlProvider.class, method="deleteByExample")
    int deleteByExample(ChannelTemplateDOCriteria example);

    /**
     *
     * @mbg.generated 2018-08-28
     */
    @Delete({
        "delete from chnl_template",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    /**
     *
     * @mbg.generated 2018-08-28
     */
    @Insert({
        "insert into chnl_template (id, chnl_config_id, ",
        "`name`, code, content, ",
        "params, `status`, create_time, ",
        "update_time, delete_flag)",
        "values (#{id,jdbcType=INTEGER}, #{chnlConfigId,jdbcType=INTEGER}, ",
        "#{name,jdbcType=VARCHAR}, #{code,jdbcType=VARCHAR}, #{content,jdbcType=VARCHAR}, ",
        "#{params,jdbcType=VARCHAR}, #{status,jdbcType=BIT}, #{createTime,jdbcType=TIMESTAMP}, ",
        "#{updateTime,jdbcType=TIMESTAMP}, #{deleteFlag,jdbcType=BIT})"
    })
    @Options(useGeneratedKeys = true)
    int insert(ChannelTemplateDO record);

    /**
     *
     * @mbg.generated 2018-08-28
     */
    @InsertProvider(type=ChannelTemplateSqlProvider.class, method="insertSelective")
    @Options(useGeneratedKeys = true)
    int insertSelective(ChannelTemplateDO record);

    /**
     *
     * @mbg.generated 2018-08-28
     */
    @SelectProvider(type=ChannelTemplateSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="chnl_config_id", property="chnlConfigId", jdbcType=JdbcType.INTEGER),
        @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR),
        @Result(column="code", property="code", jdbcType=JdbcType.VARCHAR),
        @Result(column="content", property="content", jdbcType=JdbcType.VARCHAR),
        @Result(column="params", property="params", jdbcType=JdbcType.VARCHAR),
        @Result(column="status", property="status", jdbcType=JdbcType.BIT),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="delete_flag", property="deleteFlag", jdbcType=JdbcType.BIT)
    })
    List<ChannelTemplateDO> selectByExample(ChannelTemplateDOCriteria example);

    /**
     *
     * @mbg.generated 2018-08-28
     */
    @Select({
        "select",
        "id, chnl_config_id, `name`, code, content, params, `status`, create_time, update_time, ",
        "delete_flag",
        "from chnl_template",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="chnl_config_id", property="chnlConfigId", jdbcType=JdbcType.INTEGER),
        @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR),
        @Result(column="code", property="code", jdbcType=JdbcType.VARCHAR),
        @Result(column="content", property="content", jdbcType=JdbcType.VARCHAR),
        @Result(column="params", property="params", jdbcType=JdbcType.VARCHAR),
        @Result(column="status", property="status", jdbcType=JdbcType.BIT),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="delete_flag", property="deleteFlag", jdbcType=JdbcType.BIT)
    })
    ChannelTemplateDO selectByPrimaryKey(Integer id);

    /**
     *
     * @mbg.generated 2018-08-28
     */
    @UpdateProvider(type=ChannelTemplateSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") ChannelTemplateDO record, @Param("example") ChannelTemplateDOCriteria example);

    /**
     *
     * @mbg.generated 2018-08-28
     */
    @UpdateProvider(type=ChannelTemplateSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") ChannelTemplateDO record, @Param("example") ChannelTemplateDOCriteria example);

    /**
     *
     * @mbg.generated 2018-08-28
     */
    @UpdateProvider(type=ChannelTemplateSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(ChannelTemplateDO record);

    /**
     *
     * @mbg.generated 2018-08-28
     */
    @Update({
        "update chnl_template",
        "set chnl_config_id = #{chnlConfigId,jdbcType=INTEGER},",
          "`name` = #{name,jdbcType=VARCHAR},",
          "code = #{code,jdbcType=VARCHAR},",
          "content = #{content,jdbcType=VARCHAR},",
          "params = #{params,jdbcType=VARCHAR},",
          "`status` = #{status,jdbcType=BIT},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "update_time = #{updateTime,jdbcType=TIMESTAMP},",
          "delete_flag = #{deleteFlag,jdbcType=BIT}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(ChannelTemplateDO record);
}