package workstation.zjyk.workstation.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import cn.com.ethank.mylibrary.resourcelibrary.utils.UICommonUtil;
import workstation.zjyk.workstation.R;
import workstation.zjyk.workstation.modle.bean.WSMyMaterielVo;
import workstation.zjyk.workstation.modle.bean.WSWip;

/**
 * Created by zjgz on 2017/11/6.
 */

public class WSAdapterReciverMaking extends BaseQuickAdapter<WSWip, BaseViewHolder> {
    public WSAdapterReciverMaking() {
        super(R.layout.item_reciver_making);
    }

    @Override
    protected void convert(BaseViewHolder holder, WSWip item) {
//        UICommonUtil.setLayoutParamsDynamic(item.getName().length(),holder.getView(R.id.ll_item));
        holder.setText(R.id.tv_name, item.getProcedureName());
        holder.setText(R.id.tv_count, item.getCount() + "");
    }
}
