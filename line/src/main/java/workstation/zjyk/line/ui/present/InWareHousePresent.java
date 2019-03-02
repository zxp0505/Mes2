package workstation.zjyk.line.ui.present;

import workstation.zjyk.line.modle.callback.RxDataCallBack;
import workstation.zjyk.line.modle.request.FeedOneRequest;
import workstation.zjyk.line.ui.views.BaseView;

import java.util.Map;

/**
 * Created by zjgz on 2017/10/27.
 */

public class InWareHousePresent extends RxPresent<BaseView> {

    private final FeedOneRequest mFeedOneRequest;

    public InWareHousePresent(BaseView view) {
        attachView(view);
        mFeedOneRequest = new FeedOneRequest();
    }

    public void inWareRequest(Map<String, String> params) {
        mFeedOneRequest.inWareRequest(getView(), params, new RxDataCallBack<String>() {
            @Override
            public void onSucess(String s) {
                if (getView() != null) {
                    getView().showNetData(s);
                }
            }

            @Override
            public void onFail() {
                if (getView() != null) {
                    getView().showNetData(null);
                }
            }
        });
    }

}
