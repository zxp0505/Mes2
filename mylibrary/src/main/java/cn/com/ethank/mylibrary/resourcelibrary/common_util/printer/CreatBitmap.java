package cn.com.ethank.mylibrary.resourcelibrary.common_util.printer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.io.File;
import java.util.List;

import cn.com.ethank.mylibrary.resourcelibrary.application.DefaultApplication;

public class CreatBitmap {
    BitmapBean bitmapBean = null;
    Bitmap bitmap;
    private File file;
    Context context;
    CreateBitmapCallBack serviceCallBack;

    public CreatBitmap(BitmapBean bitmapBean, CreateBitmapCallBack serviceCallBack) {
        this.context = DefaultApplication.getInstance();
        this.bitmapBean = bitmapBean;
        this.serviceCallBack = serviceCallBack;
        this.bitmap = Bitmap.createBitmap(720, 1080, Config.ARGB_8888);
    }

    public void onDrawImage() {
        try {
            if (bitmapBean == null) {
                serviceCallBack.onFail();
                return;
            }
            Canvas canvas = new Canvas(bitmap);
            Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            paint.setColor(Color.BLACK);
            //第一行
            paint.setTextSize(bitmapBean.getTextSize_firstline());
            canvas.drawText(bitmapBean.getFirst_line_text(), bitmapBean.getWidth_start_first(), bitmapBean.getHeight_start_first(), paint);
            //第二行
            paint.setTextSize(bitmapBean.getTextSize_secondline());
            canvas.drawText(bitmapBean.getSecond_line_text(), bitmapBean.getWidth_start_second(), bitmapBean.getHeight_start_second(), paint);
            //第二行
            paint.setTextSize(bitmapBean.getTextSize_secondline_two());
            canvas.drawText(bitmapBean.getSecond_line_two_text(), bitmapBean.getWidth_start_second_two(), bitmapBean.getHeight_start_second_two(), paint);
//第三行
            paint.setTextSize(bitmapBean.getTextSize_threeline());
            canvas.drawText(bitmapBean.getThree_line_text(), bitmapBean.getWidth_start_three(), bitmapBean.getHeight_start_three(), paint);

            //第四行
            paint.setTextSize(bitmapBean.getTextSize_fourline());
            canvas.drawText(bitmapBean.getFour_line_text(), bitmapBean.getWidth_start_four(), bitmapBean.getHeight_start_four(), paint);

            canvas.drawText(bitmapBean.getFour_line_two_text(), bitmapBean.getWidth_start_four_two(), bitmapBean.getHeight_start_four_two(), paint);

            List<BitmapBean.MaterBean> materDatas = bitmapBean.getMaterDatas();
            int startY_mater =bitmapBean.getHeight_start_four_two()+50;
            for (int i = 0; i < materDatas.size(); i++) {
                BitmapBean.MaterBean materBean = materDatas.get(i);
                canvas.drawText(materBean.getName(), bitmapBean.getWidth_mater_first(), startY_mater, paint);
                canvas.drawText(materBean.getNumber()+"", bitmapBean.getWidth_mater_second(), startY_mater, paint);
                startY_mater = startY_mater+50;
            }

            canvas.save(Canvas.ALL_SAVE_FLAG);
            canvas.restore();

            if (file != null && file.exists()) {
                file.delete();
            }

            serviceCallBack.onScuess(bitmap);
        } catch (Exception e) {
            e.printStackTrace();
            serviceCallBack.onFail();
        } finally {
            try {

            } catch (Exception e2) {

            }
        }

    }
}
