package com.leonzhangxf.sms.dao.mapper;

import com.leonzhangxf.sms.domain.ChannelConfigParamsDO;
import com.leonzhangxf.sms.domain.ChannelConfigParamsDOCriteria;
import java.util.List;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface ChannelConfigParamsMapper {
    /**
     *
     * @mbg.generated 2018-08-28
     */
    @SelectProvider(type=ChannelConfigParamsSqlProvider.class, method="countByExample")
    long countByExample(ChannelConfigParamsDOCriteria example);

    /**
     *
     * @mbg.generated 2018-08-28
     */
    @DeleteProvider(type=ChannelConfigParamsSqlProvider.class, method="deleteByExample")
    int deleteByExample(ChannelConfigParamsDOCriteria example);

    /**
     *
     * @mbg.generated 2018-08-28
     */
    @Delete({
        "delete from chnl_config_params",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    /**
     *
     * @mbg.generated 2018-08-28
     */
    @Insert({
        "insert into chnl_config_params (id, chnl_config_id, ",
        "`key`, `value`, create_time, ",
        "update_time, delete_flag)",
        "values (#{id,jdbcType=INTEGER}, #{chnlConfigId,jdbcType=INTEGER}, ",
        "#{key,jdbcType=VARCHAR}, #{value,jdbcType=VARCHAR}, #{createTime,jdbcType=TIMESTAMP}, ",
        "#{updateTime,jdbcType=TIMESTAMP}, #{deleteFlag,jdbcType=BIT})"
    })
    int insert(ChannelConfigParamsDO record);

    /**
     *
     * @mbg.generated 2018-08-28
     */
    @InsertProvider(type=ChannelConfigParamsSqlProvider.class, method="insertSelective")
    int insertSelective(ChannelConfigParamsDO record);

    /**
     *
     * @mbg.generated 2018-08-28
     */
    @SelectProvider(type=ChannelConfigParamsSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="chnl_config_id", property="chnlConfigId", jdbcType=JdbcType.INTEGER),
        @Result(column="key", property="key", jdbcType=JdbcType.VARCHAR),
        @Result(column="value", property="value", jdbcType=JdbcType.VARCHAR),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="delete_flag", property="deleteFlag", jdbcType=JdbcType.BIT)
    })
    List<ChannelConfigParamsDO> selectByExample(ChannelConfigParamsDOCriteria example);

    /**
     *
     * @mbg.generated 2018-08-28
     */
    @Select({
        "select",
        "id, chnl_config_id, `key`, `value`, create_time, update_time, delete_flag",
        "from chnl_config_params",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="chnl_config_id", property="chnlConfigId", jdbcType=JdbcType.INTEGER),
        @Result(column="key", property="key", jdbcType=JdbcType.VARCHAR),
        @Result(column="value", property="value", jdbcType=JdbcType.VARCHAR),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="delete_flag", property="deleteFlag", jdbcType=JdbcType.BIT)
    })
    ChannelConfigParamsDO selectByPrimaryKey(Integer id);

    /**
     *
     * @mbg.generated 2018-08-28
     */
    @UpdateProvider(type=ChannelConfigParamsSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") ChannelConfigParamsDO record, @Param("example") ChannelConfigParamsDOCriteria example);

    /**
     *
     * @mbg.generated 2018-08-28
     */
    @UpdateProvider(type=ChannelConfigParamsSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") ChannelConfigParamsDO record, @Param("example") ChannelConfigParamsDOCriteria example);

    /**
     *
     * @mbg.generated 2018-08-28
     */
    @UpdateProvider(type=ChannelConfigParamsSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(ChannelConfigParamsDO record);

    /**
     *
     * @mbg.generated 2018-08-28
     */
    @Update({
        "update chnl_config_params",
        "set chnl_config_id = #{chnlConfigId,jdbcType=INTEGER},",
          "`key` = #{key,jdbcType=VARCHAR},",
          "`value` = #{value,jdbcType=VARCHAR},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "update_time = #{updateTime,jdbcType=TIMESTAMP},",
          "delete_flag = #{deleteFlag,jdbcType=BIT}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(ChannelConfigParamsDO record);
}