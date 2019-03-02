package workstation.zjyk.com.scanapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zxing.ZXingView;
import workstation.zjyk.com.scanapp.R;

/**
 * Created by zjgz on 2018/1/19.
 */

public class ScanCodeActivity extends AppCompatActivity implements QRCodeView.Delegate {
    @BindView(R.id.zxingview)
    ZXingView mQRCodeView;
    @BindView(R.id.tv_control_light)
    TextView tvControlLight;
    @BindView(R.id.tv_quit)
    TextView tvQuit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_code);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mQRCodeView.setDelegate(this);
    }

    @OnClick({R.id.tv_control_light, R.id.tv_quit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_control_light:
                String str = tvControlLight.getText().toString();
                if ("打开灯光".equals(str)) {
                    mQRCodeView.openFlashlight();
                    tvControlLight.setText("关闭灯光");
                } else if ("关闭灯光".equals(str)) {
                    mQRCodeView.closeFlashlight();
                    tvControlLight.setText("打开灯光");
                }

                break;
            case R.id.tv_quit:
                quitFinish(false);
                break;
        }
    }

    private void quitFinish(boolean hasResult) {
        Intent intent = new Intent();
        intent.putExtra("scanResult", scanResult);
        intent.putExtra("hasResult", hasResult);
        setResult(1, intent);
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mQRCodeView.startCamera();
//        mQRCodeView.startCamera(Camera.CameraInfo.CAMERA_FACING_FRONT);

        mQRCodeView.showScanRect();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mQRCodeView.startSpotAndShowRect();
    }

    @Override
    protected void onStop() {
        mQRCodeView.stopCamera();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        mQRCodeView.onDestroy();
        super.onDestroy();
    }

    private void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(200);
    }

    String scanResult;

    @Override
    public void onScanQRCodeSuccess(String result) {
//        Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
        vibrate();
        if (!TextUtils.isEmpty(result)) {
            this.scanResult = result;
            quitFinish(true);
        }

//        mQRCodeView.startSpot();
    }

    @Override
    public void onScanQRCodeOpenCameraError() {
        Toast.makeText(this, "打开相机出错", Toast.LENGTH_SHORT).show();
    }

}
