package workstation.zjyk.workstation.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import cn.com.ethank.mylibrary.resourcelibrary.glide.ImageLoader;
import cn.com.ethank.mylibrary.resourcelibrary.glide.view.MyImageViewTarget;
import workstation.zjyk.workstation.R;
import workstation.zjyk.workstation.modle.bean.WSPerson;

/**
 * Created by zjgz on 2017/11/22.
 */

public class WSAdapterWorkerPhoto extends BaseQuickAdapter<WSPerson, BaseViewHolder> {
    public WSAdapterWorkerPhoto() {
        super(R.layout.layout_worker_photo);
    }

    @Override
    protected void convert(BaseViewHolder holder, WSPerson item) {
        item.setPosition(holder.getAdapterPosition());
        ImageLoader.loadImage(mContext, item.getUserPic(), R.drawable.ic_main_user, holder.getView(R.id.iv_worker_photo));
        ImageLoader.loadImage(mContext, item.getUserPic(), R.drawable.ic_main_user, new MyImageViewTarget(holder.getView(R.id.iv_worker_photo), 115, 115));

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
