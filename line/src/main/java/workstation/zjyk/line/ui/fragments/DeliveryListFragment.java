package workstation.zjyk.line.ui.fragments;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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
import cn.com.ethank.mylibrary.resourcelibrary.intent.StartIntentUtils;
import cn.com.ethank.mylibrary.resourcelibrary.toast.ToastUtil;
import workstation.zjyk.line.R;
import workstation.zjyk.line.modle.bean.ReceiveEnum;
import workstation.zjyk.line.modle.bean.TrayVo;
import workstation.zjyk.line.modle.bean.Wrap;
import workstation.zjyk.line.ui.activity.MainActivity;
import workstation.zjyk.line.ui.activity.TrayInfoActivity;
import workstation.zjyk.line.ui.adapter.AdapterDeliveryList;
import workstation.zjyk.line.ui.adapter.AdapterFeed;
import workstation.zjyk.line.ui.present.DeliveryListPresent;
import workstation.zjyk.line.ui.present.FeedPresent;
import workstation.zjyk.line.ui.present.MvpBasePresenter;
import workstation.zjyk.line.ui.views.DeliveryListView;

/**
 * 可投递托盘列表
 * Created by zjgz on 2017/12/4.
 */

public class DeliveryListFragment extends BaseFragment implements DeliveryListView {
    @BindView(R.id.recycyleview)
    RecyclerView recycyleview;
    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout swipeRefresh;
    Unbinder unbinder;
    @BindView(R.id.ll_root)
    LinearLayout llRoot;
    @BindView(R.id.tv_reciver_text)
    TextView tvReciverText;
    private int pageNo = 1;//当前页码
    private int pageSize = 15;//每页数量
    private boolean isLoadEnd;
    private View mFooterView;
    //    private AdapterFeed mAdapterFeed;
    Map<String, String> barCodeMap = new HashMap<>();//存储条码的map
    private AdapterDeliveryList mAdapterDeliveryList;
    private DeliveryListPresent mDeliveryListPresent;

    public static DeliveryListFragment newInstance() {
        DeliveryListFragment fragment = new DeliveryListFragment();
        return fragment;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        unbinder = ButterKnife.bind(this, view);
        mDeliveryListPresent = new DeliveryListPresent(this);
        initSwipeRefresh();
        mFooterView = LayoutInflater.from(getActivity()).inflate(R.layout.footer_view, null);
        mAdapterDeliveryList = new AdapterDeliveryList();
        recycyleview.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        recycyleview.setAdapter(mAdapterDeliveryList);
        mAdapterDeliveryList.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                if (isLoadEnd) {
                    mAdapterDeliveryList.loadMoreEnd(false);
                } else {
                    swipeRefresh.setEnabled(false);
                    getData();
                }
            }
        });
        mAdapterDeliveryList.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                TrayVo trayVo = (TrayVo) adapter.getData().get(position);
                switch (view.getId()) {
                    case R.id.iv_tray:
                        Map<String, String> params = new HashMap<>();
                        params.put("code", trayVo.getOneDemensionCode());
                        mDeliveryListPresent.requestTrayDetail(params);
                        break;
                }
            }
        });

    }

    @Override
    public void initData() {
//        refreshData();
        getData();
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_delivery_list;
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
                pageNo = 1;
                isLoadEnd = false;
                mAdapterDeliveryList.setEnableLoadMore(false);//下拉刷新的时候禁止上拉加载
                refreshData();
//                getData();
            }
        });
    }

    private void getData() {
        mDeliveryListPresent.requestDeliveryList(pageNo, pageSize,true);
    }

    /**
     * 刷新页面
     */
    public void refreshData() {
        pageNo = 1;
        mDeliveryListPresent.requestDeliveryList(pageNo, pageSize,false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    //处理扫描结果
    @Override
    public void setScanResult(String scanResult) {
        super.setScanResult(scanResult);
    }

    //------------------------------------------------上拉加载
    @Override
    public void showFirstData(List<TrayVo> datas) {
        if (datas != null) {
            if (datas.size() == 0) {
                ToastUtil.showInfoCenterShort("没有需要投递的托盘");
            }
            mAdapterDeliveryList.setNewData(datas);
        } else {
            mAdapterDeliveryList.setNewData(new ArrayList<TrayVo>());
        }
        int currentCount = mAdapterDeliveryList.getItemCount();
        setPage(currentCount - 1);
        hideLoading();
        setRecycleView(datas);
    }


    @Override
    public void loadMoreData(List<TrayVo> datas) {
        if (datas != null) {
            mAdapterDeliveryList.addData(datas);
        }
        swipeRefresh.setEnabled(true);
        mAdapterDeliveryList.loadMoreComplete();
        int itemCount = mAdapterDeliveryList.getItemCount();
        setPage(itemCount - 1);
        setRecycleView(datas);
    }


    String currentBarCode;//记录当前已扫出的编码


    @Override
    public void loadError() {
        hideSwipeRefresh();
    }

    @Override
    public void showTrayInfo(Wrap wrap) {
        if (wrap != null) {
            //TODO 展示详情
            Bundle bundle = new Bundle();
            bundle.putParcelable("data", wrap);
            StartIntentUtils.startIntentUtils(getActivity(), TrayInfoActivity.class, bundle);
        }
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

    private void setRecycleView(List<TrayVo> data) {
        if (data != null && data.size() < pageSize) {
            isLoadEnd = true;
            removeFooter();
            mAdapterDeliveryList.loadMoreEnd(false);
        } else {
            isLoadEnd = false;
            addFooter();
            mAdapterDeliveryList.setEnableLoadMore(true);
        }
    }

    private void addFooter() {
        removeFooter();
        mAdapterDeliveryList.addFooterView(mFooterView);
    }

    private void removeFooter() {
        if (mAdapterDeliveryList.getFooterLayoutCount() > 0) {
            mAdapterDeliveryList.removeAllFooterView();
        }
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

    @Override
    MvpBasePresenter getPresenter() {
        return mDeliveryListPresent;
    }

    @OnClick(R.id.tv_reciver_text)
    public void onViewClicked() {

    }

    //------------------------------------------------
}
