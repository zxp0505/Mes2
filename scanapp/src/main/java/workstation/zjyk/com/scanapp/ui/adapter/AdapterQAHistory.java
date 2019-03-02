package workstation.zjyk.com.scanapp.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.text.SimpleDateFormat;
import java.util.Date;

import workstation.zjyk.com.scanapp.R;
import workstation.zjyk.com.scanapp.modle.bean.QualityHandledRecordVO;

public class AdapterQAHistory extends BaseQuickAdapter<QualityHandledRecordVO, BaseViewHolder> {
    public AdapterQAHistory() {
        super(R.layout.item_qa_history);
    }

    @Override
    protected void convert(BaseViewHolder holder, QualityHandledRecordVO item) {
        holder.setText(R.id.tv_barcode, item.getBarCode());
        holder.setText(R.id.tv_model, item.getModelTypeName());
        holder.setText(R.id.tv_product_number, item.getSerialNumber());
        Date planDate = item.getPlanDate();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(planDate);
        holder.setText(R.id.tv_date, dateString);
        holder.setText(R.id.tv_opreation_name, item.getHandlerPerson());
        if (item.getHandleType() != null) {
            holder.setText(R.id.tv_opreation_method, item.getHandleType().getKey());
        }
        Date handledDate = item.getHandledDate();
        String handleDateStr = formatter.format(handledDate);
        holder.setText(R.id.tv_handle_date, handleDateStr);
        holder.addOnClickListener(R.id.fl_item);


    }
}
