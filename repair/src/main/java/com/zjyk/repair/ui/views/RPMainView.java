package com.zjyk.repair.ui.views;

import com.zjyk.repair.modle.bean.RPAppUpdate;
import com.zjyk.repair.modle.bean.RPPerson;
import com.zjyk.repair.modle.bean.RPWorkReportRecordVO;
import com.zjyk.repair.modle.bean.RPWorkStationVo;

import java.util.List;

/**
 * Created by zjgz on 2017/11/10.
 */

public interface RPMainView extends RPBaseView {
    void showStation(RPWorkStationVo workStationVo);

    void loginOutResult(boolean result);


    void showAllPersons(List<RPPerson> personList);

    void downApkResult(boolean result, String saveUrl);

    void showCheckApkInfo(RPAppUpdate RPAppUpdate);

    void showUserReportDetail(List<RPWorkReportRecordVO> wsWorkReportRecordVOList);
}
