package workstation.zjyk.workstation.ui.pop;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import workstation.zjyk.workstation.R;
import workstation.zjyk.workstation.modle.bean.WSAccessoryAddress;
import workstation.zjyk.workstation.modle.bean.WSTaskDetailItemBean;
import workstation.zjyk.workstation.modle.bean.WSTaskMainInfo;
import workstation.zjyk.workstation.ui.adapter.WSAdapterEnclosurePopu;
import workstation.zjyk.workstation.ui.adapter.WSAdapterTaskDetailPopu;
import workstation.zjyk.workstation.ui.pop.basePop.WSBasePopupWindow;

/**
 * 产品附件popu
 * Created by zjgz on 2018/1/12.
 */

public class WSEnclosurePopu extends WSBasePopupWindow implements WSBasePopupWindow.OnDismissListener {
    @BindView(R.id.recycleview)
    RecyclerView recycleview;
    private WSAdapterEnclosurePopu wsAdapterEnclosurePopu;
    WSEnclosureCallBack enclosureCallBack;
    WSAccessoryAddress currentBean = null;

    public WSEnclosurePopu(Activity context, WSEnclosureCallBack enclosureCallBack) {
        super(context, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, R.style.PopAnimTwo);
        this.enclosureCallBack = enclosureCallBack;
        initView();
    }

    public WSEnclosurePopu setData(List<WSAccessoryAddress> accessoryAddresslist) {

        wsAdapterEnclosurePopu.setNewData(accessoryAddresslist);
        return this;
    }

    private void initView() {
        recycleview.setLayoutManager(new LinearLayoutManager(mContext));
        wsAdapterEnclosurePopu = new WSAdapterEnclosurePopu();
        recycleview.setAdapter(wsAdapterEnclosurePopu);
        wsAdapterEnclosurePopu.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                List<WSAccessoryAddress> datas = adapter.getData();
                WSAccessoryAddress wsAccessoryAddress = datas.get(position);
                currentBean = wsAccessoryAddress;
                switch (view.getId()) {
                    case R.id.ll_root:
                        if (!wsAccessoryAddress.isSelect()) {
                            wsAccessoryAddress.setSelect(true);
                            adapter.notifyItemChanged(position);
                            enclosureCallBack.selectIten(wsAccessoryAddress);
                            dismiss();
                        }
                        break;
                }
            }
        });
        setOnDismissListener(this);

    }

    @Override
    public View getPopupView() {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.popu_enclosure, null);
        return inflate;
    }

    @Override
    public View getAnimaView() {
        return null;
    }

    @Override
    protected Animation getShowAnimation() {
        return null;
    }

    @Override
    protected View getClickToDismissView() {
        return null;
    }

    @Override
    public void onDismiss(String name) {
        if (currentBean != null && currentBean.isSelect()) {
            currentBean.setSelect(false);
            wsAdapterEnclosurePopu.notifyItemChanged(currentBean.getPosition());
        }
    }
}
