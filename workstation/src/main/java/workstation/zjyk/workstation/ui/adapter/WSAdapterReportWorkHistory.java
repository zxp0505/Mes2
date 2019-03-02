package workstation.zjyk.workstation.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import workstation.zjyk.workstation.R;
import workstation.zjyk.workstation.modle.bean.WSDailyWorkHistory;

/**
 * Created by zjgz on 2017/12/12.
 */

public class WSAdapterReportWorkHistory extends BaseQuickAdapter<WSDailyWorkHistory, BaseViewHolder> {
    public WSAdapterReportWorkHistory() {
        super(R.layout.item_report_work_history);
    }

    @Override
    protected void convert(BaseViewHolder holder, WSDailyWorkHistory item) {
        holder.setText(R.id.tv_name, item.getName());
        holder.setText(R.id.tv_step, item.getProcedureStepName());
        holder.setText(R.id.tv_count, item.getCount() + "");
    }
}
