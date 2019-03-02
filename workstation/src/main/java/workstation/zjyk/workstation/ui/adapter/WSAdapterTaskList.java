package workstation.zjyk.workstation.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import workstation.zjyk.workstation.R;
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

public class WSAdapterTaskList extends BaseQuickAdapter<WSWorkStationTask, BaseViewHolder> {
    public WSAdapterTaskList() {
        super(R.layout.item_task_list_new);
    }

    @Override
    protected void convert(BaseViewHolder holder, WSWorkStationTask item) {
        WSTaskTypeEnum wsTaskTypeEnum = item.getTaskType();//任务类型
        WSWorkConditionStatusEnum workConditionStatus = item.getWorkConditionStatus();//任务条件
        holder.setVisible(R.id.tv_task_type, false);
        if (workConditionStatus == WSWorkConditionStatusEnum.NOTREADY) {
            //异常
//            UICommonUtil.setTextViewDrawableRight(mContext, R.drawable.unnormal, holder.getView(R.id.tv_work_condition));
//            holder.setBackgroundRes(R.id.fl_root, R.drawable.shape_task_bg_unnormal);
            holder.setImageResource(R.id.iv_work_condition, R.drawable.weijiuxu);
        } else {
            //正常
            holder.setImageResource(R.id.iv_work_condition, R.drawable.jiuxu);
//            UICommonUtil.setTextViewDrawableRight(mContext, R.drawable.select_worker, holder.getView(R.id.tv_work_condition));
        }
        if (wsTaskTypeEnum != null) {
            switch (wsTaskTypeEnum) {
                case COMMON:
                    //普通任务
                    holder.setBackgroundColor(R.id.ll_content, mContext.getResources().getColor(R.color.task_normal));
                    break;
                case FQA_RETURN:
                case REWORK:
                    //返工任务
                    holder.setBackgroundColor(R.id.ll_content, mContext.getResources().getColor(R.color.task_rework));
//                    holder.setBackgroundRes(R.id.fl_root, R.drawable.shape_task_bg_review);
                    break;
                case REPAIR_HELP:
                    //維修辅助任务
                    holder.setBackgroundColor(R.id.ll_content, mContext.getResources().getColor(R.color.task_assis));
//                    holder.setBackgroundRes(R.id.fl_root, R.drawable.shape_task_bg_assis);
                    break;
                case REPAIR:
                    //維修
                    holder.setBackgroundColor(R.id.ll_content, mContext.getResources().getColor(R.color.task_assis));
//                    holder.setBackgroundRes(R.id.fl_root, R.drawable.shape_task_bg_assis);
                    break;
                case HISTORY_TASK:
                    holder.setBackgroundColor(R.id.ll_content, mContext.getResources().getColor(R.color.task_history));
                    holder.setVisible(R.id.tv_task_type, true);
                    if (item.getHistoryTaskType() != null) {
                        holder.setText(R.id.tv_task_type, item.getHistoryTaskType().getKey());
                    }
                    break;
            }
        } else {
            //普通任务
            holder.setBackgroundColor(R.id.ll_content, mContext.getResources().getColor(R.color.task_normal));
//            holder.setBackgroundRes(R.id.fl_root, R.drawable.shape_task_bg_normal);
        }

        holder.setText(R.id.tv_product_number, item.getProductNumber());//"生产序号: " +
        holder.setText(R.id.tv_product_modle, item.getProductModelTypeName());//"产品型号: " +
        holder.setText(R.id.tv_task_come_time, item.getTaksStartTime());//"任务下达时间: " +
        holder.setText(R.id.tv_delivery_time, item.getDeliverTime()).addOnClickListener(R.id.fl_root)
                .addOnClickListener(R.id.ll_station_trasfer);//"交货时间: " +

        holder.setText(R.id.tv_remark, item.getDescription());
//        holder.setText(R.id.tv_order, "生产序号: " + item.getProductNumber());
//        holder.setText(R.id.tv_product, "产品型号: " + item.getProductModelTypeName());
//        holder.setText(R.id.tv_produce, "工序: " + item.getProcedureName());
//        holder.setText(R.id.tv_count, "数量: " + item.getPlanCount()).addOnClickListener(R.id.fl_root)
//                .addOnClickListener(R.id.tv_station_trasfer);
        WSYesOrNoEnum firstProduct = item.getFirstProduct();
        if (firstProduct == WSYesOrNoEnum.YES) {
            holder.setImageResource(R.id.iv_first, R.drawable.shoujian);
//            holder.setVisible(R.id.iv_first, true);
        } else {
            holder.setImageResource(R.id.iv_first, R.drawable.kongbai);
//            holder.setVisible(R.id.iv_first, false);
        }

        WSWorkStationTaskStatusEnum stationTaskStatusEnum = item.getStationTaskStatusEnum();
        //转移
        if (wsTaskTypeEnum == WSTaskTypeEnum.COMMON) {
            if (stationTaskStatusEnum == WSWorkStationTaskStatusEnum.NOTSTARTED) {
                holder.setVisible(R.id.ll_station_trasfer, true);
            } else {
                holder.setVisible(R.id.ll_station_trasfer, false);
            }
        } else {
            holder.setVisible(R.id.ll_station_trasfer, false);
        }

        switch (stationTaskStatusEnum) {
            case SUSPEND:
                holder.setImageResource(R.id.iv_pause_status, R.drawable.zantingjishi);
                break;
            case STARTED:
                holder.setImageResource(R.id.iv_pause_status, R.drawable.yikaishi);
                break;
            default:
                holder.setImageResource(R.id.iv_pause_status, R.drawable.kongbai);
                break;
        }

        WSOrderStatusEnum status = item.getStatus();
        switch (status) {
            case PAUSE:
                holder.setImageResource(R.id.iv_pause_task, R.drawable.zanting);
                break;
            default:
                holder.setImageResource(R.id.iv_pause_task, R.drawable.kongbai);
                break;
        }

        int planCount = item.getPlanCount();
        int workStationOutHistoryCount = item.getWorkStationOutHistoryCount();
        holder.setText(R.id.tv_count_alrealy, workStationOutHistoryCount + "");
        holder.setText(R.id.tv_count_all, planCount + "");
        if (item.getIsTop() == 1) {
            holder.setVisible(R.id.iv_top, true);
        } else {
            holder.setVisible(R.id.iv_top, false);
        }
        TaskItemRelativeLayout flRoot = holder.getView(R.id.fl_root);
        if (item.getWorryDistribute() != null && item.getWorryDistribute() == WSYesOrNoEnum.YES) {
//            Log.e(TaskItemRelativeLayout.TAG, "convert");
            holder.setVisible(R.id.iv_urgent, true);
        } else {
            holder.setVisible(R.id.iv_urgent, false);
        }
    }

    @Override
    public void onViewAttachedToWindow(BaseViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        TaskItemRelativeLayout flRoot = holder.getView(R.id.fl_root);
        int adapterPosition = holder.getAdapterPosition();
        if (adapterPosition < getData().size()) {
            WSWorkStationTask item = getData().get(adapterPosition);
            if (item.getWorryDistribute() != null && item.getWorryDistribute() == WSYesOrNoEnum.YES) {
//                Log.e(TaskItemRelativeLayout.TAG, "onViewAttachedToWindow");
                flRoot.startUpdateBg();
            } else {
                flRoot.stopUpdateBg();
            }
        }

    }
}
