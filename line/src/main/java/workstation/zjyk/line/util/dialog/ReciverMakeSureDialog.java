package workstation.zjyk.line.util.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import workstation.zjyk.line.R;
import workstation.zjyk.line.modle.bean.GoodsBean;
import workstation.zjyk.line.modle.bean.MessageBean;
import workstation.zjyk.line.ui.adapter.AdapterReciverMakeSure;
import workstation.zjyk.line.ui.present.ReciverMakeSurepresent;
import workstation.zjyk.line.util.dialog.callback.DialogCallbackMakeSure;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zjgz on 2017/11/1.
 */

public class ReciverMakeSureDialog extends CommonDialog implements DialogInterface.OnDismissListener {
    @BindView(R.id.tv_send_good_number)
    TextView tvSendGoodNumber;
    @BindView(R.id.recycyleview)
    RecyclerView recycyleview;
    @BindView(R.id.tv_reciver_and_makesure)
    TextView tvReciverAndMakesure;
    @BindView(R.id.tv_reciver_makesure)
    TextView tvReciverMakesure;
    @BindView(R.id.tv_cancle)
    TextView tvCancle;
    private ReciverMakeSurepresent reciverMakeSurepresent;
    private AdapterReciverMakeSure mAdapterReciverMakeSure;
    private List<GoodsBean> data;
    Context context;
    DialogCallbackMakeSure callbackMakeSure;

    public ReciverMakeSureDialog(Context context) {
        super(context, R.style.CommonDialog);
        initDialogView(context);
    }

    public ReciverMakeSureDialog(Context context, DialogCallbackMakeSure callbackMakeSure) {
        super(context, R.style.CommonDialog);
        this.callbackMakeSure = callbackMakeSure;
        initDialogView(context);
    }

    public ReciverMakeSureDialog(Context context, int themeResId) {
        super(context, themeResId);
        initDialogView(context);
    }

    protected ReciverMakeSureDialog(Context context, boolean cancelable, DialogInterface.OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initDialogView(context);

    }

    private void initDialogView(Context context) {
        this.context = context;
        setContentView(R.layout.activity_reciver_make_sure);
        ButterKnife.bind(this);
//        reciverMakeSurepresent = new ReciverMakeSurepresent();
        EventBus.getDefault().register(this);
        initView();
    }

    private void initView() {
        mAdapterReciverMakeSure = new AdapterReciverMakeSure("");
        recycyleview.setLayoutManager(new LinearLayoutManager(context));
        recycyleview.setAdapter(mAdapterReciverMakeSure);
        setOnDismissListener(this);
    }

    public void setData(List<GoodsBean> datas) {
        this.data = datas;
        if (data != null && data.size() > 0) {
            GoodsBean bean = data.get(0);
            tvSendGoodNumber.setText("仓库发料条码:" + bean.getBarCode());
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageBean messageBean) {
        switch (messageBean.getCode()) {
            case 0:
                setReciverAndFeedResult();
                break;
        }
    }


    @OnClick({R.id.tv_reciver_and_makesure, R.id.tv_reciver_makesure, R.id.tv_cancle})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.tv_reciver_and_makesure:
                setReciverAndFeedResult();
                break;
            case R.id.tv_reciver_makesure:
                setReciverResult();
                break;
            case R.id.tv_cancle:
                dismiss();
                break;

        }
    }

    private void setReciverAndFeedResult() {
//        Intent intent = new Intent();
//        Bundle bundle = new Bundle();
//        bundle.putParcelableArrayList(Constants.RESULT_DATA, data);
//        bundle.putInt(Constants.RESULT_KEY, Constants.RECIVER_AND_FEED_FRAGMENT);
//        intent.putExtras(bundle);
//        setResult(0, intent);
//        finish();
        dismiss();
    }

    private void setReciverResult() {
//        Intent intent = new Intent();
//        Bundle bundle = new Bundle();
//        bundle.putParcelableArrayList(Constants.RESULT_DATA, data);
//        bundle.putInt(Constants.RESULT_KEY, Constants.RECIVER_MAKE_SURE);
//        intent.putExtras(bundle);
//        setResult(0, intent);
//        finish();
        if (callbackMakeSure != null) {
            callbackMakeSure.makeSureReciver(data);
        }
        dismiss();
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        EventBus.getDefault().unregister(this);
    }
}
