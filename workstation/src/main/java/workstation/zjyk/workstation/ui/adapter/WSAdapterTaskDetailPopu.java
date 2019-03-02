package workstation.zjyk.workstation.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import workstation.zjyk.workstation.R;
import workstation.zjyk.workstation.modle.bean.WSTaskDetailItemBean;

/**
 * Created by zjgz on 2018/1/12.
 */

public class WSAdapterTaskDetailPopu extends BaseQuickAdapter<WSTaskDetailItemBean, BaseViewHolder> {
    public WSAdapterTaskDetailPopu() {
        super(R.layout.item_task_detail_popu);
    }

    @Override
    protected void convert(BaseViewHolder holder, WSTaskDetailItemBean item) {
        holder.setText(R.id.tv_name, item.getName());
        holder.setText(R.id.tv_detail, item.getDetail());
    }
}
