package workstation.zjyk.line.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import workstation.zjyk.line.R;
import workstation.zjyk.line.modle.bean.AccessoryAddress;

/**
 * Created by zjgz on 2017/12/21.
 */

public class AdapterLookEnclosure extends BaseQuickAdapter<AccessoryAddress, BaseViewHolder> {
    public AdapterLookEnclosure() {
        super(R.layout.item_look_enclosure);
    }

    @Override
    protected void convert(BaseViewHolder holder, AccessoryAddress item) {
        holder.setText(R.id.tv_name, item.getAccessoryType()).addOnClickListener(R.id.ll_root);
    }
}
