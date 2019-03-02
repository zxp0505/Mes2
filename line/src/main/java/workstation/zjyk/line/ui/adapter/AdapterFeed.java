package workstation.zjyk.line.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import workstation.zjyk.line.R;
import workstation.zjyk.line.modle.bean.MaterielEnum;
import workstation.zjyk.line.modle.bean.Wrap;

/**
 * Created by zjgz on 2017/10/26.
 */

public class AdapterFeed extends BaseQuickAdapter<Wrap, BaseViewHolder> {
    public AdapterFeed() {
        super(R.layout.item_feed_menu_list_two);  //feed_menu_list_item
    }

    @Override
    protected void convert(BaseViewHolder holder, Wrap item) {
        holder.setText(R.id.tv_bar_code, item.getCode());
        holder.setText(R.id.tv_order_number, item.getOrderId()).addOnClickListener(R.id.iv_one);
//        holder.setText(R.id.tv_material_barcode, item.getCode());
//        holder.setText(R.id.tv_order_product_number, item.getOrderId());
//        if (item.getMaterielStatus() == MaterielEnum.DISTRIBUTED) {
//            //已分配
//            holder.setText(R.id.tv_materail_status, "已分配");
//        } else if (item.getMaterielStatus() == MaterielEnum.UNDISTRIBUTED) {
//            //未分配
//            holder.setText(R.id.tv_materail_status, "未分配");
//        }

    }
}
