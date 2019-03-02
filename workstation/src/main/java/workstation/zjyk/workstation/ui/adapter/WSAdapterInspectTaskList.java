package workstation.zjyk.workstation.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import workstation.zjyk.workstation.R;
import workstation.zjyk.workstation.modle.bean.WSOrderCheckVo;
import workstation.zjyk.workstation.modle.bean.WSWorkStationTask;
import workstation.zjyk.workstation.modle.bean.enumpackage.WSOrderStatusEnum;
import workstation.zjyk.workstation.modle.bean.enumpackage.WSTaskTypeEnum;
import workstation.zjyk.workstation.modle.bean.enumpackage.WSWorkConditionStatusEnum;
import workstation.zjyk.workstation.modle.bean.enumpackage.WSWorkStationTaskStatusEnum;
import workstation.zjyk.workstation.modle.bean.enumpackage.WSYesOrNoEnum;
import workstation.zjyk.workstation.ui.customview.TaskItemRelativeLayout;

/**
 * Created by zjgz on 2017/12/7.
 */

public class WSAdapterInspectTaskList extends BaseQuickAdapter<WSOrderCheckVo, BaseViewHolder> {
    public WSAdapterInspectTaskList() {
        super(R.layout.item_inspect_task_list);
    }

    @Override
    protected void convert(BaseViewHolder holder, WSOrderCheckVo item) {

        holder.setText(R.id.tv_product_number, item.getProductNumber());//"生产序号: " +
        holder.setText(R.id.tv_order_number, item.getSerialNumber());//"生产序号: " +
        holder.setText(R.id.tv_product_modle, item.getOrderProductModelTypeName());//"产品型号: " +
        holder.setText(R.id.tv_task_come_time, item.getPlanDate());//"任务下达时间: " +
        holder.setText(R.id.tv_delivery_time, item.getDeliverTime()).addOnClickListener(R.id.fl_root)
                .addOnClickListener(R.id.ll_station_trasfer);//"交货时间: " +

        holder.setText(R.id.tv_remark, item.getDescription());

        WSYesOrNoEnum checkStatus = item.getCheckStatus();
        //转移
        if (checkStatus == WSYesOrNoEnum.YES) {
            holder.setText(R.id.tv_inspect_status, "已巡检");
            holder.setBackgroundRes(R.id.tv_inspect_status, R.drawable.shape_task_bg_unnormal);
            holder.setBackgroundRes(R.id.ll_content,R.drawable.shape_task_bg_unnormal);
        } else {
            holder.setBackgroundRes(R.id.ll_content,R.drawable.shape_report_work_select);
            holder.setBackgroundRes(R.id.tv_inspect_status, R.drawable.shape_report_work_select);
            holder.setText(R.id.tv_inspect_status, "未巡检");
        }


        String planCount = item.getCount();
        holder.setText(R.id.tv_count_all, planCount);
    }

    @Override
    public void onViewAttachedToWindow(BaseViewHolder holder) {
        super.onViewAttachedToWindow(holder);

    }
}
