package workstation.zjyk.com.scanapp.ui.adapter;

import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import cn.com.ethank.mylibrary.resourcelibrary.utils.UICommonUtil;
import workstation.zjyk.com.scanapp.R;

public class AdapterPreviewHistoryPhoto extends BaseQuickAdapter<String, BaseViewHolder> {
    public AdapterPreviewHistoryPhoto() {
        super(R.layout.item_preview_history_photo);
    }

    @Override
    protected void convert(BaseViewHolder holder, String item) {
//        ImageLoader.loadImage(mContext, item, R.drawable.picfail, holder.getView(R.id.iv_item));
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.picfail)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(mContext)
                .load(item)
                .apply(options)
                .into((ImageView) holder.getView(R.id.iv_item));
        holder.addOnClickListener(R.id.iv_item);
        ImageView view = (ImageView) holder.getView(R.id.iv_item);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = UICommonUtil.getScreenHeightPixels(mContext);
//        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, UICommonUtil.getScreenHeightPixels(mContext));
        view.setLayoutParams(layoutParams);
    }
}
