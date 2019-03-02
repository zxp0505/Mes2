package workstation.zjyk.workstation.ui.views;

import android.content.Context;

/**
 * Created by zjgz on 2018/1/24.
 */

public interface WSReciverBaseView  extends WSBaseView {
    //检查是否已经绑定托盘了
    void checkTrayResult(boolean result,String str);
    Context getContextByView();
}
