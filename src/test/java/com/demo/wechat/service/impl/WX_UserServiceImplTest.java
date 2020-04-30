package com.demo.wechat.service.impl;

import com.demo.wechat.domain.mapper.WX_UserMapper;
import com.demo.wechat.domain.model.WX_User;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class WX_UserServiceImplTest {
    @Autowired
    private WX_UserMapper wx_userMapper;
    @Test
    public void deleteByPrimaryKey() {
    }

    @Test
    public void insertSelective() {
        WX_User wx_user=new WX_User();
        wx_user.setOpenid("123");
        wx_user.setNickname("肖瑜");
        //int a=wx_userMapper.insert(wx_user);
        //Assert.assertEquals(1,a);
    }

    @Test
    public void selectByPrimaryKey() {
        //WX_User wx_user=wx_userMapper.selectByPrimaryKey(1);
        //Assert.assertNotNull(wx_user);
    }

    @Test
    public void selectAll() {
    }

    @Test
    public void updateByPrimaryKeySelective() {
    }
}