package workstation.zjyk.workstation.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import workstation.zjyk.workstation.R;
import workstation.zjyk.workstation.modle.bean.WSMYMater;
import workstation.zjyk.workstation.modle.bean.WSWip;

/**
 * Created by zjgz on 2017/12/8.
 */

public class WSAdapterExceptionMaking extends BaseQuickAdapter<WSWip, BaseViewHolder> {
    public WSAdapterExceptionMaking() {
        super(R.layout.item_exception_making);
    }

    @Override
    protected void convert(BaseViewHolder holder, WSWip item) {
        item.setPosition(holder.getAdapterPosition());
        holder.setText(R.id.tv_produce_name, item.getProcedureName());
        holder.setText(R.id.tv_count, item.getCount() + "");
        holder.setText(R.id.tv_except_count, item.getTrueCount() + "");
        holder.addOnClickListener(R.id.iv_reduce).addOnClickListener(R.id.iv_add).addOnClickListener(R.id.tv_except_count);

    }
}
