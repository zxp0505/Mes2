package workstation.zjyk.line.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import cn.com.ethank.mylibrary.resourcelibrary.utils.UICommonUtil;
import workstation.zjyk.line.R;
import workstation.zjyk.line.modle.bean.WorkStationRequestDetail;

/**
 * Created by zjgz on 2017/10/27.
 */

public class AdapterStationRequireDetail extends BaseQuickAdapter<WorkStationRequestDetail, BaseViewHolder> {
    public AdapterStationRequireDetail() {
        super(R.layout.item_station_require_detail_list);
    }

    @Override
    protected void convert(BaseViewHolder holder, WorkStationRequestDetail item) {
        UICommonUtil.setLayoutParamsDynamic(item.getMaterielName().length(), holder.getView(R.id.ll_item));
        holder.setText(R.id.tv_materail_number, item.getMaterielModel());
        holder.setText(R.id.tv_materail_name, item.getMaterielName());
        holder.setText(R.id.tv_count_require, item.getCount() + "");

    }
}
