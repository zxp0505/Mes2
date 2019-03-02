package workstation.zjyk.workstation.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import workstation.zjyk.workstation.R;
import workstation.zjyk.workstation.modle.bean.WSDailyWorkHistory;
import workstation.zjyk.workstation.modle.bean.WSWorkReportRecordVO;

/**
 * Created by zjgz on 2017/12/12.
 */

public class WSAdapterUserRecordDetail extends BaseQuickAdapter<WSWorkReportRecordVO, BaseViewHolder> {
    public WSAdapterUserRecordDetail() {
        super(R.layout.item_user_record_detail);
    }

    @Override
    protected void convert(BaseViewHolder holder, WSWorkReportRecordVO item) {
        holder.setText(R.id.tv_order_number, item.getSerialNumber());
        holder.setText(R.id.tv_product_number, item.getProductNumber());
        holder.setText(R.id.tv_model, item.getProductModelTypeName());
        holder.setText(R.id.tv_count, item.getCount() + "");
        holder.setText(R.id.tv_report_data, item.getReportRecord());
    }
}
