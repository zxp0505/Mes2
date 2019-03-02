package workstation.zjyk.workstation.modle.bean;

import cn.com.ethank.mylibrary.resourcelibrary.utils.NumberUtils;

/**
 * Created by zjgz on 2017/12/8.
 */

public class WSMYMater {
    private String name;
    private int sholdReciver;
    private int areadlyReciver;
    private int surplus;
    private double exceptionCount;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSholdReciver() {
        return sholdReciver;
    }

    public void setSholdReciver(int sholdReciver) {
        this.sholdReciver = sholdReciver;
    }

    public int getAreadlyReciver() {
        return areadlyReciver;
    }

    public void setAreadlyReciver(int areadlyReciver) {
        this.areadlyReciver = areadlyReciver;
    }

    public int getSurplus() {
        return surplus;
    }

    public void setSurplus(int surplus) {
        this.surplus = surplus;
    }

    public double getExceptionCount() {
        return exceptionCount;
    }

    public void setExceptionCount(double exceptionCount) {
        this.exceptionCount = NumberUtils.todoubleSaveTwo(exceptionCount);
    }
}
