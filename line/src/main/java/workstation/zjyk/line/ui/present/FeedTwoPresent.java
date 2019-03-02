package workstation.zjyk.line.ui.present;

import workstation.zjyk.line.modle.bean.WorkStationDistributeMateriel;
import workstation.zjyk.line.modle.callback.RxDataCallBack;
import workstation.zjyk.line.modle.request.FeedOneRequest;
import workstation.zjyk.line.ui.views.BaseView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zjgz on 2017/10/27.
 */

public class FeedTwoPresent extends MvpBasePresenter<BaseView> {

    private final FeedOneRequest mFeedOneRequest;

    public FeedTwoPresent(BaseView view) {
        attachView(view);
        mFeedOneRequest = new FeedOneRequest();
    }

    public void requestData(String code, String requestId) {
        Map<String, String> map = new HashMap<>();
        map.put("code", code);
        map.put("requestId", requestId);
        mFeedOneRequest.getInHouseData(getView(), map, new RxDataCallBack<List<WorkStationDistributeMateriel>>() {
            @Override
            public void onSucess(List<WorkStationDistributeMateriel> workStationDistributeMateriels) {
                if (getView() != null) {
                    getView().showNetData(workStationDistributeMateriels);
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
//        Observable.create(new ObservableOnSubscribe<List<MaterDetailBean>>() {
//            @Override
//            public void subscribe(ObservableEmitter<List<MaterDetailBean>> e) throws Exception {
//                List<MaterDetailBean> list = new ArrayList<>();
//                for (int i = 0; i < 5; i++) {
//                    MaterDetailBean bean = new MaterDetailBean();
//                    bean.setMaterailName("螺丝");
//                    bean.setMaterailNumber("111100");
//                    bean.setMaterailRequire("100");
//                    bean.setMaterailSurplusBag(5);
//                    bean.setMaterailSurplusWarehouse(10);
//                    bean.setMaterailTureDistrubeBag(0);
//                    bean.setMaterailTureDistrubeWareHouse(0);
//                    list.add(bean);
//                }
//                SystemClock.sleep(2000);
//                e.onNext(list);
//                e.onComplete();
//
//            }
//        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<List<MaterDetailBean>>() {
//            @Override
//            public void onSubscribe(Disposable d) {
//
//            }
//
//            @Override
//            public void onNext(List<MaterDetailBean> stationBeans) {
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
}
