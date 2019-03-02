package workstation.zjyk.line.util.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import workstation.zjyk.line.R;
import workstation.zjyk.line.modle.bean.AccessoryAddress;
import workstation.zjyk.line.ui.adapter.AdapterLookEnclosure;
import workstation.zjyk.line.util.dialog.callback.EnclosureCallBack;

/**
 * 查看附件
 */

public class LookEnclosureDialog extends CommonDialog {


    @BindView(R.id.recycleview)
    RecyclerView recycleview;
    String title;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_close)
    ImageView ivClose;
    private AdapterLookEnclosure mAdapterLookEnclosure;
    List<AccessoryAddress> datas;
    EnclosureCallBack enclosureCallBack;

    public LookEnclosureDialog(Context context, String title, EnclosureCallBack enclosureCallBack) {
        super(context, R.style.CommonDialog);
        this.title = title;
        this.enclosureCallBack = enclosureCallBack;
        initDialogView(context);

    }


    public LookEnclosureDialog(Context context, int themeResId) {
        super(context, themeResId);
        initDialogView(context);
    }

    protected LookEnclosureDialog(Context context, boolean cancelable, DialogInterface.OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initDialogView(context);
    }

    private void initDialogView(Context context) {
        setContentView(R.layout.dialog_look_enclosure);
        ButterKnife.bind(this);
        mAdapterLookEnclosure = new AdapterLookEnclosure();
        recycleview.setLayoutManager(new GridLayoutManager(getContext(), 3));
        recycleview.setAdapter(mAdapterLookEnclosure);
        mAdapterLookEnclosure.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                AccessoryAddress bean = (AccessoryAddress) adapter.getData().get(position);
                switch (view.getId()) {
                    case R.id.ll_root:
                        enclosureCallBack.selectIten(bean);
                        break;
                }
            }
        });

    }

    public void setData(List<AccessoryAddress> datas) {
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
