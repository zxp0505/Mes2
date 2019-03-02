package workstation.zjyk.line.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import cn.com.ethank.mylibrary.resourcelibrary.utils.UICommonUtil;
import workstation.zjyk.line.R;
import workstation.zjyk.line.modle.bean.MaterielVo;

/**
 * Created by zjgz on 2017/11/6.
 */

public class AdapterDelivery extends BaseQuickAdapter<MaterielVo, BaseViewHolder> {
    public AdapterDelivery() {
        super(R.layout.item_delivery);
    }

    @Override
    protected void convert(BaseViewHolder holder, MaterielVo item) {
        UICommonUtil.setLayoutParamsDynamic(item.getName().length(),holder.getView(R.id.ll_item));
        holder.setText(R.id.tv_name, item.getName());
        holder.setText(R.id.tv_count, item.getCount() + "");
        holder.setText(R.id.tv_materail_number,item.getModel());
    }
}
