package workstation.zjyk.com.scanapp.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import workstation.zjyk.com.scanapp.R;
import workstation.zjyk.com.scanapp.modle.bean.ScanTrayInfoMaterielVo;
import workstation.zjyk.com.scanapp.modle.bean.ScanTrayInfoWIPVo;

/**
 * Created by zjgz on 2018/2/9.
 */

public class WipDetailAdapter extends BaseQuickAdapter<ScanTrayInfoWIPVo, BaseViewHolder> {
    public WipDetailAdapter() {
        super(R.layout.item_wip);
    }

    @Override
    protected void convert(BaseViewHolder holder, ScanTrayInfoWIPVo item) {
        holder.setText(R.id.tv_name, item.getWipName());
        holder.setText(R.id.tv_count, item.getCount() + "");
    }
}
