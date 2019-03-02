package workstation.zjyk.workstation.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.ethank.mylibrary.resourcelibrary.toast.ToastUtil;
import workstation.zjyk.workstation.R;
import workstation.zjyk.workstation.modle.bean.WSTray;
import workstation.zjyk.workstation.modle.bean.WSTrayLoadInfo;
import workstation.zjyk.workstation.modle.bean.WSWip;
import workstation.zjyk.workstation.ui.adapter.WSAdapterReciverMaking;
import workstation.zjyk.workstation.ui.present.WSReciverMakingPresent;
import workstation.zjyk.workstation.ui.views.WSRecvierMakingView;

/**
 * 接收在制品
 * Created by zjgz on 2017/11/6.
 */

public class WSReciverMakingDialogActivity extends WSBaseActivity<WSReciverMakingPresent> implements WSRecvierMakingView {
    @BindView(R.id.tv_tray_number)
    TextView tvTrayNumber;
    @BindView(R.id.tv_order_product_number)
    TextView tvOrderProductNumber;
    @BindView(R.id.view_line_one)
    View viewLineOne;
    @BindView(R.id.tv_materail_detail)
    TextView tvMaterailDetail;
    @BindView(R.id.recycyleview)
    RecyclerView recycyleview;
    @BindView(R.id.tv_yes)
    TextView tvYes;
    @BindView(R.id.tv_cancle)
    TextView tvCancle;
    @BindView(R.id.tv_product_number)
    TextView tvProductNumber;
    @BindView(R.id.tv_tray_content)
    TextView tvTrayContent;
    @BindView(R.id.view_line_two)
    View viewLineTwo;
    @BindView(R.id.iv_close)
    ImageView ivClose;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    private WSTrayLoadInfo wrap;
    private List<WSWip> materiels;
    private WSAdapterReciverMaking mAdapterReciverMaterail;
    private String oneDemensionCode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initOnCreate() {
        initVeiw();
    }

    @Override
    protected void creatPresent() {
        currentPresent = new WSReciverMakingPresent();
    }


    private void initVeiw() {
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            wrap = extras.getParcelable("data");
            WSTray wsTray = wrap.getTray();
            if (wsTray != null) {
                oneDemensionCode = wsTray.getOneDemensionCode();
                tvTrayNumber.setText("托盘编号: " + oneDemensionCode);
            }
//            tvOrderProductNumber.setText("订单生产编号:" + wrap.getOrderId());
            tvTitle.setText("您扫描到" + currentPresent.getReciverType(wrap) + "的托盘,托盘信息如下:");
            tvProductNumber.setText("产品型号: " + wrap.getProductModelTypeName());
            tvTrayContent.setText("托盘承载内容: " + currentPresent.getType(wrap));
            tvOrderProductNumber.setText("订单生产编号: " + wrap.getSerialNumber());
            materiels = wrap.getWipList();
        }

        mAdapterReciverMaterail = new WSAdapterReciverMaking();
        recycyleview.setLayoutManager(new LinearLayoutManager(this));
        recycyleview.setAdapter(mAdapterReciverMaterail);
        if (materiels != null) {
            mAdapterReciverMaterail.setNewData(materiels);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_reciver_making;
    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @OnClick({R.id.tv_yes, R.id.tv_cancle})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_yes:
                Map<String, String> params = new HashMap<>();
                params.put("taskId", wrap.getWorkStationTaskId());
                params.put("code", oneDemensionCode);
                currentPresent.reciverCheckTray(params);
                break;
            case R.id.tv_cancle:
                finish();
                break;
        }
    }


    @Override
    public void onScanResult(String scanResult) {
        super.onScanResult(scanResult);
    }

    private String bindTrayCode = "";

    @Override
    public void reciverResult(boolean result, String bindTrayCode) {
        if (result) {
            if (!TextUtils.isEmpty(bindTrayCode)) {
                this.bindTrayCode = bindTrayCode;
            }
            ToastUtil.showInfoCenterShort("收取在制品成功");
            quitRefresh(true);
        }

    }

    private void quitRefresh(boolean isToDetail) {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putBoolean("isRefresh", true);
        bundle.putBoolean("isToDetail", isToDetail);
        bundle.putString("trayCode", bindTrayCode);//托盘码
        bundle.putString("taskId", wrap.getWorkStationTaskId());
        intent.putExtras(bundle);
        setResult(0, intent);
        finish();
    }


    @Override
    public void closeCurrent() {
        quitRefresh(false);
    }

    @Override
    public void checkTrayResult(boolean result, String bindTrayCode) {
        if (result) {
            String detail = currentPresent.setReciverData(wrap, bindTrayCode);
            if (!TextUtils.isEmpty(detail)) {
                Map<String, String> params = new HashMap<>();
                params.put("workStationReceiveInfo", detail);
                currentPresent.reciverMaking(params);
            }

        }
    }

    @Override
    public Context getContextByView() {
        return this;
    }
}
