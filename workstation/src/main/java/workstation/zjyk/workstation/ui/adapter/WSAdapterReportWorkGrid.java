package workstation.zjyk.workstation.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import workstation.zjyk.workstation.R;
import workstation.zjyk.workstation.modle.bean.WSProcedureStep;

/**
 * Created by zjgz on 2017/12/12.
 */

public class WSAdapterReportWorkGrid extends BaseQuickAdapter<WSProcedureStep, BaseViewHolder> {
    public WSAdapterReportWorkGrid() {
        super(R.layout.item_report_work_grid);
    }

    @Override
    protected void convert(BaseViewHolder holder, WSProcedureStep item) {
        int adapterPosition = holder.getAdapterPosition();
        item.setPosition(adapterPosition);
        holder.addOnClickListener(R.id.fl_item);
        holder.setText(R.id.tv_step, item.getName());

        if (item.isSelect()) {
            holder.setBackgroundRes(R.id.tv_step, R.drawable.shape_report_work_select);
        } else {
            holder.setBackgroundRes(R.id.tv_step, R.drawable.shape_report_work_unselect);
        }
    }
}
