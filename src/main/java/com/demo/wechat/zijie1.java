package com.demo.wechat;

import java.util.*;

public class zijie1{
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();
        for (int i = 0; i < t; i++) {
            int n = sc.nextInt();
            sc.useDelimiter("\n");
            String A = sc.next();
            String B = sc.next();
            String[] arrayA = A.split(" ");
            String[] arrayB = A.split(" ");
            System.out.println(Action(arrayA, arrayB));
            Set set=new HashSet();
            Map map=new Hashtable();
        }

    }

    public static String Action(String[] A, String[] B) {
        int l = 0;
        int r = 0;
        boolean flag = true;
        int de = 0;
        String result = null;
        for (int i = 0; i < A.length; i++) {

            if (!A[i].equals(B[i])) {
                if (l == 0) {
                    de = Integer.parseInt(A[i]) - Integer.parseInt(B[i]);
                }
                l = i;
                flag = false;
                int temp = Integer.parseInt(A[i]) - Integer.parseInt(B[i]);
                if (temp != de) {
                    return "NO";
                }
                if (flag == false) {
                    if (A[i].equals(B[i])) {
                        r = i;
                    }
                }
                if (r > 0) {
                    if (A[i].equals(B[i])) {
                        return "NO";
                    }
                }

            }

        }
        return "YES";
    }
}
