package com.demo.wechat;

import java.util.ArrayList;
import java.util.List;

public class Test{
    public static void main(String[] args) {
        List<User> list=new ArrayList();
        for (int i=0;i<10;i++){
            User user=new User("name"+i,"time"+(10-i));
            list.add(user);
        }

        List<User> res=Action(list);
        for (int i=0;i<res.size();i++){
            User user=list.get(i);
            System.out.println(i+"==="+user.register_time);
        }

    }
    public static List<User> Action(List<User> list){

        for(int i=0;i<list.size();i++){
            User user=list.get(i);
            int a= Integer.parseInt(user.register_time.split("time")[1]);
            for(int j=i;j<list.size();j++){
                User user2=list.get(j);
                int b= Integer.parseInt(user2.register_time.split("time")[1]);
                if(a>b){
                    User useri=list.get(i);
                    User userj=list.get(j);
                    User temp=useri;
                    useri.setUserName(userj.userName);;
                    useri.setRegister_time(userj.register_time);
                    userj.setUserName(temp.userName);
                    userj.setRegister_time(temp.register_time);
                }

            }
        }
        return list;
    }
}
class User{
     String userName;
     String register_time;

    public User(String userName, String register_time) {
        this.userName = userName;
        this.register_time = register_time;
    }

    public User() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRegister_time() {
        return register_time;
    }

    public void setRegister_time(String register_time) {
        this.register_time = register_time;
    }
}
