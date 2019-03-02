package workstation.zjyk.line.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import workstation.zjyk.line.R;
import workstation.zjyk.line.modle.bean.ExceptionRecordBean;

/**
 * Created by zjgz on 2017/11/10.
 */

public class AdapterExceptionReport extends BaseQuickAdapter<ExceptionRecordBean, BaseViewHolder> {
    public AdapterExceptionReport() {
        super(R.layout.item_exception_report);
    }

    @Override
    protected void convert(BaseViewHolder holder, ExceptionRecordBean item) {
        holder.setText(R.id.tv_exception_number, item.getCode());
        holder.setText(R.id.tv_exception_person, item.getName());
        holder.setText(R.id.tv_exception_date, item.getOutTime());
        holder.setText(R.id.tv_exception_opreat, "打印详情").addOnClickListener(R.id.tv_exception_opreat);
    }
}
