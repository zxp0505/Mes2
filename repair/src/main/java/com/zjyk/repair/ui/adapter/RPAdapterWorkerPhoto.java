package com.zjyk.repair.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import cn.com.ethank.mylibrary.resourcelibrary.glide.ImageLoader;
import com.zjyk.repair.R;
import com.zjyk.repair.modle.bean.RPPerson;

/**
 * Created by zjgz on 2017/11/22.
 */

public class RPAdapterWorkerPhoto extends BaseQuickAdapter<RPPerson, BaseViewHolder> {
    public RPAdapterWorkerPhoto() {
        super(R.layout.layout_worker_photo);
    }

    @Override
    protected void convert(BaseViewHolder holder, RPPerson item) {
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
