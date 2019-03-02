package workstation.zjyk.workstation.util.dialog;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.ethank.mylibrary.resourcelibrary.keyboard.KeyboardUtils;
import cn.com.ethank.mylibrary.resourcelibrary.toast.ToastUtil;
import workstation.zjyk.workstation.R;
import workstation.zjyk.workstation.modle.bean.WSMaintainReason;
import workstation.zjyk.workstation.ui.adapter.WSAdapterRepairReason;
import workstation.zjyk.workstation.util.dialog.callback.WSDialogCallBackTwo;

/**
 * Created by zjgz on 2017/10/31.
 */

public class WSRepairReasonDialog extends WSCommonDialog {
    @BindView(R.id.btn_cancel)
    Button btnCancel;
    @BindView(R.id.btn_sure)
    Button btnSure;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    String title;
    WSDialogCallBackTwo dialogCallBackTwo;
    @BindView(R.id.edit_repair_reason)
    EditText editRepairReason;
    @BindView(R.id.recycyle)
    RecyclerView recycyle;
    private WSAdapterRepairReason mAdapterRepairReason;


    public WSRepairReasonDialog(Context context, String title, WSDialogCallBackTwo dialogCallBackTwo) {
        super(context, R.style.CommonDialog);
        this.title = title;
        this.dialogCallBackTwo = dialogCallBackTwo;
        initDialogView();
    }

    public WSRepairReasonDialog(Context context, int themeResId) {
        super(context, themeResId);
        initDialogView();
    }

    protected WSRepairReasonDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initDialogView();
    }


    private void initDialogView() {
        setContentView(R.layout.dialog_repair_reason);
        ButterKnife.bind(this);
        tvTitle.setText(title);
        mAdapterRepairReason = new WSAdapterRepairReason();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 7, LinearLayoutManager.HORIZONTAL, false);
        recycyle.setLayoutManager(gridLayoutManager);
//        recycyle.setLayoutManager(new LinearLayoutManager(getContext()));
        recycyle.setAdapter(mAdapterRepairReason);
        editRepairReason.setTag(HANDLE_AND_SCAN_TAG);
        editRepairReason.setVisibility(View.GONE);
//        KeyboardUtils.show(editRepairReason);
        mAdapterRepairReason.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                WSMaintainReason reason = (WSMaintainReason) adapter.getData().get(position);
                reason.setSelect(!reason.isSelect());
                adapter.notifyItemChanged(position);
            }
        });
    }


    @OnClick({R.id.btn_cancel, R.id.btn_sure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_cancel:
                dialogCallBackTwo.OnNegativeClick();
//                KeyboardUtils.hideKeyboardOnly(editRepairReason);
                dismiss();
                break;
            case R.id.btn_sure:
                List<WSMaintainReason> selectDatas = getSelectDatas();
                dialogCallBackTwo.OnPositiveClick(selectDatas);
                dismiss();
//                if (selectDatas != null && selectDatas.size() > 0) {
//                } else {
//                    ToastUtil.showInfoCenterShort("请选择维修类型");
//                }
//                String reason = editRepairReason.getText().toString();

//                KeyboardUtils.hideKeyboardOnly(editRepairReason);

                break;
        }
    }

    private List<WSMaintainReason> getSelectDatas() {
        List<WSMaintainReason> newList = new ArrayList<>();
        List<WSMaintainReason> data = mAdapterRepairReason.getData();
        for (int i = 0; i < data.size(); i++) {
            WSMaintainReason reason = data.get(i);
            if (reason.isSelect()) {
                newList.add(reason);
            }
        }
        return newList;
    }

    public void setData(List<WSMaintainReason> reasonList) {
        mAdapterRepairReason.setNewData(reasonList);
    }


    public void showThis() {
        KeyboardUtils.show(editRepairReason);
        show();
    }


}
