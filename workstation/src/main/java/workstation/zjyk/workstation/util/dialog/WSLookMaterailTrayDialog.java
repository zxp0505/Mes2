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
import workstation.zjyk.workstation.modle.bean.WSMaterialTray;
import workstation.zjyk.workstation.modle.bean.WSWipTray;
import workstation.zjyk.workstation.modle.net.WSErrorResultClickListner;
import workstation.zjyk.workstation.ui.adapter.WSAdapterLookMaterailTray;
import workstation.zjyk.workstation.ui.adapter.WSAdapterTrayList;


public class WSLookMaterailTrayDialog extends WSCommonDialog {
    public static final int STATUS0 = 0;
    public static final int STATUS1 = 1;
    int currentStatus = 0;//当前标识 0 :查看物料所在托盘 1：查看在制品所在托盘
    @BindView(R.id.recycleview)
    RecyclerView recycleview;
    WSMaterialTray wsMaterialTray;
    WSWipTray wsWipTray;
    String title;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_close)
    ImageView ivClose;
    private WSAdapterLookMaterailTray wsAdapterLookMaterailTray;

    public WSLookMaterailTrayDialog(Context context, String title, WSMaterialTray wsMaterialTray, WSWipTray wsWipTray, int currentStatus) {
        super(context, R.style.CommonDialog);
        this.wsMaterialTray = wsMaterialTray;
        this.wsWipTray = wsWipTray;
        this.currentStatus = currentStatus;
        this.title = title;
        initDialogView(context);

    }


    public WSLookMaterailTrayDialog(Context context, int themeResId) {
        super(context, themeResId);
        initDialogView(context);
    }

    protected WSLookMaterailTrayDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initDialogView(context);
    }

    private void initDialogView(Context context) {
        setContentView(R.layout.dialog_look_materail_tray);
        ButterKnife.bind(this);
        wsAdapterLookMaterailTray = new WSAdapterLookMaterailTray();
        recycleview.setLayoutManager(new LinearLayoutManager(getContext()));
        recycleview.setAdapter(wsAdapterLookMaterailTray);
        refreshData();
    }

    public void setData(String title, WSMaterialTray wsMaterialTray, WSWipTray wsWipTray, int currentStatus) {
        this.wsMaterialTray = wsMaterialTray;
        this.wsWipTray = wsWipTray;
        this.currentStatus = currentStatus;
        this.title = title;
        refreshData();
    }

    private void refreshData() {
        if (currentStatus == STATUS0 && wsMaterialTray != null) {
            tvTitle.setText(wsMaterialTray.getMaterialName() + "(物料)所在托盘");
            wsAdapterLookMaterailTray.setNewData(wsMaterialTray.getTrayList());
        } else if (currentStatus == STATUS1 && wsWipTray != null) {
            tvTitle.setText(wsWipTray.getProcedureName() + "(在制品)所在托盘");
            wsAdapterLookMaterailTray.setNewData(wsWipTray.getTrayList());
        }
    }


    @OnClick(R.id.iv_close)
    public void onViewClicked() {
        dismiss();
    }
}
