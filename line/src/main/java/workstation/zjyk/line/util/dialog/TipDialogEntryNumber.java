package workstation.zjyk.line.util.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import cn.com.ethank.mylibrary.resourcelibrary.keyboard.KeyboardUtils;
import cn.com.ethank.mylibrary.resourcelibrary.utils.NumberUtils;
import cn.com.ethank.mylibrary.resourcelibrary.utils.edit.EditInputFilter;
import workstation.zjyk.line.R;
import workstation.zjyk.line.modle.bean.WorkStationDistributeMateriel;
import workstation.zjyk.line.ui.pop.basePop.InputMethodUtils;
import workstation.zjyk.line.util.DialogCallBackTwo;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.ethank.mylibrary.resourcelibrary.toast.ToastUtil;

/**
 * Created by zjgz on 2017/10/31.
 */

public class TipDialogEntryNumber extends CommonDialog {
    @BindView(R.id.edit_number)
    EditText editNumber;
    @BindView(R.id.btn_cancel)
    Button btnCancel;
    @BindView(R.id.btn_sure)
    Button btnSure;
    DialogCallBackTwo dialogCallBack;
    WorkStationDistributeMateriel bean;
    boolean isBag;//是否是包内

    public TipDialogEntryNumber(Context context) {
        super(context, R.style.CommonDialog);
        initDialogView();
    }

    public TipDialogEntryNumber(Context context, int themeResId) {
        super(context, themeResId);
        initDialogView();
    }

    protected TipDialogEntryNumber(Context context, boolean cancelable, DialogInterface.OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initDialogView();
    }

    public void setBeanAndDialogCallBack(WorkStationDistributeMateriel bean, DialogCallBackTwo dialogCallBack) {
        this.dialogCallBack = dialogCallBack;
        this.bean = bean;
        this.isBag = isBag;
//        InputFilter[] filters = {new EditInputFilter(bean.getWrapCount())};
//        editNumber.setFilters(filters);
    }

    private void initDialogView() {
        setContentView(R.layout.dialog_entry_number);
        ButterKnife.bind(this);
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
                    if (bean.isClickBag()) {
                        bean.setWrapTrueCount(entryNumber);
//                        bean.setWrapCount(bean.getWrapCount() - entryNumber);
                    } else {
                        bean.setStorageTrueCount(entryNumber);
//                        bean.setStorageCount(bean.getStorageCount() - entryNumber);
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
//        if("0".equals(str)){
//            return true;
//        }
//        Pattern pattern = Pattern.compile("([1-9]\\d*\\.?\\d*)|(0\\.\\d*[1-9])");
//        Matcher isNum = pattern.matcher(str);
//        if (!isNum.matches()) {
//            return false;
//        }
//        return true;
        return NumberUtils.isNumeric(str);
    }

    private boolean check(double entryNumber) {
        if (bean != null) {
            double materailSurplusBag = bean.getWrapCount();
            double materailSurplusWarehouse = bean.getStorageCount();
            if (bean.isClickBag() && entryNumber <= materailSurplusBag) {
                return true;
            } else {
                ToastUtil.showCenterShort("分配数量不能超出包内剩余总数", true);
            }
            if (!bean.isClickBag() && entryNumber <= materailSurplusWarehouse) {
                return true;
            } else {
                ToastUtil.showCenterShort("分配数量不能超出仓内剩余总数", true);
            }
        }
        return false;
    }

}
