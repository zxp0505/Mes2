package workstation.zjyk.line.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import cn.com.ethank.mylibrary.resourcelibrary.utils.UICommonUtil;
import workstation.zjyk.line.R;
import workstation.zjyk.line.modle.bean.ExceptionDetailBean;

/**
 * Created by zjgz on 2017/11/1.
 */

public class AdapterUnusalTwo extends BaseQuickAdapter<ExceptionDetailBean, BaseViewHolder> {
    boolean isReport;

    public AdapterUnusalTwo(boolean isReport) {
        super(R.layout.unusal_menu_list_item_two);
        this.isReport = isReport;
    }

    @Override
    protected void convert(BaseViewHolder holder, ExceptionDetailBean item) {
        UICommonUtil.setLayoutParamsDynamic(item.getName().length(),80,holder.getView(R.id.ll_item));
        item.setPosition(holder.getAdapterPosition());
        holder.setText(R.id.tv_materail_number, item.getOid());
        holder.setText(R.id.tv_materail_name, item.getName());
//        holder.setText(R.id.tv_require, item.getCount() + "");
        holder.setText(R.id.tv_true_bag, item.getExceptionOutCount() + "");
        if (isReport) {
            holder.setVisible(R.id.iv_reduce_bag, false).setVisible(R.id.iv_add_bag, false);
        } else {
            holder.addOnClickListener(R.id.iv_reduce_bag).addOnClickListener(R.id.iv_add_bag)
                    .addOnClickListener(R.id.tv_true_bag);
        }
    }
}
