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
import workstation.zjyk.workstation.modle.net.WSErrorResultClickListner;
import workstation.zjyk.workstation.ui.adapter.WSAdapterTrayListMaterail;

/**
 * 输入物料情况查看托盘物料情况
 */
public class WSLookTrayListMaterailDialog extends WSCommonDialog {


    WSErrorResultClickListner errorResultClickListner;
    @BindView(R.id.recycleview)
    RecyclerView recycleview;
    List<MultiItemEntity> datas;
    String title;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_close)
    ImageView ivClose;
    private WSAdapterTrayListMaterail wsAdapterTrayList;

    public WSLookTrayListMaterailDialog(Context context, String title, List<MultiItemEntity> datas) {
        super(context, R.style.CommonDialog);
        this.datas = datas;
        this.title = title;
        initDialogView(context);

    }


    public WSLookTrayListMaterailDialog(Context context, int themeResId) {
        super(context, themeResId);
        initDialogView(context);
    }

    protected WSLookTrayListMaterailDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initDialogView(context);
    }

    private void initDialogView(Context context) {
        setContentView(R.layout.dialog_look_tray_list_materail);
        ButterKnife.bind(this);
        wsAdapterTrayList = new WSAdapterTrayListMaterail();
        recycleview.setLayoutManager(new LinearLayoutManager(getContext()));
        wsAdapterTrayList.bindToRecyclerView(recycleview);
        refreshData();

    }

    public void setData(String title, List<MultiItemEntity> datas) {
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
