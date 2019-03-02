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

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.com.ethank.mylibrary.resourcelibrary.server.MyServer;
import cn.com.ethank.mylibrary.resourcelibrary.toast.ToastUtil;
import me.yokeyword.fragmentation.ISupportFragment;
import workstation.zjyk.workstation.R;
import workstation.zjyk.workstation.modle.bean.WSExceptionOutRecordVo;
import workstation.zjyk.workstation.modle.bean.WSMaterial;
import workstation.zjyk.workstation.modle.bean.WSTrayLoadInfo;
import workstation.zjyk.workstation.modle.bean.WSWip;
import workstation.zjyk.workstation.ui.WSMainActivity;
import workstation.zjyk.workstation.ui.adapter.WSAdapterExceptionMaking;
import workstation.zjyk.workstation.ui.adapter.WSAdapterExceptionMaterail;
import workstation.zjyk.workstation.ui.present.WSExceptPresent;
import workstation.zjyk.workstation.ui.views.WSExcepView;
import workstation.zjyk.workstation.util.dialog.WSDialogUtils;
import workstation.zjyk.workstation.util.dialog.callback.WSDialogCallBackThree;
import workstation.zjyk.workstation.util.dialog.callback.WSDialogCallBackTwo;

/**
 * 异常输出
 * Created by zjgz on 2017/12/12.
 */

public class WSExceptionFragment extends WSBaseFragment<WSExceptPresent> implements WSExcepView {

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
    @BindView(R.id.tv_bind_tray)
    TextView tvBindTray;
    @BindView(R.id.iv_back_three)
    ImageView ivBackThree;
    @BindView(R.id.tv_hand_barcode_three)
    TextView tvHandBarcodeThree;
    private Unbinder bind;
    private WSAdapterExceptionMaking mAdapterExceptionMaking;
    private WSAdapterExceptionMaterail mAdapterExceptionMaterail;
    private String taskId;
    private WSTrayLoadInfo trayLoadInfo;

    public static ISupportFragment newInstance(Bundle bundle) {
        WSExceptionFragment wsExceptionFragment = new WSExceptionFragment();
        wsExceptionFragment.setArguments(bundle);
        return wsExceptionFragment;
    }

    @Override
    protected void creatPresent() {
        currentPresent = new WSExceptPresent();
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        bind = ButterKnife.bind(this, view);
        rlTitleOne.setVisibility(View.GONE);
        rlTitleTwo.setVisibility(View.GONE);
        rlTitleThree.setVisibility(View.VISIBLE);
        ivBackThree.setOnClickListener(this);
        tvQuit.setVisibility(View.VISIBLE);
        tvQuit.setText("异常输出记录");
        tvHandBarcodeThree.setVisibility(View.GONE);
        Bundle arguments = getArguments();
        if (arguments != null) {
            taskId = arguments.getString("taskId");
            trayLoadInfo = arguments.getParcelable("data");
        }
        tvTitleLeft.setText("请选择异常的物料进行输出:");
        mAdapterExceptionMaking = new WSAdapterExceptionMaking();
        recycyleMaking.setLayoutManager(new LinearLayoutManager(getActivity()));
        recycyleMaking.setAdapter(mAdapterExceptionMaking);

        mAdapterExceptionMaterail = new WSAdapterExceptionMaterail();
        recycyleEntryMaterail.setLayoutManager(new LinearLayoutManager(getActivity()));
        recycyleEntryMaterail.setAdapter(mAdapterExceptionMaterail);

        showExceptionInfo(trayLoadInfo);
        mAdapterExceptionMaterail.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                WSMaterial bean = (WSMaterial) adapter.getData().get(position);
                double count = bean.getCount();
                double trueCount = bean.getTrueCount();
                switch (view.getId()) {
                    case R.id.iv_reduce:
                        if (trueCount >= 1) {
                            trueCount--;
                            bean.setTrueCount(trueCount);
                            adapter.notifyItemChanged(position);
                        } else {
                            ToastUtil.showCenterShort("输出数量不能小于0!", true);
                        }
                        break;
                    case R.id.iv_add:
                        if (trueCount < count) {
                            trueCount++;
                            if (trueCount > count) {
                                trueCount = count;
                            }
                            bean.setTrueCount(trueCount);
                            adapter.notifyItemChanged(position);
                        } else {
                            ToastUtil.showCenterShort("输出数量不能大于总数量!", true);
                        }
                        break;
                    case R.id.tv_except_count:
                        showExceptMaterailEntryNumberDialog(bean);
                        break;
                }
            }
        });

        mAdapterExceptionMaking.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                WSWip wsWip = (WSWip) adapter.getData().get(position);
                double count = wsWip.getCount();
                double trueCount = wsWip.getTrueCount();
                switch (view.getId()) {
                    case R.id.iv_reduce:
                        if (trueCount >= 1) {
                            trueCount--;
                            wsWip.setTrueCount(trueCount);
                            adapter.notifyItemChanged(position);
                        } else {
                            ToastUtil.showCenterShort("输出数量不能小于0!", true);
                        }
                        break;
                    case R.id.iv_add:
                        if (trueCount < count) {
                            trueCount++;
                            if (trueCount > count) {
                                trueCount = count;
                            }
                            wsWip.setTrueCount(trueCount);
                            adapter.notifyItemChanged(position);
                        } else {
                            ToastUtil.showCenterShort("输出数量不能大于总数量!", true);
                        }
                        break;
                    case R.id.tv_except_count:
                        showExceptMakingEntryNumberDialog(wsWip);
                        break;
                }
            }
        });

    }

    @Override
    public void initData() {
        //放在任务详情的界面请求数据了
//        Map<String, String> params = new HashMap<>();
//        params.put("taskId", taskId);
//        currentPresent.requesExceptList(params);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_exception;
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

    @OnClick({R.id.tv_quit, R.id.tv_bind_tray_except})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_quit:
                //查看异常输出记录
                requestExceptHistoryList();
                break;
            case R.id.tv_bind_tray_except:
                toBindTray();
                break;
        }
    }

    private void requestExceptHistoryList() {
        Map<String, String> params = new HashMap<>();
        params.put("taskId", taskId);
        currentPresent.requesExceptHistoryList(params);
    }

    @Override
    public void showExceptHistoryList(List<WSExceptionOutRecordVo> wsExceptionOutRecordVoList) {
        if (wsExceptionOutRecordVoList != null) {
            if (wsExceptionOutRecordVoList.size() > 0) {
                showExceptOutHistoryDialog(wsExceptionOutRecordVoList);
            } else {
                ToastUtil.showInfoCenterShort("暂无异常输出记录");
            }
        }
    }

    private void showExceptOutHistoryDialog(List<WSExceptionOutRecordVo> wsExceptionOutRecordVoList) {
        WSDialogUtils.showExceptOutHistoryDialog(mActivity, "异常输出记录", wsExceptionOutRecordVoList, new WSDialogCallBackThree() {
            @Override
            public void OnPositiveClick(Object obj) {

            }

            @Override
            public void checkScanCode(String code) {
                if (!TextUtils.isEmpty(code)) {
                    Map<String, String> params = new HashMap<>();
                    params.put("recordId", code);
                    currentPresent.requesExceptPrint(params);
                }

            }

            @Override
            public void OnNegativeClick() {

            }
        });
    }

    private void toBindTray() {
        List<WSMaterial> materialsData = mAdapterExceptionMaterail.getData();
        List<WSWip> makingData = mAdapterExceptionMaking.getData();
        List<WSMaterial> newMaterialsData = new ArrayList<>();
        List<WSWip> newMakingData = new ArrayList<>();
        for (int i = 0; i < materialsData.size(); i++) {
            WSMaterial wsMaterial = materialsData.get(i);
            if (wsMaterial.getTrueCount() > 0) {
                newMaterialsData.add(wsMaterial);
            }
        }

        for (int i = 0; i < makingData.size(); i++) {
            WSWip wsWip = makingData.get(i);
            if (wsWip.getTrueCount() > 0) {
                newMakingData.add(wsWip);
            }
        }

        if (newMaterialsData.size() > 0 || newMakingData.size() > 0) {
//            start(WSExceptionBindTrayFragment.newInstance(newMaterialsData,newMakingData));
            startForResult(WSExceptionBindTrayFragment.newInstance(newMaterialsData, newMakingData, taskId), 0);
        } else {
            ToastUtil.showCenterShort("请输入异常数量", true);
        }

    }

    @Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            if (data != null) {
                boolean isQuit = data.getBoolean("isQuit");
                boolean isRefresh = data.getBoolean("isRefresh");
                if (isQuit) {
                    setFragmentResult(0, data);
                    super.clickBack();
                }
                if (isRefresh) {
                    setSynStatus(true, MyServer.ACTION_TASK_EXCEPTION);
                }
            }

        }
    }

    @Override
    public void showExceptionInfo(WSTrayLoadInfo wsTrayLoadInfo) {
        if (wsTrayLoadInfo != null) {
            List<WSMaterial> materialList = wsTrayLoadInfo.getMaterialList();
            List<WSWip> wipList = wsTrayLoadInfo.getWipList();
            if (materialList != null) {
                for (int i = 0; i < materialList.size(); i++) {
                    WSMaterial wsMaterial = materialList.get(i);
                    wsMaterial.setCount(Integer.MAX_VALUE);
                }
                mAdapterExceptionMaterail.setNewData(materialList);
            }
            if (wipList != null) {
                mAdapterExceptionMaking.setNewData(wipList);
            }
        }
    }

    @Override
    public void showExceptPrint(boolean result) {
        if (result) {
            ToastUtil.showInfoCenterShort("打印成功");
        }
    }

    private void showExceptMaterailEntryNumberDialog(WSMaterial wsMaterial) {
        WSDialogUtils.showExceptMaterailEntryNumberDialog(getActivity(), "请输入异常物料数量", wsMaterial, new WSDialogCallBackTwo() {
            @Override
            public void OnPositiveClick(Object obj) {
                if (obj instanceof WSMaterial) {
                    WSMaterial bean = (WSMaterial) obj;
                    mAdapterExceptionMaterail.notifyItemChanged(bean.getPosition());
                }
            }

            @Override
            public void OnNegativeClick() {

            }
        });
    }

    private void showExceptMakingEntryNumberDialog(WSWip wsWip) {
        WSDialogUtils.showExceptMakingEntryNumberDialog(getActivity(), "请输入异常物料数量", wsWip, new WSDialogCallBackTwo() {
            @Override
            public void OnPositiveClick(Object obj) {
                if (obj instanceof WSWip) {
                    WSWip bean = (WSWip) obj;
                    mAdapterExceptionMaking.notifyItemChanged(bean.getPosition());
                }
            }

            @Override
            public void OnNegativeClick() {

            }
        });
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
}
