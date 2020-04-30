package com.demo.wechat.domain.mapper;

import com.demo.wechat.domain.model.WX_User;

import java.util.List;

public interface WX_UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(WX_User record);

    int insertSelective(WX_User record);

    WX_User selectByPrimaryKey(Integer id);

    WX_User selectByOpenId(String openid);

    List<WX_User> selectAll();

    int updateByPrimaryKeySelective(WX_User record);

    int updateByPrimaryKey(WX_User record);

    int updateByOpenId(WX_User wx_user);
}