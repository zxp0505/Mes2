package workstation.zjyk.workstation.ui.views;

import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.wang.avi.Indicator;

import java.util.ArrayList;

/**
 * Created by Jack Wang on 2016/8/5.
 */

public class MyCustomIndicator extends Indicator {


    public static final float SCALE = 1.0f;
    public int count = 13;
    private float[] scaleFloats;
    private int[] delays;
    int delay_space = 120;

    //scale x ,y
//    private float[] scaleFloats = new float[]{SCALE,
//            SCALE,
//            SCALE,
//            SCALE,
//            SCALE
//            , SCALE
//            , SCALE, SCALE};
    public MyCustomIndicator() {
        initScaleFloats();

    }

    private void initScaleFloats() {
        delays = new int[count];
        scaleFloats = new float[count];
        for (int i = 0; i < count; i++) {
            scaleFloats[i] = SCALE;
            delays[i] = (i + 1) * 120;
        }
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        float circleSpacing = 6;
//        float radius = (Math.min(getWidth(), getHeight()) - circleSpacing * 2) / 12;
        float radius = 5;
        float x = getWidth() / 2 - (radius * 2 + circleSpacing);
        float y = getHeight() / 2;
        for (int i = 0; i < count; i++) {
            canvas.save();
            float translateX = x + (radius * 2) * i + circleSpacing * i;
            canvas.translate(translateX, y);
            canvas.scale(scaleFloats[i], scaleFloats[i]);
            canvas.drawCircle(0, 0, radius, paint);
            canvas.restore();
        }
    }

    @Override
    public ArrayList<ValueAnimator> onCreateAnimators() {
        ArrayList<ValueAnimator> animators = new ArrayList<>();
//        int[] delays = new int[]{120, 240, 360, 480, 600, 720, 840, 960};
        for (int i = 0; i < count; i++) {
            final int index = i;

            ValueAnimator scaleAnim = ValueAnimator.ofFloat(1, 0.3f, 1);

            scaleAnim.setDuration(1000);
            scaleAnim.setRepeatCount(-1);
            scaleAnim.setStartDelay(delays[i]);

            addUpdateListener(scaleAnim, new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    scaleFloats[index] = (float) animation.getAnimatedValue();
                    postInvalidate();

                }
            });
            animators.add(scaleAnim);
        }
        return animators;
    }


}
