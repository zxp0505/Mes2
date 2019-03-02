package workstation.zjyk.workstation.util.dialog;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import workstation.zjyk.workstation.R;
import workstation.zjyk.workstation.modle.bean.WSTaskDetailItemBean;
import workstation.zjyk.workstation.modle.bean.WSTaskMainInfo;
import workstation.zjyk.workstation.modle.bean.WSWorkStationTask;
import workstation.zjyk.workstation.modle.bean.WSWorkStationTrayTaskMainInfoVo;
import workstation.zjyk.workstation.ui.adapter.WSAdapterTaskReciver;
import workstation.zjyk.workstation.util.dialog.callback.WSDialogCallBackTwo;

/**
 * 已接收托盘
 * Created by zjgz on 2018/3/26.
 */

public class WSReciveredTrayDialog extends WSCommonDialog {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.recycyle)
    RecyclerView recycleviewHistory;
    String title;
    WSDialogCallBackTwo dialogCallBackTwo;
    @BindView(R.id.btn_cancel)
    Button btnCancel;
    @BindView(R.id.btn_sure)
    Button btnSure;
    private WSAdapterTaskReciver mAdapterTaskReciver;
    private WSWorkStationTask taskVo;

    public WSReciveredTrayDialog(Context context, String title, WSDialogCallBackTwo dialogCallBackTwo) {
        super(context);
        getWindow().setBackgroundDrawable(new ColorDrawable(getContext().getResources().getColor(R.color.transparent)));
        this.title = title;
        this.dialogCallBackTwo = dialogCallBackTwo;
        initDialogView(context);
    }

    public WSReciveredTrayDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected WSReciveredTrayDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    private void initDialogView(Context context) {
        setContentView(R.layout.dialog_recivered_tray);
        ButterKnife.bind(this);
        tvTitle.setText(title);
        mAdapterTaskReciver = new WSAdapterTaskReciver();
        recycleviewHistory.setLayoutManager(new LinearLayoutManager(getContext()));
        recycleviewHistory.setAdapter(mAdapterTaskReciver);

    }

    List<WSTaskDetailItemBean> datas = new ArrayList<>();

    public void setData(WSWorkStationTrayTaskMainInfoVo wsWorkStationTrayTaskMainInfoVo) {
        if (wsWorkStationTrayTaskMainInfoVo != null) {
            WSTaskMainInfo info = wsWorkStationTrayTaskMainInfoVo.getTaskMainVo();
            taskVo = wsWorkStationTrayTaskMainInfoVo.getTask();

            String productNumber = info.getProductNumber();//生产序号
            addData("生产序号: ", productNumber);
            String productModelTypeName = info.getProductModelTypeName();//产品型号
            addData("产品型号: ", productModelTypeName);
            String procedureName = info.getProcedureName();//工序名称
            addData("工序名称: ", procedureName);
            String serialNumber = info.getSerialNumber();//订单生产编号
            addData("订单生产编号: ", serialNumber);
            String productModel = info.getProductModel();//产品货号
            addData("产品货号: ", productModel);
            String productStartTime = info.getProductStartTime();//投产时间
            addData("投产时间: ", productStartTime);
            String deliverTime = info.getDeliverTime();//交货时间
            addData("交货时间: ", deliverTime);


        }
        if (mAdapterTaskReciver != null) {
            mAdapterTaskReciver.setNewData(datas);
        }
    }

    private void addData(String name, String detail) {
        if (!TextUtils.isEmpty(detail)) {
            WSTaskDetailItemBean bean = new WSTaskDetailItemBean();
            bean.setName(name);
            bean.setDetail(detail);
            datas.add(bean);
        }
    }


    @OnClick({R.id.btn_cancel, R.id.btn_sure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_cancel:
                dialogCallBackTwo.OnNegativeClick();
                dismiss();
                break;
            case R.id.btn_sure:
                dialogCallBackTwo.OnPositiveClick(taskVo);
                dismiss();
                break;
        }
    }
}
