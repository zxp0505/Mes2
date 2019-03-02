package workstation.zjyk.workstation.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import workstation.zjyk.workstation.R;
import workstation.zjyk.workstation.modle.bean.WSInputWipInfo;
import workstation.zjyk.workstation.modle.bean.WSMYMater;

/**
 * Created by zjgz on 2017/12/8.
 */

public class WSAdapterTaskMaking extends BaseQuickAdapter<WSInputWipInfo, BaseViewHolder> {
    public WSAdapterTaskMaking() {
        super(R.layout.item_task_making);
    }

    @Override
    protected void convert(BaseViewHolder holder, WSInputWipInfo item) {
        holder.setText(R.id.tv_materail_name, item.getProcedureName());
        holder.setText(R.id.tv_s_reciver_count, item.getMustCount() + "");
        holder.setText(R.id.tv_a_reciver_count, item.getReceivedCount() + "");
        holder.setText(R.id.tv_a_repair_count, item.getRepairCount() + "");
        holder.setText(R.id.tv_a_review_count, item.getReturnCount() + "");
        holder.setText(R.id.tv_except_count, item.getExceptionCount() + "");
        holder.setText(R.id.tv_stupls, item.getRemainCount() + "").addOnClickListener(R.id.iv_look_tray_info_item);
    }
}
