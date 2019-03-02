package workstation.zjyk.line.modle.bean;

import java.io.Serializable;

/**
 * 确认发放物料界面使用
 * Created by zjgz on 2017/10/27.
 */

public class MaterDetailBean implements Serializable {
    private String materailNumber;
    private String materailName;
    private String materailRequire;
    private String materailType;
    private int materailSurplusBag;//包内剩余
    private int materailSurplusWarehouse;//仓内剩余
    private int materailTureDistrubeBag;//实际分配包内
    private int materailTureDistrubeWareHouse;//实际分配仓内
    boolean isClickBag; //点击的是包内还是仓内
    int position;//记录bean位置

    public String getMaterailNumber() {
        return materailNumber;
    }

    public void setMaterailNumber(String materailNumber) {
        this.materailNumber = materailNumber;
    }

    public String getMaterailName() {
        return materailName;
    }

    public void setMaterailName(String materailName) {
        this.materailName = materailName;
    }

    public String getMaterailRequire() {
        return materailRequire;
    }

    public void setMaterailRequire(String materailRequire) {
        this.materailRequire = materailRequire;
    }

    public String getMaterailType() {
        return materailType;
    }

    public void setMaterailType(String materailType) {
        this.materailType = materailType;
    }

    public int getMaterailSurplusBag() {
        return materailSurplusBag;
    }

    public void setMaterailSurplusBag(int materailSurplusBag) {
        this.materailSurplusBag = materailSurplusBag;
    }

    public int getMaterailSurplusWarehouse() {
        return materailSurplusWarehouse;
    }

    public void setMaterailSurplusWarehouse(int materailSurplusWarehouse) {
        this.materailSurplusWarehouse = materailSurplusWarehouse;
    }

    public int getMaterailTureDistrubeBag() {
        return materailTureDistrubeBag;
    }

    public void setMaterailTureDistrubeBag(int materailTureDistrubeBag) {
        this.materailTureDistrubeBag = materailTureDistrubeBag;
    }

    public int getMaterailTureDistrubeWareHouse() {
        return materailTureDistrubeWareHouse;
    }

    public void setMaterailTureDistrubeWareHouse(int materailTureDistrubeWareHouse) {
        this.materailTureDistrubeWareHouse = materailTureDistrubeWareHouse;
    }

    public boolean isClickBag() {
        return isClickBag;
    }

    public void setClickBag(boolean clickBag) {
        isClickBag = clickBag;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

}
