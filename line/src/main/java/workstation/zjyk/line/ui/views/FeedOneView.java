package workstation.zjyk.line.ui.views;

import workstation.zjyk.line.modle.bean.WorkStationRequest;
import workstation.zjyk.line.modle.bean.WorkStationRequestDetail;

import java.util.List;

/**
 * Created by zjgz on 2017/11/9.
 */

public interface FeedOneView extends BaseView {
    void showStationDatas(List<WorkStationRequest> datas);
    void showStationRequireDetails(List<WorkStationRequestDetail> datas);
}
