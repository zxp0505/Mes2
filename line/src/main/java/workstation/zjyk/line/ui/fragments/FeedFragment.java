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
import cn.com.ethank.mylibrary.resourcelibrary.toast.ToastUtil;
import workstation.zjyk.line.R;
import workstation.zjyk.line.modle.bean.ReceiveEnum;
import workstation.zjyk.line.modle.bean.Wrap;
import workstation.zjyk.line.ui.activity.MainActivity;
import workstation.zjyk.line.ui.adapter.AdapterFeed;
import workstation.zjyk.line.ui.present.FeedPresent;
import workstation.zjyk.line.ui.present.MvpBasePresenter;
import workstation.zjyk.line.ui.views.FeedView;
import workstation.zjyk.line.util.Constants;
import workstation.zjyk.line.util.DialogCallBackTwo;
import workstation.zjyk.line.util.dialog.DialogUtils;

/**
 * 分料fragment
 * Created by zjgz on 2017/10/26.
 */

public class FeedFragment extends BaseFragment implements FeedView {
    @BindView(R.id.recycyleview)
    RecyclerView recycyleview;
    @BindView(R.id.swiperefresh)
    SwipeRefreshLayout swipeRefresh;
    Unbinder unbinder;
    @BindView(R.id.ll_root)
    LinearLayout llRoot;
    //    @BindView(R.id.tv_reciver_text)
//    TextView tvReciverText;
    @BindView(R.id.tv_hand_barcode)
    TextView tvHandBarcode;
    Unbinder unbinder1;
    @BindView(R.id.tv_one_bond)
    TextView tvOneBond;
    private int pageNo = 1;//当前页码
    private int pageSize = 15;//每页数量
    private boolean isLoadEnd;
    private View mFooterView;
    private AdapterFeed mAdapterFeed;
    private FeedPresent mFeedPresent;
    Map<String, String> barCodeMap = new HashMap<>();//存储条码的map

    public static FeedFragment newInstance() {
        FeedFragment feedFragment = new FeedFragment();
        return feedFragment;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        unbinder = ButterKnife.bind(this, view);
        mFeedPresent = new FeedPresent(this);
        tvOneBond.setVisibility(View.VISIBLE);
        initSwipeRefresh();
        mFooterView = LayoutInflater.from(getActivity()).inflate(R.layout.footer_view, null);
        mAdapterFeed = new AdapterFeed();
        recycyleview.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        recycyleview.setAdapter(mAdapterFeed);
        mAdapterFeed.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                if (isLoadEnd) {
                    mAdapterFeed.loadMoreEnd(false);
                } else {
                    swipeRefresh.setEnabled(false);
                    getData();
                }
            }
        });
        mAdapterFeed.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.iv_one:
                        if (Constants.isLogin) {
                            Wrap wrap = (Wrap) adapter.getData().get(position);
                            String code = wrap.getCode();
                            mFeedPresent.getBarCodeStatus(code, true);
                        } else {
                            ToastUtil.showCenterShort(getResources().getString(R.string.text_un_login), true);
                        }
                        break;
                }

            }
        });


    }

    @Override
    public void initData() {
        refreshData();
//        getData();
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_feed;
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
                mAdapterFeed.setEnableLoadMore(false);//下拉刷新的时候禁止上拉加载
//                getData();
                refreshData();
            }
        });
    }

    private void getData() {
        mFeedPresent.requestReciverRecord(pageNo, pageSize, true);
    }

    /**
     * 刷新页面
     */
    public void refreshData() {

        pageNo = 1;
        mFeedPresent.requestReciverRecord(pageNo, pageSize, false);
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

    //处理扫描结果
    @Override
    public void setScanResult(String scanResult) {
        super.setScanResult(scanResult);
//        if(!TextUtils.isEmpty(scanResult) && !TextUtils.isEmpty(currentBarCode) && scanResult.equals(currentBarCode)){
//            if(recycyleview.getVisibility() == View.VISIBLE){
//                EventBus.getDefault().post(new MessageBean(0));
//            }else{
//                ToastUtil.showCenterShort("扫码过快，请稍等...");
//            }
//        }else{
        mFeedPresent.getBarCodeStatus(scanResult, false);
//        }
    }

    //------------------------------------------------上拉加载
    @Override
    public void showFirstData(List<Wrap> datas) {
        if (datas != null) {
            mAdapterFeed.setNewData(datas);
        } else {
            mAdapterFeed.setNewData(new ArrayList<Wrap>());
        }
        int currentCount = mAdapterFeed.getItemCount();
        setPage(currentCount - 1);
        hideLoading();
        setRecycleView(datas);
    }


    @Override
    public void loadMoreData(List<Wrap> datas) {
        if (datas != null) {
            mAdapterFeed.addData(datas);
        }
        swipeRefresh.setEnabled(true);
        mAdapterFeed.loadMoreComplete();
        int itemCount = mAdapterFeed.getItemCount();
        setPage(itemCount - 1);
        setRecycleView(datas);
    }


    String currentBarCode;//记录当前已扫出的编码

    @Override
    public void showScanResult(Wrap wrap) {
        if (wrap != null) {
            if (wrap.getReceiveStatus() == ReceiveEnum.UNRECEIVED) {
                //未接收 展示收料信息
                ((MainActivity) getActivity()).opreation(0, wrap);
            } else if (wrap.getReceiveStatus() == ReceiveEnum.RECEIVED) {
                //已接收 显示分料信息界面
                ((MainActivity) getActivity()).opreation(1, wrap);
            } else if (wrap.getReceiveStatus() == ReceiveEnum.DELIVER) {
                ((MainActivity) getActivity()).opreation(2, wrap);
            } else if (wrap.getReceiveStatus() == ReceiveEnum.EXCEPTION) {
                ((MainActivity) getActivity()).opreation(3, wrap);
            }
        }


    }


    @Override
    public void loadError() {
        hideSwipeRefresh();
    }


    /**
     * 从收料dialog点击确认收料过来的数据
     *
     * @param
     */
    public void addScanResult(Wrap wrap) {
        refreshData();
//        if (wrap != null) {
//            List<Wrap> dataResult = new ArrayList<>();
//            dataResult.add(wrap);
//            List<Wrap> data = mAdapterFeed.getData();
//            if (data == null) {
//                mAdapterFeed.setNewData(data);
//                showFirstData(dataResult);
//            } else {
//                //避免推送优先过来
//                if (data.size() < pageSize && !data.contains(wrap)) {
//                    loadMoreData(dataResult);
//                }
//            }
//        }
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

    private void setRecycleView(List<Wrap> data) {
        if (data != null && data.size() < pageSize) {
            isLoadEnd = true;
            removeFooter();
            mAdapterFeed.loadMoreEnd(false);
        } else {
            isLoadEnd = false;
            addFooter();
            mAdapterFeed.setEnableLoadMore(true);
        }
    }

    private void addFooter() {
        removeFooter();
        mAdapterFeed.addFooterView(mFooterView);
    }

    private void removeFooter() {
        if (mAdapterFeed.getFooterLayoutCount() > 0) {
            mAdapterFeed.removeAllFooterView();
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
        return mFeedPresent;
    }

    @OnClick({R.id.tv_hand_barcode, R.id.tv_one_bond})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_hand_barcode:
                if (Constants.isLogin) {
                    showEntryBarcode();
                } else {
                    ToastUtil.showCenterShort(getResources().getString(R.string.text_un_login), true);
                }
                break;
            case R.id.tv_one_bond:
                //一键下发
                start(BondOrderFragment.newInstance());
                break;
        }
    }


    //------------------------------------------------

    private void showEntryBarcode() {
        DialogUtils.showEntryBarcodeDialog(getActivity(), getString(R.string.text_hand_entry_barcode), new DialogCallBackTwo() {
            @Override
            public void OnPositiveClick(Object obj) {
                if (obj != null) {
                    String barcode = (String) obj;
                    if (mActivity != null && mActivity instanceof MainActivity) {
                        ((MainActivity) mActivity).onScanResult(barcode.trim());
                    }
                }

            }

            @Override
            public void OnNegativeClick() {

            }
        });
    }
}
