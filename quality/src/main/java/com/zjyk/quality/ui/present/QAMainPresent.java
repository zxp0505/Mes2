package com.zjyk.quality.ui.present;

import com.zjyk.quality.modle.bean.QAPerson;
import com.zjyk.quality.modle.bean.QAWorkStationVo;
import com.zjyk.quality.modle.callback.QARxDataCallBack;
import com.zjyk.quality.modle.request.QAMainRequest;
import com.zjyk.quality.ui.views.QABaseView;
import com.zjyk.quality.ui.views.QAMainView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zjgz on 2017/11/3.
 */

public class QAMainPresent extends QARxPresent<QAMainView> {

    private final QAMainRequest mMainRequest;

    public QAMainPresent() {
        mMainRequest = new QAMainRequest();
    }

    /**
     * 获取工位信息  ---线边库
     */
    public void getStationName() {
        mMainRequest.getStationName(new HashMap<String, String>(), getView(), new QARxDataCallBack<QAWorkStationVo>() {
            @Override
            public void onSucess(QAWorkStationVo workStationVo) {
                if (getView() != null) {
                    ((QAMainView) getView()).showStation(workStationVo);
                }
            }

            @Override
            public void onFail() {
                if (getView() != null) {
                    ((QAMainView) getView()).showStation(null);
                }
            }
        });
    }

    /**
     * 获取工位上所有人员信息
     */
    public void getStationAllPerson(boolean loading) {
        mMainRequest.getStationAllPerson(new HashMap<String, String>(), getView(), new QARxDataCallBack<List<QAPerson>>() {
            @Override
            public void onSucess(List<QAPerson> personList) {
                if (getView() != null) {
                    ((QAMainView) getView()).showAllPersons(personList);
                }
            }

            @Override
            public void onFail() {
                if (getView() != null) {
//                    ((MainView) getView()).showAllPersons(null);
                }
            }
        }, loading);
    }

    public void loginOut(Map<String, String> map) {
        mMainRequest.loginOut(map, getView(), new QARxDataCallBack<String>() {
            @Override
            public void onSucess(String s) {
                if (getView() != null) {
                    ((QAMainView) getView()).loginOutResult(true);
                }
            }

            @Override
            public void onFail() {
                if (getView() != null) {
                    ((QAMainView) getView()).loginOutResult(false);
                }
            }
        });
    }


    /**
     * 发送心跳
     *
     * @param params
     */
    public void requestHeart(Map<String, String> params) {
        mMainRequest.requestHeart(params, getView(), new QARxDataCallBack<String>() {
            @Override
            public void onSucess(String s) {

            }

            @Override
            public void onFail() {

            }
        }, false);
    }
}
