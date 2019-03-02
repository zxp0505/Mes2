package workstation.zjyk.line.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import workstation.zjyk.line.R;
import workstation.zjyk.line.modle.bean.MaterielVo;

/**
 * Created by zjgz on 2017/10/27.
 */

public class AdapterInWarehouse extends BaseQuickAdapter<MaterielVo, BaseViewHolder> {
    public AdapterInWarehouse() {
        super(R.layout.item_inwarehouse_menu);
    }

    @Override
    protected void convert(BaseViewHolder holder, MaterielVo item) {
        holder.setText(R.id.tv_materail_number, item.getModel());
        holder.setText(R.id.tv_materail_name, item.getName());
        holder.setText(R.id.tv_materail_batch_number, item.getMaterielBatchNumber());
        holder.setText(R.id.tv_count_all, item.getRemainCount() + "");
        holder.setText(R.id.tv_count_inware, item.getInWareCount() + "").addOnClickListener(R.id.iv_reduce_ware_house)
                .addOnClickListener(R.id.iv_add_ware_house)
                .addOnClickListener(R.id.tv_count_inware);

    }
}
