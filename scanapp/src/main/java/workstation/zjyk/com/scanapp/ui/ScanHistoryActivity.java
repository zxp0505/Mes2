package workstation.zjyk.com.scanapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.ethank.mylibrary.resourcelibrary.intent.StartIntentUtils;
import pub.devrel.easypermissions.AppSettingsDialogHolderActivity;
import workstation.zjyk.com.scanapp.R;
import workstation.zjyk.com.scanapp.modle.bean.QualityHandledRecordVO;
import workstation.zjyk.com.scanapp.ui.adapter.AdapterQAHistory;
import workstation.zjyk.com.scanapp.ui.present.ScanHistoryPresent;
import workstation.zjyk.com.scanapp.ui.views.ScanHistoryView;
import workstation.zjyk.com.scanapp.util.BundleParams;
import workstation.zjyk.com.scanapp.util.dialog.ScanDialogUtils;
import workstation.zjyk.com.scanapp.util.dialog.callback.ScanDialogCallBackTwo;

public class ScanHistoryActivity extends ScanBaseActivity<ScanHistoryPresent> implements ScanHistoryView {
    @BindView(R.id.recycyleview)
    RecyclerView recycyleview;
    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout swipeRefresh;
    private AdapterQAHistory mAdapterQAHistory;
    private ArrayList<QualityHandledRecordVO> historyList;

    private int pageNo = 1;//当前页码
    private int pageSize = 8;//每页数量
    private boolean isLoadEnd;
    private String searchText = "";

    @Override
    protected void creatPresent() {
        currentPresent = new ScanHistoryPresent();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_history;
    }

    @Override
    protected View getLoadingTargetView() {
        return mFlContent;
    }

    @Override
    public void initOnCreate() {
        initSwipeRefresh();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            historyList = bundle.getParcelableArrayList(BundleParams.DATA);
        }
        mBtRightOne.setVisibility(View.VISIBLE);
        mBtRightTwo.setVisibility(View.VISIBLE);
        mBtRightOne.setOnClickListener(this);
        mBtRightTwo.setOnClickListener(this);
        tvTitle.setText("历史记录");
        mAdapterQAHistory = new AdapterQAHistory();
        recycyleview.setLayoutManager(new GridLayoutManager(this, 1));
        recycyleview.setAdapter(mAdapterQAHistory);
        mAdapterQAHistory.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                QualityHandledRecordVO qualityHandledRecordVO = (QualityHandledRecordVO) adapter.getData().get(position);
                switch (view.getId()) {
                    case R.id.fl_item:
                        TextView handleTv = view.findViewById(R.id.tv_handle_date);
                        Bundle bundle = new Bundle();
                        bundle.putString(BundleParams.RECOEDID, qualityHandledRecordVO.getBarCode());
                        bundle.putString(BundleParams.HANDLE_TIME, handleTv.getText().toString().trim());
                        bundle.putBoolean(BundleParams.TODETAIL, true);
                        StartIntentUtils.startIntentUtils(ScanHistoryActivity.this, ScanResultAboutPictrueActivity.class, bundle);
                        break;
                }
            }
        });
        if (historyList != null && historyList.size() < pageSize) {
            isLoadEnd = true;
        }

        mAdapterQAHistory.setNewData(historyList);

        mAdapterQAHistory.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                if (isLoadEnd) {
                    mAdapterQAHistory.loadMoreEnd(false);
                } else {
                    swipeRefresh.setEnabled(false);
                    getData(false);
                }
            }
        }, recycyleview);

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
                mAdapterQAHistory.setEnableLoadMore(false);//下拉刷新的时候禁止上拉加载
                pullToRefresh();

            }
        });
    }

    private void pullToRefresh() {
        searchText = "";
        getData(false);
    }

    private void getData(boolean isShowloading) {
        currentPresent.requestHistoryList(pageNo, pageSize, "", searchText, isShowloading);
    }

    private void getDataBySearchText() {
        pageNo = 1;
        getData(true);
    }

    //------------------------------------------------上拉加载
    @Override
    public void showFirstData(List<QualityHandledRecordVO> datas) {
        if (datas != null) {
            mAdapterQAHistory.setNewData(datas);
        } else {
            mAdapterQAHistory.setNewData(new ArrayList<QualityHandledRecordVO>());
        }
        int currentCount = mAdapterQAHistory.getItemCount();
        setPage(currentCount - 1);
        hideLoading();
        setRecycleView(datas);
    }


    @Override
    public void loadMoreData(List<QualityHandledRecordVO> datas) {
        if (datas != null) {
            mAdapterQAHistory.addData(datas);
        }
        swipeRefresh.setEnabled(true);
        mAdapterQAHistory.loadMoreComplete();
        int itemCount = mAdapterQAHistory.getItemCount();
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


    private void setRecycleView(List<QualityHandledRecordVO> data) {
        if (data != null && data.size() < pageSize) {
            isLoadEnd = true;
            removeFooter();
            mAdapterQAHistory.loadMoreEnd(false);
        } else {
            isLoadEnd = false;
            addFooter();
            mAdapterQAHistory.setEnableLoadMore(true);
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
        super.hideLoading();
        hideSwipeRefresh();
//        toggleShowLoading(false, "");

    }

    private void hideSwipeRefresh() {
        if (swipeRefresh.isRefreshing()) {
            swipeRefresh.setRefreshing(false);
        }
    }


    //------------------------------------------------


    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.bt_right_one:
                Bundle bundle = new Bundle();
                bundle.putInt(BundleParams.FLAG, 1);
                StartIntentUtils.startIntentUtils(ScanHistoryActivity.this, ScanCode2Activity.class, bundle);
                break;
            case R.id.bt_right_two:
                showEntryCodeDialog();
                break;
        }
    }

    private void showEntryCodeDialog() {
        ScanDialogUtils.showEntryCodeDialog(this, "请输入型号、订单编号进行搜索", new ScanDialogCallBackTwo() {
            @Override
            public void OnPositiveClick(Object obj) {
                if (obj != null && obj instanceof String) {
                    String str = (String) obj;
                    searchText = str;
                    getDataBySearchText();
                }
            }

            @Override
            public void OnNegativeClick() {

            }
        });
    }
}
