package workstation.zjyk.com.scanapp.ui;

import android.Manifest;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.permissions.RxPermissions;
import com.luck.picture.lib.tools.PictureFileUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.ethank.mylibrary.resourcelibrary.common_util.JsonUtil;
import cn.com.ethank.mylibrary.resourcelibrary.intent.StartIntentUtils;
import cn.com.ethank.mylibrary.resourcelibrary.keyboard.KeyboardUtils;
import cn.com.ethank.mylibrary.resourcelibrary.toast.ToastUtil;
import fr.ganfra.materialspinner.MaterialSpinner;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import workstation.zjyk.com.scanapp.R;
import workstation.zjyk.com.scanapp.modle.bean.QualityHandleDetailVO;
import workstation.zjyk.com.scanapp.modle.bean.ScanQualityHandleVO;
import workstation.zjyk.com.scanapp.modle.bean.ScanResultItem;
import workstation.zjyk.com.scanapp.modle.bean.ScanTrayInfoMaterielVo;
import workstation.zjyk.com.scanapp.modle.bean.ScanTrayInfoVo;
import workstation.zjyk.com.scanapp.modle.bean.ScanTrayInfoWIPVo;
import workstation.zjyk.com.scanapp.modle.bean.enumpackage.ExceptionHandleTypeEnum;
import workstation.zjyk.com.scanapp.modle.bean.enumpackage.ExceptionTypeEnum;
import workstation.zjyk.com.scanapp.modle.bean.enumpackage.ScanTrayBindTypeEnum;
import workstation.zjyk.com.scanapp.modle.bean.enumpackage.ScanYesOrNoEnum;
import workstation.zjyk.com.scanapp.ui.adapter.AdapterHistoryPhoto;
import workstation.zjyk.com.scanapp.ui.adapter.GridImageAdapter;
import workstation.zjyk.com.scanapp.ui.adapter.ScanResultAdapter;
import workstation.zjyk.com.scanapp.ui.application.ScanBaseApplication;
import workstation.zjyk.com.scanapp.ui.layoutmanager.FullyGridLayoutManager;
import workstation.zjyk.com.scanapp.ui.present.ScanMainPresent;
import workstation.zjyk.com.scanapp.ui.views.ScanMainView;
import workstation.zjyk.com.scanapp.util.BundleParams;
import workstation.zjyk.com.scanapp.util.ScanUserManager;
import workstation.zjyk.com.scanapp.util.dialog.CommitConfirmDialog;
import workstation.zjyk.com.scanapp.util.dialog.ScanDialogUtils;
import workstation.zjyk.com.scanapp.util.dialog.callback.ScanDialogCallBackTwo;

/**
 * Created by zjgz on 2018/1/22.
 */

public class ScanResultAboutPictrueActivity extends ScanBaseActivity<ScanMainPresent> implements ScanMainView {
    @BindView(R.id.recycleview)
    RecyclerView recycleview;
    @BindView(R.id.recycleview_order)
    RecyclerView recycleviewOrder;
    @BindView(R.id.ll_root)
    LinearLayout llRoot;

    @BindView(R.id.tv_tray_title)
    TextView tvTrayTitle;
    @BindView(R.id.tv_order_title)
    TextView tvOrderTitle;
    @BindView(R.id.recycle_photo)
    RecyclerView recyclePhoto;
    @BindView(R.id.tv_upload)
    TextView tvUpload;
    @BindView(R.id.spinner_one)
    MaterialSpinner spinnerOne;
    @BindView(R.id.spinner_two)
    MaterialSpinner spinnerTwo;
    @BindView(R.id.ll_upload)
    LinearLayout llUpload;
    @BindView(R.id.edit_reason)
    EditText editReason;
    @BindView(R.id.nestScroll)
    NestedScrollView nestScroll;
    @BindView(R.id.checkbox_supply)
    CheckBox checkboxSupply;
    @BindView(R.id.recycle_photo_detail)
    RecyclerView recyclePhotoDetail;
    @BindView(R.id.tv_except_type)
    TextView tvExceptType;
    @BindView(R.id.tv_opreation_method)
    TextView tvOpreationMethod;
    @BindView(R.id.ll_detail)
    LinearLayout llDetail;
    @BindView(R.id.tv_opreation_name)
    TextView tvOpreationName;
    @BindView(R.id.tv_feed_record)
    TextView tvFeedRecord;
    @BindView(R.id.tv_except_reason)
    TextView tvExceptReason;
    private ScanResultAdapter mScanResultAdapter;
    private String scanResult;
    StringBuilder stringBuilder = new StringBuilder();
    private ScanResultAdapter mScanResultOrderAdapter;
    private GridImageAdapter adapterPhoto;
    private List<ExceptionTypeEnum> exceptTypes;
    private List<ExceptionHandleTypeEnum> handleTypes;
    private boolean toDetail;
    private String detailRecordId;
    private String handleTime;
    private CommitConfirmDialog mCommitConfirmDialog;


    private void initView() {
        Intent intent = getIntent();
        if (intent != null) {
            scanResult = intent.getStringExtra("scanResult");
            toDetail = intent.getBooleanExtra(BundleParams.TODETAIL, false);
            detailRecordId = intent.getStringExtra(BundleParams.RECOEDID);
            handleTime = intent.getStringExtra(BundleParams.HANDLE_TIME);
            flag = intent.getIntExtra(BundleParams.FLAG, 0);
        }

        if (toDetail) {
            tvTitle.setText("详情");
            if (flag == 1) {
                ivScan.setVisibility(View.VISIBLE);
                ivBack.setVisibility(View.GONE);
            } else {
                ivScan.setVisibility(View.GONE);
                ivBack.setVisibility(View.VISIBLE);
            }
        } else {
            tvTitle.setText("扫描结果");
            ivScan.setVisibility(View.VISIBLE);
            ivBack.setVisibility(View.GONE);
        }


        mScanResultAdapter = new ScanResultAdapter();
        recycleview.setLayoutManager(new LinearLayoutManager(this));
        recycleview.setAdapter(mScanResultAdapter);
        mScanResultOrderAdapter = new ScanResultAdapter();

//        recycleviewOrder.setHasFixedSize(true);
        recycleviewOrder.setLayoutManager(new LinearLayoutManager(this));
        recycleviewOrder.setAdapter(mScanResultOrderAdapter);
//        recycleviewOrder.setNestedScrollingEnabled(false);

        mScanResultAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                List<ScanResultItem> data = adapter.getData();
                ScanResultItem scanResultItem = data.get(position);
                if (scanResultItem.isShowLoad() && currentTrayInfoVo != null) {
                    if (getString(R.string.materail_name).equals(scanResultItem.getName())) {
                        //物料
                        List<ScanTrayInfoMaterielVo> materielList = currentTrayInfoVo.getMaterielList();
                        if (materielList != null && materielList.size() > 0) {
                            Bundle bundle = new Bundle();
                            bundle.putParcelableArrayList("data", (ArrayList<? extends Parcelable>) materielList);
                            StartIntentUtils.startIntentUtils(ScanResultAboutPictrueActivity.this, MaterailDetailActivity.class, bundle);
                        }

                    } else if (getString(R.string.wip_name).equals(scanResultItem.getName())) {
                        //在制品
                        List<ScanTrayInfoWIPVo> wipList = currentTrayInfoVo.getWipList();
                        if (wipList != null && wipList.size() > 0) {
                            Bundle bundle = new Bundle();
                            bundle.putParcelableArrayList("data", (ArrayList<? extends Parcelable>) wipList);
                            StartIntentUtils.startIntentUtils(ScanResultAboutPictrueActivity.this, WipDetailActivity.class, bundle);
                        }

                    }
                }
            }
        });
        if (toDetail) {
            if (!TextUtils.isEmpty(detailRecordId)) {
                requestDetail(detailRecordId);
            }
            llUpload.setVisibility(View.GONE);
            llDetail.setVisibility(View.VISIBLE);

        } else {
            llDetail.setVisibility(View.GONE);
            llUpload.setVisibility(View.VISIBLE);
            if (!TextUtils.isEmpty(scanResult)) {
                initData(scanResult);
            }

            initPickPhoto();
            setUploadBg();
            initSpinner();
        }


//        KeyboardUtils.hideTwo(editReason);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            nestScroll.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    if (getCurrentFocus() == editReason) {
//                        KeyboardUtils.hideTwo(editReason);
                    }
                }
            });
        }
        editReason.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                KeyboardUtils.show(editReason);
            }
        });
    }

    private void checkEditReason() {
        int width, height;
        Point p = new Point();
        getWindowManager().getDefaultDisplay().getSize(p);
        width = p.x;
        height = p.y;

        Rect rect = new Rect(0, 0, width, height);
        if (editReason.getLocalVisibleRect(rect)) {

            System.out.println("控件在屏幕显示范围内");
        } else {
            System.out.println("控件已滑出屏幕…………");
        }
    }

    @Override
    protected void onPause() {
        KeyboardUtils.hideKeyboardOnly(editReason);
        super.onPause();
    }

    private void initSpinner() {
//        String[] ITEMS = {"Item 1", "Item 2", "Item 3", "Item 4", "Item 5", "Item 6"};
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ITEMS);


        exceptTypes = new ArrayList<>();
        handleTypes = new ArrayList<>();
        List<String> exceptTypesStr = new ArrayList<>();
        List<String> handleTypesString = new ArrayList<>();

        exceptTypes.add(ExceptionTypeEnum.MATERIEL);
        exceptTypes.add(ExceptionTypeEnum.PROCESS);
        exceptTypes.add(ExceptionTypeEnum.OTHER);

        exceptTypesStr.add(ExceptionTypeEnum.MATERIEL.getKey());
        exceptTypesStr.add(ExceptionTypeEnum.PROCESS.getKey());
        exceptTypesStr.add(ExceptionTypeEnum.OTHER.getKey());

        handleTypes.add(ExceptionHandleTypeEnum.BACK);
        handleTypes.add(ExceptionHandleTypeEnum.DETAIN);
        handleTypes.add(ExceptionHandleTypeEnum.RETURN);
        handleTypes.add(ExceptionHandleTypeEnum.SCRAP);
        handleTypes.add(ExceptionHandleTypeEnum.COMPROMISE);
        handleTypes.add(ExceptionHandleTypeEnum.CHOOSE);
        handleTypes.add(ExceptionHandleTypeEnum.REDO);
        handleTypes.add(ExceptionHandleTypeEnum.REWORK);

        handleTypesString.add(ExceptionHandleTypeEnum.BACK.getKey());
        handleTypesString.add(ExceptionHandleTypeEnum.DETAIN.getKey());
        handleTypesString.add(ExceptionHandleTypeEnum.RETURN.getKey());
        handleTypesString.add(ExceptionHandleTypeEnum.SCRAP.getKey());
        handleTypesString.add(ExceptionHandleTypeEnum.COMPROMISE.getKey());
        handleTypesString.add(ExceptionHandleTypeEnum.CHOOSE.getKey());
        handleTypesString.add(ExceptionHandleTypeEnum.REDO.getKey());
        handleTypesString.add(ExceptionHandleTypeEnum.REWORK.getKey());

        ArrayAdapter<String> exceptTypeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, exceptTypesStr);
        exceptTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerOne.setAdapter(exceptTypeAdapter);

        ArrayAdapter<String> exceptHandleTypeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, handleTypesString);
        exceptHandleTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTwo.setAdapter(exceptHandleTypeAdapter);


    }

    private void initPickPhoto() {
        FullyGridLayoutManager manager = new FullyGridLayoutManager(this, 4, GridLayoutManager.VERTICAL, false);
        recyclePhoto.setLayoutManager(manager);
        adapterPhoto = new GridImageAdapter(this, onAddPicClickListener);
        adapterPhoto.setList(selectList);
        adapterPhoto.setSelectMax(6);
        recyclePhoto.setAdapter(adapterPhoto);
        adapterPhoto.setOnItemClickListener(new GridImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                if (selectList.size() > 0) {
                    LocalMedia media = selectList.get(position);
                    String pictureType = media.getPictureType();
                    int mediaType = PictureMimeType.pictureToVideo(pictureType);
                    switch (mediaType) {
                        case 1:
                            // 预览图片 可自定长按保存路径
                            //PictureSelector.create(MainActivity.this).themeStyle(themeId).externalPicturePreview(position, "/custom_file", selectList);
                            PictureSelector.create(ScanResultAboutPictrueActivity.this).themeStyle(R.style.picture_default_style).openExternalPreview(position, selectList);
                            break;
                        case 2:
                            // 预览视频
//                            PictureSelector.create(MainActivity.this).externalPictureVideo(media.getPath());
                            break;
                        case 3:
                            // 预览音频
//                            PictureSelector.create(MainActivity.this).externalPictureAudio(media.getPath());
                            break;
                    }
                }
            }
        });

        RxPermissions permissions = new RxPermissions(this);
        permissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(new Observer<Boolean>() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onNext(Boolean aBoolean) {
                if (aBoolean) {
                    PictureFileUtils.deleteCacheDirFile(ScanResultAboutPictrueActivity.this);
                } else {
                    Toast.makeText(ScanResultAboutPictrueActivity.this,
                            getString(R.string.picture_jurisdiction), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onComplete() {
            }
        });
    }

    private GridImageAdapter.onAddPicClickListener onAddPicClickListener = new GridImageAdapter.onAddPicClickListener() {
        @Override
        public void onAddPicClick() {
            // 进入相册 以下是例子：不需要的api可以不写
            PictureSelector.create(ScanResultAboutPictrueActivity.this)
                    .openGallery(PictureMimeType.ofImage())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                    .theme(R.style.custom_style)// 主题样式设置 具体参考 values/styles   用法：R.style.picture.white.style
                    .maxSelectNum(6)// 最大图片选择数量
                    .minSelectNum(1)// 最小选择数量
                    .imageSpanCount(4)// 每行显示个数
                    .selectionMode(PictureConfig.MULTIPLE)// 多选 or 单选
                    .previewImage(true)// 是否可预览图片
//                    .previewVideo(cb_preview_video.isChecked())// 是否可预览视频
//                    .enablePreviewAudio(cb_preview_audio.isChecked()) // 是否可播放音频
                    .isCamera(true)// 是否显示拍照按钮
                    .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
                    //.imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
                    //.setOutputCameraPath("/CustomPath")// 自定义拍照保存路径
                    .enableCrop(false)// 是否裁剪
                    .compress(true)// 是否压缩
                    .synOrAsy(true)//同步true或异步false 压缩 默认同步
                    //.compressSavePath(getPath())//压缩图片保存地址
                    //.sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
                    .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
//                    .withAspectRatio(aspect_ratio_x, aspect_ratio_y)// 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
//                    .hideBottomControls(cb_hide.isChecked() ? false : true)// 是否显示uCrop工具栏，默认不显示
//                    .isGif(cb_isGif.isChecked())// 是否显示gif图片
//                    .freeStyleCropEnabled(true)// 裁剪框是否可拖拽
//                    .circleDimmedLayer(cb_crop_circular.isChecked())// 是否圆形裁剪
//                    .showCropFrame(cb_showCropFrame.isChecked())// 是否显示裁剪矩形边框 圆形裁剪时建议设为false
//                    .showCropGrid(cb_showCropGrid.isChecked())// 是否显示裁剪矩形网格 圆形裁剪时建议设为false
                    .openClickSound(false)// 是否开启点击声音
                    .selectionMedia(selectList)// 是否传入已选图片
                    //.isDragFrame(false)// 是否可拖动裁剪框(固定)
//                        .videoMaxSecond(15)
//                        .videoMinSecond(10)
                    //.previewEggs(false)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
                    //.cropCompressQuality(90)// 裁剪压缩质量 默认100
                    .minimumCompressSize(100)// 小于100kb的图片不压缩
                    //.cropWH()// 裁剪宽高比，设置如果大于图片本身宽高则无效
                    //.rotateEnabled(true) // 裁剪是否可旋转图片
                    //.scaleEnabled(true)// 裁剪是否可放大缩小图片
                    //.videoQuality()// 视频录制质量 0 or 1
                    //.videoSecond()//显示多少秒以内的视频or音频也可适用
                    //.recordVideoSecond()//录制视频秒数 默认60s
                    .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
        }

        @Override
        public void onReducePicClick() {
            setUploadBg();
        }

    };
    private List<LocalMedia> selectList = new ArrayList<>();
    private List<LocalMedia> selectDetailList = new ArrayList<>();

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    selectList = PictureSelector.obtainMultipleResult(data);
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                    // 如果裁剪并压缩了，已取压缩路径为准，因为是先裁剪后压缩的
                    for (LocalMedia media : selectList) {
                        Log.i("图片-----》", media.getPath());
                    }
                    adapterPhoto.setList(selectList);
                    adapterPhoto.notifyDataSetChanged();
                    setUploadBg();
                    break;
            }
        }
    }


    private void initData(String scanCode) {
        Map<String, String> params = new HashMap<>();
        params.put("barCode", scanCode);
        currentPresent.requestByScancode(params);
    }

    private void requestDetail(String recordId) {
        Map<String, String> params = new HashMap<>();
        params.put("recordId", recordId);
        currentPresent.requestDetail(params);
    }

    @Override
    protected void creatPresent() {
        currentPresent = new ScanMainPresent();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_scan_result_about_pic;
    }

    @Override
    protected View getLoadingTargetView() {
        return mFlContent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initView();
    }


    ScanTrayInfoVo currentTrayInfoVo;
    Map<String, String> downPicMap = new HashMap<>();
    QualityHandleDetailVO qualityHandleDetailVO;

    @Override
    public void showDetail(QualityHandleDetailVO qualityHandleDetailVO) {
        if (qualityHandleDetailVO != null) {
            this.qualityHandleDetailVO = qualityHandleDetailVO;
            ScanTrayInfoVo trayInfoVo = new ScanTrayInfoVo();
            trayInfoVo.setBinder(qualityHandleDetailVO.getBinder());
            trayInfoVo.setBinderCode(qualityHandleDetailVO.getBinderCode());
            trayInfoVo.setBinderName(qualityHandleDetailVO.getBinderName());
            trayInfoVo.setBindingType(qualityHandleDetailVO.getBindingType());
            trayInfoVo.setMaterielList(qualityHandleDetailVO.getMaterielList());
            trayInfoVo.setProductModel(qualityHandleDetailVO.getProductModel());
            trayInfoVo.setProductModelType(qualityHandleDetailVO.getProductModelType());
            trayInfoVo.setProductNumber(qualityHandleDetailVO.getProductNumber());
            trayInfoVo.setReceiver(qualityHandleDetailVO.getReceiver());
            trayInfoVo.setSerialNumber(qualityHandleDetailVO.getSerialNumber());
            trayInfoVo.setTrayCode("");
            trayInfoVo.setWipList(null);
            trayInfoVo.setQualitOutDate(qualityHandleDetailVO.getQualitOutDate());
            trayInfoVo.setHandledDate(handleTime);
            scanResult(trayInfoVo);

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
            List<String> address = qualityHandleDetailVO.getAddress();
            AdapterHistoryPhoto mAdapterHistoryPhoto = new AdapterHistoryPhoto();
            recyclePhotoDetail.setLayoutManager(new GridLayoutManager(this, 3));
            recyclePhotoDetail.setAdapter(mAdapterHistoryPhoto);
            mAdapterHistoryPhoto.setNewData(address);
            mAdapterHistoryPhoto.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                @Override
                public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                    List<String> data = mAdapterHistoryPhoto.getData();
                    switch (view.getId()) {
                        case R.id.iv_item:
                            String requestUrl = data.get(position);
                            if (downPicMap.containsKey(requestUrl)) {
                                showDownPicResult(requestUrl, downPicMap.get(requestUrl));
                            } else {
                                currentPresent.downLoadPic(requestUrl, ScanResultAboutPictrueActivity.this);
                            }
                            break;
                    }
                }
            });
        }
    }

    @Override
    public void showDownPicResult(String requestUrl, String picPath) {
        downPicMap.put(requestUrl, picPath);
        selectDetailList.clear();
        LocalMedia localMedia = new LocalMedia();
        localMedia.setCut(false);
        localMedia.setCompressed(false);
        localMedia.setPath(picPath);
        selectDetailList.add(localMedia);
        PictureSelector.create(ScanResultAboutPictrueActivity.this).themeStyle(R.style.picture_default_style).openExternalPreview(0, selectDetailList);
    }


    @Override
    public void scanResult(ScanTrayInfoVo trayInfoVo) {
        this.currentTrayInfoVo = trayInfoVo;
        List<ScanResultItem> datas = new ArrayList<>();
        List<ScanResultItem> orderdatas = new ArrayList<>();
        if (trayInfoVo != null) {
            String binderName = trayInfoVo.getBinderName();//输出人员姓名
            String binderCode = trayInfoVo.getBinderCode();//输出人员工号
            String qualitOutDate = trayInfoVo.getQualitOutDate();//异常输出时间
            String handledDate = trayInfoVo.getHandledDate();//处理时间
            String trayCode = trayInfoVo.getTrayCode();//托盤编号
            ScanTrayBindTypeEnum bindingType = trayInfoVo.getBindingType();//托盘绑定类型
            String binder = trayInfoVo.getBinder();//输出工位名称 来源
            String receiver = trayInfoVo.getReceiver();//目的工位名称
            String serialNumber = trayInfoVo.getSerialNumber();//订单生产编号
            String productNumber = trayInfoVo.getProductNumber();//生产序号
            String productModel = trayInfoVo.getProductModel();//产品货号
            String productModelType = trayInfoVo.getProductModelType();//产品型号
            List<ScanTrayInfoMaterielVo> materielList = trayInfoVo.getMaterielList();//物料集合
            List<ScanTrayInfoWIPVo> wipList = trayInfoVo.getWipList();//在制品集合
            if (!TextUtils.isEmpty(binderName)) {
                ScanResultItem item = new ScanResultItem();
                item.setName("输出人员姓名: ");
                item.setValue(binderName);
                item.setShowLoad(false);
                datas.add(item);
            }

            if (!TextUtils.isEmpty(binderCode)) {
                ScanResultItem item = new ScanResultItem();
                item.setName("输出人员工号: ");
                item.setValue(binderCode);
                item.setShowLoad(false);
                datas.add(item);
            }

            if (!TextUtils.isEmpty(qualitOutDate)) {
                ScanResultItem item = new ScanResultItem();
                item.setName("输出时间: ");
                item.setValue(qualitOutDate);
                item.setShowLoad(false);
                datas.add(item);
            }

            if (!TextUtils.isEmpty(handledDate)) {
                ScanResultItem item = new ScanResultItem();
                item.setName("处理时间: ");
                item.setValue(handledDate);
                item.setShowLoad(false);
                datas.add(item);
            }

            if (!TextUtils.isEmpty(trayCode)) {
                ScanResultItem item = new ScanResultItem();
                item.setName("托盘编号: ");
                item.setValue(trayCode);
                item.setShowLoad(false);
                datas.add(item);
            }
            if (bindingType != null) {
                ScanResultItem item = new ScanResultItem();
                item.setName("托盘类型: ");
                item.setValue(bindingType.getKey());
                item.setShowLoad(false);
                datas.add(item);
            }
            if (materielList != null && materielList.size() > 0) {
                ScanResultItem item = new ScanResultItem();
                item.setName(getString(R.string.materail_name));
                item.setValue("");
                item.setShowLoad(true);
                datas.add(item);
            }
            if (wipList != null && wipList.size() > 0) {
                ScanResultItem item = new ScanResultItem();
                item.setName(getString(R.string.wip_name));
                item.setValue("");
                item.setShowLoad(true);
                datas.add(item);
            }
            if (!TextUtils.isEmpty(binder)) {
                ScanResultItem item = new ScanResultItem();
                item.setName("托盘来源: ");
                item.setValue(binder);
                item.setShowLoad(false);
                datas.add(item);
            }
            if (!TextUtils.isEmpty(receiver)) {
                ScanResultItem item = new ScanResultItem();
                item.setName("目标工位: ");
                item.setValue(receiver);
                item.setShowLoad(false);
                datas.add(item);
            }


            if (!TextUtils.isEmpty(serialNumber)) {
                ScanResultItem item = new ScanResultItem();
                item.setName("订单生产编号: ");
                item.setValue(serialNumber);
                item.setShowLoad(false);
                orderdatas.add(item);
            }


            if (!TextUtils.isEmpty(productNumber)) {
                ScanResultItem item = new ScanResultItem();
                item.setName("生产序号: ");
                item.setValue(productNumber);
                item.setShowLoad(false);
                orderdatas.add(item);
            }
            if (!TextUtils.isEmpty(productModel)) {
                ScanResultItem item = new ScanResultItem();
                item.setName("产品货号: ");
                item.setValue(productModel);
                item.setShowLoad(false);
                orderdatas.add(item);
            }
            if (!TextUtils.isEmpty(productModelType)) {
                ScanResultItem item = new ScanResultItem();
                item.setName("产品型号: ");
                item.setValue(productModelType);
                item.setShowLoad(false);
                orderdatas.add(item);
            }
            setUploadImageUi(true);

        } else {


        }
        if (datas.size() > 0) {
            tvTrayTitle.setVisibility(View.VISIBLE);
        }
        if (orderdatas.size() > 0) {
            tvOrderTitle.setVisibility(View.VISIBLE);
        }
        mScanResultAdapter.setNewData(datas);
        mScanResultOrderAdapter.setNewData(orderdatas);


    }

    private void setUploadImageUi(boolean scanResult) {
        if (!toDetail) {
            if (scanResult) {
                llUpload.setVisibility(View.VISIBLE);
            } else {
                llUpload.setVisibility(View.GONE);
            }
        }

    }

    @Override
    public void scanResult(String message, Throwable throwable) {
        setUploadImageUi(false);
//        if (!TextUtils.isEmpty(message)) {
//            StringBuilder stringBuilder = new StringBuilder();
//            stringBuilder.append("扫描条码: ");
//            stringBuilder.append(scanResult);
//            stringBuilder.append("\n\n" + message);
//            ScanDialogUtils.showOneTipDialog(this, stringBuilder.toString(), "确定", new ScanDialogCallBackTwo() {
//                @Override
//                public void OnPositiveClick(Object obj) {
//
//                }
//
//                @Override
//                public void OnNegativeClick() {
//
//                }
//            });
//        }
    }

    @Override
    public void upLoadResult(boolean result) {
        if (result) {
            selectList.clear();
            adapterPhoto.setList(selectList);
            adapterPhoto.notifyDataSetChanged();
            setUploadBg();
            ToastUtil.showInfoCenterShort("提交成功");
            toScanActivity();
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitApp();
        }
        return super.onKeyDown(keyCode, event);
    }

    private void exitApp() {
        ScanBaseApplication.getInstance().exit();
    }

    private void setUploadBg() {
        if (selectList.size() > 0) {
            tvUpload.setBackgroundResource(R.drawable.shape_upload_bg);
            tvUpload.setTag(true);
        } else {
            tvUpload.setBackgroundResource(R.drawable.shape_upload_bg_un);
            tvUpload.setTag(false);
        }
    }

    @OnClick({R.id.tv_upload, R.id.tv_refuse})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_upload:
                KeyboardUtils.hideKeyboardOnly(editReason);
                boolean tag = (boolean) tvUpload.getTag();
                if (tag) {
                    showCommitConfirmDialog();
//                    uploadImages();
                } else {
                    ToastUtil.showInfoCenterShort("请选择上传的图片");
                }
                break;
            case R.id.tv_refuse:
                KeyboardUtils.hideKeyboardOnly(editReason);
                refuseRequest();
                break;
        }
    }

    private void commitExcept() {
        int spinnerOneSelectedItemPosition = spinnerOne.getSelectedItemPosition();
        int spinnerTwoSelectedItemPosition = spinnerTwo.getSelectedItemPosition();

        Map<String, String> params = new HashMap<>();
        currentPresent.commitExcept(params);
    }

    @Override
    public void commitExceptResult(boolean result) {
        if (result) {
            ToastUtil.showInfoCenterShort("提交成功");
        }
    }

    private void uploadImages() {

        Map<String, String> params = new HashMap<>();
        params.put("barCode", scanResult);
        List<String> datas = new ArrayList<>();
        for (int i = 0; i < selectList.size(); i++) {
            datas.add(selectList.get(i).getPath());
        }
        int spinnerOneSelectedItemPosition = spinnerOne.getSelectedItemPosition();
        int spinnerTwoSelectedItemPosition = spinnerTwo.getSelectedItemPosition();
        ScanQualityHandleVO scanQualityHandleVO = new ScanQualityHandleVO();
        scanQualityHandleVO.setExceptionType(exceptTypes.get(spinnerOneSelectedItemPosition));
        scanQualityHandleVO.setHandleType(handleTypes.get(spinnerTwoSelectedItemPosition));
        scanQualityHandleVO.setPersonId(ScanUserManager.getInstance().getPersonId());
        if (checkboxSupply.isChecked()) {
            scanQualityHandleVO.setNeedSupply(ScanYesOrNoEnum.YES);
        } else {
            scanQualityHandleVO.setNeedSupply(ScanYesOrNoEnum.NO);
        }
        scanQualityHandleVO.setReason(editReason.getText().toString());
        params.put("qualityHandleVOInfo", JsonUtil.toJson(scanQualityHandleVO));
        currentPresent.uploadImages(params, datas);
    }

    private void refuseRequest() {
        Map<String, String> params = new HashMap<>();
        params.put("trayCode", scanResult);
        currentPresent.refuseRequest(params);
    }

    @Override
    public void refuseResult(boolean result) {
        if (result) {
            ToastUtil.showInfoCenterShort("拒绝成功");
            toScanActivity();
        }
    }

    private void showCommitConfirmDialog() {
        if (mCommitConfirmDialog == null) {
            mCommitConfirmDialog = ScanDialogUtils.showCommitConfirmDialog(this, "", new ScanDialogCallBackTwo() {
                @Override
                public void OnPositiveClick(Object obj) {
                    uploadImages();
                }

                @Override
                public void OnNegativeClick() {

                }
            });
        }
        QualityHandleDetailVO qualityHandleDetailVO = new QualityHandleDetailVO();
        if (ScanUserManager.getInstance().getCurrentPerson() != null) {
            qualityHandleDetailVO.setPersonName(ScanUserManager.getInstance().getCurrentPerson().getUserName());
        }
        qualityHandleDetailVO.setExceptionType(exceptTypes.get(spinnerOne.getSelectedItemPosition()));
        qualityHandleDetailVO.setHandleType(handleTypes.get(spinnerTwo.getSelectedItemPosition()));
        qualityHandleDetailVO.setReason(editReason.getText().toString());
        mCommitConfirmDialog.showDatas(mScanResultAdapter.getData(), mScanResultOrderAdapter.getData(), selectList, qualityHandleDetailVO);
        mCommitConfirmDialog.show();
    }
}
