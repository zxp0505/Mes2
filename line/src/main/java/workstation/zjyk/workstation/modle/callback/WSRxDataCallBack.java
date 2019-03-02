package workstation.zjyk.workstation.modle.callback;

/**
 * Created by zjgz on 2017/11/5.
 */

public interface WSRxDataCallBack<T> {
    void onSucess(T t);

    void onFail();

}
