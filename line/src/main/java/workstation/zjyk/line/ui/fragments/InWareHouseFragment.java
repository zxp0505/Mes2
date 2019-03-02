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

import com.chad.library.adapter.base.BaseQuickAdapter;

import workstation.zjyk.line.R;
import workstation.zjyk.line.modle.bean.Idcount;
import workstation.zjyk.line.modle.bean.MaterielTypeEnum;
import workstation.zjyk.line.modle.bean.MaterielVo;
import workstation.zjyk.line.modle.bean.PutStorageMaterielVO;
import workstation.zjyk.line.ui.activity.MainActivity;
import workstation.zjyk.line.ui.adapter.AdapterInWarehouse;
import workstation.zjyk.line.ui.present.InWareHousePresent;
import workstation.zjyk.line.ui.present.MvpBasePresenter;
import workstation.zjyk.line.ui.views.BaseView;
import workstation.zjyk.line.util.Constants;
import workstation.zjyk.line.util.DialogCallBackTwo;
import cn.com.ethank.mylibrary.resourcelibrary.common_util.JsonUtil;
import workstation.zjyk.line.util.dialog.DialogUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.com.ethank.mylibrary.resourcelibrary.toast.ToastUtil;

/**
 * 入仓
 * Created by zjgz on 2017/10/26.
 */

public class InWareHouseFragment extends BaseFragment implements BaseView {
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
    private InWareHousePresent mInWareHousePresent;
    private AdapterInWarehouse mAdapterInWarehouse;
    private List<MaterielVo> materielVos;
    private String wrapid;

    public static InWareHouseFragment newInstance(Bundle bundle) {
        InWareHouseFragment feedOneFragment = new InWareHouseFragment();
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
            materielVos = arguments.getParcelableArrayList("data");
            wrapid = arguments.getString("wrapid");
        }
        tvFeedTitle.setText(R.string.tv_in_warehouse_title);
        tvTitleDescribe.setText(R.string.tv_in_warehouse_describe);
        tvNext.setText("确认入仓");
        tvCancle.setText("取消");
        mInWareHousePresent = new InWareHousePresent(this);
        recycyleview.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapterInWarehouse = new AdapterInWarehouse();
        recycyleview.setAdapter(mAdapterInWarehouse);
        if (materielVos != null && materielVos.size() > 0) {
            for (int i = 0; i < materielVos.size(); i++) {
                MaterielVo materielVo = materielVos.get(i);
                materielVo.setInWareCount(materielVo.getRemainCount());
            }
            mAdapterInWarehouse.setNewData(materielVos);
        }
        mAdapterInWarehouse.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(final BaseQuickAdapter adapter, View view, final int position) {
                MaterielVo materielVo = (MaterielVo) adapter.getData().get(position);
                double count = materielVo.getRemainCount();//剩余总数量
                double inWareCount = materielVo.getInWareCount();//入仓数量
                switch (view.getId()) {
                    case R.id.iv_reduce_ware_house:
                        //减少
                        if (inWareCount > 0) {
                            inWareCount--;
                            materielVo.setInWareCount(inWareCount);
                            adapter.notifyItemChanged(position);
                        } else {
                            ToastUtil.showCenterShort("入仓数量不能小于0",true);
                        }
                        break;
                    case R.id.iv_add_ware_house:
                        //增加
                        if (inWareCount < count) {
                            inWareCount++;
                            materielVo.setInWareCount(inWareCount);
                            adapter.notifyItemChanged(position);
                        } else {
                            ToastUtil.showCenterShort("入仓数量不能大于总数量",true);
                        }
                        break;
                    case R.id.tv_count_inware:
                        //弹框
                        DialogUtils.showInWareNumberDialog(getActivity(), materielVo, new DialogCallBackTwo() {
                            @Override
                            public void OnPositiveClick(Object obj) {
                                if (obj instanceof MaterielVo) {
                                    MaterielVo materielVo1 = (MaterielVo) obj;
                                    adapter.notifyItemChanged(position);
                                }
                            }

                            @Override
                            public void OnNegativeClick() {

                            }
                        });
                        break;
                }
            }
        });

    }

    @Override
    public void initData() {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_inwarehouse;
    }

    @Override
    protected View getLoadingTargetView() {
        return recycyleview;
    }

    @Override
    MvpBasePresenter getPresenter() {
        return mInWareHousePresent;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
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
                inWare();

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

    private void inWare() {
        String materielInfo = getParam();
        if (!TextUtils.isEmpty(materielInfo)) {
            Map<String, String> params = new HashMap<>();
            params.put("materielInfo", materielInfo);
            mInWareHousePresent.inWareRequest(params);
        } else {
            ToastUtil.showCenterShort("入仓数量不能为0!",true);
        }
    }

    private String getParam() {
        double inWareCountActurl = 0;
        PutStorageMaterielVO putStorageMaterielVO = new PutStorageMaterielVO();
        putStorageMaterielVO.setWorkStationId(Constants.getStationId());
        List<Idcount> idcounts = new ArrayList<>();
        putStorageMaterielVO.setIdcountList(idcounts);
        List<MaterielVo> data = mAdapterInWarehouse.getData();
        for (int i = 0; i < data.size(); i++) {
            MaterielVo materielVo = data.get(i);
            Idcount idcount = new Idcount();
            idcount.setMaterielBatchNumber(materielVo.getMaterielBatchNumber());
            double inWareCount = materielVo.getInWareCount();
            if (inWareCount == 0) {
                continue;
            }
            inWareCountActurl = inWareCountActurl + inWareCount;
            idcount.setMaterielCount(inWareCount);
            idcount.setMaterielOid(materielVo.getModel());
            idcount.setMaterielId(materielVo.getMaterielId());
            idcount.setMaterielType(MaterielTypeEnum.ACCESSORIES);
            idcount.setDetailId(materielVo.getDetailId());
            idcounts.add(idcount);
        }
        if (inWareCountActurl > 0) {
            return JsonUtil.toJson(putStorageMaterielVO);
        } else {
            return "";
        }

    }

    @Override
    public void showNetData(Object o) {
        super.showNetData(o);
        if (o != null) {
            MainActivity activity = (MainActivity) getActivity();
            activity.showFeedSucessPop("入仓成功", 1, wrapid);
        } else {

        }
    }
}
