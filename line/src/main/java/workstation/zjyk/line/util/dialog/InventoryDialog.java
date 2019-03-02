package workstation.zjyk.line.util.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import workstation.zjyk.line.R;
import workstation.zjyk.line.modle.bean.LineMaterielStorageManagerVo;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.ethank.mylibrary.resourcelibrary.toast.ToastUtil;

/**
 * 库存管理修改
 * Created by zjgz on 2017/11/1.
 */

public class InventoryDialog extends CommonDialog {


    @BindView(R.id.tv_materail_number)
    TextView tvMaterailNumber;
    @BindView(R.id.tv_materail_name)
    TextView tvMaterailName;
    @BindView(R.id.tv_count)
    TextView tvCount;
    @BindView(R.id.btn_sure)
    Button btnSure;
    @BindView(R.id.btn_cancle)
    Button btnCancle;
    LineMaterielStorageManagerVo lineMaterielStorageManagerVo;
    InventoryDialogCallback inventoryDialogCallback;
    @BindView(R.id.ed_count)
    EditText edCount;

    public InventoryDialog(Context context, LineMaterielStorageManagerVo lineMaterielStorageManagerVo, InventoryDialogCallback inventoryDialogCallback) {
        super(context, R.style.CommonDialog);
        this.lineMaterielStorageManagerVo = lineMaterielStorageManagerVo;
        this.inventoryDialogCallback = inventoryDialogCallback;
        initDialogView(context);

    }

    public InventoryDialog(Context context, int themeResId) {
        super(context, themeResId);
        initDialogView(context);
    }

    protected InventoryDialog(Context context, boolean cancelable, DialogInterface.OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initDialogView(context);
    }

    private void initDialogView(Context context) {
        setContentView(R.layout.dialog_inventory);
        ButterKnife.bind(this);
        tvMaterailNumber.setText("物料编号: " + lineMaterielStorageManagerVo.getMaterielId());
        tvMaterailName.setText("物料名称: " + lineMaterielStorageManagerVo.getName());
        tvCount.setText("数量: ");
        edCount.setText(lineMaterielStorageManagerVo.getCount() + "");
        btnCancle.setText("取消");
        btnSure.setText("确定");
    }


    @OnClick({R.id.btn_sure, R.id.btn_cancle})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_sure:
                String str = edCount.getText().toString();
                int entryNumber = Integer.parseInt(str);
//                if (!check(entryNumber)) {
//                    return;
//                }
                lineMaterielStorageManagerVo.setModifyCount(entryNumber);
                inventoryDialogCallback.save(lineMaterielStorageManagerVo);
                dismiss();
                break;
            case R.id.btn_cancle:
                inventoryDialogCallback.cancle();
                dismiss();
                break;
        }
    }

    public interface InventoryDialogCallback {
        void save(LineMaterielStorageManagerVo lineMaterielStorageManagerVo);

        void cancle();
    }

    private boolean check(int entryNumber) {
        if (lineMaterielStorageManagerVo != null) {
            int count = lineMaterielStorageManagerVo.getCount();
            if (entryNumber <= count) {
                return true;
            } else {
                ToastUtil.showCenterShort("入仓数量不能超出包内剩余总数",true);
            }
        }
        return false;
    }
}
