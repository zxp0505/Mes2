package workstation.zjyk.line.util.dialog;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.ethank.mylibrary.resourcelibrary.toast.ToastUtil;
import workstation.zjyk.line.R;
import workstation.zjyk.line.modle.bean.BondBean;
import workstation.zjyk.line.modle.bean.OrderDistributeNeedMaterielVo;
import workstation.zjyk.line.modle.bean.YesOrNoEnum;
import workstation.zjyk.line.util.DialogCallBackTwo;

/**
 * Created by zjgz on 2017/11/1.
 */

public class RemindDialog extends CommonDialog {
    @BindView(R.id.tv_dialog_title)
    TextView tvDialogTitle;
    @BindView(R.id.tv_cancle)
    TextView tvCancle;
    DialogCallBackTwo callBack;
    @BindView(R.id.tv_sure)
    TextView tvSure;
    @BindView(R.id.checkbox)
    CheckBox checkbox;
    @BindView(R.id.iv_tray)
    ImageView ivTray;
    @BindView(R.id.tv_tray_number)
    TextView tvTrayNumber;

    public RemindDialog(Context context, DialogCallBackTwo callBack) {
        super(context, R.style.CommonDialog);
        this.callBack = callBack;
        initDialogView(context);
    }

    public RemindDialog(Context context, int themeResId) {
        super(context, themeResId);
        initDialogView(context);
    }

    protected RemindDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initDialogView(context);
    }

    private void initDialogView(Context context) {
        setContentView(R.layout.dialog_remind);
        ButterKnife.bind(this);
    }

    boolean isCheck = false;
    YesOrNoEnum yesOrNoEnum;

    public void setTrayCode(String trayCode) {
        if (!TextUtils.isEmpty(trayCode)) {
            tvTrayNumber.setText(trayCode);
        }

        // 1.第一次分料 2.未绑定 3.显示checkbox
        if (yesOrNoEnum != null && yesOrNoEnum == YesOrNoEnum.NO) {
            if (getContext().getResources().getString(R.string.text_unbind).equals(trayCode)) {
                checkbox.setVisibility(View.VISIBLE);
                tvSure.setText("打印");
            } else {
                checkbox.setVisibility(View.GONE);
                tvSure.setText("发料");
            }
        }
    }

    OrderDistributeNeedMaterielVo orderDistributeNeedMaterielVo;
    boolean isHistoryDialog;

    public void setData(String data, OrderDistributeNeedMaterielVo orderDistributeNeedMaterielVo, boolean isHistoryDialog) {
        this.orderDistributeNeedMaterielVo = orderDistributeNeedMaterielVo;
        this.isHistoryDialog = isHistoryDialog;
        if (orderDistributeNeedMaterielVo != null) {
            this.yesOrNoEnum = orderDistributeNeedMaterielVo.getDistributeStruts();
        }

        if (yesOrNoEnum != null && yesOrNoEnum == YesOrNoEnum.YES) {
            checkbox.setVisibility(View.GONE);
            tvDialogTitle.setVisibility(View.VISIBLE);
            tvDialogTitle.setText("请扫描托盘");
            tvTrayNumber.setText(getContext().getString(R.string.text_unbind));
            ivTray.setVisibility(View.VISIBLE);
            tvTrayNumber.setVisibility(View.VISIBLE);
            tvSure.setText("发料");
        } else {
            isCheck = false;
            tvSure.setText("打印");
            checkbox.setVisibility(View.VISIBLE);
            //历史记录 不显示绑盘
            if (!isHistoryDialog) {
                tvDialogTitle.setVisibility(View.VISIBLE);
                ivTray.setVisibility(View.VISIBLE);
                tvTrayNumber.setVisibility(View.VISIBLE);
                tvDialogTitle.setText("请扫描托盘");
                tvTrayNumber.setText(getContext().getString(R.string.text_unbind));
            } else {
                tvDialogTitle.setVisibility(View.GONE);
                ivTray.setVisibility(View.GONE);
                tvTrayNumber.setVisibility(View.GONE);
            }
            checkbox.setText(data);
            checkbox.setChecked(false);
            checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    isCheck = isChecked;
                }
            });
        }

    }

    @OnClick({R.id.tv_cancle, R.id.tv_sure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_sure:
                //3种情况 1.第一次发料 （1、绑盘 2.不绑盘 打印） 2.非第一次发料 （只能绑盘--type =3 ） 3.历史记录 只能打印
                BondBean bondBean = new BondBean();
                if (yesOrNoEnum == YesOrNoEnum.YES) {
                    if (getContext().getString(R.string.text_unbind).equals(tvTrayNumber.getText().toString().trim())) {
                        ToastUtil.showInfoCenterShort("请先绑定托盘");
                        return;
                    } else {
                        bondBean.setType(3);
                        bondBean.setTrayCode(tvTrayNumber.getText().toString().trim());
                    }

                } else {
                    bondBean.setType(1);
                    bondBean.setTrayCode(tvTrayNumber.getText().toString().trim());
                    bondBean.setCheck(isCheck);
                }
                callBack.OnPositiveClick(bondBean);
                break;
            case R.id.tv_cancle:
                callBack.OnNegativeClick();
                break;
        }
        dismiss();
    }

    @Override
    public void onScanResult(String scanResult) {
        if (!TextUtils.isEmpty(scanResult)) {
            BondBean bondBean = new BondBean();
            bondBean.setType(2);
            bondBean.setTrayCode(scanResult);
            callBack.OnPositiveClick(bondBean);
        }
    }
}
