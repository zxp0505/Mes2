package workstation.zjyk.workstation.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import cn.com.ethank.mylibrary.resourcelibrary.utils.UICommonUtil;
import workstation.zjyk.workstation.R;
import workstation.zjyk.workstation.modle.bean.WSMaterial;
import workstation.zjyk.workstation.modle.bean.WSMyMaterielVo;

/**
 * Created by zjgz on 2017/11/6.
 */

public class WSAdapterReciverMaterail extends BaseQuickAdapter<WSMaterial, BaseViewHolder> {
    int singleLineTextlenth =35;
    public WSAdapterReciverMaterail(int singleLineTextlenth) {
        super(R.layout.item_reciver_materail);
        this.singleLineTextlenth = singleLineTextlenth;
    }

    @Override
    protected void convert(BaseViewHolder holder, WSMaterial item) {
        if(item.getName().length()>singleLineTextlenth){
            UICommonUtil.setLayoutParamsDynamic(item.getName().length(),mContext.getResources().getDimensionPixelOffset(R.dimen.y_design_75px),singleLineTextlenth,holder.getView(R.id.ll_item));
        }
        holder.setText(R.id.tv_name, item.getName());
        holder.setText(R.id.tv_count, item.getCount() + "");
        holder.setText(R.id.tv_materail_number,item.getModel());
    }
}
