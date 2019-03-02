package workstation.zjyk.line.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.com.ethank.mylibrary.resourcelibrary.utils.UICommonUtil;
import workstation.zjyk.line.R;
import workstation.zjyk.line.modle.bean.OrderDistributeNeedMaterielVo;
import workstation.zjyk.line.modle.bean.OrderMaterielCountVo;
import workstation.zjyk.line.modle.bean.YesOrNoEnum;
import workstation.zjyk.line.util.Constants;

public class AdapterBondDetail extends BaseQuickAdapter<OrderDistributeNeedMaterielVo, BaseViewHolder> {
    public AdapterBondDetail() {
        super(R.layout.item_bond_detail);
    }

    int flag = 0;

    @Override
    protected void convert(BaseViewHolder holder, OrderDistributeNeedMaterielVo item) {

        //dhc  //隐藏收料的一键发料
        holder.setVisible(R.id.tv_one_bond, false);

        if (Constants.isReciver) {
            holder.setVisible(R.id.tv_one_bond, false);
            holder.setVisible(R.id.tv_history, false);
            holder.setVisible(R.id.view_line_three, false);
        }
        int adapterPosition = holder.getAdapterPosition();
        if (adapterPosition == 0) {
            holder.setVisible(R.id.view_line, false);
        } else {
            holder.setVisible(R.id.view_line, true);
        }
        if (flag == 0) {
            holder.setText(R.id.tv_one_bond, mContext.getString(R.string.text_one_bond));
        } else {
            holder.setText(R.id.tv_one_bond, mContext.getString(R.string.text_bonded));
        }

        holder.setText(R.id.tv_produce, item.getOrderProcedureName()).addOnClickListener(R.id.tv_one_bond).addOnClickListener(R.id.tv_history);
        ;
//        holder.setText(R.id.tv_materail_number, "");
//        holder.setText(R.id.tv_materail_name, "");
//        holder.setText(R.id.tv_require_count, "");
//        holder.setText(R.id.tv_recevied_count, "");
//        holder.setText(R.id.tv_bonded_count, "");
//        holder.setText(R.id.tv_to_bond_count, "")

        List<OrderMaterielCountVo> materielList = item.getMaterielList();
        LinearLayout llRight = holder.getView(R.id.ll_right);
        llRight.removeAllViews();
        if (materielList != null && materielList.size() > 0) {
            for (int i = 0; i < materielList.size(); i++) {
                OrderMaterielCountVo orderMaterielCountVo = materielList.get(i);
                View view = LayoutInflater.from(mContext).inflate(R.layout.item_layout_bond_list_materail, null);
                TextView tvMaterailNumber = view.findViewById(R.id.tv_materail_number);
                TextView tvMaterailName = view.findViewById(R.id.tv_materail_name);
                TextView tvRequireCount = view.findViewById(R.id.tv_require_count);
                TextView tvReceviedCount = view.findViewById(R.id.tv_recevied_count);
                TextView tvBondedCount = view.findViewById(R.id.tv_bonded_count);
                TextView tvToBondCount = view.findViewById(R.id.tv_to_bond_count);
                tvMaterailNumber.setText(orderMaterielCountVo.getMaterielModel());
                tvMaterailName.setText(orderMaterielCountVo.getMaterielName());
                tvRequireCount.setText(orderMaterielCountVo.getCount() + "");
                tvReceviedCount.setText(orderMaterielCountVo.getReceiveCount() + "");
                tvBondedCount.setText(orderMaterielCountVo.getDistributeCount() + "");
                tvToBondCount.setText(orderMaterielCountVo.getDistributeMainCount() + "");
                llRight.addView(view);
            }

        }

        YesOrNoEnum taskProcedureStruts = item.getTaskProcedureStruts();
        TextView tvMaterailCondition = holder.getView(R.id.tv_materail_condition);
        if (taskProcedureStruts == YesOrNoEnum.YES) {
            tvMaterailCondition.setText("料齐");
            UICommonUtil.setTextViewDrawableLeft(mContext, R.drawable.ok, tvMaterailCondition);
        } else {
            tvMaterailCondition.setText("欠料");
            UICommonUtil.setTextViewDrawableLeft(mContext, R.drawable.unnormal, tvMaterailCondition);
        }

    }

    public void setCustomDatas(int flag, List<OrderDistributeNeedMaterielVo> datas) {
        this.flag = flag;
        setNewData(datas);
    }
}
