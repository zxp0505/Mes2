package com.zjyk.repair.ui;

import cn.com.ethank.mylibrary.resourcelibrary.toast.ToastUtil;

public interface Test {
    void a();
    default void test(){
        ToastUtil.showInfoCenterShort("接口内部的默认实现");
    }
}
