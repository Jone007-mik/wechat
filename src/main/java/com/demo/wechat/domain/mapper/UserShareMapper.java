package com.demo.wechat.domain.mapper;

import com.demo.wechat.domain.model.UserShare;

public interface UserShareMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserShare record);

    int insertSelective(UserShare record);

    UserShare selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserShare record);

    int updateByPrimaryKey(UserShare record);

    UserShare selectBySId(String sid);

}