package workstation.zjyk.line.ui.fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.com.ethank.mylibrary.resourcelibrary.common_util.JsonUtil;
import cn.com.ethank.mylibrary.resourcelibrary.core.coding.MD5Util;
import cn.com.ethank.mylibrary.resourcelibrary.intent.StartIntentUtils;
import cn.com.ethank.mylibrary.resourcelibrary.toast.ToastUtil;
import cn.com.ethank.mylibrary.resourcelibrary.utils.SDCardHelper;
import io.reactivex.functions.Consumer;
import workstation.zjyk.line.R;
import workstation.zjyk.line.modle.bean.AccessoryAddress;
import workstation.zjyk.line.modle.bean.BondBean;
import workstation.zjyk.line.modle.bean.DistributeMateriel;
import workstation.zjyk.line.modle.bean.LineDistributeHistoryVO;
import workstation.zjyk.line.modle.bean.OrderDistributeNeedMaterielVo;
import workstation.zjyk.line.modle.bean.OrderDistributeWrapMaterielVo;
import workstation.zjyk.line.modle.bean.OrderMaterielCountVo;
import workstation.zjyk.line.modle.bean.OrderMaterielVo;
import workstation.zjyk.line.modle.bean.YesOrNoEnum;
import workstation.zjyk.line.ui.activity.PdfReadDialogActivity;
import workstation.zjyk.line.ui.adapter.AdapterBondDetail;
import workstation.zjyk.line.ui.present.BondDetailPresent;
import workstation.zjyk.line.ui.present.MvpBasePresenter;
import workstation.zjyk.line.ui.views.BondDetailView;
import workstation.zjyk.line.util.Constants;
import workstation.zjyk.line.util.DialogCallBackTwo;
import workstation.zjyk.line.util.dialog.DialogUtils;
import workstation.zjyk.line.util.dialog.LookEnclosureDialog;
import workstation.zjyk.line.util.dialog.RemindDialog;
import workstation.zjyk.line.util.dialog.RemindTwoDialog;
import workstation.zjyk.line.util.dialog.callback.EnclosureCallBack;

public class BondDetailFragment extends BaseFragment implements BondDetailView {

    @BindView(R.id.tv_unbond)
    TextView tvUnbond;
    @BindView(R.id.tv_bonded)
    TextView tvBonded;
    @BindView(R.id.tv_cancle)
    TextView tvCancle;
    @BindView(R.id.recycyleview)
    RecyclerView recycyleview;
    Unbinder unbinder;
    @BindView(R.id.tv_package)
    TextView tvPackage;
    @BindView(R.id.iv_urgent)
    ImageView ivUrgent;
    @BindView(R.id.iv_top)
    ImageView ivTop;
    @BindView(R.id.tv_accessory)
    TextView tvAccessory;
    private Unbinder bind;
    private AdapterBondDetail mAdapterBondDetail;
    private BondDetailPresent mBondDetailPresent;
    int selectFlag = 0;//0未下发 1已下发
    private String orderId;
    private boolean isRefresh = false;//是否刷新
    private RemindDialog mRemindDialog;
    private LookEnclosureDialog mEnclosureDialog;
    private AccessoryAddress currentAccessoryAddress;
    private String productid;

    public static BondDetailFragment newInstance(Bundle bundle) {
        BondDetailFragment bondDetailFragment = new BondDetailFragment();
        bondDetailFragment.setArguments(bundle);
        return bondDetailFragment;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        bind = ButterKnife.bind(this, view);
        Bundle bundle = getArguments();
        boolean isBonded = bundle.getBoolean("isBonded");
        if (isBonded) {
            tvUnbond.setVisibility(View.GONE);
            selectFlag = 1;
        }
        OrderMaterielVo orderMaterielVo = bundle.getParcelable("data");
        if (orderMaterielVo != null) {
            orderId = orderMaterielVo.getOrderid();
            YesOrNoEnum orderNoMaterielTage = orderMaterielVo.getOrderNoMaterielTage();
            YesOrNoEnum isTop = orderMaterielVo.getIsTop();
            productid = orderMaterielVo.getProductid();
            YesOrNoEnum worryDistribute = orderMaterielVo.getWorryDistribute();
            if (worryDistribute == YesOrNoEnum.YES) {
                ivUrgent.setVisibility(View.VISIBLE);
            }
            if (isTop == YesOrNoEnum.YES) {
                ivTop.setVisibility(View.VISIBLE);
            }

        }
        setSelectTab();
        mBondDetailPresent = new BondDetailPresent(this);
        mAdapterBondDetail = new AdapterBondDetail();
        recycyleview.setLayoutManager(new LinearLayoutManager(mActivity));
        recycyleview.setAdapter(mAdapterBondDetail);
        mAdapterBondDetail.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                OrderDistributeNeedMaterielVo orderDistributeNeedMaterielVo = (OrderDistributeNeedMaterielVo) adapter.getData().get(position);
                switch (view.getId()) {
                    case R.id.tv_one_bond:
                        if (((TextView) view).getText().equals(getResources().getString(R.string.text_one_bond))) {
                            List<OrderMaterielCountVo> bondMaterailList = getBondMaterailList(orderDistributeNeedMaterielVo);
                            if (bondMaterailList != null && bondMaterailList.size() > 0) {
                                if (orderDistributeNeedMaterielVo.getTaskProcedureStruts() == YesOrNoEnum.NO) {
                                    //欠料提醒
                                    showRemindTwoDialog(orderDistributeNeedMaterielVo);
                                } else {
                                    showRemindDialog(orderDistributeNeedMaterielVo, "", false);
                                }
                            } else {
                                ToastUtil.showInfoCenterShort("暂无可下发的物料");
                            }
                        }
                        break;
                    case R.id.tv_history:
                        requestHistoryRecord(orderDistributeNeedMaterielVo.getTaskId());
                        break;
                }
            }
        });
    }

    /**
     * 欠料提醒
     *
     * @param orderDistributeNeedMaterielVo
     */
    private void showRemindTwoDialog(OrderDistributeNeedMaterielVo orderDistributeNeedMaterielVo) {
        RemindTwoDialog remindTwoDialog = DialogUtils.showRemindTwoDialog(mActivity, "当前工序欠料状态,是否下发?", new DialogCallBackTwo() {
            @Override
            public void OnPositiveClick(Object obj) {
                showRemindDialog(orderDistributeNeedMaterielVo, "", false);
            }

            @Override
            public void OnNegativeClick() {

            }
        });
        TextView tvTitleView = remindTwoDialog.getTvTitleView();
        if (tvTitleView != null) {
            tvTitleView.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimensionPixelSize(R.dimen.text_design_60px));
            tvTitleView.setTextColor(getResources().getColor(R.color.red));
        }
        remindTwoDialog.show();
    }

    private void showHistoryRecordDialog(List<LineDistributeHistoryVO> datas) {
        DialogUtils.showBondHsitoryRecordDialog(mActivity, "历史记录", datas, new DialogCallBackTwo() {
            @Override
            public void OnPositiveClick(Object obj) {
                if (obj != null && obj instanceof LineDistributeHistoryVO) {
                    //补打操作
                    LineDistributeHistoryVO lineDistributeHistoryVO = (LineDistributeHistoryVO) obj;
//                    bondPrint(lineDistributeHistoryVO.getTrayCode());
                    showRemindDialog(null, lineDistributeHistoryVO.getTrayCode(), true);
                }
            }

            @Override
            public void OnNegativeClick() {

            }
        });
    }

    private void requestHistoryRecord(String taskId) {
        Map<String, String> params = new HashMap<>();
        params.put("taskId", taskId);
        mBondDetailPresent.requestHistoryRecord(params, true);
    }

    /**
     * @param orderDistributeNeedMaterielVo
     * @param trayCode
     * @param isHistoryDialog               用于区分 历史记录的dialog
     */
    private void showRemindDialog(final OrderDistributeNeedMaterielVo orderDistributeNeedMaterielVo, String trayCode, boolean isHistoryDialog) {
        //打印辅料
//验证托盘码
//先校验托盘码  然后输出
        mRemindDialog = DialogUtils.showRemindDialog(mActivity, "打印辅料", new DialogCallBackTwo() {
            @Override
            public void OnPositiveClick(Object obj) {
                if (obj != null && obj instanceof BondBean) {
                    if (orderDistributeNeedMaterielVo != null) {
                        BondBean bean = (BondBean) obj;
                        String trayBindCode = bean.getTrayCode();
                        if (getContext().getString(R.string.text_unbind).equals(trayBindCode)) {
                            trayBindCode = "";
                        }
                        if (bean.getType() == 1) {
                            //打印辅料 根据绑定的托盘吗 进行判断
                            requestOneBond(orderDistributeNeedMaterielVo, bean.isCheck(), trayBindCode);
                        } else if (bean.getType() == 2) {
                            //验证托盘码
//                            String trayCodeNow = bean.getTrayCode();
                            //先校验托盘码  然后输出
                            checkBindTrayCode(trayBindCode);
                        } else if (bean.getType() == 3) {
//                            String trayCodeNow = bean.getTrayCode();
                            //绑定托盘  然后输出
                            requestOneBond(orderDistributeNeedMaterielVo, false, trayBindCode);
                        }
                    } else {
                        if (!TextUtils.isEmpty(trayCode)) {
                            bondPrint(trayCode, ((BondBean) obj).isCheck());
                        }
                    }
                }
            }

            @Override
            public void OnNegativeClick() {

            }
        });
        mRemindDialog.setData("打印辅料", orderDistributeNeedMaterielVo, isHistoryDialog);
        mRemindDialog.show();

    }

    private void checkBindTrayCode(String trayCode) {
        Map<String, String> params = new HashMap<>();
        params.put("code", trayCode);
        mBondDetailPresent.checkBindTrayCode(params, true, trayCode);
    }

    /**
     * @param orderDistributeNeedMaterielVo
     * @param isCheck
     * @param bindTrayCode                  code不为null的话 直接绑定托盘输出 不打印
     */
    private void requestOneBond(OrderDistributeNeedMaterielVo orderDistributeNeedMaterielVo, boolean isCheck, String bindTrayCode) {
        Map<String, String> params = new HashMap<>();
        DistributeMateriel distributeMateriel = new DistributeMateriel();
        List<OrderMaterielCountVo> bondList = getBondMaterailList(orderDistributeNeedMaterielVo);
        if (bondList.size() > 0) {
            distributeMateriel.setMaterielInfoStr(JsonUtil.toJson(bondList));
            distributeMateriel.setWorkStationTaskId(orderDistributeNeedMaterielVo.getTaskId());
            distributeMateriel.setWorkStationId(Constants.getStationId());
            params.put("distributeMaterielStr", JsonUtil.toJson(distributeMateriel));
            if (!TextUtils.isEmpty(bindTrayCode)) {
                params.put("trayCode", bindTrayCode);
            }
            boolean isPrint = true;//是否打印
            String trayCode = params.get("trayCode");
            if (!TextUtils.isEmpty(trayCode)) {
                //只绑托盘 不打印
                isPrint = false;
            }
            mBondDetailPresent.requestOneBond(params, isCheck, true, isPrint);
        } else {
            ToastUtil.showInfoCenterShort("暂无可下发的物料");
        }

    }

    private List<OrderMaterielCountVo> getBondMaterailList(OrderDistributeNeedMaterielVo orderDistributeNeedMaterielVo) {
        List<OrderMaterielCountVo> bondList = new ArrayList<>();
        List<OrderMaterielCountVo> materielList = orderDistributeNeedMaterielVo.getMaterielList();
        for (int i = 0; i < materielList.size(); i++) {
            OrderMaterielCountVo orderMaterielCountVo = materielList.get(i);
            double distributeMainCount = orderMaterielCountVo.getDistributeMainCount();
            if (distributeMainCount > 0) {
                bondList.add(orderMaterielCountVo);
            }
        }
        return bondList;
    }

    @Override
    public void showOneBondResult(boolean result, boolean errorClick, String trayCode, boolean isCheck, boolean isPrint) {
        isRefresh = true;
        if (result) {
            ToastUtil.showInfoCenterShort("发料成功");
            if (isPrint) {
                bondPrint(trayCode, isCheck);
            } else {
                initData();
            }
        } else {
            if (errorClick) {
                initData();
            }
        }
    }

    @Override
    public void showPrintResult(boolean result) {
        if (result) {
            ToastUtil.showInfoCenterShort("打印成功");
        }
        initData();
    }

    @Override
    public void showFileMd5CheckResult(boolean result, String fileName, String newDownUrl) {
        if (result) {
            //打开文件
            downPdfResult(true, fileName);
        } else {
            //下载文件
            if (!TextUtils.isEmpty(newDownUrl)) {
                currentAccessoryAddress.setAddress(newDownUrl);
//                processInstructionUrl = newDownUrl;
            }
            mBondDetailPresent.downLoadPdf(getActivity(), currentAccessoryAddress.getAddress(), fileName);
        }
    }

    @Override
    public void downPdfResult(boolean result, String filePath) {
        if (result) {
            Bundle bundle = new Bundle();
            bundle.putString("pdfPath", filePath);
            if (currentAccessoryAddress != null) {
                bundle.putString("titleName", currentAccessoryAddress.getAccessoryType());
            }
            dismissEnclosureDialog();
            StartIntentUtils.startIntentUtils(getActivity(), PdfReadDialogActivity.class, bundle);
        } else {
            ToastUtil.showInfoCenterShort("文件下载失败");
        }
    }

    private void dismissEnclosureDialog() {
        if (mEnclosureDialog != null && mEnclosureDialog.isShowing()) {
            mEnclosureDialog.dismiss();
        }
    }

    private void bondPrint(String trayCode, boolean isCheck) {
        Map<String, String> params = new HashMap<>();
        params.put("code", trayCode);
        if (isCheck) {
            //0 打印辅料 1不打印
            params.put("tag", "0");
        } else {
            params.put("tag", "1");
        }

        mBondDetailPresent.bondPrint(params, true);
    }

    @Override
    public void showBindResult(boolean result, String trayCode) {
        if (result) {
            mRemindDialog.setTrayCode(trayCode);
        } else {
            mRemindDialog.setTrayCode(getContext().getString(R.string.text_unbind));
        }
    }

    @Override
    public void showBondHistoryData(List<LineDistributeHistoryVO> datas) {
        if (datas != null) {
            if (datas.size() > 0) {
                showHistoryRecordDialog(datas);
            } else {
                ToastUtil.showInfoCenterShort("暂无历史记录");
            }
        }
    }

    @Override
    public void initData() {
        getData();
    }

    private void getData() {
        Map<String, String> params = new HashMap<>();
        params.put("orderId", orderId);
        if (selectFlag == 0) {
            params.put("distributeTage", "");
        } else {
            params.put("distributeTage", "1");
        }
        mBondDetailPresent.requestBondList(params, true);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_bond_detail;
    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    MvpBasePresenter getPresenter() {
        return mBondDetailPresent;
    }

    @Override
    public void onDestroyView() {
        bind.unbind();
        super.onDestroyView();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @OnClick({R.id.tv_unbond, R.id.tv_bonded, R.id.tv_cancle, R.id.tv_accessory})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_unbond:
                //未下发
                if (selectFlag == 1) {
                    selectFlag = 0;
                    getData();
                }
                break;
            case R.id.tv_bonded:
                //已下发
                if (selectFlag == 0) {
                    selectFlag = 1;
                    getData();
                }
                break;
            case R.id.tv_cancle:
                quitRefresh();
                break;
            case R.id.tv_accessory:
                //附件
                requestAccessoryList();
                break;
        }
    }

    private void requestAccessoryList() {
        Map<String, String> params = new HashMap<>();
        params.put("productId", productid);
        mBondDetailPresent.requestAccessoryList(params, true);
    }

    @Override
    public void showAccessoryList(List<AccessoryAddress> datas) {
        if (datas != null) {
            if (datas.size() > 0) {
                showEnclosureDialog(datas);
            } else {
                ToastUtil.showInfoCenterShort("暂无附件");
            }
        }
    }

    private void quitRefresh() {
        Bundle bundle = new Bundle();
        bundle.putBoolean("isRefresh", isRefresh);
        setFragmentResult(0, bundle);
        pop();
    }

    @Override
    public void showData(OrderDistributeWrapMaterielVo orderDistributeWrapMaterielVo) {
        if (orderDistributeWrapMaterielVo != null) {
            String distributeCode = orderDistributeWrapMaterielVo.getDistributeCode();
            tvPackage.setText(distributeCode);
            List<OrderDistributeNeedMaterielVo> datas = orderDistributeWrapMaterielVo.getMaterielList();
            mAdapterBondDetail.setCustomDatas(selectFlag, datas);
            if (datas != null && datas.size() > 0) {
            } else {
                if (selectFlag == 0) {
                    ToastUtil.showInfoCenterShort("暂无未下发物料");
                } else {
                    ToastUtil.showInfoCenterShort("暂无已下发物料");
                }
            }
            setSelectTab();
        }

    }

    private void setSelectTab() {
        if (selectFlag == 0) {
            tvUnbond.setBackgroundResource(R.drawable.shape_bond_tab_bg_selected);
            tvBonded.setBackgroundResource(R.drawable.shape_bond_tab_bg_unselect);
        } else {
            tvUnbond.setBackgroundResource(R.drawable.shape_bond_tab_bg_unselect);
            tvBonded.setBackgroundResource(R.drawable.shape_bond_tab_bg_selected);
        }
    }


    private void showEnclosureDialog(List<AccessoryAddress> accessoryAddresslist) {
        if (mEnclosureDialog == null) {
            mEnclosureDialog = DialogUtils.showLookEnclosureDialog(mActivity, "产品附件", new EnclosureCallBack() {
                @Override
                public void selectIten(AccessoryAddress wsAccessoryAddress) {
                    currentAccessoryAddress = wsAccessoryAddress;
                    if (currentAccessoryAddress != null) {
                        downPdf(currentAccessoryAddress);
                    }
                }
            });
        }
        if (accessoryAddresslist != null) {
            mEnclosureDialog.setData(accessoryAddresslist);
        }
        mEnclosureDialog.show();
    }

    private void downPdf(AccessoryAddress currentAccessoryAddress) {
        //查看文件存在不
        //文件存在 根据文件的md5来判断文件是否是 同一个
        //是源文件 根据文件地址展示 否则去下载
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            //
            RxPermissions rxPermissions = new RxPermissions(getActivity());
            rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(new Consumer<Boolean>() {
                @Override
                public void accept(Boolean aBoolean) throws Exception {
                    if (aBoolean) {
                        downPdfStart(currentAccessoryAddress);
                    } else {
                        ToastUtil.showInfoCenterShort("权限拒绝，无法下载");
                    }
                }
            });
        } else {
            downPdfStart(currentAccessoryAddress);
        }
    }

    String taskId = "";

    private void downPdfStart(AccessoryAddress currentAccessoryAddress) {
        String processInstructionUrl = currentAccessoryAddress.getAddress();
        String accessoryType = currentAccessoryAddress.getAccessoryType();
        if (!TextUtils.isEmpty(processInstructionUrl)) {
//            String[] split = processInstructionUrl.split("/");
//            split[split.length - 1]
            String fileName = SDCardHelper.getSDCardPrivateFilesDir(getActivity(), null) + "/" + productid + accessoryType + ".pdf";
            File file = new File(fileName);
            if (file.exists()) {
                try {
                    //查看文件是否是源文件
                    String md5ByFile = MD5Util.getMd5ByFile(file);
                    mBondDetailPresent.checkFileMd5(accessoryType, processInstructionUrl, productid, md5ByFile, fileName);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    mBondDetailPresent.downLoadPdf(getActivity(), processInstructionUrl, fileName);
                }
            } else {
                mBondDetailPresent.downLoadPdf(getActivity(), processInstructionUrl, fileName);
            }
        } else {
            ToastUtil.showInfoCenterShort("未上传工艺指导书!");
        }
    }
}


