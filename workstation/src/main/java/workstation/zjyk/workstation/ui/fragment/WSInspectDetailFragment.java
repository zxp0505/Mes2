package workstation.zjyk.workstation.ui.fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jakewharton.rxbinding2.view.RxView;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.com.ethank.mylibrary.resourcelibrary.core.coding.MD5Util;
import cn.com.ethank.mylibrary.resourcelibrary.intent.StartIntentUtils;
import cn.com.ethank.mylibrary.resourcelibrary.toast.ToastUtil;
import cn.com.ethank.mylibrary.resourcelibrary.utils.SDCardHelper;
import io.reactivex.functions.Consumer;
import me.yokeyword.fragmentation.ISupportFragment;
import me.yokeyword.fragmentation.SupportFragment;
import workstation.zjyk.workstation.R;
import workstation.zjyk.workstation.modle.bean.WSAccessoryAddress;
import workstation.zjyk.workstation.modle.bean.WSBeginOrEnd;
import workstation.zjyk.workstation.modle.bean.WSFollowedBean;
import workstation.zjyk.workstation.modle.bean.WSInputInfo;
import workstation.zjyk.workstation.modle.bean.WSLabelBean;
import workstation.zjyk.workstation.modle.bean.WSMaterialTray;
import workstation.zjyk.workstation.modle.bean.WSOrderCheckVo;
import workstation.zjyk.workstation.modle.bean.WSProcedureStep;
import workstation.zjyk.workstation.modle.bean.WSReworkTrayInfo;
import workstation.zjyk.workstation.modle.bean.WSTaskDetailItemBean;
import workstation.zjyk.workstation.modle.bean.WSTaskMainInfo;
import workstation.zjyk.workstation.modle.bean.WSTaskOtherInfo;
import workstation.zjyk.workstation.modle.bean.WSTaskProductCheckTray;
import workstation.zjyk.workstation.modle.bean.WSTrayLoadInfo;
import workstation.zjyk.workstation.modle.bean.WSTrayVo;
import workstation.zjyk.workstation.modle.bean.WSWipTray;
import workstation.zjyk.workstation.modle.bean.WSWorkStationOutVO;
import workstation.zjyk.workstation.modle.bean.WSWorkStationTask;
import workstation.zjyk.workstation.modle.bean.enumpackage.WSBeginOrEndEnum;
import workstation.zjyk.workstation.modle.bean.enumpackage.WSTaskTypeEnum;
import workstation.zjyk.workstation.modle.bean.enumpackage.WSWorkConditionStatusEnum;
import workstation.zjyk.workstation.modle.bean.enumpackage.WSYesOrNoEnum;
import workstation.zjyk.workstation.ui.WSMainActivity;
import workstation.zjyk.workstation.ui.activity.WSBomDtailListDialogActivity;
import workstation.zjyk.workstation.ui.activity.WSPdfReadDialogActivity;
import workstation.zjyk.workstation.ui.adapter.WSAdapterTaskDetailPopu;
import workstation.zjyk.workstation.ui.pop.WSEnclosureCallBack;
import workstation.zjyk.workstation.ui.pop.WSEnclosurePopu;
import workstation.zjyk.workstation.ui.pop.WSTaskDetailPopu;
import workstation.zjyk.workstation.ui.present.WSTaskPresent;
import workstation.zjyk.workstation.ui.views.WSTaskView;
import workstation.zjyk.workstation.util.dialog.WSDialogUtils;
import workstation.zjyk.workstation.util.dialog.WSLookEnclosureDialog;
import workstation.zjyk.workstation.util.dialog.WSLookTrayListVpDialog;
import workstation.zjyk.workstation.util.dialog.WSPushTwoDialog;
import workstation.zjyk.workstation.util.dialog.WSRepairDialog;
import workstation.zjyk.workstation.util.dialog.WSReviewWorkDialog;
import workstation.zjyk.workstation.util.dialog.WSReviewWorkScanTrayDialog;

/**
 * 巡检详情
 * Created by zjgz on 2017/12/8.
 */

public class WSInspectDetailFragment extends WSBaseFragment<WSTaskPresent> implements WSTaskView {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_guide)
    TextView tvGuide;
    @BindView(R.id.tv_bom)
    TextView tvBom;
    @BindView(R.id.iv_back_one)
    ImageView ivBackOne;
    @BindView(R.id.recycyleview)
    RecyclerView recycyleview;
    @BindView(R.id.tv_inspect_status)
    TextView tvInspectStatus;
    Unbinder unbinder1;
    private WSTaskDetailPopu mTaskDetailPopu;
    private WSReviewWorkScanTrayDialog mReviewWorkScanTrayDialog;
    private WSReviewWorkDialog wsReviewWorkDialog;
    private WSRepairDialog wsRepairDialog;
    private WSBeginOrEndEnum currentEnumstatus;//当前枚举状态 主要用作开始暂停
    private WSTaskTypeEnum wsTaskTypeEnum = WSTaskTypeEnum.COMMON;//默认是普通任务
    private String taskId;
    private String procedureId;//当前工序
    private RecyclerView recycyleEntryMaterailVp;
    private RecyclerView recycyleMakingVp;
    private WSLookTrayListVpDialog wsLookTrayListVpDialog;
    private int predictCount;//预计可输出
    private WSWorkConditionStatusEnum workConditionStatus;//工作条件  暂时不考虑未就绪状态
    private List<WSAccessoryAddress> accessoryAddresslist;
    private WSEnclosurePopu wsEnclosurePopu;
    private WSAccessoryAddress currentAccessoryAddress;
    private int popupWidth;
    private int popupHeight;
    private WSLookEnclosureDialog mEnclosureDialog;
    private int outputCount;//正常输出数量
    private WSPushTwoDialog mPushTwoDialog;
    private static final int WRAN_WRITE = 1;
    private static final int WRAN_RED = 2;
    private static final int CHANGE_TIMER = 1000;

    private String productId;
    private int canCheckOut;
    private String description;//备注
    private Unbinder unbinder;
    private WSAdapterTaskDetailPopu wsAdapterTaskDetailPopu;
    private WSYesOrNoEnum checkStatus;
    private WSOrderCheckVo wsWorkStationTask;


    public static WSInspectDetailFragment newInstance(WSOrderCheckVo wsWorkStationTask) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(dataKey, wsWorkStationTask);
        WSInspectDetailFragment wsTaskFragment = new WSInspectDetailFragment();
        wsTaskFragment.setArguments(bundle);
        return wsTaskFragment;
    }

    @Override
    protected void creatPresent() {
        currentPresent = new WSTaskPresent();
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        unbinder = ButterKnife.bind(this, view);
        Bundle arguments = getArguments();
        wsWorkStationTask = arguments.getParcelable(dataKey);
//        if (wsWorkStationTask != null) {
//            if (wsWorkStationTask.getTaskType() != null) {
//                wsTaskTypeEnum = wsWorkStationTask.getTaskType();
//            }
//            workConditionStatus = wsWorkStationTask.getWorkConditionStatus();
//            taskId = wsWorkStationTask.getId();
//            description = wsWorkStationTask.getDescription();
//
//        }
        tvTitle.setText("详情");
        recycyleview.setLayoutManager(new LinearLayoutManager(mActivity));
        wsAdapterTaskDetailPopu = new WSAdapterTaskDetailPopu();
        recycyleview.setAdapter(wsAdapterTaskDetailPopu);
        if (wsWorkStationTask != null) {

            setMainData(wsWorkStationTask);
            checkStatus = wsWorkStationTask.getCheckStatus();
            if (checkStatus == WSYesOrNoEnum.YES) {
                tvInspectStatus.setText("已巡检");
                tvInspectStatus.setBackgroundResource(R.drawable.shape_task_bg_unnormal);
            } else {
                tvInspectStatus.setText("巡检");
                tvInspectStatus.setBackgroundResource(R.drawable.shape_report_work_select);
            }
        }
        RxView.clicks(tvBom).throttleFirst(1500, TimeUnit.MILLISECONDS).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                getBomInfo();
            }
        });
        RxView.clicks(tvGuide).throttleFirst(500, TimeUnit.MILLISECONDS).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                if (accessoryAddresslist != null && accessoryAddresslist.size() > 0) {
                    showEnclosureDialog();
                } else {
                    ToastUtil.showInfoCenterShort("暂无可查看的附件");
                }
            }
        });

    }


    @Override
    public void initData() {
        getTaskMainInfo(true);
        getTaskOtherInfo(true);
    }


    private void refreshData() {
        getTaskMainInfo(false);
        getTaskOtherInfo(false);
    }

    private void getTaskMainInfo(boolean isShowLoading) {
        Map<String, String> params = new HashMap<>();
        params.put("taskId", taskId);
//        currentPresent.requestTaskMainInfo(params, isShowLoading);
    }

    public void getFollowData(boolean isShowLoading) {
        Map<String, String> params = new HashMap<>();
        params.put("productId", productId);
        params.put("procedureId", procedureId);
//        currentPresent.getFollowData(params, isShowLoading);
    }

    private void getTaskOtherInfo(boolean isShowLoading) {
        Map<String, String> params = new HashMap<>();
        params.put("taskId", taskId);
//        currentPresent.requestTaskOtherInfo(params, isShowLoading);
    }


    private void getTrayMaterailList() {
        Map<String, String> params = new HashMap<>();
        params.put("taskId", taskId);
        currentPresent.requestTrayMaterailList(params);
    }


    private void getTrayMakingList() {
        Map<String, String> params = new HashMap<>();
        params.put("taskId", taskId);
        currentPresent.requestTrayMakingList(params);
    }

    private void getMaterailTrayList() {
        Map<String, String> params = new HashMap<>();
        params.put("taskId", taskId);
        currentPresent.requestMaterailTrayList(params);
    }

    private void getMakingTrayList() {
        Map<String, String> params = new HashMap<>();
        params.put("taskId", taskId);
        currentPresent.requestMakingTrayList(params);
    }

    private void getBomInfo() {
        Map<String, String> params = new HashMap<>();
        params.put("taskId", taskId);
        currentPresent.requestBomInfo(params);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_inspect_detail;
    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        // TODO: inflate a fragment view
//        View rootView = super.onCreateView(inflater, container, savedInstanceState);
//        unbinder1 = ButterKnife.bind(this, rootView);
//        return rootView;
//    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (getDetailPopu() != null && getDetailPopu().isShowing()) {
            getDetailPopu().dismiss();
        }
        if (getEnClosurePopu() != null && getEnClosurePopu().isShowing()) {
            getEnClosurePopu().dismiss();
        }
        unbinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    private WSTaskDetailPopu getDetailPopu() {
        if (mTaskDetailPopu == null) {
            mTaskDetailPopu = new WSTaskDetailPopu(getActivity());
            mTaskDetailPopu.addViewTreeObserver();
        }
        if (wsTaskMainInfo != null) {
            mTaskDetailPopu.setData(wsTaskMainInfo);
        }
        return mTaskDetailPopu;
    }

    //产品附件popu
    private WSEnclosurePopu getEnClosurePopu() {
        if (wsEnclosurePopu == null) {
            wsEnclosurePopu = new WSEnclosurePopu(mActivity, new WSEnclosureCallBack() {
                @Override
                public void selectIten(WSAccessoryAddress wsAccessoryAddress) {
                    currentAccessoryAddress = wsAccessoryAddress;
                    if (currentAccessoryAddress != null) {
                        downPdf(currentAccessoryAddress);
                    }
                }
            });
            wsEnclosurePopu.addViewTreeObserver();
        }
        if (accessoryAddresslist != null) {
            wsEnclosurePopu.setData(accessoryAddresslist);
        }

        return wsEnclosurePopu;
    }

    private void showEnclosureDialog() {
        if (mEnclosureDialog == null) {
            mEnclosureDialog = WSDialogUtils.showLookEnclosureDialog(mActivity, "产品附件", new WSEnclosureCallBack() {
                @Override
                public void selectIten(WSAccessoryAddress wsAccessoryAddress) {
                    currentAccessoryAddress = wsAccessoryAddress;
                    if (currentAccessoryAddress != null) {
                        downPdf(currentAccessoryAddress);
                    }
                }
            });
        }
        if (accessoryAddresslist != null) {
            mEnclosureDialog.setData(accessoryAddresslist);
        }
        mEnclosureDialog.show();
    }


    private WSReviewWorkFragment getReviewWorkFragment() {
        WSReviewWorkFragment reviewWorkFragment = findFragment(WSReviewWorkFragment.class);
        return reviewWorkFragment;
    }


    private void downPdf(WSAccessoryAddress currentAccessoryAddress) {
        //查看文件存在不
        //文件存在 根据文件的md5来判断文件是否是 同一个
        //是源文件 根据文件地址展示 否则去下载
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            //
            RxPermissions rxPermissions = new RxPermissions(getActivity());
            rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(new Consumer<Boolean>() {
                @Override
                public void accept(Boolean aBoolean) throws Exception {
                    if (aBoolean) {
                        downPdfStart(currentAccessoryAddress);
                    } else {
                        ToastUtil.showInfoCenterShort("权限拒绝，无法下载");
                    }
                }
            });
        } else {
            downPdfStart(currentAccessoryAddress);
        }
    }

    private void downPdfStart(WSAccessoryAddress currentAccessoryAddress) {
        String processInstructionUrl = currentAccessoryAddress.getAddress();
        String accessoryType = currentAccessoryAddress.getAccessoryType();
        if (!TextUtils.isEmpty(processInstructionUrl)) {
//            String[] split = processInstructionUrl.split("/");
//            split[split.length - 1]
            String fileName = SDCardHelper.getSDCardPrivateFilesDir(getActivity(), null) + "/" + taskId + accessoryType;
            File file = new File(fileName);
            if (file.exists()) {
                try {
                    //查看文件是否是源文件
                    String md5ByFile = MD5Util.getMd5ByFile(file);
                    currentPresent.checkFileMd5(accessoryType, processInstructionUrl, taskId, md5ByFile, fileName);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    currentPresent.downLoadPdf(getActivity(), processInstructionUrl, fileName);
                }
            } else {
                currentPresent.downLoadPdf(getActivity(), processInstructionUrl, fileName);
            }
        } else {
            ToastUtil.showInfoCenterShort("未上传工艺指导书!");
        }
    }

    @Override
    public void showFileMd5CheckResult(boolean result, String fileName, String newDownUrl) {
        if (result) {
            //打开文件
            downPdfResult(true, fileName);
        } else {
            //下载文件
            if (!TextUtils.isEmpty(newDownUrl)) {
                currentAccessoryAddress.setAddress(newDownUrl);
//                processInstructionUrl = newDownUrl;
            }
            currentPresent.downLoadPdf(getActivity(), currentAccessoryAddress.getAddress(), fileName);
        }
    }

    @Override
    public void showRewrokTrayinfo(WSReworkTrayInfo wsReworkTrayInfo) {
        if (wsReworkTrayInfo != null) {
        }
    }

    @Override
    public void showRepairBindTrayResult(boolean result, WSWorkStationOutVO wsWorkStationOutVO) {
        if (result) {

        }
    }

    @Override
    public void showExceptionInfo(WSTrayLoadInfo trayLoadInfo) {
    }

    @Override
    public void toEndTaskOpition(boolean confirm, int currentStatus) {
        if (confirm) {
            if (currentStatus == 2) {
                //跳转报工界面
            }
        }
    }


    /**
     * 退出到任务详情界面
     */
    private void quitToTaskFragment() {
        ISupportFragment topFragment = getTopFragment();
        if (topFragment != null && !(topFragment instanceof WSInspectDetailFragment)) {
            ((SupportFragment) topFragment).popTo(WSInspectDetailFragment.class, false);
        }
    }

    private void quitRefresh() {
        Bundle bundle = new Bundle();
        bundle.putBoolean("isRefresh", true);
        setFragmentResult(0, bundle);
        pop();
    }


    WSTaskMainInfo wsTaskMainInfo = null;

    @Override
    public void showTaskInfo(WSTaskMainInfo wsTaskMainInfo) {
        if (wsTaskMainInfo != null) {
            this.wsTaskMainInfo = wsTaskMainInfo;
            accessoryAddresslist = wsTaskMainInfo.getAccessoryAddresslist();
//            setMainData(wsTaskMainInfo);
        }

    }

    private void setMainData(WSOrderCheckVo info) {
        String productNumber = info.getProductNumber();//生产序号
        if (!TextUtils.isEmpty(productNumber)) {
            addData("生产序号: ", productNumber);
        }
        String productModelTypeName = info.getOrderProductModelTypeName();//产品型号
        if (!TextUtils.isEmpty(productModelTypeName)) {
            addData("产品型号: ", productModelTypeName);
        }
        String procedureName = "";//工序名称
        if (!TextUtils.isEmpty(procedureName)) {
            addData("工序名称: ", procedureName);
        }
        String serialNumber = info.getSerialNumber();//订单生产编号
        if (!TextUtils.isEmpty(serialNumber)) {
            addData("订单生产编号: ", serialNumber);
        }
        String productModel = info.getOrderProductModel();//产品货号
        if (!TextUtils.isEmpty(productModel)) {
            addData("产品货号: ", productModel);
        }
        String productStartTime = info.getPlanDate();//投产时间
        if (!TextUtils.isEmpty(productStartTime)) {
            addData("投产时间: ", productStartTime);
        }
        String deliverTime = info.getDeliverTime();//交货时间
        if (!TextUtils.isEmpty(deliverTime)) {
            addData("交货时间: ", deliverTime);
        }
        String count = info.getCount();//生产数量
        addData("生产数量: ", count + "");
        if (!TextUtils.isEmpty(description)) {
            addData("备注: ", description);
        }
        String checkPersonName = info.getCheckPersonName();//巡检人
        if (!TextUtils.isEmpty(checkPersonName)) {
            addData("巡检人: ", checkPersonName);
        }
        String checkDate = info.getCheckDate();//巡检时间
        if (!TextUtils.isEmpty(checkDate)) {
            addData("巡检时间: ", checkDate);
        }

        String checkDescription = info.getCheckDescription();//巡检时间
        if (!TextUtils.isEmpty(checkDescription)) {
            addData("巡检备注: ", checkDescription);
        }


        wsAdapterTaskDetailPopu.setNewData(datas);
    }

    private void addData(String name, String detail) {
        WSTaskDetailItemBean bean = new WSTaskDetailItemBean();
        bean.setName(name);
        bean.setDetail(detail);
        datas.add(bean);
    }

    List<WSTaskDetailItemBean> datas = new ArrayList<>();

    @Override
    public void showFolloweData(List<WSFollowedBean> wsFollowedList) {
        if (wsFollowedList != null && wsFollowedList.size() > 0) {
            showFollowedData(wsFollowedList);
        } else {
            showNullFollowed();
        }
    }

    private void showNullFollowed() {
    }

    private void showFollowedData(List<WSFollowedBean> wsFollowedList) {
    }


    @Override
    public void showTaskOtherInfo(WSTaskOtherInfo wsTaskOtherInfo) {

    }

    @Override
    public void showMaterailAndMakingList(WSInputInfo inputInfo) {
    }

    @Override
    public void showTrayMaterailList(List<WSTrayLoadInfo> datas) {
    }

    @Override
    public void showTrayMakingList(List<WSTrayLoadInfo> datas) {
    }

    @Override
    public void showTrayList(List<WSTrayVo> trayList) {
    }

    @Override
    public void showMaterailTrayList(WSMaterialTray wsMaterialTray) {

    }

    @Override
    public void showMakingTrayList(WSWipTray wsWipTray) {
    }

    @Override
    public void showBomInfo(WSTrayLoadInfo info) {
        if (info != null) {
            Bundle bundle = new Bundle();
            bundle.putParcelable("data", info);
            bundle.putString("taskId", taskId);
            bundle.putString("productId", wsTaskMainInfo.getProductId());
            bundle.putString("procedureId", wsTaskMainInfo.getProcedureId());
            StartIntentUtils.startIntentUtilsFromResult(mActivity, WSBomDtailListDialogActivity.class, WSMainActivity.LOOK_BOM_DETAIL, bundle);
        }
    }


    @Override
    public void downPdfResult(boolean result, String filePath) {
        if (result) {
            Bundle bundle = new Bundle();
            bundle.putString("pdfPath", filePath);
            if (currentAccessoryAddress != null) {
                bundle.putString("titleName", currentAccessoryAddress.getAccessoryType());
            }
            dismissEnclosureDialog();
            StartIntentUtils.startIntentUtils(getActivity(), WSPdfReadDialogActivity.class, bundle);
        } else {
            ToastUtil.showInfoCenterShort("文件下载失败");
        }
    }

    private void dismissEnclosureDialog() {
        if (mEnclosureDialog != null && mEnclosureDialog.isShowing()) {
            mEnclosureDialog.dismiss();
        }
    }

    long currentTime;//当前累计时间

    @Override
    public void showBeginOrPauseResult(boolean result, int currentStatus, WSBeginOrEnd wsBeginOrEnd) {
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void showRewrokResult(boolean result) {
        if (result) {
        }
        refreshData();
    }

    @Override
    public void showRepairResult(boolean result) {
        if (result) {
        }
    }

    @Override
    public void showLabelTypes(List<WSLabelBean> result) {

    }

    @Override
    public void showPrintLabelResult(boolean result) {

    }

    @Override
    public void showStepList(List<WSProcedureStep> datas) {
        if (datas != null && datas.size() > 0) {
        }

    }

    @Override
    public void setActivityLogoOrBack() {
        if (mActivity != null && mActivity instanceof WSMainActivity) {
            ((WSMainActivity) mActivity).setActivityLogoOrBack(1);
        }
    }

    @Override
    public void setFragmentBack() {
        super.setFragmentBack();
    }

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
    }

    @Override
    protected void clickBack() {
        quitRefresh();
    }

    @Override
    public void setScanResult(String scanResult) {
        super.setScanResult(scanResult);
        WSTaskListFragment fragment = findFragment(WSTaskListFragment.class);
        if (fragment != null) {
            fragment.setScanResult(scanResult);
        }
    }

    @Override
    public void showInspebctionList(List<WSTaskProductCheckTray> datas) {
    }

    @Override
    public void toInspectionResult(boolean result) {
    }

    @Override
    public void toMakeSureInspectResult(boolean result) {
        if (result) {
            quitRefresh();
        }

    }


    private void toInspection(String data) {
        Map<String, String> params = new HashMap<>();
        params.put("checkProduct", data);
        currentPresent.toInspection(params, true);
    }

    private void checkInspectionCode(String code) {
        Map<String, String> params = new HashMap<>();
        params.put("code", code);
        currentPresent.checkInspectionCode(params, true);
    }

    @Override
    public void checkInspectionResult(boolean result, String code) {
    }

    @Override
    public void showWarnResult(boolean result) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder1 = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @OnClick({R.id.iv_back_one, R.id.tv_inspect_status})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back_one:
                pop();
                break;
            case R.id.tv_inspect_status:
                if (checkStatus != null && checkStatus == WSYesOrNoEnum.NO) {
                    //确认巡检
                    toMakeSureInspect();
                }
                break;
        }
    }

    private void toMakeSureInspect() {
        if (wsWorkStationTask != null) {
            Map<String, String> params = new HashMap<>();
            params.put("orderCheckId", wsWorkStationTask.getOrderCheckId());
            currentPresent.toMakeSureInspect(params, true);
        }

    }
}
