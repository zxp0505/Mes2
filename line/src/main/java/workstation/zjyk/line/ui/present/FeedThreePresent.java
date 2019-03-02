package workstation.zjyk.line.ui.present;

import workstation.zjyk.line.modle.callback.RxDataCallBack;
import workstation.zjyk.line.modle.net.ErrorResultClickListner;
import workstation.zjyk.line.modle.request.FeedOneRequest;
import workstation.zjyk.line.ui.views.BaseView;
import workstation.zjyk.line.ui.views.FeedThreeView;

import java.util.Map;

/**
 * Created by zjgz on 2017/11/10.
 */

public class FeedThreePresent extends RxPresent<FeedThreeView> {

    private final FeedOneRequest mFeedOneRequest;

    public FeedThreePresent(FeedThreeView baseView) {
        attachView(baseView);
        mFeedOneRequest = new FeedOneRequest();
    }

    public void requestTrayStatus(Map<String, String> params) {
        mFeedOneRequest.requestTrayStatus(getView(), params, new RxDataCallBack<String>() {
            @Override
            public void onSucess(String s) {
                if (getView() != null) {
                    ((FeedThreeView) getView()).showTrayStatus(true);
                }
            }

            @Override
            public void onFail() {
                if (getView() != null) {
                    ((FeedThreeView) getView()).showTrayStatus(false);
                }
            }
        });
    }

    /**
     * 确认发料
     *
     * @param params
     */
    public void requestMakeSureFeed(Map<String, String> params, ErrorResultClickListner errorResultClickListner) {
        mFeedOneRequest.requestMakeSureFeed(getView(), params, new RxDataCallBack<String>() {
            @Override
            public void onSucess(String s) {
                if (getView() != null) {
                    ((FeedThreeView) getView()).showFeedResult(s);
                }
            }

            @Override
            public void onFail() {
                if (getView() != null) {
                    ((FeedThreeView) getView()).showFeedResult("");
                }
            }
        },errorResultClickListner);
    }
}
