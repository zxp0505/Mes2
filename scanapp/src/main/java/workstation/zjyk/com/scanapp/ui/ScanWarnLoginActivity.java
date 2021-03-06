package workstation.zjyk.com.scanapp.ui;

import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.ethank.mylibrary.resourcelibrary.common_util.SharePreferenceKeyUtil;
import cn.com.ethank.mylibrary.resourcelibrary.intent.StartIntentUtils;
import cn.com.ethank.mylibrary.resourcelibrary.shareutils.SharedPreferencesUitl;
import cn.com.ethank.mylibrary.resourcelibrary.toast.ToastUtil;
import workstation.zjyk.com.scanapp.R;
import workstation.zjyk.com.scanapp.modle.bean.ScanPersonInfo;
import workstation.zjyk.com.scanapp.ui.present.ScanLoginPresent;
import workstation.zjyk.com.scanapp.ui.views.ScanLoginView;
import workstation.zjyk.com.scanapp.util.ScanUserManager;

/**
 * 警告登录
 */
public class ScanWarnLoginActivity extends ScanBaseActivity<ScanLoginPresent> implements ScanLoginView {
    @BindView(R.id.et_username)
    EditText etUsername;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.bt_login)
    AppCompatButton btLogin;

    @Override
    protected void creatPresent() {
        currentPresent = new ScanLoginPresent();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activit_warn_login;
    }

    @Override
    protected View getLoadingTargetView() {
        return mFlContent;
    }

    @Override
    public void initOnCreate() {
        ivBack.setVisibility(View.GONE);
        String username = SharedPreferencesUitl.getStringData(SharePreferenceKeyUtil.USERNAME);
        String psw = SharedPreferencesUitl.getStringData(SharePreferenceKeyUtil.PSW);
        if (!TextUtils.isEmpty(username)) {
            etUsername.setText(username);
            etPassword.setText(psw);
        }
    }


    @OnClick({R.id.bt_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_login:
                login();
                break;
        }
    }

    private void login() {
        String str_username = etUsername.getText().toString().trim();
        String str_password = etPassword.getText().toString().trim();
        if (TextUtils.isEmpty(str_username)) {
            ToastUtil.showInfoCenterShort("用户名不能为空");
            return;
        }
        if (TextUtils.isEmpty(str_password)) {
            ToastUtil.showInfoCenterShort("密码不能为空");
            return;
        }
        Map<String, String> params = new HashMap<>();
        params.put("username", str_username);
        params.put("password", str_password);
        currentPresent.requestWarnLogin(params);
    }


    @Override
    public void loginResult(ScanPersonInfo personInfo) {

    }

    @Override
    public void loginWarnResult(String id) {
        if (!TextUtils.isEmpty(id)) {
            ToastUtil.showInfoCenterShort("登录成功");
            ScanUserManager.getInstance().setWarnPersonId(id);
            ScanUserManager.getInstance().setWarnUserName(etUsername.getText().toString().trim());
            SharedPreferencesUitl.saveStringData(SharePreferenceKeyUtil.USERNAME, etUsername.getText().toString().trim());
            SharedPreferencesUitl.saveStringData(SharePreferenceKeyUtil.PSW, etPassword.getText().toString().trim());
            go();
        } else {
            ToastUtil.showInfoCenterShort("登录失败");
        }
    }

    private void go() {
        StartIntentUtils.startIntentUtils(this, ScanWaitWarnActivity.class);
        finish();
    }
}
