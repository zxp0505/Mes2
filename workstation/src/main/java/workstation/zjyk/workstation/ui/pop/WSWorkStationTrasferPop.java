package workstation.zjyk.workstation.ui.pop;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import workstation.zjyk.workstation.R;
import workstation.zjyk.workstation.ui.pop.basePop.WSBasePopupWindow;
import workstation.zjyk.workstation.util.dialog.callback.WSDialogCallBackTwo;

/**
 * Created by zjgz on 2017/12/27.
 */

public class WSWorkStationTrasferPop extends WSBasePopupWindow {
    @BindView(R.id.tv_station_trasfer)
    TextView tvStationTrasfer;
    @BindView(R.id.tv_cancle)
    TextView tvCancle;
    WSDialogCallBackTwo wsDialogCallBackTwo;
    @BindView(R.id.ll_root)
    LinearLayout llRoot;

    public WSWorkStationTrasferPop(Activity context, WSDialogCallBackTwo wsDialogCallBackTwo) {
        super(context, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        this.wsDialogCallBackTwo = wsDialogCallBackTwo;
    }

    @Override
    public View getPopupView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.popu_workerstation_trasfer, null);
        return view;
    }

    @Override
    public View getAnimaView() {
        return llRoot;
    }

    @Override
    protected Animation getShowAnimation() {
        return null;
    }

    @Override
    public Animator getShowAnimator() {
        Animator animator = AnimatorInflater.loadAnimator(mContext, R.animator.animator_scale_entry);
        if (mAnimaView != null) {
            animator.setTarget(mAnimaView);
        }
        return animator;
    }

    @Override
    public Animator getExitAnimator() {
        Animator animator = AnimatorInflater.loadAnimator(mContext, R.animator.animator_scale_exit);
        if (mAnimaView != null) {
            animator.setTarget(mAnimaView);
        }
        return animator;
    }

    @Override
    protected View getClickToDismissView() {
        return tvCancle;
    }

    @OnClick(R.id.tv_station_trasfer)
    public void onViewClicked() {
        //转移工位
        if (wsDialogCallBackTwo != null) {
            wsDialogCallBackTwo.OnPositiveClick("");
        }
        dismiss();
    }
}
