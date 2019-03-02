package workstation.zjyk.line.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
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

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.ethank.mylibrary.resourcelibrary.toast.ToastUtil;
import workstation.zjyk.line.R;
import workstation.zjyk.line.modle.bean.Person;
import workstation.zjyk.line.ui.present.LoginPresenterImpl;
import workstation.zjyk.line.ui.views.ILoginView;
import workstation.zjyk.line.util.DialogCallBackTwo;
import workstation.zjyk.line.util.dialog.DialogUtils;
import workstation.zjyk.line.util.dialog.ProgressHUD;
import workstation.zjyk.line.view.CameraPreview;


/**
 * Created by hanxue on 17/6/21.
 */

public class ActivityLogin extends BasePermissionActivity implements View.OnClickListener, ILoginView {

    @BindView(R.id.tv_back)
    TextView tvBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    //    @BindView(R.id.ib_refresh)
//    ImageView ibRefresh;
//    @BindView(R.id.ll_right)
//    LinearLayout llRight;
    //    @BindView(R.id.rt_title)
//    RelativeLayout rtTitle;
//    @BindView(R.id.toolbar)
//    Toolbar toolbar;
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
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_hand_barcode)
    TextView tvHandBarcode;
    private CameraPreview cameraPreview;
    private HandlerThread mCameraRenderThread;
    private Handler mCameraRenderHandler;
    public static final int CAMERA_FLAG_RESULT = 1000;
    private String photoPath;
    private Person userBean;
    /**
     * 是否是管理员 true表示是
     */
    private ProgressHUD progressHUD;
    private LoginPresenterImpl loginPresent;
    private int loginType;//登陸類型

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_login);
        Intent intent = getIntent();
        if (intent != null) {
            loginType = intent.getIntExtra("loginType", 0);
        }
        checkSelfPermission();
        initTitle();
        initViews();
//        initCameraHander();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    private void checkSelfPermission() {
        if (!hasPersimmions(PERMISSIONS_CAMERA)) {
            requestNewPersimmions("需要拍照权限", 0, PERMISSIONS_CAMERA);
        }
        if (!hasPersimmions(PERMISSIONS_STORAGE)) {
            requestNewPersimmions("需要读写卡权限", 1, PERMISSIONS_STORAGE);
        }
    }


    private void initTitle() {
//        tvTitle.setText(getString(R.string.st_main_login));
    }

    private void initViews() {
        loginPresent = new LoginPresenterImpl(this);
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

    private void initCameraHander() {
        mCameraRenderThread = new HandlerThread("cameraRenderThread");
        mCameraRenderThread.start();
        mCameraRenderHandler = new Handler(mCameraRenderThread.getLooper()) {

            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 0: {
                        if (cameraPreview != null) {
                            cameraPreview.onPause();//释放相机
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    flContainer.removeAllViews();
                                }
                            });
                        }
                    }
                    break;
                    case 1: {
                        cameraPreview = new CameraPreview(ActivityLogin.this, new CameraPreview.ICameraPreviewListener() {
                            @Override
                            public void cameraCreateError() {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (isFinishing()) {
                                            return;
                                        }
                                        DialogUtils.showTipDialogOneButtonNoTitle(ActivityLogin.this, "创建相机失败,请检查是否开启相关权限", getString(R.string.st_confirm), null);
                                    }
                                });
                            }

                            @Override
                            public void pictureCallback(Bitmap bitmap) {
                                if (isFinishing()) {
                                    return;
                                }
                                // 保存到本地
                                savePhoto(bitmap);
                            }
                        }, true);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                flContainer.addView(cameraPreview);
                            }
                        });
                    }
                    break;
                }
            }
        };
    }

    @OnClick({R.id.tv_back, R.id.ll_login, R.id.iv_login_photograph, R.id.btn_login, R.id.tv_hand_barcode})
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
//                if (TextUtils.isEmpty(photoPath)) {
                if (cameraPreview != null) {
                    showUploadPhotoProgress();
                    cameraPreview.doTakePicture();// 拍照
                }
                break;
            case R.id.btn_login:
                if (!TextUtils.isEmpty(editEntry.getText().toString().trim())) {
                    requestLogin(editEntry.getText().toString().trim());
                } else {
                    ToastUtil.showCenterShort("工号不能为空", true);
                }

//                }
                break;
            case R.id.tv_hand_barcode:
                showEntryBarcode();
                break;
        }
    }

    private void showEntryBarcode() {
        DialogUtils.showEntryBarcodeDialog(this, getString(R.string.text_hand_entry_barcode), new DialogCallBackTwo() {
            @Override
            public void OnPositiveClick(Object obj) {
                if (obj != null) {
                    String barcode = (String) obj;
                    onScanResult(barcode.trim());
                }

            }

            @Override
            public void OnNegativeClick() {

            }
        });
    }


    @Override
//    @VerifyActivityIsFinsh
    public void loginSuccess(Person userBean) {
        this.userBean = userBean;
        uploadResult(userBean);
        //不拍照
//        preparedCamere();
//        llLogin.setVisibility(View.GONE);
//        llLoginHand.setVisibility(View.GONE);
//        rlLogin2.setVisibility(View.VISIBLE);
    }

    @Override
//    @VerifyActivityIsFinsh
    public void loginError(String message) {
//        DialogUtils.showTipDialogOneButtonNoTitle(ActivityLogin.this, message, getString(R.string.st_confirm), null);
    }


    @Override
//    @VerifyActivityIsFinsh
    public void savePhotoSuccess() {
        login();
    }

    @Override
//    @VerifyActivityIsFinsh
    public void savePhotoError(String message) {
        DialogUtils.showTipDialogOneButtonNoTitle(ActivityLogin.this, message, getString(R.string.st_confirm), null);
        // 失败之后重新打开相机
        photoPath = null;
        if (cameraPreview != null) {
            cameraPreview.startPreview();
        }
    }

    @Override
    public boolean checkActivityIsFinishing() {
        return isFinishing();
    }

    @Override
//    @VerifyActivityIsFinsh
    public void showLoginProgress() {
//        progressHUD = ProgressHUD.show(ActivityLogin.this, "", true, null);
    }

    @Override
//    @VerifyActivityIsFinsh
    public void hideLoginProgress() {
//        if (progressHUD != null) {
//            progressHUD.dismiss();
//        }
    }

    @Override
//    @VerifyActivityIsFinsh
    public void showUploadPhotoProgress() {
//        progressHUD = ProgressHUD.show(ActivityLogin.this, "", true, null);
    }

    @Override
//    @VerifyActivityIsFinsh
    public void hideUploadPhotoProgress() {
//        if (progressHUD != null) {
//            progressHUD.dismiss();
//        }
    }

    @Override
    public void uploadResult(Person person) {
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
    public void quitResult(Person person) {
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
//        preparedCamere();
    }

    private void preparedCamere() {
        if (hasPersimmions(PERMISSIONS_CAMERA) && hasPersimmions(PERMISSIONS_CAMERA)) {
//            if (userBean != null) {
            mCameraRenderHandler.sendEmptyMessage(1);
//            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (userBean != null) {
//            mCameraRenderHandler.sendEmptyMessage(0);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
//        mCameraRenderThread.quit();
    }


    @Override
    public void permissionGranted(int requestCode, List<String> nowperms) {
        ToastUtil.showCenterShort("拍照权限同意", true);
        preparedCamere();
    }

    @Override
    public void permissionsDenied(int requestCode, List<String> nowperms) {
        if (requestCode == 0) {
            ToastUtil.showCenterShort("拍照权限拒绝", true);
            showDialogToGetPermission("请在-应用设置-权限中，开启相机和存储权限！");
        } else if (requestCode == 1) {
            ToastUtil.showCenterShort("拍照权限拒绝", true);
            showDialogToGetPermission("请在-应用设置-权限中，开启相机和存储权限！");

        }
    }


    private void savePhoto(final Bitmap b) {
        if (loginType == 0) {
            llLogin.setVisibility(View.VISIBLE);
        } else if (loginType == 1) {
            llLoginHand.setVisibility(View.VISIBLE);
        }

        rlLogin2.setVisibility(View.GONE);
        mCameraRenderHandler.sendEmptyMessage(0);
        photoPath = loginPresent.savePhoto(userBean, b);
    }

    private void login() {
//        userBean.setPhoto(photoPath);
        int resultCode;
//        if (!isAdmin) {
//            resultCode = Constants.C_LOGIN_REQUEST_USER;
//            workstation.zjyk.line.ui.login.model.UserManager.getInstance().updateUserInfo(userBean);
//        } else {
//            resultCode = Constants.C_LOGIN_REQUEST_ADMIN;
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
        loginPresent.login(scanResult);
    }

    private void requesQuit(String scanResult) {
        loginPresent.quit(scanResult);
    }
}
