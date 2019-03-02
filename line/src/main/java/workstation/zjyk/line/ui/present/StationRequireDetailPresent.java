package workstation.zjyk.line.ui.present;

import android.os.SystemClock;

import workstation.zjyk.line.modle.bean.StationMaterailDetailBean;
import workstation.zjyk.line.ui.views.BaseView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zjgz on 2017/10/27.
 */

public class StationRequireDetailPresent extends MvpBasePresenter<BaseView> {
    public StationRequireDetailPresent(BaseView view) {
        attachView(view);
    }

    public void requestData() {
        if (getView() != null) {
            getView().showLoading("");
        }
        Observable.create(new ObservableOnSubscribe<List<StationMaterailDetailBean>>() {
            @Override
            public void subscribe(ObservableEmitter<List<StationMaterailDetailBean>> e) throws Exception {
                List<StationMaterailDetailBean> list = new ArrayList<>();
                for (int i = 0; i < 5; i++) {
                    StationMaterailDetailBean bean = new StationMaterailDetailBean();
                    bean.setCountRequire(4);
                    bean.setCountWareHouse(5);
                    bean.setName("螺丝");
                    bean.setNumber("10002");
                    list.add(bean);
                }
                SystemClock.sleep(2000);
                e.onNext(list);
                e.onComplete();

            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<List<StationMaterailDetailBean>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(List<StationMaterailDetailBean> stationBeans) {
                BaseView view = getView();
                if (view != null) {
                    view.hideLoading();
                    view.showNetData(stationBeans);

                }


            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }
}
