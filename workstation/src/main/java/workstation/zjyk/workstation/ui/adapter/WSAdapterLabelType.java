package workstation.zjyk.workstation.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import workstation.zjyk.workstation.R;
import workstation.zjyk.workstation.modle.bean.WSAccessoryAddress;
import workstation.zjyk.workstation.modle.bean.WSLabelBean;

/**
 * Created by zjgz on 2017/12/21.
 */

public class WSAdapterLabelType extends BaseQuickAdapter<WSLabelBean, BaseViewHolder> {
    public WSAdapterLabelType() {
        super(R.layout.item_label_type);
    }

    @Override
    protected void convert(BaseViewHolder holder, WSLabelBean item) {
        item.setPosition(holder.getAdapterPosition());
        holder.setText(R.id.tv_name, item.getLabelName()).addOnClickListener(R.id.ll_root);
        if (item.isSlect()) {
            holder.setBackgroundRes(R.id.tv_name, R.drawable.shape_report_work_select);
        } else {
            holder.setBackgroundRes(R.id.tv_name, R.drawable.shape_report_work_unselect);

        }
    }
}
