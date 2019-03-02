package workstation.zjyk.line.ui.views;

import workstation.zjyk.line.modle.bean.Wrap;

import java.util.List;

/**
 * Created by zjgz on 2017/10/25.
 */

public interface FeedView extends BaseView {
    void showFirstData(List<Wrap> datas);

    void loadMoreData(List<Wrap> datas);

    void showScanResult(Wrap wrap);

    void loadError();

}
