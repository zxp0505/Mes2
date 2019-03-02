package workstation.zjyk.workstation.ui.adapter;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import cn.com.ethank.mylibrary.resourcelibrary.utils.UICommonUtil;
import workstation.zjyk.workstation.R;
import workstation.zjyk.workstation.modle.bean.WSInputWipInfo;
import workstation.zjyk.workstation.modle.bean.WSMaterial;

/**
 * Created by zjgz on 2017/12/8.
 */

public class WSAdapterReviewWork extends BaseQuickAdapter<WSInputWipInfo, BaseViewHolder> {
    public WSAdapterReviewWork() {
        super(R.layout.item_review_workl);
    }

    @Override
    protected void convert(BaseViewHolder holder, WSInputWipInfo item) {
        item.setPosition(holder.getAdapterPosition());
        holder.setText(R.id.tv_produce, item.getProcedureName());
        holder.setText(R.id.tv_remain_count, item.getRemainCount() + "");
        holder.setText(R.id.tv_except_count, item.getTrueCount() + "");
        if (item.isSelect()) {
            holder.setImageResource(R.id.iv_select_status, R.drawable.select_work);
        } else {
            holder.setImageResource(R.id.iv_select_status, R.drawable.un_select_work);
        }
        holder.addOnClickListener(R.id.iv_reduce).addOnClickListener(R.id.iv_add).addOnClickListener(R.id.tv_except_count).addOnClickListener(R.id.iv_select_status);
        EditText editReason = holder.getView(R.id.edit_reason);
        editReason.setText(item.getReason());
        editReason.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                item.setReason(s.toString().trim());
            }
        });
    }
}
