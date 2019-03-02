package workstation.zjyk.line.util.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import cn.com.ethank.mylibrary.resourcelibrary.utils.NumberUtils;
import workstation.zjyk.line.R;
import workstation.zjyk.line.modle.bean.MaterielVo;
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

public class TipDialogEntryNumberTwo extends CommonDialog {
    @BindView(R.id.edit_number)
    EditText editNumber;
    @BindView(R.id.btn_cancel)
    Button btnCancel;
    @BindView(R.id.btn_sure)
    Button btnSure;
    DialogCallBackTwo dialogCallBack;
    MaterielVo bean;
    boolean isBag;//是否是包内
    @BindView(R.id.tv_title)
    TextView tvTitle;

    public TipDialogEntryNumberTwo(Context context) {
        super(context, R.style.CommonDialog);
        initDialogView();
    }

    public TipDialogEntryNumberTwo(Context context, int themeResId) {
        super(context, themeResId);
        initDialogView();
    }

    protected TipDialogEntryNumberTwo(Context context, boolean cancelable, DialogInterface.OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initDialogView();
    }

    public void setBeanAndDialogCallBack(MaterielVo bean, DialogCallBackTwo dialogCallBack) {
        this.dialogCallBack = dialogCallBack;
        this.bean = bean;
        this.isBag = isBag;
    }

    private void initDialogView() {
        setContentView(R.layout.dialog_entry_number);
        ButterKnife.bind(this);
        tvTitle.setText("请输入入仓数量");
        editNumber.requestFocus();

    }

    @OnClick({R.id.btn_cancel, R.id.btn_sure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_cancel:
                dialogCallBack.OnNegativeClick();
                dismiss();
                break;
            case R.id.btn_sure:
                String str = editNumber.getText().toString();
                if (!TextUtils.isEmpty(str)) {
                    if (!isNumeric(str)) {
                        ToastUtil.showCenterShort("请输入数字",true);
                        return;
                    }
                    int entryNumber = Integer.parseInt(str);
                    if (!check(entryNumber)) {
                        return;
                    }
                    bean.setInWareCount(entryNumber);
                    dialogCallBack.OnPositiveClick(bean);
                    dismiss();
                } else {
                    ToastUtil.showCenterShort("不能为空",true);
                }
                break;
        }
    }

    public boolean isNumeric(String str) {
        return NumberUtils.isNumeric(str);
//        Pattern pattern = Pattern.compile("[0-9]*");
//        Matcher isNum = pattern.matcher(str);
//        if (!isNum.matches()) {
//            return false;
//        }
//        return true;
    }

    private boolean check(int entryNumber) {
        if (bean != null) {
            double count = bean.getRemainCount();
            if (entryNumber <= count) {
                return true;
            } else {
                ToastUtil.showCenterShort("入仓数量不能超出包内剩余总数",true);
            }
        }
        return false;
    }

}
