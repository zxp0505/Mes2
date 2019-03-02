package workstation.zjyk.workstation.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
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
import workstation.zjyk.workstation.modle.bean.WSInputWipInfo;
import workstation.zjyk.workstation.modle.bean.WSMaterial;
import workstation.zjyk.workstation.modle.bean.WSReturnOrRepair;
import workstation.zjyk.workstation.modle.bean.WSReturnOrRepairHistory;
import workstation.zjyk.workstation.modle.bean.WSReviewWorkStationVo;
import workstation.zjyk.workstation.modle.bean.WSWorkStationOutVO;
import workstation.zjyk.workstation.ui.adapter.WSAdapterReviewWork;
import workstation.zjyk.workstation.ui.adapter.WSAdapterReviewWorkHistory;
import workstation.zjyk.workstation.ui.manager.WSUserManager;
import workstation.zjyk.workstation.ui.present.WSReviewWrokPresent;
import workstation.zjyk.workstation.ui.views.WSReviewWorkView;
import workstation.zjyk.workstation.util.dialog.WSDialogUtils;
import workstation.zjyk.workstation.util.dialog.WSReviewWorkDialog;
import workstation.zjyk.workstation.util.dialog.callback.WSDialogCallBackTwo;

/**
 * 返工
 * Created by zjgz on 2018/1/26.
 */

public class WSReviewWorkFragment extends WSBaseFragment<WSReviewWrokPresent> implements WSReviewWorkView {
    @BindView(R.id.rl_title_one)
    RelativeLayout rlTitleOne;
    @BindView(R.id.rl_title_two)
    RelativeLayout rlTitleTwo;
    @BindView(R.id.rl_title_three)
    RelativeLayout rlTitleThree;
    @BindView(R.id.recycleview)
    RecyclerView recycleview;
    Unbinder unbinder;
    @BindView(R.id.iv_back_three)
    ImageView ivBackThree;
    @BindView(R.id.tv_title_left)
    TextView tvTitleLeft;
    @BindView(R.id.tv_hand_barcode_three)
    TextView tvHandBarcodeThree;
    Unbinder unbinder1;
    @BindView(R.id.tv_bind_tray)
    TextView tvBindTray;
    @BindView(R.id.tv_output_count)
    TextView tvOutputCount;
    @BindView(R.id.recycleview_history)
    RecyclerView recycleviewHistory;
    @BindView(R.id.tv_review_confirm)
    TextView tvReviewConfirm;
    @BindView(R.id.tv_review_work_count)
    TextView tvReviewWorkCount;
    private WSAdapterReviewWork wsAdapterReviewWork;
    private ArrayList<WSInputWipInfo> wipData;
    private WSReviewWorkDialog wsReviewWorkDialog;
    private WSInputWipInfo currentBean;
    private String taskId;
    private WSAdapterReviewWorkHistory mAdapterReviewWorkHistory;

    public static WSReviewWorkFragment newInstance(Bundle bundle) {
        WSReviewWorkFragment wsReviewWorkFragment = new WSReviewWorkFragment();
        wsReviewWorkFragment.setArguments(bundle);
        return wsReviewWorkFragment;
    }

    @Override
    protected void creatPresent() {
        currentPresent = new WSReviewWrokPresent();
    }

    @Override
    public void setActivityLogoOrBack() {

    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        unbinder = ButterKnife.bind(this, view);
        rlTitleOne.setVisibility(View.GONE);
        rlTitleTwo.setVisibility(View.GONE);
        tvHandBarcodeThree.setVisibility(View.GONE);
        tvBindTray.setVisibility(View.VISIBLE);
        rlTitleThree.setVisibility(View.VISIBLE);
        ivBackThree.setOnClickListener(this);
        tvTitleLeft.setText("返工");
        tvBindTray.setTag(false);
        tvReviewConfirm.setTag(false);

        setConfirmBg();
        Bundle arguments = getArguments();
        if (arguments != null) {
            wipData = arguments.getParcelableArrayList("wipData");
            taskId = arguments.getString("taskId");
        }
        recycleview.setLayoutManager(new LinearLayoutManager(mActivity));
        wsAdapterReviewWork = new WSAdapterReviewWork();
        recycleview.setAdapter(wsAdapterReviewWork);
        wsAdapterReviewWork.setNewData(wipData);

        recycleviewHistory.setLayoutManager(new LinearLayoutManager(mActivity));
        mAdapterReviewWorkHistory = new WSAdapterReviewWorkHistory();
        recycleviewHistory.setAdapter(mAdapterReviewWorkHistory);

        wsAdapterReviewWork.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                WSInputWipInfo bean = (WSInputWipInfo) adapter.getData().get(position);
                double count = bean.getRemainCount();
                double trueCount = bean.getTrueCount();
                switch (view.getId()) {
                    case R.id.iv_reduce:
                        if (trueCount >= 1) {
                            trueCount--;
                            bean.setTrueCount(trueCount);
                            adapter.notifyItemChanged(position);
                        } else {
                            ToastUtil.showCenterShort("输出数量不能小于0!", true);
                        }
                        break;
                    case R.id.iv_add:
                        if (trueCount < count) {
                            trueCount++;
                            if (trueCount > count) {
                                trueCount = count;
                            }
                            bean.setTrueCount(trueCount);
                            adapter.notifyItemChanged(position);
                        } else {
                            ToastUtil.showCenterShort("输出数量不能大于总数量!", true);
                        }
                        break;
                    case R.id.tv_except_count:
                        showReviewWorkEntryNumberDialog(bean);
                        break;
                    case R.id.iv_select_status:
                        bean.setSelect(!bean.isSelect());
                        adapter.notifyItemChanged(bean.getPosition());
                        if (currentBean != null && (!currentBean.getProcedureName().equals(bean.getProcedureName()))) {
                            currentBean.setSelect(false);
                            adapter.notifyItemChanged(currentBean.getPosition());
                        }
                        currentBean = bean;
                        setConfirmBg();
                        break;
                }
            }

        });

    }

    public void synReviewworkList(List<WSInputWipInfo> wipData){
        wsAdapterReviewWork.setNewData(wipData);
    }

    private void showReviewWorkEntryNumberDialog(WSInputWipInfo wsMaterial) {
        WSDialogUtils.showReviewWorkEntryNumberDialog(getActivity(), "请输入返工数量", wsMaterial, new WSDialogCallBackTwo() {
            @Override
            public void OnPositiveClick(Object obj) {
                if (obj instanceof WSInputWipInfo) {
                    WSInputWipInfo bean = (WSInputWipInfo) obj;
                    wsAdapterReviewWork.notifyItemChanged(bean.getPosition());
                }
            }

            @Override
            public void OnNegativeClick() {

            }
        });
    }

    private void setConfirmBg() {
        boolean isClickable = (boolean) tvReviewConfirm.getTag();
        if (currentBean != null) {
            if (currentBean.isSelect()) {
                if (!isClickable) {
                    tvBindTray.setBackgroundResource(R.drawable.shape_blue);
                    tvBindTray.setTag(true);
                    tvReviewConfirm.setBackgroundResource(R.drawable.shape_blue);
                    tvReviewConfirm.setTag(true);
                }
            } else {
                if (isClickable) {
                    tvBindTray.setBackgroundResource(R.drawable.shape_gray_bg);
                    tvBindTray.setTag(false);
                    tvReviewConfirm.setBackgroundResource(R.drawable.shape_gray_bg);
                    tvReviewConfirm.setTag(false);
                }
            }
        } else {
            tvBindTray.setBackgroundResource(R.drawable.shape_gray_bg);
            tvBindTray.setTag(false);
            tvReviewConfirm.setBackgroundResource(R.drawable.shape_gray_bg);
            tvReviewConfirm.setTag(false);
        }
    }

    @Override
    public void initData() {
        requestReviewHistory(true);
    }

    private void requestReviewHistory(boolean isShowLoading) {
        Map<String, String> params = new HashMap<>();
        params.put("taskId", taskId);
        currentPresent.requestReviewHistory(params, isShowLoading);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_review_work;
    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        // TODO: inflate a fragment view
//        View rootView = super.onCreateView(inflater, container, savedInstanceState);
//
//        unbinder1 = ButterKnife.bind(this, rootView);
//        return rootView;
//    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.tv_review_confirm, R.id.tv_bind_tray})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_review_confirm://界面已弃用
                //绑定托盘
                boolean tag = (boolean) tvReviewConfirm.getTag();
                if (tag) {
                    if (getBindData().size() > 0) {
                        Map<String, String> params = new HashMap<>();
                        params.put("taskId", taskId);
                        params.put("procedureId", currentBean.getId());
                        currentPresent.requestReviewWorkStation(params, currentBean);
                    } else {
                        ToastUtil.showInfoCenterShort("请输入返工数量!");
                    }
                } else {
                    ToastUtil.showInfoCenterShort("请选择返工工序!");
                }
                break;
            case R.id.tv_bind_tray:
                //绑定托盘
                boolean tag1 = (boolean) tvBindTray.getTag();
                if (tag1) {
                    if (getBindData().size() > 0) {
                        Map<String, String> params = new HashMap<>();
                        params.put("taskId", taskId);
                        params.put("procedureId", currentBean.getId());
                        currentPresent.requestReviewWorkStation(params, currentBean);
                    } else {
                        ToastUtil.showInfoCenterShort("请输入返工数量!");
                    }
                } else {
                    ToastUtil.showInfoCenterShort("请选择返工工序!");
                }

                break;
        }
    }

    @Override
    protected void synCurrentFragment(int refreshType) {
        super.synCurrentFragment(refreshType);
        if (refreshType == getRefreshaType()) {
            requestReviewHistory(false);
        }
    }

    private int getRefreshaType() {
        return MyServer.ACTION_TASK_REWORK;
    }

    private List<WSInputWipInfo> getBindData() {
        List<WSInputWipInfo> newDatas = new ArrayList<>();
        List<WSInputWipInfo> data = wsAdapterReviewWork.getData();
        if (data != null && data.size() > 0) {
            for (int i = 0; i < data.size(); i++) {
                WSInputWipInfo wsInputWipInfo = data.get(i);
                double trueCount = wsInputWipInfo.getTrueCount();
                if (trueCount > 0) {
                    newDatas.add(wsInputWipInfo);
                }
            }
        }
        return newDatas;
    }

    private WSReviewWorkDialog getReviewDialog() {
        if (wsReviewWorkDialog == null)
            wsReviewWorkDialog = WSDialogUtils.showReviewDialog(getActivity(), "返工", new WSDialogCallBackTwo() {
                @Override
                public void OnPositiveClick(Object obj) {
                    if (obj != null && obj instanceof WSWorkStationOutVO) {
                        //先校验托盘 然后请求返工

                        WSWorkStationOutVO wsWorkStationOutVO = (WSWorkStationOutVO) obj;
                        Map<String, String> params = new HashMap<>();
                        params.put("code", wsWorkStationOutVO.getTrayCode());
                        currentPresent.reviewBindTray(params, wsWorkStationOutVO);


                    }

                }

                @Override
                public void OnNegativeClick() {

                }
            });
        return wsReviewWorkDialog;
    }

    @Override
    public void showRewrokResult(boolean result) {
        if (result) {
//            getReviewDialog().dismiss();
            quitRefresh();
        }
    }

    private void quitRefresh() {
        Bundle bundle = new Bundle();
        bundle.putBoolean("isRefresh", true);
        setFragmentResult(0, bundle);
        super.clickBack();
    }

    @Override
    public void closeCurrent() {
        quitRefresh();
    }

    @Override
    public void showRewrokStations(List<WSReviewWorkStationVo> wsReviewWorkStationVos, WSInputWipInfo currentBean) {
        if (wsReviewWorkStationVos != null && wsReviewWorkStationVos.size() > 0) {
            getReviewDialog().setData(getActivity(), wsReviewWorkStationVos, currentBean).showThis();
        }
    }

    @Override
    public void showReviewBindTrayResult(boolean result, WSWorkStationOutVO wsWorkStationOutVO) {
        if (result) {
            getReviewDialog().dismissThis();
            wsWorkStationOutVO.setReason(currentBean.getReason());
            wsWorkStationOutVO.setWorkStationTaskId(taskId);
            wsWorkStationOutVO.setOutPersonId(WSUserManager.getInstance().getPersonId());
            Map<String, String> params = new HashMap<>();
            params.put("workStationOutInfo", JsonUtil.toJson(wsWorkStationOutVO));
            currentPresent.requestRework(params);
        }
    }

    @Override
    public void showReviewWorkHistory(WSReturnOrRepair wsReturnOrRepair) {
        if (wsReturnOrRepair != null) {
            tvOutputCount.setText("返工总数:" + wsReturnOrRepair.getTotalOutCount());
            tvReviewWorkCount.setText("返工返回总数:" + wsReturnOrRepair.getTotalReturnCount());
            List<WSReturnOrRepairHistory> historyList = wsReturnOrRepair.getHistoryList();
            mAdapterReviewWorkHistory.setNewData(historyList);
        }
    }
}
