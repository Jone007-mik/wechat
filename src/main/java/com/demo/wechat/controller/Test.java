package com.demo.wechat.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Test {
    public static void main(String[] args) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmssS");
        String  a = simpleDateFormat.format(new Date());
        System.out.println(a);
        Map map=new HashMap();

        System.out.println(System.currentTimeMillis()/1000);
    }
}
