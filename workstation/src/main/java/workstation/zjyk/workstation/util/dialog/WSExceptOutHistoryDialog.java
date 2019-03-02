package workstation.zjyk.workstation.util.dialog;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import workstation.zjyk.workstation.R;
import workstation.zjyk.workstation.modle.bean.WSDailyWorkHistory;
import workstation.zjyk.workstation.modle.bean.WSExceptionOutRecordVo;
import workstation.zjyk.workstation.ui.adapter.WSAdapteExceptOutHistory;
import workstation.zjyk.workstation.util.dialog.callback.WSDialogCallBackThree;
import workstation.zjyk.workstation.util.dialog.callback.WSDialogCallBackTwo;

/**
 * 异常输出历史记录
 * Created by zjgz on 2018/3/26.
 */

public class WSExceptOutHistoryDialog extends WSCommonDialog {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.recycyle)
    RecyclerView recycleviewHistory;
    @BindView(R.id.iv_close)
    ImageView ivClose;
    String title;
    WSDialogCallBackThree dialogCallBack;
    private WSAdapteExceptOutHistory mAdapterExceptOutHistory;

    public WSExceptOutHistoryDialog(Context context, String title, WSDialogCallBackThree dialogCallBackTwo) {
        super(context);
        getWindow().setBackgroundDrawable(new ColorDrawable(getContext().getResources().getColor(R.color.transparent)));
        this.title = title;
        this.dialogCallBack = dialogCallBackTwo;
        initDialogView(context);
    }

    public WSExceptOutHistoryDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected WSExceptOutHistoryDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    private void initDialogView(Context context) {
        setContentView(R.layout.dialog_except_out_history);
        ButterKnife.bind(this);
        tvTitle.setText(title);
        mAdapterExceptOutHistory = new WSAdapteExceptOutHistory();
        recycleviewHistory.setLayoutManager(new LinearLayoutManager(getContext()));
        recycleviewHistory.setAdapter(mAdapterExceptOutHistory);
        mAdapterExceptOutHistory.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                List<WSExceptionOutRecordVo> data = adapter.getData();
                WSExceptionOutRecordVo wsExceptionOutRecordVo = data.get(position);
                switch (view.getId()) {
                    case R.id.tv_print:
                        dialogCallBack.checkScanCode(wsExceptionOutRecordVo.getRecordId());
                        break;
                }
            }
        });

    }

    public void setData(List<WSExceptionOutRecordVo> datas) {
        if (mAdapterExceptOutHistory != null) {
            mAdapterExceptOutHistory.setNewData(datas);
        }
    }

    @OnClick(R.id.iv_close)
    public void onViewClicked() {
        dismiss();
    }
}
