package workstation.zjyk.line.ui.fragments;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import cn.com.ethank.mylibrary.resourcelibrary.common_util.JsonUtil;
import cn.com.ethank.mylibrary.resourcelibrary.toast.ToastUtil;
import workstation.zjyk.line.R;
import workstation.zjyk.line.modle.bean.ExceptionDetailBean;
import workstation.zjyk.line.modle.bean.ExceptionOutBean;
import workstation.zjyk.line.modle.bean.ExceptionPrintBean;
import workstation.zjyk.line.modle.bean.ExceptionToServer;
import workstation.zjyk.line.modle.bean.MaterielVo;
import workstation.zjyk.line.ui.activity.MainActivity;
import workstation.zjyk.line.ui.adapter.AdapterUnusal;
import workstation.zjyk.line.ui.adapter.AdapterUnusalTwo;
import workstation.zjyk.line.ui.present.MvpBasePresenter;
import workstation.zjyk.line.ui.present.UnusalPresent;
import workstation.zjyk.line.ui.views.UnusalView;
import workstation.zjyk.line.util.Constants;
import workstation.zjyk.line.util.DialogCallBackTwo;
import workstation.zjyk.line.util.dialog.DialogUtils;

/**
 * 异常输出
 * Created by zjgz on 2017/11/1.
 */

public class UnusalFragment extends BaseFragment implements UnusalView {
    @BindView(R.id.tv_cancle)
    TextView tvCancle;
    @BindView(R.id.recycyleview)
    RecyclerView recycyleview;
    @BindView(R.id.tv_next_bottom)
    TextView tvNextBottom;
    Unbinder unbinder;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_bar_code)
    TextView tvBarCode;
    @BindView(R.id.tv_number)
    TextView tvNumber;
    @BindView(R.id.tv_next)
    TextView tvNext;
    private AdapterUnusal adapterUnusal;
    private boolean isReport;
    private ArrayList<ExceptionDetailBean> exceptionDetailBeans;
    private String exceptionNumber;
    private List<MaterielVo> materielVos;
    private String wrapid;
    private UnusalPresent mUnusalPresent;
    private String recordId;
    private AdapterUnusalTwo adapterUnusalTwo;
    private View menu_unreport;
    private View menu_report;
    private String serialNumber;

    public static UnusalFragment newInstance(Bundle bundle) {
        UnusalFragment unusalFragment = new UnusalFragment();
        unusalFragment.setArguments(bundle);
        return unusalFragment;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        ButterKnife.bind(this, view);
        menu_unreport = view.findViewById(R.id.menu_unreport);
        menu_report = view.findViewById(R.id.menu_report);
        mUnusalPresent = new UnusalPresent(this);
        Bundle arguments = getArguments();
        if (arguments != null) {
            isReport = arguments.getBoolean("isReport");
        }
        tvTitle.setText("异常输出");
        if (isReport) {
            tvNextBottom.setText("打印");
            exceptionDetailBeans = arguments.getParcelableArrayList("data");
            exceptionNumber = arguments.getString("exceptionNumber");
            recordId = arguments.getString("recordId");
            for (int i = 0; i < exceptionDetailBeans.size(); i++) {
                ExceptionDetailBean exceptionDetailBean = exceptionDetailBeans.get(i);
                exceptionDetailBean.setExceptionOutCount(exceptionDetailBean.getCount());
            }
        } else {
            tvNextBottom.setText("输出并打印");
            tvNextBottom.setVisibility(View.GONE);
            wrapid = arguments.getString("wrapid");
            serialNumber = arguments.getString("serialNumber");
            exceptionDetailBeans = arguments.getParcelableArrayList("data");
        }

        tvBarCode.setText("仓库发料编号:1000");
        tvNumber.setText("异常: " + exceptionNumber);

        adapterUnusal = new AdapterUnusal(isReport);
        adapterUnusalTwo = new AdapterUnusalTwo(isReport);
        recycyleview.setLayoutManager(new LinearLayoutManager(getActivity()));
        if (isReport) {
            tvNext.setVisibility(View.GONE);
            menu_report.setVisibility(View.VISIBLE);
            menu_unreport.setVisibility(View.GONE);
            recycyleview.setAdapter(adapterUnusalTwo);
            adapterUnusalTwo.setNewData(exceptionDetailBeans);
        } else {
            tvNext.setVisibility(View.VISIBLE);
            menu_report.setVisibility(View.GONE);
            menu_unreport.setVisibility(View.VISIBLE);
            ViewGroup.LayoutParams layoutParams = recycyleview.getLayoutParams();
            layoutParams.height = 720;
            recycyleview.setLayoutParams(layoutParams);
            recycyleview.setAdapter(adapterUnusal);
        }
        if (exceptionDetailBeans != null && exceptionDetailBeans.size() > 0) {
            adapterUnusal.setNewData(exceptionDetailBeans);
        }
        adapterUnusal.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                ExceptionDetailBean exceptionDetailBean = (ExceptionDetailBean) adapter.getData().get(position);
                double exceptionOutCount = exceptionDetailBean.getExceptionOutCount();
                double count = exceptionDetailBean.getCount();
                switch (view.getId()) {
                    case R.id.iv_reduce_bag:
                        if (exceptionOutCount >= 1) {
                            exceptionOutCount--;
                            exceptionDetailBean.setExceptionOutCount(exceptionOutCount);
                            adapter.notifyItemChanged(position);
                        } else {
                            ToastUtil.showCenterShort("异常输出数不能小于0", true);
                        }
                        break;
                    case R.id.iv_add_bag:
                        if (exceptionOutCount <= count - 1) {
                            exceptionOutCount++;
                            exceptionDetailBean.setExceptionOutCount(exceptionOutCount);
                            adapter.notifyItemChanged(position);
                        } else {
                            ToastUtil.showCenterShort("异常输出数不能大于总数量", true);
                        }
                        break;
                    case R.id.tv_true_bag:
                        showDialogEntryNumber(exceptionDetailBean);
                        break;
                }
            }
        });
    }

    private void showDialogEntryNumber(ExceptionDetailBean exceptionDetailBean) {
        DialogUtils.showExceptionNumberDialog(getActivity(), exceptionDetailBean, new DialogCallBackTwo() {
            @Override
            public void OnPositiveClick(Object obj) {
                if (obj != null && obj instanceof ExceptionDetailBean) {
                    ExceptionDetailBean bean = (ExceptionDetailBean) obj;
                    adapterUnusal.notifyItemChanged(bean.getPosition());
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
        return R.layout.fragment_unusal;
    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    MvpBasePresenter getPresenter() {
        return null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.tv_cancle, R.id.tv_next_bottom, R.id.tv_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_cancle:
                if (!isReport) {
                    if (getActivity() instanceof MainActivity) {
                        MainActivity activity = (MainActivity) getActivity();
                        activity.opreationMaterDetailDialogActivity();
                    }
                }
                pop();
                break;
            case R.id.tv_next_bottom:
                if (isReport) {
                    Map<String, String> params = new HashMap<>();
                    params.put("recordId", recordId);
                    params.put("status", "0");
                    requestExceptionPrintData(params);
                } else {
                    //先获取 异常ID 然后请求打印
                    String exceptionMessage = getExceptionMessage();
                    if (!TextUtils.isEmpty(exceptionMessage)) {
                        Map<String, String> params = new HashMap<>();
                        params.put("exceptionInfo", exceptionMessage);
                        mUnusalPresent.getExceptionOutData(params);
                    } else {
                        ToastUtil.showCenterShort("异常数量不能为0", true);
                    }
                }
                break;
            case R.id.tv_next:
                //下一步
                startExceptBindTray();
                break;
        }
    }

    private void startExceptBindTray() {
        List<ExceptionDetailBean> exceptionDetail = getExceptionDetail();
        if (exceptionDetail.size() > 0) {
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList("data", (ArrayList<? extends Parcelable>) exceptionDetail);
            bundle.putString("wrapid",wrapid);
            bundle.putString("serialNumber",serialNumber);
            start(ExceptionBindTrayFragment.newInstance(bundle));
        } else {
            ToastUtil.showCenterShort("异常数量不能为0", true);
        }
    }

    private String getExceptionMessage() {
        double exceptionOutCountActual = 0; //如果excaption为0提示用户
        Map<String, String> map = new HashMap<>();
        map.put("distributeCode", wrapid);
        map.put("workStationId", Constants.getStationId());
        List<ExceptionToServer> toServers = new ArrayList<>();
        List<ExceptionDetailBean> data = adapterUnusal.getData();
        for (int i = 0; i < data.size(); i++) {
            ExceptionDetailBean exceptionDetailBean = data.get(i);
            double exceptionOutCount = exceptionDetailBean.getExceptionOutCount();
            if (exceptionOutCount == 0) {
                continue;
            }
            exceptionOutCountActual = exceptionOutCountActual + exceptionOutCount;
            ExceptionToServer exceptionToServer = new ExceptionToServer();
            exceptionToServer.setCount(exceptionOutCount);
            exceptionToServer.setMaterielBatchNumber(exceptionDetailBean.getMaterielBatchNumber());
            exceptionToServer.setMaterielOid(exceptionDetailBean.getOid());
            toServers.add(exceptionToServer);
        }
        map.put("materielInfoList", JsonUtil.toJson(toServers));
        if (exceptionOutCountActual > 0) {
            return JsonUtil.toJson(map);
        } else {
            return "";
        }

    }

    private List<ExceptionDetailBean> getExceptionDetail() {
        List<ExceptionDetailBean> dataNew = new ArrayList<>();
        List<ExceptionDetailBean> data = adapterUnusal.getData();
        for (int i = 0; i < data.size(); i++) {
            ExceptionDetailBean exceptionDetailBean = data.get(i);
            double exceptionOutCount = exceptionDetailBean.getExceptionOutCount();
            if (exceptionOutCount == 0) {
                continue;
            }
            dataNew.add(exceptionDetailBean);
        }
        return dataNew;
    }

    @Override
    public void showExceptionOut(ExceptionOutBean exceptionOutBean) {
        if (exceptionOutBean != null) {
            if (!isReport) {
                Map<String, String> params = new HashMap<>();
                params.put("recordId", exceptionOutBean.getRecordId());
                params.put("status", exceptionOutBean.getStatus() + "");
                requestExceptionPrintData(params);
            }
        }
    }

    @Override
    public void showExceptionPrintData(ExceptionPrintBean exceptionPrintBean, String recordId, int status) {
        if (exceptionPrintBean != null) {
            MainActivity activity = (MainActivity) getActivity();
            if (!isReport) {
                //跳转到feedfragment /物料清单
//                activity.showFeedSucessPop("异常输出成功", 1,   );
                activity.showExceptionPrinterSucessPop("异常输出成功", 1, wrapid, recordId, status);//根据status来判断异常是否还有剩余
            } else {
                //回到异常管理
                activity.showExceptionPrinterSucessPop("打印异常报告", 0, wrapid, recordId, status);
            }
        }
    }

    private void requestExceptionPrintData(Map<String, String> params) {
        mUnusalPresent.getExceptionPrintData(params);
    }

}
