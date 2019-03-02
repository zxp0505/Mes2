package workstation.zjyk.line.modle.callback;

/**
 * Created by zjgz on 2017/11/5.
 */

public interface RxDataCallBack<T> {
    void onSucess(T t);

    void onFail();

}
