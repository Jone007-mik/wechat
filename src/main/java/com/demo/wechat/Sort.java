package com.demo.wechat;

import java.util.Random;
import java.util.Scanner;

public class Sort{
    public static void main(String[] args) {
        int[] array=new int[1000000];
        for(int i=0;i<array.length;i++){
            Random rm =new Random();
            array[i]=rm.nextInt(999);
        }
        int begin_Time= (int) System.currentTimeMillis();
        //public_sort(array);
        quick_sort(array,0,array.length-1);
        int end_Time= (int) System.currentTimeMillis();
        System.out.println(end_Time-begin_Time);
    }
    public static void quick_sort(int[] array,int left,int right){
        if(left>=right){
            return;
        }
        //首先进行初始赋值
        int base=array[left];
        int i=left;
        int j=right;
        //先判断j与基准数base，若j大则j--否则判断i,若ij的循环都停下了，则交换ij，执行下一次遍历
        while(i!=j){
            while(array[j]>=base&&i<j){
                j--;
            }
            while(array[i]<=base&&i<j){
                i++;
            }
            int temp=array[j];
            array[j]=array[i];
            array[i]=temp;
        }
        //跳出while后，先把基准值与此时相遇的i j交换值，然后递归调用
        array[left]=array[i];
        array[i]=base;
        //左边的
        quick_sort(array,left,i);
        //左边的
        quick_sort(array,j+1,right);

    }

    /**
     * 普通排序
     * @param array
     * @return
     */
    public static int[] public_sort(int []array){
        for(int i=0;i<array.length;i++){
            for(int j=i;j<array.length;j++){
                if(array[i]>array[j]){
                    int temp=array[j];
                    array[j]=array[i];
                    array[i]=temp;
                }
            }
        }
        return array;

    }
}
