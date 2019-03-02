package com.zjyk.repair.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.v7.widget.ButtonBarLayout;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.ethank.mylibrary.resourcelibrary.toast.ToastUtil;
import com.zjyk.repair.R;
import com.zjyk.repair.modle.bean.RPPerson;
import com.zjyk.repair.ui.present.RPLoginPresenterImpl;
import com.zjyk.repair.ui.views.RPILoginView;


/**
 * Created by hanxue on 17/6/21.
 */

public class RPActivityLogin extends RPBaseActivity implements View.OnClickListener, RPILoginView {

    @BindView(R.id.tv_back)
    TextView tvBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.ib_refresh)
    ImageView ibRefresh;
    @BindView(R.id.ll_right)
    LinearLayout llRight;
    //    @BindView(R.id.rt_title)
//    RelativeLayout rtTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_main_station_name)
    TextView tvMainStationName;
    @BindView(R.id.ll_login)
    LinearLayout llLogin;
    @BindView(R.id.fl_container)
    FrameLayout flContainer;
    @BindView(R.id.iv_login_photograph)
    ImageView ivLoginPhotograph;
    @BindView(R.id.rl_login2)
    RelativeLayout rlLogin2;
    @BindView(R.id.playButton)
    ButtonBarLayout playButton;
    @BindView(R.id.edit_entry)
    EditText editEntry;
    @BindView(R.id.ll_login_hand)
    LinearLayout llLoginHand;
    @BindView(R.id.btn_login)
    Button btnLogin;
    private HandlerThread mCameraRenderThread;
    private Handler mCameraRenderHandler;
    public static final int CAMERA_FLAG_RESULT = 1000;
    private String photoPath;
    private RPPerson userBean;
    /**
     * 是否是管理员 true表示是
     */
//    private RPProgressHUD progressHUD;
//    private RPLoginPresenterImpl loginPresent;
    private int loginType;//登陸類型

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        if (intent != null) {
            loginType = intent.getIntExtra("loginType", 0);
        }
        initTitle();
        initViews();
    }

    @Override
    protected void creatPresent() {
        currentPresent = new RPLoginPresenterImpl(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }


    private void initTitle() {
//        tvTitle.setText(getString(R.string.st_main_login));
    }

    private void initViews() {
//        loginPresent = new RPLoginPresenterImpl(this);
        if (loginType == 0) {
            tvTitle.setText("扫码登录");
            llLogin.setVisibility(View.VISIBLE);
            llLoginHand.setVisibility(View.GONE);
            tvMainStationName.setText(getString(R.string.st_main_login_hint));
        } else if (loginType == 1) {
            tvTitle.setText("手动登录");
            llLogin.setVisibility(View.GONE);
            llLoginHand.setVisibility(View.VISIBLE);
        } else if (loginType == 2) {
            tvTitle.setText("扫码退出");
            llLogin.setVisibility(View.VISIBLE);
            llLoginHand.setVisibility(View.GONE);
            tvMainStationName.setText(getString(R.string.st_main_quit_hint));
        }
    }


    @OnClick({R.id.tv_back, R.id.ll_login, R.id.iv_login_photograph, R.id.btn_login, R.id.tv_title})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.tv_back:
                finish();
                break;
            case R.id.ll_login:
//                // 模拟扫描了条形码
//                loginPresent.login("");//wh
//                preparedCamere();
                break;
            case R.id.iv_login_photograph:
                break;
            case R.id.btn_login:
                if (!TextUtils.isEmpty(editEntry.getText().toString().trim())) {
                    requestLogin(editEntry.getText().toString().trim());
                } else {
                    ToastUtil.showCenterShort("工号不能为空", true);
                }

//                }
                break;
            case R.id.tv_title:
                break;
        }
    }


    @Override
//    @VerifyActivityIsFinsh
    public void loginSuccess(RPPerson userBean) {
        this.userBean = userBean;
        uploadResult(userBean);
        //不拍照
    }

    @Override
    public void loginError(String message) {
        
    }


    @Override
    public void uploadResult(RPPerson person) {
        if (person != null) {
            ToastUtil.showInfoCenterShort(person.getName() + "登录成功");
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putInt("type", 1);
            bundle.putSerializable("person", person);
            intent.putExtras(bundle);
            setResult(0, intent);
            finish();
        }
    }

    @Override
    public void quitResult(RPPerson person) {
        if (person != null) {
            ToastUtil.showInfoCenterShort(person.getName() + "退出成功");
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putInt("type", 2);
            bundle.putSerializable("person", person);
            intent.putExtras(bundle);
            setResult(0, intent);
            finish();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    protected void onPause() {
        super.onPause();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
//        mCameraRenderThread.quit();
    }


    private void savePhoto(final Bitmap b) {
        if (loginType == 0) {
            llLogin.setVisibility(View.VISIBLE);
        } else if (loginType == 1) {
            llLoginHand.setVisibility(View.VISIBLE);
        }

        rlLogin2.setVisibility(View.GONE);
        mCameraRenderHandler.sendEmptyMessage(0);
        photoPath = ((RPLoginPresenterImpl) currentPresent).savePhoto(userBean, b);
    }

    private void login() {
//        userBean.setPhoto(photoPath);
        int resultCode;
//        if (!isAdmin) {
//            resultCode = RPConstants.C_LOGIN_REQUEST_USER;
//            workstation.zjyk.line.ui.login.model.UserManager.getInstance().updateUserInfo(userBean);
//        } else {
//            resultCode = RPConstants.C_LOGIN_REQUEST_ADMIN;
//            workstation.zjyk.line.ui.login.model.UserManager.getInstance().updateAdminUserInfo(userBean);
//        }
//        setResult(resultCode);
//        finish();
    }

    @Override
    public void onScanResult(String scanResult) {
        super.onScanResult(scanResult);
        if (isFinishing()) {
            return;
        }
        if (loginType == 0) {
            requestLogin(scanResult);
        } else if (loginType == 1) {
            ToastUtil.showCenterShort("请手动输入工号", true);
        } else if (loginType == 2) {
            //登出
            requesQuit(scanResult);
        }
    }

    private void requestLogin(String scanResult) {
        ((RPLoginPresenterImpl) currentPresent).login(scanResult);
    }

    private void requesQuit(String scanResult) {
        ((RPLoginPresenterImpl) currentPresent).quit(scanResult);
    }
}
