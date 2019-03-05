package workstation.zjyk.com.scanapp.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.ethank.mylibrary.resourcelibrary.toast.ToastUtil;
import workstation.zjyk.com.scanapp.R;
import workstation.zjyk.com.scanapp.ui.present.ScanMainPresent;
import workstation.zjyk.com.scanapp.ui.webview.IWebPageView;
import workstation.zjyk.com.scanapp.ui.webview.MyJavascriptInterface;
import workstation.zjyk.com.scanapp.ui.webview.MyWebChromeClient;
import workstation.zjyk.com.scanapp.ui.webview.MyWebViewClient;
import workstation.zjyk.com.scanapp.util.CheckNetwork;

/**
 * Created by zhangxiaoping on 2019/3/2 15:07
 */
public class ScanH5Activity extends ScanBaseActivity<ScanMainPresent> implements IWebPageView {
    @BindView(R.id.webview)
    WebView webView;
    @BindView(R.id.bt_refresh)
    Button btRefresh;
    private String currentUrl;

    @Override
    protected void creatPresent() {

    }

    @Override
    public void initOnCreate() {
        super.initOnCreate();
        tvTitle.setVisibility(View.VISIBLE);
        tvTitle.setText("报警内容");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_h5;
    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        currentUrl = intent.getStringExtra("url");
        initWebView();
        webView.loadUrl(currentUrl);
//        webView.loadUrl("http://www.baidu.com");
    }

    @SuppressLint({"SetJavaScriptEnabled", "AddJavascriptInterface"})
    private void initWebView() {
        WebSettings ws = webView.getSettings();
        // 网页内容的宽度是否可大于WebView控件的宽度
        ws.setLoadWithOverviewMode(false);
        // 保存表单数据
        ws.setSaveFormData(true);
        // 是否应该支持使用其屏幕缩放控件和手势缩放
        ws.setSupportZoom(true);
        ws.setBuiltInZoomControls(true);
        ws.setDisplayZoomControls(false);
        // 启动应用缓存
        ws.setAppCacheEnabled(true);
        // 设置缓存模式
        ws.setCacheMode(WebSettings.LOAD_DEFAULT);
        // setDefaultZoom  api19被弃用
        // 设置此属性，可任意比例缩放。
        ws.setUseWideViewPort(true);
        // 不缩放
        webView.setInitialScale(100);
        // 告诉WebView启用JavaScript执行。默认的是false。
        ws.setJavaScriptEnabled(true);
        //  页面加载好以后，再放开图片
        ws.setBlockNetworkImage(false);
        // 使用localStorage则必须打开
        ws.setDomStorageEnabled(true);
        // 排版适应屏幕
        ws.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        // WebView是否新窗口打开(加了后可能打不开网页)
//        ws.setSupportMultipleWindows(true);

        // webview从5.0开始默认不允许混合模式,https中不能加载http资源,需要设置开启。
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ws.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        /** 设置字体默认缩放大小(改变网页字体大小,setTextSize  api14被弃用)*/
        ws.setTextZoom(100);

        MyWebChromeClient mWebChromeClient = new MyWebChromeClient(this);
        webView.setWebChromeClient(mWebChromeClient);
        // 与js交互
        webView.addJavascriptInterface(new MyJavascriptInterface(this), "injectedObject");
        webView.setWebViewClient(new MyWebViewClient(this));
        webView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
//                return handleLongImage();
                return false;
            }
        });

    }

    @Override
    public void hindProgressBar() {

    }

    @Override
    public void showWebView() {

    }

    @Override
    public void hindWebView() {

    }

    @Override
    public void startProgress(int newProgress) {

    }

    @Override
    public void addImageClickListener() {

    }

    @Override
    public void fullViewAddView(View view) {

    }

    @Override
    public void showVideoFullView() {

    }

    @Override
    public void hindVideoFullView() {

    }

    @Override
    public void onReceivedError(int errorCode, String description) {
        ToastUtil.showInfoCenterShort(""+description);
        btRefresh.setVisibility(View.VISIBLE);
        if (!CheckNetwork.isNetworkConnected(this)) {

        }
    }

    public ViewGroup getVideoFullView() {
        return null;
    }

    @OnClick(R.id.bt_refresh)
    public void onViewClicked() {
        webView.loadUrl(currentUrl);
        btRefresh.setVisibility(View.GONE);
    }
}
