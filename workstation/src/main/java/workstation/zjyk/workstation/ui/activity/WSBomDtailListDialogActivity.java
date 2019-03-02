package workstation.zjyk.workstation.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.ethank.mylibrary.resourcelibrary.common_util.JsonUtil;
import cn.com.ethank.mylibrary.resourcelibrary.toast.ToastUtil;
import workstation.zjyk.workstation.BuildConfig;
import workstation.zjyk.workstation.R;
import workstation.zjyk.workstation.modle.bean.WSConcernMaterielVO;
import workstation.zjyk.workstation.modle.bean.WSMaterial;
import workstation.zjyk.workstation.modle.bean.WSProductProcedureConcernMaterielVO;
import workstation.zjyk.workstation.modle.bean.WSTrayLoadInfo;
import workstation.zjyk.workstation.modle.bean.WSWip;
import workstation.zjyk.workstation.ui.adapter.WSAdapterBomMaking;
import workstation.zjyk.workstation.ui.adapter.WSAdapterBomMaterail;
import workstation.zjyk.workstation.ui.present.WSBomDetailPresent;
import workstation.zjyk.workstation.ui.views.WSBomDetailView;

/**
 * Created by zjgz on 2017/12/13.
 */

public class WSBomDtailListDialogActivity extends WSBaseActivity<WSBomDetailPresent> implements WSBomDetailView {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.recycyle_entry_materail)
    RecyclerView recycyleEntryMaterail;
    @BindView(R.id.recycyle_making)
    RecyclerView recycyleMaking;
    @BindView(R.id.iv_close)
    ImageView ivClose;
    @BindView(R.id.tv_follow)
    TextView tvFollow;
    @BindView(R.id.tv_print)
    TextView tvPrint;
    private WSAdapterBomMaking mAdapterBomMaking;
    private WSAdapterBomMaterail mAdapterBomMaterail;
    private String taskId;
    private String productId;
    private String procedureId;
    private boolean isRefresh = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initOnCreate() {
        initView();
    }


    private void initView() {
        if (BuildConfig.isInspect) {
            tvFollow.setVisibility(View.INVISIBLE);
            tvPrint.setVisibility(View.INVISIBLE);
        }
        setFollowStatusBg(null);
        mAdapterBomMaking = new WSAdapterBomMaking();
        recycyleMaking.setLayoutManager(new LinearLayoutManager(this));
        recycyleMaking.setAdapter(mAdapterBomMaking);


        mAdapterBomMaterail = new WSAdapterBomMaterail();
        recycyleEntryMaterail.setLayoutManager(new LinearLayoutManager(this));
        recycyleEntryMaterail.setAdapter(mAdapterBomMaterail);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        WSTrayLoadInfo mTrayLoadInfo = bundle.getParcelable("data");
        taskId = bundle.getString("taskId");
        productId = bundle.getString("productId");
        procedureId = bundle.getString("procedureId");
        if (mTrayLoadInfo != null) {
            List<WSMaterial> materialList = mTrayLoadInfo.getMaterialList();
            List<WSWip> wipList = mTrayLoadInfo.getWipList();
            List<WSMaterial> materials = handleData(materialList);
            mAdapterBomMaterail.setNewData(materials);
            mAdapterBomMaking.setNewData(wipList);
        }

        mAdapterBomMaterail.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (tvFollow.getVisibility() != View.VISIBLE) {
                    return;
                }
                List<WSMaterial> data = adapter.getData();
                WSMaterial wsMaterial = data.get(position);
                int followStatus = wsMaterial.getFollowStatus();
                switch (view.getId()) {
                    case R.id.fl_root:
                        if (followStatus == 0) {
                            selectCount++;
                        } else {
                            selectCount--;
                        }
                        followStatus = followStatus == 0 ? 1 : 0;
                        wsMaterial.setFollowStatus(followStatus);
                        adapter.notifyItemChanged(position);
                        setFollowStatusBg(wsMaterial);
                        break;
                }

            }
        });

    }

    private void setFollowStatusBg(WSMaterial wsMaterial) {
        if (wsMaterial != null) {
            String id = wsMaterial.getId();
            if (followed.containsKey(id)) {
                if (wsMaterial.getFollowStatus() == 0) {
                    setFollowTrue();
                } else {
                    setFollowFalse();
                }
            } else {
                if (wsMaterial.getFollowStatus() == 0) {
                    if (followed.size() < selectCount) {
                        setFollowTrue();
                    } else {
                        setFollowFalse();
                    }
                } else {
                    setFollowTrue();
                }
            }
        } else {
            setFollowFalse();
        }
    }

    private void setFollowTrue() {
        boolean isClickable = (boolean) tvFollow.getTag();
        if (!isClickable) {
            tvFollow.setBackgroundResource(R.drawable.shape_blue);
            tvFollow.setTag(true);
        }
    }

    private void setFollowFalse() {
        tvFollow.setBackgroundResource(R.drawable.shape_gray_bg);
        tvFollow.setTag(false);
    }

    Map<String, String> followed = new HashMap<>();
    int selectCount = 0;

    private List<WSMaterial> handleData(List<WSMaterial> materialList) {
        List<WSMaterial> mainMaterials = new ArrayList<>();
        if (materialList != null && materialList.size() > 0) {
            List<WSMaterial> assisMaterials = new ArrayList<>();
            for (int i = 0; i < materialList.size(); i++) {
                WSMaterial wsMaterial = materialList.get(i);
                String tag = wsMaterial.getTag();//1主料 2辅料
                if ("2".equals(tag)) {
                    assisMaterials.add(wsMaterial);
                } else {
                    //主料
                    mainMaterials.add(wsMaterial);
                }

                if (wsMaterial.getFollowStatus() == 1) {
                    followed.put(wsMaterial.getId(), wsMaterial.getName());
                }
            }
            mainMaterials.addAll(assisMaterials);
        }
        selectCount = followed.size();
        return mainMaterials;

    }

    @Override
    protected void creatPresent() {
        currentPresent = new WSBomDetailPresent();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_bom;
    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @OnClick({R.id.iv_close, R.id.tv_print, R.id.tv_follow})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_close:
                refreshQuit();
                break;
            case R.id.tv_print:
                requestPrintBom();
                break;
            case R.id.tv_follow:
                //变更关注
                boolean tag = (boolean) tvFollow.getTag();
                if (tag) {
                    commitFollowData();
                } else {
                    ToastUtil.showInfoCenterShort("请选择变更关注的物料");
                }
                break;
        }
    }

    private void refreshQuit() {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putBoolean("isRefresh", isRefresh);
        intent.putExtras(bundle);
        setResult(0, intent);
        finish();
    }

    private void requestPrintBom() {
        Map<String, String> params = new HashMap<>();
        params.put("taskId", taskId);
        currentPresent.requestPrintBom(params);
    }

    private void commitFollowData() {
        WSProductProcedureConcernMaterielVO followData = getFollowData();
        String followDataStr = JsonUtil.toJson(followData);
        if (!TextUtils.isEmpty(followDataStr)) {
            Map<String, String> params = new HashMap<>();
            params.put("concernMaterielListInfo", followDataStr);
            params.put("taskId", taskId);
            currentPresent.commitFollowData(params);
        }
    }

    private WSProductProcedureConcernMaterielVO getFollowData() {
        WSProductProcedureConcernMaterielVO wsProductProcedureConcernMaterielVO = new WSProductProcedureConcernMaterielVO();
        wsProductProcedureConcernMaterielVO.setProductId(productId);
        wsProductProcedureConcernMaterielVO.setProcedureId(procedureId);
        List<WSConcernMaterielVO> concernMaterielList = new ArrayList<>();
        wsProductProcedureConcernMaterielVO.setConcernMaterielList(concernMaterielList);
        List<WSMaterial> materials = mAdapterBomMaterail.getData();
        for (int i = 0; i < materials.size(); i++) {
            WSMaterial wsMaterial = materials.get(i);
            if (wsMaterial.getFollowStatus() == 1) {
                WSConcernMaterielVO wsConcernMaterielVO = new WSConcernMaterielVO();
                wsConcernMaterielVO.setMaterielId(wsMaterial.getId());
                wsConcernMaterielVO.setConcernProcedureId(wsMaterial.getProcedureId());
                wsConcernMaterielVO.setCount(wsMaterial.getCount());
                concernMaterielList.add(wsConcernMaterielVO);
            }
        }

        return wsProductProcedureConcernMaterielVO;
    }

    @Override
    public void showPrintResult(boolean result) {
        if (result) {
            ToastUtil.showInfoCenterShort(getString(R.string.text_print_success));
        }
    }

    @Override
    public void showUpdateFollow(boolean result) {
        if (result) {
            isRefresh = true;
            ToastUtil.showInfoCenterShort("关注变更成功");
            resetFollowStatus();
        }
    }

    private void resetFollowStatus() {
        selectCount = 0;
        handleData(mAdapterBomMaterail.getData());
        setFollowStatusBg(null);
    }
}
