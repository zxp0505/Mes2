package workstation.zjyk.com.scanapp.ui.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import workstation.zjyk.com.scanapp.R;
import workstation.zjyk.com.scanapp.modle.bean.ScanResultItem;

/**
 * Created by zjgz on 2018/2/8.
 */

public class ScanResultAdapter extends BaseQuickAdapter<ScanResultItem, BaseViewHolder> {
    public ScanResultAdapter() {
        super(R.layout.item_scan_result);
    }

    @Override
    protected void convert(BaseViewHolder holder, ScanResultItem item) {
        holder.setText(R.id.tv_name, item.getName()).addOnClickListener(R.id.prl_root);
        holder.setText(R.id.tv_value, item.getValue());
        if (!TextUtils.isEmpty(item.getValue())) {
            holder.setVisible(R.id.tv_value, true);
        } else {
            holder.setVisible(R.id.tv_value, false);
        }
        if (item.isShowLoad()) {
            holder.setVisible(R.id.iv_load, true);
        } else {
            holder.setVisible(R.id.iv_load, false);
        }
        int size = getData().size();
        int adapterPosition = holder.getAdapterPosition();
        if (adapterPosition == 0) {
            holder.setVisible(R.id.view_top_line, true);
        } else {
            holder.setVisible(R.id.view_top_line, false);
        }
        if (adapterPosition == (size - 1)) {
            holder.setVisible(R.id.view_botoom_line, false);
        } else {
            holder.setVisible(R.id.view_botoom_line, true);
        }
    }
}
