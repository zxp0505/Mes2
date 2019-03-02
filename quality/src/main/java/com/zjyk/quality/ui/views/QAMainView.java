package com.zjyk.quality.ui.views;

import com.zjyk.quality.modle.bean.QAPerson;
import com.zjyk.quality.modle.bean.QAWorkStationVo;

import java.util.List;

/**
 * Created by zjgz on 2017/11/10.
 */

public interface QAMainView extends QABaseView {
    void showStation(QAWorkStationVo workStationVo);

    void loginOutResult(boolean result);

    void clearTrayResult(boolean result);

    void showAllPersons(List<QAPerson> personList);
}
