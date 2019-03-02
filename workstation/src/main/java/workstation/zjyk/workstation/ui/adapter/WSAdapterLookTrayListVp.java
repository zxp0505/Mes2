package workstation.zjyk.workstation.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import workstation.zjyk.workstation.R;
import workstation.zjyk.workstation.modle.bean.WSTray;
import workstation.zjyk.workstation.modle.bean.WSTrayVo;
import workstation.zjyk.workstation.modle.bean.enumpackage.WSTrayDeliverStatusEnum;
import workstation.zjyk.workstation.modle.bean.enumpackage.WSTrayMaterielWIPEnum;

/**
 * Created by zjgz on 2017/12/21.
 */

public class WSAdapterLookTrayListVp extends BaseQuickAdapter<WSTrayVo, BaseViewHolder> {
    public WSAdapterLookTrayListVp() {
        super(R.layout.item_look_tray_list_vp);
    }

    @Override
    protected void convert(BaseViewHolder holder, WSTrayVo item) {
        holder.setText(R.id.tv_name, item.getOneDemensionCode());
        WSTrayMaterielWIPEnum type = item.getType();
        String typeText = "未知";
        if (type != null) {
            typeText = type.getKey();
        }
        holder.setText(R.id.tv_count, typeText);
        WSTrayDeliverStatusEnum deliverStatus = item.getDeliverStatus();
        if (deliverStatus != null && deliverStatus == WSTrayDeliverStatusEnum.RECEIVED) {
            holder.setBackgroundColor(R.id.ll_item, mContext.getResources().getColor(R.color.c_btn_login));
        } else {
            holder.setBackgroundColor(R.id.ll_item, mContext.getResources().getColor(R.color.c_work_yellow2));
        }
    }
}
