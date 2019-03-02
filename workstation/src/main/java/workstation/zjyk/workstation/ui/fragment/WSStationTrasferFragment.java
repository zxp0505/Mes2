package workstation.zjyk.workstation.ui.fragment;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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
import me.yokeyword.fragmentation.ISupportFragment;
import workstation.zjyk.workstation.R;
import workstation.zjyk.workstation.modle.bean.WSWorkStationInfo;
import workstation.zjyk.workstation.modle.bean.WSWorkStationTask;
import workstation.zjyk.workstation.ui.WSMainActivity;
import workstation.zjyk.workstation.ui.adapter.WSAdapterStationTransfer;
import workstation.zjyk.workstation.ui.manager.WSUserManager;
import workstation.zjyk.workstation.ui.present.WSStationTrasferPresent;
import workstation.zjyk.workstation.ui.views.WSStationTrasferView;

/**
 * 工位转移
 * Created by zjgz on 2017/12/12.
 */

public class WSStationTrasferFragment extends WSBaseFragment<WSStationTrasferPresent> implements WSStationTrasferView {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rl_title_two)
    RelativeLayout rlTitleTwo;
    @BindView(R.id.tv_title_left)
    TextView tvTitleLeft;
    @BindView(R.id.tv_quit)
    TextView tvQuit;
    @BindView(R.id.rl_title_three)
    RelativeLayout rlTitleThree;
    @BindView(R.id.recycleview)
    RecyclerView recycleview;
    Unbinder unbinder;
    @BindView(R.id.tv_report_confirm)
    TextView tvReportConfirm;
    @BindView(R.id.rl_title_one)
    RelativeLayout rlTitleOne;
    @BindView(R.id.iv_back_three)
    ImageView ivBackThree;
    @BindView(R.id.tv_hand_barcode_three)
    TextView tvHandBarcodeThree;
    private Unbinder bind;
    private WSAdapterStationTransfer mAdapterStationTransfer;
    WSWorkStationInfo currentBean;
    private WSWorkStationTask wsWorkStationTask;
    private List<WSWorkStationInfo> wsWorkStationInfos;

    public static ISupportFragment newInstance(Bundle bundle) {
        WSStationTrasferFragment wsStationTrasferFragment = new WSStationTrasferFragment();
        wsStationTrasferFragment.setArguments(bundle);
        return wsStationTrasferFragment;
    }

    @Override
    protected void creatPresent() {
        currentPresent = new WSStationTrasferPresent();
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        bind = ButterKnife.bind(this, view);
        rlTitleOne.setVisibility(View.GONE);
        rlTitleTwo.setVisibility(View.GONE);
        rlTitleThree.setVisibility(View.VISIBLE);
        ivBackThree.setOnClickListener(this);
        tvQuit.setVisibility(View.GONE);
        tvHandBarcodeThree.setVisibility(View.GONE);
        tvTitleLeft.setText("请选择转移工位:");
        tvReportConfirm.setTag(false);
        setConfirmBg();
        Bundle arguments = getArguments();
        if (arguments != null) {
            wsWorkStationTask = arguments.getParcelable("data");
            wsWorkStationInfos = arguments.getParcelableArrayList("stationlist");
        }
        mAdapterStationTransfer = new WSAdapterStationTransfer();
        recycleview.setLayoutManager(new LinearLayoutManager(getActivity()));
        recycleview.setAdapter(mAdapterStationTransfer);
        if (wsWorkStationInfos != null) {
            mAdapterStationTransfer.setNewData(wsWorkStationInfos);
        }
        mAdapterStationTransfer.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                WSWorkStationInfo bean = (WSWorkStationInfo) adapter.getData().get(position);
                switch (view.getId()) {
                    case R.id.iv_select_status:
                        bean.setSelect(!bean.isSelect());
                        adapter.notifyItemChanged(bean.getPosition());
                        if (currentBean != null && (!currentBean.getName().equals(bean.getName()))) {
                            currentBean.setSelect(false);
                            adapter.notifyItemChanged(currentBean.getPosition());
                        }
                        currentBean = bean;
                        setConfirmBg();
                        break;
                }
            }
        });

    }

    private void setConfirmBg() {
        boolean isClickable = (boolean) tvReportConfirm.getTag();
        if (currentBean != null) {
            if (currentBean.isSelect()) {
                if (!isClickable) {
                    tvReportConfirm.setBackgroundResource(R.drawable.shape_blue);
                    tvReportConfirm.setTag(true);
                }
            } else {
                if (isClickable) {
                    tvReportConfirm.setBackgroundResource(R.drawable.shape_gray_bg);
                    tvReportConfirm.setTag(false);
                }
            }
        } else {
            tvReportConfirm.setBackgroundResource(R.drawable.shape_gray_bg);
            tvReportConfirm.setTag(false);
        }
    }


    @Override
    public void initData() {
//        requesStationTrasferList();

    }

    private void requesStationTrasferList() {
        Map<String, String> params = new HashMap<>();
        params.put("taskId", wsWorkStationTask.getId());
        currentPresent.requesStationTrasferList(params);
    }

    private void confirmStationTrasfer() {
        Map<String, String> params = new HashMap<>();
        params.put("workStationTaskId", wsWorkStationTask.getId());
        params.put("workStationId", currentBean.getId());
        currentPresent.confirmStationTrasfer(params);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_station_transfer;
    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        bind.unbind();
    }

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        // TODO: inflate a fragment view
//        View rootView = super.onCreateView(inflater, container, savedInstanceState);
//        unbinder = ButterKnife.bind(this, rootView);
//        return rootView;
//    }

//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        unbinder.unbind();
//    }

    @OnClick({R.id.tv_quit, R.id.tv_report_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_quit:
                pop();
                break;
            case R.id.tv_report_confirm:
                boolean isClickable = (boolean) tvReportConfirm.getTag();
                if (isClickable) {
                    confirmStationTrasfer();
                } else {
                    ToastUtil.showInfoCenterShort("请先选择转移工位!");
                }

                break;
        }
    }


    @Override
    public void setFragmentBack() {
        super.setFragmentBack();
    }

    @Override
    public void setActivityLogoOrBack() {
        if (mActivity != null && mActivity instanceof WSMainActivity) {
            ((WSMainActivity) mActivity).setActivityLogoOrBack(1);
        }
    }

    @Override
    public void showStationList(List<WSWorkStationInfo> datas) {
        if (datas != null) {
            if (datas.size() == 0) {
                ToastUtil.showInfoCenterShort("没有可以转移的工位!");
            } else {
                mAdapterStationTransfer.setNewData(datas);
            }
        }


    }

    @Override
    public void showStationTrasferResult(boolean result) {
        if (result) {
            ToastUtil.showInfoCenterShort("任务转移成功");
            quitRefresh();
//            setFragmentBack();
        }
    }

    private void quitRefresh() {
        Bundle bundle = new Bundle();
        bundle.putBoolean("isRefresh", true);
        setFragmentResult(0, bundle);
        clickBack();
    }


    @Override
    public void closeCurrent() {
        clickBack();
    }
}
