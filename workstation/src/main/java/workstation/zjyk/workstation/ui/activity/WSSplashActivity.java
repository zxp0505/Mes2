package workstation.zjyk.workstation.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import cn.com.ethank.mylibrary.resourcelibrary.intent.StartIntentUtils;
import cn.com.ethank.mylibrary.resourcelibrary.toast.ToastUtil;
import workstation.zjyk.workstation.ui.WSMainActivity;
import workstation.zjyk.workstation.util.WSConstants;

/**
 * Created by zjgz on 2017/11/24.
 */

public class WSSplashActivity extends AppCompatActivity {
    Handler handler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (WSConstants.isInspect) {
                    //巡检
                    StartIntentUtils.startIntentUtils(WSSplashActivity.this, WSLoginHzActivity.class);
                } else {
                    StartIntentUtils.startIntentUtils(WSSplashActivity.this, WSMainActivity.class);
                }
                finish();
            }
        }, 1000);

    }

    @Override
    protected void onDestroy() {
        handler.removeCallbacksAndMessages(null);
        handler = null;
        super.onDestroy();
    }
}
