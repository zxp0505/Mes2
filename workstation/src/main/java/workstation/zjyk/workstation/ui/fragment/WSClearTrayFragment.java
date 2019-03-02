package workstation.zjyk.workstation.ui.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.com.ethank.mylibrary.resourcelibrary.toast.ToastUtil;
import me.yokeyword.fragmentation.ISupportFragment;
import workstation.zjyk.workstation.R;
import workstation.zjyk.workstation.ui.WSMainActivity;
import workstation.zjyk.workstation.ui.present.WSClearTrayPresent;
import workstation.zjyk.workstation.ui.views.WSClearTrayView;

/**
 * 清盘
 * Created by zjgz on 2017/12/12.
 */

public class WSClearTrayFragment extends WSBaseFragment<WSClearTrayPresent> implements WSClearTrayView {

    Unbinder unbinder;
    @BindView(R.id.tv_tray_number)
    TextView tvTrayNumber;
    @BindView(R.id.tv_clear)
    TextView tvClear;
    @BindView(R.id.iv_back_one)
    ImageView ivBackOne;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_hand_barcode)
    TextView tvHandBarcode;
    @BindView(R.id.rl_title_one)
    RelativeLayout rlTitleOne;
    @BindView(R.id.rl_title_two)
    RelativeLayout rlTitleTwo;
    @BindView(R.id.rl_title_three)
    RelativeLayout rlTitleThree;
    @BindView(R.id.tv_right)
    TextView tvRight;
    private Unbinder bind;

    public static ISupportFragment newInstance() {
        WSClearTrayFragment wsExceptionFragment = new WSClearTrayFragment();
        return wsExceptionFragment;
    }

    @Override
    protected void creatPresent() {
        currentPresent = new WSClearTrayPresent();
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        bind = ButterKnife.bind(this, view);
        tvTrayNumber.setText(getString(R.string.text_unbind));
        tvTitle.setVisibility(View.VISIBLE);
        rlTitleOne.setVisibility(View.VISIBLE);
        rlTitleTwo.setVisibility(View.GONE);
        rlTitleThree.setVisibility(View.GONE);
        tvRight.setVisibility(View.GONE);
        tvTitle.setText("请扫描需要解绑的托盘");
        ivBackOne.setVisibility(View.GONE);
        tvHandBarcode.setOnClickListener(this);

    }

    @Override
    public void initData() {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_clear_tray;
    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        bind.unbind();
    }

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        // TODO: inflate a fragment view
//        View rootView = super.onCreateView(inflater, container, savedInstanceState);
//        unbinder = ButterKnife.bind(this, rootView);
//        return rootView;
//    }

//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        unbinder.unbind();
//    }

    @Override
    public void setScanResult(String scanResult) {
        if (!TextUtils.isEmpty(scanResult)) {
            Map<String, String> params = new HashMap<>();
            params.put("code", scanResult);
            currentPresent.requesBindTray(params);
        }
    }

    @Override
    public void setFragmentBack() {

    }

    @Override
    public void setActivityLogoOrBack() {
        if (mActivity != null && mActivity instanceof WSMainActivity) {
            ((WSMainActivity) mActivity).setActivityLogoOrBack(0);
        }
    }

    private void clearTray() {
        Map<String, String> params = new HashMap<>();
        currentPresent.requesClearTray(params);
    }

    @Override
    public void bindTrayResult(String result) {
        if (!TextUtils.isEmpty(result)) {
            tvTrayNumber.setText(result);
            ToastUtil.showInfoCenterShort(getString(R.string.text_bind_sucess));
        } else {
            tvTrayNumber.setText(getString(R.string.text_unbind));
        }
    }

    @Override
    public void clearTrayResult(boolean result) {
        if (result) {
            ToastUtil.showInfoCenterShort("托盘解绑成功");
        }
    }

    @OnClick(R.id.tv_clear)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_clear:
                if (getString(R.string.text_unbind).equals(tvTrayNumber.getText().toString().trim())) {
                    ToastUtil.showInfoCenterShort("请先绑定托盘");
                } else {
                    clearTray();
                }
                break;
        }
    }
}
