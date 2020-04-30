package com.demo.wechat.common;

import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class Constant {
    //测试环境
    public static final String APPID = "wxf74ed69e5184825d";
    //测试环境
    public static final String APPSECRET = "33feff934a40a6719efee08846991f6c";
    //假装的数据库
    public static Map<String ,String> map=new HashMap<>();
    //念念的小卖部
    //正式公众号APPID
    //public static final String APPID = "wxc37f01894579f97b";
    //正式公众号APPSECRET
    //public static final String APPSECRET = "3fbcaa5d45231815023070fdd2220a49";
    //正式公众号MCHID
    public static final String MCHID = "1519853611";
    //正式公众号KEY
    public static final String KEY = "1234567890qwertyuiopasdfghjklzxc";
    //全局接口唯一调用凭据access_tocken
    public static String ACCESS_TOCKEN = null;
    //获取access_tocken时间
    public static Long ACCESS_TOCKEN_TIME = 0L;
    //微信js调用临时凭据jsapi_ticket
    public static String JSAPI_TICKET = null;
    //获取jsapi_ticket时间
    public static Long JSAPI_TICKET_TIME = 0L;

    public static String getAccessTocken() {
        Long nowTime = System.currentTimeMillis() / 1000;
        //定义7200s失效，所以提前一点创建access_tocken
        if (ACCESS_TOCKEN == null || nowTime - ACCESS_TOCKEN_TIME > 7000) {
            String url = "https://api.weixin.qq.com/cgi-bin/token?" +
                    "grant_type=client_credential&appid=" + APPID + "&secret=" + APPSECRET;
            try {
                JSONObject jsonObject = HttpClientUtil.doGet(url);
                if (jsonObject != null) {
                    ACCESS_TOCKEN = (String) jsonObject.get("access_token");
                    ACCESS_TOCKEN_TIME = System.currentTimeMillis() / 1000;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return ACCESS_TOCKEN;
    }
    public static String getJsapiTicket(){
        long nowTime = System.currentTimeMillis();
        if(JSAPI_TICKET==null||nowTime-JSAPI_TICKET_TIME>7000){
            String url="https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="+getAccessTocken()+"&type=jsapi";
            try {
                JSONObject jsonObject = HttpClientUtil.doGet(url);
                JSAPI_TICKET= jsonObject.get("ticket").toString();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return JSAPI_TICKET;
    }

    public  static String getUrl(String url)  {
        //使用 urlEncode 对链接进行处理
        try {
            String encode = URLEncoder.encode(url, "utf-8");
            url="https://open.weixin.qq.com/connect/oauth2/authorize?appid="+Constant.APPID+"&redirect_uri="+encode+"&response_type=code&" +
                    "scope=snsapi_userinfo&state=STATE#wechat_redirect";
            System.out.println(url);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return  url;
    }
    public static void main(String[] args) {
        System.out.println(getJsapiTicket());
    }
}
