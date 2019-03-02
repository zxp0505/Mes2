package workstation.zjyk.workstation.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import workstation.zjyk.workstation.R;
import workstation.zjyk.workstation.modle.bean.WSLabelBean;

/**
 * Created by zjgz on 2017/12/21.
 */

public class WSAdapterLabelTemplet extends BaseQuickAdapter<WSLabelBean.TempletDataBean, BaseViewHolder> {
    public WSAdapterLabelTemplet() {
        super(R.layout.item_label_templet);
    }

    @Override
    protected void convert(BaseViewHolder holder, WSLabelBean.TempletDataBean item) {
        item.setPosition(holder.getAdapterPosition());
        holder.setText(R.id.tv_name, item.getTempletName()).addOnClickListener(R.id.cons_root);
        if (item.isSelect()) {
            holder.setBackgroundRes(R.id.iv_select_status, R.drawable.select_work);
        } else {
            holder.setBackgroundRes(R.id.iv_select_status, R.drawable.un_select_work);

        }
    }
}
