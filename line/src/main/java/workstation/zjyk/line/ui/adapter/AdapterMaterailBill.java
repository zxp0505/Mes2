package workstation.zjyk.line.ui.adapter;

import android.graphics.Color;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import cn.com.ethank.mylibrary.resourcelibrary.utils.UICommonUtil;
import workstation.zjyk.line.R;
import workstation.zjyk.line.modle.bean.MaterielTypeEnum;
import workstation.zjyk.line.modle.bean.MaterielVo;

/**
 * 物料清单adapter
 * Created by zjgz on 2017/10/26.
 */

public class AdapterMaterailBill extends BaseQuickAdapter<MaterielVo, BaseViewHolder> {
    String orderId;//订单生产编号

    public AdapterMaterailBill(String orderId) {
        super(R.layout.item_feed_menu_materail_bill);
        this.orderId = orderId;
    }

    @Override
    protected void convert(BaseViewHolder holder, MaterielVo item) {
        UICommonUtil.setLayoutParamsDynamic(item.getName().length(),holder.getView(R.id.ll_item));
        holder.setText(R.id.tv_order_product_number, orderId);
        holder.setText(R.id.tv_materail_number, item.getModel());
        holder.setText(R.id.tv_materail_name, item.getName());
        holder.setText(R.id.tv_materail_batch_number, item.getMaterielBatchNumber());
        holder.setText(R.id.tv_reciver_count, item.getCount() + "");
        holder.setText(R.id.tv_undistribution_count, item.getRemainCount() + "");
        if (item.getMaterielType() == MaterielTypeEnum.ACCESSORIES) {
            holder.setTextColor(R.id.tv_materail_name, Color.YELLOW);
        } else {
            holder.setTextColor(R.id.tv_materail_name, Color.BLACK);
        }
    }
}
