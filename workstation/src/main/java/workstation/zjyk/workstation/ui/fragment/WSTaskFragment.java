package workstation.zjyk.workstation.ui.fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
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
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.jakewharton.rxbinding2.view.RxView;
import com.marquee.dingrui.marqueeviewlib.MarqueeView;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.wang.avi.AVLoadingIndicatorView;

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
import cn.com.ethank.mylibrary.resourcelibrary.common_util.JsonUtil;
import cn.com.ethank.mylibrary.resourcelibrary.core.coding.MD5Util;
import cn.com.ethank.mylibrary.resourcelibrary.intent.StartIntentUtils;
import cn.com.ethank.mylibrary.resourcelibrary.server.MessageEventBean;
import cn.com.ethank.mylibrary.resourcelibrary.server.MyServer;
import cn.com.ethank.mylibrary.resourcelibrary.toast.ToastUtil;
import cn.com.ethank.mylibrary.resourcelibrary.utils.SDCardHelper;
import cn.com.ethank.mylibrary.resourcelibrary.utils.TimerUtils;
import io.reactivex.functions.Consumer;
import me.yokeyword.fragmentation.ISupportFragment;
import me.yokeyword.fragmentation.SupportFragment;
import workstation.zjyk.workstation.R;
import workstation.zjyk.workstation.modle.bean.WSAccessoryAddress;
import workstation.zjyk.workstation.modle.bean.WSBeginOrEnd;
import workstation.zjyk.workstation.modle.bean.WSFollowedBean;
import workstation.zjyk.workstation.modle.bean.WSInputInfo;
import workstation.zjyk.workstation.modle.bean.WSInputMaterialInfo;
import workstation.zjyk.workstation.modle.bean.WSInputWipInfo;
import workstation.zjyk.workstation.modle.bean.WSLabelBean;
import workstation.zjyk.workstation.modle.bean.WSLevel0Item;
import workstation.zjyk.workstation.modle.bean.WSLevel1Item;
import workstation.zjyk.workstation.modle.bean.WSMaterial;
import workstation.zjyk.workstation.modle.bean.WSMaterialTray;
import workstation.zjyk.workstation.modle.bean.WSPrintlabelVo;
import workstation.zjyk.workstation.modle.bean.WSProcedureStep;
import workstation.zjyk.workstation.modle.bean.WSReworkTrayInfo;
import workstation.zjyk.workstation.modle.bean.WSTaskMainInfo;
import workstation.zjyk.workstation.modle.bean.WSTaskOtherInfo;
import workstation.zjyk.workstation.modle.bean.WSTaskProductCheckTray;
import workstation.zjyk.workstation.modle.bean.WSTrayLoadInfo;
import workstation.zjyk.workstation.modle.bean.WSTrayVo;
import workstation.zjyk.workstation.modle.bean.WSWip;
import workstation.zjyk.workstation.modle.bean.WSWipTray;
import workstation.zjyk.workstation.modle.bean.WSWorkStationOutVO;
import workstation.zjyk.workstation.modle.bean.WSWorkStationTask;
import workstation.zjyk.workstation.modle.bean.enumpackage.WSBeginOrEndEnum;
import workstation.zjyk.workstation.modle.bean.enumpackage.WSTaskTypeEnum;
import workstation.zjyk.workstation.modle.bean.enumpackage.WSTrayLoadTypeEnum;
import workstation.zjyk.workstation.modle.bean.enumpackage.WSWorkConditionStatusEnum;
import workstation.zjyk.workstation.modle.bean.enumpackage.WSYesOrNoEnum;
import workstation.zjyk.workstation.ui.WSMainActivity;
import workstation.zjyk.workstation.ui.activity.WSBomDtailListDialogActivity;
import workstation.zjyk.workstation.ui.activity.WSPdfReadDialogActivity;
import workstation.zjyk.workstation.ui.adapter.WSAdapterBomMaterailFollow;
import workstation.zjyk.workstation.ui.adapter.WSAdapterTaskEntry;
import workstation.zjyk.workstation.ui.adapter.WSAdapterTaskMaking;
import workstation.zjyk.workstation.ui.adapter.WSTaskAdapter;
import workstation.zjyk.workstation.ui.manager.WSUserManager;
import workstation.zjyk.workstation.ui.pop.WSEnclosureCallBack;
import workstation.zjyk.workstation.ui.pop.WSEnclosurePopu;
import workstation.zjyk.workstation.ui.pop.WSTaskDetailPopu;
import workstation.zjyk.workstation.ui.present.WSTaskPresent;
import workstation.zjyk.workstation.ui.views.WSTaskView;
import workstation.zjyk.workstation.util.dialog.WSDialogUtils;
import workstation.zjyk.workstation.util.dialog.WSLabelTempletDialog;
import workstation.zjyk.workstation.util.dialog.WSLabelTypesDialog;
import workstation.zjyk.workstation.util.dialog.WSLookEnclosureDialog;
import workstation.zjyk.workstation.util.dialog.WSLookMaterailTrayDialog;
import workstation.zjyk.workstation.util.dialog.WSLookTrayListDialog;
import workstation.zjyk.workstation.util.dialog.WSLookTrayListMaterailDialog;
import workstation.zjyk.workstation.util.dialog.WSLookTrayListVpDialog;
import workstation.zjyk.workstation.util.dialog.WSOutPutInsoectionDialog;
import workstation.zjyk.workstation.util.dialog.WSPushTwoDialog;
import workstation.zjyk.workstation.util.dialog.WSRepairDialog;
import workstation.zjyk.workstation.util.dialog.WSReviewWorkDialog;
import workstation.zjyk.workstation.util.dialog.WSReviewWorkScanTrayDialog;
import workstation.zjyk.workstation.util.dialog.WSReworkDialog;
import workstation.zjyk.workstation.util.dialog.callback.WSDialogCallBackThree;
import workstation.zjyk.workstation.util.dialog.callback.WSDialogCallBackTwo;

/**
 * Created by zjgz on 2017/12/8.
 */

public class WSTaskFragment extends WSBaseFragment<WSTaskPresent> implements WSTaskView {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_product_number)
    TextView tvProductNumber;
    @BindView(R.id.tv_model)
    TextView tvModel;
    @BindView(R.id.tv_produce)
    TextView tvProduce;
    @BindView(R.id.rl_title_two)
    RelativeLayout rlTitleTwo;
    Unbinder unbinder;
    @BindView(R.id.tv_cancle)
    TextView tvCancle;
    Unbinder unbinder1;
    @BindView(R.id.recycyle_entry_materail)
    RecyclerView recycyleEntryMaterail;
    @BindView(R.id.recycyle_making)
    RecyclerView recycyleMaking;
    @BindView(R.id.ll_left)
    LinearLayout llLeft;
    @BindView(R.id.ll_right)
    LinearLayout llRight;
    @BindView(R.id.tv_guide)
    TextView tvGuide;
    @BindView(R.id.tv_bom)
    TextView tvBom;
    @BindView(R.id.tv_targt)
    TextView tvTargt;
    @BindView(R.id.tv_output_ok)
    TextView tvOutputOk;
    @BindView(R.id.tv_except)
    TextView tvExcept;
    @BindView(R.id.tv_output)
    TextView tvOutput;
    @BindView(R.id.tv_start_pause)
    TextView tvStartPause;
    @BindView(R.id.tv_report_wrok)
    TextView tvReportWrok;
    @BindView(R.id.iv_work_status)
    ImageView ivWorkStatus;
    @BindView(R.id.ll_start_pause)
    LinearLayout llStartPause;
    @BindView(R.id.rl_title_one)
    RelativeLayout rlTitleOne;
    @BindView(R.id.iv_back_two)
    ImageView ivbacktwo;
    //    @BindView(R.id.tv_detail)
//    TextView tvDetail;
    @BindView(R.id.iv_back_three)
    ImageView ivBackThree;
    @BindView(R.id.tv_repair)
    TextView tvRepair;
    @BindView(R.id.tv_rework)
    TextView tvRework;
    @BindView(R.id.tv_title_left)
    TextView tvTitleLeft;
    @BindView(R.id.tv_plan)
    TextView tvPlan;
    @BindView(R.id.tv_time_count)
    TextView tvTimeCount;
    @BindView(R.id.start_pause_loading)
    AVLoadingIndicatorView startPauseLoading;
    @BindView(R.id.tv_hand_barcode_two)
    TextView tvHandBarcodeTwo;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.tv_vp_name)
    TextView tvVpName;
    @BindView(R.id.iv_look_making_tray_info_vp)
    ImageView ivLookMakingTrayInfoVp;
    @BindView(R.id.tv_hand_barcode_two_two)
    TextView tvHandBarcodeTwoTwo;
    @BindView(R.id.iv_back_four)
    ImageView ivBackFour;
    @BindView(R.id.tv_product_number_four)
    TextView tvProductNumberFour;
    @BindView(R.id.tv_model_four)
    TextView tvModelFour;
    @BindView(R.id.iv_title_line_bg_four)
    ImageView ivTitleLineBgFour;
    @BindView(R.id.tv_detail_four)
    TextView tvDetailFour;
    @BindView(R.id.rl_title_four)
    RelativeLayout rlTitleFour;
    @BindView(R.id.avi_time)
    AVLoadingIndicatorView aviTime;
    @BindView(R.id.rl_title_three)
    RelativeLayout rlTitleThree;
    @BindView(R.id.rl_root)
    RelativeLayout rlRoot;
    Unbinder unbinder2;
    @BindView(R.id.tv_inspection_four)
    TextView tvInspectionFour;
    @BindView(R.id.tv_mark)
    MarqueeView tvMark;
    @BindView(R.id.iv_top)
    ImageView ivTop;
    @BindView(R.id.iv_urgent)
    ImageView ivUrgent;
    @BindView(R.id.tv_print_label)
    TextView tvPrintLabel;
    private WSAdapterTaskEntry mAdapterTaskEntry;
    private WSAdapterTaskMaking mAdapterTaskMaking;
    //    private String processInstructionUrl = "";//工艺指导书的地址
    int currentStatus = 0; //0 :未开始 1：已开始 可暂停 2：已结束 3:已完成（待结束状态） 计划==输出
    private WSLookTrayListMaterailDialog wsLookTrayListMaterailDialog;
    private WSLookTrayListDialog wsLookTrayListDialog;
    private WSLookMaterailTrayDialog wsLookMaterailTrayDialog;
    private WSLookMaterailTrayDialog wsLookMaterailTrayDialogTwo;
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

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case WRAN_WRITE:
                    setWranWrite();
                    handler.sendEmptyMessageDelayed(WRAN_RED, CHANGE_TIMER);
                    break;
                case WRAN_RED:
                    setWranRed();
                    handler.sendEmptyMessageDelayed(WRAN_WRITE, CHANGE_TIMER);
                    break;
            }
        }
    };
    private RecyclerView recycleFollowVp;
    private WSAdapterBomMaterailFollow mAdapterBomMaterailFollow;
    private TextView tvUnFollow;
    private String productId;
    private String procedureId1;
    private WSOutPutInsoectionDialog mOutPutInsoectionDialog;
    private String checkTag;
    private WSReworkDialog mReworkDialog;
    private int canCheckOut;
    private String description;//备注
    private WSLabelTypesDialog mLabelTypesDialog;
    private WSLabelTempletDialog mLabelTempletDialog;

    private void setWranWrite() {
        GradientDrawable gradientDrawable = (GradientDrawable) rlRoot.getBackground();
        gradientDrawable.setColor(getContext().getResources().getColor(R.color.white_full));
    }

    private void setWranRed() {
        GradientDrawable gradientDrawable = (GradientDrawable) rlRoot.getBackground();
        gradientDrawable.setColor(getContext().getResources().getColor(R.color.red));
    }

    public void startWran() {
        handler.sendEmptyMessage(WRAN_RED);
    }

    public static WSTaskFragment newInstance(WSWorkStationTask wsWorkStationTask) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(dataKey, wsWorkStationTask);
        WSTaskFragment wsTaskFragment = new WSTaskFragment();
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
        WSWorkStationTask wsWorkStationTask = arguments.getParcelable(dataKey);
        if (wsWorkStationTask != null) {
            if (wsWorkStationTask.getTaskType() != null) {
                wsTaskTypeEnum = wsWorkStationTask.getTaskType();
            }
            workConditionStatus = wsWorkStationTask.getWorkConditionStatus();
            taskId = wsWorkStationTask.getId();
            description = wsWorkStationTask.getDescription();
            if (!TextUtils.isEmpty(description)) {
                tvMark.setVisibility(View.VISIBLE);
                tvMark.setContent(description);
                float scale = this.getResources().getDisplayMetrics().density;
                if (scale <= 1) {
                    tvMark.setTextSize(32);
                }
            } else {
                tvMark.setVisibility(View.GONE);
            }

        }
        setIvTitleLineBgFour();
        rlTitleOne.setVisibility(View.GONE);
        rlTitleTwo.setVisibility(View.GONE);
        rlTitleThree.setVisibility(View.GONE);
        rlTitleFour.setVisibility(View.VISIBLE);
//        tvHandBarcodeTwoTwo.setVisibility(View.VISIBLE);
//        tvHandBarcodeTwoTwo.setOnClickListener(this);
        ivbacktwo.setOnClickListener(this);
        ivBackFour.setOnClickListener(this);
        currentStatus = 0;
        startPauseLoading.setVisibility(View.INVISIBLE);
//        startPauseLoading.show();
//        setWorkStatus();
        setDefaultTag();
        hideStartPauseLoading();
        tvHandBarcodeTwo.setVisibility(View.GONE);
        tvTimeCount.setVisibility(View.INVISIBLE);
        initViewPager();
//        mAdapterTaskEntry = new WSAdapterTaskEntry();
//        recycyleEntryMaterail.setLayoutManager(new LinearLayoutManager(getActivity()));
//        recycyleEntryMaterail.setAdapter(mAdapterTaskEntry);
//
//        mAdapterTaskMaking = new WSAdapterTaskMaking();
//        recycyleMaking.setLayoutManager(new LinearLayoutManager(getActivity()));
//        recycyleMaking.setAdapter(mAdapterTaskMaking);

        if (wsWorkStationTask.getIsTop() == 1) {
            ivTop.setVisibility(View.VISIBLE);
        } else {
            ivTop.setVisibility(View.GONE);
        }
        if (wsWorkStationTask.getWorryDistribute() != null && wsWorkStationTask.getWorryDistribute() == WSYesOrNoEnum.YES) {
            ivUrgent.setVisibility(View.VISIBLE);
        } else {
            ivUrgent.setVisibility(View.GONE);
        }

        mAdapterTaskEntry.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.iv_look_tray_info_item:
//                        getMaterailTrayList();
                        break;

                }
            }
        });
        mAdapterTaskMaking.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.iv_look_tray_info_item:
//                        getMakingTrayList();
                        break;

                }
            }
        });
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
//                    int[] locations = new int[2];
//                    tvGuide.getLocationInWindow(locations);
//                    WSEnclosurePopu enClosurePopu = getEnClosurePopu();
//                    popupWidth = enClosurePopu.getmPopupWindow().getContentView().getMeasuredWidth();
//                    popupHeight = enClosurePopu.getmPopupWindow().getContentView().getMeasuredHeight();
//                    enClosurePopu.showPopuWindowAtlocation(tvGuide, locations[0] + tvGuide.getMeasuredWidth() - popupWidth, locations[1] - popupHeight, true);
                } else {
                    ToastUtil.showInfoCenterShort("暂无可查看的附件");
                }
            }
        });

    }

    private void setIvTitleLineBgFour() {
        if (wsTaskTypeEnum != null) {
            switch (wsTaskTypeEnum) {
                case COMMON:
                    ivTitleLineBgFour.setBackgroundColor(getResources().getColor(R.color.task_normal));
                    break;
                case FQA_RETURN:
                case REWORK:
                    ivTitleLineBgFour.setBackgroundColor(getResources().getColor(R.color.task_rework));
                    break;
                case REPAIR_HELP:
                    ivTitleLineBgFour.setBackgroundColor(getResources().getColor(R.color.task_assis));
                    break;
                case HISTORY_TASK:
                    ivTitleLineBgFour.setBackgroundColor(getResources().getColor(R.color.task_history));
                    break;
            }
        }
    }


    private void initViewPager() {
        tvVpName.setText("关注物料");
        List<View> viewList = new ArrayList<>();
        View view_materail = LayoutInflater.from(getActivity()).inflate(R.layout.layout_vp_materail, null);
        View view_making = LayoutInflater.from(getActivity()).inflate(R.layout.layout_vp_making, null);
        View view_follow = LayoutInflater.from(getActivity()).inflate(R.layout.layout_vp_follow, null);
        recycyleEntryMaterailVp = view_materail.findViewById(R.id.recycyle_entry_materail_vp);
        recycyleMakingVp = view_making.findViewById(R.id.recycyle_making_vp);
        recycleFollowVp = view_follow.findViewById(R.id.recycyle_entry_follow_vp);
        tvUnFollow = view_follow.findViewById(R.id.tv_un_follow);

        mAdapterTaskEntry = new WSAdapterTaskEntry();
        recycyleEntryMaterailVp.setLayoutManager(new LinearLayoutManager(getActivity()));
        recycyleEntryMaterailVp.setAdapter(mAdapterTaskEntry);


        mAdapterTaskMaking = new WSAdapterTaskMaking();
        recycyleMakingVp.setLayoutManager(new LinearLayoutManager(getActivity()));
        recycyleMakingVp.setAdapter(mAdapterTaskMaking);

        mAdapterBomMaterailFollow = new WSAdapterBomMaterailFollow();
        recycleFollowVp.setLayoutManager(new LinearLayoutManager(getActivity()));
        recycleFollowVp.setAdapter(mAdapterBomMaterailFollow);

        // dhc 工位滑屏 切换
        viewList.add(view_follow);
        viewList.add(view_materail);
        viewList.add(view_making);


        WSTaskAdapter wsTaskAdapter = new WSTaskAdapter(viewList);
        viewPager.setAdapter(wsTaskAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //dhc 滑屏
                switch (position) {
                    case 1:
                        tvVpName.setText("物料");
                        break;
                    case 2:
                        tvVpName.setText("在制品");
                        break;
                    case 0:
                        tvVpName.setText("关注物料");
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    private List<MultiItemEntity> creatTrayListData(List<WSTrayLoadInfo> data) {
        List<MultiItemEntity> datas = new ArrayList<>();
        int leve0 = data.size();
        int leve1 = 0;
        for (int k = 0; k < data.size(); k++) {

            WSTrayLoadInfo wsTrayLoadInfo = data.get(k);
            WSTrayLoadTypeEnum type = wsTrayLoadInfo.getType();
            if (type == WSTrayLoadTypeEnum.MATERIAL) {
                leve1 = wsTrayLoadInfo.getMaterialList().size();
            } else if (type == WSTrayLoadTypeEnum.WIP) {
                leve1 = wsTrayLoadInfo.getWipList().size();
            }
            if (wsTrayLoadInfo.getTray() == null) {
                return datas;
            }
            for (int i = 0; i < leve0; i++) {
                WSLevel0Item wsLevel0Item = new WSLevel0Item("托盘" + wsTrayLoadInfo.getTray().getOneDemensionCode(), "" + wsTrayLoadInfo.getTray().getCount());
                for (int j = 0; j < leve1; j++) {
                    WSLevel1Item wsLevel1Item = null;
                    if (type == WSTrayLoadTypeEnum.MATERIAL) {
                        WSMaterial wsMaterial = wsTrayLoadInfo.getMaterialList().get(j);
                        wsLevel1Item = new WSLevel1Item(wsMaterial.getName(), "" + wsMaterial.getCount(), wsMaterial.getModel());
                    } else if (type == WSTrayLoadTypeEnum.WIP) {
                        WSWip wsWip = wsTrayLoadInfo.getWipList().get(j);
                        wsLevel1Item = new WSLevel1Item(wsWip.getProcedureName(), "" + wsWip.getCount());
                    }
                    wsLevel0Item.addSubItem(wsLevel1Item);
                }
                datas.add(wsLevel0Item);
            }
        }
        return datas;
    }

    @Override
    public void initData() {
        getTaskMainInfo(true);
        getTaskOtherInfo(true);
        getTaskMaterailAndMakingList(true);
    }

    public boolean isSameOrder(String taskId) {
        if (!TextUtils.isEmpty(taskId) && taskId.equals(this.taskId)) {
            return true;
        }
        return false;
    }

    private void refreshData() {
        getTaskMainInfo(false);
        getTaskOtherInfo(false);
        getTaskMaterailAndMakingList(false);
    }

    private void getTaskMainInfo(boolean isShowLoading) {
        Map<String, String> params = new HashMap<>();
        params.put("taskId", taskId);
        currentPresent.requestTaskMainInfo(params, isShowLoading);
    }

    public void getFollowData(boolean isShowLoading) {
        Map<String, String> params = new HashMap<>();
        params.put("productId", productId);
        params.put("procedureId", procedureId);
        currentPresent.getFollowData(params, isShowLoading);
    }

    private void getTaskOtherInfo(boolean isShowLoading) {
        Map<String, String> params = new HashMap<>();
        params.put("taskId", taskId);
        currentPresent.requestTaskOtherInfo(params, isShowLoading);
    }

    private void getTaskMaterailAndMakingList(boolean isShowLoading) {
        Map<String, String> params = new HashMap<>();
        params.put("taskId", taskId);
        currentPresent.requestTaskMaterailAndMakingList(params, isShowLoading);
    }

    private void getTrayMaterailList() {
        Map<String, String> params = new HashMap<>();
        params.put("taskId", taskId);
        currentPresent.requestTrayMaterailList(params);
    }

    private void getTrayList() {
        Map<String, String> params = new HashMap<>();
        params.put("taskId", taskId);
        currentPresent.requestTrayList(params);
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
        return R.layout.fragment_task;
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
        handler.removeCallbacksAndMessages(null);
        handler = null;
        setWranWrite();
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
        closeDialog();
        super.onDestroy();
    }

    private void closeDialog() {
        if (wsLookTrayListMaterailDialog != null && wsLookTrayListMaterailDialog.isShowing()) {
            wsLookTrayListMaterailDialog.dismiss();
        }
        if (wsLookTrayListDialog != null && wsLookTrayListDialog.isShowing()) {
            wsLookTrayListDialog.dismiss();
        }
        if (wsLookMaterailTrayDialog != null && wsLookMaterailTrayDialog.isShowing()) {
            wsLookMaterailTrayDialog.dismiss();
        }
        if (wsLookMaterailTrayDialogTwo != null && wsLookMaterailTrayDialogTwo.isShowing()) {
            wsLookMaterailTrayDialogTwo.dismiss();
        }
    }

    private static final int EXCEPT_REQUEST_CODE = 0;
    private static final int OUTPUT_REQUEST_CODE = 1;
    private static final int REVIRE_REQUEST_CODE = 2;//返工
    private static final int REPAIR_REQUEST_CODE = 3;//维修
    private static final int REPORT_WORK_REQUEST_CODE = 4;//报工

    @OnClick({R.id.tv_print_label, R.id.tv_inspection_four, R.id.tv_detail_four, R.id.tv_cancle, R.id.iv_look_materail_tray_info, R.id.ll_output_ok, R.id.iv_look_making_tray_info, R.id.tv_except, R.id.tv_report_wrok, R.id.tv_detail, R.id.tv_rework, R.id.tv_repair, R.id.ll_start_pause, R.id.iv_look_making_tray_info_vp})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_cancle:
                pop();
                break;
            case R.id.iv_look_materail_tray_info:
                //展示托盘列表层级结构
                getTrayMaterailList();
                break;
            case R.id.iv_look_making_tray_info:
                getTrayMakingList();
                break;
            case R.id.ll_output_ok:
//                if (!checkWorkConditionStatusTwo()) {
//                    return;
//                }
//                || wsTaskTypeEnum == WSTaskTypeEnum.HISTORY_TASK
                if (wsTaskTypeEnum == WSTaskTypeEnum.FQA_RETURN) {
                    ToastUtil.showInfoCenterShort("当前任务禁止输出");
                    return;
                }
                if (currentStatus == 1 && getString(R.string.text_work_pause).equals(tvStartPause.getText()) || currentStatus == 3 || currentStatus == 2) {
                    //暂停时 不能输出      已完成状态可输出  用于报验 打印历史输出记录
                    Bundle bundle = new Bundle();
                    bundle.putString("taskId", taskId);
                    bundle.putInt("predictCount", predictCount);
                    bundle.putSerializable("taskType", wsTaskTypeEnum);
                    if (wsTaskMainInfo != null) {
                        bundle.putString("checkTag", wsTaskMainInfo.getCheckTag());
                    }
                    startForResult(WSMakingOutputFragment.newInstance(bundle), OUTPUT_REQUEST_CODE);
                } else {
                    checkCurrentStatus();
                }
                break;
            case R.id.tv_except:
                if (!checkWorkConditionStatus()) {
                    return;
                }
                boolean isclickable = (boolean) tvExcept.getTag();
                if (isclickable) {
                    requesExceptList();
                } else {
                    checkCurrentStatus();
                }
                break;
            case R.id.tv_report_wrok:
                if (!checkWorkConditionStatus()) {
                    return;
                }
                reportWork();
                break;
            case R.id.tv_detail_four:
            case R.id.tv_detail:
                //详情
                if (wsTaskMainInfo != null) {
//                    int dimensionPixelSize = getResources().getDimensionPixelSize(cn.com.ethank.mylibrary.R.dimen.x_design_425px);//
//                            getDetailPopu().showAsDropDown(tvDetailFour, -dimensionPixelSize, 0);
                    WSTaskDetailPopu detailPopu = getDetailPopu();
                    int[] locations = new int[2];
                    tvDetailFour.getLocationInWindow(locations);
                    int detailPopupWidth = detailPopu.getmPopupWindow().getContentView().getMeasuredWidth();
                    int detailPopupHeight = detailPopu.getmPopupWindow().getContentView().getMeasuredHeight();
                    detailPopu.showPopuWindowAtlocation(tvDetailFour, locations[0] + tvDetailFour.getMeasuredWidth() - detailPopupWidth, locations[1] + tvDetailFour.getMeasuredHeight(), true);
                }


                break;
            case R.id.tv_repair:
                //维修
                if (!checkWorkConditionStatus()) {
                    return;
                }
                boolean isrepaireclickable = (boolean) tvRepair.getTag();
                if (isrepaireclickable) {
//                    getRepairDialog().showThis();
                    if (predictCount > 0) {
                        Bundle bundle = new Bundle();
                        bundle.putString("taskId", taskId);
                        bundle.putInt("predictCount", predictCount);
                        startForResult(WSRepairFragment.newInstance(bundle), REPAIR_REQUEST_CODE);
                    } else {
                        ToastUtil.showInfoCenterShort("沒有可维修的在制品");
                    }
                } else {
                    checkCurrentStatus();
                }

                break;
            case R.id.tv_rework:
                //返工
                if (!checkWorkConditionStatus()) {
                    return;
                }

                boolean isreviewclickable = (boolean) tvRework.getTag();
                if (isreviewclickable) {
                    if (wsTaskTypeEnum == WSTaskTypeEnum.FQA_RETURN && !TextUtils.isEmpty(checkTag) && "yes".equals(checkTag)) {
                        showReworkDialog();
                    } else {
                        List<WSInputWipInfo> reworkData = getReworkData();
                        if (reworkData != null && reworkData.size() > 0) {
                            Bundle bundle1 = new Bundle();
                            bundle1.putString("taskId", taskId);
                            bundle1.putParcelableArrayList("wipData", (ArrayList<? extends Parcelable>) reworkData);
                            startForResult(WSReviewWorkFragment.newInstance(bundle1), REVIRE_REQUEST_CODE);
                        } else {
                            ToastUtil.showInfoCenterShort("沒有可返工的在制品");
                        }
                    }
                } else {
                    checkCurrentStatus();
                }

                break;
            case R.id.ll_start_pause:
                boolean tag = (boolean) llStartPause.getTag();
                if (!checkWorkConditionStatus()) {
                    return;
                }
                if (tag) {
                    llStartPause.setTag(false);
                    startOrPause();
                } else {
                    checkWorkConditionStatus();
                    checkCurrentStatus();
                }
                break;
            case R.id.iv_look_making_tray_info_vp:
                getTrayList();
                break;
            case R.id.tv_inspection_four:
                //报验
                if (wsTaskTypeEnum == WSTaskTypeEnum.FQA_RETURN) {
                    if (canCheckOut > 0) {
                        showInspebctionListDialog(null);
                    } else {
                        ToastUtil.showInfoCenterShort("没有可报验的物料!");
                    }
                } else {
                    requestInspectionData();
                }
                break;
            case R.id.tv_print_label:
                //打印标签
                requestLabelTypes();
                break;
        }

    }

    private void requestLabelTypes() {
        Map<String, String> params = new HashMap<>();

        params.put("productId", productId);

        currentPresent.requestLabelTypes(params);
    }

    private void requestPrintLabel(WSLabelBean.TempletDataBean bean) {
        Map<String, String> params = new HashMap<>();
        if (bean != null) {
            WSPrintlabelVo printlabelVo = new WSPrintlabelVo();
            printlabelVo.setCount(bean.getCount());
            printlabelVo.setLabelType(bean.getParentType());
            printlabelVo.setTempletType(bean.getTempletType() + "");
            printlabelVo.setProductModel(wsTaskMainInfo.getProductModel());//货号
            printlabelVo.setProductModelType(wsTaskMainInfo.getProductModelTypeName());//型号
            printlabelVo.setSerielNumber(wsTaskMainInfo.getSerialNumber());//订单编号
            printlabelVo.setProductNumber(wsTaskMainInfo.getProductNumber());//生产序号
            printlabelVo.setTempletId(bean.getTempletId());
            printlabelVo.setPwd(bean.getPwd());
            params.put("printLabel", JsonUtil.toJson(printlabelVo));
        }
        currentPresent.printLabel(params);
    }

    @Override
    public void showPrintLabelResult(boolean result) {
        if (result) {
            ToastUtil.showInfoCenterShort("打印成功");
            if (mLabelTempletDialog != null && mLabelTempletDialog.isShowing()) {
                mLabelTempletDialog.dismiss();
            }

            if (mLabelTypesDialog != null && mLabelTypesDialog.isShowing()) {
                mLabelTypesDialog.dismiss();
            }

        }
    }

    @Override
    public void showLabelTypes(List<WSLabelBean> result) {
        if (result != null) {
            if (result.size() > 0) {
                showLabelTypesDialog(result);
            } else {
                ToastUtil.showInfoCenterShort("暂无标签");
            }
        }

    }

    private void showReworkDialog() {
        mReworkDialog = WSDialogUtils.showReworkDialog(mActivity, "请输入返工数量", outputCount, new WSDialogCallBackTwo() {
            @Override
            public void OnPositiveClick(Object obj) {
                if (obj != null) {
                    String number_str = (String) obj;
                    requestRework(Integer.parseInt(number_str));
                }

            }

            @Override
            public void OnNegativeClick() {

            }
        });
    }

    private void requestRework(int count) {
        WSWorkStationOutVO wsWorkStationOutVO = new WSWorkStationOutVO();
        wsWorkStationOutVO.setWorkStationTaskId(taskId);
        wsWorkStationOutVO.setRepairReturnCount(count);
        wsWorkStationOutVO.setOutPersonId(WSUserManager.getInstance().getPersonId());
        Map<String, String> params = new HashMap<>();
        params.put("workStationOutInfo", JsonUtil.toJson(wsWorkStationOutVO));
        currentPresent.requestRework(params);

    }

    private void requestInspectionData() {
        Map<String, String> params = new HashMap<>();
        params.put("taskId", taskId);
        currentPresent.requestInspectionData(params, true);
    }

    private void reportWork() {
        boolean isclickableReport = (boolean) tvReportWrok.getTag();
        if (isclickableReport) {
            requesStepList();
        } else {
            checkCurrentStatus();
        }
    }

    //获取返工数据
    private List<WSInputWipInfo> getReworkData() {
        List<WSInputWipInfo> reworkList = new ArrayList<>();
        List<WSInputWipInfo> data = mAdapterTaskMaking.getData();
        for (int i = 0; i < data.size(); i++) {
            WSInputWipInfo wsInputWipInfo = data.get(i);
            if (wsInputWipInfo.isSelect()) {
                wsInputWipInfo.setSelect(false);
            }
            if (wsInputWipInfo.getRemainCount() > 0) {
                reworkList.add(wsInputWipInfo);
            }
        }
        return reworkList;

    }

    private boolean checkWorkConditionStatus() {
//        if (workConditionStatus == WSWorkConditionStatusEnum.NOTREADY) {
//            ToastUtil.showInfoCenterShort("未就绪状态，不能操作!");
//            return false;
//        }
        return true;
    }

    private boolean checkWorkConditionStatusTwo() {
        if (workConditionStatus == WSWorkConditionStatusEnum.NOTREADY) {
            ToastUtil.showInfoCenterShort("未就绪状态，不能操作!");
            return false;
        }
        return true;
    }

    private WSReviewWorkScanTrayDialog getReviewWorkScantrayDialog() {
        if (mReviewWorkScanTrayDialog == null)
            mReviewWorkScanTrayDialog = WSDialogUtils.showReviewWorkScantrayDialog(getActivity(), "请输入托盘码", new WSDialogCallBackTwo() {
                @Override
                public void OnPositiveClick(Object obj) {
                    String trayCode = (String) obj;
                    Map<String, String> params = new HashMap<>();
                    params.put("code", trayCode);
                    currentPresent.requestReworkTrayInfo(params);
                }

                @Override
                public void OnNegativeClick() {

                }
            });
        return mReviewWorkScanTrayDialog;
    }

    private WSReviewWorkDialog getReviewDialog() {
        if (wsReviewWorkDialog == null) {
            wsReviewWorkDialog = WSDialogUtils.showReviewDialog(getActivity(), "返工", new WSDialogCallBackTwo() {
                @Override
                public void OnPositiveClick(Object obj) {
                    Map<String, String> params = new HashMap<>();
                    currentPresent.requestRework(params);

                }

                @Override
                public void OnNegativeClick() {

                }
            });
        }
        return wsReviewWorkDialog;
    }

    private WSRepairDialog getRepairDialog() {
        if (wsRepairDialog == null) {
            wsRepairDialog = WSDialogUtils.showRepairDialog(getActivity(), "维修", new WSDialogCallBackTwo() {
                @Override
                public void OnPositiveClick(Object obj) {
                    if (obj != null && obj instanceof WSWorkStationOutVO) {
                        //1.先校验托盘码 2.输出维修
                        WSWorkStationOutVO wsWorkStationOutVO = (WSWorkStationOutVO) obj;
                        Map<String, String> params = new HashMap<>();
                        params.put("code", wsWorkStationOutVO.getTrayCode());
                        currentPresent.repairBindTray(params, wsWorkStationOutVO);
                    }
                }

                @Override
                public void OnNegativeClick() {

                }
            });
        }
        return wsRepairDialog;
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

    private void showLabelTypesDialog(List<WSLabelBean> datas) {
        if (datas.size() > 0) {
            showLabelTempletDialog(datas.get(0));
        }
//        if (mLabelTypesDialog == null) {
//            mLabelTypesDialog = WSDialogUtils.showLabelTypesDialog(mActivity, "标签打印", new WSDialogCallBackTwo() {
//                @Override
//                public void OnPositiveClick(Object obj) {
//                    if (obj != null && obj instanceof WSLabelBean) {
//                        WSLabelBean labelBean = (WSLabelBean) obj;
//                        showLabelTempletDialog(labelBean);
//                    }
//                }
//
//                @Override
//                public void OnNegativeClick() {
//
//                }
//            });
//        }
//        mLabelTypesDialog.setData(datas);
//        mLabelTypesDialog.show();

    }

    private void showLabelTempletDialog(WSLabelBean labelBean) {
        if (mLabelTempletDialog == null) {
            mLabelTempletDialog = WSDialogUtils.showLabelTempletDialog(mActivity, "请选择规格为" + labelBean.getLabelName() + "模板并输入数量", new WSDialogCallBackTwo() {
                @Override
                public void OnPositiveClick(Object obj) {
                    if (obj != null && obj instanceof WSLabelBean.TempletDataBean) {
                        WSLabelBean.TempletDataBean templetDataBean = (WSLabelBean.TempletDataBean) obj;
                        requestPrintLabel(templetDataBean);
                    }
                }

                @Override
                public void OnNegativeClick() {
                    if (mLabelTypesDialog != null && !mLabelTypesDialog.isShowing()) {
                        mLabelTypesDialog.show();
                    }
                }
            });
        }
        mLabelTempletDialog.setData(labelBean);
        mLabelTempletDialog.show();

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

    private void requestStartPause(int currentStatus) {
        Map<String, String> params = new HashMap<>();
        params.put("status", currentStatus + "");
        params.put("taskId", taskId);
        currentPresent.requestStartPause(params);
    }

    private void requesStepList() {
        Map<String, String> params = new HashMap<>();
        params.put("procedureId", procedureId);
        params.put("taskId", taskId);
        currentPresent.requesStepList(params);
    }

    private void requesExceptList() {
        Map<String, String> params = new HashMap<>();
        params.put("taskId", taskId);
        currentPresent.requesExceptList(params);
    }

    /**
     * pc端操作暂停订单
     */
    public void toPauseStatus(int type, String id) {
        if (!TextUtils.isEmpty(id)) {
            if (id.equals(taskId)) {
                if (currentStatus == 1) {
                    //不去请求了 刷新本地状态
                    if (type == MyServer.ACTION_ORDER_PAUSE) {
                        //订单暂停
                        if (currentEnumstatus != WSBeginOrEndEnum.PAUSE) {
                            currentEnumstatus = WSBeginOrEndEnum.PAUSE;
                            setWorkStatus1();
                            if (mPushTwoDialog != null) {
                                String title = mPushTwoDialog.getTitle();
                                if (getString(R.string.order_reagin).equals(title)) {
                                    mPushTwoDialog.dismiss();
                                }
                            }
                            showPushDialog(getString(R.string.order_pause), 0);
                        }
                    } else if (type == MyServer.ACTION_ORDER_REAGIN) {
                        //订单恢复.
                        if (mPushTwoDialog != null) {
                            String title = mPushTwoDialog.getTitle();
                            if (getString(R.string.order_pause).equals(title)) {
                                mPushTwoDialog.dismiss();
                            }
                        }
                        showPushDialog(getString(R.string.order_reagin), 2);
                    }
                }
            }
        }
    }

    /**
     * pc端操作终止订单 强行终止
     */
    public void toEndStatus(String id) {
        //校验任务id
        if (!TextUtils.isEmpty(id)) {
            if (id.equals(taskId)) {
                //订单终止强制退出 刷新列表
                showPushDialog("订单终止", 1);
            }
        }
    }


    /**
     * 同步任务操作
     *
     * @param messageEventBean
     */
    public void synTaskOpreat(MessageEventBean messageEventBean) {
        String message = messageEventBean.getMessage();
        if (TextUtils.isEmpty(message) || (!message.equals(taskId))) {
            return;
        }
        switch (messageEventBean.getRefreshType()) {
            case MyServer.ACTION_TASK_START:
                //任务开始
            case MyServer.ACTION_TASK_PAUSE:
                //任务暂停
            case MyServer.ACTION_TASK_FINISH:
                //任务已完成
            case MyServer.ACTION_TASK_END:
                //任务结束
                synStartOrPause(messageEventBean.getRefreshType());
                break;
            case MyServer.ACTION_TASK_RECIVER_MATERAIL:
                //接收物料
            case MyServer.ACTION_TASK_OUTPUT:
                //任务存在输出

            case MyServer.ACTION_TASK_REWORK:
                //任务返工
                if (getReviewWorkFragment() != null) {
                    getTaskMaterailAndMakingList(false);
                }
            case MyServer.ACTION_TASK_REPAIR:
                //任务维修
            case MyServer.ACTION_TASK_EXCEPTION:
                //任务异常
            case MyServer.ACTION_TASK_REPORT_WORK:
                //任务报工
                synTopFragmentStatus(messageEventBean.getRefreshType());
                break;
            case MyServer.ACTION_TASK_UPDATE_FOLLOW:
                getFollowData(false);
                break;
        }
    }

    private void synStartOrPause(int type) {
        if (type == MyServer.ACTION_TASK_START) {
            currentStatus = 1;
            if (currentEnumstatus != WSBeginOrEndEnum.BEGIN) {
                currentEnumstatus = WSBeginOrEndEnum.BEGIN;
                setWorkStatus1();
            }
        } else if (type == MyServer.ACTION_TASK_PAUSE) {
            currentStatus = 1;
            if (currentEnumstatus != WSBeginOrEndEnum.PAUSE) {
                currentEnumstatus = WSBeginOrEndEnum.PAUSE;
                setWorkStatus1();
            }
        } else if (type == MyServer.ACTION_TASK_FINISH) {
            currentStatus = 3;
            if (currentEnumstatus != WSBeginOrEndEnum.FINISH) {
                currentEnumstatus = WSBeginOrEndEnum.FINISH;
                setWorkStatus3();
            }

        } else if (type == MyServer.ACTION_TASK_END) {
            currentStatus = 2;
            if (currentEnumstatus != WSBeginOrEndEnum.END) {
                currentEnumstatus = WSBeginOrEndEnum.END;
                setWorkStatus2();
            }
        }
    }

    //同步输出  -- 1.输出历史记录2.
    private void synTopFragmentStatus(int refreshType) {
        ISupportFragment topFragment = getTopFragment();
        if (topFragment instanceof WSTaskFragment) {
            refreshData();
        } else {
            ((WSBaseFragment) topFragment).setSynStatus(true, refreshType);
        }
    }

    private void showPushDialog(String title, int code) {
        //暂停
        //终止
        mPushTwoDialog = WSDialogUtils.showPushTwoDialog(getActivity(), title, code, new WSDialogCallBackTwo() {
            @Override
            public void OnPositiveClick(Object obj) {
                if (obj != null) {
                    Integer code = (Integer) obj;
                    if (code == 0) {
                        //暂停
                    } else if (code == 1) {
                        //终止
                        quitRefresh();
                    }
                }
            }

            @Override
            public void OnNegativeClick() {

            }
        });
    }

    private void startOrPause() {
        //开始 暂停 结束
        if (currentStatus != 2) {
            if (currentStatus == 0) {
                currentStatus = 1;
            }
//            setWorkStatus();
            if (currentStatus == 1) {
                showStartPauseLoading();
                if (getString(R.string.text_work_start).equals(tvStartPause.getText())) {
                    //0请求暂停 1开始 2结束
                    requestStartPause(1);
                } else if (getString(R.string.text_work_pause).equals(tvStartPause.getText())) {
                    requestStartPause(0);
                }
            } else if (currentStatus == 3) {
                //待结束状态 去请求结束 弹框提醒
                if (getString(R.string.text_work_to_end).equals(tvStartPause.getText())) {
                    WSDialogUtils.showRemindDialog(getActivity(), "确定要结束吗?", new WSDialogCallBackTwo() {
                        @Override
                        public void OnPositiveClick(Object obj) {
                            requestStartPause(2);
                        }

                        @Override
                        public void OnNegativeClick() {
                            llStartPause.setTag(true);
                        }
                    });
                }
            }
        } else {
            ToastUtil.showInfoCenterShort("该工序已结束!");
        }

    }

    private WSReviewWorkFragment getReviewWorkFragment() {
        WSReviewWorkFragment reviewWorkFragment = findFragment(WSReviewWorkFragment.class);
        return reviewWorkFragment;
    }

    private void checkCurrentStatus() {
        if (!checkWorkConditionStatus()) {
            return;
        }
        switch (currentStatus) {
            case 0:
                ToastUtil.showInfoCenterShort("当前工序尚未开始!");
                break;
            case 1:
                if (wsTaskTypeEnum != null) {
                    switch (wsTaskTypeEnum) {
                        case COMMON:
                            ToastUtil.showInfoCenterShort("当前工序处于暂停,请先开始!");
                            break;
                        case REWORK:
                            ToastUtil.showInfoCenterShort("当前任务是返工任务,不可以操作!");
                            break;
                        case REPAIR_HELP:
                            ToastUtil.showInfoCenterShort("当前任务是辅助任务,不可以操作!");
                            break;
                        case HISTORY_TASK:
                            ToastUtil.showInfoCenterShort("当前任务是历史任务,不可以操作!");
                            break;
                    }
                }

                break;
            case 2:
                ToastUtil.showInfoCenterShort("当前工序已结束!");
                break;
            case 3:
                ToastUtil.showInfoCenterShort("当前工序的在制品已全部输出!");
                break;
        }
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
            getReviewWorkScantrayDialog().dismiss();
            getReviewDialog().setData(getActivity(), wsReworkTrayInfo).show();
        }
    }

    @Override
    public void showRepairBindTrayResult(boolean result, WSWorkStationOutVO wsWorkStationOutVO) {
        if (result) {
            viewPager.postDelayed(new Runnable() {
                @Override
                public void run() {
                    getRepairDialog().dismissThis();
                    wsWorkStationOutVO.setOutPersonId(WSUserManager.getInstance().getPersonId());
                    wsWorkStationOutVO.setWorkStationTaskId(taskId);
                    Map<String, String> params = new HashMap<>();
                    params.put("workStationOutInfo", JsonUtil.toJson(wsWorkStationOutVO));
                    currentPresent.requestRepair(params);
                }
            }, 100);

        }
    }

    @Override
    public void showExceptionInfo(WSTrayLoadInfo trayLoadInfo) {
        if (trayLoadInfo != null) {
            List<WSMaterial> materialList = trayLoadInfo.getMaterialList();
            if (materialList != null) {
                if (materialList.size() > 0) {
                    Bundle bundleExcept = new Bundle();
                    bundleExcept.putString("taskId", taskId);
                    bundleExcept.putParcelable("data", trayLoadInfo);
                    startForResult(WSExceptionFragment.newInstance(bundleExcept), EXCEPT_REQUEST_CODE);
                } else if (materialList.size() == 0) {
                    ToastUtil.showInfoCenterShort("没有可以异常输出的物料!");
                }
            } else {
                ToastUtil.showInfoCenterShort("没有可以异常输出的物料!");
            }
        }
    }

    @Override
    public void toEndTaskOpition(boolean confirm, int currentStatus) {
        if (confirm) {
            if (currentStatus == 2) {
                //跳转报工界面
                reportWork();
            }
        }
    }

    private void setWorkStatus() {
        //0 :未开始 1：已开始 可暂停 2：结束3:已完成（待结束状态） 计划==输出
        switch (currentStatus) {
            case 0:
                setWorkStatus0();
                break;
            case 1:
                setWorkStatus1();
                break;
            case 2:
                setWorkStatus2();
                break;
            case 3:
                setWorkStatus3();
                break;
        }
    }

    private void setWorkStatus3() {
        //待结束状态
        ivWorkStatus.setImageResource(R.drawable.work_end);
        tvStartPause.setText(R.string.text_work_to_end);
        llStartPause.setBackgroundResource(R.drawable.shape_blue);
        llStartPause.setTag(true);
//可以报工
        tvReportWrok.setBackgroundResource(R.drawable.shape_blue);
        tvExcept.setBackgroundResource(R.drawable.shape_gray_bg);
        tvRepair.setBackgroundResource(R.drawable.shape_gray_bg);
        tvRework.setBackgroundResource(R.drawable.shape_gray_bg);
        tvReportWrok.setTag(true);
        tvExcept.setTag(false);
        tvRepair.setTag(false);
        tvRework.setTag(false);
        quitToTaskFragment();
    }

    private void setWorkStatus0() {

        setWorkConditionStatus();
        tvTimeCount.setText(getString(R.string.text_work_unstart));

        ivWorkStatus.setImageResource(R.drawable.work_start);
        tvStartPause.setText(R.string.text_work_start);
        //报工异常置灰 不可点击
        tvReportWrok.setBackgroundResource(R.drawable.shape_gray_bg);
        tvExcept.setBackgroundResource(R.drawable.shape_gray_bg);
        tvRepair.setBackgroundResource(R.drawable.shape_gray_bg);
        tvRework.setBackgroundResource(R.drawable.shape_gray_bg);
        tvReportWrok.setTag(false);
        tvExcept.setTag(false);
        tvRepair.setTag(false);
        tvRework.setTag(false);
    }

    private void setWorkStatus1() {
        //优先设置就绪状态
        setWorkConditionStatus();
        boolean isClickable = (boolean) tvReportWrok.getTag();
        if (currentEnumstatus != null) {
            //与服务器状态同步
            if (currentEnumstatus == WSBeginOrEndEnum.BEGIN) {
                tvStartPause.setText(R.string.text_work_start);//后面才是真得设置
            } else if (currentEnumstatus == WSBeginOrEndEnum.PAUSE) {
                tvStartPause.setText(R.string.text_work_pause);
            }
        }
        //此处有疑问 优先未就绪状态---不能操作那些按钮
        //-------------------------------------------------------------
        if (workConditionStatus == WSWorkConditionStatusEnum.NOTREADY) {
//            tvStartPause.setText(R.string.text_work_pause);
        }
        //-------------------------------------------------------------
        if (getString(R.string.text_work_start).equals(tvStartPause.getText())) {
            ivWorkStatus.setImageResource(R.drawable.work_pause);
            tvStartPause.setText(R.string.text_work_pause);
            if (!isClickable) {
                tvReportWrok.setBackgroundResource(R.drawable.shape_blue);
                tvExcept.setBackgroundResource(R.drawable.shape_blue);
                tvReportWrok.setTag(true);
                tvExcept.setTag(true);
                if (wsTaskTypeEnum == WSTaskTypeEnum.COMMON) {
                    //辅助和返工任务--不可以操作维修和返工
                    tvRepair.setBackgroundResource(R.drawable.shape_blue);
                    tvRework.setBackgroundResource(R.drawable.shape_blue);
                    tvRepair.setTag(true);
                    tvRework.setTag(true);
                } else {
                    tvRepair.setBackgroundResource(R.drawable.shape_gray_bg);
                    tvRepair.setTag(false);
                    //FQA_返工
                    if (wsTaskTypeEnum == WSTaskTypeEnum.FQA_RETURN && !TextUtils.isEmpty(checkTag) && "yes".equals(checkTag)) {
                        tvRework.setBackgroundResource(R.drawable.shape_blue);
                        tvRework.setTag(true);
                    } else {
                        tvRework.setBackgroundResource(R.drawable.shape_gray_bg);
                        tvRework.setTag(false);
                    }
                }
            }
            //TODO

        } else {
            ivWorkStatus.setImageResource(R.drawable.work_start);
            tvStartPause.setText(R.string.text_work_start);
            if (isClickable) {
                tvReportWrok.setBackgroundResource(R.drawable.shape_gray_bg);
                tvExcept.setBackgroundResource(R.drawable.shape_gray_bg);
                tvRepair.setBackgroundResource(R.drawable.shape_gray_bg);
                tvRework.setBackgroundResource(R.drawable.shape_gray_bg);
                tvReportWrok.setTag(false);
                tvExcept.setTag(false);
                tvRepair.setTag(false);
                tvRework.setTag(false);
            }
            quitToTaskFragment();
        }
    }

    private void setWorkStatus2() {
        setWorkConditionStatus();
        ivWorkStatus.setImageResource(R.drawable.work_end);
        tvStartPause.setText(R.string.text_work_end);
        llStartPause.setBackgroundResource(R.drawable.shape_gray_bg);
        llStartPause.setTag(false);


        tvExcept.setBackgroundResource(R.drawable.shape_gray_bg);
        tvRepair.setBackgroundResource(R.drawable.shape_gray_bg);
        tvRework.setBackgroundResource(R.drawable.shape_gray_bg);

        tvExcept.setTag(false);
        tvRepair.setTag(false);
        tvRework.setTag(false);
        if (wsTaskTypeEnum == WSTaskTypeEnum.HISTORY_TASK) {
            //历史任务可报工 可输出
            tvReportWrok.setBackgroundResource(R.drawable.shape_blue);
            tvReportWrok.setTag(true);
        } else {
            tvReportWrok.setBackgroundResource(R.drawable.shape_gray_bg);
            tvReportWrok.setTag(false);
        }
        //非历史任务 结束后退出
        if (wsTaskTypeEnum != WSTaskTypeEnum.HISTORY_TASK) {
            quitRefresh();
        }
    }

    //设置就绪状态
    private void setWorkConditionStatus() {
//        if (workConditionStatus == WSWorkConditionStatusEnum.READY) {
        //已就绪
        llStartPause.setBackgroundResource(R.drawable.shape_blue);
        llStartPause.setTag(true);
//        } else {
//            //未就绪
//            llStartPause.setBackgroundResource(R.drawable.shape_gray_bg);
//            llStartPause.setTag(false);
//        }
    }

    /**
     * 退出到任务详情界面
     */
    private void quitToTaskFragment() {
        ISupportFragment topFragment = getTopFragment();
        if (topFragment != null && !(topFragment instanceof WSTaskFragment)) {
            ((SupportFragment) topFragment).popTo(WSTaskFragment.class, false);
        }
    }

    public void quitRefresh() {
        Bundle bundle = new Bundle();
        bundle.putBoolean("isRefresh", true);
        setFragmentResult(0, bundle);
        pop();
    }

    private void setDefaultTag() {
        setWorkStatus0();
        currentEnumstatus = WSBeginOrEndEnum.PAUSE;
    }


    WSTaskMainInfo wsTaskMainInfo = null;

    @Override
    public void showTaskInfo(WSTaskMainInfo wsTaskMainInfo) {
        if (wsTaskMainInfo != null) {
            if (tvTargt == null) {
                return;
            }
            this.wsTaskMainInfo = wsTaskMainInfo;

            checkTag = wsTaskMainInfo.getCheckTag();

            if (!TextUtils.isEmpty(checkTag) && "yes".equals(checkTag)) {
                tvInspectionFour.setVisibility(View.VISIBLE);
                //tvPrintLabel.setVisibility(View.VISIBLE);
            } else {
                //tvPrintLabel.setVisibility(View.GONE);
                tvInspectionFour.setVisibility(View.GONE);
            }

            if (!TextUtils.isEmpty(wsTaskMainInfo.getPrintLableTag()) && "YES".equals(wsTaskMainInfo.getPrintLableTag().toUpperCase())) {
                tvPrintLabel.setVisibility(View.VISIBLE);
            } else {
                tvPrintLabel.setVisibility(View.GONE);
            }


//            tvProductNumber.setText("产品型号: " + wsTaskMainInfo.getProductModelTypeName());
//            tvProductNumber.setText(wsTaskMainInfo.getProductNumber());//"生产序号: " +
//            tvModel.setText(wsTaskMainInfo.getProductModelTypeName());//"产品型号: " +
            tvProductNumberFour.setText(wsTaskMainInfo.getProductNumber());
            tvModelFour.setText(wsTaskMainInfo.getProductModelTypeName());
//            tvProduce.setText("工序: " + wsTaskMainInfo.getProcedureName());
            tvModel.setVisibility(View.VISIBLE);
            tvProduce.setVisibility(View.GONE);
            accessoryAddresslist = wsTaskMainInfo.getAccessoryAddresslist();
//            processInstructionUrl = wsTaskMainInfo.getProcessInstructionUrl();
            procedureId = wsTaskMainInfo.getProcedureId();
            productId = wsTaskMainInfo.getProductId();
            procedureId1 = wsTaskMainInfo.getProcedureId();
            workConditionStatus = wsTaskMainInfo.getWorkConditionStatus();//就绪状态
            if (workConditionStatus == null) {
                workConditionStatus = WSWorkConditionStatusEnum.NOTREADY;
            }
            showWorkTimeTvStatus(wsTaskMainInfo.getStatus());
        } else {
//            processInstructionUrl = "";
            currentAccessoryAddress = null;
        }

        getFollowData(true);
//        getTaskOtherInfo();
    }

    @Override
    public void showFolloweData(List<WSFollowedBean> wsFollowedList) {
        if (wsFollowedList != null && wsFollowedList.size() > 0) {
            showFollowedData(wsFollowedList);
        } else {
            showNullFollowed();
        }
    }

    private void showNullFollowed() {
        tvUnFollow.setVisibility(View.VISIBLE);
        recycleFollowVp.setVisibility(View.INVISIBLE);
    }

    private void showFollowedData(List<WSFollowedBean> wsFollowedList) {
        mAdapterBomMaterailFollow.setNewData(wsFollowedList);
        tvUnFollow.setVisibility(View.GONE);
        recycleFollowVp.setVisibility(View.VISIBLE);
    }


    @Override
    public void showTaskOtherInfo(WSTaskOtherInfo wsTaskOtherInfo) {
        if (wsTaskOtherInfo != null) {
            if (tvTargt == null) {
                return;
            }
            predictCount = wsTaskOtherInfo.getPredictCount();
            outputCount = wsTaskOtherInfo.getPlanCount();
            canCheckOut = wsTaskOtherInfo.getCanCheckCount();
            tvTargt.setText(wsTaskOtherInfo.getPlanCount() + "");
            tvOutput.setText(wsTaskOtherInfo.getPredictCount() + "");
            tvOutputOk.setText(wsTaskOtherInfo.getNormalCount() + "");
            tvPlan.setText("/" + wsTaskOtherInfo.getPlanCount());
            if (wsTaskOtherInfo.getPlanCount() == wsTaskOtherInfo.getNormalCount()) {
                //输出数量等于计划数量，即可结束任务  dhc
                currentStatus = 3;//可操作結束
                setWorkStatus();
            }
        }

//        getTaskMaterailAndMakingList();
    }

    @Override
    public void showMaterailAndMakingList(WSInputInfo inputInfo) {
        if (tvTargt == null) {
            return;
        }
        if (inputInfo != null && mAdapterTaskEntry != null) {
            List<WSInputMaterialInfo> materialList = inputInfo.getMaterialList();
            if (materialList != null) {
                mAdapterTaskEntry.setNewData(materialList);
            } else {
                mAdapterTaskEntry.setNewData(new ArrayList<>());
            }
            List<WSInputWipInfo> wipList = inputInfo.getWipList();
            if (wipList != null) {
                mAdapterTaskMaking.setNewData(wipList);
            } else {
                mAdapterTaskMaking.setNewData(new ArrayList<>());
            }
        }

        if (getReviewWorkFragment() != null) {
            List<WSInputWipInfo> reworkData = getReworkData();
            getReviewWorkFragment().synReviewworkList(reworkData);
        }
    }

    @Override
    public void showTrayMaterailList(List<WSTrayLoadInfo> datas) {
        if (datas != null && datas.size() > 0) {
            if (tvTargt == null) {
                return;
            }
            if (wsLookTrayListMaterailDialog == null) {
                wsLookTrayListMaterailDialog = WSDialogUtils.showLookTrayListMaterailDialog(getActivity(), "所有物料托盘信息", creatTrayListData(datas));
            } else {
                wsLookTrayListMaterailDialog.setData("所有物料托盘信息", creatTrayListData(datas));
                wsLookTrayListMaterailDialog.show();
            }
        } else {
            ToastUtil.showInfoCenterShort("该托盘没有物料");
        }
    }

    @Override
    public void showTrayMakingList(List<WSTrayLoadInfo> datas) {
        if (datas != null && datas.size() > 0) {
            if (tvTargt == null) {
                return;
            }
            if (wsLookTrayListDialog == null) {
                wsLookTrayListDialog = WSDialogUtils.showLookTrayListDialog(getActivity(), "所有在制品托盘信息", creatTrayListData(datas));
            } else {
                wsLookTrayListDialog.setData("所有在制品托盘信息", creatTrayListData(datas));
                wsLookTrayListDialog.show();
            }
        } else {
            ToastUtil.showInfoCenterShort("该托盘没有在制品");
        }
    }

    @Override
    public void showTrayList(List<WSTrayVo> trayList) {
        if (trayList != null && trayList.size() > 0) {
            if (tvTargt == null) {
                return;
            }
            if (wsLookTrayListVpDialog == null) {
                wsLookTrayListVpDialog = WSDialogUtils.showLookMaterailTrayListDialog(getActivity(), "托盘信息");
            }
            wsLookTrayListVpDialog.setData("托盘信息", trayList);
            wsLookTrayListVpDialog.show();

        } else if (trayList != null && trayList.size() == 0) {
            ToastUtil.showInfoCenterShort("未查到托盘信息");
        }
    }

    @Override
    public void showMaterailTrayList(WSMaterialTray wsMaterialTray) {
        if (wsMaterialTray != null && wsMaterialTray.getTrayList() != null && wsMaterialTray.getTrayList().size() > 0) {
            if (wsLookMaterailTrayDialog == null) {
                wsLookMaterailTrayDialog = WSDialogUtils.showLookMaterailTrayDialog(getActivity(), "", wsMaterialTray);
            } else {
                wsLookMaterailTrayDialog.setData("", wsMaterialTray, null, WSLookMaterailTrayDialog.STATUS0);
                wsLookMaterailTrayDialog.show();
            }
        } else {
            ToastUtil.showInfoCenterShort("该物料未查到所在的托盘");
        }

    }

    @Override
    public void showMakingTrayList(WSWipTray wsWipTray) {
        if (tvTargt == null) {
            return;
        }
        if (wsWipTray != null && wsWipTray.getTrayList() != null && wsWipTray.getTrayList().size() > 0) {
            if (wsLookMaterailTrayDialogTwo == null) {
                wsLookMaterailTrayDialogTwo = WSDialogUtils.showLookMaterailTrayDialog(getActivity(), "", wsWipTray);
            } else {
                wsLookMaterailTrayDialogTwo.setData("", null, wsWipTray, WSLookMaterailTrayDialog.STATUS1);
                wsLookMaterailTrayDialogTwo.show();
            }
        } else {
            ToastUtil.showInfoCenterShort("该在制品未查到所在的托盘");
        }
    }

    @Override
    public void showBomInfo(WSTrayLoadInfo info) {
        if (tvTargt == null) {
            return;
        }
        if (info != null) {
            Bundle bundle = new Bundle();
            bundle.putParcelable("data", info);
            bundle.putString("taskId", taskId);
            bundle.putString("productId", wsTaskMainInfo.getProductId());
            bundle.putString("procedureId", wsTaskMainInfo.getProcedureId());
            StartIntentUtils.startIntentUtilsFromResult(mActivity, WSBomDtailListDialogActivity.class, WSMainActivity.LOOK_BOM_DETAIL, bundle);
        }
    }

    boolean isCancle = false;//是否取消

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
        if (tvTargt == null) {
            return;
        }
        if (result && wsBeginOrEnd != null) {
            showWorkTimeTvStatus(wsBeginOrEnd);
//            setWorkStatus();
        }
        hideStartPauseLoading();
    }

    private void hideStartPauseLoading() {
        if (startPauseLoading != null) {
            startPauseLoading.hide();
//        startPauseLoading.setVisibility(View.INVISIBLE);
            llStartPause.setTag(true);
        }
    }

    private void showStartPauseLoading() {
//        llStartPause.setTag(false);
        if (startPauseLoading != null) {
            startPauseLoading.show();
            startPauseLoading.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 定时更新工作时间
     *
     * @param spaceTime 间隔秒数 默认30
     */
    public void updateWorkTime(int spaceTime) {
        if (currentStatus != 0 && tvStartPause != null && (!tvStartPause.getText().equals(getString(R.string.text_work_start)))) {
            //统计开始 非暂停状态时间
            currentTime = currentTime + spaceTime;
            if (tvTimeCount != null) {
                tvTimeCount.setText(TimerUtils.getSecondsStringTime(currentTime));//"累计时间: "
            }
        }
    }

    private void showWorkTimeTvStatus(WSBeginOrEnd wsBeginOrEnd) {
        if (wsBeginOrEnd == null) {
            return;
        }
        currentEnumstatus = wsBeginOrEnd.getStatus();
        if (currentEnumstatus != null) {
            switch (currentEnumstatus) {
                case NOTBEGIN:
                    //未开始
                    currentStatus = 0;
                    break;
                case BEGIN:
                case PAUSE:
                    //开始或暂停
                    currentStatus = 1;
                    break;
                case FINISH:
                    //任务已完成
                    currentStatus = 3;
                    break;
                case END:
                    //结束
                    currentStatus = 2;
                    break;
            }
        }

        tvTimeCount.setVisibility(View.VISIBLE);
        String time = wsBeginOrEnd.getTime();
        if (!"null".equals(time) && !TextUtils.isEmpty(time)) {
            currentTime = Long.parseLong(time);//s
            tvTimeCount.setText(TimerUtils.getSecondsStringTime(currentTime));
        } else {
            tvTimeCount.setText(getString(R.string.text_un_conditionStatus));
        }
        setWorkStatus();
        setTimeAnimation();
    }

    private void setTimeAnimation() {
        if (currentEnumstatus != null) {
            switch (currentEnumstatus) {
                case NOTBEGIN:
                    //未开始
                    stopTimeAnimation();
                    break;
                case BEGIN:
                    startTimeAnimation();
                    break;
                case PAUSE:
                    //开始或暂停
                    stopTimeAnimation();
                    break;
                case FINISH:
                    //任务已完成
                    startTimeAnimation();
                    break;
                case END:
                    //结束
                    stopTimeAnimation();
                    break;
                default:
                    stopTimeAnimation();
                    break;
            }
        }
    }

    private void startTimeAnimation() {
        if (aviTime != null && aviTime.getVisibility() != View.VISIBLE) {
            aviTime.show();
        }
    }

    private void stopTimeAnimation() {
        if (aviTime != null && aviTime.getVisibility() == View.VISIBLE) {
            aviTime.hide();
        }
    }

    private void startScrollMart() {
        if (tvMark != null && tvMark.getVisibility() == View.VISIBLE) {
            tvMark.continueRoll();
        }
    }

    private void stopScrollMart() {
        if (tvMark != null && tvMark.getVisibility() == View.VISIBLE) {
            tvMark.stopRoll();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        setTimeAnimation();
        startScrollMart();
    }

    @Override
    public void onPause() {
        stopTimeAnimation();
        stopScrollMart();
        super.onPause();
    }

    @Override
    public void showRewrokResult(boolean result) {
        if (result) {
            if (mReworkDialog != null) {
                mReworkDialog.dismiss();
            }
        }
        refreshData();
    }

    @Override
    public void showRepairResult(boolean result) {
        if (result) {
            ToastUtil.showInfoCenterShort("报修成功");
            getTaskOtherInfo(true);
            getTaskMaterailAndMakingList(true);
        }
    }

    @Override
    public void showStepList(List<WSProcedureStep> datas) {
        if (datas != null && datas.size() > 0) {
            Bundle bundle = new Bundle();
            bundle.putString("procedureId", procedureId);
            bundle.putString("taskId", taskId);
            bundle.putInt("outputCount", outputCount);
            bundle.putParcelableArrayList("stepdata", (ArrayList<? extends Parcelable>) datas);
            startForResult(WSReportWorkFragment.newInstance(bundle), REPORT_WORK_REQUEST_CODE);
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
        super.onFragmentResult(requestCode, resultCode, data);
        switch (requestCode) {
            case EXCEPT_REQUEST_CODE:
                if (data != null) {
                    boolean isQuit = data.getBoolean("isQuit");
                    boolean isRefresh = data.getBoolean("isRefresh");
                    if (isRefresh) {
                        initData();
                    }
                }
                break;
            case OUTPUT_REQUEST_CODE:
                if (data != null) {
                    boolean isRefresh = data.getBoolean("isRefresh");
                    if (isRefresh) {
                        initData();
                    }
                }
                break;
            case REPORT_WORK_REQUEST_CODE:
            case REPAIR_REQUEST_CODE:
            case REVIRE_REQUEST_CODE:
                //返工 维修
                if (data != null) {
                    boolean isRefresh = data.getBoolean("isRefresh");
                    if (isRefresh) {
                        initData();
                    }
                }
                break;

        }
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
        if (datas != null) {
            if (datas.size() > 0) {
                showInspebctionListDialog(datas);
            } else {
                ToastUtil.showCenterShort("暂无可报验的托盘", true);
            }
        }
    }

    @Override
    public void toInspectionResult(boolean result) {
        if (wsTaskTypeEnum == WSTaskTypeEnum.FQA_RETURN) {
            //无论报验成功失败 都得关闭 刷新数据
            if (mOutPutInsoectionDialog != null && mOutPutInsoectionDialog.isShowing()) {
                mOutPutInsoectionDialog.dismiss();
            }
            if (result) {
                ToastUtil.showInfoCenterShort("报验成功");
            }
            initData();
        } else {
            if (result && mOutPutInsoectionDialog != null && mOutPutInsoectionDialog.isShowing()) {
                ToastUtil.showInfoCenterShort("报验成功");
                mOutPutInsoectionDialog.dismiss();
            }
        }
    }

    @Override
    public void toMakeSureInspectResult(boolean result) {

    }


    public void showInspebctionListDialog(List<WSTaskProductCheckTray> datas) {
        if (mOutPutInsoectionDialog == null) {
            String title = "报验";
            if (wsTaskTypeEnum == WSTaskTypeEnum.FQA_RETURN) {
                title = "再次报验";
            }
            mOutPutInsoectionDialog = WSDialogUtils.showOutPutInsoectionDialog(mActivity, title, new WSDialogCallBackThree() {

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
        mOutPutInsoectionDialog.setData(datas, wsTaskTypeEnum, canCheckOut, taskId);
        mOutPutInsoectionDialog.show();
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
        if (result && mOutPutInsoectionDialog != null) {
            mOutPutInsoectionDialog.setScanCode(code);
        } else {
            mOutPutInsoectionDialog.setScanCode(getResources().getString(R.string.text_unbind));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder2 = ButterKnife.bind(this, rootView);
        return rootView;
    }
}
