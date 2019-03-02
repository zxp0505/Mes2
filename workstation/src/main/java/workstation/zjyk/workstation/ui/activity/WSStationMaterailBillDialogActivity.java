package workstation.zjyk.workstation.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.ethank.mylibrary.resourcelibrary.toast.ToastUtil;
import workstation.zjyk.workstation.R;
import workstation.zjyk.workstation.modle.bean.WSMaterial;
import workstation.zjyk.workstation.modle.bean.WSTray;
import workstation.zjyk.workstation.ui.adapter.WSAdapterReciverMaterail;
import workstation.zjyk.workstation.ui.adapter.WSAdapterStationMaterailBill;
import workstation.zjyk.workstation.ui.present.WSReciverMaterailPresent;
import workstation.zjyk.workstation.ui.views.WSRecvierMaterailView;

/**
 * 工位物料清单
 * Created by zjgz on 2017/11/6.
 */

public class WSStationMaterailBillDialogActivity extends WSBaseActivity  {

    @BindView(R.id.recycyleview)
    RecyclerView recycyleview;
    @BindView(R.id.tv_close)
    TextView tvClose;
    private WSAdapterStationMaterailBill mAdapterStationMaterailBill;
    private ArrayList<WSMaterial> materiels;

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
        currentPresent = new WSReciverMaterailPresent();
    }


    private void initVeiw() {
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            materiels = extras.getParcelableArrayList("data");
//            wrap = extras.getParcelable("data");
//            WSTray wsTray = wrap.getTray();
//            if (wsTray != null) {
//                tvTrayNumber.setText("托盘编号: " + wsTray.getOneDemensionCode());
//            }
//            materiels = wrap.getMaterialList();
        }
//
        mAdapterStationMaterailBill = new WSAdapterStationMaterailBill();
        recycyleview.setLayoutManager(new LinearLayoutManager(this));
        recycyleview.setAdapter(mAdapterStationMaterailBill);
        if (materiels != null) {
            mAdapterStationMaterailBill.setNewData(materiels);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_station_materail_bill;
    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }



    @Override
    public void onScanResult(String scanResult) {
        super.onScanResult(scanResult);
    }



    @OnClick(R.id.tv_close)
    public void onViewClicked() {
        finish();
    }
}
