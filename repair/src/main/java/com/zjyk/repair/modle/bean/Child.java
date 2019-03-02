package com.zjyk.repair.modle.bean;

public class Child extends Parent {
    public static void eat() {

    }

    private void bullueSort() {
        //冒泡排序  从小往大
        int[] numbers = {1, 3, 2};
        int tem = numbers[0];
        for (int i = 0; i < numbers.length; i++) {

            for (int j = 0; j < numbers.length; j++) {
                if (numbers[j] > numbers[j + 1]) {
                    tem = numbers[j];
                    numbers[j] = numbers[j + 1];
                    numbers[j + 1] = tem;
                }
            }
        }
    }

    private void selectSort() {

        //对未排序的元素中取出最小的元素放到首位
        int[] numbers = {1, 3, 2};
        int tem = 0;
        for (int i = 0; i < numbers.length; i++) {
            for (int j = numbers.length - 1; j > i; j--) {
                if (numbers[j] < numbers[tem]) {
                    tem = j;
                }
            }
            tem++;
        }
    }
}
