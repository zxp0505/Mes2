package workstation.zjyk.line.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import workstation.zjyk.line.R;
import workstation.zjyk.line.modle.bean.LineMaterielStorageManagerVo;

/**
 * Created by zjgz on 2017/11/14.
 */

public class AdapterInventoryControl extends BaseQuickAdapter<LineMaterielStorageManagerVo, BaseViewHolder> {
    public AdapterInventoryControl() {
        super(R.layout.item_inventory_control_menu);
    }

    @Override
    protected void convert(BaseViewHolder holder, LineMaterielStorageManagerVo item) {
        item.setPosition(holder.getAdapterPosition());
        holder.setText(R.id.tv_materail_number, item.getoId());
        holder.setText(R.id.tv_materail_name, item.getName());
        holder.setText(R.id.tv_count, item.getCount() + "");
        holder.addOnClickListener(R.id.tv_opration);
    }
}
