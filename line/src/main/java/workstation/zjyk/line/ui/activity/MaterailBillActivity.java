package workstation.zjyk.line.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.ethank.mylibrary.resourcelibrary.toast.ToastUtil;
import workstation.zjyk.line.R;
import workstation.zjyk.line.modle.bean.ExceptionDetailBean;
import workstation.zjyk.line.modle.bean.MaterielTypeEnum;
import workstation.zjyk.line.modle.bean.MaterielVo;
import workstation.zjyk.line.modle.bean.WorkStationRequest;
import workstation.zjyk.line.modle.bean.WorkStationRequestDetail;
import workstation.zjyk.line.modle.bean.Wrap;
import workstation.zjyk.line.ui.adapter.AdapterMaterailBill;
import workstation.zjyk.line.ui.present.FeedOnePresent;
import workstation.zjyk.line.ui.views.FeedOneView;
import workstation.zjyk.line.util.Constants;

/**
 * 物料清单
 * Created by zjgz on 2017/10/26.
 */

public class MaterailBillActivity extends BaseActivity implements FeedOneView {
    @BindView(R.id.tv_send_good_number)
    TextView tvSendGoodNumber;
    @BindView(R.id.recycyleview)
    RecyclerView recycyleview;
    @BindView(R.id.tv_start_feed)
    TextView tvStartFeed;
    @BindView(R.id.tv_put_in_store)
    TextView tvPutInStore;
    @BindView(R.id.tv_unusal)
    TextView tvUnusal;
    @BindView(R.id.ll_content)
    LinearLayout llContent;
    @BindView(R.id.iv_close)
    ImageView ivClose;
    @BindView(R.id.rl_opreat)
    RelativeLayout rlOpreat;
    private AdapterMaterailBill mAdapterMaterailBill;
    private Wrap wrap;
    private String orderId;
    private FeedOnePresent mFeedOnePresent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_materail_bill;
    }

    private void initView() {
        mFeedOnePresent = new FeedOnePresent(this);
        Intent intent = getIntent();
        if (intent != null) {
            wrap = intent.getParcelableExtra("data");
            if (wrap != null) {
                tvSendGoodNumber.setText("仓库发料条码:" + wrap.getCode());
                orderId = wrap.getOrderId();
                if(wrap.isOnlyLook()){
                    rlOpreat.setVisibility(View.GONE);
                }


            }
        }
        //暂时隐藏
        rlOpreat.setVisibility(View.GONE);

        mAdapterMaterailBill = new AdapterMaterailBill(orderId);
        recycyleview.setLayoutManager(new LinearLayoutManager(this));
        recycyleview.setAdapter(mAdapterMaterailBill);
        if (wrap != null) {
            List<MaterielVo> materiels = wrap.getMateriels();
            mAdapterMaterailBill.setNewData(materiels);
        }


    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @OnClick({R.id.tv_start_feed, R.id.tv_put_in_store, R.id.tv_unusal, R.id.iv_close})
    public void click(View view) {

        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        switch (view.getId()) {
            case R.id.tv_start_feed:
                if (!checkRemainCount()) {
                    return;
                }
                //先查看工位数量 大于0 跳转
                mFeedOnePresent.requestData(wrap.getCode());

                break;
            case R.id.tv_put_in_store:
                if (!checkRemainCount()) {
                    return;
                }
                List<MaterielVo> accessories = getAccessories();
                if (accessories != null && accessories.size() > 0) {
                    bundle.putInt(Constants.RESULT_KEY, Constants.WAREHOUSE);
                    bundle.putString("wrapid", wrap.getCode());
                    bundle.putParcelableArrayList("data", (ArrayList<? extends Parcelable>) accessories);
                    intent.putExtras(bundle);
                    setResult(0, intent);
                    finish();
                } else {
                    ToastUtil.showCenterShort("没有可以入仓的辅料", true);
                }

                break;
            case R.id.iv_close:
                finish();
                break;
            case R.id.tv_unusal:
                //异常
                if (!checkRemainCount()) {
                    return;
                }
                List<ExceptionDetailBean> accessories1 = convertExceptionBean();
                bundle.putString("wrapid", wrap.getCode());
                bundle.putString("serialNumber", wrap.getOrderId());
                bundle.putParcelableArrayList("data", (ArrayList<? extends Parcelable>) accessories1);
                bundle.putInt(Constants.RESULT_KEY, Constants.UNUSAL);
                intent.putExtras(bundle);
                setResult(0, intent);
                finish();
                break;
        }

    }

    private List<MaterielVo> getAccessories() {
        List<MaterielVo> accessorsData = new ArrayList<>();
        List<MaterielVo> data = mAdapterMaterailBill.getData();
        double unDistributioncCount = 0;
        for (int i = 0; i < data.size(); i++) {
            MaterielVo materielVo = data.get(i);
            if (materielVo.getMaterielType() == MaterielTypeEnum.ACCESSORIES) {
                accessorsData.add(materielVo);
                unDistributioncCount = unDistributioncCount + materielVo.getRemainCount();
            }
        }
        if (unDistributioncCount > 0) {
            return accessorsData;
        } else {
            ToastUtil.showCenterShort("暂无可入仓的物料", true);
            return null;
        }
    }

    /**
     * 检查剩余物料
     *
     * @return
     */
    private boolean checkRemainCount() {
        List<MaterielVo> data = mAdapterMaterailBill.getData();
        double unDistributioncCount = 0;
        for (int i = 0; i < data.size(); i++) {
            MaterielVo materielVo = data.get(i);
            unDistributioncCount = unDistributioncCount + materielVo.getRemainCount();
        }

        if (unDistributioncCount > 0) {
            return true;
        } else {
            ToastUtil.showCenterShort("暂无可分配的物料", true);
            return false;
        }
    }

    private List<ExceptionDetailBean> convertExceptionBean() {
        List<ExceptionDetailBean> accessorsData = new ArrayList<>();
        List<MaterielVo> data = mAdapterMaterailBill.getData();
        for (int i = 0; i < data.size(); i++) {
            MaterielVo materielVo = data.get(i);
            ExceptionDetailBean bean = new ExceptionDetailBean();
            bean.setOid(materielVo.getModel());
            bean.setExceptionOutCount(0);
            bean.setCount(materielVo.getRemainCount());
            bean.setMaterielBatchNumber(materielVo.getMaterielBatchNumber());
            bean.setName(materielVo.getName());
            accessorsData.add(bean);
        }
        return accessorsData;
    }

    @Override
    public void showStationDatas(List<WorkStationRequest> datas) {
        if (datas != null && datas.size() > 0) {
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putInt(Constants.RESULT_KEY, Constants.START_FEED);
            bundle.putString("wrapid", wrap.getCode());
            bundle.putParcelableArrayList("data", (ArrayList<? extends Parcelable>) datas);
            intent.putExtras(bundle);
            setResult(0, intent);
            finish();
        } else {
            ToastUtil.showCenterShort("没有可选的工位", true);
        }
    }

    @Override
    public void showStationRequireDetails(List<WorkStationRequestDetail> datas) {

    }
}
