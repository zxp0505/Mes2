package workstation.zjyk.com.scanapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

import butterknife.BindView;
import workstation.zjyk.com.scanapp.R;
import workstation.zjyk.com.scanapp.modle.bean.ScanTrayInfoWIPVo;
import workstation.zjyk.com.scanapp.ui.adapter.WipDetailAdapter;

/**
 * Created by zjgz on 2018/2/8.
 */

public class WipDetailActivity extends ScanBaseActivity {
    @BindView(R.id.recycleview)
    RecyclerView recycleview;
    private ArrayList<ScanTrayInfoWIPVo> wips;
    private WipDetailAdapter wipDetailAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        Intent intent = getIntent();
        if (intent != null) {
            wips = intent.getParcelableArrayListExtra("data");
        }
        tvTitle.setText("在制品详情");
        tvTitle.setVisibility(View.VISIBLE);
        recycleview.setLayoutManager(new LinearLayoutManager(this));
        wipDetailAdapter = new WipDetailAdapter();
        recycleview.setAdapter(wipDetailAdapter);
        wipDetailAdapter.setNewData(wips);

    }

    @Override
    protected void creatPresent() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_wip;
    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

}
