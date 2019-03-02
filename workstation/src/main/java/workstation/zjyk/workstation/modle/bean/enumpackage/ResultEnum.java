//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package workstation.zjyk.workstation.modle.bean.enumpackage;

import workstation.zjyk.workstation.modle.bean.enumpackage.BaseEnum;

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
