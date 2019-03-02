package workstation.zjyk.workstation.ui.activity;

import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.ethank.mylibrary.resourcelibrary.common_util.AppUtils;
import cn.com.ethank.mylibrary.resourcelibrary.intent.StartIntentUtils;
import cn.com.ethank.mylibrary.resourcelibrary.toast.ToastUtil;
import cn.com.ethank.ui.common.dialog.LibraryDialogUtils;
import cn.com.ethank.ui.common.dialog.callback.ResourceDialogCallBackTwo;
import workstation.zjyk.workstation.R;
import workstation.zjyk.workstation.modle.bean.ScanPersonInfo;
import workstation.zjyk.workstation.modle.bean.WSPerson;
import workstation.zjyk.workstation.ui.manager.WSUserManager;
import workstation.zjyk.workstation.ui.present.WSInspectLoginPresent;
import workstation.zjyk.workstation.ui.views.WSInspectLoginView;
import workstation.zjyk.workstation.util.WSURLBuilder;

public class WSLoginHzActivity extends WSBaseActivity<WSInspectLoginPresent> implements WSInspectLoginView {
    @BindView(R.id.et_username)
    EditText etUsername;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.bt_login)
    AppCompatButton btLogin;

    @Override
    protected void creatPresent() {
        currentPresent = new WSInspectLoginPresent();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activit_inspect_login;
    }

    @Override
    protected View getLoadingTargetView() {
        return mFlContent;
    }

    @Override
    public void initOnCreate() {
        testServer();
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
            WSPerson person = new WSPerson();
            person.setName(personInfo.getUserName());
            person.setUserPic(personInfo.getUserpic());
            person.setPersonId(personInfo.getUserId());
            WSUserManager.getInstance().setCurrentUser(person);
            goScan();
        }
    }

    private void testServer() {
        currentPresent.testServer();
    }

    @Override
    public void showTestServerResult(boolean result) {
        if (result) {
        } else {
            showModifiHostUrlDialog();
        }
    }

    private void showModifiHostUrlDialog() {
        LibraryDialogUtils.showSetDomainDialog(this, WSURLBuilder.getDomain(), "请检查服务器地址", new ResourceDialogCallBackTwo() {
            @Override
            public void OnPositiveClick(Object obj) {
                //重启
                if (obj != null && obj instanceof String) {
                    WSURLBuilder.setDomain((String) obj);
                }
                AppUtils.restartApp(WSSplashActivity.class);
                finish();
//                gotoSplash();
            }

            @Override
            public void OnNegativeClick() {
                finish();
            }
        });
        return;
    }


    private void goScan() {
        StartIntentUtils.startIntentUtils(this, WSInspectActivity.class);
        finish();
    }

}
