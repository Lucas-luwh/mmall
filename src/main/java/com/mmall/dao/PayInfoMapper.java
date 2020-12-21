package com.mmall.dao;

import com.mmall.pojo.PayInfo;

/**
 * @author julin
 */
public interface PayInfoMapper {
    int deleteByPrimaryKey(Integer id);

    /**
     * 支付信息
     * @param record 支付信息
     * @return 结果
     */
    int insert(PayInfo record);

    int insertSelective(PayInfo record);

    PayInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PayInfo record);

    int updateByPrimaryKey(PayInfo record);
}