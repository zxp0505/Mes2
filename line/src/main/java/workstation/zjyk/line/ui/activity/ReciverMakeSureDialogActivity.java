package workstation.zjyk.line.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.ethank.mylibrary.resourcelibrary.toast.ToastUtil;
import workstation.zjyk.line.R;
import workstation.zjyk.line.modle.bean.MaterielVo;
import workstation.zjyk.line.modle.bean.MessageBean;
import workstation.zjyk.line.modle.bean.Wrap;
import workstation.zjyk.line.ui.adapter.AdapterReciverMakeSure;
import workstation.zjyk.line.ui.present.ReciverMakeSurepresent;
import workstation.zjyk.line.util.Constants;

/**
 * 确认收料
 * Created by zjgz on 2017/10/25.
 */

public class ReciverMakeSureDialogActivity extends BaseActivity {

    @BindView(R.id.tv_send_good_number)
    TextView tvSendGoodNumber;
    @BindView(R.id.recycyleview)
    RecyclerView recycyleview;
    @BindView(R.id.tv_reciver_and_makesure)
    TextView tvReciverAndMakesure;
    @BindView(R.id.tv_reciver_makesure)
    TextView tvReciverMakesure;
    @BindView(R.id.tv_cancle)
    TextView tvCancle;
    private ReciverMakeSurepresent reciverMakeSurepresent;
    private AdapterReciverMakeSure mAdapterReciverMakeSure;
    private Wrap data;
    private List<MaterielVo> materiels;
    private String orderId;
    private String code;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        reciverMakeSurepresent = new ReciverMakeSurepresent(this);
//        EventBus.getDefault().register(this);
        initView();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_reciver_make_sure;
    }

    private void initView() {
        Intent intent = getIntent();
        if (intent != null) {
            data = intent.getParcelableExtra("data");
            if (data != null) {
                code = data.getCode();
                tvSendGoodNumber.setText("仓库发料条码:" + code);
                materiels = data.getMateriels();
                orderId = data.getOrderId();
            }
        }

        mAdapterReciverMakeSure = new AdapterReciverMakeSure(orderId);
        recycyleview.setLayoutManager(new LinearLayoutManager(this));
        recycyleview.setAdapter(mAdapterReciverMakeSure);
        if (materiels != null) {
            mAdapterReciverMakeSure.setNewData(materiels);
        }

        mAdapterReciverMakeSure.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                List<MaterielVo> data = adapter.getData();
                MaterielVo materielVo = data.get(position);
                if(materielVo.isSeleted()){
                    materielVo.setSeleted(false);
                }else{
                    materielVo.setSeleted(true);
                }
                adapter.notifyItemChanged(position);
            }
        });


    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageBean messageBean) {
        switch (messageBean.getCode()) {
            case 0:
                setReciverAndFeedResult();
                break;
        }
    }

    @Override
    protected View getLoadingTargetView() {
        return recycyleview;
    }

    @OnClick({R.id.tv_reciver_and_makesure, R.id.tv_reciver_makesure, R.id.tv_cancle})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.tv_reciver_and_makesure:
                setReciverAndFeedResult();
                break;
            case R.id.tv_reciver_makesure:
                Map<String, String> map = new HashMap<>();
                map.put("code", code);
                reciverMakeSurepresent.reciverRequest(map);
//                setReciverResult();
                break;
            case R.id.tv_cancle:
                finish();
                break;

        }
    }

    private void setReciverAndFeedResult() {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.RESULT_DATA, data);
        bundle.putInt(Constants.RESULT_KEY, Constants.RECIVER_AND_FEED_FRAGMENT);
        intent.putExtras(bundle);
        setResult(0, intent);
        finish();
    }

    private void setReciverResult() {

        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.RESULT_DATA, data);
        bundle.putInt(Constants.RESULT_KEY, Constants.RECIVER_MAKE_SURE);
        intent.putExtras(bundle);
        setResult(0, intent);
        finish();
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Override
    public void showNetData(Object o) {
        if (o != null) {
            setReciverResult();
        } else {
//            ToastUtil.showCenterShort("收料失败",true);
        }
    }
}
