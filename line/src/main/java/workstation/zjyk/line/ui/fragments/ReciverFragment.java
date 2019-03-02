package workstation.zjyk.line.ui.fragments;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import workstation.zjyk.line.R;
import workstation.zjyk.line.modle.bean.GoodsBean;
import workstation.zjyk.line.ui.adapter.AdapterReciver;
import workstation.zjyk.line.ui.present.MvpBasePresenter;
import workstation.zjyk.line.ui.present.ReciverPresent;
import workstation.zjyk.line.ui.views.ReciverView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 收料fragment
 * Created by zjgz on 2017/10/25.
 */

public class ReciverFragment extends BaseFragment implements ReciverView {
    @BindView(R.id.line_tiaomu)
    LinearLayout lineTiaomu;
    @BindView(R.id.recycyleview)
    RecyclerView recycyleview;
    Unbinder unbinder;
    String[] str_menus = new String[]{"订单生产编号", "物料编号", "物料名称", "批次号", "数量"};
    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout swipeRefresh;
    @BindView(R.id.ll_root)
    LinearLayout llRoot;
    @BindView(R.id.tv_reciver_text)
    TextView tvReciverText;
    private AdapterReciver mAdapterReciver;
    private int pageNo = 1;//当前页码
    private int pageSize = 15;//每页数量
    private boolean isLoadEnd;
    private View mFooterView;
    private ReciverPresent reciverPresent;

    public static ReciverFragment newInstatnce() {
        ReciverFragment reciverFragment = new ReciverFragment();
        return reciverFragment;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        unbinder = ButterKnife.bind(this, view);
        reciverPresent = new ReciverPresent(this);
        initSwipeRefresh();
        mFooterView = LayoutInflater.from(getActivity()).inflate(R.layout.footer_view, null);
        mAdapterReciver = new AdapterReciver();
//        recycyleview.addItemDecoration(new MyDecoration(getActivity(),LinearLayoutManager.VERTICAL));
        recycyleview.setLayoutManager(new LinearLayoutManager(getActivity()));
        recycyleview.setAdapter(mAdapterReciver);
        mAdapterReciver.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                if (isLoadEnd) {
                    mAdapterReciver.loadMoreEnd(false);
                } else {
                    swipeRefresh.setEnabled(false);
                    getData();
                }
            }
        });

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
                mAdapterReciver.setEnableLoadMore(false);//下拉刷新的时候禁止上拉加载
                getData();
            }
        });
    }

    @Override
    public void initData() {
        showLoading("");
        getData();
    }

    private void getData() {
        reciverPresent.loadData(pageNo, pageSize);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_reciver;
    }

    @Override
    protected View getLoadingTargetView() {
        return llRoot;
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

    //------------------------------------------------上拉加载
    @Override
    public void showFirstData(List<GoodsBean> datas) {
        mAdapterReciver.setNewData(datas);
        int currentCount = mAdapterReciver.getItemCount();
        setPage(currentCount - 1);
        hideLoading();
        setRecycleView(datas);
    }


    @Override
    public void loadMoreData(List<GoodsBean> datas) {
        mAdapterReciver.addData(datas);
        swipeRefresh.setEnabled(true);
        mAdapterReciver.loadMoreComplete();
        int itemCount = mAdapterReciver.getItemCount();
        setPage(itemCount - 1);
        setRecycleView(datas);
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

    private void setRecycleView(List<GoodsBean> data) {
        if (data.size() < pageSize) {
            isLoadEnd = true;
            removeFooter();
            mAdapterReciver.loadMoreEnd(false);
        } else {
            isLoadEnd = false;
            addFooter();
            mAdapterReciver.setEnableLoadMore(true);
        }
    }

    private void addFooter() {
        removeFooter();
        mAdapterReciver.addFooterView(mFooterView);
    }

    private void removeFooter() {
        if (mAdapterReciver.getFooterLayoutCount() > 0) {
            mAdapterReciver.removeAllFooterView();
        }
    }

    @Override
    public void hideLoading() {
        if (swipeRefresh.isRefreshing()) {
            swipeRefresh.setRefreshing(false);
        }
        toggleShowLoading(false, "");

    }

    @Override
    MvpBasePresenter getPresenter() {
        return reciverPresent;
    }

    @OnClick(R.id.tv_reciver_text)
    public void onViewClicked() {
//        ((MainActivity)getActivity()).opreation(0);
    }

    //------------------------------------------------
}
