package com.leonzhangxf.sms.dao.mapper;

import com.leonzhangxf.sms.domain.TemplateDO;
import com.leonzhangxf.sms.domain.TemplateDOCriteria;
import java.util.List;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface TemplateMapper {
    /**
     *
     * @mbg.generated 2018-09-05
     */
    @SelectProvider(type=TemplateSqlProvider.class, method="countByExample")
    long countByExample(TemplateDOCriteria example);

    /**
     *
     * @mbg.generated 2018-09-05
     */
    @DeleteProvider(type=TemplateSqlProvider.class, method="deleteByExample")
    int deleteByExample(TemplateDOCriteria example);

    /**
     *
     * @mbg.generated 2018-09-05
     */
    @Delete({
        "delete from template",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    /**
     *
     * @mbg.generated 2018-09-05
     */
    @Insert({
        "insert into template (id, client_id, ",
        "chnl_signature_id, chnl_template_id, ",
        "`name`, template_id, ",
        "`desc`, `usage`, `status`, ",
        "create_time, update_time, ",
        "delete_flag)",
        "values (#{id,jdbcType=INTEGER}, #{clientId,jdbcType=INTEGER}, ",
        "#{chnlSignatureId,jdbcType=INTEGER}, #{chnlTemplateId,jdbcType=INTEGER}, ",
        "#{name,jdbcType=VARCHAR}, #{templateId,jdbcType=VARCHAR}, ",
        "#{desc,jdbcType=VARCHAR}, #{usage,jdbcType=BIT}, #{status,jdbcType=BIT}, ",
        "#{createTime,jdbcType=TIMESTAMP}, #{updateTime,jdbcType=TIMESTAMP}, ",
        "#{deleteFlag,jdbcType=BIT})"
    })
    int insert(TemplateDO record);

    /**
     *
     * @mbg.generated 2018-09-05
     */
    @InsertProvider(type=TemplateSqlProvider.class, method="insertSelective")
    int insertSelective(TemplateDO record);

    /**
     *
     * @mbg.generated 2018-09-05
     */
    @SelectProvider(type=TemplateSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="client_id", property="clientId", jdbcType=JdbcType.INTEGER),
        @Result(column="chnl_signature_id", property="chnlSignatureId", jdbcType=JdbcType.INTEGER),
        @Result(column="chnl_template_id", property="chnlTemplateId", jdbcType=JdbcType.INTEGER),
        @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR),
        @Result(column="template_id", property="templateId", jdbcType=JdbcType.VARCHAR),
        @Result(column="desc", property="desc", jdbcType=JdbcType.VARCHAR),
        @Result(column="usage", property="usage", jdbcType=JdbcType.BIT),
        @Result(column="status", property="status", jdbcType=JdbcType.BIT),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="delete_flag", property="deleteFlag", jdbcType=JdbcType.BIT)
    })
    List<TemplateDO> selectByExample(TemplateDOCriteria example);

    /**
     *
     * @mbg.generated 2018-09-05
     */
    @Select({
        "select",
        "id, client_id, chnl_signature_id, chnl_template_id, `name`, template_id, `desc`, ",
        "`usage`, `status`, create_time, update_time, delete_flag",
        "from template",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="client_id", property="clientId", jdbcType=JdbcType.INTEGER),
        @Result(column="chnl_signature_id", property="chnlSignatureId", jdbcType=JdbcType.INTEGER),
        @Result(column="chnl_template_id", property="chnlTemplateId", jdbcType=JdbcType.INTEGER),
        @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR),
        @Result(column="template_id", property="templateId", jdbcType=JdbcType.VARCHAR),
        @Result(column="desc", property="desc", jdbcType=JdbcType.VARCHAR),
        @Result(column="usage", property="usage", jdbcType=JdbcType.BIT),
        @Result(column="status", property="status", jdbcType=JdbcType.BIT),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="delete_flag", property="deleteFlag", jdbcType=JdbcType.BIT)
    })
    TemplateDO selectByPrimaryKey(Integer id);

    /**
     *
     * @mbg.generated 2018-09-05
     */
    @UpdateProvider(type=TemplateSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") TemplateDO record, @Param("example") TemplateDOCriteria example);

    /**
     *
     * @mbg.generated 2018-09-05
     */
    @UpdateProvider(type=TemplateSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") TemplateDO record, @Param("example") TemplateDOCriteria example);

    /**
     *
     * @mbg.generated 2018-09-05
     */
    @UpdateProvider(type=TemplateSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(TemplateDO record);

    /**
     *
     * @mbg.generated 2018-09-05
     */
    @Update({
        "update template",
        "set client_id = #{clientId,jdbcType=INTEGER},",
          "chnl_signature_id = #{chnlSignatureId,jdbcType=INTEGER},",
          "chnl_template_id = #{chnlTemplateId,jdbcType=INTEGER},",
          "`name` = #{name,jdbcType=VARCHAR},",
          "template_id = #{templateId,jdbcType=VARCHAR},",
          "`desc` = #{desc,jdbcType=VARCHAR},",
          "`usage` = #{usage,jdbcType=BIT},",
          "`status` = #{status,jdbcType=BIT},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "update_time = #{updateTime,jdbcType=TIMESTAMP},",
          "delete_flag = #{deleteFlag,jdbcType=BIT}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(TemplateDO record);
}