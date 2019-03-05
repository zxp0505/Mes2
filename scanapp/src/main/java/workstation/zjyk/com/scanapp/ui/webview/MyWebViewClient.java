package workstation.zjyk.com.scanapp.ui.webview;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import workstation.zjyk.com.scanapp.ui.ScanH5Activity;
import workstation.zjyk.com.scanapp.util.CheckNetwork;
import workstation.zjyk.com.scanapp.util.Tools;

/**
 * Created by jingbin on 2016/11/17.
 * 监听网页链接:
 * - 根据标识:打电话、发短信、发邮件
 * - 进度条的显示
 * - 添加javascript监听
 * - 唤起京东，支付宝，微信原生App
 */
public class MyWebViewClient extends WebViewClient {

    private IWebPageView mIWebPageView;
    private ScanH5Activity mActivity;

    public MyWebViewClient(IWebPageView mIWebPageView) {
        this.mIWebPageView = mIWebPageView;
        mActivity = (ScanH5Activity) mIWebPageView;

    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
//        Log.e("jing", "----url:" + url);
        if (TextUtils.isEmpty(url)) {
            return false;
        }

        if (url.startsWith("http:") || url.startsWith("https:")) {
            // 可能有提示下载Apk文件
            if (url.contains(".apk")) {
                handleOtherwise(mActivity, url);
                return true;
            }
            return false;
        }

        handleOtherwise(mActivity, url);
        return true;
    }


    @Override
    public void onPageFinished(WebView view, String url) {
        if (!CheckNetwork.isNetworkConnected(mActivity)) {
            mIWebPageView.hindProgressBar();
        }
        // html加载完成之后，添加监听图片的点击js函数
        mIWebPageView.addImageClickListener();
        super.onPageFinished(view, url);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onReceivedError(WebView view, int errorCode, String description, String
            failingUrl) {
        super.onReceivedError(view, errorCode, description, failingUrl);
        if (errorCode == 404) {
            //用javascript隐藏系统定义的404页面信息
            String data = "Page NO FOUND！";
            view.loadUrl("javascript:document.body.innerHTML=\"" + data + "\"");
        }else{

        }
    }

    // 视频全屏播放按返回页面被放大的问题
    @Override
    public void onScaleChanged(WebView view, float oldScale, float newScale) {
        super.onScaleChanged(view, oldScale, newScale);
        if (newScale - oldScale > 7) {
            view.setInitialScale((int) (oldScale / newScale * 100)); //异常放大，缩回去。
        }
    }

    /**
     * 网页里可能唤起其他的app
     */
    private void handleOtherwise(Activity activity, String url) {
        String appPackageName = "";
        // 支付宝支付
        if (url.contains("alipays")) {
            appPackageName = "com.eg.android.AlipayGphone";

            // 微信支付
        } else if (url.contains("weixin://wap/pay")) {
            appPackageName = "com.tencent.mm";

            // 京东产品详情
        } else if (url.contains("openapp.jdmobile")) {
            appPackageName = "com.jingdong.app.mall";
        } else {
            startActivity(url);
        }
        if (Tools.isApplicationAvilible(activity, appPackageName)) {
            startActivity(url);
        }
    }

    private void startActivity(String url) {
        try {

            // 用于DeepLink测试
            if (url.startsWith("will://")) {
                Uri uri = Uri.parse(url);
                Log.e("---------scheme", uri.getScheme() + "；host: " + uri.getHost() + "；Id: " + uri.getPathSegments().get(0));
            }

            Intent intent1 = new Intent();
            intent1.setAction("android.intent.action.VIEW");
            Uri uri = Uri.parse(url);
            intent1.setData(uri);
            intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mActivity.startActivity(intent1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
