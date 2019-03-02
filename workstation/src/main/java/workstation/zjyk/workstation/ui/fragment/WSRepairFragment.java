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
import workstation.zjyk.workstation.modle.bean.WSMaintainReason;
import workstation.zjyk.workstation.modle.bean.WSReturnOrRepair;
import workstation.zjyk.workstation.modle.bean.WSReturnOrRepairHistory;
import workstation.zjyk.workstation.modle.bean.WSWorkStationOutVO;
import workstation.zjyk.workstation.ui.WSMainActivity;
import workstation.zjyk.workstation.ui.adapter.WSAdapterRepairHistory;
import workstation.zjyk.workstation.ui.manager.WSUserManager;
import workstation.zjyk.workstation.ui.present.WSRepairPresent;
import workstation.zjyk.workstation.ui.views.WSRepairVew;
import workstation.zjyk.workstation.util.dialog.WSDialogUtils;
import workstation.zjyk.workstation.util.dialog.WSRepairReasonDialog;
import workstation.zjyk.workstation.util.dialog.callback.WSDialogCallBackTwo;

/**
 * 维修
 * Created by zjgz on 2017/12/12.
 */

public class WSRepairFragment extends WSBaseFragment<WSRepairPresent> implements WSRepairVew {

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
    @BindView(R.id.iv_back_two)
    ImageView ivBackTwo;
    @BindView(R.id.tv_detail)
    TextView tvDetail;
    @BindView(R.id.tv_hand_barcode_two)
    TextView tvHandBarcodeTwo;
    @BindView(R.id.tv_output_count_history)
    TextView tvOutputCountHistory;
    @BindView(R.id.tv_repair_count_history)
    TextView tvRepairCountHistory;
    @BindView(R.id.tv_repair_reason)
    TextView tvRepairReason;
    private Unbinder bind;
    int outputCount = 0;
    private String taskId;
    private int predictCount;
    private WSAdapterRepairHistory mAdapterRepairHistory;
    private WSRepairReasonDialog mRepairReasonDialog;
    private String reason;
    private List<WSMaintainReason> selectRepairReason;

    public static WSRepairFragment newInstance(Bundle bundle) {
        WSRepairFragment wsMakingOutputFragment = new WSRepairFragment();
        wsMakingOutputFragment.setArguments(bundle);
        return wsMakingOutputFragment;
    }

    @Override
    protected void creatPresent() {
        currentPresent = new WSRepairPresent();
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        bind = ButterKnife.bind(this, view);
        rlTitleOne.setVisibility(View.GONE);
        rlTitleTwo.setVisibility(View.VISIBLE);
        tvModel.setVisibility(View.GONE);
        tvProduce.setVisibility(View.GONE);
        tvProductNumber.setText("维修");
        ivBackTwo.setOnClickListener(this);
        tvDetail.setVisibility(View.VISIBLE);
        tvDetail.setText("维修原因");
        int dimensionPixelSize = getResources().getDimensionPixelOffset(R.dimen.x_design_250px);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) tvDetail.getLayoutParams();
        layoutParams.rightMargin = dimensionPixelSize;
        tvDetail.requestLayout();

        tvHandBarcodeTwo.setOnClickListener(this);
        tvOutputCount.setText(outputCount + "");
        tvTrayNumber.setText(getString(R.string.text_unbind));
        Bundle arguments = getArguments();
        if (arguments != null) {
            taskId = arguments.getString("taskId");
            predictCount = arguments.getInt("predictCount");

        }
        recycleview.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapterRepairHistory = new WSAdapterRepairHistory();
        recycleview.setAdapter(mAdapterRepairHistory);
    }

    @Override
    public void initData() {
        requestRepairHistory(true);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_repair;
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

    @OnClick({R.id.tv_cancle, R.id.iv_reduce, R.id.iv_add, R.id.iv_delivery, R.id.tv_output_count, R.id.tv_detail})
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
                //缺少数量校验
                if (getString(R.string.text_unbind).equals(tvTrayNumber.getText().toString().trim())) {
                    ToastUtil.showInfoCenterShort("请先绑定托盘");
                } else {

                    if (outputCount > 0) {
                        requesRepairDelivery();
                    } else {
                        ToastUtil.showInfoCenterShort("请输入维修数量");
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
            case R.id.tv_detail:
                //输入维修原因
                requestReairReasons();
                break;

        }
    }

    private void requestReairReasons() {
        Map<String, String> params = new HashMap<>();
        currentPresent.requestReairReasons(params);
    }

    @Override
    public void showReairReasons(List<WSMaintainReason> reasons) {
        if (reasons != null) {
            if (reasons.size() > 0) {
                getReairReasonDialog(reasons);
            } else {
                ToastUtil.showInfoCenterShort("暂无维修原因类型");
            }
        }

    }

    private WSRepairReasonDialog getReairReasonDialog(List<WSMaintainReason> reasons) {
        if (selectRepairReason != null) {
            for (int i = 0; i < reasons.size(); i++) {
                WSMaintainReason reason = reasons.get(i);
                if (selectRepairReason.contains(reason)) {
                    reason.setSelect(true);
                }
            }
        }
        if (mRepairReasonDialog == null) {
            mRepairReasonDialog = WSDialogUtils.showRepairReasonDialog(mActivity, "请选择维修原因", new WSDialogCallBackTwo() {
                @Override
                public void OnPositiveClick(Object obj) {
                    if (obj != null && obj instanceof List) {
//                        reason = (String) obj;
                        selectRepairReason = (List<WSMaintainReason>) obj;

                        StringBuilder builder = new StringBuilder();
                        for (int i = 0; i < selectRepairReason.size(); i++) {
                            builder.append(selectRepairReason.get(i).getName()).append(",");
                        }
                        if (builder.toString().length() > 0) {
                            builder = builder.deleteCharAt(builder.toString().length() - 1);
                            reason = builder.toString();
                            tvRepairReason.setVisibility(View.VISIBLE);
                            tvRepairReason.setText("维修原因:" + reason);
                        } else {
                            tvRepairReason.setVisibility(View.GONE);
                        }

                    }
                }

                @Override
                public void OnNegativeClick() {

                }
            });
        }
        mRepairReasonDialog.setData(reasons);
        mRepairReasonDialog.show();
        return mRepairReasonDialog;

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
    public void showBindResult(String result) {
        if (!TextUtils.isEmpty(result)) {
            tvTrayNumber.setText(result);
            ToastUtil.showInfoCenterShort(getString(R.string.text_bind_sucess));
        } else {
            tvTrayNumber.setText(getString(R.string.text_unbind));
        }
    }

    @Override
    public void showRepairResult(boolean result) {
        if (result) {
            ToastUtil.showInfoCenterShort("报修成功");
            refreshQuit();
        }
    }


    private void refreshQuit() {
        Bundle bundle = new Bundle();
        bundle.putBoolean("isRefresh", true);
        setFragmentResult(0, bundle);
        super.clickBack();
    }

    @Override
    protected void synCurrentFragment(int refreshType) {
        super.synCurrentFragment(refreshType);
        if (getRefreshaType() == refreshType) {
            requestRepairHistory(false);
        }
    }

    private int getRefreshaType() {
        return MyServer.ACTION_TASK_REPAIR;
    }

    @Override
    public void showRepairHistory(WSReturnOrRepair wsReturnOrRepair) {
        if (wsReturnOrRepair != null && tvRepairCountHistory != null) {
            tvRepairCountHistory.setText("维修返回总数: " + wsReturnOrRepair.getTotalReturnCount());
            tvOutputCountHistory.setText("维修总数: " + wsReturnOrRepair.getTotalOutCount());
            List<WSReturnOrRepairHistory> historyList = wsReturnOrRepair.getHistoryList();
            mAdapterRepairHistory.setNewData(historyList);
        }

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

    private void requestRepairHistory(boolean isShowLoading) {
        Map<String, String> params = new HashMap<>();
        params.put("taskId", taskId);
        currentPresent.requestRepairHistory(params, isShowLoading);
    }

    private void requesBindTray(String code) {
        Map<String, String> params = new HashMap<>();
        params.put("code", code);
        currentPresent.requesBindTray(params);
    }

    private void requesRepairDelivery() {
        if (TextUtils.isEmpty(reason)) {
            ToastUtil.showInfoCenterShort("请选择维修原因");
            return;
        }
        WSWorkStationOutVO wsWorkStationOutVO = new WSWorkStationOutVO();
        wsWorkStationOutVO.setReason(reason);
        wsWorkStationOutVO.setTrayCode(tvTrayNumber.getText().toString().trim());
        wsWorkStationOutVO.setRepairReturnCount(outputCount);
        wsWorkStationOutVO.setOutPersonId(WSUserManager.getInstance().getPersonId());
        wsWorkStationOutVO.setWorkStationTaskId(taskId);
        Map<String, String> params = new HashMap<>();
        params.put("workStationOutInfo", JsonUtil.toJson(wsWorkStationOutVO));
        currentPresent.requestRepair(params);
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
}
