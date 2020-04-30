package com.demo.wechat.common;

import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class JSsdkUtil {

    public static Map<String ,Object> getSDKMap(String url){
        String jsapi_ticket = Constant.getJsapiTicket();
        Long timestamp=System.currentTimeMillis()/1000;
        String nonceStr = getNoncestr();
        String str="jsapi_ticket="+jsapi_ticket+"&noncestr="+nonceStr+"&timestamp="+timestamp+"&url="+url;
        String signature = getSha1(str);
        Map<String ,Object>map=new HashMap<>();
        map.put("timestamp",timestamp);
        map.put("nonceStr",nonceStr);
        map.put("signature",signature);
        map.put("appId",Constant.APPID);
        return map;
    }

    public static String getNoncestr(){
        String s=UUID.randomUUID().toString().replace("-","").substring(0,16);
        return s;
    }
    public static String getSha1(String str) {
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
            mdTemp.update(str.getBytes("UTF-8"));
            byte[] md = mdTemp.digest();
            int j = md.length;
            char buf[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
                buf[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(buf);
        } catch (Exception e) {
            return str;
        }
    }

    public static void main(String[] args) {
        System.out.println(getNoncestr());
    }
}
