package workstation.zjyk.line.ui.present;

import android.os.SystemClock;

import workstation.zjyk.line.modle.bean.GoodsBean;
import workstation.zjyk.line.modle.request.ReciverRequest;
import workstation.zjyk.line.ui.views.ReciverView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zjgz on 2017/10/25.
 */

public class ReciverPresent extends MvpBasePresenter<ReciverView> {

    private final ReciverRequest reciverRequest;

    public ReciverPresent(ReciverView reciverView) {
        attachView(reciverView);
        reciverRequest = new ReciverRequest(this);
    }

    public void loadData(final int pageNo, final int pageSize) {
        Map<String, String> map = new HashMap<>();
        map.put("pageNo", pageNo + "");
        map.put("pageSize", pageSize + "");

        Observable.create(new ObservableOnSubscribe<List<GoodsBean>>() {
            @Override
            public void subscribe(ObservableEmitter<List<GoodsBean>> e) throws Exception {
                List<GoodsBean> dataList = new ArrayList<>();
                if (pageNo == 4) {
                    for (int i = 0; i < pageSize - 3; i++) {
                        GoodsBean bean = new GoodsBean();
                        bean.setOrderId("" + ((pageNo - 1) * pageSize + i));
                        bean.setCount("11");
                        bean.setMaterielBatchNumber("" + ((pageNo - 1) * pageSize + i));
                        bean.setName("物料:" + ((pageNo - 1) * pageSize + i));
                        dataList.add(bean);
                    }
                } else {
                    for (int i = 0; i < pageSize; i++) {
                        GoodsBean bean = new GoodsBean();
                        bean.setCount("11");
                        bean.setMaterielBatchNumber("" + ((pageNo - 1) * pageSize + i));
                        bean.setName("物料:" + ((pageNo - 1) * pageSize + i));
                        bean.setName("物料:" + ((pageNo - 1) * pageSize + i));
                        dataList.add(bean);
                    }
                }
                SystemClock.sleep(1000);

                e.onNext(dataList);
                e.onComplete();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<List<GoodsBean>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(List<GoodsBean> goodsBeans) {
//                getView().hideLoading();
                if (pageNo == 1) {
                    getView().showFirstData(goodsBeans);
                } else {
                    getView().loadMoreData(goodsBeans);
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
//        reciverRequest.requestData(map, new DataCallback<GoodsBean>() {
//            @Override
//            public void onError(Call call, Exception e, int id) {
//                ToastUtil.showCenterShort("onerror:" + e.getMessage());
//            }
//
//            @Override
//            public void onResponse(BaseBean<GoodsBean> response, int id) {
////                if (pageNo == 1) {
////                    getView().showFirstData(data);
////                } else {
////                    getView().loadMoreData(data);
////                }
//            }
//        });
    }
}
