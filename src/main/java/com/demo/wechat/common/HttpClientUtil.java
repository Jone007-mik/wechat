package com.demo.wechat.common;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.IOException;

public class HttpClientUtil {
    public static JSONObject doGet(String url) throws IOException {
        JSONObject jsonObject=null;
        //发送get请求，并创建HttpGet
        HttpGet httpGet=new HttpGet(url);
        //创建http请求连接
        HttpClient httpClient=new DefaultHttpClient();
        //执行发送请求，并响应结果
        HttpResponse httpResponse=httpClient.execute(httpGet);
        //判断请求是否成功
        if(httpResponse.getStatusLine().getStatusCode()==200){
            //响应结果
            String s = EntityUtils.toString(httpResponse.getEntity(), "utf-8");
            jsonObject=new JSONObject(s);
        }

        return jsonObject;
    }
    public static JSONObject doPost(String url,String str) throws IOException {
        JSONObject jsonObject=null;
        HttpPost httpPost=new HttpPost(url);
        HttpClient httpClient=new DefaultHttpClient();
        httpPost.setEntity(new StringEntity(str,"utf-8"));
        HttpResponse httpResponse=httpClient.execute(httpPost);
        if(httpResponse.getStatusLine().getStatusCode()==200){
            //执行响应
            String s = EntityUtils.toString(httpResponse.getEntity(), "utf-8");
            jsonObject=new JSONObject(s);
        }

        return jsonObject;
    }
}


















