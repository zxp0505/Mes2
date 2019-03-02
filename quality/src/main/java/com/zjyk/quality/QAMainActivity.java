package com.zjyk.quality;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.util.TreeMap;

/**
 * Created by zjgz on 2017/12/1.
 */

public class QAMainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rp_activity_main);
        TreeMap<String, String> treeMap = new TreeMap<>();
    }
}
