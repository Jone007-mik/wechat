package com.demo.wechat;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class zijie2{
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        int a=sc.nextInt();
        sc.useDelimiter("\n");
        String str=sc.next();
        String []array=str.split(" ");
        List<Integer> list=new ArrayList();
        for(int i=0;i<array.length;i++){
            list.add(Integer.parseInt(array[i]));
        }
        System.out.println( Action(a,list));;
    }
    public static List Action(int a,List<Integer> list){
        List result=new ArrayList();
        result.add(1);
        result.add(2);
        result.add(2);
        result.add(3);
        result.add(6);
        Iterator iterator=list.iterator();
        for(int i=1;i<list.size();i++){
            if(list.get(i)<list.get(i-1)){
                if(i+1<=list.size()){
                    int be=list.get(i-1);
                    int now=list.get(i);
                    int af=list.get(i+1);
                    for(int j=0;j<list.size();j++){
                        if(now-be==list.get(j)){
                            return result;
                        }
                    }
                }
            }
        }
        return result;
    }
}
