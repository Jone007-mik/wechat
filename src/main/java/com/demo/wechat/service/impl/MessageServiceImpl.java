package com.demo.wechat.service.impl;

import com.demo.wechat.common.Constant;
import com.demo.wechat.common.HttpClientUtil;
import com.demo.wechat.common.JsonUtil;
import com.demo.wechat.domain.mapper.UserShareMapper;
import com.demo.wechat.domain.mapper.WX_UserMapper;
import com.demo.wechat.domain.model.UserShare;
import com.demo.wechat.domain.model.WX_User;
import com.demo.wechat.service.MessageService;
import org.jdom2.Element;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    private WX_UserMapper wx_userMapper;
    @Autowired
    private UserShareMapper userShareMapper;
    @Override
    public String updateMessage(Element rootElement) {
        //开发者微信号
        String toUserName = rootElement.getChildText("ToUserName");
        //发送方帐号（一个OpenID）
        String fromUserName = rootElement.getChildText("FromUserName");
        //消息创建时间 （整型)
        String createTime = rootElement.getChildText("CreateTime");
        //消息类型，event
        String msgType = rootElement.getChildText("MsgType");
        //事件类型，subscribe(订阅)、unsubscribe(取消订阅)
        String event = rootElement.getChildText("Event");
        //二维码
        String Ticket=(rootElement.getChildText("Ticket")==null)?"":rootElement.getChildText("Ticket");
        String EventKey=(rootElement.getChildText("EventKey")==null)?"":rootElement.getChildText("EventKey");
        System.out.println("EventKey"+EventKey);
        System.out.println("Ticket"+Ticket);
        String xml= messageType(msgType, event,fromUserName,toUserName,Ticket,EventKey);
        return xml;
    }
    //关注公众号的业务层
    private String sub(String openId ,String toUserName){
        String xml="SUCCESS";
        WX_User wxUserinfo = getWXUserinfo(openId);
        WX_User wx_user = wx_userMapper.selectByOpenId(openId);
        if(wx_user==null){
            System.out.println("增加用户");
            wx_userMapper.insert(wxUserinfo);
        }else if(wx_user.getSubscribe()==0){
            System.out.println("你已经注册了,并修改subscribe为1");
            wxUserinfo.setSubscribe(1);
            System.out.println(wxUserinfo.toString());
            wx_userMapper.updateByPrimaryKeySelective(wxUserinfo);
        }
        xml="<xml>\n" +
                "  <ToUserName><![CDATA["+openId+"]]></ToUserName>\n" +
                "  <FromUserName><![CDATA["+toUserName+"]]></FromUserName>\n" +
                "  <CreateTime>"+System.currentTimeMillis()+"</CreateTime>\n" +
                "  <MsgType><![CDATA[news]]></MsgType>\n" +
                "  <ArticleCount>3</ArticleCount>\n" +
                "  <Articles>\n" +
                "  <item>\n" +
                "      <Title><![CDATA[分享送红包]]></Title>\n" +
                "      <Description><![CDATA[越扫越土豪]]></Description>\n" +
                "      <PicUrl><![CDATA[http://f4qcgsh.hn3.mofasuidao.cn/app/images/money1.png]]></PicUrl>\n" +
                "      <Url><![CDATA["+Constant.getUrl("http://f4qcgsh.hn3.mofasuidao.cn/app/extension.html")+"]]></Url>\n" +
                "  </item>\n" +
                "  <item>\n" +
                "      <Title><![CDATA[秒杀活动]]></Title>\n" +
                "      <Description><![CDATA[秒杀活动]]></Description>\n" +
                "      <PicUrl><![CDATA[http://f4qcgsh.hn3.mofasuidao.cn/app/upload/qiang.png]]></PicUrl>\n" +
                "      <Url><![CDATA["+Constant.getUrl("http://f4qcgsh.hn3.mofasuidao.cn/app/timed-seckill.html")+"]]></Url>\n" +
                "    </item>\n" +
                "    <item>\n" +
                "      <Title><![CDATA[刘世洋中间]]></Title>\n" +
                "      <Description><![CDATA[description1]]></Description>\n" +
                "      <PicUrl><![CDATA[http://f4qcgsh.hn3.mofasuidao.cn/app/upload/qiang.png]]></PicUrl>\n" +
                "      <Url><![CDATA["+Constant.getUrl("http://f4qcgsh.hn3.mofasuidao.cn/app/assess.html")+"]]></Url>\n" +
                "    </item>\n" +
                "  </Articles>\n" +
                "</xml>";
        return xml;
    }
    //取消关注的业务层
    private void unSub(String openId){
        WX_User wx_user = new WX_User();
        wx_user.setSubscribe(0);
        wx_user.setOpenid(openId);
        wx_userMapper.updateByOpenId(wx_user);
    }
    //用户扫码的业务层
    private void scan(String toUserName,String openId,String Ticket,String EventKey){
       String sid=openId;
       int fid= Integer.parseInt(EventKey.split("_")[1]);
        UserShare userShare=new UserShare();
        userShare.setFid(fid);
        userShare.setSid(sid);
        if(userShareMapper.selectBySId(sid)==null){
            System.out.println(userShareMapper.insert(userShare));;
        }else{
            System.out.println("你已经扫过啦，不能重复邀请");
        }
    }
    private  String messageType(String msgType,String event,String openId,String toUserName,String Ticket,String EventKey){
        String xml="SUCCESS";
        if(msgType.equals("event")){
            System.out.println("进入事件类型&判断事件");
            if(Ticket!=""){
                if(EventKey!=""){
                    if(event.equals("SCAN")) {
                        System.out.println("判断事件为已关注用户扫码事件");
                    }else{
                        System.out.println("判断事件为未关注用户扫码事件");
                        scan(toUserName, openId, Ticket,EventKey);
                    }
                }
            }
                if(event.equals("subscribe")){
                System.out.println("判断事件为关注事件");
                 xml = sub(openId,toUserName);
            }else if(event.equals("unsubscribe")){
                System.out.println("判断事件为取消关注事件");
                unSub(openId);
            }else if(event.equals("SCAN")){
                System.out.println("判断事件为已关注用户扫码事件");
            }else if(event.equals("LOCATION")){
                System.out.println("判断事件为地理位置事件");
            }else if(event.equals("CLICK")){
                System.out.println("判断事件为自定义菜单点击事件");
            }else if(event.equals("VIEW")){
                System.out.println("判断事件为自定义菜单view事件");
            }
        }else if(msgType.equals("text")) {
            //进入消息类型
            System.out.println("判断事件为文本消息");
        }else if(event.equals("image")){
            System.out.println("判断事件为图片消息");
        }else if(event.equals("voice")){
            System.out.println("判断事件为语音消息");
        }else if(event.equals("video")){
            System.out.println("判断事件为视频消息");
        }else if(event.equals("shortvideo")){
            System.out.println("判断事件为小视频消息");
        }else if(event.equals("location")){
            System.out.println("判断事件为地理位置消息");
        }else if(event.equals("link")){
            System.out.println("判断事件为链接消息");
        }
        return xml;
    }
    private WX_User getWXUserinfo(String openId) {
        System.out.println("Constant.getAccessTocken()+++==" + Constant.getAccessTocken());
        String url = "https://api.weixin.qq.com/cgi-bin/user/info?access_token="+Constant.getAccessTocken()+"&openid="+openId+"&lang=zh_CN";
        WX_User wx_user = null;
        try {
            JSONObject jsonObject = HttpClientUtil.doGet(url);
            wx_user = JsonUtil.fromJson(jsonObject.toString(), WX_User.class);
            System.out.println(wx_user.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return wx_user;
    }
}
