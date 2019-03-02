package workstation.zjyk.line.ui.fragments;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;

import cn.com.ethank.mylibrary.resourcelibrary.toast.ToastUtil;
import workstation.zjyk.line.R;
import workstation.zjyk.line.modle.bean.ExceptionDetailBean;
import workstation.zjyk.line.modle.bean.ExceptionRecordBean;
import workstation.zjyk.line.ui.adapter.AdapterExceptionReport;
import workstation.zjyk.line.ui.present.ExceptionPresent;
import workstation.zjyk.line.ui.present.MvpBasePresenter;
import workstation.zjyk.line.ui.views.ExceptionView;
import workstation.zjyk.line.util.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 异常报告
 * Created by zjgz on 2017/11/10.
 */

public class ExceptionReportFragment extends BaseFragment implements ExceptionView {

    @BindView(R.id.recycyleview)
    RecyclerView recycyleview;
    Unbinder unbinder;
    private Unbinder bind;
    private AdapterExceptionReport mAdapterExceptionReport;
    private ExceptionPresent mExceptionPresent;

    public static ExceptionReportFragment newInstance() {
        ExceptionReportFragment exceptionReportFragment = new ExceptionReportFragment();
        return exceptionReportFragment;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        bind = ButterKnife.bind(this, view);
        mExceptionPresent = new ExceptionPresent(this);
        mAdapterExceptionReport = new AdapterExceptionReport();
        recycyleview.setLayoutManager(new LinearLayoutManager(getActivity()));
        recycyleview.setAdapter(mAdapterExceptionReport);
        mAdapterExceptionReport.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                ExceptionRecordBean bean = (ExceptionRecordBean) adapter.getData().get(position);
                Map<String, String> params = new HashMap<>();
                params.put("recordId", bean.getRecordId());
                mExceptionPresent.getExceptionDataDetails(params, bean);
            }
        });
    }

    @Override
    public void initData() {
        Map<String, String> params = new HashMap<>();
        params.put("workStationId", Constants.getStationId());
        mExceptionPresent.getExceptionData(params);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_excption_report;
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
    public void onDestroy() {
        bind.unbind();
        super.onDestroy();
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

    @Override
    public void showExceptionRecord(List<ExceptionRecordBean> datas) {
        if (datas != null) {
            if (datas.size() == 0) {
                ToastUtil.showInfoCenterShort("无异常数据！");
            }
            mAdapterExceptionReport.setNewData(datas);
        }
    }

    @Override
    public void showExceptionDetails(List<ExceptionDetailBean> datas, ExceptionRecordBean bean) {
        if (datas != null && datas.size() > 0) {
            Bundle bundle = new Bundle();
            bundle.putBoolean("isReport", true);
            bundle.putParcelableArrayList("data", (ArrayList<? extends Parcelable>) datas);
            bundle.putString("exceptionNumber", bean.getCode());
            bundle.putString("recordId", bean.getRecordId());
            start(UnusalFragment.newInstance(bundle));
        } else {

        }
    }
}
