package com.demo.wechat.common;

import com.github.wxpay.sdk.WXPay;

import java.util.HashMap;
import java.util.Map;

public class PayTest {
    public static void main(String[] args) {
       /* WXPay wxPay = new WXPay(new MyWXPayConfig());
        Map<String, String> reqData =new HashMap<>();
        reqData.put("body","支付测试：LCJ");//商品描述
        reqData.put("out_trade_no","20191126125347");//商户订单号  不能重复提交
        reqData.put("total_fee","1");//标价金额
        reqData.put("spbill_create_ip","123.12.12.123");//终端IP
        reqData.put("notify_url","http://f4qcgsh.hn3.mofasuidao.cn/wechat/pay/notifyUrl");//通知地址
        reqData.put("trade_type","NATIVE ");//交易类型
        try {
            Map<String, String> requestData = wxPay.fillRequestData(reqData);
            Map<String, String> unifiedOrder = wxPay.unifiedOrder(requestData);
            System.out.println(unifiedOrder);
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }
}
