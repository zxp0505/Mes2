package workstation.zjyk.line.ui.present;

import java.util.Map;

import workstation.zjyk.line.modle.bean.ExceptionOutBean;
import workstation.zjyk.line.modle.callback.RxDataCallBack;
import workstation.zjyk.line.modle.net.ErrorResultClickListner;
import workstation.zjyk.line.modle.request.FeedOneRequest;
import workstation.zjyk.line.ui.views.ExceptionBindTrayView;

/**
 * Created by zjgz on 2017/11/10.
 */

public class ExceptionBindTrayPresent extends RxPresent<ExceptionBindTrayView> {

    private final FeedOneRequest mFeedOneRequest;

    public ExceptionBindTrayPresent(ExceptionBindTrayView baseView) {
        attachView(baseView);
        mFeedOneRequest = new FeedOneRequest();
    }

    public void requestTrayStatus(Map<String, String> params) {
        mFeedOneRequest.requestTrayExceptionStatus(getView(), params, new RxDataCallBack<String>() {
            @Override
            public void onSucess(String s) {
                if (getView() != null) {
                    ((ExceptionBindTrayView) getView()).showTrayStatus(true);
                }
            }

            @Override
            public void onFail() {
                if (getView() != null) {
                    ((ExceptionBindTrayView) getView()).showTrayStatus(false);
                }
            }
        });
    }

    /**
     * 确认绑定异常托盘
     *
     * @param params
     */
    public void requestMakeSureFeed(Map<String, String> params, ErrorResultClickListner errorResultClickListner) {
        mFeedOneRequest.requestMakeSureFeedException(getView(), params, new RxDataCallBack<ExceptionOutBean>() {
            @Override
            public void onSucess(ExceptionOutBean s) {
                if (getView() != null) {
                    ((ExceptionBindTrayView) getView()).showFeedResult(s);
                }
            }

            @Override
            public void onFail() {
                if (getView() != null) {
                    ((ExceptionBindTrayView) getView()).showFeedResult(null);
                }
            }
        },errorResultClickListner);
    }
}
