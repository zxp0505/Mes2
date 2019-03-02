package com.zjyk.repair.ui.fragment;

import android.os.Bundle;
import android.view.View;

import com.trello.rxlifecycle2.LifecycleTransformer;
import com.zjyk.repair.R;
import com.zjyk.repair.modle.bean.RPErrorBean;
import com.zjyk.repair.modle.net.RPErrorResultClickListner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Vector;
import java.util.WeakHashMap;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class RPContentFragment extends RPBaseFragment {
    @Override
    protected void creatPresent() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Collections.unmodifiableList(new ArrayList<>());
                HashSet<String> sets = new HashSet<>();
            }
        });
        Vector<String> vector=new Vector<>();
        vector.add("");
//        Collections.checkedList()

    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {

    }

    @Override
    public void initData() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_content;
    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    public void showResultError(RPErrorBean errorBean, RPErrorResultClickListner errorResultClickListner) {

    }

    @Override
    public LifecycleTransformer bindToLife() {
        return null;
    }

}
