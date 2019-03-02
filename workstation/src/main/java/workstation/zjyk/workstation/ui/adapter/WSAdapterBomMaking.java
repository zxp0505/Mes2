package workstation.zjyk.workstation.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import workstation.zjyk.workstation.R;
import workstation.zjyk.workstation.modle.bean.WSMYMater;
import workstation.zjyk.workstation.modle.bean.WSWip;

/**
 * Created by zjgz on 2017/12/8.
 */

public class WSAdapterBomMaking extends BaseQuickAdapter<WSWip, BaseViewHolder> {
    public WSAdapterBomMaking() {
        super(R.layout.item_bom_making);
    }

    @Override
    protected void convert(BaseViewHolder holder, WSWip item) {
        holder.setText(R.id.tv_produce_name, item.getProcedureName());
        holder.setText(R.id.tv_count, item.getCount()+"");

    }
}
