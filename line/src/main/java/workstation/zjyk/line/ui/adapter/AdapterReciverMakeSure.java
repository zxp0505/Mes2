package workstation.zjyk.line.ui.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import cn.com.ethank.mylibrary.resourcelibrary.utils.UICommonUtil;
import workstation.zjyk.line.R;
import workstation.zjyk.line.modle.bean.MaterielVo;

/**
 * Created by zjgz on 2017/10/26.
 */

public class AdapterReciverMakeSure extends BaseQuickAdapter<MaterielVo, BaseViewHolder> {
    String orderId;

    public AdapterReciverMakeSure(String orderId) {
        super(R.layout.item_reciver_make_sure);//
        this.orderId = orderId;
    }

    @Override
    protected void convert(BaseViewHolder holder, MaterielVo item) {
        int length = item.getName().length();
        LinearLayout ll_item = holder.getView(R.id.ll_item);
        UICommonUtil.setLayoutParamsDynamic(length, ll_item);
        holder.addOnClickListener(R.id.ll_item);
        holder.setText(R.id.tv_order_product_number, orderId + "");
        holder.setText(R.id.tv_materail_number, item.getModel());
        holder.setText(R.id.tv_materail_name, item.getName());
        holder.setText(R.id.tv_batch_number, item.getMaterielBatchNumber());
        holder.setText(R.id.tv_count, item.getCount() + "");
        if (item.isSeleted()) {
            ll_item.setBackgroundColor(mContext.getResources().getColor(R.color.red));
        } else {
            ll_item.setBackgroundColor(mContext.getResources().getColor(R.color.transparent));
        }

    }
}
