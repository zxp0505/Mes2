package workstation.zjyk.workstation.ui.adapter;

import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import cn.com.ethank.mylibrary.resourcelibrary.utils.UICommonUtil;
import workstation.zjyk.workstation.R;
import workstation.zjyk.workstation.modle.bean.WSMYMater;
import workstation.zjyk.workstation.modle.bean.WSMaterial;
import workstation.zjyk.workstation.modle.bean.WSProcedureStep;

/**
 * Created by zjgz on 2017/12/8.
 */

public class WSAdapterBomMaterail extends BaseQuickAdapter<WSMaterial, BaseViewHolder> {
    public WSAdapterBomMaterail() {
        super(R.layout.item_bom_materail);
    }

    @Override
    protected void convert(BaseViewHolder holder, WSMaterial item) {
//        int singleLineTextlenth = 20;
//        if (item.getName().length() > singleLineTextlenth) {
//            UICommonUtil.setLayoutParamsDynamic(item.getName().length(), mContext.getResources().getDimensionPixelOffset(R.dimen.y_design_75px), singleLineTextlenth, holder.getView(R.id.ll_item));
//        }
        holder.setVisible(R.id.view_line, false);
        holder.setText(R.id.tv_materail_number, item.getModel());
        holder.setText(R.id.tv_materail_name, item.getName());
        holder.setText(R.id.tv_count, item.getCount() + "").addOnClickListener(R.id.fl_root);
        String procedureName = item.getProcedureName();
        if (TextUtils.isEmpty(procedureName)) {
            procedureName = "辅料";
            item.setProcedureName(procedureName);
        }

        int adapterPosition = holder.getAdapterPosition();
        if (adapterPosition > 0) {
            WSMaterial preData = getData().get(adapterPosition - 1);
            if (preData != null && preData.getProcedureName().equals(item.getProcedureName())) {
                holder.setText(R.id.tv_produce_name, "");
            } else {
                holder.setText(R.id.tv_produce_name, item.getProcedureName());
                holder.setVisible(R.id.view_line, true);
            }
        } else {
            holder.setText(R.id.tv_produce_name, item.getProcedureName());
        }
        String tag = item.getTag();
//        if ("2".equals(tag)) {
//            //辅料
//            holder.setBackgroundColor(R.id.fl_root, mContext.getResources().getColor(R.color.stroke_black));
//        } else {
//            holder.setBackgroundColor(R.id.fl_root, mContext.getResources().getColor(R.color.transparent));
//        }

        int followStatus = item.getFollowStatus();
        if (followStatus == 0) {
            //未关注 dialog_bg
            holder.setBackgroundColor(R.id.fl_root, mContext.getResources().getColor(R.color.white_full));
        } else {
            //已关注
            holder.setBackgroundColor(R.id.fl_root, mContext.getResources().getColor(R.color.task_normal));
        }
    }
}
