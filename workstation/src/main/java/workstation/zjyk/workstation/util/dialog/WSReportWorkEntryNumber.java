package workstation.zjyk.workstation.util.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.ethank.mylibrary.resourcelibrary.keyboard.KeyboardUtils;
import cn.com.ethank.mylibrary.resourcelibrary.toast.ToastUtil;
import cn.com.ethank.mylibrary.resourcelibrary.utils.NumberUtils;
import workstation.zjyk.workstation.R;
import workstation.zjyk.workstation.modle.bean.WSInputWipInfo;
import workstation.zjyk.workstation.modle.bean.WSMaterial;
import workstation.zjyk.workstation.modle.bean.WSMyReportWorkBean;
import workstation.zjyk.workstation.modle.bean.WSProcedureStep;
import workstation.zjyk.workstation.modle.bean.WSWip;
import workstation.zjyk.workstation.util.dialog.callback.WSDialogCallBackTwo;

/**
 * Created by zjgz on 2017/10/31.
 */

public class WSReportWorkEntryNumber<T> extends WSCommonDialog {
    @BindView(R.id.edit_number)
    EditText editNumber;
    @BindView(R.id.btn_cancel)
    Button btnCancel;
    @BindView(R.id.btn_sure)
    Button btnSure;
    WSDialogCallBackTwo dialogCallBack;
    T bean;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    String title;

    public WSReportWorkEntryNumber(Context context) {
        super(context, R.style.CommonDialog);
        initDialogView();
    }

    public WSReportWorkEntryNumber(Context context, int themeResId) {
        super(context, themeResId);
        initDialogView();
    }

    protected WSReportWorkEntryNumber(Context context, boolean cancelable, DialogInterface.OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initDialogView();
    }

    public void setBeanAndDialogCallBack(T bean, String title, WSDialogCallBackTwo dialogCallBack) {
        this.dialogCallBack = dialogCallBack;
        this.bean = bean;
        this.title = title;
        tvTitle.setText(title);
//        InputFilter[] filters = {new EditInputFilter(bean.getCount())};
//        editNumber.setFilters(filters);
    }

    private void initDialogView() {
        setContentView(R.layout.dialog_entry_number);
        ButterKnife.bind(this);
//        tvTitle.setText(title);
        editNumber.setTag(MUST_HANDLE_TAG);
        KeyboardUtils.show(editNumber);
    }


    @OnClick({R.id.btn_cancel, R.id.btn_sure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_cancel:
                dialogCallBack.OnNegativeClick();
                KeyboardUtils.hideKeyboardOnly(editNumber);
                dismiss();
                break;
            case R.id.btn_sure:
                String str = editNumber.getText().toString();
                if (!TextUtils.isEmpty(str)) {
                    if (!isNumeric(str)) {
                        ToastUtil.showCenterShort("请输入数字", true);
                        return;
                    }
                    double entryNumber = Double.parseDouble(str);
                    if (!check(entryNumber)) {
                        return;
                    }
                    if (bean instanceof WSProcedureStep) {
                        WSProcedureStep wsMyReportWorkBean = (WSProcedureStep) bean;
                        wsMyReportWorkBean.setTrueCount(entryNumber);
                    } else if (bean instanceof WSMaterial) {
                        WSMaterial wsMaterial = (WSMaterial) bean;
                        wsMaterial.setTrueCount(entryNumber);
                    } else if (bean instanceof WSWip) {
                        WSWip wsWip = (WSWip) bean;
                        wsWip.setTrueCount(entryNumber);
                    } else if (bean instanceof WSInputWipInfo) {
                        WSInputWipInfo wsInputWipInfo = (WSInputWipInfo) bean;
                        wsInputWipInfo.setTrueCount(entryNumber);
                    }

                    dialogCallBack.OnPositiveClick(bean);
                    KeyboardUtils.hideKeyboardOnly(editNumber);
                    dismiss();
                } else {
                    ToastUtil.showCenterShort("不能为空", true);
                }
                break;
        }
    }

    public boolean isNumeric(String str) {
        return NumberUtils.isNumeric(str);
    }

    private boolean check(double entryNumber) {
        if (bean != null) {
            double count = 0.0;
            if (bean instanceof WSProcedureStep) {
                WSProcedureStep wsMyReportWorkBean = (WSProcedureStep) bean;
                count = wsMyReportWorkBean.getCount();
            } else if (bean instanceof WSMaterial) {
                WSMaterial wsMaterial = (WSMaterial) bean;
                count = wsMaterial.getCount();
            } else if (bean instanceof WSWip) {
                WSWip wsWip = (WSWip) bean;
                count = wsWip.getCount();
            } else if (bean instanceof WSInputWipInfo) {
                WSInputWipInfo wsInputWipInfo = (WSInputWipInfo) bean;
                count = wsInputWipInfo.getRemainCount();
            }
            if (entryNumber <= count) {
                return true;
            } else {
                ToastUtil.showCenterShort("输出数量不能超出剩余总数", true);
            }
        }


        return false;
    }

}
