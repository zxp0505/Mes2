package com.zjyk.quality.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zjyk.quality.R;
import com.zjyk.quality.modle.bean.QAPerson;

import cn.com.ethank.mylibrary.resourcelibrary.glide.ImageLoader;

/**
 * Created by zjgz on 2017/11/22.
 */

public class QAAdapterWorkerPhoto extends BaseQuickAdapter<QAPerson, BaseViewHolder> {
    public QAAdapterWorkerPhoto() {
        super(R.layout.layout_worker_photo);
    }

    @Override
    protected void convert(BaseViewHolder holder, QAPerson item) {
        item.setPosition(holder.getAdapterPosition());
        ImageLoader.loadImage(mContext, item.getUserPic(), R.drawable.ic_main_user, holder.getView(R.id.iv_worker_photo));
        holder.addOnClickListener(R.id.iv_worker_photo);
        if (item.isSelect()) {
            holder.setVisible(R.id.iv_select_status, true);
        } else {
            holder.setVisible(R.id.iv_select_status, false);
        }
    }

    public void removeItem(int position) {
        getData().remove(position);
        notifyItemRemoved(position);
        if (position != getData().size()) {
            notifyItemRangeChanged(position, getData().size() - position);
        }
    }
}
