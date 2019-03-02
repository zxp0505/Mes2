package workstation.zjyk.workstation.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import workstation.zjyk.workstation.R;
import workstation.zjyk.workstation.modle.bean.WSTray;

/**
 * Created by zjgz on 2017/12/21.
 */

public class WSAdapterLookMaterailTray extends BaseQuickAdapter<WSTray, BaseViewHolder> {
    public WSAdapterLookMaterailTray() {
        super(R.layout.item_look_materail_tray);
    }

    @Override
    protected void convert(BaseViewHolder holder, WSTray item) {
        holder.setText(R.id.tv_name, item.getOneDemensionCode());
        holder.setText(R.id.tv_count, item.getCount() + "");
    }
}
