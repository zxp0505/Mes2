//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package workstation.zjyk.workstation.modle.bean;

import java.io.Serializable;

import workstation.zjyk.workstation.modle.bean.enumpackage.ResultEnum;

/**
 * 针对结果的data提供泛型支持
 *
 * @param <T>
 */
public class WSBaseResultCommon<T> implements Serializable {
    private ResultEnum result;
    private String message;
    private T data;
    private String code;
    private int totalCount = 0;
    /**
     * 1： 对象 2：List 3  string或其他
     */
    private int dataType;

    public WSBaseResultCommon() {
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

    public int getDataType() {
        return dataType;
    }

    public void setDataType(int dataType) {
        this.dataType = dataType;
    }
}
