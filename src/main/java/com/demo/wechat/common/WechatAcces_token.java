package com.demo.wechat.common;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class WechatAcces_token {

    public static String getAccesTocken() {
        String access_token = null;
        String url = "https://api.weixin.qq.com/cgi-bin/token?" +
                "grant_type=client_credential&appid="+Constant.APPID+"&secret="+Constant.APPSECRET;
        try {
            JSONObject jsonObject = HttpClientUtil.doGet(url);
            access_token = jsonObject.get("access_token").toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return access_token;
    }

    public static void main(String[] args) {
        String accesTocken = getAccesTocken();
        if (accesTocken != null) {
            String url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=" + accesTocken;
            //最大的json
            JSONObject big = new JSONObject();
            //放的一级菜单
            JSONArray button = new JSONArray();
            //创建菜单
            JSONObject menu1 = new JSONObject();
            menu1.put("type", "view");
            menu1.put("name", "首页");
            //menu1.put("url", Constant.getUrl("http://f4qcgsh.hn3.mofasuidao.cn/app/index.html"));
            menu1.put("url", Constant.getUrl("http://f4qcgsh.hn3.mofasuidao.cn/app/index.html"));
            JSONObject menu2 = new JSONObject();
            menu2.put("type", "view");
            menu2.put("name", "购物车");
            //menu2.put("url", Constant.getUrl("http://f4qcgsh.hn3.mofasuidao.cn/app/shopcar.html"));
            menu2.put("url", Constant.getUrl("http://f4qcgsh.hn3.mofasuidao.cn/app/shopcar.html"));
            JSONObject menu3 = new JSONObject();
            menu3.put("type", "view");
            menu3.put("name", "个人中心");
            //menu3.put("url", Constant.getUrl("http://f4qcgsh.hn3.mofasuidao.cn/app/pcenter.html"));
            menu3.put("url", Constant.getUrl("http://f4qcgsh.hn3.mofasuidao.cn/app/pcenter.html"));

            button.put(menu1);
            button.put(menu2);
            button.put(menu3);
            big.put("button", button);
            try {
                JSONObject jsonObject = HttpClientUtil.doPost(url, big.toString());
                System.out.println(jsonObject);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
