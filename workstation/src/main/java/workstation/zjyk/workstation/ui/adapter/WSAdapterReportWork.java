package workstation.zjyk.workstation.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import workstation.zjyk.workstation.R;
import workstation.zjyk.workstation.modle.bean.WSMyReportWorkBean;
import workstation.zjyk.workstation.modle.bean.WSProcedureStep;

/**
 * Created by zjgz on 2017/12/12.
 */

public class WSAdapterReportWork extends BaseQuickAdapter<WSProcedureStep, BaseViewHolder> {
    public WSAdapterReportWork() {
        super(R.layout.item_report_work);
    }

    @Override
    protected void convert(BaseViewHolder holder, WSProcedureStep item) {
        item.setPosition(holder.getAdapterPosition());
        holder.setText(R.id.tv_step, item.getName());
        holder.setText(R.id.tv_count, item.getTrueCount() + "");
        if (item.isSelect()) {
            holder.setImageResource(R.id.iv_select_status, R.drawable.select_work);
        } else {
            holder.setImageResource(R.id.iv_select_status, R.drawable.un_select_work);
        }
        holder.addOnClickListener(R.id.iv_reduce).addOnClickListener(R.id.iv_add).addOnClickListener(R.id.iv_select_status).addOnClickListener(R.id.tv_count);
    }
}
