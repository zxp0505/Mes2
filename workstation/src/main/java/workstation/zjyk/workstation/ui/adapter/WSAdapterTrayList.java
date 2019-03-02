package workstation.zjyk.workstation.ui.adapter;

import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

import cn.com.ethank.mylibrary.resourcelibrary.utils.UICommonUtil;
import workstation.zjyk.workstation.R;
import workstation.zjyk.workstation.modle.bean.WSLevel0Item;
import workstation.zjyk.workstation.modle.bean.WSLevel1Item;

/**
 * Created by zjgz on 2017/12/11.
 */

public class WSAdapterTrayList extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {
    public static final int TYPE_LEVEL_0 = 0;
    public static final int TYPE_LEVEL_1 = 1;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     */
    public WSAdapterTrayList() {//List<MultiItemEntity> data
        super(null);
        addItemType(TYPE_LEVEL_0, R.layout.item_expandable_lv0);
        addItemType(TYPE_LEVEL_1, R.layout.item_expandable_lv1);
    }

    @Override
    protected void convert(BaseViewHolder holder, MultiItemEntity item) {
        switch (holder.getItemViewType()) {
            case TYPE_LEVEL_0:
                WSLevel0Item level0Item = (WSLevel0Item) item;
                holder.setText(R.id.tv_tray_name, level0Item.title);
                holder.setText(R.id.tv_count, level0Item.count);
                if(level0Item.isExpanded()){
                    UICommonUtil.setTextViewDrawableLeft(mContext, R.drawable.close_list_tree, holder.getView(R.id.tv_tray_name));
                }else{
                    UICommonUtil.setTextViewDrawableLeft(mContext, R.drawable.open_list_tree, holder.getView(R.id.tv_tray_name));

                }
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = holder.getAdapterPosition();
                        Log.d(TAG, "Level 0 item pos: " + pos);
                        if (level0Item.isExpanded()) {
                            collapse(pos);
                        } else {
                            expand(pos);
                            moveToPosition(pos);
                        }
                    }
                });
                break;
            case TYPE_LEVEL_1:
                WSLevel1Item level1Item = (WSLevel1Item) item;
                int singleLineTextlenth =16;
                if(level1Item.title.length()>singleLineTextlenth){
                    UICommonUtil.setLayoutParamsDynamic(level1Item.title.length(),mContext.getResources().getDimensionPixelOffset(R.dimen.y_design_70px),singleLineTextlenth,holder.getView(R.id.ll_item));
                }
                holder.setText(R.id.tv_materail_name, level1Item.title);
                holder.setText(R.id.tv_materail_count, level1Item.count);
                break;
        }
    }

    private void moveToPosition(int n) {
        //先从RecyclerView的LayoutManager中获取第一项和最后一项的Position
        int firstItem = ((LinearLayoutManager) getRecyclerView().getLayoutManager()).findFirstVisibleItemPosition();
        int lastItem = ((LinearLayoutManager) getRecyclerView().getLayoutManager()).findLastVisibleItemPosition();
        //然后区分情况
        if (n <= firstItem) {
            //当要置顶的项在当前显示的第一个项的前面时
            getRecyclerView().scrollToPosition(n);
        } else if (n <= lastItem) {
            //当要置顶的项已经在屏幕上显示时
            int top = getRecyclerView().getChildAt(n - firstItem).getTop();
            getRecyclerView().scrollBy(0, top);
        } else {
            //当要置顶的项在当前显示的最后一项的后面时
            getRecyclerView().scrollToPosition(n);
        }

    }
}
