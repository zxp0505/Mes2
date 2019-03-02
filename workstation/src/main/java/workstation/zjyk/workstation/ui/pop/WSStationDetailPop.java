package workstation.zjyk.workstation.ui.pop;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import workstation.zjyk.workstation.R;
import workstation.zjyk.workstation.modle.bean.WSWorkStationVo;
import workstation.zjyk.workstation.ui.adapter.WSAdapterStationDetailPopu;
import workstation.zjyk.workstation.ui.pop.basePop.WSBasePopupWindow;
import workstation.zjyk.workstation.util.WSConstants;
import workstation.zjyk.workstation.util.dialog.callback.WSDialogCallBackTwo;

/**
 * Created by zjgz on 2018/3/2.
 */

public class WSStationDetailPop extends WSBasePopupWindow implements WSBasePopupWindow.OnDismissListener {

    @BindView(R.id.recycleview)
    RecyclerView recycleview;
    @BindView(R.id.tv_change_station)
    TextView tvChangeStation;
    private WSAdapterStationDetailPopu mAdapterStationDetailPopu;
    WSDialogCallBackTwo wsDialogCallBackTwo;

    public WSStationDetailPop(Activity context, WSDialogCallBackTwo wsDialogCallBackTwo) {
        super(context, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        this.wsDialogCallBackTwo = wsDialogCallBackTwo;
        initView();
    }

    private void initView() {
        mAdapterStationDetailPopu = new WSAdapterStationDetailPopu();
        recycleview.setLayoutManager(new LinearLayoutManager(mContext));
        recycleview.setAdapter(mAdapterStationDetailPopu);
        setOnDismissListener(this);

    }

    public void setData(WSWorkStationVo workStationVo) {
        List<String> datas = new ArrayList<>();
        if (workStationVo != null) {
            String ipAddress = workStationVo.getIpAddress();
            String posDesc = workStationVo.getPosDesc();
            String procedurName = workStationVo.getProcedurName();
            if (TextUtils.isEmpty(ipAddress)) {
                ipAddress = WSConstants.getClientIp();
            }
            datas.add("ip地址: " + ipAddress);
            if (!TextUtils.isEmpty(posDesc)) {
                datas.add("位置: " + posDesc);
            }
            if (!TextUtils.isEmpty(procedurName)) {
                datas.add("工序: " + procedurName);
            }
        } else {
            String ipAddress = WSConstants.getClientIp();
            datas.add("ip地址: " + ipAddress);
        }

        mAdapterStationDetailPopu.setNewData(datas);

    }

    @Override
    public View getPopupView() {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.popu_station_detail, null);
        return inflate;
    }

    @Override
    public View getAnimaView() {
        return null;
    }

    @Override
    protected Animation getShowAnimation() {
        return null;
    }

    @Override
    protected View getClickToDismissView() {
        return null;
    }

    @Override
    public void onDismiss(String name) {

    }

    @OnClick({R.id.tv_change_station})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_change_station:
                //更换工位
                wsDialogCallBackTwo.OnPositiveClick("");
                break;
        }
    }
}
