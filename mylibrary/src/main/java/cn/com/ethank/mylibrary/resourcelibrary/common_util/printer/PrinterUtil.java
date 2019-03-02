package cn.com.ethank.mylibrary.resourcelibrary.common_util.printer;

import android.Manifest;
import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.print.PrintHelper;

import com.tbruyelle.rxpermissions2.RxPermissions;

import cn.com.ethank.mylibrary.resourcelibrary.common_util.ThreadFactoryUtils;

import java.lang.ref.WeakReference;

import cn.com.ethank.mylibrary.resourcelibrary.toast.ToastUtil;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by zjgz on 2017/11/9.
 */

public class PrinterUtil {
    public static class Holder {
        public static final PrinterUtil printer = new PrinterUtil();
    }

    WeakReference<Activity> weakReference = null;

    public static PrinterUtil getInstance() {
        return Holder.printer;
    }

    private static final int PRINTER_BITMAP = 0;
    Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case PRINTER_BITMAP:
                    Bundle bundle = msg.getData();
                    Bitmap bitmap = bundle.getParcelable("bitmap");
                    doPhotoPrint(bitmap);
                    break;
            }

        }
    };

    public void printerPhoto(final BitmapBean bean, Activity activity) {
//        BitmapBean bean = new BitmapBean();
        //第一行
        bean.setFirst_line_text("托盘编号:8888888888");
        bean.setWidth_start_first(20);
        bean.setHeight_start_first(20);
        bean.setTextSize_firstline(20);
        //第二行
        bean.setSecond_line_text("目标工位:光学001");
        bean.setWidth_start_second(20);
        bean.setHeight_start_second(70);
        bean.setTextSize_secondline(20);

        bean.setSecond_line_two_text("订单生产编号:S0000001");
        bean.setWidth_start_second_two(200);
        bean.setHeight_start_second_two(70);
        bean.setTextSize_secondline_two(20);
//第三行
        bean.setThree_line_text("物料清单");
        bean.setWidth_start_three(200);
        bean.setHeight_start_three(120);
        bean.setTextSize_threeline(20);
        //第四行
        bean.setFour_line_text("物料名称");
        bean.setWidth_start_four(50);
        bean.setHeight_start_four(170);
        bean.setTextSize_fourline(20);

        bean.setFour_line_two_text("数量");
        bean.setWidth_start_four_two(250);
        bean.setHeight_start_four_two(170);
        bean.setTextSize_fourline_two(20);

        bean.setTextSize_mater(18);
        bean.setWidth_mater_first(40);
        bean.setWidth_mater_second(280);


        weakReference = new WeakReference<Activity>(activity);
        ThreadFactoryUtils.THREAD_POOL_EXECUTOR.execute(new Runnable() {
            @Override
            public void run() {
                new CreatBitmap(bean, new CreateBitmapCallBack() {
                    @Override
                    public void onScuess(Bitmap bitmap) {
                        Message obtain = Message.obtain();
                        obtain.what = PRINTER_BITMAP;
                        Bundle bundle = new Bundle();
                        bundle.putParcelable("bitmap", bitmap);
                        obtain.setData(bundle);
                        handler.sendMessageAtFrontOfQueue(obtain);
                    }

                    @Override
                    public void onFail() {

                    }
                }).onDrawImage();
            }
        });
    }

    private void doPhotoPrint(Bitmap bitmap) {
        Activity activity = weakReference.get();
        if (activity != null) {
            //申请sd卡权限
            RxPermissions rxPermissions = new RxPermissions(activity);
            rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS).subscribe(new Observer<Boolean>() {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onNext(Boolean aBoolean) {
                    if (aBoolean) {
                        //实例化类
                        PrintHelper photoPrinter = new PrintHelper(activity);
                        photoPrinter.setScaleMode(PrintHelper.SCALE_MODE_FIT);//设置填充的类型，填充的类型指的是在A4纸上打印时的填充类型，两种模式
                        photoPrinter.printBitmap("jpgTestPrint", bitmap, new PrintHelper.OnPrintFinishCallback() {
                            @Override
                            public void onFinish() {
                                ToastUtil.showCenterShort("打印结束",true);
                            }
                        });
                    }
                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onComplete() {

                }
            });
//            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE,
//                            Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS},
//                    4);


        }
    }
}
