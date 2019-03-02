package workstation.zjyk.com.scanapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import workstation.zjyk.com.scanapp.R;
import workstation.zjyk.com.scanapp.modle.bean.ScanTrayInfoMaterielVo;
import workstation.zjyk.com.scanapp.ui.adapter.MaterailDetailAdapter;

/**
 * Created by zjgz on 2018/2/8.
 */

public class MaterailDetailActivity extends ScanBaseActivity {
    @BindView(R.id.recycleview)
    RecyclerView recycleview;
    private MaterailDetailAdapter materailDetailAdapter;
    private ArrayList<ScanTrayInfoMaterielVo> materails;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        Intent intent = getIntent();
        if (intent != null) {
            materails = intent.getParcelableArrayListExtra("data");
        }
        tvTitle.setText("物料详情");
        recycleview.setLayoutManager(new LinearLayoutManager(this));
        materailDetailAdapter = new MaterailDetailAdapter();
        recycleview.setAdapter(materailDetailAdapter);
        materailDetailAdapter.setNewData(materails);

    }

    @Override
    protected void creatPresent() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_materail;
    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

}
