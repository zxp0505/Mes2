package workstation.zjyk.line.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import workstation.zjyk.line.R;
import workstation.zjyk.line.modle.bean.GoodsBean;

/**
 * Created by zjgz on 2017/10/25.
 */

public class AdapterReciver extends BaseQuickAdapter<GoodsBean,BaseViewHolder> {
    public AdapterReciver() {
        super(R.layout.item_reciver);
    }

    @Override
    protected void convert(BaseViewHolder holder, GoodsBean item) {
        holder.setText(R.id.tv_order_product_number,item.getOrderId());//订单生产编号
        holder.setText(R.id.tv_material_number,item.getModel());//物料编号
        holder.setText(R.id.tv_material_name,item.getName());//物料名称
        holder.setText(R.id.tv_batch_number,item.getMaterielBatchNumber());//批次号
        holder.setText(R.id.tv_count,item.getCount());//数量
    }
}
