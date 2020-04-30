package com.demo.wechat.common;

import com.github.wxpay.sdk.WXPayConfig;

import java.io.InputStream;

public class MyWXPayConfig implements WXPayConfig {
    @Override
    public String getAppID() {
        return Constant.APPID;
    }

    @Override
    public String getMchID() {
        return Constant.MCHID;
    }

    @Override
    public String getKey() {
        return Constant.KEY;
    }

    @Override
    public InputStream getCertStream() {
        return null;
    }

    @Override
    public int getHttpConnectTimeoutMs() {
        return 0;
    }

    @Override
    public int getHttpReadTimeoutMs() {
        return 0;
    }
}
