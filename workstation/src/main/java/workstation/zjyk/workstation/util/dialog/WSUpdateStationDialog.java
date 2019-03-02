package workstation.zjyk.workstation.util.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.ethank.mylibrary.resourcelibrary.toast.ToastUtil;
import workstation.zjyk.workstation.R;
import workstation.zjyk.workstation.modle.bean.WSWorkStation;
import workstation.zjyk.workstation.ui.adapter.WSAdapterUpdateStationDialog;
import workstation.zjyk.workstation.util.dialog.callback.WSDialogCallBackTwo;

/**
 * Created by zjgz on 2017/11/1.
 */

public class WSUpdateStationDialog extends WSCommonDialog implements DialogInterface.OnDismissListener {
    @BindView(R.id.tv_dialog_title)
    TextView tvDialogTitle;
    @BindView(R.id.tv_cancle)
    TextView tvCancle;
    WSDialogCallBackTwo callBack;
    @BindView(R.id.tv_sure)
    TextView tvSure;
    @BindView(R.id.recycyleview)
    RecyclerView recycyleview;
    String title;
    private WSAdapterUpdateStationDialog wsAdapterUpdateStationDialog;
    WSWorkStation selectWorkStation = null;

    public WSUpdateStationDialog(Context context, String title, WSDialogCallBackTwo callBack) {
        super(context, R.style.CommonDialog);
        this.callBack = callBack;
        this.title = title;
        initDialogView(context);
    }

    public WSUpdateStationDialog(Context context, int themeResId) {
        super(context, themeResId);
        initDialogView(context);
    }

    protected WSUpdateStationDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initDialogView(context);
    }

    private void initDialogView(Context context) {
        setContentView(R.layout.dialog_update_station);
        ButterKnife.bind(this);
        tvDialogTitle.setText(title);
        tvSure.setTag(false);
        setTvSureBg();
        wsAdapterUpdateStationDialog = new WSAdapterUpdateStationDialog();
        recycyleview.setLayoutManager(new GridLayoutManager(context, 3));
        recycyleview.setAdapter(wsAdapterUpdateStationDialog);
        wsAdapterUpdateStationDialog.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                WSWorkStation wsWorkStation = (WSWorkStation) adapter.getData().get(position);
                if (selectWorkStation != null && selectWorkStation.getPosition() == position) {
                    return;
                }
                if (selectWorkStation != null) {
                    selectWorkStation.setSelect(false);
                    wsAdapterUpdateStationDialog.notifyItemChanged(selectWorkStation.getPosition());
                }
                selectWorkStation = wsWorkStation;
                selectWorkStation.setSelect(true);
                wsAdapterUpdateStationDialog.notifyItemChanged(selectWorkStation.getPosition());
                setTvSureBg();
            }
        });
        setOnDismissListener(this);
    }

    private void setTvSureBg() {
        if (selectWorkStation != null) {
            if (tvSure.getTag() != null && (!(boolean) tvSure.getTag())) {
                tvSure.setBackgroundResource(R.drawable.shape_blue);
                tvSure.setTag(true);
            }
        } else {
            tvSure.setBackgroundResource(R.drawable.shape_gray);
            tvSure.setTag(false);
        }
    }

    public void setData(List<WSWorkStation> wsWorkStations) {
        wsAdapterUpdateStationDialog.setNewData(wsWorkStations);
        setTvSureBg();
    }

    @OnClick({R.id.tv_cancle, R.id.tv_sure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_sure:
                boolean tag = (boolean) tvSure.getTag();
                if (tag) {
                    callBack.OnPositiveClick(selectWorkStation);
                } else {
                    ToastUtil.showInfoCenterShort("请选择要更换的工位");
                }
                break;
            case R.id.tv_cancle:
                callBack.OnNegativeClick();
                dismiss();
                break;
        }

    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        selectWorkStation = null;
    }
}
