package workstation.zjyk.workstation.ui.views;


/**
 * Created by zjgz on 2017/12/8.
 */

public interface WSRecvierMakingView extends WSReciverBaseView {
    void reciverResult(boolean result,String bindTrayCode);
    /**
     * 当接收物料出现异常的时候 关闭当前 界面
     */
    void closeCurrent();
}
