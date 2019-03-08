package workstation.zjyk.com.scanapp.util.dialog;

import android.content.Context;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.luck.picture.lib.entity.LocalMedia;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fr.ganfra.materialspinner.MaterialSpinner;
import workstation.zjyk.com.scanapp.R;
import workstation.zjyk.com.scanapp.modle.bean.QualityHandleDetailVO;
import workstation.zjyk.com.scanapp.modle.bean.ScanResultItem;
import workstation.zjyk.com.scanapp.modle.bean.enumpackage.ScanYesOrNoEnum;
import workstation.zjyk.com.scanapp.ui.adapter.AdapterHistoryPhoto;
import workstation.zjyk.com.scanapp.ui.adapter.ScanResultAdapter;
import workstation.zjyk.com.scanapp.util.dialog.callback.ScanDialogCallBackTwo;


public class CommitConfirmDialog extends ScanCommonDialog {


    ScanDialogCallBackTwo dialogCallBackTwo;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_tray_title)
    TextView tvTrayTitle;
    @BindView(R.id.recycleview)
    RecyclerView recycleview;
    @BindView(R.id.ll_content)
    LinearLayout llContent;
    @BindView(R.id.tv_order_title)
    TextView tvOrderTitle;
    @BindView(R.id.recycleview_order)
    RecyclerView recycleviewOrder;
    @BindView(R.id.recycle_photo_detail)
    RecyclerView recyclePhotoDetail;
    @BindView(R.id.tv_opreation_name)
    TextView tvOpreationName;
    @BindView(R.id.tv_feed_record)
    TextView tvFeedRecord;
    @BindView(R.id.tv_except_type)
    TextView tvExceptType;
    @BindView(R.id.tv_opreation_method)
    TextView tvOpreationMethod;
    @BindView(R.id.tv_except_reason)
    TextView tvExceptReason;
    @BindView(R.id.tv_upload_detail)
    TextView tvUploadDetail;
    @BindView(R.id.tv_refuse_detail)
    TextView tvRefuseDetail;
    @BindView(R.id.ll_detail)
    LinearLayout llDetail;
    @BindView(R.id.recycle_photo)
    RecyclerView recyclePhoto;
    @BindView(R.id.spinner_one)
    MaterialSpinner spinnerOne;
    @BindView(R.id.spinner_two)
    MaterialSpinner spinnerTwo;
    @BindView(R.id.checkbox_supply)
    CheckBox checkboxSupply;
    @BindView(R.id.edit_reason)
    EditText editReason;
    @BindView(R.id.tv_upload)
    TextView tvUpload;
    @BindView(R.id.tv_refuse)
    TextView tvRefuse;
    @BindView(R.id.ll_upload)
    LinearLayout llUpload;
    @BindView(R.id.ll_root)
    LinearLayout llRoot;
    @BindView(R.id.nestScroll)
    NestedScrollView nestScroll;
    private ScanResultAdapter mScanResultAdapter;
    private ScanResultAdapter mScanResultOrderAdapter;
    private AdapterHistoryPhoto mAdapterHistoryPhoto;

    public CommitConfirmDialog(Context context, String title, ScanDialogCallBackTwo dialogCallBackTwo) {
        this(context, title, "确定", dialogCallBackTwo);

    }

    public CommitConfirmDialog(Context context, String title, String buttonText, ScanDialogCallBackTwo dialogCallBackTwo) {
        super(context, R.style.CommonDialog);
        this.dialogCallBackTwo = dialogCallBackTwo;
        initDialogView(context);

    }

    public CommitConfirmDialog(Context context, int themeResId) {
        super(context, themeResId);
        initDialogView(context);
    }

    protected CommitConfirmDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initDialogView(context);
    }

    private void initDialogView(Context context) {
        setContentView(R.layout.dialog_scan_result_about_pic);
        ButterKnife.bind(this);
        mScanResultAdapter = new ScanResultAdapter();
        recycleview.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recycleview.setAdapter(mScanResultAdapter);

        mScanResultOrderAdapter = new ScanResultAdapter();
        recycleviewOrder.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recycleviewOrder.setAdapter(mScanResultOrderAdapter);

        //选择的图片
        mAdapterHistoryPhoto = new AdapterHistoryPhoto();
        recyclePhotoDetail.setLayoutManager(new GridLayoutManager(this.getContext(), 1));
        recyclePhotoDetail.setAdapter(mAdapterHistoryPhoto);


    }

    public void showDatas(List<ScanResultItem> data, List<ScanResultItem> mScanResultOrderAdapterData, List<LocalMedia> selectList, QualityHandleDetailVO qualityHandleDetailVO) {
        mScanResultAdapter.setNewData(data);
        mScanResultOrderAdapter.setNewData(mScanResultOrderAdapterData);
        List<String> photos = new ArrayList<>();
        if (selectList != null) {
            for (int i = 0; i < selectList.size(); i++) {
                photos.add(selectList.get(i).getPath());
            }
        }
        mAdapterHistoryPhoto.setNewData(photos);
        if (qualityHandleDetailVO != null) {
            tvOpreationName.setText("操作人: " + qualityHandleDetailVO.getPersonName());
            String feedStr = (qualityHandleDetailVO.getNeedSupply() != null && qualityHandleDetailVO.getNeedSupply() == ScanYesOrNoEnum.YES) ? "是" : "否";
            tvFeedRecord.setText("是否补料: " + feedStr);
            if (qualityHandleDetailVO.getExceptionType() != null) {
                tvExceptType.setText("异常类型: " + qualityHandleDetailVO.getExceptionType().getKey());
            }

            if (qualityHandleDetailVO.getHandleType() != null) {
                tvOpreationMethod.setText("处理方式: " + qualityHandleDetailVO.getHandleType().getKey());
            }
            String reason = TextUtils.isEmpty(qualityHandleDetailVO.getReason()) ? "无" : qualityHandleDetailVO.getReason();
            tvExceptReason.setText("异常原因: " + reason);
        }
    }



    @OnClick({R.id.tv_upload_detail, R.id.tv_refuse_detail})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_upload_detail:
                if (dialogCallBackTwo != null) {
                    dialogCallBackTwo.OnPositiveClick("");
                }
                break;
            case R.id.tv_refuse_detail:
                if (dialogCallBackTwo != null) {
                    dialogCallBackTwo.OnNegativeClick();
                }
                break;
        }
    }
}
