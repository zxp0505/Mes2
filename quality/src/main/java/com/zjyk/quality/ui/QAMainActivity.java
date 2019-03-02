package com.zjyk.quality.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zjyk.quality.ui.activity.QABaseActivity;
import com.zjyk.quality.R;
import com.zjyk.quality.modle.bean.QAPerson;
import com.zjyk.quality.modle.bean.QAWorkStationVo;
import com.zjyk.quality.ui.activity.QAActivityLogin;
import com.zjyk.quality.ui.activity.QABaseActivity;
import com.zjyk.quality.ui.adapter.QAAdapterWorkerPhoto;
import com.zjyk.quality.ui.present.QAMainPresent;
import com.zjyk.quality.ui.views.QAMainView;
import com.zjyk.quality.util.QAConstants;
import com.zjyk.quality.util.dialog.QADialogUtils;
import com.zjyk.quality.util.dialog.callback.QADialogCallBackTwo;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.ethank.mylibrary.resourcelibrary.common_util.SharePreferenceKeyUtil;
import cn.com.ethank.mylibrary.resourcelibrary.glide.ImageLoader;
import cn.com.ethank.mylibrary.resourcelibrary.intent.StartIntentUtils;
import cn.com.ethank.mylibrary.resourcelibrary.server.MessageEventBean;
import cn.com.ethank.mylibrary.resourcelibrary.server.MyServer;
import cn.com.ethank.mylibrary.resourcelibrary.shareutils.SharedPreferencesUitl;
import cn.com.ethank.mylibrary.resourcelibrary.toast.ToastUtil;
import cn.com.ethank.mylibrary.resourcelibrary.utils.TimerUtils;

/**
 * Created by zjgz on 2017/11/30.
 */

public class QAMainActivity extends QABaseActivity implements QAMainView {
    @BindView(R.id.tv_station_position)
    TextView tvStationPosition;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.tv_timer)
    TextView tvTimer;
    @BindView(R.id.iv_main_user_photo)
    ImageView ivMainUserPhoto;
    @BindView(R.id.tv_worker_name_current)
    TextView tvWorkerNameCurrent;
    @BindView(R.id.recycle_woker_photo)
    RecyclerView recycleWokerPhoto;
    @BindView(R.id.btn_login_two)
    Button btnLoginTwo;
    @BindView(R.id.btn_quit_two)
    Button btnQuitTwo;
    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.draw_layout)
    DrawerLayout drawLayout;
    private QAAdapterWorkerPhoto mAdapterWorkerPhoto;
    QAPerson currentPerson = null;
    private static final int UPDATE_TIME = 0;//更新时间
    private static final int SEND_HEART = 1;//发送心跳
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case UPDATE_TIME:
                    String currentTimer = msg.getData().getString("currentTimer");
                    Log.e("currentTimer:", currentTimer);
                    tvTimer.setText(currentTimer);
                    startTimer();
                    break;
                case SEND_HEART:
                    startHeart();
                    break;
            }
        }
    };
    private MyServer myServer;
    private StringBuffer personsIdBuffer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
//        initData();
        initFragment();
        startTimer();
        startServer();
        startHeart();
    }

    private void initFragment() {

    }

    private void initData() {
        ((QAMainPresent) currentPresent).getStationName();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMain(MessageEventBean messageEventBean) {
        if (messageEventBean.getType() == 1) {
            switch (messageEventBean.getRefreshType()) {
                case 1:
                    //刷新登录
                    ((QAMainPresent) currentPresent).getStationAllPerson(false);
                    break;
                case 2:
                    //刷新包裹
                    break;
            }
        }

    }

    private void startServer() {
        try {
            myServer = new MyServer();
            myServer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startTimer() {
        //开启定时更新时间

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                String currentTimer = TimerUtils.getCurrentTimer();
                Message obtain = Message.obtain();
                Bundle bundle = new Bundle();
                bundle.putString("currentTimer", currentTimer);
                obtain.setData(bundle);
                obtain.what = UPDATE_TIME;
                handler.sendMessage(obtain);
            }
        }, 30 * 1000);
    }


    /**
     * 发送心跳
     */
    private void startHeart() {
        if (QAConstants.isLogin) {
            Map<String, String> params = new HashMap<>();
            params.put("personsId", getPersonsId());
            ((QAMainPresent) currentPresent).requestHeart(params);
        }
        handler.sendEmptyMessageDelayed(SEND_HEART, 30 * 1000);
    }

    private String getPersonsId() {
        personsIdBuffer.setLength(0);
        List<QAPerson> data = mAdapterWorkerPhoto.getData();
        for (int i = 0; i < data.size(); i++) {
            QAPerson person = data.get(i);
            personsIdBuffer.append(person.getPersonId());
            personsIdBuffer.append(",");
        }
        if (personsIdBuffer.length() > 0) {
            personsIdBuffer.deleteCharAt(personsIdBuffer.length() - 1);
        }
        return personsIdBuffer.toString();
    }

    @Override
    protected void creatPresent() {
        currentPresent = new QAMainPresent();
    }

    private void initView() {
        personsIdBuffer = new StringBuffer();
        tvDate.setText(TimerUtils.getNowDate());
        recycleWokerPhoto.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mAdapterWorkerPhoto = new QAAdapterWorkerPhoto();
        recycleWokerPhoto.setAdapter(mAdapterWorkerPhoto);
        mAdapterWorkerPhoto.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                QAPerson person = (QAPerson) adapter.getData().get(position);
                if (currentPerson != null && currentPerson.getPersonId() != person.getPersonId()) {
                    QADialogUtils.showChangeWorkerDialog(QAMainActivity.this, "确定要切换为" + person.getName() + "用户吗?", new QADialogCallBackTwo() {
                        @Override
                        public void OnPositiveClick(Object obj) {
                            currentPerson.setSelect(false);
                            person.setSelect(true);
                            adapter.notifyItemChanged(currentPerson.getPosition());
                            adapter.notifyItemChanged(person.getPosition());
                            currentPerson = person;
                            setCurrentPersonName();
                        }

                        @Override
                        public void OnNegativeClick() {

                        }
                    });

                }

            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.ws_activity_main;
    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @OnClick({R.id.btn_login_two, R.id.btn_quit_two})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_login_two:
                //扫码登录
                Bundle bundle = new Bundle();
                bundle.putInt("loginType", 0);
                StartIntentUtils.startIntentUtilsFromResult(QAMainActivity.this, QAActivityLogin.class, QAConstants.LOGIN_ACTIVITY, bundle);
                break;
            case R.id.btn_quit_two:
                //扫码退出
                if (QAConstants.isLogin) {
                    Bundle bundle2 = new Bundle();
                    bundle2.putInt("loginType", 2);
                    StartIntentUtils.startIntentUtilsFromResult(QAMainActivity.this, QAActivityLogin.class, QAConstants.LOGIN_ACTIVITY, bundle2);
                } else {
                    ToastUtil.showCenterShort(getString(R.string.login_out_un), false);
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case QAConstants.LOGIN_ACTIVITY:
                //登陆界面过来的
                if (data != null) {
                    showLoginResult(data);
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);

    }

    private void showLoginResult(Intent data) {
        int type = data.getIntExtra("type", 1);
        QAPerson person = (QAPerson) data.getSerializableExtra("person");
        if (type == 1) {
            //登录显示 最后一个登录的人
            List<QAPerson> personList = new ArrayList<>();
            personList.add(person);
            //登录  1.当前没人 2.已经登录上了
            if (currentPerson == null) {
                person.setSelect(true);
                mAdapterWorkerPhoto.setNewData(personList);
                currentPerson = person;

            } else if (currentPerson != null) {
                //判断当前人有没有登录过 没登录 添加进去


                currentPerson.setSelect(false);
                mAdapterWorkerPhoto.notifyItemChanged(currentPerson.getPosition());
                person.setSelect(true);
                currentPerson = person;
                //兼容处理 以防后台出现用户登录不准的情况
                List<QAPerson> data1 = mAdapterWorkerPhoto.getData();
                if (data1 != null && !data1.contains(person)) {
                    mAdapterWorkerPhoto.addData(person);
                } else {
                    mAdapterWorkerPhoto.notifyItemChanged(currentPerson.getPosition());
                }
//                recycleWokerPhoto.scrollToPosition(mAdapterWorkerPhoto.getData().size());
            }

        } else if (type == 2) {
            //退出
            int quitPersonIndex = -1;
            List<QAPerson> data1 = mAdapterWorkerPhoto.getData();
            for (int i = 0; i < data1.size(); i++) {
                QAPerson person1 = data1.get(i);
                if (person1.getPersonId().equals(person.getPersonId())) {
                    quitPersonIndex = person1.getPosition();
                    break;
                }
            }
            if (quitPersonIndex >= 0) {
                if (quitPersonIndex == currentPerson.getPosition() && data1.size() > 1) {
                    //退出的是当前操作员工 特殊处理
                    if (quitPersonIndex == 0) {
                        currentPerson = data1.get(1);
                    } else {
                        currentPerson = data1.get(0);
                    }
                    currentPerson.setSelect(true);
                } else if (data1.size() == 1) {
                    currentPerson = null;
                }
                mAdapterWorkerPhoto.removeItem(quitPersonIndex);
            }
        }

        setCurrentPersonName();
    }

    private void setCurrentPersonName() {
        if (currentPerson != null) {
            tvWorkerNameCurrent.setText(currentPerson.getName());
            SharedPreferencesUitl.saveStringData(SharePreferenceKeyUtil.PERSON_ID, currentPerson.getPersonId());
            QAConstants.isLogin = true;
            ImageLoader.loadImage(this, currentPerson.getUserPic(), R.drawable.ic_main_user, ivMainUserPhoto);
        } else {
            tvWorkerNameCurrent.setText("请先登录");
            ivMainUserPhoto.setImageResource(R.drawable.ic_main_user);
            SharedPreferencesUitl.saveStringData(SharePreferenceKeyUtil.PERSON_ID, "");
            QAConstants.isLogin = false;
        }
        if (mAdapterWorkerPhoto.getData() != null && mAdapterWorkerPhoto.getData().size() > 1) {
            recycleWokerPhoto.setVisibility(View.VISIBLE);
        } else {
            recycleWokerPhoto.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void showStation(QAWorkStationVo workStationVo) {
        if (workStationVo != null) {
            String newDate = workStationVo.getNewDate();
//            if (!TextUtils.isEmpty(newDate)) {
//                newDate = newDate.split("_")[0];
//            }
//            tvDate.setText(newDate);
            tvStationPosition.setText(workStationVo.getWorkStationName());
            SharedPreferencesUitl.saveStringData(SharePreferenceKeyUtil.STATION_NAME, workStationVo.getWorkStationName());
            SharedPreferencesUitl.saveStringData(SharePreferenceKeyUtil.STATION_ID, workStationVo.getWorkStationId());
        }
        ((QAMainPresent) currentPresent).getStationAllPerson(true);
    }

    @Override
    public void loginOutResult(boolean result) {

    }

    @Override
    public void clearTrayResult(boolean result) {

    }

    @Override
    public void showAllPersons(List<QAPerson> personList) {
        if (personList != null && personList.size() > 0) {
            if (currentPerson == null) {
                currentPerson = personList.get(0);
                currentPerson.setSelect(true);
            } else {
                //推送过来得
                if (personList.contains(currentPerson)) {
                    //多pad
                    int i = personList.indexOf(currentPerson);
                    currentPerson = personList.get(i);
                } else {
                    //多pad -从别的pad退出 选取集合第一个当作选中员工
                    currentPerson = personList.get(0);
                }
                currentPerson.setSelect(true);
            }

            mAdapterWorkerPhoto.setNewData(personList);
        } else {
            currentPerson = null;
            if (personList != null) {
                mAdapterWorkerPhoto.setNewData(personList);
            } else {
                mAdapterWorkerPhoto.setNewData(new ArrayList<>());
            }
        }
        //移动到末端
        setCurrentPersonName();

    }
}
