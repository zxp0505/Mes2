//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package workstation.zjyk.line.modle.bean;

import workstation.zjyk.line.modle.net.ResultEnum;

import java.io.Serializable;

public class BaseResult implements Serializable {
    private ResultEnum result;
    private String message;
    private String data;
    private String code;
    private int totalCount = 0;

    public BaseResult() {
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

    public String getData() {
        return this.data;
    }

    public void setData(String data) {
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
