package workstation.zjyk.workstation.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import workstation.zjyk.workstation.R;
import workstation.zjyk.workstation.modle.bean.WSProcedureStep;
import workstation.zjyk.workstation.modle.bean.WSWorkStationInfo;

/**
 * Created by zjgz on 2017/12/12.
 */

public class WSAdapterStationTransfer extends BaseQuickAdapter<WSWorkStationInfo, BaseViewHolder> {
    public WSAdapterStationTransfer() {
        super(R.layout.item_station_transfer);
    }

    @Override
    protected void convert(BaseViewHolder holder, WSWorkStationInfo item) {
        item.setPosition(holder.getAdapterPosition());
        holder.setText(R.id.tv_station_name, item.getName());
        holder.setText(R.id.tv_station_location, item.getPosDesc());
        if (item.isSelect()) {
            holder.setImageResource(R.id.iv_select_status, R.drawable.select_work);
        } else {
            holder.setImageResource(R.id.iv_select_status, R.drawable.un_select_work);
        }
        holder.addOnClickListener(R.id.iv_select_status);
    }
}
