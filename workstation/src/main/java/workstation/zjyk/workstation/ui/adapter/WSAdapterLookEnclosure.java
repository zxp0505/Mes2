package workstation.zjyk.workstation.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import workstation.zjyk.workstation.R;
import workstation.zjyk.workstation.modle.bean.WSAccessoryAddress;

/**
 * Created by zjgz on 2017/12/21.
 */

public class WSAdapterLookEnclosure extends BaseQuickAdapter<WSAccessoryAddress, BaseViewHolder> {
    public WSAdapterLookEnclosure() {
        super(R.layout.item_look_enclosure);
    }

    @Override
    protected void convert(BaseViewHolder holder, WSAccessoryAddress item) {
        holder.setText(R.id.tv_name, item.getAccessoryType()).addOnClickListener(R.id.ll_root);
    }
}
