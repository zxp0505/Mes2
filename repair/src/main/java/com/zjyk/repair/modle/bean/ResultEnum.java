//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.zjyk.repair.modle.bean;

public enum ResultEnum implements BaseEnum {
    OK("正确"),
    ERROR("错误");

    private final String key;

    ResultEnum(String key) {
        this.key = key;
    }

    public String getKey() {
        return this.key;
    }
}
