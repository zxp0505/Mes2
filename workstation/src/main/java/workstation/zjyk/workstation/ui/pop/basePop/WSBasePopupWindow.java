package workstation.zjyk.workstation.ui.pop.basePop;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroupOverlay;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.PopupWindow;

import butterknife.ButterKnife;
import cn.com.ethank.mylibrary.resourcelibrary.common_util.log.LoggerUtil;


/**
 * Created by 大灯泡 on 2016/1/14.
 * 抽象通用popupwindow的父类
 */
public abstract class WSBasePopupWindow implements WSBasePopup {
    private static final String TAG = "WSBasePopupWindow";
    //元素定义
    protected PopupWindow mPopupWindow;
    //popup视图
    protected View mPopupView;
    protected View mAnimaView;
    protected View mDismissView;
    protected Activity mContext;
    //是否自动弹出输入框(default:false)
    private boolean autoShowInputMethod = false;
    private OnDismissListener mOnDismissListener;
    //anima
    protected Animation curExitAnima;
    protected Animator curExitAnimator;
    protected Animation curAnima;
    protected Animator curAnimator;

    protected String name;
    private static final float DEFAULT_DIM = 0.7f;
    //背景变暗时透明度
    protected float mDimValue = DEFAULT_DIM;
    //背景变暗颜色
    @ColorInt
    protected int mDimColor = Color.BLACK;

    public WSBasePopupWindow(Activity context) {
        initView(context, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 0);
    }

    public WSBasePopupWindow(Activity context, int w, int h) {
        initView(context, w, h, 0);
    }

    public WSBasePopupWindow(Activity context, int w, int h, String name) {
        this.name = name;
        initView(context, w, h, 0);
    }

    public WSBasePopupWindow(Activity context, int w, int h, int style) {
        initView(context, w, h, style);
    }

    private void initView(Activity context, int w, int h, int style) {
        mContext = context;

        mPopupView = getPopupView();
        ButterKnife.bind(this, mPopupView);
        mPopupView.setFocusableInTouchMode(true);
        //默认占满全屏
        mPopupWindow = new PopupWindow(mPopupView, w, h);
        //内容进行测量
        mPopupWindow.getContentView().measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        //指定透明背景，back键相关
        mPopupWindow.setBackgroundDrawable(new ColorDrawable());
        mPopupWindow.setFocusable(true);
        mPopupWindow.setOutsideTouchable(true);
        //无需动画
        if (style >= 0) {
            mPopupWindow.setAnimationStyle(style);
        } else {
            mPopupWindow.setAnimationStyle(0);
        }


        //=============================================================为外层的view添加点击事件，并设置点击消失
        mAnimaView = getAnimaView();
        mDismissView = getClickToDismissView();
        if (mDismissView != null) {
            mDismissView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
            if (mAnimaView != null) {
                mAnimaView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
            }
        }
        //=============================================================元素获取
        curAnima = getShowAnimation();
        curAnimator = getShowAnimator();
        curExitAnima = getExitAnimation();
        curExitAnimator = getExitAnimator();
    }

    public void addViewTreeObserver() {
        mPopupView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (isRequireUpdate && mAnchorView != null) {
                    updateToTopAlignRight();
                }
                removeGlobalLayoutListener(mPopupView, this);
            }
        });
    }

    private void updateToTopAlignRight() {
        int popupWidth = mPopupView.getMeasuredWidth();
        int popupHeight = mPopupView.getMeasuredHeight();
        int[] locations = new int[2];
        mAnchorView.getLocationInWindow(locations);
        mPopupWindow.update(mAnchorView, mAnchorView.getMeasuredWidth() - popupWidth, 0, popupWidth, popupHeight);
    }

    private void removeGlobalLayoutListener(View view, ViewTreeObserver.OnGlobalLayoutListener mOnGlobalLayoutListener) {
        if (view != null) {
            if (Build.VERSION.SDK_INT >= 16) {
                view.getViewTreeObserver().removeOnGlobalLayoutListener(mOnGlobalLayoutListener);
            } else {
                view.getViewTreeObserver().removeGlobalOnLayoutListener(mOnGlobalLayoutListener);
            }
        }
    }

    public void setOutSideTouchable(boolean b) {
        mPopupWindow.setOutsideTouchable(b);
    }

    //------------------------------------------抽象-----------------------------------------------

    /**
     * PopupWindow展示出来后，需要执行动画的View.一般为蒙层之上的View
     *
     * @return
     */
    protected abstract Animation getShowAnimation();

    /**
     * 设置一个点击后触发dismiss PopupWindow的View，一般为蒙层
     *
     * @return
     */
    protected abstract View getClickToDismissView();

    /**
     * 设置展示动画View的属性动画
     *
     * @return
     */
    public Animator getShowAnimator() {
        return null;
    }

    /**
     * 设置一个拥有输入功能的View，一般为EditTextView
     *
     * @return
     */
    public View getInputView() {
        return null;
    }

    /**
     * 设置PopupWindow销毁时的退出动画
     *
     * @return
     */
    public Animation getExitAnimation() {
        return null;
    }

    /**
     * 设置PopupWindow销毁时的退出属性动画
     *
     * @return
     */
    public Animator getExitAnimator() {
        return null;
    }

    //------------------------------------------showPopup-----------------------------------------------

    /**
     * 调用此方法时，PopupWindow将会显示在DecorView
     */
    public void showPopupWindow() {
        try {
            tryToShowPopup(0, null);
        } catch (Exception e) {
            Log.e(TAG, "show error");
            e.printStackTrace();
        }
    }

    public void showPopupWindow(int res) {
        try {
            tryToShowPopup(res, null);
        } catch (Exception e) {
            Log.e(TAG, "show error");
            e.printStackTrace();
        }
    }

    public void showPopupWindow(View v) {
        try {
            tryToShowPopup(0, v);
        } catch (Exception e) {
            Log.e(TAG, "show error");
            e.printStackTrace();
        }
    }

    boolean isBackgroundDim;//是否显示背景黑暗

    public void showPopupWindow(boolean isBackgroundDim) {
        this.isBackgroundDim = isBackgroundDim;
        handleBackgroundDim(null);
        showPopupWindow();
    }

    /**
     * 展示popu自定义为位置
     */
    public void showPopuWindowAtlocation(View v, int left, int top) {
        try {
            tryToShowPopupAtLocation(v, left, top);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    boolean isRequireUpdate = false;//是否需要更新
    View mAnchorView = null;

    /**
     * 为了准确的获取popup的大小 通过设置view的监听 获取真实view大小或  然后update
     * addViewTreeObserver
     *
     * @param v
     * @param left
     * @param top
     * @param isRequireUpdate
     */
    public void showPopuWindowAtlocation(View v, int left, int top, boolean isRequireUpdate) {
        this.isRequireUpdate = isRequireUpdate;
        this.mAnchorView = v;
        showPopuWindowAtlocation(v, left, top);
    }

    //------------------------------------------Methods-----------------------------------------------
    private void tryToShowPopup(int res, View v) throws Exception {
        //传递了view
        if (res == 0 && v != null) {
            mPopupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);
        }
        //传递了res
        if (res != 0 && v == null) {
            mPopupWindow.showAtLocation(mContext.findViewById(res), Gravity.CENTER, 0, 0);
        }
        //什么都没传递，取顶级view的id
        if (res == 0 && v == null) {
            mPopupWindow.showAtLocation(mContext.findViewById(android.R.id.content), Gravity.CENTER, 0, 0);
        }
        if (curAnima != null && mAnimaView != null) {
            mAnimaView.clearAnimation();
            mAnimaView.startAnimation(curAnima);
        }
        if (curAnima == null && curAnimator != null && mAnimaView != null) {
            curAnimator.start();
        }
        //自动弹出键盘
        if (autoShowInputMethod && getInputView() != null) {
            getInputView().requestFocus();
            InputMethodUtils.showInputMethod(getInputView(), 150);
        }
        saveCurrentPopu();
    }

    public void showAsDropDown(View view) {
        mPopupWindow.showAsDropDown(view);
    }

    public void showAsDropDown(View view, int xoff, int yoff) {
        mPopupWindow.showAsDropDown(view, xoff, yoff);
    }

    public PopupWindow getmPopupWindow() {
        return mPopupWindow;
    }

    /**
     * 保存当前的popu
     */
    private void saveCurrentPopu() {
//    	VerRoomManager.getInstance().setCurrentPopu(mPopupWindow);//记录当前的popu
    }

    private void tryToShowPopupAtLocation(View v, int left, int top) throws Exception {
        mPopupWindow.showAtLocation(v, Gravity.NO_GRAVITY, left, top);
        if (curAnima != null && mAnimaView != null) {
            mAnimaView.clearAnimation();
            mAnimaView.startAnimation(curAnima);
        }
        if (curAnima == null && curAnimator != null && mAnimaView != null) {
            curAnimator.start();
        }
        //自动弹出键盘
        if (autoShowInputMethod && getInputView() != null) {
            getInputView().requestFocus();
            InputMethodUtils.showInputMethod(getInputView(), 150);
        }
        saveCurrentPopu();
    }

    /**
     * PopupWindow是否需要自适应输入法，为输入法弹出让出区域
     *
     * @param needAdjust <br>
     *                   ture for "SOFT_INPUT_ADJUST_RESIZE" mode<br>
     *                   false for "SOFT_INPUT_ADJUST_NOTHING" mode
     */
    public void setAdjustInputMethod(boolean needAdjust) {
        if (needAdjust) {
            mPopupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        } else {
            mPopupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
        }
    }

    /**
     * 当PopupWindow展示的时候，这个参数决定了是否自动弹出输入法
     * 如果使用这个方法，您必须保证通过 <strong>getInputView()<strong/>得到一个EditTextView
     *
     * @param autoShow
     */
    public void setAutoShowInputMethod(boolean autoShow) {
        this.autoShowInputMethod = autoShow;
        if (autoShow) {
            setAdjustInputMethod(true);
        } else {
            setAdjustInputMethod(false);
        }
    }

    /**
     * 这个参数决定点击返回键是否可以取消掉PopupWindow
     *
     * @param backPressEnable
     */
    public void setBackPressEnable(boolean backPressEnable) {
        if (backPressEnable) {
            mPopupWindow.setBackgroundDrawable(new ColorDrawable());
        } else {
            mPopupWindow.setBackgroundDrawable(null);
        }
    }

    /**
     * 这个方法封装了LayoutInflater.from(context).inflate，方便您设置PopupWindow所用的xml
     *
     * @param resId reference of layout
     * @return root View of the layout
     */
    public View getPopupViewById(int resId) {
        if (resId != 0) {
            return LayoutInflater.from(mContext).inflate(resId, null);
        } else {
            return null;
        }
    }

    protected View findViewById(int id) {
        if (mPopupView != null && id != 0) {
            return mPopupView.findViewById(id);
        }
        return null;
    }

    /**
     * 这个方法用于简化您为View设置OnClickListener事件，多个View将会使用同一个点击事件
     *
     * @param listener
     * @param views
     */
    protected void setViewClickListener(View.OnClickListener listener, View... views) {
        for (View view : views) {
            if (view != null && listener != null) {
                view.setOnClickListener(listener);
            }
        }
    }

    //---------------------------------------------------------------------------------------------

    /**
     * 处理背景变暗
     * https://blog.nex3z.com/2016/12/04/%E5%BC%B9%E5%87%BApopupwindow%E5%90%8E%E8%AE%A9%E8%83%8C%E6%99%AF%E5%8F%98%E6%9A%97%E7%9A%84%E6%96%B9%E6%B3%95/
     */
    private void handleBackgroundDim(ViewGroup dimView) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            if (dimView != null) {
                applyDim(dimView);
            } else {
                if (getContentView() != null) {
                    Activity activity = (Activity) getContentView().getContext();
                    if (activity != null) {
                        applyDim(activity);
                    }
                }
            }
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void applyDim(Activity activity) {
        ViewGroup parent = (ViewGroup) activity.getWindow().getDecorView().getRootView();
        //activity跟布局
//        ViewGroup parent = (ViewGroup) parent1.getChildAt(0);
        Drawable dim = new ColorDrawable(mDimColor);
        dim.setBounds(0, 0, parent.getWidth(), parent.getHeight());
        dim.setAlpha((int) (255 * mDimValue));
        ViewGroupOverlay overlay = parent.getOverlay();
        overlay.add(dim);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void applyDim(ViewGroup dimView) {
        ViewGroup parent = dimView;
        Drawable dim = new ColorDrawable(mDimColor);
        dim.setBounds(0, 0, parent.getWidth(), parent.getHeight());
        dim.setAlpha((int) (255 * mDimValue));
        ViewGroupOverlay overlay = parent.getOverlay();
        overlay.add(dim);
    }

    /**
     * 清除背景变暗
     */
    private void clearBackgroundDim(ViewGroup dimView) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            if (isBackgroundDim) {
                if (dimView != null) {
                    clearDim(dimView);
                } else {
                    if (getContentView() != null) {
                        Activity activity = (Activity) getContentView().getContext();
                        if (activity != null) {
                            clearDim(activity);
                        }
                    }
                }
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void clearDim(Activity activity) {
        ViewGroup parent = (ViewGroup) activity.getWindow().getDecorView().getRootView();
        //activity跟布局
//        ViewGroup parent = (ViewGroup) parent1.getChildAt(0);
        ViewGroupOverlay overlay = parent.getOverlay();
        overlay.clear();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void clearDim(ViewGroup dimView) {
        ViewGroup parent = dimView;
        ViewGroupOverlay overlay = parent.getOverlay();
        overlay.clear();
    }

    /**
     * 获取PopupWindow中加载的view
     *
     * @return
     */
    public View getContentView() {
        if (mPopupWindow != null) {
            return mPopupWindow.getContentView();
        } else {
            return null;
        }
    }

    //------------------------------------------Getter/Setter-----------------------------------------------

    /**
     * PopupWindow是否处于展示状态
     *
     * @return
     */
    public boolean isShowing() {
        return mPopupWindow.isShowing();
    }

    public OnDismissListener getOnDismissListener() {
        return mOnDismissListener;
    }

    public void setOnDismissListener(OnDismissListener onDismissListener) {
        mOnDismissListener = onDismissListener;
        if (mOnDismissListener != null) {
            mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
//                	VerRoomManager.getInstance().setNoPopu();
                    clearBackgroundDim(null);
                    mOnDismissListener.onDismiss(name);
                }
            });
        }
    }

    //------------------------------------------状态控制-----------------------------------------------

    /**
     * 取消一个PopupWindow，如果有退出动画，PopupWindow的消失将会在动画结束后执行
     */
    public void dismiss() {
        try {
            if (curExitAnima != null) {
                curExitAnima.setAnimationListener(mAnimationListener);
                mAnimaView.clearAnimation();
                mAnimaView.startAnimation(curExitAnima);
            } else if (curExitAnimator != null) {
                curExitAnimator.removeListener(mAnimatorListener);
                curExitAnimator.addListener(mAnimatorListener);
                curExitAnimator.start();
            } else {
                mPopupWindow.dismiss();
            }
        } catch (Exception e) {
            Log.d(TAG, "dismiss error");
        }
    }
    //------------------------------------------Anima-----------------------------------------------

    private Animator.AnimatorListener mAnimatorListener = new Animator.AnimatorListener() {
        @Override
        public void onAnimationStart(Animator animation) {

        }

        @Override
        public void onAnimationEnd(Animator animation) {
            mPopupWindow.dismiss();
        }

        @Override
        public void onAnimationCancel(Animator animation) {

        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }
    };

    private Animation.AnimationListener mAnimationListener = new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            mPopupWindow.dismiss();
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    };

    /**
     * 生成TranslateAnimation
     *
     * @param durationMillis 动画显示时间
     * @param start          初始位置
     */
    protected Animation getTranslateAnimation(int start, int end, int durationMillis) {
        Animation translateAnimation = new TranslateAnimation(0, 0, start, end);
        translateAnimation.setDuration(durationMillis);
        translateAnimation.setFillEnabled(true);
        translateAnimation.setFillAfter(true);
        return translateAnimation;
    }

    /**
     * 生成ScaleAnimation
     */
    protected Animation getScaleAnimation(float fromX, float toX, float fromY, float toY, int pivotXType, float pivotXValue, int pivotYType, float pivotYValue) {
        Animation scaleAnimation = new ScaleAnimation(fromX, toX, fromY, toY, pivotXType, pivotXValue, pivotYType,
                pivotYValue);
        scaleAnimation.setDuration(300);
        scaleAnimation.setFillEnabled(true);
        scaleAnimation.setFillAfter(true);
        return scaleAnimation;
    }

    /**
     * 生成自定义ScaleAnimation
     */
    protected Animation getDefaultScaleAnimation() {
        Animation scaleAnimation = new ScaleAnimation(0f, 1f, 0f, 1f, Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(300);
        scaleAnimation.setInterpolator(new AccelerateInterpolator());
        scaleAnimation.setFillEnabled(true);
        scaleAnimation.setFillAfter(true);
        return scaleAnimation;
    }

    /**
     * 生成默认的AlphaAnimation
     */
    protected Animation getDefaultAlphaAnimation() {
        Animation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
        alphaAnimation.setDuration(300);
        alphaAnimation.setInterpolator(new AccelerateInterpolator());
        alphaAnimation.setFillEnabled(true);
        alphaAnimation.setFillAfter(true);
        return alphaAnimation;
    }

    /**
     * 从下方滑动上来
     */
    protected AnimatorSet getDefaultSlideFromBottomAnimationSet() {
        AnimatorSet set = null;
        set = new AnimatorSet();
        if (mAnimaView != null) {
            set.playTogether(ObjectAnimator.ofFloat(mAnimaView, "translationY", 250, 0).setDuration(400),
                    ObjectAnimator.ofFloat(mAnimaView, "alpha", 0.4f, 1).setDuration(250 * 3 / 2));
        }
        return set;
    }

    //------------------------------------------Interface-----------------------------------------------
    public interface OnDismissListener {
        void onDismiss(String name);
    }
}
