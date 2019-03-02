package workstation.zjyk.workstation.ui.adapter;

import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import workstation.zjyk.workstation.R;
import workstation.zjyk.workstation.modle.bean.WSConcernMaterielVO;
import workstation.zjyk.workstation.modle.bean.WSFollowedBean;
import workstation.zjyk.workstation.modle.bean.WSMaterial;

/**
 * Created by zjgz on 2017/12/8.
 */

public class WSAdapterBomMaterailFollow extends BaseQuickAdapter<WSFollowedBean, BaseViewHolder> {
    public WSAdapterBomMaterailFollow() {
        super(R.layout.item_bom_materail_follow);
    }

    @Override
    protected void convert(BaseViewHolder holder, WSFollowedBean item) {
        holder.setText(R.id.tv_materail_number, item.getMaterielModel());
        holder.setText(R.id.tv_materail_name, item.getMaterielName());
        holder.setText(R.id.tv_count, item.getCount() + "");

        String procedureName = item.getProcedureName();
        if (TextUtils.isEmpty(procedureName)) {
            procedureName = "辅料";
            item.setProcedureName(procedureName);
        }

        int adapterPosition = holder.getAdapterPosition();
        if (adapterPosition > 0) {
            WSFollowedBean preData = getData().get(adapterPosition - 1);
            if (preData != null &&  preData.getProcedureName() != null && preData.getProcedureName().equals(item.getProcedureName())) {
                holder.setText(R.id.tv_produce_name, "");
            } else {
                holder.setText(R.id.tv_produce_name, item.getProcedureName());
            }
        } else {
            holder.setText(R.id.tv_produce_name, item.getProcedureName());
        }
    }
}
