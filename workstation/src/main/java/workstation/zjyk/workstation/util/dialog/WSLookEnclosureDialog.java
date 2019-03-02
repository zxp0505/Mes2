package workstation.zjyk.workstation.util.dialog;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
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
import workstation.zjyk.workstation.modle.bean.WSAccessoryAddress;
import workstation.zjyk.workstation.modle.bean.WSTrayVo;
import workstation.zjyk.workstation.ui.adapter.WSAdapterLookEnclosure;
import workstation.zjyk.workstation.ui.adapter.WSAdapterLookTrayListVp;
import workstation.zjyk.workstation.ui.pop.WSEnclosureCallBack;

/**
 * 查看附件
 */

public class WSLookEnclosureDialog extends WSCommonDialog {


    @BindView(R.id.recycleview)
    RecyclerView recycleview;
    String title;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_close)
    ImageView ivClose;
    private WSAdapterLookEnclosure mAdapterLookEnclosure;
    List<WSAccessoryAddress> datas;
    WSEnclosureCallBack enclosureCallBack;

    public WSLookEnclosureDialog(Context context, String title, WSEnclosureCallBack enclosureCallBack) {
        super(context, R.style.CommonDialog);
        this.title = title;
        this.enclosureCallBack = enclosureCallBack;
        initDialogView(context);

    }


    public WSLookEnclosureDialog(Context context, int themeResId) {
        super(context, themeResId);
        initDialogView(context);
    }

    protected WSLookEnclosureDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initDialogView(context);
    }

    private void initDialogView(Context context) {
        setContentView(R.layout.dialog_look_enclosure);
        ButterKnife.bind(this);
        mAdapterLookEnclosure = new WSAdapterLookEnclosure();
        recycleview.setLayoutManager(new GridLayoutManager(getContext(), 3));
        recycleview.setAdapter(mAdapterLookEnclosure);
        mAdapterLookEnclosure.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                WSAccessoryAddress bean = (WSAccessoryAddress) adapter.getData().get(position);
                switch (view.getId()) {
                    case R.id.ll_root:
                        enclosureCallBack.selectIten(bean);
                        break;
                }
            }
        });

    }

    public void setData(List<WSAccessoryAddress> datas) {
        this.datas = datas;
        refreshData();
    }

    private void refreshData() {
        tvTitle.setText(title);
        mAdapterLookEnclosure.setNewData(datas);
    }

    @OnClick(R.id.iv_close)
    public void onViewClicked() {
        dismiss();
    }
}
