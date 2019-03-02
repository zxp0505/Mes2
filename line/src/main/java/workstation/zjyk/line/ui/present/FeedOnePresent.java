package workstation.zjyk.line.ui.present;

import workstation.zjyk.line.modle.bean.WorkStationRequest;
import workstation.zjyk.line.modle.bean.WorkStationRequestDetail;
import workstation.zjyk.line.modle.callback.RxDataCallBack;
import workstation.zjyk.line.modle.request.FeedOneRequest;
import workstation.zjyk.line.ui.views.FeedOneView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zjgz on 2017/10/27.
 */

public class FeedOnePresent extends MvpBasePresenter<FeedOneView> {

    private final FeedOneRequest mFeedOneRequest;

    public FeedOnePresent(FeedOneView view) {
        attachView(view);
        mFeedOneRequest = new FeedOneRequest();

    }

    public void requestData(String wrapid) {
        Map<String, String> map = new HashMap<>();
        map.put("code", wrapid);
        mFeedOneRequest.getStation(getView(), map, new RxDataCallBack<List<WorkStationRequest>>() {
            @Override
            public void onSucess(List<WorkStationRequest> workStationRequests) {
                if (getView() != null) {
                    getView().showStationDatas(workStationRequests);
                }
            }

            @Override
            public void onFail() {
                if (getView() != null) {
                    getView().showNetData(null);
                }
            }
        });
//        if (getView() != null) {
//            getView().showLoading("");
//        }
//        Observable.create(new ObservableOnSubscribe<List<StationBean>>() {
//            @Override
//            public void subscribe(ObservableEmitter<List<StationBean>> e) throws Exception {
//                List<StationBean> list = new ArrayList<>();
//                for (int i = 0; i < 10; i++) {
//                    StationBean bean = new StationBean();
//                    bean.setProduce("机械初加工");
//                    bean.setProductNumber("生产编号：1000000000");
//                    bean.setWorkNumber("工位001");
//                    list.add(bean);
//                }
//                SystemClock.sleep(2000);
//                e.onNext(list);
//                e.onComplete();
//
//            }
//        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<List<StationBean>>() {
//            @Override
//            public void onSubscribe(Disposable d) {
//
//            }
//
//            @Override
//            public void onNext(List<StationBean> stationBeans) {
//                BaseView view = getView();
//                if (view != null) {
//                    view.hideLoading();
//                    view.showNetData(stationBeans);
//
//                }
//
//
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

    public void requestMaterailDetail(Map<String, String> map) {
        mFeedOneRequest.requestMaterailDetail(getView(), map, new RxDataCallBack<List<WorkStationRequestDetail>>() {
            @Override
            public void onSucess(List<WorkStationRequestDetail> workStationRequestDetails) {
                if (getView() != null) {
                    getView().showStationRequireDetails(workStationRequestDetails);
                }
            }

            @Override
            public void onFail() {
                if (getView() != null) {
                    getView().showStationRequireDetails(null);
                }
            }
        });
    }
}
