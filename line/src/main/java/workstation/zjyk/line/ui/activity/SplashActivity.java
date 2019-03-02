package workstation.zjyk.line.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import cn.com.ethank.mylibrary.resourcelibrary.intent.StartIntentUtils;

/**
 * Created by zjgz on 2017/11/24.
 */

public class SplashActivity extends AppCompatActivity {
    Handler handler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                StartIntentUtils.startIntentUtils(SplashActivity.this, MainActivity.class);
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
