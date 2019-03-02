package workstation.zjyk.line.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import workstation.zjyk.line.R;
import workstation.zjyk.line.modle.bean.MaterielVo;
import workstation.zjyk.line.modle.bean.WorkStationRequestDetail;
import workstation.zjyk.line.modle.bean.Wrap;
import workstation.zjyk.line.ui.adapter.AdapterStationRequireDetail;
import workstation.zjyk.line.ui.adapter.AdapterTrayInfo;
import workstation.zjyk.line.ui.present.StationRequireDetailPresent;

/**
 * 未投递托盘详情
 * Created by zjgz on 2017/10/27.
 */

public class TrayInfoActivity extends BaseActivity {
    @BindView(R.id.tv_feed_title)
    TextView tvFeedTitle;
    @BindView(R.id.recycyleview)
    RecyclerView recycyleview;
    @BindView(R.id.tv_cancle)
    TextView tvCancle;
    private AdapterTrayInfo mAdapterTrayInfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_tray_info;
    }

    private void initData() {
    }

    private void initView() {
        recycyleview.setLayoutManager(new LinearLayoutManager(this));
        mAdapterTrayInfo = new AdapterTrayInfo();
        recycyleview.setAdapter(mAdapterTrayInfo);

        Intent intent = getIntent();
        if (intent != null) {
            Wrap wrap = intent.getParcelableExtra("data");
            if (wrap != null) {
                List<MaterielVo> materiels = wrap.getMateriels();
                if (materiels != null) {
                    mAdapterTrayInfo.setNewData(materiels);
                }
            }
        }

    }

    @Override
    public void showNetData(Object o) {
        super.showNetData(o);
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
