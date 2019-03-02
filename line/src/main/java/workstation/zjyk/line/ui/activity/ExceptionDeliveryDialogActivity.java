package workstation.zjyk.line.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import workstation.zjyk.line.R;
import workstation.zjyk.line.modle.bean.MaterielVo;
import workstation.zjyk.line.modle.bean.Wrap;
import workstation.zjyk.line.ui.adapter.AdapterDelivery;
import workstation.zjyk.line.ui.adapter.AdapterDeliveryException;
import workstation.zjyk.line.ui.present.DeliveryExceptionPresent;
import workstation.zjyk.line.ui.present.DeliveryPresent;
import workstation.zjyk.line.ui.views.DeliveryExceptionView;
import workstation.zjyk.line.ui.views.DeliveryView;
import workstation.zjyk.line.util.Constants;
import workstation.zjyk.line.util.DialogCallBackTwo;
import workstation.zjyk.line.util.dialog.DialogUtils;

/**
 * 异常投递托盘
 * Created by zjgz on 2017/11/6.
 */

public class ExceptionDeliveryDialogActivity extends BaseActivity implements DeliveryExceptionView {
    @BindView(R.id.tv_tray_number)
    TextView tvTrayNumber;
//    @BindView(R.id.tv_targt_station)
//    TextView tvTargtStation;
    @BindView(R.id.tv_order_product_number)
    TextView tvOrderProductNumber;
    @BindView(R.id.view_line_one)
    View viewLineOne;
    @BindView(R.id.tv_materail_detail)
    TextView tvMaterailDetail;
    @BindView(R.id.recycyleview)
    RecyclerView recycyleview;
    @BindView(R.id.tv_yes)
    TextView tvYes;
    @BindView(R.id.tv_cancle)
    TextView tvCancle;
    @BindView(R.id.tv_print)
    TextView tvPrint;
    private DeliveryExceptionPresent mDeliveryPresent;
    private Wrap wrap;
    private List<MaterielVo> materiels;
    private AdapterDeliveryException mAdapterDeliveryException;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initVeiw();
        initData();
    }

    private void initData() {
    }

    private void initVeiw() {
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            wrap = extras.getParcelable("data");
            mDeliveryPresent = new DeliveryExceptionPresent(this);
            tvTrayNumber.setText("托盘编号: " + wrap.getCode());
//            tvTargtStation.setText("目标工位: " + wrap.getWorkStationName());
            tvOrderProductNumber.setText("订单生产编号:" + wrap.getOrderId());
            materiels = wrap.getMateriels();
        }

        mAdapterDeliveryException = new AdapterDeliveryException();
        recycyleview.setLayoutManager(new LinearLayoutManager(this));
        recycyleview.setAdapter(mAdapterDeliveryException);
        if (materiels != null) {
            mAdapterDeliveryException.setNewData(materiels);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_delivery_exception;
    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @OnClick({R.id.tv_yes, R.id.tv_cancle, R.id.tv_print, R.id.iv_close, R.id.tv_hand_barcode})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_yes:
                Map<String, String> params = new HashMap<>();
                params.put("code", wrap.getCode());
                mDeliveryPresent.requestDelivery(params);
                break;
            case R.id.iv_close:
            case R.id.tv_cancle:
                finish();
                break;
            case R.id.tv_print:
                //打印
                Map<String, String> param_print = new HashMap<>();
                param_print.put("recordId", wrap.getRecordId());
//                param_print.put("code", wrap.getCode());
                mDeliveryPresent.printDeliveryExceptionInfo(param_print);
                break;
            case R.id.tv_hand_barcode:
                showEntryBarcode();
                break;
        }
    }

    private void showEntryBarcode() {
        DialogUtils.showEntryBarcodeDialog(this, getString(R.string.text_hand_entry_barcode), new DialogCallBackTwo() {
            @Override
            public void OnPositiveClick(Object obj) {
                if (obj != null) {
                    String barcode = (String) obj;
                    onScanResult(barcode);
                }

            }

            @Override
            public void OnNegativeClick() {

            }
        });
    }

    @Override
    public void showNetData(Object o) {
        super.showNetData(o);
        if (o != null) {
            Intent intent = new Intent();
            intent.putExtra("code", wrap.getCode());
            intent.putExtra(Constants.RESULT_KEY, 0);
            setResult(3, intent);
            finish();
        }
    }

    @Override
    public void onScanResult(String scanResult) {
        super.onScanResult(scanResult);
//        if (currentPrintStatus) {
        Map<String, String> params = new HashMap<>();
        params.put("barCode", scanResult);//控制清单编码
        params.put("code", wrap.getCode());//托盘码
        mDeliveryPresent.scanDeliveryException(params);
//        } else {
//            ToastUtil.showCenterShort("请先打印", true);
//        }
    }

    private boolean currentPrintStatus = false;//打印成功的话 再扫凭证

    @Override
    public void showPrintResult(boolean result) {
        currentPrintStatus = result;
    }

    @Override
    public void showDeliveryResult(boolean result) {
        if (result) {
            finish();
        }
    }
}
