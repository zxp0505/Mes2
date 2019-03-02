package workstation.zjyk.line.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import cn.com.ethank.mylibrary.resourcelibrary.utils.UICommonUtil;
import workstation.zjyk.line.R;
import workstation.zjyk.line.modle.bean.ExceptionDetailBean;

/**
 * Created by zjgz on 2017/10/27.
 */

public class AdapterExceptionBind extends BaseQuickAdapter<ExceptionDetailBean, BaseViewHolder> {
    public AdapterExceptionBind() {
        super(R.layout.item_exception_bind);
    }

    @Override
    protected void convert(BaseViewHolder holder, ExceptionDetailBean item) {
        UICommonUtil.setLayoutParamsDynamic(item.getName().length(), 70, holder.getView(R.id.ll_item));
        holder.setText(R.id.tv_materail_number, item.getOid());
        holder.setText(R.id.tv_materail_name, item.getName());
        holder.setText(R.id.tv_count_require, item.getExceptionOutCount()+"");
    }
}
