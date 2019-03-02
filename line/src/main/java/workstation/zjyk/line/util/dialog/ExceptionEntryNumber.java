package workstation.zjyk.line.util.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import cn.com.ethank.mylibrary.resourcelibrary.keyboard.KeyboardUtils;
import cn.com.ethank.mylibrary.resourcelibrary.utils.NumberUtils;
import cn.com.ethank.mylibrary.resourcelibrary.utils.edit.EditInputFilter;
import workstation.zjyk.line.R;
import workstation.zjyk.line.modle.bean.ExceptionDetailBean;
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

public class ExceptionEntryNumber extends CommonDialog {
    @BindView(R.id.edit_number)
    EditText editNumber;
    @BindView(R.id.btn_cancel)
    Button btnCancel;
    @BindView(R.id.btn_sure)
    Button btnSure;
    DialogCallBackTwo dialogCallBack;
    ExceptionDetailBean bean;
    @BindView(R.id.tv_title)
    TextView tvTitle;

    public ExceptionEntryNumber(Context context) {
        super(context, R.style.CommonDialog);
        initDialogView();
    }

    public ExceptionEntryNumber(Context context, int themeResId) {
        super(context, themeResId);
        initDialogView();
    }

    protected ExceptionEntryNumber(Context context, boolean cancelable, DialogInterface.OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initDialogView();
    }

    public void setBeanAndDialogCallBack(ExceptionDetailBean bean, DialogCallBackTwo dialogCallBack) {
        this.dialogCallBack = dialogCallBack;
        this.bean = bean;
//        InputFilter[] filters = {new EditInputFilter(bean.getCount())};
//        editNumber.setFilters(filters);
    }

    private void initDialogView() {
        setContentView(R.layout.dialog_entry_number);
        ButterKnife.bind(this);
        tvTitle.setText("请输入异常输出数量");
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
                    bean.setExceptionOutCount(entryNumber);
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
//        if("0".equals(str)){
//            return true;
//        }
//        Pattern pattern = Pattern.compile("([1-9]\\d*\\.?\\d*)|(0\\.\\d*[1-9])");
//        Matcher isNum = pattern.matcher(str);
//        if (!isNum.matches()) {
//            return false;
//        }
//        return true;
    }

    private boolean check(double entryNumber) {
        if (bean != null) {
            double count = bean.getCount();
            if (entryNumber <= count) {
                return true;
            } else {
                ToastUtil.showCenterShort("异常输出数量不能超出剩余总数", true);
            }
        }
        return false;
    }

}
