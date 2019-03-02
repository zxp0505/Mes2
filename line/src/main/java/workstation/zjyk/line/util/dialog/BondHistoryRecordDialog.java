package workstation.zjyk.line.util.dialog;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import workstation.zjyk.line.R;
import workstation.zjyk.line.modle.bean.LineDistributeHistoryVO;
import workstation.zjyk.line.ui.adapter.AdapterBondHistory;
import workstation.zjyk.line.util.DialogCallBackTwo;

/**
 * Created by zjgz on 2017/11/1.
 */

public class BondHistoryRecordDialog extends CommonDialog {
    Context context;
    DialogCallBackTwo callbackMakeSure;
    @BindView(R.id.recycyleview)
    RecyclerView recycyleview;
    @BindView(R.id.iv_close)
    ImageView ivClose;
    private AdapterBondHistory mAdapterBondHistory;

    public BondHistoryRecordDialog(Context context) {
        super(context, R.style.CommonDialog);
        initDialogView(context);
    }

    public BondHistoryRecordDialog(Context context, DialogCallBackTwo callbackMakeSure) {
        super(context, R.style.CommonDialog);
        this.callbackMakeSure = callbackMakeSure;
        initDialogView(context);
    }

    public BondHistoryRecordDialog(Context context, int themeResId) {
        super(context, themeResId);
        initDialogView(context);
    }

    protected BondHistoryRecordDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initDialogView(context);

    }

    private void initDialogView(Context context) {
        this.context = context;
        setContentView(R.layout.dialog_bond_history_record);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mAdapterBondHistory = new AdapterBondHistory();
        recycyleview.setLayoutManager(new LinearLayoutManager(context));
        recycyleview.setAdapter(mAdapterBondHistory);
        mAdapterBondHistory.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                LineDistributeHistoryVO data = (LineDistributeHistoryVO) adapter.getData().get(position);
                switch (view.getId()) {
                    case R.id.tv_print:
                        //打印
                        callbackMakeSure.OnPositiveClick(data);
                        break;
                }
            }
        });
    }

    public void setData(List<LineDistributeHistoryVO> datas) {
        mAdapterBondHistory.setNewData(datas);
    }


    @OnClick({R.id.iv_close})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_close:
                dismiss();
                break;
        }
    }
}
