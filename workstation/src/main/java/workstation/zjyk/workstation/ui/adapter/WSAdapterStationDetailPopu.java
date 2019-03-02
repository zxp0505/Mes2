package workstation.zjyk.workstation.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import workstation.zjyk.workstation.R;
import workstation.zjyk.workstation.modle.bean.WSAccessoryAddress;

/**
 * Created by zjgz on 2018/1/12.
 */

public class WSAdapterStationDetailPopu extends BaseQuickAdapter<String, BaseViewHolder> {
    public WSAdapterStationDetailPopu() {
        super(R.layout.item_station_detail_popu);
    }

    @Override
    protected void convert(BaseViewHolder holder, String item) {
        holder.setText(R.id.tv_name, item);
    }
}
