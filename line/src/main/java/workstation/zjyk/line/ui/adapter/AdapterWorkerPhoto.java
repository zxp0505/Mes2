package workstation.zjyk.line.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import cn.com.ethank.mylibrary.resourcelibrary.common_util.SharePreferenceKeyUtil;
import cn.com.ethank.mylibrary.resourcelibrary.glide.ImageLoader;
import cn.com.ethank.mylibrary.resourcelibrary.shareutils.SharedPreferencesUitl;
import workstation.zjyk.line.R;
import workstation.zjyk.line.modle.bean.Person;

/**
 * Created by zjgz on 2017/11/22.
 */

public class AdapterWorkerPhoto extends BaseQuickAdapter<Person, BaseViewHolder> {
    public AdapterWorkerPhoto() {
        super(R.layout.layout_worker_photo);
    }

    @Override
    protected void convert(BaseViewHolder holder, Person item) {
        item.setPosition(holder.getAdapterPosition());
        ImageLoader.loadImage(mContext, item.getUserPic(), R.drawable.ic_main_user, holder.getView(R.id.iv_worker_photo));
        holder.addOnClickListener(R.id.iv_worker_photo);
        if (item.isSelect()) {
            holder.setVisible(R.id.iv_select_status, true);
//            SharedPreferencesUitl.saveStringData(SharePreferenceKeyUtil.PERSON_ID, item.getPersonId());
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
