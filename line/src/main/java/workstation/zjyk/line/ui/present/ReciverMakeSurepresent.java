package workstation.zjyk.line.ui.present;

import workstation.zjyk.line.modle.callback.RxDataCallBack;
import workstation.zjyk.line.modle.net.ReciverMakeSureRequest;
import workstation.zjyk.line.ui.views.BaseView;

import java.util.Map;

/**
 * Created by zjgz on 2017/10/26.
 */

public class ReciverMakeSurepresent extends MvpBasePresenter<BaseView> {

    private final ReciverMakeSureRequest makeSureRequesteciverMakeSureRequest;

    public ReciverMakeSurepresent(BaseView baseView) {
        attachView(baseView);
        makeSureRequesteciverMakeSureRequest = new ReciverMakeSureRequest();

    }

    public void reciverRequest(Map<String, String> map) {
        makeSureRequesteciverMakeSureRequest.makeSureReciver(map, getView(), new RxDataCallBack<String>() {
            @Override
            public void onSucess(String s) {
                if (getView() != null) {
                    getView().showNetData(1);
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
