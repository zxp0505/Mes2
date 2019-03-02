package workstation.zjyk.workstation.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.haibin.calendarview.Calendar;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.com.ethank.mylibrary.resourcelibrary.toast.ToastUtil;
import cn.com.ethank.mylibrary.resourcelibrary.utils.TimerUtils;
import cn.com.ethank.mylibrary.resourcelibrary.utils.UICommonUtil;
import io.reactivex.functions.Consumer;
import workstation.zjyk.workstation.R;
import workstation.zjyk.workstation.modle.bean.WSDateBean;
import workstation.zjyk.workstation.modle.bean.WSMaterial;
import workstation.zjyk.workstation.modle.bean.WSOrderCheckVo;
import workstation.zjyk.workstation.modle.bean.WSTrayLoadInfo;
import workstation.zjyk.workstation.modle.bean.WSWorkStationInfo;
import workstation.zjyk.workstation.modle.bean.WSWorkStationTask;
import workstation.zjyk.workstation.modle.bean.WSWorkStationTaskListBean;
import workstation.zjyk.workstation.modle.bean.WSWorkStationTaskTableCount;
import workstation.zjyk.workstation.modle.bean.enumpackage.WSTaskTypeEnum;
import workstation.zjyk.workstation.modle.bean.enumpackage.WSYesOrNoEnum;
import workstation.zjyk.workstation.ui.WSMainActivity;
import workstation.zjyk.workstation.ui.adapter.WSAdapterInspectTaskList;
import workstation.zjyk.workstation.ui.adapter.WSAdapterTaskList;
import workstation.zjyk.workstation.ui.customview.MyGridlayoutMManager;
import workstation.zjyk.workstation.ui.manager.WSUserManager;
import workstation.zjyk.workstation.ui.pop.WSWorkStationTrasferPop;
import workstation.zjyk.workstation.ui.present.WSInspectTaskListPresent;
import workstation.zjyk.workstation.ui.present.WSTaskListPresent;
import workstation.zjyk.workstation.ui.views.WSInspectListView;
import workstation.zjyk.workstation.ui.views.WSTaskListView;
import workstation.zjyk.workstation.util.dialog.WSDialogUtils;
import workstation.zjyk.workstation.util.dialog.WSSelectDateDialog;
import workstation.zjyk.workstation.util.dialog.callback.WSDialogCallBackTwo;

/**
 * 巡检任务列表
 * Created by zjgz on 2017/12/7.
 */

public class WSInspectTaskListFragment extends WSBaseFragment<WSInspectTaskListPresent> implements WSInspectListView {

    @BindView(R.id.recycyleview)
    RecyclerView recycyleview;
    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout swipeRefresh;
    Unbinder unbinder;
    @BindView(R.id.ll_root)
    LinearLayout llRoot;
    Unbinder unbinder1;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.rl_title_one)
    RelativeLayout rlTitleOne;
    @BindView(R.id.rl_title_two)
    RelativeLayout rlTitleTwo;
    @BindView(R.id.rl_title_three)
    RelativeLayout rlTitleThree;
    @BindView(R.id.tv_hand_barcode)
    TextView tvHandBarcode;
    @BindView(R.id.iv_back_one)
    ImageView ivBackOne;
    WSTaskTypeEnum type_current = WSTaskTypeEnum.COMMON;//1：正常任务 2：返工任务 3：辅助任务 4.历史任务
    //    private static final WSTaskTypeEnum type_normal = WSTaskTypeEnum.COMMON;
//    private static final WSTaskTypeEnum type_review = WSTaskTypeEnum.REWORK;
//    private static final WSTaskTypeEnum type_assis = WSTaskTypeEnum.REPAIR_HELP;
    @BindView(R.id.tv_task_normal)
    TextView tvTaskNormal;
    @BindView(R.id.tv_task_rework)
    TextView tvTaskRework;
    @BindView(R.id.tv_task_assist)
    TextView tvTaskAssist;
    @BindView(R.id.iv_task_tile_line)
    ImageView ivTaskTileLine;
    @BindView(R.id.iv_trangle)
    ImageView ivTrangle;
    @BindView(R.id.tv_search)
    TextView tvSearch;
    Unbinder unbinder2;
    @BindView(R.id.tv_search_by_date)
    TextView tvSearchByDate;
    @BindView(R.id.tv_task_history)
    TextView tvTaskHistory;
    private int pageNo = 1;//当前页码
    private int pageSize = 8;//每页数量
    private boolean isLoadEnd;
    private View mFooterView;
    private WSAdapterInspectTaskList mWSAdapterTaskList;
    //    private WSTaskListPresent mTaskListPresent;
    private WSWorkStationTrasferPop mWorkStationTrasferPop;
    private static final int REQUEST_CODE_TASK_DETAIL = 0;//任务详情
    private static final int REQUEST_CODE_TASK_TRANSFER = 1;//任务转移
    private int mCenterNormalPosition;
    private int mCenterReworkPosition;
    private int mCenterAssistPosition;
    private String searchText = "";
    private String searchDate = "";
    private int mCenterHistoryPosition;
    private WSSelectDateDialog mSelectDateDialog;
    private View tabTaskList;

    public static WSInspectTaskListFragment newInstance() {
        WSInspectTaskListFragment wsTaskListFragment = new WSInspectTaskListFragment();
        return wsTaskListFragment;
    }


    @Override
    protected void creatPresent() {
        currentPresent = new WSInspectTaskListPresent();
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        unbinder = ButterKnife.bind(this, view);
        tabTaskList = view.findViewById(R.id.tab_tasklist);
        tabTaskList.setVisibility(View.GONE);
        searchDate = TimerUtils.getNowDate();
        tvTitle.setText(searchDate);
        tvSearch.setText("订单搜索");
        rlTitleOne.setVisibility(View.VISIBLE);
        rlTitleTwo.setVisibility(View.GONE);
        rlTitleThree.setVisibility(View.GONE);
        ivBackOne.setVisibility(View.GONE);
        tvHandBarcode.setOnClickListener(this);
        tvHandBarcode.setVisibility(View.GONE);
        tvRight.setVisibility(View.GONE);
        tvSearchByDate.setVisibility(View.VISIBLE);
        initSwipeRefresh();
        mFooterView = LayoutInflater.from(getActivity()).inflate(R.layout.footer_view, null);
        mWSAdapterTaskList = new WSAdapterInspectTaskList();
        recycyleview.setLayoutManager(new MyGridlayoutMManager(getActivity(), 2));
        recycyleview.setAdapter(mWSAdapterTaskList);
        mWSAdapterTaskList.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                Log.e(TAG, "onLoadMoreRequested:---isLoadEnd:" + isLoadEnd);
                if (isLoadEnd) {
                    mWSAdapterTaskList.loadMoreEnd(false);
                } else {
//                    if (!NoDoubleClickUtil.doubleClick(800)) {
                    swipeRefresh.setEnabled(false);
                    getData(false);
//                    }
                }
            }
        }, recycyleview);

        mWSAdapterTaskList.setOnItemChildLongClickListener(new BaseQuickAdapter.OnItemChildLongClickListener() {
            @Override
            public boolean onItemChildLongClick(BaseQuickAdapter adapter, View view, int position) {
                //长按工位转移
                int[] locationOnScreen = UICommonUtil.getLocationOnScreen(view);
                int width = view.getWidth();
                int height = view.getHeight();
                getStationTrasferPop().showPopuWindowAtlocation(view, locationOnScreen[0] + width / 2, locationOnScreen[1] + height / 4);
//                getStationTrasferPop().showPopupWindow();
                return false;
            }
        });
        mWSAdapterTaskList.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (!checkLogin()) {
                    return;
                }
                WSOrderCheckVo wsWorkStationTask = (WSOrderCheckVo) adapter.getData().get(position);
                switch (view.getId()) {
                    case R.id.fl_root:
                        //条件未就绪也可以查看
                        goTaskFragment(wsWorkStationTask);
                        break;
                    case R.id.ll_station_trasfer:
//                        requesStationTrasferList(wsWorkStationTask);
                        break;

                }

            }


        });

        RxView.clicks(tvRight).throttleFirst(800, TimeUnit.MILLISECONDS).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                if (!checkLogin()) {
                    return;
                }
                getMaterailBill();
            }
        });

    }

    public void goTaskFragment(WSOrderCheckVo wsWorkStationTask) {
        startForResult(WSInspectDetailFragment.newInstance(wsWorkStationTask), REQUEST_CODE_TASK_DETAIL);
    }

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE_TASK_DETAIL:
            case REQUEST_CODE_TASK_TRANSFER:
                if (data != null) {
                    boolean isRefresh = data.getBoolean("isRefresh");
                    if (isRefresh) {
                        getFirstDataNormal();
//                        getFirstData();
                    }
                }
                break;
        }
    }

    //请求工位转移列表
    private void requesStationTrasferList(WSWorkStationTask wsWorkStationTask) {
        Map<String, String> params = new HashMap<>();
        params.put("taskId", wsWorkStationTask.getId());
        currentPresent.requesStationTrasferList(params, wsWorkStationTask);
    }

    private WSWorkStationTrasferPop getStationTrasferPop() {
        if (mWorkStationTrasferPop == null) {
            mWorkStationTrasferPop = new WSWorkStationTrasferPop(getActivity(), new WSDialogCallBackTwo() {
                @Override
                public void OnPositiveClick(Object obj) {
//                    start(WSStationTrasferFragment.newInstance());
                }

                @Override
                public void OnNegativeClick() {

                }
            });
        }
        return mWorkStationTrasferPop;

    }

    private void showFirstDialog(WSWorkStationTask wsWorkStationTask) {
        WSDialogUtils.showOneTipDialog(getActivity(), "您选择的订单任务需要做首件,请知晓!", "好的,知道了", new WSDialogCallBackTwo() {
            @Override
            public void OnPositiveClick(Object obj) {
                startForResult(WSTaskFragment.newInstance(wsWorkStationTask), REQUEST_CODE_TASK_DETAIL);
            }

            @Override
            public void OnNegativeClick() {

            }
        });
    }

    @Override
    public void initData() {
        requestTaskListCount(true);
        getFirstData();
    }

    private void getFirstData() {
        pageNo = 1;
        getData(true);
    }

    private void requestTaskListCount(boolean isShowLoading) {
//        currentPresent.requestTaskListCount(isShowLoading);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_task_list;
    }

    @Override
    protected View getLoadingTargetView() {
        return llRoot;
    }

    private void initSwipeRefresh() {
        swipeRefresh.setSize(SwipeRefreshLayout.DEFAULT);//设置下拉圆圈的大小，两个值 LARGE， DEFAULT
        swipeRefresh.setColorSchemeResources(android.R.color.holo_red_light, android.R.color.holo_green_light, android.R.color.holo_blue_light);//色环指下拉圆圈上的颜色
        swipeRefresh.setEnabled(true);//设置是否禁止下拉刷新
//        mSwipeRefresh.setProgressBackgroundColor(android.R.color.holo_orange_dark);//// 设定下拉圆圈的背景
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //下拉刷新
                searchText = "";
                pageNo = 1;
                isLoadEnd = false;
                mWSAdapterTaskList.setEnableLoadMore(false);//下拉刷新的时候禁止上拉加载
                refreshCurrent();
            }
        });
    }

    private void getData(boolean isShowLoading) {
        if (TextUtils.isEmpty(searchDate)) {
            hideSwipeRefresh();
            return;
        }
        currentPresent.requestInspectTaskList(type_current, pageNo, pageSize, searchText, searchDate, "0", isShowLoading);
    }

    private void getFirstDataNormal() {
        searchText = "";
//        searchDate = "";
        getFirstData();
    }

    /**
     * 根据日期搜索
     */
    private void getDataBySearchDate() {
        searchText = "";
        getFirstData();
    }

    /**
     * 任务搜素
     */
    public void getDataBySearchTask(String searchText) {
        this.searchText = searchText;
//        searchDate = "";
        getFirstData();
    }

    /**
     * 刷新页面
     */
    public void refreshData() {
        requestTaskListCount(false);
        refreshCurrent();
    }

    public void refreshCurrent() {
        pageNo = 1;
//        searchDate = "";
        searchText = "";
        getData(false);
    }

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        // TODO: inflate a fragment view
//        View rootView = super.onCreateView(inflater, container, savedInstanceState);
//
//        unbinder1 = ButterKnife.bind(this, rootView);
//        return rootView;
//    }

//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        unbinder.unbind();
//    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
    }

    //处理扫描结果
    @Override
    public void setScanResult(String scanResult) {
        super.setScanResult(scanResult);
        currentPresent.getBarCodeStatus(scanResult);
    }

    private void setTotalNumber(WSWorkStationTaskListBean wsWorkStationTaskListBean) {
        int total = wsWorkStationTaskListBean.getTotal();
        WSTaskTypeEnum type = wsWorkStationTaskListBean.getType();
        clearTaskBg();
        switch (type) {
            case COMMON:
                tvTaskNormal.setText("正常任务(" + total + ")");
                tvTaskNormal.setBackgroundColor(getResources().getColor(R.color.task_normal));
                tvTaskNormal.setTextColor(getResources().getColor(R.color.black));

                break;
            case REWORK:
                tvTaskRework.setText("返工任务(" + total + ")");
                tvTaskRework.setBackgroundColor(getResources().getColor(R.color.task_rework));
                tvTaskRework.setTextColor(getResources().getColor(R.color.black));
                break;
            case REPAIR_HELP:
                tvTaskAssist.setText("辅助任务(" + total + ")");
                tvTaskAssist.setBackgroundColor(getResources().getColor(R.color.task_assis));
                tvTaskAssist.setTextColor(getResources().getColor(R.color.black));
                break;
            case HISTORY_TASK:
                tvTaskHistory.setText("历史任务(" + total + ")");
                tvTaskHistory.setBackgroundColor(getResources().getColor(R.color.task_history));
                tvTaskHistory.setTextColor(getResources().getColor(R.color.black));
                break;
        }

        getTaskCenterPosition();
        setTaskTiltleLineBg(type);
        setTranglePosition(type);
    }

    private void setTaskTiltleLineBg(WSTaskTypeEnum type) {
        switch (type) {
            case COMMON:
                ivTaskTileLine.setBackgroundColor(getResources().getColor(R.color.task_normal));
                break;
            case REWORK:
                ivTaskTileLine.setBackgroundColor(getResources().getColor(R.color.task_rework));
                break;
            case REPAIR_HELP:
                ivTaskTileLine.setBackgroundColor(getResources().getColor(R.color.task_assis));
                break;
            case HISTORY_TASK:
                ivTaskTileLine.setBackgroundColor(getResources().getColor(R.color.task_history));
                break;
        }
    }

    private void setTranglePosition(WSTaskTypeEnum type) {
        RelativeLayout.LayoutParams ivTrangleLayoutParams = (RelativeLayout.LayoutParams) ivTrangle.getLayoutParams();
        switch (type) {
            case COMMON:
                ivTaskTileLine.setBackgroundColor(getResources().getColor(R.color.task_normal));
                ivTrangleLayoutParams.leftMargin = mCenterNormalPosition;
                break;
            case REWORK:
                ivTaskTileLine.setBackgroundColor(getResources().getColor(R.color.task_rework));
                ivTrangleLayoutParams.leftMargin = mCenterReworkPosition;
                break;
            case REPAIR_HELP:
                ivTaskTileLine.setBackgroundColor(getResources().getColor(R.color.task_assis));
                ivTrangleLayoutParams.leftMargin = mCenterAssistPosition;
                break;
            case HISTORY_TASK:
                ivTaskTileLine.setBackgroundColor(getResources().getColor(R.color.task_history));
                ivTrangleLayoutParams.leftMargin = mCenterHistoryPosition;
                break;
        }
        ivTrangle.requestLayout();
    }

    Handler handler = new Handler();


    //------------------------------------------------上拉加载
    @Override
    public void showFirstData(List<WSOrderCheckVo> datas) {
        if (datas != null) {
            mWSAdapterTaskList.setNewData(datas);
        } else {
            mWSAdapterTaskList.setNewData(new ArrayList<WSOrderCheckVo>());
        }
        int currentCount = mWSAdapterTaskList.getItemCount();
        setPage(currentCount - 1);
        hideLoading();
        setRecycleView(datas);
    }


    @Override
    public void loadMoreData(List<WSOrderCheckVo> datas) {
        if (datas != null) {
            mWSAdapterTaskList.addData(datas);
        }
        swipeRefresh.setEnabled(true);
        mWSAdapterTaskList.loadMoreComplete();
        int itemCount = mWSAdapterTaskList.getItemCount();
        setPage(itemCount - 1);
        setRecycleView(datas);
    }


    String currentBarCode;//记录当前已扫出的编码

    @Override
    public void showScanResult(WSTrayLoadInfo trayLoadInfo) {

        if (trayLoadInfo != null) {
            ((WSMainActivity) getActivity()).opreationScanResult(trayLoadInfo);
//            if (wrap.getReceiveStatus() == ReceiveEnum.UNRECEIVED) {
//                //未接收 展示收料信息
//                ((MainActivity) getActivity()).opreation(0, wrap);
//            } else if (wrap.getReceiveStatus() == ReceiveEnum.RECEIVED) {
//                //已接收 显示分料信息界面
//                ((MainActivity) getActivity()).opreation(1, wrap);
//            } else if (wrap.getReceiveStatus() == ReceiveEnum.DELIVER) {
//                ((MainActivity) getActivity()).opreation(2, wrap);
//            }
        }


    }

    @Override
    public void loadError() {
        hideSwipeRefresh();
    }

    @Override
    public void showMaterailBill(List<WSMaterial> materials) {
        if (materials != null && materials.size() > 0) {
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList("data", (ArrayList<? extends Parcelable>) materials);
            if (mActivity != null && mActivity instanceof WSMainActivity) {
                ((WSMainActivity) mActivity).showDialogActivity(3, bundle);
            }
        } else {
            ToastUtil.showInfoCenterShort("工位没有剩余物料");
        }
    }

    @Override
    public void showStationList(List<WSWorkStationInfo> wsWorkStationInfos, WSWorkStationTask wsWorkStationTask) {
        if (wsWorkStationInfos != null) {
            if (wsWorkStationInfos.size() == 0) {
                ToastUtil.showInfoCenterShort("没有可以转移的工位!");
            } else {
                Bundle bundle = new Bundle();
                bundle.putParcelable("data", wsWorkStationTask);
                bundle.putParcelableArrayList("stationlist", (ArrayList<? extends Parcelable>) wsWorkStationInfos);
                startForResult(WSStationTrasferFragment.newInstance(bundle), REQUEST_CODE_TASK_TRANSFER);
            }
        }
    }

    @Override
    public void showTaskListCount(WSWorkStationTaskTableCount wsWorkStationTaskTableCount) {
        if (wsWorkStationTaskTableCount != null) {
            String common = wsWorkStationTaskTableCount.getCommon();
            String repair = wsWorkStationTaskTableCount.getRepair();
            String rework = wsWorkStationTaskTableCount.getRework();
            String history = wsWorkStationTaskTableCount.getHistory();
            tvTaskNormal.setText("正常任务(" + common + ")");
            tvTaskRework.setText("返工任务(" + rework + ")");
            tvTaskAssist.setText("辅助任务(" + repair + ")");
            tvTaskHistory.setText("历史任务(" + history + ")");
        }
        getTaskCenterPosition();

    }

    private void getTaskCenterPosition() {
        int tvTaskNormalWidth = tvTaskNormal.getWidth();
        int tvTaskReworkWidth = tvTaskRework.getWidth();
        int tvTaskAssistWidth = tvTaskAssist.getWidth();
        int tvTaskHistoryWidth = tvTaskHistory.getWidth();
        float tvTaskNormalX = tvTaskNormal.getX();
        float tvTaskReworkX = tvTaskRework.getX();
        float tvTaskAssistX = tvTaskAssist.getX();
        float tvTaskHistoryX = tvTaskHistory.getX();
        mCenterNormalPosition = (int) tvTaskNormalX + tvTaskNormalWidth / 2;
        mCenterReworkPosition = (int) tvTaskReworkX + tvTaskReworkWidth / 2;
        mCenterAssistPosition = (int) tvTaskAssistX + tvTaskAssistWidth / 2;
        mCenterHistoryPosition = (int) tvTaskHistoryX + tvTaskHistoryWidth / 2;
    }


    /**
     * 设置page
     *
     * @param currentCount
     */
    private void setPage(int currentCount) {
        int divisor = currentCount / pageSize;
        if (divisor >= 1) {
            pageNo = divisor + 1;
        } else if (divisor == 0) {
            pageNo = 1;
        }
    }

    private static final String TAG = "LOAD";

    private void setRecycleView(List<WSOrderCheckVo> data) {
        Log.e(TAG, "setRecycleView:" + data.size());
        if (data != null && data.size() < pageSize) {
            isLoadEnd = true;
            removeFooter();
            mWSAdapterTaskList.loadMoreEnd(false);
        } else {
            isLoadEnd = false;
            addFooter();
            mWSAdapterTaskList.setEnableLoadMore(true);
        }
    }

    private void addFooter() {
//        removeFooter();
//        mWSAdapterTaskList.addFooterView(mFooterView);
    }

    private void removeFooter() {
//        if (mWSAdapterTaskList.getFooterLayoutCount() > 0) {
//            mWSAdapterTaskList.removeAllFooterView();
//        }
    }

    @Override
    public void hideLoading() {
        hideSwipeRefresh();
        toggleShowLoading(false, "");

    }

    private void hideSwipeRefresh() {
        if (swipeRefresh.isRefreshing()) {
            swipeRefresh.setRefreshing(false);
        }
    }


    //------------------------------------------------

    @Override
    public void setFragmentBack() {

    }

    @Override
    public void setActivityLogoOrBack() {
        if (mActivity != null && mActivity instanceof WSMainActivity) {
            ((WSMainActivity) mActivity).setActivityLogoOrBack(0);
        }
    }

    @OnClick({R.id.tv_task_normal, R.id.tv_task_rework, R.id.tv_task_assist, R.id.tv_task_history, R.id.tv_search, R.id.tv_search_by_date})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_right:
                break;
            case R.id.tv_task_normal:
                if (type_current != WSTaskTypeEnum.COMMON) {
                    type_current = WSTaskTypeEnum.COMMON;
                    getFirstDataNormal();
                }
                break;
            case R.id.tv_task_rework:
                if (type_current != WSTaskTypeEnum.REWORK) {
                    type_current = WSTaskTypeEnum.REWORK;
                    getFirstDataNormal();
                }
                break;
            case R.id.tv_task_assist:
                if (type_current != WSTaskTypeEnum.REPAIR_HELP) {
                    type_current = WSTaskTypeEnum.REPAIR_HELP;
                    getFirstDataNormal();
                }
                break;
            case R.id.tv_task_history:
                if (type_current != WSTaskTypeEnum.HISTORY_TASK) {
                    type_current = WSTaskTypeEnum.HISTORY_TASK;
                    getFirstDataNormal();
                }
                break;
            case R.id.tv_search:
                showHandSearchDialog();
                break;
            case R.id.tv_search_by_date:
                showSelectDateDialog(new ArrayList<>());
//                requestDateList();
                break;
        }

    }

    private void requestDateList() {
        Map<String, String> params = new HashMap<>();
        params.put("workStationId", WSUserManager.getInstance().getWorkStationId());
        params.put("taskType", type_current.toString());
        currentPresent.requestDateList(params);
    }

    @Override
    public void showDateList(List<String> dateList) {
        if (dateList != null) {
            if (dateList.size() > 0) {
                showSelectDateDialog(dateList);
            } else {
                ToastUtil.showInfoCenterShort("暂无可选的日期");
            }
        }
    }

    private void showSelectDateDialog(List<String> dateList) {
        if (mSelectDateDialog == null) {
            mSelectDateDialog = WSDialogUtils.showSelectDateDialog(mActivity, "请选择日期", new WSDialogCallBackTwo() {
                @Override
                public void OnPositiveClick(Object obj) {
                    if (obj != null && obj instanceof WSDateBean) {
                        WSDateBean dateBean = (WSDateBean) obj;
                        Calendar calendar =dateBean.getCalendar();
                        searchDate = calendar.getYear() + "-" + (calendar.getMonth() < 10 ? "0" + calendar.getMonth() : calendar.getMonth()) + "-" + (calendar.getDay() < 10 ? "0" + calendar.getDay() : calendar.getDay());
                        tvTitle.setText(searchDate);
                        getDataBySearchDate();
                    }

                }

                @Override
                public void OnNegativeClick() {

                }
            });
        }
        mSelectDateDialog.setDateList(dateList);
        mSelectDateDialog.show();
    }

    private void showHandSearchDialog() {
        WSDialogUtils.showHandSearchDialog(mActivity, "请输入订单编号、生产序号、型号、货号", new WSDialogCallBackTwo() {
            @Override
            public void OnPositiveClick(Object obj) {
                if (obj != null) {
                    String searchText = (String) obj;
                    getDataBySearchTask(searchText);
                }

            }

            @Override
            public void OnNegativeClick() {

            }
        });
    }

    private void clearTaskBg() {
        tvTaskNormal.setBackgroundColor(getResources().getColor(R.color.white_full));
        tvTaskRework.setBackgroundColor(getResources().getColor(R.color.white_full));
        tvTaskAssist.setBackgroundColor(getResources().getColor(R.color.white_full));
        tvTaskHistory.setBackgroundColor(getResources().getColor(R.color.white_full));
        tvTaskNormal.setTextColor(getResources().getColor(R.color.black_fourty));
        tvTaskRework.setTextColor(getResources().getColor(R.color.black_fourty));
        tvTaskAssist.setTextColor(getResources().getColor(R.color.black_fourty));
        tvTaskHistory.setTextColor(getResources().getColor(R.color.black_fourty));
    }

    //获取物料清单
    private void getMaterailBill() {
        Map<String, String> params = new HashMap<>();
        currentPresent.getMaterailBill(params);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder2 = ButterKnife.bind(this, rootView);
        return rootView;
    }
}
