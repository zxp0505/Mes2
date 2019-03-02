package workstation.zjyk.line.ui.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnErrorListener;
import com.github.barteksc.pdfviewer.listener.OnPageErrorListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.github.barteksc.pdfviewer.util.FitPolicy;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.ethank.mylibrary.resourcelibrary.common_util.log.LoggerUtil;
import workstation.zjyk.line.R;

/**
 * pdf展示
 * Created by zjgz on 2017/12/13.
 */

public class PdfReadDialogActivity extends BaseActivity {
    @BindView(R.id.pdfView)
    PDFView pdfView;
    @BindView(R.id.tv_back)
    TextView tvBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_hand_barcode)
    TextView tvHandBarcode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initOnCreate();
    }

    public void initOnCreate() {
        initView();
    }

    private void initView() {
        tvHandBarcode.setVisibility(View.INVISIBLE);
        Intent intent = getIntent();
        String pdfpath = intent.getStringExtra("pdfPath");
        String titleName = intent.getStringExtra("titleName");
        if (!TextUtils.isEmpty(titleName)) {
            tvTitle.setText(titleName);
        } else {
            tvTitle.setText("指导书");
        }
        if (!TextUtils.isEmpty(pdfpath)) {
            pdfView.fromFile(new File(pdfpath)).defaultPage(0)
                    .enableAnnotationRendering(true)
                    .scrollHandle(new DefaultScrollHandle(this))
                    .spacing(10) // in dp
                    .pageFitPolicy(FitPolicy.WIDTH)
                    .onPageError(new OnPageErrorListener() {
                        @Override
                        public void onPageError(int page, Throwable t) {
                            LoggerUtil.e("onPageError：" + t.getMessage());
                        }
                    })
                    .onError(new OnErrorListener() {
                        @Override
                        public void onError(Throwable t) {
                            LoggerUtil.e("onError：" + t.getMessage());
                        }
                    })
                    .load();
        }
//        try {
//            pdfView.fromStream(getAssets().open("test.pdf"))
//                    .defaultPage(0)
//                    .enableAnnotationRendering(true)
//                    .scrollHandle(new DefaultScrollHandle(this))
//                    .spacing(10) // in dp
//                    .pageFitPolicy(FitPolicy.BOTH)
//                    .load();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_pdf_read;
    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }


    @OnClick(R.id.tv_back)
    public void onViewClicked() {
        finish();
    }
}
