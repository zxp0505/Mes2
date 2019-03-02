package workstation.zjyk.workstation.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import cn.com.ethank.mylibrary.resourcelibrary.utils.UICommonUtil;
import workstation.zjyk.workstation.R;
import workstation.zjyk.workstation.modle.bean.WSMYMater;
import workstation.zjyk.workstation.modle.bean.WSMaterial;

/**
 * Created by zjgz on 2017/12/8.
 */

public class WSAdapterExceptionMaterail extends BaseQuickAdapter<WSMaterial, BaseViewHolder> {
    public WSAdapterExceptionMaterail() {
        super(R.layout.item_exception_materail);
    }

    @Override
    protected void convert(BaseViewHolder holder, WSMaterial item) {
        int singleLineTextlenth = 35;
        if (item.getName().length() > singleLineTextlenth) {
            UICommonUtil.setLayoutParamsDynamic(item.getName().length(), mContext.getResources().getDimensionPixelOffset(R.dimen.y_design_75px), singleLineTextlenth, holder.getView(R.id.ll_item));
        }
        item.setPosition(holder.getAdapterPosition());
        holder.setText(R.id.tv_materail_number, item.getModel());
        holder.setText(R.id.tv_materail_name, item.getName());
        holder.setText(R.id.tv_count, item.getCount() + "");
        holder.setText(R.id.tv_except_count, item.getTrueCount() + "");
        holder.addOnClickListener(R.id.iv_reduce).addOnClickListener(R.id.iv_add).addOnClickListener(R.id.tv_except_count);
    }
}
