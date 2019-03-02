package workstation.zjyk.com.scanapp.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import workstation.zjyk.com.scanapp.R;
import workstation.zjyk.com.scanapp.modle.bean.ScanTrayInfoMaterielVo;

/**
 * Created by zjgz on 2018/2/9.
 */

public class MaterailDetailAdapter extends BaseQuickAdapter<ScanTrayInfoMaterielVo, BaseViewHolder> {
    public MaterailDetailAdapter() {
        super(R.layout.item_materail);
    }

    @Override
    protected void convert(BaseViewHolder holder, ScanTrayInfoMaterielVo item) {
        holder.setText(R.id.tv_number, item.getMaterielCode());
        holder.setText(R.id.tv_name, item.getMaterielName());
        holder.setText(R.id.tv_count, item.getCount() + "");
    }
}
