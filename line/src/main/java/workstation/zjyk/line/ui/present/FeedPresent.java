package workstation.zjyk.line.ui.present;

import android.os.SystemClock;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import workstation.zjyk.line.modle.bean.Wrap;
import workstation.zjyk.line.modle.callback.RxDataCallBack;
import workstation.zjyk.line.modle.net.FeedRequest;
import workstation.zjyk.line.ui.views.FeedView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zjgz on 2017/10/26.
 */

public class FeedPresent extends MvpBasePresenter<FeedView> {

    private final FeedRequest mFeedRequest;

    public FeedPresent(FeedView feedView) {
        attachView(feedView);
        mFeedRequest = new FeedRequest();

    }

    /**
     * 获取收料记录
     *
     * @param pageNo
     * @param pageSize
     */
    public void requestReciverRecord(final int pageNo, final int pageSize, final boolean isShowDialog) {
        Map<String, String> params = new HashMap<>();
        params.put("pageNumber", pageNo + "");
        params.put("pageSize", pageSize + "");
        mFeedRequest.requestReciverRecord(getView(), params, new RxDataCallBack<List<Wrap>>() {
            @Override
            public void onSucess(List<Wrap> wraps) {
                if (getView() != null) {
                    if (pageNo == 1) {
                        getView().showFirstData(wraps);
                    } else {
                        getView().loadMoreData(wraps);
                    }
                }
            }

            @Override
            public void onFail() {
                if (getView() != null) {
                    getView().loadError();
                }
            }
        }, isShowDialog);
//        Observable.create(new ObservableOnSubscribe<List<Wrap>>() {
//            @Override
//            public void subscribe(ObservableEmitter<List<Wrap>> e) throws Exception {
//                List<Wrap> dataList = new ArrayList<>();
//                if (pageNo == 4) {
//                    for (int i = 0; i < pageSize - 3; i++) {
//                        Wrap bean = new Wrap();
//                        bean.setOrderId("" + ((pageNo - 1) * pageSize + i));
//                        bean.setCode("条码"+ i);
//                        dataList.add(bean);
//                    }
//                } else {
//                    for (int i = 0; i < pageSize; i++) {
//                        Wrap bean = new Wrap();
//                        bean.setOrderId("" + ((pageNo - 1) * pageSize + i));
//                        bean.setCode("条码"+ i);
//                        dataList.add(bean);
//                    }
//                }
//                SystemClock.sleep(1000);
//
//                e.onNext(dataList);
//                e.onComplete();
//            }
//        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<List<Wrap>>() {
//            @Override
//            public void onSubscribe(Disposable d) {
//
//            }
//
//            @Override
//            public void onNext(List<Wrap> goodsBeans) {
////                getView().hideLoading();
//                if (getView() == null) {
//                    return;
//                }
//                if (pageNo == 1) {
//                    ((FeedView) getView()).showFirstData(goodsBeans);
//                } else {
//                    ((FeedView) getView()).loadMoreData(goodsBeans);
//                }
//            }
//
//            @Override
//            public void onError(Throwable e) {
//
//            }
//
//            @Override
//            public void onComplete() {
//
//            }
//        });
    }

    /**
     * 获取扫描的结果
     *
     * @param scanResult
     */
    public void getBarCodeStatus(final String scanResult, boolean isLook) {
        if (getView() == null) {
            return;
        }
        Map<String, String> parms = new HashMap<>();
        parms.put("code", scanResult);
        mFeedRequest.getBarCodeResult(getView(), parms, new RxDataCallBack<Wrap>() {
            @Override
            public void onSucess(Wrap wrap) {
                if (getView() != null) {
                    if (wrap != null) {
                        wrap.setOnlyLook(isLook);
                    }
                    getView().showScanResult(wrap);
                }
            }

            @Override
            public void onFail() {
                if (getView() != null) {
                    getView().showScanResult(null);

                }
            }
        });
    }
}
