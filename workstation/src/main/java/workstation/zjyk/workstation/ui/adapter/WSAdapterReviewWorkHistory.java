package workstation.zjyk.workstation.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import workstation.zjyk.workstation.R;
import workstation.zjyk.workstation.modle.bean.WSReturnOrRepairHistory;

/**
 * Created by zjgz on 2018/2/1.
 */

public class WSAdapterReviewWorkHistory extends BaseQuickAdapter<WSReturnOrRepairHistory, BaseViewHolder> {
    public WSAdapterReviewWorkHistory() {
        super(R.layout.item_review_work_history);
    }

    @Override
    protected void convert(BaseViewHolder holder, WSReturnOrRepairHistory item) {
        holder.setText(R.id.tv_delivey_time, item.getDeliverTime());
        holder.setText(R.id.tv_person, item.getPersonName());
        holder.setText(R.id.tv_produce, item.getProcedureName());
        holder.setText(R.id.tv_output_count, item.getOutCount() + "");
        holder.setText(R.id.tv_review_count, item.getReturnCount() + "");
    }
}
