package workstation.zjyk.workstation.util.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.ethank.mylibrary.resourcelibrary.common_util.JsonUtil;
import cn.com.ethank.mylibrary.resourcelibrary.keyboard.KeyboardUtils;
import cn.com.ethank.mylibrary.resourcelibrary.toast.ToastUtil;
import workstation.zjyk.workstation.R;
import workstation.zjyk.workstation.modle.bean.WSTaskProductCheckTray;
import workstation.zjyk.workstation.modle.bean.WSWorkStationCheckVO;
import workstation.zjyk.workstation.modle.bean.enumpackage.WSTaskTypeEnum;
import workstation.zjyk.workstation.ui.adapter.WSAdapterInspection;
import workstation.zjyk.workstation.ui.manager.WSUserManager;
import workstation.zjyk.workstation.util.dialog.callback.WSDialogCallBackThree;

/**
 * 输出报验
 * Created by zjgz on 2018/3/26.
 */

public class WSOutPutInsoectionDialog extends WSCommonDialog implements DialogInterface.OnDismissListener {
    String title;
    WSDialogCallBackThree dialogCallBackThree;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.recycleview)
    RecyclerView recycleview;
    @BindView(R.id.btn_cancel)
    Button btnCancel;
    @BindView(R.id.btn_sure)
    Button btnSure;
    @BindView(R.id.tv_tray_number)
    TextView tvTrayNumber;
    @BindView(R.id.edit_number)
    EditText editNumber;
    @BindView(R.id.ll_insoection)
    LinearLayout llInsoection;
    @BindView(R.id.ll_insoection_two)
    LinearLayout llInsoectionTwo;
    private WSAdapterInspection mAdapterInspection;
    int selectCount = 0;
    WSTaskTypeEnum wsTaskTypeEnum;
    int predictCount;

    public WSOutPutInsoectionDialog(Context context, String title, WSDialogCallBackThree dialogCallBackTwo) {
        super(context);
        getWindow().setBackgroundDrawable(new ColorDrawable(getContext().getResources().getColor(R.color.transparent)));
        this.title = title;
        this.dialogCallBackThree = dialogCallBackTwo;
        initDialogView(context);
    }

    public WSOutPutInsoectionDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected WSOutPutInsoectionDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    private void initDialogView(Context context) {
        setContentView(R.layout.dialog_output_inspection);
        ButterKnife.bind(this);
        tvTrayNumber.setText(getContext().getText(R.string.text_unbind));
        btnSure.setTag(false);
        tvTitle.setText(title);
        setConfirmBg();
        recycleview.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapterInspection = new WSAdapterInspection();
        recycleview.setAdapter(mAdapterInspection);
        mAdapterInspection.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                WSTaskProductCheckTray bean = (WSTaskProductCheckTray) adapter.getData().get(position);
                boolean select = bean.isSelect();
                switch (view.getId()) {
                    case R.id.iv_select_status:
                        bean.setSelect(!select);
                        adapter.notifyItemChanged(position);
                        if (bean.isSelect()) {
                            selectCount++;
                        } else {
                            selectCount--;
                            if (selectCount < 0) {
                                selectCount = 0;
                            }
                        }
                        setConfirmBg();
                        break;
                }
            }
        });

        setOnDismissListener(this);
        editNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KeyboardUtils.show(editNumber);
            }
        });

    }

    @Override
    public boolean dispatchKeyEvent(@NonNull KeyEvent event) {
        View currentFocus = getCurrentFocus();
        int deviceId = event.getDeviceId();
        if (currentFocus != null && currentFocus instanceof EditText && deviceId > 0) {
            editNumber.setFocusable(false);
            editNumber.setFocusableInTouchMode(false);
            editNumber.clearFocus();
            KeyboardUtils.hideKeyboard(editNumber);
        }
        return super.dispatchKeyEvent(event);
    }

    private void setConfirmBg() {
        boolean isClickable = (boolean) btnSure.getTag();
        if (selectCount > 0) {
            if (!isClickable) {
                btnSure.setBackgroundResource(R.drawable.shape_blue);
                btnSure.setTag(true);
            }
        } else {
            if (wsTaskTypeEnum == WSTaskTypeEnum.FQA_RETURN) {
                btnSure.setBackgroundResource(R.drawable.shape_blue);
                btnSure.setTag(true);
            } else {
                btnSure.setBackgroundResource(R.drawable.shape_gray_bg);
                btnSure.setTag(false);
            }
        }
    }

    String taskId;

    public void setData(List<WSTaskProductCheckTray> datas, WSTaskTypeEnum wsTaskTypeEnum, int predictCount, String taskId) {
        this.predictCount = predictCount;
        this.taskId = taskId;
        if (datas != null && datas.size() > 0) {
            mAdapterInspection.setNewData(datas);
        }
        this.wsTaskTypeEnum = wsTaskTypeEnum;
        if (wsTaskTypeEnum != null && wsTaskTypeEnum == WSTaskTypeEnum.FQA_RETURN) {
            llInsoection.setVisibility(View.GONE);
            llInsoectionTwo.setVisibility(View.VISIBLE);
            editNumber.setText(predictCount + "");
            editNumber.setSelection(editNumber.getText().toString().length());
//            KeyboardUtils.setEdittextOnlyHandInput(editNumber);
            KeyboardUtils.show(editNumber);
            setConfirmBg();
        } else {
            llInsoection.setVisibility(View.VISIBLE);
            llInsoectionTwo.setVisibility(View.GONE);
        }
    }

    @OnClick({R.id.btn_cancel, R.id.btn_sure, R.id.iv_close})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_close:
            case R.id.btn_cancel:
                if (dialogCallBackThree != null) {
                    dialogCallBackThree.OnNegativeClick();
                }
                if (editNumber.getVisibility() == View.VISIBLE) {
                    KeyboardUtils.hideKeyboard(editNumber);
                }
                dismiss();
                break;
            case R.id.btn_sure:
                if (dialogCallBackThree != null) {
                    if (check()) {
                        dialogCallBackThree.OnPositiveClick(getSelectData());
                    }
                }
                break;
        }
    }

    private boolean check() {
        if (selectCount > 0 || wsTaskTypeEnum == WSTaskTypeEnum.FQA_RETURN) {
            if (wsTaskTypeEnum == WSTaskTypeEnum.FQA_RETURN) {
                if (TextUtils.isEmpty(editNumber.getText().toString())) {
                    ToastUtil.showInfoCenterShort("输入数量不能为空！");
                    return false;
                } else {
                    int number = Integer.parseInt(editNumber.getText().toString());
                    if (number > predictCount) {
                        ToastUtil.showInfoCenterShort("输入数量不能超过预计输出数！");
                        return false;
                    } else if (number == 0) {
                        ToastUtil.showInfoCenterShort("输入数量不能为0！");
                        return false;
                    }
                }
            }
            if (getContext().getResources().getString(R.string.text_unbind).equals(tvTrayNumber.getText())) {
                ToastUtil.showInfoCenterShort("请绑定报验签！");
                return false;
            }

            return true;

        } else {
            ToastUtil.showInfoCenterShort("请选择报验的托盘！");
            return false;
        }
    }

    private String getSelectData() {
        List<WSTaskProductCheckTray> datas = mAdapterInspection.getData();
        WSWorkStationCheckVO wsWorkStationCheckVO = new WSWorkStationCheckVO();

        wsWorkStationCheckVO.setTrayCode(tvTrayNumber.getText().toString());
        wsWorkStationCheckVO.setOutPersonId(WSUserManager.getInstance().getPersonId());
        if (wsTaskTypeEnum == WSTaskTypeEnum.FQA_RETURN && editNumber.getVisibility() == View.VISIBLE) {
            //二次报验
            wsWorkStationCheckVO.setCount(Integer.parseInt(editNumber.getText().toString()));
            wsWorkStationCheckVO.setWorkStationTaskId(taskId);
        } else {
            //正常报验
            wsWorkStationCheckVO.setWorkStationTaskId(datas.get(0).getTaskId());
            List<String> outRecordList = new ArrayList<>();

            List<String> trayIds = new ArrayList<>();
            wsWorkStationCheckVO.setOutRecordList(outRecordList);
            wsWorkStationCheckVO.setTrayId(trayIds);

            for (int i = 0; i < datas.size(); i++) {
                WSTaskProductCheckTray wsTaskProductCheckTray = datas.get(i);
                if (wsTaskProductCheckTray.isSelect()) {
                    String trayId = wsTaskProductCheckTray.getTrayId();
                    String outId = wsTaskProductCheckTray.getOutId();
                    trayIds.add(trayId);
                    outRecordList.add(outId);
                }
            }
        }
        return JsonUtil.toJson(wsWorkStationCheckVO);
    }

    @Override
    public void onScanResult(String scanResult) {
        super.onScanResult(scanResult);
        if (dialogCallBackThree != null) {
            dialogCallBackThree.checkScanCode(scanResult);
        }
    }

    public void setScanCode(String scanCode) {
        tvTrayNumber.setText(scanCode);
    }

    @Override
    public void onDismiss(DialogInterface dialog) {

        List<WSTaskProductCheckTray> data = mAdapterInspection.getData();
        for (int i = 0; i < data.size(); i++) {
            WSTaskProductCheckTray wsTaskProductCheckTray = data.get(i);
            if (wsTaskProductCheckTray.isSelect()) {
                wsTaskProductCheckTray.setSelect(false);
                mAdapterInspection.notifyItemChanged(wsTaskProductCheckTray.getPosition());
            }
        }
        tvTrayNumber.setText(getContext().getText(R.string.text_unbind));
        selectCount = 0;
        setConfirmBg();
    }
}
