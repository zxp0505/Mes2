package workstation.zjyk.workstation.util.dialog;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import workstation.zjyk.workstation.R;
import workstation.zjyk.workstation.modle.bean.WSTrayVo;
import workstation.zjyk.workstation.modle.net.WSErrorResultClickListner;
import workstation.zjyk.workstation.ui.adapter.WSAdapterLookTrayListVp;
import workstation.zjyk.workstation.ui.adapter.WSAdapterTrayList;


public class WSLookTrayListVpDialog extends WSCommonDialog {


    @BindView(R.id.recycleview)
    RecyclerView recycleview;
    List<WSTrayVo> datas;
    String title;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_close)
    ImageView ivClose;
    private WSAdapterLookTrayListVp wsAdapterTrayList;

    public WSLookTrayListVpDialog(Context context, String title) {
        super(context, R.style.CommonDialog);
        this.title = title;
        initDialogView(context);

    }


    public WSLookTrayListVpDialog(Context context, int themeResId) {
        super(context, themeResId);
        initDialogView(context);
    }

    protected WSLookTrayListVpDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initDialogView(context);
    }

    private void initDialogView(Context context) {
        setContentView(R.layout.dialog_look_tray_list_vp);
        ButterKnife.bind(this);
        wsAdapterTrayList = new WSAdapterLookTrayListVp();
        recycleview.setLayoutManager(new LinearLayoutManager(getContext()));
        wsAdapterTrayList.bindToRecyclerView(recycleview);
        refreshData();

    }
    public void setData(String title, List<WSTrayVo> datas) {
        this.datas = datas;
        this.title = title;
        refreshData();
    }

    private void refreshData() {
        tvTitle.setText(title);
        wsAdapterTrayList.setNewData(datas);
    }

    @OnClick(R.id.iv_close)
    public void onViewClicked() {
        dismiss();
    }
}
