package workstation.zjyk.line.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import cn.com.ethank.mylibrary.resourcelibrary.utils.UICommonUtil;
import workstation.zjyk.line.R;
import workstation.zjyk.line.modle.bean.WorkStationDistributeMateriel;

/**
 * Created by zjgz on 2017/10/27.
 */

public class AdapterFeedTwo extends BaseQuickAdapter<WorkStationDistributeMateriel, BaseViewHolder> {
    public AdapterFeedTwo() {
        super(R.layout.feed_two_menu_list_item);
    }

    @Override
    protected void convert(BaseViewHolder holder, WorkStationDistributeMateriel item) {//MaterDetailBean
        UICommonUtil.setLayoutParamsDynamic(item.getMaterielName().length(),100,holder.getView(R.id.ll_item));
        item.setPosition(holder.getAdapterPosition());
        holder.setText(R.id.tv_materail_number, item.getMaterielModel());
        holder.setText(R.id.tv_materail_name, item.getMaterielName());
        holder.setText(R.id.tv_require, item.getCount() + "");
        holder.setText(R.id.tv_surplus_bag, item.getWrapCount() + "");
        holder.setText(R.id.tv_surplus_warehouse, item.getStorageCount() + "");


        holder.setText(R.id.tv_true_bag, item.getWrapTrueCount() + "");
        holder.setText(R.id.tv_true_ware_house, item.getStorageTrueCount() + "");
        holder.addOnClickListener(R.id.iv_reduce_bag).addOnClickListener(R.id.iv_add_bag).addOnClickListener(R.id.iv_reduce_ware_house)
                .addOnClickListener(R.id.iv_add_ware_house)
                .addOnClickListener(R.id.tv_true_bag).addOnClickListener(R.id.tv_true_ware_house);
    }


}
