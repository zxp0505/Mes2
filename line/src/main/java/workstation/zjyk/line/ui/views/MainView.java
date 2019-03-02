package workstation.zjyk.line.ui.views;

import java.util.List;

import workstation.zjyk.line.modle.bean.AppUpdate;
import workstation.zjyk.line.modle.bean.Person;
import workstation.zjyk.line.modle.bean.WorkStationVo;

/**
 * Created by zjgz on 2017/11/10.
 */

public interface MainView extends BaseView {
    void showStation(WorkStationVo workStationVo);

    void loginOutResult(boolean result);

    void clearTrayResult(boolean result);

    void showAllPersons(List<Person> personList);

    void showCheckApkInfo(AppUpdate wsAppUpdate);

    void downApkResult(boolean result, String saveUrl);
    void showTestServerResult(boolean result);
}
