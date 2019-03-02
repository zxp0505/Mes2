package workstation.zjyk.line.ui.fragments;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;

import workstation.zjyk.line.R;
import workstation.zjyk.line.modle.bean.WorkStationRequest;
import workstation.zjyk.line.modle.bean.WorkStationRequestDetail;
import workstation.zjyk.line.ui.activity.MainActivity;
import workstation.zjyk.line.ui.activity.StationRequireActivity;
import workstation.zjyk.line.ui.adapter.AdapterSelectStation;
import workstation.zjyk.line.ui.present.FeedOnePresent;
import workstation.zjyk.line.ui.present.MvpBasePresenter;
import workstation.zjyk.line.ui.views.FeedOneView;

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

/**
 * 分料第一步 选择工位
 * Created by zjgz on 2017/10/26.
 */

public class FeedOneFragment extends BaseFragment implements FeedOneView {
    @BindView(R.id.recycyleview)
    RecyclerView recycyleview;
    @BindView(R.id.tv_next)
    TextView tvNext;
    Unbinder unbinder;
    @BindView(R.id.tv_feed_title)
    TextView tvFeedTitle;
    @BindView(R.id.tv_cancle)
    TextView tvCancle;
    @BindView(R.id.tv_title_describe)
    TextView tvTitleDescribe;
    @BindView(R.id.tv_station_count)
    TextView tvStationCount;
    Unbinder unbinder1;
    private AdapterSelectStation mAdapterSelectStation;
    private FeedOnePresent mFeedOnePresent;
    private String wrapid;//包裹id
    private List<WorkStationRequest> workStationRequests;


    public static FeedOneFragment newInstance(Bundle bundle) {
        FeedOneFragment feedOneFragment = new FeedOneFragment();
        feedOneFragment.setArguments(bundle);
        return feedOneFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    WorkStationRequest selectStationBean = null;//已选工位bean

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        unbinder = ButterKnife.bind(this, view);
        Bundle arguments = getArguments();
        if (arguments != null) {
            wrapid = arguments.getString("wrapid");
            workStationRequests = arguments.getParcelableArrayList("data");
        }
        tvFeedTitle.setText(R.string.feed_order_one);
        tvTitleDescribe.setText(R.string.feed_order_one_describe);
        mFeedOnePresent = new FeedOnePresent(this);
        recycyleview.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        mAdapterSelectStation = new AdapterSelectStation();
        recycyleview.setAdapter(mAdapterSelectStation);
        if (workStationRequests != null) {
            WorkStationRequest firstStation = workStationRequests.get(0);
            firstStation.setSelect(true);
            selectStationBean = firstStation;
            mAdapterSelectStation.setNewData(workStationRequests);
        }
        mAdapterSelectStation.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                WorkStationRequest stationBean = (WorkStationRequest) adapter.getData().get(position);
                switch (view.getId()) {
                    case R.id.tv_station_require_detail:
                        //工位需求详情
                        Map<String, String> params = new HashMap<>();
                        params.put("requestId", stationBean.getRequestId());
                        mFeedOnePresent.requestMaterailDetail(params);
                        break;
                    case R.id.ll_item:
                        //点击单个item
                        if (selectStationBean != null && position != selectStationBean.getPosition()) {
                            selectStationBean.setSelect(false);
                            adapter.notifyItemChanged(selectStationBean.getPosition());
                        }
                        stationBean.setSelect(!stationBean.isSelect());
                        adapter.notifyItemChanged(position);
                        selectStationBean = stationBean;
                        break;
                }
            }
        });
    }

    @Override
    public void initData() {
        //请求已经在物料清单界面进行过滤了
//        mFeedOnePresent.requestData(wrapid);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_feedone;
    }

    @Override
    protected View getLoadingTargetView() {
        return recycyleview;
    }

    @Override
    MvpBasePresenter getPresenter() {
        return mFeedOnePresent;
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

    @OnClick({R.id.tv_next, R.id.tv_cancle})
    public void clicked(View view) {
        switch (view.getId()) {
            case R.id.tv_next:
                if (selectStationBean != null && !TextUtils.isEmpty(selectStationBean.getRequestId()) && selectStationBean.isSelect()) {
                    FeedTwoFragment feedTwoFragment = FeedTwoFragment.newInstance(wrapid, selectStationBean.getRequestId(), selectStationBean.getWorkStationName());
                    start(feedTwoFragment);
                } else {
                    ToastUtil.showCenterShort("请选择工位", true);
                }

                break;
            case R.id.tv_cancle:
                if (getActivity() instanceof MainActivity) {
                    MainActivity activity = (MainActivity) getActivity();
                    activity.opreationMaterDetailDialogActivity();
                }
                pop();
                break;
        }
    }

    @Override
    public void showStationDatas(List<WorkStationRequest> datas) {
        if (datas != null) {
            mAdapterSelectStation.setNewData(datas);
            tvStationCount.setVisibility(View.VISIBLE);
            tvStationCount.setText("共" + datas.size() + "个");
        }
    }

    @Override
    public void showStationRequireDetails(List<WorkStationRequestDetail> datas) {
        if (datas != null) {
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList("data", (ArrayList<? extends Parcelable>) datas);
            StartIntentUtils.startIntentUtils(getActivity(), StationRequireActivity.class, bundle);
        }
    }

}
