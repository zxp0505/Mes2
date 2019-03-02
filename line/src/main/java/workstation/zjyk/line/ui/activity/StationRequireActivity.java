package workstation.zjyk.line.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import workstation.zjyk.line.R;
import workstation.zjyk.line.modle.bean.WorkStationRequestDetail;
import workstation.zjyk.line.ui.adapter.AdapterStationRequireDetail;
import workstation.zjyk.line.ui.present.StationRequireDetailPresent;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 工位需求详情
 * Created by zjgz on 2017/10/27.
 */

public class StationRequireActivity extends BaseActivity {
    @BindView(R.id.tv_feed_title)
    TextView tvFeedTitle;
    @BindView(R.id.recycyleview)
    RecyclerView recycyleview;
    @BindView(R.id.tv_cancle)
    TextView tvCancle;
    private AdapterStationRequireDetail mAdapterStationRequireDetail;
    private StationRequireDetailPresent mStationRequireDetailPresent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
//        initData();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.pop_station_require_detail;
    }

    private void initData() {
        mStationRequireDetailPresent.requestData();
    }

    private void initView() {
        mStationRequireDetailPresent = new StationRequireDetailPresent(this);
        recycyleview.setLayoutManager(new LinearLayoutManager(this));
        mAdapterStationRequireDetail = new AdapterStationRequireDetail();
        recycyleview.setAdapter(mAdapterStationRequireDetail);

        Intent intent = getIntent();
        if (intent != null) {
            ArrayList<WorkStationRequestDetail> datas = intent.getParcelableArrayListExtra("data");
            mAdapterStationRequireDetail.setNewData(datas);
        }

    }

    @Override
    public void showNetData(Object o) {
        super.showNetData(o);
//        if (o != null && o instanceof List) {
//            List<StationMaterailDetailBean> datas = (List<StationMaterailDetailBean>) o;
//            mAdapterStationRequireDetail.setNewData(datas);
//        }
    }

    @Override
    protected View getLoadingTargetView() {
        return recycyleview;
    }

    @OnClick(R.id.tv_cancle)
    public void click() {
        finish();
    }
}
