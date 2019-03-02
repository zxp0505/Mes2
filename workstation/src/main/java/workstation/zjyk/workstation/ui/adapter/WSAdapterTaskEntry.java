package workstation.zjyk.workstation.ui.adapter;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.logging.Handler;

import cn.com.ethank.mylibrary.resourcelibrary.utils.UICommonUtil;
import workstation.zjyk.workstation.R;
import workstation.zjyk.workstation.modle.bean.WSInputMaterialInfo;

/**
 * Created by zjgz on 2017/12/8.
 */

public class WSAdapterTaskEntry extends BaseQuickAdapter<WSInputMaterialInfo, BaseViewHolder> {
    public WSAdapterTaskEntry() {
        super(R.layout.item_task_entry_matarail);
    }

    @Override
    protected void convert(BaseViewHolder holder, WSInputMaterialInfo item) {
        int singleLineTextlenth = 40;
        if (item.getMaterialName().length() > singleLineTextlenth) {
            UICommonUtil.setLayoutParamsDynamic(item.getMaterialName().length(), mContext.getResources().getDimensionPixelOffset(R.dimen.y_design_75px), singleLineTextlenth, holder.getView(R.id.ll_root));
        }
        holder.setText(R.id.tv_materail_number, item.getModel());
        holder.setText(R.id.tv_materail_name, item.getMaterialName());
        holder.setText(R.id.tv_s_reciver_count, item.getMustCount() + "");
        holder.setText(R.id.tv_a_reciver_count, item.getReceivedCount() + "");
        holder.setText(R.id.tv_except_count, item.getExceptionCount() + "");
        holder.setText(R.id.tv_repair_count, item.getRepairCount() + "");
        holder.setText(R.id.tv_stupls, item.getRemainCount() + "").addOnClickListener(R.id.iv_look_tray_info_item);
    }
}
