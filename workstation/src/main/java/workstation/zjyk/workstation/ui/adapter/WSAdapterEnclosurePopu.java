package workstation.zjyk.workstation.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import workstation.zjyk.workstation.R;
import workstation.zjyk.workstation.modle.bean.WSAccessoryAddress;

/**
 * Created by zjgz on 2018/1/12.
 */

public class WSAdapterEnclosurePopu extends BaseQuickAdapter<WSAccessoryAddress, BaseViewHolder> {
    public WSAdapterEnclosurePopu() {
        super(R.layout.item_enclosure_popu);
    }

    @Override
    protected void convert(BaseViewHolder holder, WSAccessoryAddress item) {
        item.setPosition(holder.getAdapterPosition());
        holder.setText(R.id.tv_name, item.getAccessoryType()).addOnClickListener(R.id.ll_root);
        if (item.isSelect()) {
            holder.setImageResource(R.id.iv_select_status, R.drawable.select_work);
        } else {
            holder.setImageResource(R.id.iv_select_status, R.drawable.un_select_work);
        }
    }
}
