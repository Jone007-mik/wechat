package com.demo.wechat.wxUtils;

import com.demo.wechat.common.Constant;
import com.demo.wechat.common.HttpClientUtil;
import org.json.JSONObject;
import sun.net.www.http.HttpClient;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class QRCode {
    public static String getTicket(String scene_str) {
        Long expire_seconds=604800L;
        String action_name="QR_STR_SCENE";
        //scene_str="8";
        String ticket="";
        String url="https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token="+Constant.getAccessTocken();
        String str="{\"expire_seconds\": 604800, \"action_name\": \"QR_STR_SCENE\", \"action_info\": {\"scene\": {\"scene_str\": "+scene_str+"}}}";
        try {
            JSONObject jsonObject = HttpClientUtil.doPost(url, str);
            System.out.println(jsonObject.toString());
            ticket= (String) jsonObject.get("ticket");


        } catch (IOException e) {
            e.printStackTrace();
        }
        return ticket;
    }
    public static String getCodeUrl(String scene_str){
        String ticket = getTicket(scene_str);
        System.out.println("ticket==="+ticket);
        String encode="";
        try {
            encode = URLEncoder.encode(ticket,"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String codeUrl = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=" + encode;
        //JSONObject jsonObject1 = HttpClientUtil.doGet(str2);
        //System.out.println(jsonObject1.toString());
        return codeUrl;
    }

    public static void main(String[] args) {
        System.out.println(getCodeUrl(""));
        //https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=gQGs8TwAAAAAAAAAAS5odHRwOi8vd2VpeGluLnFxLmNvbS9xLzAyWDJHRjVoYURmUGwxWTVTQU50Y08AAgSFu9tdAwSAOgkA
    }
}
