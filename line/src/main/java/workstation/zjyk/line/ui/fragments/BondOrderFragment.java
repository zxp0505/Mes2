package workstation.zjyk.line.ui.fragments;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.haibin.calendarview.Calendar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.com.ethank.mylibrary.resourcelibrary.toast.ToastUtil;
import workstation.zjyk.line.R;
import workstation.zjyk.line.modle.bean.OrderMaterielVo;
import workstation.zjyk.line.ui.adapter.AdapterBondOrder;
import workstation.zjyk.line.ui.present.BondOrderPresent;
import workstation.zjyk.line.ui.present.MvpBasePresenter;
import workstation.zjyk.line.ui.views.BondOrderView;
import workstation.zjyk.line.util.Constants;
import workstation.zjyk.line.util.DialogCallBackTwo;
import workstation.zjyk.line.util.dialog.DialogUtils;
import workstation.zjyk.line.util.dialog.SelectDateDialog;

public class BondOrderFragment extends BaseFragment implements BondOrderView {

    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.tv_date_select)
    TextView tvDateSelect;
    Unbinder unbinder;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_cancle)
    TextView tvCancle;
    @BindView(R.id.recycyleview)
    RecyclerView recycyleview;
    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout swipeRefresh;
    Unbinder unbinder1;
    @BindView(R.id.tv_order_select)
    TextView tvOrderSelect;
    @BindView(R.id.tv_bond_un)
    TextView tvBondUn;
    @BindView(R.id.tv_bonded)
    TextView tvBonded;
    @BindView(R.id.tv_bond_all)
    TextView tvBondAll;
    private SelectDateDialog mSelectDateDialog;

    private int pageNo = 1;//当前页码
    private int pageSize = 8;//每页数量
    private boolean isLoadEnd;
    private String searchDate = "";
    private String searchText = "";
    private AdapterBondOrder mAdapterBondOrder;
    private BondOrderPresent mBondOrderPresent;
    int bondType = 1;//1:未下发 2：已下发 3：所有

    public static BondOrderFragment newInstance() {
        BondOrderFragment bondOrderFragment = new BondOrderFragment();
        return bondOrderFragment;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        unbinder = ButterKnife.bind(this, view);
        initSwipeRefresh();
        tvTitle.setText("订单");
        setDate();
        if (Constants.isReciver) {
            tvCancle.setVisibility(View.VISIBLE);
            RelativeLayout.LayoutParams layoutParams_title = (RelativeLayout.LayoutParams) tvTitle.getLayoutParams();
            layoutParams_title.leftMargin = getResources().getDimensionPixelOffset(R.dimen.x_design_200px);
            tvTitle.requestLayout();
        } else {
            tvCancle.setVisibility(View.GONE);
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) tvDateSelect.getLayoutParams();
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            tvDateSelect.requestLayout();
        }
        mBondOrderPresent = new BondOrderPresent(this);
        recycyleview.setLayoutManager(new GridLayoutManager(mActivity, 2));
        mAdapterBondOrder = new AdapterBondOrder();
        recycyleview.setAdapter(mAdapterBondOrder);
        mAdapterBondOrder.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                if (isLoadEnd) {
                    mAdapterBondOrder.loadMoreEnd(false);
                } else {
                    swipeRefresh.setEnabled(false);
                    getData(false);
                }
            }
        }, recycyleview);
        mAdapterBondOrder.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {


            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                OrderMaterielVo orderMaterielVo = (OrderMaterielVo) adapter.getData().get(position);
                switch (view.getId()) {
                    case R.id.fl_item:
                    case R.id.tv_one_bond:
                        //一键下发

                        if (!Constants.isLogin) {
                            ToastUtil.showInfoCenterShort(getResources().getString(R.string.st_main_station_login_hint));
                            return;
                        }
                        TextView tvOneBOnd;
                        if (view instanceof TextView) {
                            tvOneBOnd = (TextView) view;
                        } else {
                            tvOneBOnd = view.findViewById(R.id.tv_one_bond);
                        }
                        boolean isBonded = false;//是否已下发
                        if (tvOneBOnd != null && tvOneBOnd.getText().equals(getResources().getString(R.string.text_one_bond))) {
                            isBonded = false;
                        } else {
                            isBonded = true;
                        }
                        Bundle bundle = new Bundle();
                        bundle.putBoolean("isBonded", isBonded);
                        bundle.putString("orderId", orderMaterielVo.getOrderid());
                        bundle.putParcelable("data", orderMaterielVo);
                        startForResult(BondDetailFragment.newInstance(bundle), REQUEST_CODE_TO_BONDDETAILFRAGMENT);
                        if (!orderMaterielVo.isClick()) {
                            orderMaterielVo.setClick(true);
                            mAdapterBondOrder.notifyItemChanged(position);
                        }
                        break;
                }
            }
        });
    }

    private static final int REQUEST_CODE_TO_BONDDETAILFRAGMENT = 1;

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE_TO_BONDDETAILFRAGMENT:
                if (data != null) {
                    boolean isRefresh = data.getBoolean("isRefresh");
                    if (isRefresh) {
                        refreshCurrent();
                    }
                }

                break;
        }

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
                pageNo = 1;
                isLoadEnd = false;

                mAdapterBondOrder.setEnableLoadMore(false);//下拉刷新的时候禁止上拉加载
                pullToRefresh();
                setDate();

            }
        });
    }

    private void setDate() {
        if (TextUtils.isEmpty(searchDate)) {
            tvDate.setText("近三个月");
        } else {
            tvDate.setText(searchDate);

        }
    }

    /**
     * 下拉刷新
     */
    private void pullToRefresh() {
        searchText = "";
        pushToRefreshCurrent();
    }

    /**
     * 刷新当前页 ---loading
     * searchDate != null
     * searchText != null
     */
    public void refreshCurrent() {
        pageNo = 1;
        getData(true);
    }

    /**
     * 刷新近三月 ---loading
     * searchDate == null
     * searchText == null
     */
    public void refreshRecent() {
        pageNo = 1;
        searchDate = "";
        searchText = "";
        getData(true);
    }

    /**
     * 推送刷新
     */
    public void pushToRefreshCurrent() {
        pageNo = 1;
        getData(false);
    }

    private void getData(boolean isShowLoading) {
        mBondOrderPresent.requestBondOrderList(pageNo, pageSize, bondType, searchDate, searchText, isShowLoading);
    }


    @Override
    public void initData() {
        getData(true);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_bond_order;
    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    MvpBasePresenter getPresenter() {
        return null;
    }

    private void showSelectDateDialog(List<String> dateList) {
        if (mSelectDateDialog == null) {
            mSelectDateDialog = DialogUtils.showSelectDateDialog(mActivity, "请选择日期", new DialogCallBackTwo() {
                @Override
                public void OnPositiveClick(Object obj) {
                    if (obj != null && obj instanceof Calendar) {
                        Calendar calendar = (Calendar) obj;
                        searchDate = calendar.getYear() + "-" + (calendar.getMonth() < 10 ? "0" + calendar.getMonth() : calendar.getMonth()) + "-" + (calendar.getDay() < 10 ? "0" + calendar.getDay() : calendar.getDay());
                        setDate();
                        getDataBySearchDate();
                    } else {
                        refreshRecent();
                        setDate();
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

    private void getDataBySearchDate() {
        pageNo = 1;
        searchText = "";
        getData(true);
    }

    private void getDataBySearchText() {
        pageNo = 1;
//        searchDate = "";
        getData(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder1 = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.tv_date_select, R.id.tv_order_select, R.id.tv_cancle, R.id.tv_bond_un, R.id.tv_bonded, R.id.tv_bond_all})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_date_select:
                List<String> dates = new ArrayList<>();
                showSelectDateDialog(dates);
                break;
            case R.id.tv_cancle:
                pop();
                break;
            case R.id.tv_order_select:
                showHandEntrySearchDialog();
                break;
            case R.id.tv_bond_un:
                //未下发
                if (bondType != 1) {
                    bondType = 1;
                    refreshCurrent();
                }
                break;
            case R.id.tv_bonded:
                //已下发
                if (bondType != 2) {
                    bondType = 2;
                    refreshCurrent();
                }
                break;
            case R.id.tv_bond_all:
                //所有
                if (bondType != 3) {
                    bondType = 3;
                    refreshCurrent();
                }
                break;
        }
    }

    private void showHandEntrySearchDialog() {
        DialogUtils.showEntryBarcodeDialog(mActivity, "请输入型号、订单编号、生产序号", new DialogCallBackTwo() {
            @Override
            public void OnPositiveClick(Object obj) {
                if (obj != null) {
                    searchText = (String) obj;
                    getDataBySearchText();
                }

            }

            @Override
            public void OnNegativeClick() {

            }
        });
    }

    private void setTabBg() {
        cleanTabBg();
        switch (bondType) {
            case 1:
                tvBondUn.setBackgroundResource(R.drawable.shape_bong_bg);
//                GradientDrawable drawable_bondun = (GradientDrawable) tvBondUn.getBackground();
//                drawable_bondun.setColor(getResources().getColor(R.color.color_base));
                break;
            case 2:
                tvBonded.setBackgroundResource(R.drawable.shape_bong_bg);
//                GradientDrawable drawable_bonded = (GradientDrawable) tvBonded.getBackground();
//                drawable_bonded.setColor(getResources().getColor(R.color.color_base));
                break;
            case 3:
                tvBondAll.setBackgroundResource(R.drawable.shape_bong_bg);
//                GradientDrawable drawable_bondall = (GradientDrawable) tvBondAll.getBackground();
//                drawable_bondall.setColor(getResources().getColor(R.color.color_base));
                break;
        }
    }

    private void cleanTabBg() {
        tvBondUn.setBackgroundResource(R.drawable.shape_bong_gray_bg);
        tvBonded.setBackgroundResource(R.drawable.shape_bong_gray_bg);
        tvBondAll.setBackgroundResource(R.drawable.shape_bong_gray_bg);
//        GradientDrawable drawable_bondun = (GradientDrawable) tvBondUn.getBackground();
//        drawable_bondun.setColor(getResources().getColor(R.color.task_history));
//        GradientDrawable drawable_bonded = (GradientDrawable) tvBonded.getBackground();
//        drawable_bonded.setColor(getResources().getColor(R.color.task_history));
//        GradientDrawable drawable_bondall = (GradientDrawable) tvBondAll.getBackground();
//        drawable_bondall.setColor(getResources().getColor(R.color.task_history));
    }


    //------------------------------------------------上拉加载
    @Override
    public void showFirstData(List<OrderMaterielVo> datas) {
        if (datas != null) {
            mAdapterBondOrder.setNewData(datas);
        } else {
            mAdapterBondOrder.setNewData(new ArrayList<OrderMaterielVo>());
        }
        int currentCount = mAdapterBondOrder.getItemCount();
        setPage(currentCount - 1);
        hideLoading();
        setRecycleView(datas);
        setTabBg();
    }


    @Override
    public void loadMoreData(List<OrderMaterielVo> datas) {
        if (datas != null) {
            mAdapterBondOrder.addData(datas);
        }
        swipeRefresh.setEnabled(true);
        mAdapterBondOrder.loadMoreComplete();
        int itemCount = mAdapterBondOrder.getItemCount();
        setPage(itemCount - 1);
        setRecycleView(datas);
    }


    @Override
    public void loadError() {
        hideSwipeRefresh();
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

    private void setRecycleView(List<OrderMaterielVo> data) {
        Log.e(TAG, "setRecycleView:" + data.size());
        if (data != null && data.size() < pageSize) {
            isLoadEnd = true;
            removeFooter();
            mAdapterBondOrder.loadMoreEnd(false);
        } else {
            isLoadEnd = false;
            addFooter();
            mAdapterBondOrder.setEnableLoadMore(true);
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
//        toggleShowLoading(false, "");

    }

    private void hideSwipeRefresh() {
        if (swipeRefresh.isRefreshing()) {
            swipeRefresh.setRefreshing(false);
        }
    }

    @OnClick(R.id.tv_order_select)
    public void onViewClicked() {
    }


    //------------------------------------------------

    @Override
    public void setScanResult(String scanResult) {
        if (!TextUtils.isEmpty(scanResult)) {
            searchText = scanResult;
            getDataBySearchText();
        }
    }
}
