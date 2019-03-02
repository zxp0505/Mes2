package workstation.zjyk.workstation.util.dialog;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import workstation.zjyk.workstation.R;
import workstation.zjyk.workstation.modle.bean.WSDailyWorkHistory;
import workstation.zjyk.workstation.ui.adapter.WSAdapterReportWorkHistory;
import workstation.zjyk.workstation.util.dialog.callback.WSDialogCallBackTwo;

/**
 * Created by zjgz on 2018/3/26.
 */

public class WSReportHistoryDialog extends WSCommonDialog {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.recycyle)
    RecyclerView recycleviewHistory;
    @BindView(R.id.iv_close)
    ImageView ivClose;
    String title;
    private WSAdapterReportWorkHistory mAdapterReportWorkHistory;
    WSDialogCallBackTwo dialogCallBackTwo;

    public WSReportHistoryDialog(Context context, String title, WSDialogCallBackTwo dialogCallBackTwo) {
        super(context);
        getWindow().setBackgroundDrawable(new ColorDrawable(getContext().getResources().getColor(R.color.transparent)));
        this.title = title;
        this.dialogCallBackTwo = dialogCallBackTwo;
        initDialogView(context);
    }

    public WSReportHistoryDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected WSReportHistoryDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    private void initDialogView(Context context) {
        setContentView(R.layout.dialog_report_history);
        ButterKnife.bind(this);

        mAdapterReportWorkHistory = new WSAdapterReportWorkHistory();
        recycleviewHistory.setLayoutManager(new LinearLayoutManager(getContext()));
        recycleviewHistory.setAdapter(mAdapterReportWorkHistory);

    }

    public void setData(List<WSDailyWorkHistory> datas) {
        tvTitle.setText("历史报工记录(共" + datas.size() + "条)");
        if (mAdapterReportWorkHistory != null) {
            mAdapterReportWorkHistory.setNewData(datas);
        }
    }

    @OnClick(R.id.iv_close)
    public void onViewClicked() {
        dismiss();
    }
}
