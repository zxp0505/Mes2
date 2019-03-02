package workstation.zjyk.workstation.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.com.ethank.mylibrary.resourcelibrary.common_util.JsonUtil;
import cn.com.ethank.mylibrary.resourcelibrary.server.MyServer;
import cn.com.ethank.mylibrary.resourcelibrary.toast.ToastUtil;
import workstation.zjyk.workstation.R;
import workstation.zjyk.workstation.modle.bean.WSDeliverHistory;
import workstation.zjyk.workstation.modle.bean.WSTaskProductCheckTray;
import workstation.zjyk.workstation.modle.bean.WSWipHistory;
import workstation.zjyk.workstation.modle.bean.WSWorkStationCheckVO;
import workstation.zjyk.workstation.modle.bean.WSWorkStationOutVO;
import workstation.zjyk.workstation.modle.bean.enumpackage.WSTaskTypeEnum;
import workstation.zjyk.workstation.ui.WSMainActivity;
import workstation.zjyk.workstation.ui.adapter.WSAdapterMakingReport;
import workstation.zjyk.workstation.ui.manager.WSUserManager;
import workstation.zjyk.workstation.ui.present.WSMakingOutPresent;
import workstation.zjyk.workstation.ui.views.WSMakingOutView;
import workstation.zjyk.workstation.util.dialog.WSDialogUtils;
import workstation.zjyk.workstation.util.dialog.WSOutPutInsoectionDialog;
import workstation.zjyk.workstation.util.dialog.callback.WSDialogCallBackThree;
import workstation.zjyk.workstation.util.dialog.callback.WSDialogCallBackTwo;

/**
 * 在制品输出
 * Created by zjgz on 2017/12/12.
 */

public class WSMakingOutputFragment extends WSBaseFragment<WSMakingOutPresent> implements WSMakingOutView {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rl_title_one)
    RelativeLayout rlTitleOne;
    @BindView(R.id.tv_product_number)
    TextView tvProductNumber;
    @BindView(R.id.tv_model)
    TextView tvModel;
    @BindView(R.id.tv_produce)
    TextView tvProduce;
    @BindView(R.id.tv_cancle)
    TextView tvCancle;
    @BindView(R.id.rl_title_two)
    RelativeLayout rlTitleTwo;
    @BindView(R.id.iv_reduce)
    ImageView ivReduce;
    @BindView(R.id.tv_output_count)
    TextView tvOutputCount;
    @BindView(R.id.iv_add)
    ImageView ivAdd;
    @BindView(R.id.ll_count)
    LinearLayout llCount;
    @BindView(R.id.tv_tray_number)
    TextView tvTrayNumber;
    Unbinder unbinder;
    @BindView(R.id.recycleview)
    RecyclerView recycleview;
    @BindView(R.id.tv_product_modle)
    TextView tvProductModle;
    @BindView(R.id.tv_delivery_count)
    TextView tvDeliveryCount;
    @BindView(R.id.iv_back_two)
    ImageView ivBackTwo;
    @BindView(R.id.tv_detail)
    TextView tvDetail;
    @BindView(R.id.tv_hand_barcode_two)
    TextView tvHandBarcodeTwo;
    @BindView(R.id.tv_inspection)
    TextView tvInspection;
    Unbinder unbinder1;
    private Unbinder bind;
    int outputCount = 0;
    private WSAdapterMakingReport makingReportAdapterMakingReport;
    private String taskId;
    private int predictCount;
    private String checkTag;
    private WSOutPutInsoectionDialog mOutPutInsoectionDialog;
    private WSTaskTypeEnum wsTaskTypeEnum;

    public static WSMakingOutputFragment newInstance(Bundle bundle) {
        WSMakingOutputFragment wsMakingOutputFragment = new WSMakingOutputFragment();
        wsMakingOutputFragment.setArguments(bundle);
        return wsMakingOutputFragment;
    }

    @Override
    protected void creatPresent() {
        currentPresent = new WSMakingOutPresent();
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        bind = ButterKnife.bind(this, view);
        rlTitleOne.setVisibility(View.GONE);
        rlTitleTwo.setVisibility(View.VISIBLE);
        tvModel.setVisibility(View.GONE);
        tvProduce.setVisibility(View.GONE);
        tvProductNumber.setText("在制品输出");
        ivBackTwo.setOnClickListener(this);
        tvDetail.setVisibility(View.GONE);
        tvHandBarcodeTwo.setOnClickListener(this);

        tvTrayNumber.setText(getString(R.string.text_unbind));
        Bundle arguments = getArguments();
        if (arguments != null) {
            taskId = arguments.getString("taskId");
            predictCount = arguments.getInt("predictCount");
            checkTag = arguments.getString("checkTag");
            wsTaskTypeEnum = (WSTaskTypeEnum) arguments.getSerializable("taskType");
            outputCount = predictCount;//默认最大
        }
        if (!TextUtils.isEmpty(checkTag) && "yes".equals(checkTag)) {
            tvInspection.setVisibility(View.GONE);
        } else {
            tvInspection.setVisibility(View.GONE);
        }
        tvOutputCount.setText(outputCount + "");
//        tvProductModle.setText("产品型号: 123");
//        tvDeliveryCount.setText("已投递总数: 18");
        recycleview.setLayoutManager(new LinearLayoutManager(getActivity()));
        makingReportAdapterMakingReport = new WSAdapterMakingReport();
        recycleview.setAdapter(makingReportAdapterMakingReport);
        makingReportAdapterMakingReport.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                WSDeliverHistory deliverHistory = (WSDeliverHistory) adapter.getData().get(position);
                switch (view.getId()) {
                    case R.id.tv_print:
                        requestPrintDelivery(deliverHistory.getRecordId(), false);
                        break;
                }
            }
        });
    }

    @Override
    public void initData() {
        requestDeliveryRecord(true);
    }

    @Override
    protected void synCurrentFragment(int refreshType) {
        super.synCurrentFragment(refreshType);
        if (getRefreshaType() == refreshType) {
            requestDeliveryRecord(false);
        }
    }

    private int getRefreshaType() {
        return MyServer.ACTION_TASK_OUTPUT;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_making_output;
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

    @OnClick({R.id.tv_cancle, R.id.iv_reduce, R.id.iv_add, R.id.iv_delivery, R.id.tv_output_count, R.id.tv_inspection})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_cancle:
                if (isSynStatus()) {
                    refreshQuit();
                } else {
                    pop();
                }
                break;
            case R.id.iv_reduce:
                reduceOutPut();
                break;
            case R.id.iv_add:
                addOutPut();
                break;
            case R.id.iv_delivery:
                if (wsTaskTypeEnum == WSTaskTypeEnum.HISTORY_TASK) {
                    ToastUtil.showInfoCenterShort("历史任务禁止输出！");
                    return;
                }

                if (getString(R.string.text_unbind).equals(tvTrayNumber.getText().toString().trim()) && !(!TextUtils.isEmpty(checkTag) && "yes".equals(checkTag))) {
                    ToastUtil.showInfoCenterShort("请先绑定托盘");
                } else {
                    if (outputCount > 0) {
                        showRemindDialog();
                    } else {
                        ToastUtil.showInfoCenterShort("请输入输出数量");
                    }
                }
                break;
            case R.id.tv_output_count:
                WSDialogUtils.showEntryNumberDialog(getActivity(), "请输入在制品输出数量", predictCount, new WSDialogCallBackTwo() {
                    @Override
                    public void OnPositiveClick(Object obj) {
                        if (obj != null) {
                            outputCount = (int) Double.parseDouble(obj.toString().trim());
                            tvOutputCount.setText(outputCount + "");
                        }

                    }

                    @Override
                    public void OnNegativeClick() {

                    }
                });
                break;
            case R.id.tv_inspection:
                //报验
                requestInspectionData();
                break;

        }
    }

    private void showRemindDialog() {
        WSDialogUtils.showRemindTwoDialog(mActivity, "请确认输出数量", outputCount, new WSDialogCallBackTwo() {
            @Override
            public void OnPositiveClick(Object obj) {
                requesMakingDelivery();
            }

            @Override
            public void OnNegativeClick() {

            }
        });
    }

    private void addOutPut() {
        if (outputCount < predictCount) {
            outputCount++;
            tvOutputCount.setText(outputCount + "");
        } else {
            ToastUtil.showInfoCenterShort("不能超过预计输出数量:" + predictCount);
        }
    }

    private void reduceOutPut() {
        if (outputCount > 0) {
            outputCount--;
            tvOutputCount.setText(outputCount + "");
        } else {
            ToastUtil.showInfoCenterShort("输出数量不能小于0");
        }
    }


    @Override
    public void showDeliveryRecord(WSWipHistory wsWipHistory) {
        if (wsWipHistory != null) {
            tvProductModle.setText("产品型号: " + wsWipHistory.getProductModelTypeName());
            tvDeliveryCount.setText("已投递总数: " + wsWipHistory.getCount());
            List<WSDeliverHistory> deliverList = wsWipHistory.getDeliverList();
            makingReportAdapterMakingReport.setNewData(deliverList);
        }
    }

    @Override
    public void showInspebctionList(List<WSTaskProductCheckTray> datas) {
        if (datas != null) {
            if (datas.size() > 0) {
                showInspebctionListDialog(datas);
            } else {
                ToastUtil.showCenterShort("暂无可报验的托盘", true);
            }
        }
    }

    private void showInspebctionListDialog(List<WSTaskProductCheckTray> datas) {
        if (mOutPutInsoectionDialog == null) {

            mOutPutInsoectionDialog = WSDialogUtils.showOutPutInsoectionDialog(mActivity, "报验", new WSDialogCallBackThree() {

                @Override
                public void OnPositiveClick(Object obj) {
                    if (obj != null) {
                        String data = (String) obj;
                        toInspection(data);
                    }

                }

                @Override
                public void checkScanCode(String code) {
                    checkInspectionCode(code);
                }

                @Override
                public void OnNegativeClick() {

                }
            });
        }
        mOutPutInsoectionDialog.setData(datas, null, 0, taskId);
        mOutPutInsoectionDialog.show();
    }

    @Override
    public void checkInspectionResult(boolean result, String code) {
        if (result && mOutPutInsoectionDialog != null) {
            mOutPutInsoectionDialog.setScanCode(code);
        } else {
            mOutPutInsoectionDialog.setScanCode(getResources().getString(R.string.text_unbind));
        }
    }

    @Override
    public void toInspectionResult(boolean result) {
        if (result && mOutPutInsoectionDialog != null && mOutPutInsoectionDialog.isShowing()) {
            mOutPutInsoectionDialog.dismiss();
        }
    }

    @Override
    public void showBindResult(String result) {
        if (!TextUtils.isEmpty(result)) {
            tvTrayNumber.setText(result);
            ToastUtil.showInfoCenterShort(getString(R.string.text_bind_sucess));
        } else {
            tvTrayNumber.setText(getString(R.string.text_unbind));
        }
    }

    @Override
    public void showDeliveryResult(boolean result, String recordId) {
        if (result) {
            ToastUtil.showInfoCenterShort("投递成功");
//            getString(R.string.text_unbind).equals(tvTrayNumber.getText().toString().trim()) &&
            if (!TextUtils.isEmpty(recordId)) {
                if (!TextUtils.isEmpty(checkTag) && "yes".equals(checkTag)) {
                    //最后一道工序 不打印
                } else {
                    requestPrintDelivery(recordId, true);
                }
            } else {
                refreshQuit();
            }
        }
    }

    @Override
    public void showDeliveryPrint(boolean result, boolean isClose) {
        if (result) {
            ToastUtil.showInfoCenterShort("打印成功");
            if (isClose) {
                refreshQuit();
            }
        }
    }

    private void requestPrintDelivery(String recordId, boolean isClose) {
        if (!TextUtils.isEmpty(recordId)) {
            Map<String, String> params = new HashMap<>();
            params.put("recordId", recordId);
            currentPresent.requestPrintDelivery(params, isClose);
        }
    }

    private void refreshQuit() {
        Bundle bundle = new Bundle();
        bundle.putBoolean("isRefresh", true);
        setFragmentResult(0, bundle);
        super.clickBack();
    }

    @Override
    public void closeCurrent() {
        refreshQuit();
    }

    @Override
    public void setScanResult(String scanResult) {
        if (!TextUtils.isEmpty(scanResult)) {
            requesBindTray(scanResult);
        }
    }

    private void requestDeliveryRecord(boolean isShowLoading) {
        Map<String, String> params = new HashMap<>();
        params.put("taskId", taskId);
        currentPresent.requestDeliveryRecord(params, isShowLoading);
    }

    private void requestInspectionData() {
        Map<String, String> params = new HashMap<>();
        params.put("taskId", taskId);
        currentPresent.requestInspectionData(params, true);
    }

    private void checkInspectionCode(String code) {
        Map<String, String> params = new HashMap<>();
        params.put("code", code);
        currentPresent.checkInspectionCode(params, true);
    }

    private void toInspection(String data) {
        Map<String, String> params = new HashMap<>();
        params.put("checkProduct", data);
        currentPresent.toInspection(params, true);
    }

    private void requesBindTray(String code) {
        Map<String, String> params = new HashMap<>();
        params.put("code", code);
        currentPresent.requesBindTray(params);
    }

    private void requesMakingDelivery() {
        String deliveryInfo = getDeliveryInfo();
        if (!TextUtils.isEmpty(deliveryInfo)) {
            Map<String, String> params = new HashMap<>();
            params.put("workStationOutInfo", deliveryInfo);
            currentPresent.requesMakingDelivery(params);
        }
    }

    private String getDeliveryInfo() {
        WSWorkStationOutVO wsWorkStationOutVO = new WSWorkStationOutVO();
        wsWorkStationOutVO.setWorkStationTaskId(taskId);
        wsWorkStationOutVO.setOutPersonId(WSUserManager.getInstance().getPersonId());
        wsWorkStationOutVO.setRepairReturnCount(outputCount);
        if (getString(R.string.text_unbind).equals(tvTrayNumber.getText().toString().trim())) {
            wsWorkStationOutVO.setTrayCode("");
        } else {
            wsWorkStationOutVO.setTrayCode(tvTrayNumber.getText().toString().trim());
        }

        return JsonUtil.toJson(wsWorkStationOutVO);
    }

    @Override
    public void setFragmentBack() {
        super.setFragmentBack();
    }

    @Override
    public void setActivityLogoOrBack() {

        if (mActivity != null && mActivity instanceof WSMainActivity) {
            ((WSMainActivity) mActivity).setActivityLogoOrBack(1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder1 = ButterKnife.bind(this, rootView);
        return rootView;
    }
}
