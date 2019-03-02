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
import workstation.zjyk.workstation.modle.bean.WSWorkReportRecordVO;
import workstation.zjyk.workstation.ui.adapter.WSAdapterUserRecordDetail;
import workstation.zjyk.workstation.util.dialog.callback.WSDialogCallBackTwo;

/**
 * Created by zjgz on 2018/3/26.
 */

public class WSUserRecordDetailDialog extends WSCommonDialog {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.recycyle)
    RecyclerView recycleview;
    @BindView(R.id.iv_close)
    ImageView ivClose;
    String title;
    WSDialogCallBackTwo dialogCallBackTwo;
    private WSAdapterUserRecordDetail mAdapterUserRecordDetail;

    public WSUserRecordDetailDialog(Context context, String title, WSDialogCallBackTwo dialogCallBackTwo) {
        super(context);
        getWindow().setBackgroundDrawable(new ColorDrawable(getContext().getResources().getColor(R.color.transparent)));
        this.title = title;
        this.dialogCallBackTwo = dialogCallBackTwo;
        initDialogView(context);
    }

    public WSUserRecordDetailDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected WSUserRecordDetailDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    private void initDialogView(Context context) {
        setContentView(R.layout.dialog_user_record_detail);
        ButterKnife.bind(this);

        mAdapterUserRecordDetail = new WSAdapterUserRecordDetail();
        recycleview.setLayoutManager(new LinearLayoutManager(getContext()));
        recycleview.setAdapter(mAdapterUserRecordDetail);

    }

    public void setData(String title, List<WSWorkReportRecordVO> wsWorkReportRecordVOList) {
        tvTitle.setText(title);
        mAdapterUserRecordDetail.setNewData(wsWorkReportRecordVOList);
    }

    @OnClick(R.id.iv_close)
    public void onViewClicked() {
        dismiss();
    }
}
