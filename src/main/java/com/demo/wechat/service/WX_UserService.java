package com.demo.wechat.service;

import com.demo.wechat.common.JsonBean;
import com.demo.wechat.domain.model.WX_User;

public interface WX_UserService {
    JsonBean deleteByPrimaryKey(Integer id);

    JsonBean insert(WX_User record);

    JsonBean selectByPrimaryKey(Integer id);

    JsonBean selectByOpenId(String openId);

    JsonBean selectAll();

    JsonBean updateByPrimaryKeySelective(WX_User record);

    JsonBean updateByOpenId(WX_User wx_user);
}
