package com.zjyk.repair.ui;


public class TestMain {
    public static void main(String[] args) {
        System.out.print("MAIN.....");
        TestChild instance = Single.INSTANCE.getInstance();
//        Log.e("","main....");
    }
}
