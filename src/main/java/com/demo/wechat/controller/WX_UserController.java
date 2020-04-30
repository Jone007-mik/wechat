package com.demo.wechat.controller;

import com.demo.wechat.common.JsonBean;
import com.demo.wechat.domain.model.WX_User;
import com.demo.wechat.service.WX_UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("user")
public class WX_UserController {
    @Autowired
    private WX_UserService wx_userService;

    @RequestMapping("selectAll")
    @ResponseBody
    public JsonBean selectAll() {
        return wx_userService.selectAll();
    }

    @RequestMapping("selectById")
    @ResponseBody
    public JsonBean selectById(Integer id) {
        return wx_userService.selectByPrimaryKey(id);
    }

    @RequestMapping("insert")
    @ResponseBody
    public JsonBean insert(WX_User wx_user) {
        return wx_userService.insert(wx_user);
    }

    @RequestMapping("update")
    @ResponseBody
    public JsonBean update(WX_User wx_user) {
        return wx_userService.updateByPrimaryKeySelective(wx_user);
    }

    @RequestMapping("delete")
    @ResponseBody
    public JsonBean delete(Integer id) {
        return wx_userService.deleteByPrimaryKey(id);
    }

}
