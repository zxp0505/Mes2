package workstation.zjyk.line.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import cn.com.ethank.mylibrary.resourcelibrary.utils.UICommonUtil;
import workstation.zjyk.line.R;
import workstation.zjyk.line.modle.bean.MaterielVo;
import workstation.zjyk.line.modle.bean.WorkStationRequestDetail;
import workstation.zjyk.line.modle.bean.Wrap;

/**
 * Created by zjgz on 2017/10/27.
 */

public class AdapterTrayInfo extends BaseQuickAdapter<MaterielVo, BaseViewHolder> {
    public AdapterTrayInfo() {
        super(R.layout.item_tray_info);
    }

    @Override
    protected void convert(BaseViewHolder holder, MaterielVo item) {
        UICommonUtil.setLayoutParamsDynamic(item.getName().length(), holder.getView(R.id.ll_item));
        holder.setText(R.id.tv_materail_name, item.getName());
        holder.setText(R.id.tv_count_require, item.getCount() + "");
        holder.setText(R.id.tv_materail_number, item.getModel());

    }
}
