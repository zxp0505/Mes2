package workstation.zjyk.line.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import workstation.zjyk.line.R;
import workstation.zjyk.line.modle.bean.LineDistributeHistoryVO;
import workstation.zjyk.line.modle.bean.MaterialVO;
import workstation.zjyk.line.modle.bean.YesOrNoEnum;

public class AdapterBondHistory extends BaseQuickAdapter<LineDistributeHistoryVO, BaseViewHolder> {
    public AdapterBondHistory() {
        super(R.layout.item_bond_history);
    }

    @Override
    protected void convert(BaseViewHolder holder, LineDistributeHistoryVO item) {
        holder.setText(R.id.tv_bond_position, item.getWorkStationName());
        holder.setText(R.id.tv_bond_time, item.getDistributeTime());
        holder.setText(R.id.tv_traycode, item.getTrayCode());

        if (item.getPrintTag() == YesOrNoEnum.YES) {
            holder.setVisible(R.id.tv_print, true);
            holder.addOnClickListener(R.id.tv_print);
        } else {
            holder.setVisible(R.id.tv_print, false);
        }
        List<MaterialVO> materialList = item.getMaterialList();
        LinearLayout llMaterailList = holder.getView(R.id.ll_mateail_list);
        llMaterailList.removeAllViews();
        if (materialList != null && materialList.size() > 0) {
            for (int i = 0; i < materialList.size(); i++) {
                MaterialVO materialVO = materialList.get(i);
                View viewMaterail = LayoutInflater.from(mContext).inflate(R.layout.item_history_record_materail, null);
                TextView tvMaterailNumber = viewMaterail.findViewById(R.id.tv_materail_number);
                TextView tvMaterailName = viewMaterail.findViewById(R.id.tv_materail_name);
                TextView tvCount = viewMaterail.findViewById(R.id.tv_count);
                tvMaterailNumber.setText(materialVO.getModel());
                tvMaterailName.setText(materialVO.getName());
                tvCount.setText(materialVO.getCount() + "");
                llMaterailList.addView(viewMaterail);
            }
        }


    }
}
