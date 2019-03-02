package workstation.zjyk.workstation.ui.customview;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import cn.com.ethank.mylibrary.resourcelibrary.common_util.log.LoggerUtil;
import cn.com.ethank.mylibrary.resourcelibrary.toast.ToastUtil;

/**
 * Created by zjgz on 2018/3/20.
 */

public class MyGridlayoutMManager extends GridLayoutManager {
    public MyGridlayoutMManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public MyGridlayoutMManager(Context context, int spanCount) {
        super(context, spanCount);
    }

    public MyGridlayoutMManager(Context context, int spanCount, int orientation, boolean reverseLayout) {
        super(context, spanCount, orientation, reverseLayout);
    }

    @Override
    public void collectAdjacentPrefetchPositions(int dx, int dy, RecyclerView.State state, LayoutPrefetchRegistry layoutPrefetchRegistry) {
        try {
            super.collectAdjacentPrefetchPositions(dx, dy, state, layoutPrefetchRegistry);
        } catch (IllegalArgumentException e) {
            ToastUtil.showInfoCenterShort("catch exception");
            LoggerUtil.e("catch exception");
        }
    }

}
