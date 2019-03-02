package workstation.zjyk.workstation.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import workstation.zjyk.workstation.R;
import workstation.zjyk.workstation.modle.bean.WSTaskDetailItemBean;

/**
 * Created by zjgz on 2018/1/12.
 */

public class WSAdapterTaskReciver extends BaseQuickAdapter<WSTaskDetailItemBean, BaseViewHolder> {
    public WSAdapterTaskReciver() {
        super(R.layout.item_task_recivered);
    }

    @Override
    protected void convert(BaseViewHolder holder, WSTaskDetailItemBean item) {
        holder.setText(R.id.tv_name, item.getName());
        holder.setText(R.id.tv_detail, item.getDetail());
    }
}
