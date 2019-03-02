package workstation.zjyk.line.ui.fragments;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;

import workstation.zjyk.line.R;
import workstation.zjyk.line.modle.bean.WorkStationDistributeMateriel;
import workstation.zjyk.line.ui.adapter.AdapterFeedTwo;
import workstation.zjyk.line.ui.present.FeedTwoPresent;
import workstation.zjyk.line.ui.present.MvpBasePresenter;
import workstation.zjyk.line.ui.views.BaseView;
import workstation.zjyk.line.util.DialogCallBackTwo;
import workstation.zjyk.line.util.dialog.DialogUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.com.ethank.mylibrary.resourcelibrary.toast.ToastUtil;

/**
 * 分料第二步 确认分发物料
 * Created by zjgz on 2017/10/26.
 */

public class FeedTwoFragment extends BaseFragment implements BaseView {
    Unbinder unbinder;
    @BindView(R.id.tv_cancle)
    TextView tvCancle;
    @BindView(R.id.recycyleview)
    RecyclerView recycyleview;
    @BindView(R.id.tv_next)
    TextView tvNext;
    @BindView(R.id.tv_feed_title)
    TextView tvFeedTitle;
    @BindView(R.id.tv_title_describe)
    TextView tvTitleDescribe;
    Unbinder unbinder1;
    private AdapterFeedTwo mAdapterFeedTwo;
    private FeedTwoPresent mFeedTwoPresent;
    private String requestId;
    private String code;
    private String stationName;

    public static FeedTwoFragment newInstance(String code, String requestId, String stationName) {
        Bundle bundle = new Bundle();
        bundle.putString("requestId", requestId);
        bundle.putString("code", code);
        bundle.putString("stationName", stationName);
        FeedTwoFragment feedOneFragment = new FeedTwoFragment();
        feedOneFragment.setArguments(bundle);
        return feedOneFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        unbinder = ButterKnife.bind(this, view);
        Bundle arguments = getArguments();
        if (arguments != null) {
            requestId = arguments.getString("requestId");
            code = arguments.getString("code");
            stationName = arguments.getString("stationName");
        }
        mFeedTwoPresent = new FeedTwoPresent(this);
        tvFeedTitle.setText(R.string.feed_order_two);
        tvTitleDescribe.setText(R.string.feed_order_two_describe);
        tvCancle.setText("返回");
        tvNext.setText("下一步");
        recycyleview.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapterFeedTwo = new AdapterFeedTwo();
        recycyleview.setAdapter(mAdapterFeedTwo);
        mAdapterFeedTwo.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                WorkStationDistributeMateriel materDetailBean = (WorkStationDistributeMateriel) adapter.getData().get(position);
                double materailSurplusBag = materDetailBean.getWrapCount();//包内剩余数量
                double materailSurplusWarehouse = materDetailBean.getStorageCount();//仓内剩余数量
                double materailTureDistrubeBag = materDetailBean.getWrapTrueCount();//包内实际数量
                double materailTureDistrubeWareHouse = materDetailBean.getStorageTrueCount();//仓内实际数量
                switch (view.getId()) {
                    case R.id.iv_reduce_bag:
                        //包内减少
                        if (materailTureDistrubeBag >=1) {
                            materailTureDistrubeBag--;
//                            materailSurplusBag++;
                            materDetailBean.setWrapTrueCount(materailTureDistrubeBag);
//                            materDetailBean.setWrapCount(materailSurplusBag);
                            adapter.notifyItemChanged(position);
                        } else {
                            ToastUtil.showCenterShort("实际分配不能小于0", true);
                        }
                        break;
                    case R.id.iv_add_bag:
                        //包内增加
                        if (materailTureDistrubeBag <= materailSurplusBag-1) {
                            materailTureDistrubeBag++;
//                            materailSurplusBag--;
                            materDetailBean.setWrapTrueCount(materailTureDistrubeBag);
//                            materDetailBean.setWrapCount(materailSurplusBag);
                            adapter.notifyItemChanged(position);
                        } else {
                            ToastUtil.showCenterShort("实际分配不能大于包内剩余总数", true);
                        }
                        break;
                    case R.id.iv_reduce_ware_house:
                        //仓内减少
                        if (materailTureDistrubeWareHouse > 0) {
                            materailTureDistrubeWareHouse--;
//                            materailSurplusWarehouse++;
                            materDetailBean.setStorageTrueCount(materailTureDistrubeWareHouse);
//                            materDetailBean.setStorageCount(materailSurplusWarehouse);
                            adapter.notifyItemChanged(position);
                        } else {
                            ToastUtil.showCenterShort("实际分配不能小于0", true);
                        }
                        break;
                    case R.id.iv_add_ware_house:
                        //仓内添加
                        if (materailTureDistrubeWareHouse < materailSurplusWarehouse) {
                            materailTureDistrubeWareHouse++;
//                            materailSurplusWarehouse--;
                            materDetailBean.setStorageTrueCount(materailTureDistrubeWareHouse);
//                            materDetailBean.setStorageCount(materailSurplusWarehouse);
                            adapter.notifyItemChanged(position);
                        } else {
                            ToastUtil.showCenterShort("实际分配不能大于仓内剩余总数", true);
                        }
                        break;
                    case R.id.tv_true_ware_house:
                        materDetailBean.setClickBag(false);
                        showDialogEntryNumber(materDetailBean);
                        break;
                    case R.id.tv_true_bag:
                        materDetailBean.setClickBag(true);
                        showDialogEntryNumber(materDetailBean);
                        break;
                }
            }
        });
    }

    private void showDialogEntryNumber(WorkStationDistributeMateriel bean) {
        DialogUtils.showDistribuNumberDialog(getActivity(), bean, new DialogCallBackTwo() {
            @Override
            public void OnPositiveClick(Object obj) {
                if (obj != null && obj instanceof WorkStationDistributeMateriel) {
                    WorkStationDistributeMateriel bean = (WorkStationDistributeMateriel) obj;
                    mAdapterFeedTwo.notifyItemChanged(bean.getPosition());
                }

            }

            @Override
            public void OnNegativeClick() {

            }
        });
    }

    @Override
    public void initData() {
        mFeedTwoPresent.requestData(code, requestId);
    }

    @Override
    public void showNetData(Object o) {
        super.showNetData(o);
        if (o != null && o instanceof List) {
            List<WorkStationDistributeMateriel> datas = (List<WorkStationDistributeMateriel>) o;
            for (int i = 0; i < datas.size(); i++) {
                setTureDistributionCount(datas.get(i));
            }
            mAdapterFeedTwo.setNewData(datas);
        }
    }

    private void setTureDistributionCount(WorkStationDistributeMateriel item) {
        double require = item.getCount();//需要数量
        double wrapCount = item.getWrapCount();//包内剩余
        double storageCount = item.getStorageCount();//仓内剩余
        double wrapTrueCount = 0;//包内实际
        double storageTrueCount = 0;//仓内实际
        //优先分配包内剩余 包内不够了 分配仓内的
        if (wrapCount > require) {
            //包内大于需求 使用包内
            wrapTrueCount = require;
            storageTrueCount = 0;
        } else {
            //包内小于需求 使用仓内
            wrapTrueCount = wrapCount;
            if (storageCount > (require - wrapCount)) {
                storageTrueCount = require - wrapCount;
            } else {
                storageTrueCount = storageCount;
            }
        }
        item.setWrapTrueCount(wrapTrueCount);
        item.setStorageTrueCount(storageTrueCount);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_feedtwo;
    }

    @Override
    protected View getLoadingTargetView() {
        return recycyleview;
    }

    @Override
    MvpBasePresenter getPresenter() {
        return mFeedTwoPresent;
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

    @OnClick({R.id.tv_cancle, R.id.tv_next})
    public void clicked(View view) {
        switch (view.getId()) {
            case R.id.tv_cancle:
                pop();
                break;
            case R.id.tv_next:
                List<WorkStationDistributeMateriel> giveOutData = getGiveOutData();
                if (giveOutData != null && giveOutData.size() > 0) {
                    Bundle bundle = new Bundle();
                    bundle.putString("requestId", requestId);//工位id
                    bundle.putString("code", code);//包裹条码
                    bundle.putString("stationName", stationName);//工位名字
                    bundle.putParcelableArrayList("data", (ArrayList<? extends Parcelable>) giveOutData);
                    FeedThreeFragment feedThreeFragment = FeedThreeFragment.newInstance(bundle);
                    start(feedThreeFragment);
                } else {
                    ToastUtil.showCenterShort("分配的数量必须大于0", true);
                }
                break;
        }
    }

    private List<WorkStationDistributeMateriel> getGiveOutData() {
        List<WorkStationDistributeMateriel> data = mAdapterFeedTwo.getData();
        List<WorkStationDistributeMateriel> dataXin = new ArrayList<>();
        if (data == null && data.size() < 1) {
            return null;
        }
        double giveOutCount = 0;//实际分配数量为0  停止下一步
        for (int i = 0; i < data.size(); i++) {
            WorkStationDistributeMateriel workStationDistributeMateriel = data.get(i);
            double storageTrueCount = workStationDistributeMateriel.getStorageTrueCount();
            double wrapTrueCount = workStationDistributeMateriel.getWrapTrueCount();
            if(wrapTrueCount >0){
                dataXin.add(workStationDistributeMateriel);
            }
            giveOutCount = giveOutCount + (storageTrueCount + wrapTrueCount);
        }
        if (giveOutCount > 0) {
            return dataXin;
        } else {
            return null;
        }
    }
}


