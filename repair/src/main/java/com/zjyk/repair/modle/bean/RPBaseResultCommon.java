//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.zjyk.repair.modle.bean;

import java.io.Serializable;

/**
 * 针对结果的data提供泛型支持
 *
 * @param <T>
 */
public class RPBaseResultCommon<T> implements Serializable {
    private ResultEnum result;
    private String message;
    private T data;
    private String code;
    private int totalCount = 0;
    private int dataType;

    public RPBaseResultCommon() {
    }

    public int getDataType() {
        return dataType;
    }

    public void setDataType(int dataType) {
        this.dataType = dataType;
    }

    public int getTotalCount() {
        return this.totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public ResultEnum getResult() {
        return this.result;
    }

    public void setResult(ResultEnum result) {
        this.result = result;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public static void main(String[] args) {
    }
}
