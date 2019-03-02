package workstation.zjyk.workstation.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import workstation.zjyk.workstation.R;
import workstation.zjyk.workstation.modle.bean.WSReturnOrRepairHistory;

/**
 * Created by zjgz on 2017/12/12.
 */

public class WSAdapterRepairHistory extends BaseQuickAdapter<WSReturnOrRepairHistory, BaseViewHolder> {
    public WSAdapterRepairHistory() {
        super(R.layout.item_repair_history);
    }

    @Override
    protected void convert(BaseViewHolder holder, WSReturnOrRepairHistory item) {
        holder.setText(R.id.tv_timer, item.getDeliverTime());
        holder.setText(R.id.tv_person, item.getPersonName());
        holder.setText(R.id.tv_produce, item.getProcedureName());
        holder.setText(R.id.tv_output_count, item.getOutCount() + "");
        holder.setText(R.id.tv_return_count, item.getReturnCount() + "");
    }
}
