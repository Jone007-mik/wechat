package com.demo.wechat.controller;

import com.demo.wechat.common.Constant;
import com.demo.wechat.common.HttpClientUtil;
import com.demo.wechat.common.JSsdkUtil;
import com.demo.wechat.common.JsonBean;
import com.demo.wechat.domain.model.WX_User;
import com.demo.wechat.service.MessageService;
import com.demo.wechat.service.WX_UserService;
import com.demo.wechat.vo.WechatInfoVO;
import com.demo.wechat.wxUtils.QRCode;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

@RestController
public class WechatController {
    @Autowired
    private MessageService messageService;
    @Autowired
    private WX_UserService wx_userService;
    @GetMapping("index")
    public String getIndex(WechatInfoVO wechatInfoVO) {
        if (wechatInfoVO != null) {
            if (wechatInfoVO.getSignature().equalsIgnoreCase(wechatInfoVO.getSha1())) {
                return wechatInfoVO.getEchostr();
            }
        }
        return wechatInfoVO.getEchostr();
    }

    @PostMapping("index")
    public String postIndex(HttpServletRequest request) {
        String xml="SUCCESS";
        ServletInputStream inputStream = null;
        try {
            inputStream = request.getInputStream();
            Element rootElement = new SAXBuilder().build(inputStream).getRootElement();
             xml = messageService.updateMessage(rootElement);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JDOMException e) {
            e.printStackTrace();
        }
        return xml;
    }

    /**
     * 网页授权
     * @param code
     * @param request
     * @return
     */
    @PostMapping("oauth")
    public JsonBean oauth(String code,HttpServletRequest request){
        System.out.println("code="+code);
        String url ="https://api.weixin.qq.com/sns/oauth2/access_token?appid="+ Constant.APPID+"&secret="+Constant.APPSECRET+"&code="+code+"&grant_type=authorization_code";
        String openId="";
        try {
            JSONObject jsonObject = HttpClientUtil.doGet(url);
            System.out.println(jsonObject.toString());
            openId=jsonObject.get("openid").toString();
            WX_User wx_user = (WX_User) wx_userService.selectByOpenId(openId).getData();
            if(wx_user!=null) {
                request.getSession().setAttribute("wx_user" , wx_user);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new JsonBean(0,"ok",openId);
    }

    /**
     *JS-SDK是微信公众平台 面向网页开发者提供的基于微信内的网页开发工具包
     * @param targetUrl
     * @return
     */
    @PostMapping("share")
    public JsonBean share(String targetUrl,HttpServletRequest request) {
        System.out.println(targetUrl);
        Map<String, Object> map = JSsdkUtil.getSDKMap(targetUrl);
        WX_User wx_user = (WX_User) request.getSession().getAttribute("wx_user");
        String ticket=QRCode.getTicket(String.valueOf(wx_user.getId()));
        System.out.println("ticket===="+ticket);
        System.out.println();
        map.put("ticket",ticket);
        return new JsonBean(0, "ok", map);
    }
    @PostMapping("qrCode")
    public JsonBean qrCode(String scene_str) {
        String codeUrl = QRCode.getCodeUrl(scene_str);
        System.out.println("分享二维码codeUrl========"+codeUrl);
        return new JsonBean(0,"ok",codeUrl);
    }


}
