package workstation.zjyk.workstation.ui.pop;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import workstation.zjyk.workstation.R;
import workstation.zjyk.workstation.modle.bean.WSTaskDetailItemBean;
import workstation.zjyk.workstation.modle.bean.WSTaskMainInfo;
import workstation.zjyk.workstation.ui.adapter.WSAdapterTaskDetailPopu;
import workstation.zjyk.workstation.ui.pop.basePop.WSBasePopupWindow;

/**
 * Created by zjgz on 2018/1/12.
 */

public class WSTaskDetailPopu extends WSBasePopupWindow {
    @BindView(R.id.recycleview)
    RecyclerView recycleview;
    WSTaskMainInfo info;
    private WSAdapterTaskDetailPopu wsAdapterTaskDetailPopu;

    public WSTaskDetailPopu(Activity context) {
        super(context, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, R.style.QQPopAnim);

        initView();
    }

    public WSTaskDetailPopu setData(WSTaskMainInfo info) {
        this.info = info;
        String productNumber = info.getProductNumber();//生产序号
        addData("生产序号: ", productNumber);
        String productModelTypeName = info.getProductModelTypeName();//产品型号
        addData("产品型号: ", productModelTypeName);
        String procedureName = info.getProcedureName();//工序名称
        addData("工序名称: ", procedureName);
        String serialNumber = info.getSerialNumber();//订单生产编号
        addData("订单生产编号: ", serialNumber);
        String productModel = info.getProductModel();//产品货号
        addData("产品货号: ", productModel);
        String productStartTime = info.getProductStartTime();//投产时间
        addData("投产时间: ", productStartTime);
        String deliverTime = info.getDeliverTime();//交货时间
        addData("交货时间: ", deliverTime);
//        String beforeProductName = info.getBeforeProductName();//前置工序名称
//        addData("前置工序名称: ", beforeProductName);
//        String beforeWorkStationName = info.getBeforeWorkStationName();//前置工位名称
//        addData("前置工位名称: ", beforeWorkStationName);
        int count = info.getCount();//生产数量
        addData("生产数量: ", count + "");
        wsAdapterTaskDetailPopu.setNewData(datas);
        return this;
    }

    private void initView() {
        recycleview.setLayoutManager(new LinearLayoutManager(mContext));
        wsAdapterTaskDetailPopu = new WSAdapterTaskDetailPopu();
        recycleview.setAdapter(wsAdapterTaskDetailPopu);

    }

    List<WSTaskDetailItemBean> datas = new ArrayList<>();

    private void addData(String name, String detail) {
        WSTaskDetailItemBean bean = new WSTaskDetailItemBean();
        bean.setName(name);
        bean.setDetail(detail);
        datas.add(bean);
    }

    @Override
    public View getPopupView() {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.popu_task_detail, null);
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
}
