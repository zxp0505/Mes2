package workstation.zjyk.line.ui.pop;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.TextView;

import workstation.zjyk.line.R;
import workstation.zjyk.line.ui.pop.basePop.BasePopupWindow;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zjgz on 2017/10/27.
 */

public class FeedPop1 extends BasePopupWindow {
    @BindView(R.id.tv_dialog_title)
    TextView tvDialogTitle;
    @BindView(R.id.tv_cancle)
    TextView tvCancle;

    public FeedPop1(Activity context, String station) {
        super(context, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        init(station);
    }

    private void init(String station) {
        tvDialogTitle.setText("您已成功为" + station + "分配了生产物料");
    }

    @Override
    public View getPopupView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.pop_feed_one, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public View getAnimaView() {
        return null;
    }

    @Override
    protected Animation getShowAnimation() {
        return null;
    }

    @Override
    protected View getClickToDismissView() {
        return tvCancle;
    }
}
