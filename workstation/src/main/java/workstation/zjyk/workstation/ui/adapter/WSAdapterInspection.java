package workstation.zjyk.workstation.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import workstation.zjyk.workstation.R;
import workstation.zjyk.workstation.modle.bean.WSProcedureStep;
import workstation.zjyk.workstation.modle.bean.WSTaskProductCheckTray;
import workstation.zjyk.workstation.modle.bean.WSWorkStationCheckVO;

/**
 * Created by zjgz on 2017/12/12.
 */

public class WSAdapterInspection extends BaseQuickAdapter<WSTaskProductCheckTray, BaseViewHolder> {
    public WSAdapterInspection() {
        super(R.layout.item_inspection);
    }

    @Override
    protected void convert(BaseViewHolder holder, WSTaskProductCheckTray item) {
        item.setPosition(holder.getAdapterPosition());
        holder.setText(R.id.tv_step, item.getTrayCode());
        holder.setText(R.id.tv_count_, item.getOutCount() + "");
        if (item.isSelect()) {
            holder.setImageResource(R.id.iv_select_status, R.drawable.select_work);
        } else {
            holder.setImageResource(R.id.iv_select_status, R.drawable.un_select_work);
        }
        holder.addOnClickListener(R.id.iv_reduce).addOnClickListener(R.id.iv_add).addOnClickListener(R.id.iv_select_status).addOnClickListener(R.id.tv_count);
    }
}
