package workstation.zjyk.workstation.ui.fragment;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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
import me.yokeyword.fragmentation.ISupportFragment;
import workstation.zjyk.workstation.R;
import workstation.zjyk.workstation.modle.bean.WSMaterial;
import workstation.zjyk.workstation.modle.bean.WSWip;
import workstation.zjyk.workstation.modle.bean.WSWorkStationMaterielVO;
import workstation.zjyk.workstation.modle.bean.WSWorkStationOutVO;
import workstation.zjyk.workstation.ui.WSMainActivity;
import workstation.zjyk.workstation.ui.adapter.WSAdapterExceptionMakingBindTray;
import workstation.zjyk.workstation.ui.adapter.WSAdapterExceptionMaterailBindTray;
import workstation.zjyk.workstation.ui.manager.WSUserManager;
import workstation.zjyk.workstation.ui.present.WSExceptBindTrayPresent;
import workstation.zjyk.workstation.ui.views.WSExcepBindTrayView;

/**
 * 异常绑定
 * Created by zjgz on 2017/12/12.
 */

public class WSExceptionBindTrayFragment extends WSBaseFragment<WSExceptBindTrayPresent> implements WSExcepBindTrayView {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rl_title_one)
    RelativeLayout rlTitleOne;
    @BindView(R.id.rl_title_two)
    RelativeLayout rlTitleTwo;
    @BindView(R.id.tv_title_left)
    TextView tvTitleLeft;
    @BindView(R.id.tv_quit)
    TextView tvQuit;
    @BindView(R.id.rl_title_three)
    RelativeLayout rlTitleThree;
    Unbinder unbinder;
    @BindView(R.id.recycyle_entry_materail)
    RecyclerView recycyleEntryMaterail;
    @BindView(R.id.recycyle_making)
    RecyclerView recycyleMaking;
    @BindView(R.id.tv_tray_number)
    TextView tvTrayNumber;
    @BindView(R.id.iv_except_out)
    ImageView ivExceptOut;
    @BindView(R.id.iv_back_three)
    ImageView ivBackThree;
    @BindView(R.id.tv_hand_barcode_three)
    TextView tvHandBarcodeThree;
    @BindView(R.id.iv_tray)
    ImageView ivTray;
    Unbinder unbinder1;
    private Unbinder bind;
    private WSAdapterExceptionMakingBindTray mAdapterExceptionMakingBindTray;
    private WSAdapterExceptionMaterailBindTray mAdapterExceptionMaterailBindTray;
    private String taskId;

    public static ISupportFragment newInstance(List<WSMaterial> newMaterialsData, List<WSWip> newMakingData, String taskId) {
        WSExceptionBindTrayFragment wsExceptionFragment = new WSExceptionBindTrayFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("materailData", (ArrayList<? extends Parcelable>) newMaterialsData);
        bundle.putParcelableArrayList("makingData", (ArrayList<? extends Parcelable>) newMakingData);
        bundle.putString("taskId", taskId);
        wsExceptionFragment.setArguments(bundle);
        return wsExceptionFragment;
    }

    @Override
    protected void creatPresent() {
        currentPresent = new WSExceptBindTrayPresent();
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        bind = ButterKnife.bind(this, view);
        rlTitleOne.setVisibility(View.GONE);
        rlTitleTwo.setVisibility(View.GONE);
        rlTitleThree.setVisibility(View.VISIBLE);
        ivBackThree.setOnClickListener(this);
        tvHandBarcodeThree.setOnClickListener(this);
        tvTitleLeft.setText("请确认异常输出的物料,并绑定托盘:");
        tvTrayNumber.setText(getResources().getString(R.string.text_unbind));
        setTrayIvStatus();
        mAdapterExceptionMakingBindTray = new WSAdapterExceptionMakingBindTray();
        recycyleMaking.setLayoutManager(new LinearLayoutManager(getActivity()));
        recycyleMaking.setAdapter(mAdapterExceptionMakingBindTray);

        mAdapterExceptionMaterailBindTray = new WSAdapterExceptionMaterailBindTray();
        recycyleEntryMaterail.setLayoutManager(new LinearLayoutManager(getActivity()));
        recycyleEntryMaterail.setAdapter(mAdapterExceptionMaterailBindTray);
        Bundle arguments = getArguments();
        if (arguments != null) {
            taskId = arguments.getString("taskId");
            ArrayList<WSMaterial> materailData = arguments.getParcelableArrayList("materailData");
            ArrayList<WSWip> makingData = arguments.getParcelableArrayList("makingData");
            mAdapterExceptionMaterailBindTray.setNewData(materailData);
            if (makingData != null) {
                mAdapterExceptionMakingBindTray.setNewData(makingData);
            }
        }

        RxView.clicks(ivExceptOut).throttleFirst(800, TimeUnit.MILLISECONDS).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                requesExceptDelivery();
            }
        });

    }

    private void setTrayIvStatus() {
        ivTray.setVisibility(View.VISIBLE);
        tvTrayNumber.setVisibility(View.VISIBLE);
    }

    @Override
    public void initData() {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_exception_bind_tray;
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

    @OnClick({R.id.tv_quit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_quit:
                pop();
                break;

        }
    }

    @Override
    public void setScanResult(String scanResult) {
        if (!TextUtils.isEmpty(scanResult)) {
            Map<String, String> params = new HashMap<>();
            params.put("code", scanResult);
            currentPresent.bindTray(params);
        }
    }

    private void requesExceptDelivery() {
        //一键输出
        String traycode = tvTrayNumber.getText().toString().trim();
//        if (getString(R.string.text_unbind).equals(traycode)) {
//            ToastUtil.showInfoCenterShort("请先绑定托盘!");
//        } else {
        if (getString(R.string.text_unbind).equals(traycode)) {
            traycode = "";
        }
        String exceptData = getExceptData(traycode);
        if (!TextUtils.isEmpty(exceptData)) {
            Map<String, String> params = new HashMap<>();
            params.put("workStationOutInfo", exceptData);
            currentPresent.requesExceptDelivery(params);
//            }

        }
    }

    private String getExceptData(String traycode) {
        double count = 0;
        List<WSMaterial> data = mAdapterExceptionMaterailBindTray.getData();
        WSWorkStationOutVO wsWorkStationOutVO = new WSWorkStationOutVO();
        List<WSWorkStationMaterielVO> wsWorkStationMaterielVOList = new ArrayList<>();
        if (data != null && data.size() > 0) {
            for (int i = 0; i < data.size(); i++) {
                WSWorkStationMaterielVO wsWorkStationMaterielVO = new WSWorkStationMaterielVO();
                WSMaterial wsMaterial = data.get(i);
                double trueCount = wsMaterial.getTrueCount();
                String id = wsMaterial.getId();
                if (trueCount > 0) {
                    count = count + trueCount;
                    wsWorkStationMaterielVO.setCount(trueCount);
                    wsWorkStationMaterielVO.setMaterielId(id);
                    wsWorkStationMaterielVOList.add(wsWorkStationMaterielVO);
                }

            }
        }
        if (count <= 0) {
            return "";
        }
        wsWorkStationOutVO.setMaterielList(wsWorkStationMaterielVOList);
        wsWorkStationOutVO.setWorkStationTaskId(taskId);
        wsWorkStationOutVO.setOutPersonId(WSUserManager.getInstance().getPersonId());
        wsWorkStationOutVO.setTrayCode(traycode);
        return JsonUtil.toJson(wsWorkStationOutVO);
    }

    @Override
    public void showScanResult(String result) {
        if (!TextUtils.isEmpty(result)) {
            if(tvTrayNumber.getVisibility() == View.VISIBLE){
                tvTrayNumber.setText(result);
                ToastUtil.showInfoCenterShort(getString(R.string.text_bind_sucess));
            }
        } else {
            tvTrayNumber.setText(getString(R.string.text_unbind));
        }
    }

    @Override
    public void showExceptPrint(boolean result) {
        if (result) {
            ToastUtil.showInfoCenterShort("打印成功");
            refreshQuit();
        }
    }

    @Override
    public void showBindTrayResult(boolean result, String exceptPrintId) {
        if (result) {
            ToastUtil.showInfoCenterShort("投递成功");
            setRefreshWhenPop();
            //打印
            String traycode = tvTrayNumber.getText().toString().trim();
            if (getString(R.string.text_unbind).equals(traycode)) {
                Map<String, String> params = new HashMap<>();
                params.put("recordId", exceptPrintId);
                currentPresent.requesExceptPrint(params);
            }

        }
    }

    //刷新签个页面退出
    private void refreshQuit() {
        setRefreshWhenPop();
        pop();
    }

    private void setRefreshWhenPop() {
        Bundle bundle = new Bundle();
        bundle.putBoolean("isQuit", true);
        bundle.putBoolean("isRefresh", true);
        toSetResult(bundle);
    }

//    @Override
//    protected void clickBack() {
//        Bundle bundle = new Bundle();
//        bundle.putBoolean("isRefresh", false);
//        toSetResult(bundle);
//        super.clickBack();
//    }

    private void toSetResult(Bundle bundle) {
        setFragmentResult(1, bundle);
    }

    @Override
    public void setFragmentBack() {
//        super.setFragmentBack();
    }

    @Override
    public void setActivityLogoOrBack() {

        if (mActivity != null && mActivity instanceof WSMainActivity) {
            ((WSMainActivity) mActivity).setActivityLogoOrBack(1);
        }
    }

    @Override
    public void closeCurrent() {
        refreshQuit();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder1 = ButterKnife.bind(this, rootView);
        return rootView;
    }
}
