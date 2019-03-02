package workstation.zjyk.workstation.ui.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import workstation.zjyk.workstation.R;
import workstation.zjyk.workstation.modle.bean.WSDeliverHistory;
import workstation.zjyk.workstation.modle.bean.WSMyMakingReport;

/**
 * Created by zjgz on 2017/12/12.
 */

public class WSAdapterMakingReport extends BaseQuickAdapter<WSDeliverHistory, BaseViewHolder> {
    public WSAdapterMakingReport() {
        super(R.layout.item_making_output);
    }

    @Override
    protected void convert(BaseViewHolder holder, WSDeliverHistory item) {
        holder.setText(R.id.tv_timer, item.getDeliverTime());
        holder.setText(R.id.tv_person, item.getPersonName());
        holder.setText(R.id.tv_produce, item.getProcedureName());
        holder.setText(R.id.tv_output_count, item.getCount() + "");
        TextView tvPrint = holder.getView(R.id.tv_print);
        if ("1".equals(item.getPrintTag())) {
            tvPrint.setVisibility(View.VISIBLE);
            holder.addOnClickListener(R.id.tv_print);
        } else {
            tvPrint.setVisibility(View.INVISIBLE);
        }
    }
}
