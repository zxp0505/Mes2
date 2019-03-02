package workstation.zjyk.line.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import cn.com.ethank.mylibrary.resourcelibrary.utils.UICommonUtil;
import workstation.zjyk.line.R;
import workstation.zjyk.line.modle.bean.TrayBean;
import workstation.zjyk.line.modle.bean.WorkStationDistributeMateriel;

/**
 * Created by zjgz on 2017/10/27.
 */

public class AdapterFeedThree extends BaseQuickAdapter<WorkStationDistributeMateriel, BaseViewHolder> {
    public AdapterFeedThree() {
        super(R.layout.item_station_require_detail_list);
    }

    @Override
    protected void convert(BaseViewHolder holder, WorkStationDistributeMateriel item) {
        UICommonUtil.setLayoutParamsDynamic(item.getMaterielName().length(),70,holder.getView(R.id.ll_item));
        holder.setText(R.id.tv_materail_number, item.getMaterielModel());
        holder.setText(R.id.tv_materail_name, item.getMaterielName());
        holder.setText(R.id.tv_count_require, item.getWrapTrueCount() + item.getStorageTrueCount() + "");
    }
}
