package workstation.zjyk.com.scanapp.ui;

import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.ethank.mylibrary.resourcelibrary.intent.StartIntentUtils;
import cn.com.ethank.mylibrary.resourcelibrary.toast.ToastUtil;
import workstation.zjyk.com.scanapp.R;
import workstation.zjyk.com.scanapp.modle.bean.ScanPersonInfo;
import workstation.zjyk.com.scanapp.ui.present.ScanLoginPresent;
import workstation.zjyk.com.scanapp.ui.views.ScanLoginView;
import workstation.zjyk.com.scanapp.util.ScanUserManager;

public class ScanLoginActivity extends ScanBaseActivity<ScanLoginPresent> implements ScanLoginView {
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
        return R.layout.activit_login;
    }

    @Override
    protected View getLoadingTargetView() {
        return mFlContent;
    }

    @Override
    public void initOnCreate() {
        ivBack.setVisibility(View.GONE);
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
        params.put("account", str_username);
        params.put("passWord", str_password);
        currentPresent.requestLogin(params);
    }


    @Override
    public void loginResult(ScanPersonInfo personInfo) {
        if (personInfo != null) {
            ToastUtil.showInfoCenterShort("登录成功");
            ScanUserManager.getInstance().setCurrentPerson(personInfo);
            goScan();
        }
    }

    private void goScan() {
        StartIntentUtils.startIntentUtils(this, ScanCode2Activity.class);
        finish();
    }
}
