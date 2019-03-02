package workstation.zjyk.line.ui.fragments;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import workstation.zjyk.line.R;
import workstation.zjyk.line.modle.bean.LineMaterielStorageManagerVo;
import workstation.zjyk.line.ui.adapter.AdapterInventoryControl;
import workstation.zjyk.line.ui.present.InventoryControlPresent;
import workstation.zjyk.line.ui.present.MvpBasePresenter;
import workstation.zjyk.line.ui.views.InventoryView;
import workstation.zjyk.line.util.dialog.DialogUtils;
import workstation.zjyk.line.util.dialog.InventoryDialog;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.com.ethank.mylibrary.resourcelibrary.toast.ToastUtil;
import me.yokeyword.fragmentation.ISupportFragment;

/**
 * 库存管理
 * Created by zjgz on 2017/11/13.
 */

public class InventoryControlFragment extends BaseFragment implements InventoryView {

    @BindView(R.id.recycyleview)
    RecyclerView recycyleview;
    Unbinder unbinder;
    private Unbinder bind;
    private AdapterInventoryControl mAdapterInventoryControl;
    private InventoryControlPresent mInventoryControlPresent;

    public static ISupportFragment newInstance() {
        InventoryControlFragment inventoryControlFragment = new InventoryControlFragment();
        return inventoryControlFragment;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        bind = ButterKnife.bind(this, view);
        mInventoryControlPresent = new InventoryControlPresent(this);
        mAdapterInventoryControl = new AdapterInventoryControl();
        recycyleview.setLayoutManager(new LinearLayoutManager(getActivity()));
        recycyleview.setAdapter(mAdapterInventoryControl);
        mAdapterInventoryControl.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                LineMaterielStorageManagerVo lineMaterielStorageManagerVo = (LineMaterielStorageManagerVo) adapter.getData().get(position);
                switch (view.getId()) {
                    case R.id.tv_opration:
                        DialogUtils.showInventoryDialog(getActivity(), lineMaterielStorageManagerVo, new InventoryDialog.InventoryDialogCallback() {
                            @Override
                            public void save(LineMaterielStorageManagerVo lineMaterielStorageManagerVo) {
                                Map<String, String> params = new HashMap<>();
                                params.put("materielId", lineMaterielStorageManagerVo.getMaterielId());
                                params.put("count", lineMaterielStorageManagerVo.getModifyCount() + "");
                                mInventoryControlPresent.updataInventoryData(params, lineMaterielStorageManagerVo);
                            }

                            @Override
                            public void cancle() {

                            }
                        });
                        break;
                }
            }
        });
    }

    @Override
    public void initData() {
        Map<String, String> params = new HashMap<>();
        mInventoryControlPresent.requestInventoryDatas(params);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_inventory_control;
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
    public void showInventoryData(List<LineMaterielStorageManagerVo> datas) {
        if (datas != null) {
            if (datas.size() > 0) {
                mAdapterInventoryControl.setNewData(datas);
            }else {
                ToastUtil.showCenterShort("没有库存记录",true);
            }
        }
    }

    @Override
    public void showUpdataResult(boolean result, LineMaterielStorageManagerVo lineMaterielStorageManagerVo) {
        if (result) {
            lineMaterielStorageManagerVo.setCount(lineMaterielStorageManagerVo.getModifyCount());
            mAdapterInventoryControl.notifyItemChanged(lineMaterielStorageManagerVo.getPosition());
        }
    }
}
