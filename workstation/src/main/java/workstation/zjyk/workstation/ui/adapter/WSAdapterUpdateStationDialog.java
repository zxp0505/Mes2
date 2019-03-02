package workstation.zjyk.workstation.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import workstation.zjyk.workstation.R;
import workstation.zjyk.workstation.modle.bean.WSWorkStation;

public class WSAdapterUpdateStationDialog extends BaseQuickAdapter<WSWorkStation, BaseViewHolder> {
    public WSAdapterUpdateStationDialog() {
        super(R.layout.item_update_station_grid);
    }

    @Override
    protected void convert(BaseViewHolder holder, WSWorkStation item) {
        int adapterPosition = holder.getAdapterPosition();
        item.setPosition(adapterPosition);
        holder.setText(R.id.tv_step, item.getName()).addOnClickListener(R.id.tv_step);
        if (item.isSelect()) {
            holder.setBackgroundRes(R.id.tv_step, R.drawable.shape_report_work_select);
        } else {
            holder.setBackgroundRes(R.id.tv_step, R.drawable.shape_report_work_unselect);
        }
    }
}
