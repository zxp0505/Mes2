package workstation.zjyk.workstation.util.dialog;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import workstation.zjyk.workstation.R;
import workstation.zjyk.workstation.modle.bean.WSAccessoryAddress;
import workstation.zjyk.workstation.modle.bean.WSLabelBean;
import workstation.zjyk.workstation.ui.adapter.WSAdapterLabelType;
import workstation.zjyk.workstation.ui.customview.MyGridlayoutMManager;
import workstation.zjyk.workstation.ui.pop.WSEnclosureCallBack;
import workstation.zjyk.workstation.util.dialog.callback.WSDialogCallBackTwo;

/**
 * 查看附件
 */

public class WSLabelTypesDialog extends WSCommonDialog {


    @BindView(R.id.recycleview)
    RecyclerView recycleview;
    String title;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_close)
    ImageView ivClose;
    List<WSLabelBean> datas;
    WSDialogCallBackTwo wsDialogCallBackTwo;
    private WSAdapterLabelType mAdapterLabelType;
    String data = "{\"data\": [{\"labelName\": \"60x40\",\"labelType\": \"1\",\"templetData\": [{\"templetName\": \"模板一\",\"templetType\": 2}]},{\"labelName\": \"70x25\",\"labelType\": \"2\",\"templetData\": [{\"templetName\": \"模板一\",\"templetType\": 2}]}]}";
    private List<WSLabelBean> labelBeans;
    WSLabelBean selectLabelBean;

    public WSLabelTypesDialog(Context context, String title, WSDialogCallBackTwo wsDialogCallBackTwo) {
        super(context, R.style.CommonDialog);
        this.title = title;
        this.wsDialogCallBackTwo = wsDialogCallBackTwo;
        initDialogView(context);

    }


    public WSLabelTypesDialog(Context context, int themeResId) {
        super(context, themeResId);
        initDialogView(context);
    }

    protected WSLabelTypesDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initDialogView(context);
    }

    private void initDialogView(Context context) {
        setContentView(R.layout.dialog_label_type);
        ButterKnife.bind(this);
        mAdapterLabelType = new WSAdapterLabelType();
//        JSONObject jsonData = (JSONObject) JSONObject.parse(data);
//        labelBeans = JSON.parseArray(jsonData.getString("data"), WSLabelBean.class);
        recycleview.setLayoutManager(new MyGridlayoutMManager(getContext(), 2));
        recycleview.setAdapter(mAdapterLabelType);
        mAdapterLabelType.setNewData(labelBeans);
        mAdapterLabelType.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                WSLabelBean labelBean = (WSLabelBean) adapter.getData().get(position);
                switch (view.getId()) {
                    case R.id.ll_root:
                        if (selectLabelBean != null && selectLabelBean.getLabelType().equals(labelBean.getLabelType())) {
                            wsDialogCallBackTwo.OnPositiveClick(selectLabelBean);
                            return;
                        } else if (selectLabelBean != null) {
                            selectLabelBean.setSlect(false);
                            adapter.notifyItemChanged(selectLabelBean.getPosition());
                        }
                        if (!labelBean.isSlect()) {
                            labelBean.setSlect(true);
                            adapter.notifyItemChanged(position);
                            selectLabelBean = labelBean;
                        }
                        wsDialogCallBackTwo.OnPositiveClick(selectLabelBean);
                        dismiss();
                        break;
                }
            }
        });

    }

    public void setData(List<WSLabelBean> datas) {
        this.datas = datas;
        refreshData();
    }

    private void refreshData() {
        tvTitle.setText(title);
        mAdapterLabelType.setNewData(datas);
    }

    @OnClick(R.id.iv_close)
    public void onViewClicked() {
        dismiss();
    }
}
