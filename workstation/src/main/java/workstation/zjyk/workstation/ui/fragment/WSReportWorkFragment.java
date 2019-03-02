package workstation.zjyk.workstation.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
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
import me.yokeyword.fragmentation.ISupportFragment;
import workstation.zjyk.workstation.R;
import workstation.zjyk.workstation.modle.bean.WSDailyWorkHistory;
import workstation.zjyk.workstation.modle.bean.WSProcedureStep;
import workstation.zjyk.workstation.modle.bean.WSWorkReportStepVO;
import workstation.zjyk.workstation.modle.bean.WSWorkReportVO;
import workstation.zjyk.workstation.ui.WSMainActivity;
import workstation.zjyk.workstation.ui.adapter.WSAdapterReportWorkGrid;
import workstation.zjyk.workstation.ui.adapter.WSAdapterReportWorkHistory;
import workstation.zjyk.workstation.ui.manager.WSUserManager;
import workstation.zjyk.workstation.ui.present.WSReportWorkPresent;
import workstation.zjyk.workstation.ui.views.WSReportWorkView;
import workstation.zjyk.workstation.util.dialog.WSDialogUtils;
import workstation.zjyk.workstation.util.dialog.WSReportHistoryDialog;
import workstation.zjyk.workstation.util.dialog.WSReportWorkEntryNumber;
import workstation.zjyk.workstation.util.dialog.callback.WSDialogCallBackTwo;

/**
 * 报工
 * Created by zjgz on 2017/12/12.
 */

public class WSReportWorkFragment extends WSBaseFragment<WSReportWorkPresent> implements WSReportWorkView {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rl_title_one)
    RelativeLayout rlTitleOne;
    @BindView(R.id.rl_title_two)
    RelativeLayout rlTitleTwo;
    @BindView(R.id.tv_title_left)
    TextView tvTitleLeft;
    @BindView(R.id.tv_quit)
    TextView tvQuit;
    @BindView(R.id.rl_title_three)
    RelativeLayout rlTitleThree;
    @BindView(R.id.recycleview)
    RecyclerView recycleview;
    Unbinder unbinder;
    @BindView(R.id.recycleview_history)
    RecyclerView recycleviewHistory;
    @BindView(R.id.tv_report_confirm)
    TextView tvReportConfirm;
    @BindView(R.id.iv_back_three)
    ImageView ivBackThree;
    @BindView(R.id.tv_hand_barcode_three)
    TextView tvHandBarcodeThree;
    @BindView(R.id.tv_step_title)
    TextView tvStepTitle;
    @BindView(R.id.tv_history_title)
    TextView tvHistoryTitle;
    int selectCount = 0;
    boolean isOverOutput = false;
    @BindView(R.id.tv_output_count)
    TextView tvOutputCount;
    Unbinder unbinder1;
    List<WSProcedureStep> selectedReportData = new ArrayList<>();//页面关闭前 记录的已报数据
    private Unbinder bind;
    //    private WSAdapterReportWork mAdapterReportWork;
    private WSAdapterReportWorkHistory mAdapterReportWorkHistory;
    private String procedureId;
    private String taskId;
    private ArrayList<WSProcedureStep> stepdatas;
    private int outputCount = 0;//正常输出数量  每个工步所报的数量不能超过正常输出数量 --变化了 根据默认的最大值
    private WSReportHistoryDialog wsReportHistoryDialog;
    private WSAdapterReportWorkGrid mAdapterReportWorkGrid;
    private WSReportWorkEntryNumber mReportWorkEntryNumber;

    public static ISupportFragment newInstance(Bundle bundle) {
        WSReportWorkFragment wsReportWorkFragment = new WSReportWorkFragment();
        wsReportWorkFragment.setArguments(bundle);
        return wsReportWorkFragment;
    }

    private void showDialogEntryNumber(WSProcedureStep bean) {
        //此处有坑
        bean.setCount(Integer.MAX_VALUE);
        //
        //                mAdapterReportWork.notifyItemChanged(data.getPosition());
        mReportWorkEntryNumber = WSDialogUtils.showReportWorkEntryNumberDialog(getActivity(), "请输入报工数量", bean, new WSDialogCallBackTwo() {
            @Override
            public void OnPositiveClick(Object obj) {
                if (obj != null) {

                    WSProcedureStep data = (WSProcedureStep) obj;
                    outputCount = (int) data.getTrueCount();
                    setOutputText();
                }
//                mAdapterReportWork.notifyItemChanged(data.getPosition());
            }

            @Override
            public void OnNegativeClick() {

            }
        });
    }

    //校验每个工步的id是否超出输出数量
    private void checkStepIdCount(String stepId, double reportCount) {
        if (reportCount > outputCount) {
            isOverOutput = true;
            return;
        }
        List<WSDailyWorkHistory> data = mAdapterReportWorkHistory.getData();
        for (int i = 0; i < data.size(); i++) {
            WSDailyWorkHistory wsDailyWorkHistory = data.get(i);
            String procedureStepId = wsDailyWorkHistory.getProcedureStepId();
            if (!TextUtils.isEmpty(stepId) && stepId.equals(procedureStepId)) {
                double historyCount = wsDailyWorkHistory.getCount();
                if ((reportCount + historyCount) > outputCount) {
                    isOverOutput = true;
                    return;
                }
            }
        }
    }

    /**
     * 刷新上次已选界面
     */
    private void refreshSelected() {
        selectCount = 0;
        setConfirmBg();
//        List<WSProcedureStep> data = mAdapterReportWork.getData();
//        for (int i = 0; i < data.size(); i++) {
//            WSProcedureStep wsProcedureStep = data.get(i);
//            double trueCount = wsProcedureStep.getTrueCount();
//            if (trueCount > 0 || wsProcedureStep.isSelect()) {
//                wsProcedureStep.setTrueCount(0);
//                wsProcedureStep.setSelect(false);
//                mAdapterReportWork.notifyItemChanged(wsProcedureStep.getPosition());
//            }
//        }
    }

    private void setConfirmBg() {
        boolean isClickable = (boolean) tvReportConfirm.getTag();
        if (selectCount > 0) {
            if (!isClickable) {
                tvReportConfirm.setBackgroundResource(R.drawable.shape_blue);
                tvReportConfirm.setTag(true);
            }
        } else {
            tvReportConfirm.setBackgroundResource(R.drawable.shape_gray_bg);
            tvReportConfirm.setTag(false);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        bind.unbind();
        if (selectedReportData != null) {
            selectedReportData.clear();
            selectedReportData = null;
        }
        if (mReportWorkEntryNumber != null && mReportWorkEntryNumber.isShowing()) {
            mReportWorkEntryNumber.dismiss();
        }
    }

    @OnClick({R.id.tv_quit, R.id.tv_report_confirm, R.id.iv_add, R.id.iv_reduce, R.id.tv_output_count})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_quit:
                //查看历史报工记录
                setSynStatus(false, MyServer.ACTION_TASK_REPORT_WORK);
                requesStepHistoryList(true);
                break;
            case R.id.tv_report_confirm:
                boolean isClickable = (boolean) tvReportConfirm.getTag();
                if (isClickable) {
                    requesReportComfirm();
                } else {
                    ToastUtil.showInfoCenterShort("请先选择提报的工步!");
                }

                break;
            case R.id.iv_add:
                addOutPut();
                break;
            case R.id.iv_reduce:
                reduceOutPut();
                break;
            case R.id.tv_output_count:
                showDialogEntryNumber(new WSProcedureStep());
                break;
        }
    }

    private void requesStepHistoryList(boolean isShowLoading) {
        Map<String, String> params = new HashMap<>();
        params.put("procedureId", procedureId);
        params.put("taskId", taskId);
        currentPresent.requesStepHistoryList(params, isShowLoading);
    }

    private void requesReportComfirm() {
        WSWorkReportVO reportData = getReportData();
        if (reportData == null) {
            return;
        }
        //return JsonUtil.toJson(wsWorkReportVO);
        if (isOverOutput) {
            showEntryScretKeyDialog(reportData);
        } else {
            showConfirmDialog(JsonUtil.toJson(reportData));
        }

    }

    private void addOutPut() {
        outputCount++;
        setOutputText();
    }

    private void setOutputText() {
        if (tvOutputCount != null) {
            tvOutputCount.setText(outputCount + "");
        }
    }

    private void reduceOutPut() {
        if (outputCount > 0) {
            outputCount--;
            if (outputCount < 1) {
                outputCount = 0;
            }
            setOutputText();
        } else {
            ToastUtil.showInfoCenterShort("输出数量不能小于0");
        }
    }

    private WSWorkReportVO getReportData() {
        isOverOutput = false;
        double count = 0;
        WSWorkReportVO wsWorkReportVO = new WSWorkReportVO();
        List<WSWorkReportStepVO> workReportStepVOList = new ArrayList<>();
        List<WSProcedureStep> data = mAdapterReportWorkGrid.getData();
        if (data != null && data.size() > 0) {
            for (int i = 0; i < data.size(); i++) {
                WSProcedureStep wsProcedureStep = data.get(i);
//                double trueCount = wsProcedureStep.getTrueCount();
                double trueCount = outputCount;
                String id = wsProcedureStep.getId();
                if (trueCount > 0 && wsProcedureStep.isSelect()) {
                    selectedReportData.add(wsProcedureStep);
                    if (!isOverOutput) {
                        checkStepIdCount(trueCount, wsProcedureStep.getReportCount());//报工数量不能大于计划数量
//                        checkStepIdCount(id, trueCount);//报工数量不能大于输出数量
                    }
                    count = count + trueCount;
                    WSWorkReportStepVO wsWorkReportStepVO = new WSWorkReportStepVO();
                    wsWorkReportStepVO.setProcedureStepId(id);
                    wsWorkReportStepVO.setCount(trueCount);
                    workReportStepVOList.add(wsWorkReportStepVO);
                }
            }

            wsWorkReportVO.setWorkReportStepList(workReportStepVOList);
        }
        wsWorkReportVO.setPersonId(WSUserManager.getInstance().getPersonId());
        wsWorkReportVO.setProcedureId(procedureId);
        wsWorkReportVO.setWorkStationTaskId(taskId);
        if (count <= 0) {
            ToastUtil.showInfoCenterShort("请添加报工数量");
            return null;
        }
        return wsWorkReportVO;


    }

    private void showEntryScretKeyDialog(WSWorkReportVO reportData) {
        WSDialogUtils.showEntryScretKeyDialog(mActivity, "报工数量已超出输出数量,请输入秘钥", new WSDialogCallBackTwo() {
            @Override
            public void OnPositiveClick(Object obj) {
                if (obj != null) {
                    String scretkay = (String) obj;
                    reportData.setToken(scretkay);
                    requesReportComfirm(JsonUtil.toJson(reportData), "");
                }
            }

            @Override
            public void OnNegativeClick() {

            }
        });
    }

    private void showConfirmDialog(String reportData) {
        WSDialogUtils.showTwoTipDialog(mActivity, "确定报工吗?", new WSDialogCallBackTwo() {
            @Override
            public void OnPositiveClick(Object obj) {
                requesReportComfirm(reportData, "");
            }

            @Override
            public void OnNegativeClick() {

            }
        });
    }

    //校验每个工步的id是否超出输出数量
    private void checkStepIdCount(double reportCount, double outPutMax) {
        if (reportCount > outPutMax) {
            isOverOutput = true;
        }
    }

    private void requesReportComfirm(String reportData, String token) {
        Map<String, String> params = new HashMap<>();
        if (!TextUtils.isEmpty(reportData)) {
            params.put("workReportInfo", reportData);
//            params.put("token", token);
            currentPresent.requesReportComfirm(params);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder1 = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    protected void creatPresent() {
        currentPresent = new WSReportWorkPresent();
    }

    @Override
    public void setFragmentBack() {
        super.setFragmentBack();
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
    public void setActivityLogoOrBack() {
        if (mActivity != null && mActivity instanceof WSMainActivity) {
            ((WSMainActivity) mActivity).setActivityLogoOrBack(1);
        }
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        bind = ButterKnife.bind(this, view);
        rlTitleOne.setVisibility(View.GONE);
        rlTitleTwo.setVisibility(View.GONE);
        rlTitleThree.setVisibility(View.VISIBLE);
        ivBackThree.setOnClickListener(this);
        tvQuit.setVisibility(View.VISIBLE);
        tvQuit.setText("历史报工记录");
        tvHandBarcodeThree.setVisibility(View.GONE);
        setReportName();
        tvReportConfirm.setTag(false);
        Bundle arguments = getArguments();
        if (arguments != null) {
            procedureId = arguments.getString("procedureId");
            taskId = arguments.getString("taskId");
            stepdatas = arguments.getParcelableArrayList("stepdata");
//            outputCount = arguments.getInt("outputCount");
            if (stepdatas != null && stepdatas.size() > 0) {
                outputCount = (int) stepdatas.get(0).getReportCount();
            }
        }
        setOutputText();
        setConfirmBg();
        tvStepTitle.setText("工步(" + stepdatas.size() + ")");
        mAdapterReportWorkHistory = new WSAdapterReportWorkHistory();
        recycleviewHistory.setLayoutManager(new LinearLayoutManager(getActivity()));
        recycleviewHistory.setAdapter(mAdapterReportWorkHistory);

//        mAdapterReportWork = new WSAdapterReportWork();
//        recycleview.setLayoutManager(new LinearLayoutManager(getActivity()));
//        recycleview.setAdapter(mAdapterReportWork);

        mAdapterReportWorkGrid = new WSAdapterReportWorkGrid();
        recycleview.setLayoutManager(new GridLayoutManager(mActivity, 3));
        recycleview.setAdapter(mAdapterReportWorkGrid);
        mAdapterReportWorkGrid.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                WSProcedureStep bean = (WSProcedureStep) adapter.getData().get(position);
                boolean select = bean.isSelect();
                switch (view.getId()) {
                    case R.id.fl_item:
                        bean.setSelect(!select);
                        adapter.notifyItemChanged(position);
                        if (bean.isSelect()) {
                            selectCount++;
                        } else {
                            selectCount--;
                            if (selectCount < 0) {
                                selectCount = 0;
                            }
                        }
                        setConfirmBg();
                        break;
                }
            }
        });
//        mAdapterReportWork.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
//            @Override
//            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
//                WSProcedureStep bean = (WSProcedureStep) adapter.getData().get(position);
//                double count = bean.getTrueCount();
//                boolean select = bean.isSelect();
//                switch (view.getId()) {
//                    case R.id.iv_reduce:
//                        if (count >= 1) {
//                            count--;
//                            bean.setTrueCount(count);
//                            adapter.notifyItemChanged(position);
//                        } else {
//                            ToastUtil.showCenterShort("数量不可以小于0", true);
//                        }
//                        break;
//                    case R.id.iv_add:
//                        count++;
//                        bean.setTrueCount(count);
//                        adapter.notifyItemChanged(position);
//                        break;
//                    case R.id.iv_select_status:
//                        bean.setSelect(!select);
//                        adapter.notifyItemChanged(position);
//                        if (bean.isSelect()) {
//                            selectCount++;
//                        } else {
//                            selectCount--;
//                            if (selectCount < 0) {
//                                selectCount = 0;
//                            }
//                        }
//                        setConfirmBg();
//                        break;
//                    case R.id.tv_count:
//                        showDialogEntryNumber(bean);
//                        break;
//                }
//            }
//        });
        showStepList(stepdatas);
    }

    public void setReportName() {
        if (tvTitleLeft != null) {
            tvTitleLeft.setText(WSUserManager.getInstance().getCurrentUserName() + ",工作提报:");
        }
    }

    @Override
    public void showStepList(List<WSProcedureStep> datas) {
        if (datas != null) {
//            for (int i = 0; i < datas.size(); i++) {
//                WSProcedureStep wsProcedureStep = datas.get(i);
//                wsProcedureStep.setTrueCount(wsProcedureStep.getReportCount());
//            }
//            mAdapterReportWork.setNewData(datas);
            if (selectedReportData != null && selectedReportData.size() > 0) {
                List<WSProcedureStep> surplusDatas = surplus(datas);
                mAdapterReportWorkGrid.setNewData(surplusDatas);
            } else {
                mAdapterReportWorkGrid.setNewData(datas);
            }
            selectCount = 0;
            setConfirmBg();
        }

    }

    //获取剩余的工步
    private List<WSProcedureStep> surplus(List<WSProcedureStep> datas) {
        List<WSProcedureStep> surplusDatas = new ArrayList<>();
        for (int i = 0; i < datas.size(); i++) {
            WSProcedureStep wsProcedureStep = datas.get(i);
            if (selectedReportData.contains(wsProcedureStep)) {
            } else {
                surplusDatas.add(wsProcedureStep);
            }
        }
        return surplusDatas;
    }

    @Override
    public void showStepHistoryList(List<WSDailyWorkHistory> datas) {
        if (datas != null) {
            if (datas.size() > 0) {
                showHistoryReportDialog(datas);
            } else {
                if (!isSynStatus()) {
                    ToastUtil.showInfoCenterShort("暂无报工记录");
                }
            }
        }
//        if (datas != null) {
//            mAdapterReportWorkHistory.setNewData(datas);
//            tvHistoryTitle.setText("历史报工记录(共" + datas.size() + "条)");
//        } else {
//            tvHistoryTitle.setText("历史报工记录(共0条)");
//        }
    }

    private void showHistoryReportDialog(List<WSDailyWorkHistory> datas) {
        if (wsReportHistoryDialog == null) {
            wsReportHistoryDialog = WSDialogUtils.showHistoryReportDialog(mActivity, "", new WSDialogCallBackTwo() {
                @Override
                public void OnPositiveClick(Object obj) {

                }

                @Override
                public void OnNegativeClick() {

                }
            });
        }
        wsReportHistoryDialog.setData(datas);
        wsReportHistoryDialog.show();
    }

    @Override
    public void showReportWorkResult(boolean result) {
        if (result) {
            ToastUtil.showInfoCenterShort("报工成功");
            if (stepdatas.size() <= selectedReportData.size()) {
                //已全部报工 退出界面
                clickBack();
            } else {
                showStepList(stepdatas);
            }
//            requesStepList();


//            requesStepHistoryList();
            //刷新界面
//            refreshSelected();
        }
    }

    private void requesStepList() {
        Map<String, String> params = new HashMap<>();
        params.put("procedureId", procedureId);
        params.put("taskId", taskId);
        currentPresent.requesStepList(params);
    }

    @Override
    public void closeCurrent() {
        clickBack();
    }

    @Override
    public void initData() {
//        requesStepList();
//        requesStepHistoryList();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_report_work;
    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    protected void synCurrentFragment(int refreshType) {
        super.synCurrentFragment(refreshType);
        if (getRefreshaType() == refreshType) {
            if (wsReportHistoryDialog != null && wsReportHistoryDialog.isShowing()) {
                requesStepHistoryList(false);
            }
        }

    }

    private int getRefreshaType() {
        return MyServer.ACTION_TASK_REPORT_WORK;
    }
}
