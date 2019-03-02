package workstation.zjyk.line.ui.adapter;

import android.graphics.Color;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import workstation.zjyk.line.R;
import workstation.zjyk.line.modle.bean.TrayBindTypeEnum;
import workstation.zjyk.line.modle.bean.TrayVo;

/**
 * Created by zjgz on 2017/12/4.
 */

public class AdapterDeliveryList extends BaseQuickAdapter<TrayVo, BaseViewHolder> {
    public AdapterDeliveryList() {
        super(R.layout.item_delivery_list);
    }


    @Override
    protected void convert(BaseViewHolder holder, TrayVo item) {
        holder.setText(R.id.tv_tray_number, item.getOneDemensionCode())
                .addOnClickListener(R.id.iv_tray);
        if (item.getBindingType() == TrayBindTypeEnum.LINE_EXCEPTION_MATERIEL) {
            holder.setTextColor(R.id.tv_tray_number, mContext.getResources().getColor(R.color.red));
        } else {
            holder.setTextColor(R.id.tv_tray_number, mContext.getResources().getColor(R.color.white_full));
        }
    }
}
