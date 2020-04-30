package com.demo.wechat.controller;

import com.demo.wechat.common.Constant;
import com.demo.wechat.common.DateUtil;
import com.demo.wechat.common.JsonBean;
import com.demo.wechat.common.MyWXPayConfig;
import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayUtil;
import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("pay")
public class PayController {
    @PostMapping("jsapiPay")
    public JsonBean jsapiPay(String openid){
        System.out.println(openid);
        JsonBean jsonBean=new JsonBean(-1,"未支付",null);
        //随机生成订单号(规则)： LCJ+用户ID+时间轴+商品ID
        String sdfTimes = DateUtil.getSdfTimes();
        String out_trade_no="LCJ-12-3"+sdfTimes;
        String  total_fee="1";//正常是在数据库进行查询得到
        WXPay wxPay = new WXPay(new MyWXPayConfig());
        Map<String, String> reqData =new HashMap<>();
        reqData.put("body","japaiPay支付测试：LCJ");//商品描述
        reqData.put("out_trade_no",out_trade_no);//商户订单号  不能重复提交
        reqData.put("open_id",openid);
        reqData.put("total_fee",total_fee);//标价金额
        reqData.put("spbill_create_ip","123.12.12.123");//终端IP
        reqData.put("notify_url","http://www.scorpio-cc.com/wechat/pay/notifyUrl");//通知地址f4qcgsh.hn3.mofasuidao.cn
        reqData.put("trade_type","JSAPI");//交易类型
        try {
            Map<String, String> unifiedOrder = wxPay.unifiedOrder(reqData);
            System.out.println("调用统一下单API返回unifiedOrder="+unifiedOrder.toString());
            /**
             * 在return_code ==success时有返回错误信息
             * 在return_code ==success&&result_code==success时返回的信息存入数据库
             */
            String prepay_id=unifiedOrder.get("prepay_id");
            //网页调起支付签名
            Map<String, String> pageMap = new HashMap<>();
            pageMap.put("appId",Constant.APPID);
            pageMap.put("timeStamp", String.valueOf(System.currentTimeMillis()/1000));
            pageMap.put("nonceStr",WXPayUtil.generateNonceStr());
            pageMap.put("package","prepay_id="+prepay_id);
            pageMap.put("signType","MD5");
            pageMap.put("paySign",WXPayUtil.generateSignature(pageMap,Constant.KEY));
            System.out.println("JSAPI支付所需参数pageMap="+pageMap.toString());
            jsonBean=new JsonBean(0,"ok",pageMap);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return jsonBean;
    }

    /**
     * nativePay支付验证
     * @param out_trade_no
     * @return
     */
    @PostMapping("cheakOrder")
    public JsonBean cheakOrder(String out_trade_no){
        JsonBean jsonBean=new JsonBean(-1,"未支付",null);
        Map<String, String> map = Constant.map;
        System.out.println(map);
        //nativePay中map放入的订单号进行验证
        String orderStatus = map.get(out_trade_no);
        System.out.println("orderStatus==="+orderStatus);
        if(orderStatus.equals("1")){
            jsonBean=new JsonBean(0,"支付成功",null);
        }
        return jsonBean;
    }

    /**
     * 生成二维码
     * @return
     */
    @PostMapping("nativePay")
    public JsonBean nativePay(){
        JsonBean jsonBean = new JsonBean(-1,"",null);
        //以下代码应该在Service中，金额应通过商品id查询数据库得到，有优惠活动应该在后端处理
        String  total_fee="1";
        //在这个map数据库中存放
        Map<String, String> map = Constant.map;
        //生成订单号(规则)： LCJ+用户ID+时间轴+商品ID
        String sdfTimes = DateUtil.getSdfTimes();
        String out_trade_no="LCJ-11-27"+sdfTimes;
        WXPay wxPay = new WXPay(new MyWXPayConfig());
        Map<String, String> reqData =new HashMap<>();
        reqData.put("body","支付测试：LCJ");//商品描述
        reqData.put("out_trade_no",out_trade_no);//商户订单号  不能重复提交
        reqData.put("total_fee",total_fee);//标价金额
        reqData.put("spbill_create_ip","123.12.12.123");//终端IP
        reqData.put("notify_url","http://f4qcgsh.hn3.mofasuidao.cn/wechat/pay/notifyUrl");//通知地址
        reqData.put("trade_type","NATIVE ");//交易类型
        try {
            Map<String, String> requestData = wxPay.fillRequestData(reqData);//通过reqData字典排序拼接成字符串+商户key, 最后得到sign
            Map<String, String> unifiedOrder = wxPay.unifiedOrder(requestData);
            System.out.println(unifiedOrder);
            if(unifiedOrder.get("return_code").equals("SUCCESS")&&unifiedOrder.get("return_msg").equals("OK")){
                map.put("out_trade_no",out_trade_no);
                map.put(out_trade_no,"-1");//以out_trade_no作为动态的key  -1表示未支付
                map.put("code_url",unifiedOrder.get("code_url"));
             jsonBean=new JsonBean(0,"OK",map);
                System.out.println(jsonBean.getData());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonBean;
    }

    /**
     * 微信回调方法，在
     * @param request
     * @return
     */
    @RequestMapping("notifyUrl")
    public String  notifyUrl(HttpServletRequest request){
        System.out.println("接受到了微信的回调");
        Map<String, String> db = Constant.map;
        try {
            ServletInputStream inputStream = request.getInputStream();
            Document build = new SAXBuilder().build(inputStream);
            String return_code = build.getRootElement().getChildText("return_code");
            if(return_code.equals("SUCCESS")){
                String out_trade_no = build.getRootElement().getChildText("out_trade_no");
                //根据订单id修改订单状态
                db.put(out_trade_no,"1");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JDOMException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Map<String ,String> map=new HashMap<>();
        map.put("return_code","SUCCESS");
        map.put("return_msg","OK");
        String xml = "";
        try {
            xml = WXPayUtil.mapToXml(map);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return xml;
    }
}
