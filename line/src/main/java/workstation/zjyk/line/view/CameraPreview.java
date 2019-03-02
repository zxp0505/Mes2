package workstation.zjyk.line.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import cn.com.ethank.mylibrary.resourcelibrary.common_util.FileUtils;

import java.io.IOException;
import java.util.List;

import cn.com.ethank.mylibrary.resourcelibrary.toast.ToastUtil;


public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {
    private static final String TAG = CameraPreview.class.getSimpleName();
    private SurfaceHolder mHolder;
    private Camera mCamera;
    private Context mContext;
    private int screenWidth = 0;
    private int screenHeight = 0;
    private int cameraPosition = 0;
    private boolean isFrontCamera = true;
    private ICameraPreviewListener iCameraPreviewListener;


    public CameraPreview(Context context, ICameraPreviewListener iCameraPreviewListener, boolean isFrontCamera) {
        super(context);
        mContext = context;
        initScreenInfo();
        this.iCameraPreviewListener = iCameraPreviewListener;
        this.isFrontCamera = isFrontCamera;
        if (isFrontCamera) {
            cameraPosition = Camera.CameraInfo.CAMERA_FACING_FRONT;
        } else {
            cameraPosition = Camera.CameraInfo.CAMERA_FACING_BACK;
        }
        // Install a SurfaceHolder.Callback so we get notified when the
        // underlying surface is created and destroyed.
        mHolder = getHolder();
        mHolder.addCallback(this);
        // deprecated setting, but required on Android versions prior to 3.0
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    private void initScreenInfo() {
        screenWidth = getResources().getDisplayMetrics().widthPixels;
        screenHeight = getResources().getDisplayMetrics().heightPixels;
    }


    public void surfaceCreated(SurfaceHolder holder) {
        // The Surface has been created, now tell the camera where to draw the preview.
        try {
            if (mCamera == null) {
                if (isFrontCamera) {
                    cameraPosition = findFrontCamera();
                    if (cameraPosition == -1) {
                        cameraPosition = findBackCamera();
                    }
                } else {
                    cameraPosition = findBackCamera();
                    if (cameraPosition == -1) {
                        cameraPosition = findFrontCamera();
                    }
                }

                mCamera = Camera.open(cameraPosition);
            }
            mCamera.setPreviewDisplay(holder);
//            setCameraDisplayOrientation((Activity) mContext, cameraPosition, mCamera);
            setCamaraParams();
            mCamera.startPreview();
        } catch (Exception e) {
            if (iCameraPreviewListener != null) {
                iCameraPreviewListener.cameraCreateError();
            }
            Log.d(TAG, "Error setting camera preview: " + e.getMessage());
        }
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        // empty. Take care of releasing the Camera preview in your activity.
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
    }

    public void setCameraDisplayOrientation(Activity activity,
                                            int cameraId, Camera camera) {
        Camera.CameraInfo info =
                new Camera.CameraInfo();
        Camera.getCameraInfo(cameraId, info);
        int rotation = activity.getWindowManager().getDefaultDisplay()
                .getRotation();
        int degrees = 90;
        switch (rotation) {
            case Surface.ROTATION_0:
                degrees = 0;
                break;
            case Surface.ROTATION_90:
                degrees = 90;
                break;
            case Surface.ROTATION_180:
                degrees = 180;
                break;
            case Surface.ROTATION_270:
                degrees = 270;
                break;
        }

        int result;
        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            result = (info.orientation + degrees) % 360;
            result = (360 - result) % 360;  // compensate the mirror
        } else {  // back-facing
            result = (info.orientation - degrees + 360) % 360;
        }
        camera.setDisplayOrientation(result);
    }

    public void onPause() {
        releaseCamera();              // release the camera immediately on pause event
    }

    public void onDestory() {

    }

    private void releaseCamera() {
        if (mCamera != null) {
            mCamera.release();        // release the camera for other applications
            mCamera = null;
        }
    }

    public void switchCamera() {
        //切换前后摄像头
        int cameraCount = 0;
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        cameraCount = Camera.getNumberOfCameras();//得到摄像头的个数
        if (cameraCount == 1) {
//            DMG.showNoamlToast("您只有一个摄像头,无法切换");
           ToastUtil.showCenterShort("您只有一个摄像头,无法切换",true);
            return;
        }
        for (int i = 0; i < cameraCount; i++) {
            Camera.getCameraInfo(i, cameraInfo);//得到每一个摄像头的信息
            if (cameraPosition == 0) {
                //现在是后置，变更为前置
                if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {//代表摄像头的方位，CAMERA_FACING_FRONT前置      CAMERA_FACING_BACK后置
                    mCamera.setPreviewCallback(null);
                    mCamera.stopPreview();//停掉原来摄像头的预览
                    mCamera.release();//释放资源
                    mCamera = null;//取消原来摄像头
                    mCamera = Camera.open(i);//打开当前选中的摄像头
                    try {
                        mCamera.setPreviewDisplay(mHolder);//通过surfaceview显示取景画面
                        cameraPosition = 1;
                        setCamaraParams();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        cameraPosition = 0;
                        e.printStackTrace();
                    }
                    mCamera.startPreview();//开始预览
                    break;
                }
            } else {
                //现在是前置， 变更为后置
                if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {//代表摄像头的方位，CAMERA_FACING_FRONT前置      CAMERA_FACING_BACK后置
                    mCamera.setPreviewCallback(null);
                    mCamera.stopPreview();//停掉原来摄像头的预览
                    mCamera.release();//释放资源
                    mCamera = null;//取消原来摄像头
                    mCamera = Camera.open(i);//打开当前选中的摄像头
                    try {
                        mCamera.setPreviewDisplay(mHolder);//通过surfaceview显示取景画面
                        cameraPosition = 0;
                        setCamaraParams();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        cameraPosition = 1;
                        e.printStackTrace();
                    }
                    mCamera.startPreview();//开始预览
                    break;
                }
            }

        }

    }

    public boolean isFrontCamera() {
        return cameraPosition == 1;
    }

    private int findFrontCamera() {
        int cameraCount = 0;
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        cameraCount = Camera.getNumberOfCameras(); // get cameras number

        for (int camIdx = 0; camIdx < cameraCount; camIdx++) {
            Camera.getCameraInfo(camIdx, cameraInfo); // get camerainfo
            if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                // 代表摄像头的方位，目前有定义值两个分别为CAMERA_FACING_FRONT前置和CAMERA_FACING_BACK后置
                return camIdx;
            }
        }
        return -1;
    }

    private int findBackCamera() {
        int cameraCount = 0;
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        cameraCount = Camera.getNumberOfCameras(); // get cameras number

        for (int camIdx = 0; camIdx < cameraCount; camIdx++) {
            Camera.getCameraInfo(camIdx, cameraInfo); // get camerainfo
            if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
                // 代表摄像头的方位，目前有定义值两个分别为CAMERA_FACING_FRONT前置和CAMERA_FACING_BACK后置
                return camIdx;
            }
        }
        return -1;
    }

    private void setCamaraParams() {
        Camera.Parameters parameters = mCamera.getParameters();
            /* 设置预览照片的大小，此处设置为全屏 */
        // WindowManager wm = (WindowManager)
        // getSystemService(Context.WINDOW_SERVICE); // 获取当前屏幕管理器对象
        // Display display = wm.getDefaultDisplay(); // 获取屏幕信息的描述类
        // parameters.setPreviewSize(display.getWidth(),
        // display.getHeight()); // 设置
//		parameters.setPreviewSize(800, 480);
            /* 每秒从摄像头捕获5帧画面， */
//		parameters.setPreviewFrameRate(5);
            /* 设置照片的输出格式:jpg */
        parameters.setPictureFormat(PixelFormat.JPEG);
            /* 照片质量 */
        parameters.set("jpeg-quality", 85);
//        parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);

        List<Camera.Size> supportedPreviewSizes = parameters.getSupportedPreviewSizes();
        Camera.Size size = getBestSize(supportedPreviewSizes);
        parameters.setPreviewSize(size.width, size.height);
//        List<Camera.Size> pictureSizes = parameters.getSupportedPictureSizes();
//        Camera.Size size2 = getCurrentScreenSize(pictureSizes, 1);
//        parameters.setPictureSize(size2.width, size2.height);
        mCamera.setParameters(parameters);
        setCameraDisplayOrientation((Activity) mContext, cameraPosition, mCamera);
        mCamera.startPreview();
        mCamera.cancelAutoFocus();
    }

    private Camera.Size getBestSize(List<Camera.Size> sizes) {
        if (sizes != null && sizes.size() > 0) {
            int width = 0, height = 0;
            if ((sizes.get(0).height > sizes.get(0).width && screenHeight > screenWidth)
                    || (sizes.get(0).height < sizes.get(0).width && screenHeight < screenWidth)) {
                width = screenWidth;
                height = screenHeight;
            } else {
                width = screenHeight;
                height = screenWidth;
            }
            float rate = (float) height / (float) width;
            if (rate > 1) {
                rate = 1.0f / rate;
            }
            for (int i = 0; i < sizes.size(); i++) {
                Camera.Size size = sizes.get(i);
                if (size.width == width && size.height == height) {
                    return size;
                }
            }
            for (int i = 0; i < sizes.size(); i++) {
                Camera.Size size = sizes.get(i);
                if (Math.abs(rate - (float) size.height / (float) size.width) < 0.05f) {
                    return size;
                }
            }
            int w = 0, h = 0;
            Camera.Size ret = sizes.get(0);
            for (int i = 0; i < sizes.size(); i++) {
                Camera.Size size = sizes.get(i);
                if (size.height == (height * size.width / width)) {
                    if (w == 0) {
                        w = size.width;
                        h = size.height;
                        ret = sizes.get(i);
                    } else {
                        if (Math.abs(size.width - width) < Math.abs(w - width)) {
                            w = size.width;
                            h = size.height;
                            ret = sizes.get(i);
                        }
                    }
                }
                if (w != 0) {
                    return ret;
                }
            }
            return sizes.get(0);
        }
        return null;
    }

    public interface ICameraPreviewListener {
        void cameraCreateError();

        void pictureCallback(Bitmap bitmap);
    }


    /**
     * 拍照
     */
    public void doTakePicture() {
        if (mCamera != null) {
            // takePicture()方法需要传入三个监听参数
            // 第一个监听器；当用户按下快门时激发该监听器
            // 第二个监听器；当相机获取原始照片时激发该监听器
            // 第三个监听器；当相机获取JPG照片时激发该监听器
            mCamera.takePicture(mShutterCallback, null, mJpegPictureCallback);
        }
    }

    /*为了实现拍照的快门声音及拍照保存照片需要下面三个回调变量*/
    Camera.ShutterCallback mShutterCallback = new Camera.ShutterCallback()
            //快门按下的回调，在这里我们可以设置类似播放“咔嚓”声之类的操作。默认的就是咔嚓。
    {
        public void onShutter() {
            // TODO Auto-generated method stub
            Log.i(TAG, "myShutterCallback:onShutter...");
        }
    };
    Camera.PictureCallback mRawCallback = new Camera.PictureCallback()
            // 拍摄的未压缩原数据的回调,可以为null
    {

        public void onPictureTaken(byte[] data, Camera camera) {
            // TODO Auto-generated method stub
            Log.i(TAG, "myRawCallback:onPictureTaken...");

        }
    };
    Camera.PictureCallback mJpegPictureCallback = new Camera.PictureCallback()
            //对jpeg图像数据的回调,最重要的一个回调
    {
        public void onPictureTaken(byte[] data, Camera camera) {
            // TODO Auto-generated method stub
            Log.i(TAG, "myJpegCallback:onPictureTaken...");
            Bitmap b = null;
            if (null != data) {
                b = BitmapFactory.decodeByteArray(data, 0, data.length);//data是字节数据，将其解析成位图
                mCamera.stopPreview();
                if (iCameraPreviewListener != null) {
                    b = FileUtils.convertBmp(b);
                    iCameraPreviewListener.pictureCallback(b);
                }
            }

        }
    };

    public void startPreview() {
        if (mCamera == null) {
            return;
        }
        //再次进入预览
        mCamera.startPreview();
    }

}
