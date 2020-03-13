package com.leonzhangxf.sms.dao.mapper;

import com.leonzhangxf.sms.domain.ClientDO;
import com.leonzhangxf.sms.domain.ClientDOCriteria;
import java.util.List;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface ClientMapper {
    /**
     *
     * @mbg.generated 2018-08-28
     */
    @SelectProvider(type=ClientSqlProvider.class, method="countByExample")
    long countByExample(ClientDOCriteria example);

    /**
     *
     * @mbg.generated 2018-08-28
     */
    @DeleteProvider(type=ClientSqlProvider.class, method="deleteByExample")
    int deleteByExample(ClientDOCriteria example);

    /**
     *
     * @mbg.generated 2018-08-28
     */
    @Delete({
        "delete from client",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    /**
     *
     * @mbg.generated 2018-08-28
     */
    @Insert({
        "insert into client (id, code, ",
        "`name`, `key`, `desc`, ",
        "`status`, create_time, ",
        "update_time, delete_flag)",
        "values (#{id,jdbcType=INTEGER}, #{code,jdbcType=VARCHAR}, ",
        "#{name,jdbcType=VARCHAR}, #{key,jdbcType=VARCHAR}, #{desc,jdbcType=VARCHAR}, ",
        "#{status,jdbcType=BIT}, #{createTime,jdbcType=TIMESTAMP}, ",
        "#{updateTime,jdbcType=TIMESTAMP}, #{deleteFlag,jdbcType=BIT})"
    })
    @Options(useGeneratedKeys = true)
    int insert(ClientDO record);

    /**
     *
     * @mbg.generated 2018-08-28
     */
    @InsertProvider(type=ClientSqlProvider.class, method="insertSelective")
    @Options(useGeneratedKeys = true)
    int insertSelective(ClientDO record);

    /**
     *
     * @mbg.generated 2018-08-28
     */
    @SelectProvider(type=ClientSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="code", property="code", jdbcType=JdbcType.VARCHAR),
        @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR),
        @Result(column="key", property="key", jdbcType=JdbcType.VARCHAR),
        @Result(column="desc", property="desc", jdbcType=JdbcType.VARCHAR),
        @Result(column="status", property="status", jdbcType=JdbcType.BIT),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="delete_flag", property="deleteFlag", jdbcType=JdbcType.BIT)
    })
    List<ClientDO> selectByExample(ClientDOCriteria example);

    /**
     *
     * @mbg.generated 2018-08-28
     */
    @Select({
        "select",
        "id, code, `name`, `key`, `desc`, `status`, create_time, update_time, delete_flag",
        "from client",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="code", property="code", jdbcType=JdbcType.VARCHAR),
        @Result(column="name", property="name", jdbcType=JdbcType.VARCHAR),
        @Result(column="key", property="key", jdbcType=JdbcType.VARCHAR),
        @Result(column="desc", property="desc", jdbcType=JdbcType.VARCHAR),
        @Result(column="status", property="status", jdbcType=JdbcType.BIT),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="delete_flag", property="deleteFlag", jdbcType=JdbcType.BIT)
    })
    ClientDO selectByPrimaryKey(Integer id);

    /**
     *
     * @mbg.generated 2018-08-28
     */
    @UpdateProvider(type=ClientSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") ClientDO record, @Param("example") ClientDOCriteria example);

    /**
     *
     * @mbg.generated 2018-08-28
     */
    @UpdateProvider(type=ClientSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") ClientDO record, @Param("example") ClientDOCriteria example);

    /**
     *
     * @mbg.generated 2018-08-28
     */
    @UpdateProvider(type=ClientSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(ClientDO record);

    /**
     *
     * @mbg.generated 2018-08-28
     */
    @Update({
        "update client",
        "set code = #{code,jdbcType=VARCHAR},",
          "`name` = #{name,jdbcType=VARCHAR},",
          "`key` = #{key,jdbcType=VARCHAR},",
          "`desc` = #{desc,jdbcType=VARCHAR},",
          "`status` = #{status,jdbcType=BIT},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "update_time = #{updateTime,jdbcType=TIMESTAMP},",
          "delete_flag = #{deleteFlag,jdbcType=BIT}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(ClientDO record);
}