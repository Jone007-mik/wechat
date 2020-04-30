package com.demo.wechat.service.impl;

import com.demo.wechat.common.JsonBean;
import com.demo.wechat.domain.mapper.WX_UserMapper;
import com.demo.wechat.domain.model.WX_User;
import com.demo.wechat.service.WX_UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class WX_UserServiceImpl implements WX_UserService {
    @Autowired
    private WX_UserMapper wx_userMapper;

    @Override
    public JsonBean deleteByPrimaryKey(Integer id) {
        int a=wx_userMapper.deleteByPrimaryKey(id);
        JsonBean jsonBean = new JsonBean(-1,"error",null);
        if(a>0){
            jsonBean = new JsonBean(0,"ok",id);
        };
        return jsonBean;
    }

    @Override
    public JsonBean insert(WX_User record) {
        int a=wx_userMapper.insert(record);
        JsonBean jsonBean = new JsonBean(-1,"error",null);
        if(a>0){
            jsonBean = new JsonBean(0,"ok",record);
        };
        return jsonBean;
    }

    @Override
    public JsonBean selectByPrimaryKey(Integer id) {
        WX_User wx_user=wx_userMapper.selectByPrimaryKey(id);
        JsonBean jsonBean = new JsonBean(-1,"error",null);
        if(wx_user!=null){
            jsonBean = new JsonBean(0,"ok",wx_user);
        };
        return jsonBean;
    }

    @Override
    public JsonBean selectByOpenId(String openId) {
        WX_User wx_user=wx_userMapper.selectByOpenId(openId);
        JsonBean jsonBean = new JsonBean(-1,"error",null);
        if(wx_user!=null){
            jsonBean = new JsonBean(0,"ok",wx_user);
        };
        return jsonBean;
    }

    @Override
    public JsonBean selectAll() {
        return new JsonBean(0,"ok",wx_userMapper.selectAll());
    }

    @Override
    public JsonBean updateByPrimaryKeySelective(WX_User record) {
        int a=wx_userMapper.updateByPrimaryKeySelective(record);
        System.out.println(record.toString());
        JsonBean jsonBean = new JsonBean(-1,"error",null);
        if(a>0){
            jsonBean = new JsonBean(0,"ok",record);
            System.out.println("修改成功");
        };
        return jsonBean;
    }

    @Override
    public JsonBean updateByOpenId(WX_User wx_user) {
        int a=wx_userMapper.updateByOpenId(wx_user);
        JsonBean jsonBean = new JsonBean(-1,"error",null);
        if(a>0){
            jsonBean = new JsonBean(0,"ok",wx_user);
        };
        return jsonBean;
    }
}
