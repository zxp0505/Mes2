package workstation.zjyk.workstation.ui.adapter;

import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import workstation.zjyk.workstation.R;
import workstation.zjyk.workstation.modle.bean.WSDailyWorkHistory;
import workstation.zjyk.workstation.modle.bean.WSExceptionOutRecordVo;

/**
 * Created by zjgz on 2017/12/12.
 */

public class WSAdapteExceptOutHistory extends BaseQuickAdapter<WSExceptionOutRecordVo, BaseViewHolder> {
    public WSAdapteExceptOutHistory() {
        super(R.layout.item_except_out_history);
    }

    @Override
    protected void convert(BaseViewHolder holder, WSExceptionOutRecordVo item) {

        holder.setText(R.id.tv_date, item.getDeliverTime());
        holder.setText(R.id.tv_worker_name, item.getPersonName());
        holder.setText(R.id.tv_materail_number, item.getMaterielCode());
        holder.setText(R.id.tv_materail_name, item.getMaterielName());
        holder.setText(R.id.tv_count, item.getCount() + "");
        if (Integer.parseInt(item.getPrintTag()) == 1) {
            if (TextUtils.isEmpty(item.getDeliverTime())) {
                holder.setVisible(R.id.tv_print, false);
            } else {
                holder.setVisible(R.id.tv_print, true).addOnClickListener(R.id.tv_print);
            }
        } else {
            holder.setVisible(R.id.tv_print, false);
        }
    }
}
