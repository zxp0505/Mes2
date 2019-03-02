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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.ethank.mylibrary.resourcelibrary.common_util.JsonUtil;
import cn.com.ethank.mylibrary.resourcelibrary.common_util.log.LoggerUtil;
import cn.com.ethank.mylibrary.resourcelibrary.toast.ToastUtil;
import workstation.zjyk.workstation.R;
import workstation.zjyk.workstation.modle.bean.WSMaterial;
import workstation.zjyk.workstation.modle.bean.WSPerson;
import workstation.zjyk.workstation.modle.bean.WSTray;
import workstation.zjyk.workstation.modle.bean.WSTrayLoadInfo;
import workstation.zjyk.workstation.modle.bean.WSWorkStationMaterielVO;
import workstation.zjyk.workstation.modle.bean.WSWorkStationReceiveVO;
import workstation.zjyk.workstation.modle.bean.WSWorkStationVo;
import workstation.zjyk.workstation.modle.bean.enumpackage.WSTrayBindTypeEnum;
import workstation.zjyk.workstation.ui.adapter.WSAdapterReciverMaterail;
import workstation.zjyk.workstation.ui.manager.WSUserManager;
import workstation.zjyk.workstation.ui.present.WSReciverMaterailPresent;
import workstation.zjyk.workstation.ui.views.WSRecvierMaterailView;
import workstation.zjyk.workstation.util.dialog.WSDialogUtils;
import workstation.zjyk.workstation.util.dialog.callback.WSDialogCallBackTwo;

/**
 * 接收物料
 * Created by zjgz on 2017/11/6.
 */

public class WSReciverMaterailDialogActivity extends WSBaseActivity<WSReciverMaterailPresent> implements WSRecvierMaterailView {
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
    @BindView(R.id.tv_tray_from)
    TextView tvTrayFrom;
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
    private List<WSMaterial> materiels;
    private WSAdapterReciverMaterail mAdapterReciverMaterail;
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
        currentPresent = new WSReciverMaterailPresent();
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
            if (!TextUtils.isEmpty(wrap.getLineWorkStationName())) {
                tvTrayFrom.setText("托盘来源: " + wrap.getLineWorkStationName());//
            } else {
                if (wrap.getBindType() == WSTrayBindTypeEnum.QUALITY_RETURN_MATERIEL) {
                    tvTrayFrom.setText("托盘来源: 质量");
                }
            }
            tvProductNumber.setText("产品型号: " + wrap.getProductModelTypeName());
            tvTrayContent.setText("托盘承载内容: " + currentPresent.getType(wrap));
            tvOrderProductNumber.setText("订单生产编号: " + wrap.getSerialNumber());
            materiels = wrap.getMaterialList();
        }

        mAdapterReciverMaterail = new WSAdapterReciverMaterail(35);
        recycyleview.setLayoutManager(new LinearLayoutManager(this));
        recycyleview.setAdapter(mAdapterReciverMaterail);
        if (materiels != null) {
            mAdapterReciverMaterail.setNewData(materiels);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_reciver_materail;
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
//                currentPresent.reciverMaterail(params);
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

    private String bindTrayCode = "";//接收后绑定的托盘

    @Override
    public void reciverResult(boolean result, String bindTrayCode) {
        if (result) {
            if (!TextUtils.isEmpty(bindTrayCode)) {
                this.bindTrayCode = bindTrayCode;
            }
            ToastUtil.showInfoCenterShort("收取物料成功");
            quitRefresh(true);
        }
    }

    @Override
    public void closeCurrent() {
        quitRefresh(false);
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
    public void checkTrayResult(boolean result, String bindTrayCode) {
        if (result) {
//            WSWorkStationReceiveVO wsWorkStationReceiveVO = setReciverData(str);
//            String detail = JsonUtil.toJson(wsWorkStationReceiveVO);
            String detail = currentPresent.setReciverData(wrap, bindTrayCode);
            if (!TextUtils.isEmpty(detail)) {
                Map<String, String> params = new HashMap<>();
                params.put("workStationReceiveInfo", detail);
                currentPresent.reciverMaterail(params);
            }

        }
//        if (result) {
//
//            WSDialogUtils.showReciverBindTrayDialog(this, "请扫描或手动输入接收托盘码", str, new WSDialogCallBackTwo() {
//                @Override
//                public void OnPositiveClick(Object obj) {
//                    String str = (String) obj;
//                    if (TextUtils.isEmpty(str)) {
////                        currentPresent.checkTrayBindResult();
//                    }
//                }
//
//                @Override
//                public void OnNegativeClick() {
//
//                }
//            });
//        }
    }

    @Override
    public Context getContextByView() {
        return this;
    }

//    private WSWorkStationReceiveVO setReciverData(String bindTrayCode) {
//        WSWorkStationReceiveVO wsWorkStationReceiveVO = new WSWorkStationReceiveVO();
//        List<WSWorkStationMaterielVO> materils = new ArrayList<>();
//        List<WSMaterial> data = mAdapterReciverMaterail.getData();
//        for (int i = 0; i < data.size(); i++) {
//            WSMaterial wsMaterial = data.get(i);
//            WSWorkStationMaterielVO wsWorkStationMaterielVO = new WSWorkStationMaterielVO();
//            wsWorkStationMaterielVO.setMaterielId(wsMaterial.getId());
//            wsWorkStationMaterielVO.setCount(wsMaterial.getCount());
//            materils.add(wsWorkStationMaterielVO);
//        }
//        wsWorkStationReceiveVO.setMaterielList(materils);//物料集合
//        wsWorkStationReceiveVO.setCount(0);//返工/维修辅助/维修返还数量
//        WSUserManager userManager = WSUserManager.getInstance();
//        WSPerson currentPerson = userManager.getCurrentPerson();
//        if (currentPerson != null) {
//            wsWorkStationReceiveVO.setPersonId(currentPerson.getPersonId());//收料人员ID
//        }
//        wsWorkStationReceiveVO.setReceiveType(wrap.getBindType());//托盘绑定类型
//        wsWorkStationReceiveVO.setTrayCode(oneDemensionCode);//接收托盘的码
//        WSWorkStationVo workStationVo = userManager.getWorkStationVo();
//        if (workStationVo != null) {
//            wsWorkStationReceiveVO.setWorkStationId(workStationVo.getWorkStationId());//工位id
//        }
//        wsWorkStationReceiveVO.setWorkStationTaskId(wrap.getWorkStationTaskId());//收料任务id
//        wsWorkStationReceiveVO.setWorkStationTrayCode(bindTrayCode);//绑定码
//        return wsWorkStationReceiveVO;
//    }
}
