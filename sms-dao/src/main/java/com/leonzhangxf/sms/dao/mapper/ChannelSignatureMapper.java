package com.leonzhangxf.sms.dao.mapper;

import com.leonzhangxf.sms.domain.ChannelSignatureDO;
import com.leonzhangxf.sms.domain.ChannelSignatureDOCriteria;
import java.util.List;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface ChannelSignatureMapper {
    /**
     *
     * @mbg.generated 2018-09-05
     */
    @SelectProvider(type=ChannelSignatureSqlProvider.class, method="countByExample")
    long countByExample(ChannelSignatureDOCriteria example);

    /**
     *
     * @mbg.generated 2018-09-05
     */
    @DeleteProvider(type=ChannelSignatureSqlProvider.class, method="deleteByExample")
    int deleteByExample(ChannelSignatureDOCriteria example);

    /**
     *
     * @mbg.generated 2018-09-05
     */
    @Delete({
        "delete from chnl_signature",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int deleteByPrimaryKey(Integer id);

    /**
     *
     * @mbg.generated 2018-09-05
     */
    @Insert({
        "insert into chnl_signature (id, chnl_config_id, ",
        "signature, `desc`, ",
        "`status`, create_time, ",
        "update_time, delete_flag)",
        "values (#{id,jdbcType=INTEGER}, #{chnlConfigId,jdbcType=INTEGER}, ",
        "#{signature,jdbcType=VARCHAR}, #{desc,jdbcType=VARCHAR}, ",
        "#{status,jdbcType=BIT}, #{createTime,jdbcType=TIMESTAMP}, ",
        "#{updateTime,jdbcType=TIMESTAMP}, #{deleteFlag,jdbcType=BIT})"
    })
    @Options(useGeneratedKeys = true)
    int insert(ChannelSignatureDO record);

    /**
     *
     * @mbg.generated 2018-09-05
     */
    @InsertProvider(type=ChannelSignatureSqlProvider.class, method="insertSelective")
    @Options(useGeneratedKeys = true)
    int insertSelective(ChannelSignatureDO record);

    /**
     *
     * @mbg.generated 2018-09-05
     */
    @SelectProvider(type=ChannelSignatureSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="chnl_config_id", property="chnlConfigId", jdbcType=JdbcType.INTEGER),
        @Result(column="signature", property="signature", jdbcType=JdbcType.VARCHAR),
        @Result(column="desc", property="desc", jdbcType=JdbcType.VARCHAR),
        @Result(column="status", property="status", jdbcType=JdbcType.BIT),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="delete_flag", property="deleteFlag", jdbcType=JdbcType.BIT)
    })
    List<ChannelSignatureDO> selectByExample(ChannelSignatureDOCriteria example);

    /**
     *
     * @mbg.generated 2018-09-05
     */
    @Select({
        "select",
        "id, chnl_config_id, signature, `desc`, `status`, create_time, update_time, delete_flag",
        "from chnl_signature",
        "where id = #{id,jdbcType=INTEGER}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.INTEGER, id=true),
        @Result(column="chnl_config_id", property="chnlConfigId", jdbcType=JdbcType.INTEGER),
        @Result(column="signature", property="signature", jdbcType=JdbcType.VARCHAR),
        @Result(column="desc", property="desc", jdbcType=JdbcType.VARCHAR),
        @Result(column="status", property="status", jdbcType=JdbcType.BIT),
        @Result(column="create_time", property="createTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="update_time", property="updateTime", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="delete_flag", property="deleteFlag", jdbcType=JdbcType.BIT)
    })
    ChannelSignatureDO selectByPrimaryKey(Integer id);

    /**
     *
     * @mbg.generated 2018-09-05
     */
    @UpdateProvider(type=ChannelSignatureSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") ChannelSignatureDO record, @Param("example") ChannelSignatureDOCriteria example);

    /**
     *
     * @mbg.generated 2018-09-05
     */
    @UpdateProvider(type=ChannelSignatureSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") ChannelSignatureDO record, @Param("example") ChannelSignatureDOCriteria example);

    /**
     *
     * @mbg.generated 2018-09-05
     */
    @UpdateProvider(type=ChannelSignatureSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(ChannelSignatureDO record);

    /**
     *
     * @mbg.generated 2018-09-05
     */
    @Update({
        "update chnl_signature",
        "set chnl_config_id = #{chnlConfigId,jdbcType=INTEGER},",
          "signature = #{signature,jdbcType=VARCHAR},",
          "`desc` = #{desc,jdbcType=VARCHAR},",
          "`status` = #{status,jdbcType=BIT},",
          "create_time = #{createTime,jdbcType=TIMESTAMP},",
          "update_time = #{updateTime,jdbcType=TIMESTAMP},",
          "delete_flag = #{deleteFlag,jdbcType=BIT}",
        "where id = #{id,jdbcType=INTEGER}"
    })
    int updateByPrimaryKey(ChannelSignatureDO record);
}