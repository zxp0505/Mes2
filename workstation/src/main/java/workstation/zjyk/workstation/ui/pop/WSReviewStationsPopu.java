package workstation.zjyk.workstation.ui.pop;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import workstation.zjyk.workstation.R;
import workstation.zjyk.workstation.modle.bean.WSReworkTrayInfo;
import workstation.zjyk.workstation.modle.bean.WSTaskDetailItemBean;
import workstation.zjyk.workstation.modle.bean.WSTaskMainInfo;
import workstation.zjyk.workstation.modle.bean.WSWorkStationInfo;
import workstation.zjyk.workstation.ui.adapter.WSAdapterReviewStationsPopu;
import workstation.zjyk.workstation.ui.adapter.WSAdapterTaskDetailPopu;
import workstation.zjyk.workstation.ui.pop.basePop.WSBasePopupWindow;

/**
 * Created by zjgz on 2018/1/12.
 */

public class WSReviewStationsPopu extends WSBasePopupWindow {
    @BindView(R.id.recycleview)
    RecyclerView recycleview;
    WSTaskMainInfo info;
    private WSAdapterReviewStationsPopu wsAdapterReviewStationsPopu;

    public WSReviewStationsPopu(Activity context) {
        super(context, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, R.style.QQPopAnim);

        initView();
    }

    public WSReviewStationsPopu setData(WSReworkTrayInfo wsReworkTrayInfo) {
        List<WSWorkStationInfo> workStationList = wsReworkTrayInfo.getWorkStationList();
        if (workStationList.size() > 5) {
            ViewGroup.LayoutParams layoutParams = recycleview.getLayoutParams();
            layoutParams.height = mContext.getResources().getDimensionPixelOffset(R.dimen.y_design_200px);
            recycleview.setLayoutParams(layoutParams);
        }
        wsAdapterReviewStationsPopu.setNewData(workStationList);
        return this;
    }

    WSWorkStationInfo currentBean;

    private void initView() {
        recycleview.setLayoutManager(new LinearLayoutManager(mContext));
        wsAdapterReviewStationsPopu = new WSAdapterReviewStationsPopu();
        recycleview.setAdapter(wsAdapterReviewStationsPopu);
        wsAdapterReviewStationsPopu.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                WSWorkStationInfo bean = (WSWorkStationInfo) adapter.getData().get(position);
                switch (view.getId()) {
                    case R.id.ll_root:
                        if (!bean.isSelect()) {
                            bean.setSelect(!bean.isSelect());
                            adapter.notifyItemChanged(bean.getPosition());
                        }
                        if (currentBean != null && (!currentBean.getName().equals(bean.getName()))) {
                            currentBean.setSelect(false);
                            adapter.notifyItemChanged(currentBean.getPosition());
                        }
                        currentBean = bean;
                        dismiss();
                        break;
                }
            }
        });

    }

    public WSWorkStationInfo getCurrentSelect() {
        return currentBean;
    }


    @Override
    public View getPopupView() {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.popu_review_stations, null);
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
