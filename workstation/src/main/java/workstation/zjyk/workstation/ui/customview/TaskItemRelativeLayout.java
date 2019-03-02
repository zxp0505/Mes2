package workstation.zjyk.workstation.ui.customview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import workstation.zjyk.workstation.R;

public class TaskItemRelativeLayout extends RelativeLayout {
    public static final String TAG = "TaskItemRelativeLayout";

    public TaskItemRelativeLayout(Context context) {
        super(context);
    }

    public TaskItemRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TaskItemRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    Disposable disposable;
    int flag = 0; //0 红 1 白

    public void startUpdateBg() {
//        if (disposable != null && !disposable.isDisposed()) {
//            return;
//        }
//        Observable.interval(800, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Long>() {
//            @Override
//            public void onSubscribe(Disposable d) {
//                disposable = d;
//            }
//
//            @Override
//            public void onNext(Long aLong) {
//                if (flag == 0) {
//                    setBackgroundResource(R.drawable.shape_task_item_bg);
//                    flag = 1;
//                } else {
//                    setBackgroundResource(R.drawable.shape_task_item_red_bg);
//                    flag = 0;
//                }
//
//            }
//
//            @Override
//            public void onError(Throwable e) {
//
//            }
//
//            @Override
//            public void onComplete() {
//
//            }
//        });

    }

    public void stopUpdateBg() {
//        if (disposable != null && !disposable.isDisposed()) {
//            disposable.dispose();
//            disposable = null;
//            setBackgroundResource(R.drawable.shape_task_item_bg);
//        }
    }

    @Override
    protected void onAttachedToWindow() {
//        Log.e(TAG, "onAttachedToWindow" + "----标识：" + this.getId());
        super.onAttachedToWindow();
    }

    @Override
    protected void onDetachedFromWindow() {
//        Log.e(TAG, "onDetachedFromWindow" + "----标识：" + this.getId());
        super.onDetachedFromWindow();
        stopUpdateBg();
    }

    @Override
    protected void onVisibilityChanged(@NonNull View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
//        Log.e(TAG, "visibility---" + visibility + "");
        if (visibility != VISIBLE) {
            stopUpdateBg();
        }

    }

}
