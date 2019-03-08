package workstation.zjyk.com.scanapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zbar.ZBarView;
import cn.com.ethank.mylibrary.resourcelibrary.intent.StartIntentUtils;
import cn.com.ethank.mylibrary.resourcelibrary.toast.ToastUtil;
import cn.com.ethank.mylibrary.resourcelibrary.utils.UICommonUtil;
import workstation.zjyk.com.scanapp.R;
import workstation.zjyk.com.scanapp.modle.bean.QualityHandledRecordVO;
import workstation.zjyk.com.scanapp.ui.present.ScanCodePresent;
import workstation.zjyk.com.scanapp.ui.views.ScanCodeView;
import workstation.zjyk.com.scanapp.util.BundleParams;
import workstation.zjyk.com.scanapp.util.ScanConstants;

/**
 * Created by zjgz on 2018/1/19.
 */

public class ScanCode2Activity extends ScanBaseActivity<ScanCodePresent> implements QRCodeView.Delegate, ScanCodeView {
    @BindView(R.id.zbarview)
    ZBarView mQRCodeView;
    @BindView(R.id.tv_control_light)
    TextView tvControlLight;
    @BindView(R.id.tv_history)
    TextView tvHistory;
//    @BindView(R.id.tv_quit)
//    TextView tvQuit;

    int flag = 0;//0:扫描完进入操作界面   1:扫描完进入详情界面
    @BindView(R.id.edit_number)
    EditText editNumber;
    @BindView(R.id.btn_sure)
    Button btnSure;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_scan_code2);
//        AppManager.getAppManager().addActivity(this);
//        ButterKnife.bind(this);
//        initView();
//        initPresent();
    }

    @Override
    public void initOnCreate() {
        Intent intent = getIntent();
        if (intent != null) {
            flag = intent.getIntExtra(BundleParams.FLAG, 0);

        }
        mRlTitle.setVisibility(View.GONE);
        if (ScanConstants.isCheckTray()) {
            tvHistory.setVisibility(View.GONE);
        } else {
            if (flag == 1) {
                //从历史记录过来
                mRlTitle.setVisibility(View.VISIBLE);
                tvHistory.setVisibility(View.GONE);
                tvTitle.setText("请扫描追溯码");
            } else {
                tvHistory.setVisibility(View.VISIBLE);
            }
        }


        mQRCodeView.setDelegate(this);
    }

    @OnClick({R.id.tv_control_light, R.id.tv_history, R.id.btn_sure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_control_light:
                String str = tvControlLight.getText().toString();
                if ("打开手电筒".equals(str)) {
                    mQRCodeView.openFlashlight();
                    tvControlLight.setText("关闭手电筒");
                    UICommonUtil.setTextViewDrawableTop(ScanCode2Activity.this, R.drawable.icon_light_hover, tvControlLight);
                } else if ("关闭手电筒".equals(str)) {
                    mQRCodeView.closeFlashlight();
                    tvControlLight.setText("打开手电筒");
                    UICommonUtil.setTextViewDrawableTop(ScanCode2Activity.this, R.drawable.icon_light, tvControlLight);
                }

                break;
            case R.id.tv_history:
                requestHistoryList();
                break;
            case R.id.btn_sure:
                String number = editNumber.getText().toString();
                if (!TextUtils.isEmpty(number)) {
                    onScanQRCodeSuccess(number);
                } else {
                    ToastUtil.showInfoCenterShort("条码不能为空");
                }
                break;
        }
    }


    private void requestHistoryList() {
        currentPresent.requestHistoryList(1, 8, "", "", true);
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

    //----------------------
    @Override
    protected View getLoadingTargetView() {
        return mFlContent;
    }

    @Override
    protected void creatPresent() {
        currentPresent = new ScanCodePresent();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_scan_code2;
    }

    //--------------------
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
            Bundle bundle = new Bundle();
            bundle.putString("scanResult", scanResult);
            bundle.putBoolean(BundleParams.TODETAIL, false);
            if (ScanConstants.isCheckTray()) {
                StartIntentUtils.startIntentUtils(this, ScanResultActivity.class, bundle);
            } else {
                if (flag == 1) {
                    bundle.putBoolean(BundleParams.TODETAIL, true);
                    bundle.putString(BundleParams.RECOEDID, scanResult);
                    bundle.putInt(BundleParams.FLAG, flag);
                }
                StartIntentUtils.startIntentUtils(this, ScanResultAboutPictrueActivity.class, bundle);
            }
            finish();
//            quitFinish(true);
        }

//        mQRCodeView.startSpot();
    }

    @Override
    public void onScanQRCodeOpenCameraError() {
        Toast.makeText(this, "打开相机出错", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showHistoryList(List<QualityHandledRecordVO> historyList) {
        if (historyList != null) {
            if (historyList.size() > 0) {
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList(BundleParams.DATA, (ArrayList<? extends Parcelable>) historyList);
                StartIntentUtils.startIntentUtils(this, ScanHistoryActivity.class, bundle);
            } else {
                ToastUtil.showInfoCenterShort("暂无历史记录");
            }
        }
    }

    @Override
    public void showLoading(String msg) {
        showLoadingDialog(msg);
    }

    @Override
    public void hideLoading() {
        hideLoadingDialog();
    }

}
