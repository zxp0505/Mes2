package workstation.zjyk.com.scanapp.modle.callback;

/**
 * Created by zjgz on 2017/11/5.
 */

public interface ScanRxDataCallBack<T> {
    void onSucess(T t);

    void onFail(String message,Throwable throwable);

}
