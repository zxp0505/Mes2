package workstation.zjyk.line.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import workstation.zjyk.line.R;
import workstation.zjyk.line.modle.bean.WorkStationRequest;

/**
 * Created by zjgz on 2017/10/26.
 */

public class AdapterSelectStation extends BaseQuickAdapter<WorkStationRequest, BaseViewHolder> {
    public AdapterSelectStation() {
        super(R.layout.item_select_station);
    }

    @Override
    protected void convert(BaseViewHolder holder, WorkStationRequest item) {
        item.setPosition(holder.getAdapterPosition());
        holder.setText(R.id.tv_station_number, item.getWorkStationName());
        holder.setText(R.id.tv_product_number, item.getSerialNumber());
        holder.setText(R.id.tv_produce, item.getProcedureName()).addOnClickListener(R.id.tv_station_require_detail).addOnClickListener(R.id.ll_item);
        if (item.isSelect()) {
            holder.setBackgroundRes(R.id.ll_item, R.drawable.shape_station_item_bg_select);
        } else {
            holder.setBackgroundRes(R.id.ll_item, R.drawable.shape_station_item_bg);
        }
    }
}
