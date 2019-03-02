package workstation.zjyk.line.ui.adapter;

import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import cn.com.ethank.mylibrary.resourcelibrary.utils.UICommonUtil;
import workstation.zjyk.line.R;
import workstation.zjyk.line.modle.bean.OrderMaterielVo;
import workstation.zjyk.line.modle.bean.YesOrNoEnum;

public class AdapterBondOrder extends BaseQuickAdapter<OrderMaterielVo, BaseViewHolder> {
    public AdapterBondOrder() {
        super(R.layout.item_bond_order);
    }

    @Override
    protected void convert(BaseViewHolder holder, OrderMaterielVo item) {
        holder.setText(R.id.tv_model, item.getOrderProductModelTypeName());
        holder.setText(R.id.tv_order_number, item.getSerialNumber());
        holder.setText(R.id.tv_product_number, item.getProductNumber());
        holder.setText(R.id.tv_count, item.getCount());
        TextView materailCondition = holder.getView(R.id.tv_materail_condition);
        if (item.getOrderNoMaterielTage() == YesOrNoEnum.YES) {
            holder.setText(R.id.tv_materail_condition, "料齐");
            UICommonUtil.setTextViewDrawableLeft(mContext, R.drawable.ok, materailCondition);
        } else {
            holder.setText(R.id.tv_materail_condition, "欠料");
            UICommonUtil.setTextViewDrawableLeft(mContext, R.drawable.unnormal, materailCondition);
        }

        if (item.getIsReceived() != null && item.getIsReceived() == YesOrNoEnum.YES) {
            holder.setBackgroundColor(R.id.cl_content, mContext.getResources().getColor(R.color.task_normal));
        } else {
            holder.setBackgroundColor(R.id.cl_content, mContext.getResources().getColor(R.color.base_msg_color));
        }
        if (item.getMaterielTage() == YesOrNoEnum.YES) {
            holder.setText(R.id.tv_one_bond, R.string.text_bonded);
            holder.setBackgroundRes(R.id.tv_one_bond, 0);
        } else {
            //一键下发
            holder.setText(R.id.tv_one_bond, R.string.text_one_bond);
            holder.setBackgroundRes(R.id.tv_one_bond, R.drawable.shape_bond_bg);
//            holder.setBackgroundColor(R.id.cl_content, mContext.getResources().getColor(R.color.transparent));
        }
        holder.addOnClickListener(R.id.tv_one_bond).addOnClickListener(R.id.fl_item);
        if (item.getIsTop() != null && item.getIsTop() == YesOrNoEnum.YES) {
            holder.setVisible(R.id.iv_top, true);
        } else {
            holder.setVisible(R.id.iv_top, false);
        }
        if (item.getWorryDistribute() != null && item.getWorryDistribute() == YesOrNoEnum.YES) {
            holder.setVisible(R.id.iv_urgent, true);
        } else {
            holder.setVisible(R.id.iv_urgent, false);
        }

        if (item.isClick()) {
            holder.setBackgroundRes(R.id.constrain_item, R.drawable.shape_order_item_bg_click);
        } else {
            holder.setBackgroundRes(R.id.constrain_item, R.drawable.shape_order_item_bg);
        }

    }
}
