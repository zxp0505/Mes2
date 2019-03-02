package workstation.zjyk.line.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
import cn.com.ethank.mylibrary.resourcelibrary.common_util.JsonUtil;
import cn.com.ethank.mylibrary.resourcelibrary.toast.ToastUtil;
import io.reactivex.functions.Consumer;
import workstation.zjyk.line.R;
import workstation.zjyk.line.modle.bean.MaterielInfo;
import workstation.zjyk.line.modle.bean.TrayBean;
import workstation.zjyk.line.modle.bean.WorkStationDistributeMateriel;
import workstation.zjyk.line.modle.net.ErrorCode;
import workstation.zjyk.line.modle.net.ErrorResultClickListner;
import workstation.zjyk.line.ui.activity.MainActivity;
import workstation.zjyk.line.ui.adapter.AdapterFeedThree;
import workstation.zjyk.line.ui.present.FeedThreePresent;
import workstation.zjyk.line.ui.present.MvpBasePresenter;
import workstation.zjyk.line.ui.views.FeedThreeView;
import workstation.zjyk.line.util.Constants;
import workstation.zjyk.line.util.DialogCallBack;
import workstation.zjyk.line.util.DialogCallBackTwo;
import workstation.zjyk.line.util.DialogUtil;
import workstation.zjyk.line.util.dialog.DialogUtils;

/**
 * 分料第三步：绑定分料托盘
 * Created by zjgz on 2017/10/26.
 */

public class FeedThreeFragment extends BaseFragment implements FeedThreeView {
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
    @BindView(R.id.tv_tray_number)
    TextView tvTrayNumber;
    @BindView(R.id.tv_hand_barcode)
    TextView tvHandBarcode;
    private AdapterFeedThree mAdapterFeedThree;
    private List<TrayBean> trayBeans;
    private String requestId;
    private FeedThreePresent mFeedThreePresent;
    private String code;
    private List<WorkStationDistributeMateriel> workStationDistributeMateriels;
    private String stationName;

    public static FeedThreeFragment newInstance(Bundle bundle) {
        FeedThreeFragment feedOneFragment = new FeedThreeFragment();
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
            workStationDistributeMateriels = arguments.getParcelableArrayList("data");
        }
        tvTrayNumber.setText(getString(R.string.text_unbind));
        mFeedThreePresent = new FeedThreePresent(this);
        tvFeedTitle.setText(R.string.feed_order_three);
        tvTitleDescribe.setText(R.string.feed_order_three_describe);
        tvCancle.setText("返回");
        tvNext.setText("确认发放");
        tvHandBarcode.setVisibility(View.VISIBLE);
        tvNext.setVisibility(View.VISIBLE);
        tvNext.setBackgroundResource(R.drawable.shape_makesure_un);
        recycyleview.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapterFeedThree = new AdapterFeedThree();
        trayBeans = new ArrayList<>();
        mAdapterFeedThree.setNewData(workStationDistributeMateriels);
        recycyleview.setAdapter(mAdapterFeedThree);
        RxView.clicks(tvNext).throttleFirst(500, TimeUnit.MILLISECONDS).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                String trayNumber = tvTrayNumber.getText().toString();
                if (getString(R.string.text_unbind).equals(trayNumber)) {
                    ToastUtil.showInfoCenterShort(getString(R.string.text_bind_tray));
                } else {
                    giveOutRequest();
                }
            }
        });
        RxView.clicks(tvHandBarcode).throttleFirst(500, TimeUnit.MILLISECONDS).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                showEntryBarcode();
            }
        });
    }

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

    @Override
    public void initData() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_feedthree;
    }

    @Override
    protected View getLoadingTargetView() {
        return recycyleview;
    }

    @Override
    MvpBasePresenter getPresenter() {
        return null;
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

    @OnClick({R.id.tv_cancle})
    public void clicked(View view) {
        switch (view.getId()) {
            case R.id.tv_cancle:
                pop();
                break;
            case R.id.tv_next:

                break;
        }
    }

    /**
     * 发料请求
     */
    private void giveOutRequest() {
        Map<String, String> params = new HashMap<>();
        params.put("requestId", requestId);//工位需求id
        params.put("distributeCode", code);//仓库发料条码
        params.put("workStationId", Constants.getStationId());//工位名字
        params.put("trayCode", currentScanResult);//托盘码
        params.put("materielInfoStr", getMaterielInfo());//物料信息 jsonarray
        String distributeMaterielInfo = JsonUtil.toJson(params);
        Map<String, String> map = new HashMap<>();
        map.put("distributeMaterielInfo", distributeMaterielInfo);
        mFeedThreePresent.requestMakeSureFeed(map, new ErrorResultClickListner() {
            @Override
            public void confirm(int code) {
                switch (code) {
                    case ErrorCode.CODE_NET_EXCEPTION:
                    case ErrorCode.CODE_NET_SOCKET_TIME_OUT:
                    case ErrorCode.CODE_HTTP_EXCEPTION:
                    case ErrorCode.CODE_UNKNOWN_EXCEPTION:
                        MainActivity activity = (MainActivity) getActivity();
                        activity.showFeedError();
                        break;
                }

            }

            @Override
            public void cancle() {

            }
        });
    }

    private String getMaterielInfo() {
        List<MaterielInfo> datas = new ArrayList<>();
        for (int i = 0; i < workStationDistributeMateriels.size(); i++) {
            WorkStationDistributeMateriel workStationDistributeMateriel = workStationDistributeMateriels.get(i);
            if (workStationDistributeMateriel.getStorageTrueCount() == 0 && workStationDistributeMateriel.getWrapTrueCount() == 0) {
                continue;
            }
            MaterielInfo info = new MaterielInfo();
            info.setMaterielOid(workStationDistributeMateriel.getMaterielModel());
            info.setStorageCount(workStationDistributeMateriel.getStorageTrueCount());
            info.setWrapCount(workStationDistributeMateriel.getWrapTrueCount());
            datas.add(info);
        }
        return JsonUtil.toJson(datas);
    }

    String currentScanResult = "";

    @Override
    public void setScanResult(String scanResult) {
        super.setScanResult(scanResult);
        if (!TextUtils.isEmpty(scanResult)) {
            currentScanResult = scanResult;
            Map<String, String> params = new HashMap<>();
            params.put("code", currentScanResult);
            params.put("requestId", requestId);
            mFeedThreePresent.requestTrayStatus(params);
        }


    }

    private void showDialog1() {
        DialogUtil.showDialog(getActivity(), "您确定要使用托盘\"100001\"盛放物料吗?", new DialogCallBack() {
            @Override
            public void OnPositiveClick() {

            }

            @Override
            public void OnNegativeClick() {

            }
        });
    }

    @Override
    public void showTrayStatus(boolean isSucess) {
        if (isSucess) {
            tvTrayNumber.setText(currentScanResult);
//            tvNext.setVisibility(View.VISIBLE);
            tvNext.setBackgroundResource(R.drawable.shape_makesure);
            ToastUtil.showInfoCenterShort("托盘绑定成功");
//            TrayBean trayBean = new TrayBean();
//            trayBean.setTrayNumber(currentScanResult);
//            trayBeans.clear();
//            trayBeans.add(trayBean);
//            mAdapterFeedThree.notifyDataSetChanged();
//            if (mAdapterFeedThree.getData().size() > 0) {
//                tvNext.setVisibility(View.VISIBLE);
//            }
        } else {
            tvTrayNumber.setText(getString(R.string.text_unbind));
            tvNext.setBackgroundResource(R.drawable.shape_makesure_un);
        }
    }

    @Override
    public void showFeedResult(String result) {
        if (!TextUtils.isEmpty(result)) {
            int resultInt = Integer.parseInt(result);
//            popTo(FeedOneFragment.class, true);
            //请求后台获取剩余的物料状态 还有剩余物料 继续分料
            MainActivity activity = (MainActivity) getActivity();
            activity.showFeedSucessPop("您已成功为" + stationName + "分配了生产物料", resultInt, code);
        }
    }
}
