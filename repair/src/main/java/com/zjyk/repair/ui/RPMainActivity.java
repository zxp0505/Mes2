package com.zjyk.repair.ui;

import android.Manifest;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zjyk.repair.R;
import com.zjyk.repair.modle.bean.RPAppTypeEnum;
import com.zjyk.repair.modle.bean.RPAppUpdate;
import com.zjyk.repair.modle.bean.RPPerson;
import com.zjyk.repair.modle.bean.RPWorkReportRecordVO;
import com.zjyk.repair.modle.bean.RPWorkStationVo;
import com.zjyk.repair.modle.bean.RPYesOrNoEnum;
import com.zjyk.repair.ui.activity.RPActivityLogin;
import com.zjyk.repair.ui.activity.RPBaseActivity;
import com.zjyk.repair.ui.adapter.RPAdapterWorkerPhoto;
import com.zjyk.repair.ui.application.RPBaseApplication;
import com.zjyk.repair.ui.manager.RPUserManager;
import com.zjyk.repair.ui.present.RPMainPresent;
import com.zjyk.repair.ui.views.RPMainView;
import com.zjyk.repair.util.RPConstants;
import com.zjyk.repair.util.dialog.RPDialogUtils;
import com.zjyk.repair.util.dialog.RPDownApkProgressDialog;
import com.zjyk.repair.util.dialog.RPPushOneDialog;
import com.zjyk.repair.util.dialog.callback.RPDialogCallBackTwo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.ethank.mylibrary.resourcelibrary.common_util.AppManager;
import cn.com.ethank.mylibrary.resourcelibrary.common_util.AppUtils;
import cn.com.ethank.mylibrary.resourcelibrary.common_util.SharePreferenceKeyUtil;
import cn.com.ethank.mylibrary.resourcelibrary.common_util.ShellUtil;
import cn.com.ethank.mylibrary.resourcelibrary.file.FileUtil;
import cn.com.ethank.mylibrary.resourcelibrary.glide.ImageLoader;
import cn.com.ethank.mylibrary.resourcelibrary.intent.StartIntentUtils;
import cn.com.ethank.mylibrary.resourcelibrary.keyreciver.HomeWatcherReceiver;
import cn.com.ethank.mylibrary.resourcelibrary.netstatus.NetStatusUtil;
import cn.com.ethank.mylibrary.resourcelibrary.netstatus.NetworkConnectChangedReceiver;
import cn.com.ethank.mylibrary.resourcelibrary.server.MessageEventBean;
import cn.com.ethank.mylibrary.resourcelibrary.server.MyServer;
import cn.com.ethank.mylibrary.resourcelibrary.shareutils.SharedPreferencesUitl;
import cn.com.ethank.mylibrary.resourcelibrary.toast.ToastUtil;
import cn.com.ethank.mylibrary.resourcelibrary.utils.NoDoubleClickUtil;
import cn.com.ethank.mylibrary.resourcelibrary.utils.SDCardHelper;
import cn.com.ethank.mylibrary.resourcelibrary.utils.TimerUtils;
import cn.com.ethank.mylibrary.resourcelibrary.utils.notify.NotifyBean;
import cn.com.ethank.mylibrary.resourcelibrary.utils.notify.NotifyUtil;
import io.reactivex.functions.Consumer;
import me.yokeyword.fragmentation.ISupportFragment;
import workstation.zjyk.workstation.IHeart;

/**
 * Created by zjgz on 2017/11/30.
 */

public class RPMainActivity extends Activity  {


//    private static final int UPDATE_TIME = 0;//更新时间
//    private static final int SEND_HEART = 1;//发送心跳
//    private static final int REQUEST_CODE_RECIVER_MATERAIL = 0; //接收物料
//    private static final int REQUEST_CODE_RECIVER_MAKING = 1; //接收在制品
//    private static final int REQUEST_CODE_RECIVER_MATERAIL_AND_MAKING = 2; //接收物料和在制品
//
//    public static final int LOOK_BOM_DETAIL = 3; //查看bom详情页
//    @BindView(R.id.tv_station_position)
//    TextView tvStationPosition;
//    @BindView(R.id.tv_date)
//    TextView tvDate;
//    @BindView(R.id.tv_timer)
//    TextView tvTimer;
//    @BindView(R.id.iv_main_user_photo)
//    ImageView ivMainUserPhoto;
//    @BindView(R.id.tv_worker_name_current)
//    TextView tvWorkerNameCurrent;
//    @BindView(R.id.recycle_woker_photo)
//    RecyclerView recycleWokerPhoto;
//    @BindView(R.id.btn_login_two)
//    Button btnLoginTwo;
//    @BindView(R.id.btn_quit_two)
//    Button btnQuitTwo;
//    //    @BindView(R.id.nav_view)
////    NavigationView navView;
////nav
////    private ImageView iv_nav_user_photo;
////    private TextView tv_nav_user_name;
//    @BindView(R.id.draw_layout)
//    DrawerLayout drawLayout;
//    @BindView(R.id.iv_logo_or_return)
//    ImageView ivLogoOrReturn;
//    @BindView(R.id.frame_content)
//    FrameLayout frameContent;
//    @BindView(R.id.rl_station_position)
//    RelativeLayout rlStationPosition;
//    @BindView(R.id.tv_person_time)
//    TextView tvPersonTime;
//    RPPerson currentPerson = null;
//    boolean isResume = false; //can not perform this action after onsaveInstanceState
//    String[] commads = new String[]{"adb shell", "su", "am force-stop workstation.zjyk.workstation"};//杀死服务进程 添加 :HeartService无效
//    private RPAdapterWorkerPhoto mAdapterWorkerPhoto;
//    private Handler handler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            switch (msg.what) {
//                case UPDATE_TIME:
//                    String currentTimer = msg.getData().getString("currentTimer");
//                    Log.e("currentTimer:", currentTimer);
//                    tvTimer.setText(currentTimer);
//                    updatePersonTime(30);
//                    startTimer();
//                    break;
//                case SEND_HEART:
//                    startHeart();
//                    break;
//            }
//        }
//    };
//    private MyServer myServer;
//    private StringBuffer personsIdBuffer;
//    private boolean isFirstStart = true;
//    private NetworkConnectChangedReceiver mNetworkConnectChangedReceiver;
//    private HomeWatcherReceiver mHomeKeyReceiver;
//    private IHeart iHeart;
//    private RPDownApkProgressDialog wsDownApkProgressDialog;
//    private RPPushOneDialog wsPushOneDialog;
//    private RPStationDetailPop mStationDetailPop;
//    private boolean isBind;
//    ServiceConnection connection = new ServiceConnection() {
//        @Override
//        public void onServiceConnected(ComponentName name, IBinder service) {
//            iHeart = IHeart.Stub.asInterface(service);
//            isBind = true;
//
//        }
//
//        @Override
//        public void onServiceDisconnected(ComponentName name) {
//            iHeart = null;
//            isBind = false;
//            startHeart();
//        }
//    };
//    private RPUserRecordDetailDialog mUserRecordDetailDialog;
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//    }
//
//    @Override
//    public void initOnCreate() {
//        initView();
//        checkNetRequestData();
////        initData();
//
//    }
//
//    @Override
//    protected void onEvent(MessageEventBean messageEventBean) {
//        super.onEvent(messageEventBean);
//        if (messageEventBean.getType() == 1) {
//            switch (messageEventBean.getRefreshType()) {
//                case 1:
//                    //刷新登录
//                    currentPresent.getStationAllPerson(false);
//                    refreshTaskList();
//                    break;
//                case 3:
//                    if (isFirstStart) {
//                        tvTimer.postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                RPConstants.getClientIp();
//                                checkNetRequestData();
//                            }
//                        }, 2000);
//                    } else {
//                        checkNetRequestData();
//                    }
//                    break;
//                case MyServer.ACTION_UPDATE_WORKSTATION_INFO:
//                    //后台更改pad所属工位
//                    //0.弹框提示1、退到首页 2.刷新工位信息 3.刷新任务列表
//                    quitMainActivity();
//                    showPushOneDialog("工位变更", MyServer.ACTION_UPDATE_WORKSTATION_INFO);
//                    initData();
//                    refreshTaskList();
//                    break;
//                case MyServer.ACTION_TO_OFFLINE:
//                    //下线推送
//                    //1.获取在线人员信息
//                    quitMainActivity();
//                    showPushOneDialog("登录超时,请重新登录...", MyServer.ACTION_TO_OFFLINE);
//                    currentPresent.getStationAllPerson(true);
//                    refreshTaskList();
//                    break;
//                case MyServer.ACTION_TASK_TRASFER:
//                    //任务转移推送
//                    refreshTaskList();
//                    break;
//                case MyServer.ACTION_TASK_COME:
//                    //任务下发
//                    refreshTaskList();
//                    break;
//                case MyServer.ACTION_ORDER_REAGIN:
//                    //订单恢复
//                case MyServer.ACTION_ORDER_PAUSE:
//                    //订单暂停
//                    opreatTaskFragment(messageEventBean);
//                    break;
//                case MyServer.ACTION_ORDER_END:
//                    //订单终止
//                    opreatTaskFragment(messageEventBean);
//                    break;
//                case MyServer.ACTION_LOGIN_CHANG:
//                    //异地登录
////                    notifyLoginChange(messageEventBean);
//                    break;
//                case MyServer.ACTION_TASK_START:
//                    //任务开始
//                case MyServer.ACTION_TASK_PAUSE:
//                    //任务暂停
//                case MyServer.ACTION_TASK_END:
//                    //任务结束
//                case MyServer.ACTION_TASK_FINISH:
//                    //任务已完成
//                case MyServer.ACTION_TASK_OUTPUT:
//                    //任务存在输出
//                case MyServer.ACTION_TASK_REWORK:
//                    //任务返工
//                case MyServer.ACTION_TASK_REPAIR:
//                    //任务维修
//                case MyServer.ACTION_TASK_EXCEPTION:
//                    //任务异常
//                case MyServer.ACTION_TASK_REPORT_WORK:
//                    //任务报工
//                case MyServer.ACTION_TASK_RECIVER_MATERAIL:
//                    //接收物料
//                case MyServer.ACTION_TASK_UPDATE_FOLLOW:
//                    //关注物料变更
//                    break;
//            }
//        }
//    }
//
//    private void refreshTaskList() {
//
//    }
//
//    private void quitMainActivity() {
//        AppManager.getAppManager().finishOtherActivity(RPMainActivity.this);
//    }
//
//    private void showPushOneDialog(String title, int code) {
//        if (wsPushOneDialog != null && wsPushOneDialog.getTitle().equals(title) && wsPushOneDialog.isShowing()) {
//            return;
//        }
//        wsPushOneDialog = RPDialogUtils.showPushOneDialog(this, title, code, new RPDialogCallBackTwo() {
//            @Override
//            public void OnPositiveClick(Object obj) {
//
//            }
//
//            @Override
//            public void OnNegativeClick() {
//
//            }
//        });
//    }
//
//    private void initData() {
//        currentPresent.getStationName();
//    }
//
//    private void opreatTaskFragment(MessageEventBean messageEventBean) {
//      }
//
//    }
//
//
//    @Override
//    protected void creatPresent() {
//        currentPresent = new RPMainPresent();
//    }
//
//    @Override
//    protected int getLayoutId() {
//        return R.layout.rp_activity_main;
//    }
//
//    @Override
//    protected View getLoadingTargetView() {
//        return null;
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        isResume = true;
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        isResume = false;
//    }
//
//    @Override
//    protected void onDestroy() {
//        if (connection != null) {
//            if (isBind) {
//                unbindService(connection);
//                isBind = false;
//            }
//        }
//        handler.removeCallbacksAndMessages(null);
//        handler = null;
//        if (myServer != null) {
//            myServer.stop();
//        }
//        if (mNetworkConnectChangedReceiver != null) {
//            unregisterReceiver(mNetworkConnectChangedReceiver);
//        }
//        if (mHomeKeyReceiver != null) {
//            unregisterReceiver(mHomeKeyReceiver);
//        }
//        killHeartProcess();
//        super.onDestroy();
//    }
//
//    @Override
//    public void onScanResult(String scanResult) {
//        ISupportFragment topFragment = getTopFragment();
////        if (topFragment != null && topFragment instanceof RPBaseFragment) {
////            RPBaseFragment baseFragment = (RPBaseFragment) topFragment;
////            baseFragment.setScanResult(scanResult);
////        }
//    }
//
//    private void killHeartProcess() {
//        ShellUtil.CommandResult commandResult = ShellUtil.execCommand(commads, true, true);
//    }
//
//    private void initView() {
//        setControlDrawLayout(true);
//        ivLogoOrReturn.setTag(0);
//        personsIdBuffer = new StringBuffer();
////        navView.setNavigationItemSelectedListener(this);
////        View headerView = navView.getHeaderView(0);
////        iv_nav_user_photo = headerView.findViewById(R.id.iv_nav_user_photo);
////        tv_nav_user_name = headerView.findViewById(R.id.tv_nav_user_name);
//        tvDate.setText(TimerUtils.getNowDate());
//        tvTimer.setText(TimerUtils.getCurrentTimer24());
//        recycleWokerPhoto.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
//        mAdapterWorkerPhoto = new RPAdapterWorkerPhoto();
//        recycleWokerPhoto.setAdapter(mAdapterWorkerPhoto);
//        mAdapterWorkerPhoto.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
//            @Override
//            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
//                RPPerson person = (RPPerson) adapter.getData().get(position);
//                if (currentPerson != null && currentPerson.getPersonId() != person.getPersonId()) {
//                    RPDialogUtils.showChangeWorkerDialog(RPMainActivity.this, "确定要切换为" + person.getName() + "用户吗?", new RPDialogCallBackTwo() {
//                        @Override
//                        public void OnPositiveClick(Object obj) {
//                            currentPerson.setSelect(false);
//                            person.setSelect(true);
//                            adapter.notifyItemChanged(currentPerson.getPosition());
//                            adapter.notifyItemChanged(person.getPosition());
//                            currentPerson = person;
//                            setCurrentPersonName();
//                        }
//
//                        @Override
//                        public void OnNegativeClick() {
//
//                        }
//                    });
//
//                }
//
//            }
//        });
//        ivLogoOrReturn.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                ToastUtil.showInfoCenterShort("当前的版本:" + AppUtils.getVersionCode(RPBaseApplication.getInstance()));
//                return false;
//            }
//        });
//        registerNetChangeReciver();
//        registerHomeKeyReceiver();
////        RxView.clicks(ivLogoOrReturn).throttleFirst(1000, TimeUnit.MILLISECONDS).subscribe(new Consumer<Object>() {
////            @Override
////            public void accept(Object o) throws Exception {
////                Integer tag = (Integer) ivLogoOrReturn.getTag();
////                if (tag == 1) {
//////                    setFragmentBack();
////                }
////            }
////        });
//    }
//
//    private void checkNetRequestData() {
//        NotifyBean bean = new NotifyBean();
//        if (AppUtils.isNetwork(RPBaseApplication.getInstance())) {
//            if (!isFirstStart) {
//                bean.setTitle("网络连接正常");
//                bean.setMessage("网络已连接正常!");
//                setNotify(bean);
//            }
//            NetStatusUtil.getInstance().setWifi(true);
//            if (isFirstStart) {
//                initOthers();
//            }
//            checkApkVersion();
//        } else {
//            NetStatusUtil.getInstance().setWifi(false);
//            if (!isFirstStart) {
//                bean.setTitle("网络连接异常");
//                bean.setMessage("请检查网络!");
//                setNotify(bean);
//            }
//            ToastUtil.showInfoCenterShort("网络连接异常");
//        }
//    }
//
//    public void setControlDrawLayout(boolean isClose) {
//        if (isClose) {
//            //禁止手势滑动
//            drawLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
//        } else {
//            //打开手势滑动
////            drawLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
//            drawLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
//        }
//    }
//
//    private void setCurrentPersonName() {
//        if (currentPerson != null) {
//            tvWorkerNameCurrent.setText(currentPerson.getName());
//            SharedPreferencesUitl.saveStringData(SharePreferenceKeyUtil.PERSON_ID, currentPerson.getPersonId());
//            RPUserManager.getInstance().setLoginStatus(true);
////            tv_nav_user_name.setText("已登录");
////            ImageLoader.loadImage(this, currentPerson.getUserPic(), R.drawable.ic_main_user, iv_nav_user_photo);
//            ImageLoader.loadImage(this, currentPerson.getUserPic(), R.drawable.ic_main_user, ivMainUserPhoto);
//            tvPersonTime.setVisibility(View.VISIBLE);
//            updatePersonTime(0);
//            RPUserManager.getInstance().setCurrentUser(currentPerson);
//            RPUserManager.getInstance().setCurrentUserName(currentPerson.getName());
//
//        } else {
//            RPUserManager.getInstance().setCurrentUser(null);
//            tvWorkerNameCurrent.setText("请先登录");
//            ivMainUserPhoto.setImageResource(R.drawable.ic_main_user);
//            tvPersonTime.setVisibility(View.GONE);
//            SharedPreferencesUitl.saveStringData(SharePreferenceKeyUtil.PERSON_ID, "");
//            RPUserManager.getInstance().setLoginStatus(false);
////            iv_nav_user_photo.setImageResource(R.drawable.ic_main_user);
////            tv_nav_user_name.setText("未登录");
//            RPUserManager.getInstance().setCurrentUserName("");
//            quitTaskListFragment();
//        }
//        if (mAdapterWorkerPhoto.getData() != null && mAdapterWorkerPhoto.getData().size() > 1) {
//            recycleWokerPhoto.setVisibility(View.VISIBLE);
//        } else {
//            recycleWokerPhoto.setVisibility(View.INVISIBLE);
//        }
//
//        updateHeartBean();
//    }
//
//    private void registerNetChangeReciver() {
//        IntentFilter filter = new IntentFilter();
//        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
//        filter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
//        filter.addAction("android.net.wifi.STATE_CHANGE");
//        mNetworkConnectChangedReceiver = new NetworkConnectChangedReceiver();
//        registerReceiver(mNetworkConnectChangedReceiver, filter);
//    }
//
//    private void registerHomeKeyReceiver() {
//        mHomeKeyReceiver = new HomeWatcherReceiver();
//        final IntentFilter homeFilter = new IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
//
//        registerReceiver(mHomeKeyReceiver, homeFilter);
//    }
//
//    private void setNotify(NotifyBean bean) {
//        NotifyUtil.getInstance().setContext(this).sendMessage(bean);
//    }
//
//    private void initOthers() {
//        initFragment();
//        startTimer();
//        startServer();
//        startHeart();
//        isFirstStart = false;
//    }
//
//    private void checkApkVersion() {
//        Map<String, String> params = new HashMap<>();
//        params.put("type", RPAppTypeEnum.ANDROID_WORK_STATION + "");
//        currentPresent.checkApkVersion(params);
//
//    }
//
//    private void updatePersonTime(long spaceTime) {
//        if (spaceTime > 0) {
//            List<RPPerson> data = mAdapterWorkerPhoto.getData();
//            if (data != null && data.size() > 0) {
//                for (int i = 0; i < data.size(); i++) {
//                    RPPerson wsPerson = data.get(i);
//                    wsPerson.setSecondsDate(wsPerson.getSecondsDate() + spaceTime);
//                }
//            }
//        }
//        if (currentPerson != null && tvPersonTime.getVisibility() == View.VISIBLE) {
//            tvPersonTime.setText(TimerUtils.getThreeStringTime(currentPerson.getSecondsDate()));
//        }
//
//    }
//
//    private void quitTaskListFragment() {
//        if (NoDoubleClickUtil.doubleClick(800)) {
//            //防止快速pop 出现异常
//            return;
//        }
//        ISupportFragment topFragment = getTopFragment();
//        if (!(topFragment instanceof RPTaskListFragment)) {
//            if (isResume) {
//                popTo(RPTaskListFragment.class, false);
//            }
//        }
//    }
//
//    private void updateHeartBean() {
//        if (iHeart != null) {
//            try {
//                HeartBean heartBean = new HeartBean();
//                heartBean.setClientIP(RPConstants.getClientIp());
//                heartBean.setPersonsId(getPersonsId());
//                heartBean.setPersonId(RPUserManager.getInstance().getPersonId());
//                if (RPUserManager.getInstance().getWorkStationVo() != null) {
//                    heartBean.setWorkStationId(RPUserManager.getInstance().getWorkStationVo().getWorkStationId());
//                }
//                iHeart.updateHeartBean(heartBean);
//            } catch (RemoteException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    private void initFragment() {
////        mTaskListFragment = findFragment(RPTaskListFragment.class);
////        if (mTaskListFragment == null) {
////            mTaskListFragment = RPTaskListFragment.newInstance();
////            loadRootFragment(R.id.frame_content, mTaskListFragment);
////        } else {
////            mTaskListFragment = findFragment(RPTaskListFragment.class);
////        }
//    }
//
//    private void startTimer() {
//        //开启定时更新时间
//
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                String currentTimer = TimerUtils.getCurrentTimer24();
//                Message obtain = Message.obtain();
//                Bundle bundle = new Bundle();
//                bundle.putString("currentTimer", currentTimer);
//                obtain.setData(bundle);
//                obtain.what = UPDATE_TIME;
//                handler.sendMessage(obtain);
//            }
//        }, 30 * 1000);
//    }
//
//    private void startServer() {
//        try {
//            myServer = new MyServer();
//            myServer.start();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 发送心跳
//     */
//    private void startHeart() {
//        Intent intent = new Intent(this, HeartBeatService.class);
//        bindService(intent, connection, Context.BIND_AUTO_CREATE);
//////        if (WSUserManager.getInstance().getLoginStatus()) {
////        Map<String, String> params = new HashMap<>();
////        params.put("personsId", getPersonsId());
////        currentPresent.requestHeart(params);
//////        }
////        handler.sendEmptyMessageDelayed(SEND_HEART, 10 * 1000);
//    }
//
//    private String getPersonsId() {
//        personsIdBuffer.setLength(0);
//        List<RPPerson> data = mAdapterWorkerPhoto.getData();
//        for (int i = 0; i < data.size(); i++) {
//            RPPerson person = data.get(i);
//            personsIdBuffer.append(person.getPersonId());
//            personsIdBuffer.append(",");
//        }
//        if (personsIdBuffer.length() > 0) {
//            personsIdBuffer.deleteCharAt(personsIdBuffer.length() - 1);
//        }
//        return personsIdBuffer.toString();
//    }
//
//    private void notifyLoginChange(MessageEventBean messageEventBean) {
//        NotifyBean notifyBean = new NotifyBean();
//        notifyBean.setMessage(messageEventBean.getMessage());
//        notifyBean.setTitle("下线通知");
//        NotifyUtil.getInstance().setContext(this).sendMessage(notifyBean);
//    }
//
//    // tag 0: logo 1:返回
//    public void setActivityLogoOrBack(int tag) {
////        Integer ivTag = (Integer) ivLogoOrReturn.getTag();
////        if (ivTag == tag) {
////            return;
////        }
////        switch (tag) {
////            case 0:
////                ivLogoOrReturn.setImageResource(R.drawable.elco_logo2);
////                break;
////            case 1:
////                ivLogoOrReturn.setImageResource(R.drawable.selector_back);
////                break;
////        }
////
////        ivLogoOrReturn.setTag(tag);
//
//    }
//
//    @OnClick({R.id.btn_login_two, R.id.btn_quit_two, R.id.tv_station_position, R.id.rl_station_position, R.id.ll_wran, R.id.tv_warn, R.id.iv_main_user_photo})
//    public void onViewClicked(View view) {
//        switch (view.getId()) {
//            case R.id.btn_login_two:
//                //扫码登录
//                Bundle bundle = new Bundle();
//                bundle.putInt("loginType", 0);
//                StartIntentUtils.startIntentUtilsFromResult(RPMainActivity.this, RPActivityLogin.class, RPConstants.LOGIN_ACTIVITY, bundle);
//                break;
//            case R.id.btn_quit_two:
//                //扫码退出
//                if (RPUserManager.getInstance().getLoginStatus()) {
//                    Bundle bundle2 = new Bundle();
//                    bundle2.putInt("loginType", 2);
//                    StartIntentUtils.startIntentUtilsFromResult(RPMainActivity.this, RPActivityLogin.class, RPConstants.LOGIN_ACTIVITY, bundle2);
//                } else {
//                    ToastUtil.showCenterShort(getString(R.string.login_out_un), true);
//                }
//                break;
//            case R.id.rl_station_position:
//            case R.id.tv_station_position:
////                getSupportDelegate().showFragmentStackHierarchyView();
//                getStationDetailPopup().showPopupWindow(true);
//                break;
//            case R.id.tv_warn:
//                toWran();
//                break;
//            case R.id.iv_main_user_photo:
//                if (RPUserManager.getInstance().getLoginStatus()) {
//                    requestUserReportDetail();
//                } else {
//                    ToastUtil.showCenterShort(getString(R.string.text_un_login), true);
//                }
//                break;
//        }
//    }
//
//    private void requestUserReportDetail() {
//        Map<String, String> params = new HashMap<>();
//        params.put("workStationId", RPUserManager.getInstance().getWorkStationId());
//        currentPresent.requestUserReportDetail(params);
//    }
//
//    @Override
//    public void showUserReportDetail(List<RPWorkReportRecordVO> wsWorkReportRecordVOList) {
//        if (wsWorkReportRecordVOList != null) {
//            if (wsWorkReportRecordVOList.size() > 0) {
//                showUserRecordDetailDialog(wsWorkReportRecordVOList);
//            } else {
//                ToastUtil.showInfoCenterShort("暂无记录");
//            }
//        }
//    }
//
//    private void showUserRecordDetailDialog(List<RPWorkReportRecordVO> wsWorkReportRecordVOList) {
//        if (mUserRecordDetailDialog == null) {
//
//            mUserRecordDetailDialog = RPDialogUtils.showUserRecordDetailDialog(this, RPUserManager.getInstance().getCurrentPerson().getName() + "的报工记录", new RPDialogCallBackTwo() {
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
//        mUserRecordDetailDialog.setData(RPUserManager.getInstance().getCurrentPerson().getName() + "的报工记录", wsWorkReportRecordVOList);
//        mUserRecordDetailDialog.show();
//    }
//
//    private RPStationDetailPop getStationDetailPopup() {
//        if (mStationDetailPop == null) {
//            mStationDetailPop = new RPStationDetailPop(this);
//        }
//        mStationDetailPop.setData(RPUserManager.getInstance().getWorkStationVo());
//        return mStationDetailPop;
//    }
//
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);//!!!!!!
//        switch (requestCode) {
//            case RPConstants.LOGIN_ACTIVITY:
//                //登陆界面过来的
//                if (data != null) {
//                    showLoginResult(data);
//                }
//                break;
//            case REQUEST_CODE_RECIVER_MATERAIL:
//            case REQUEST_CODE_RECIVER_MAKING:
//            case REQUEST_CODE_RECIVER_MATERAIL_AND_MAKING:
////                if (data != null) {
////                    boolean isRefresh = data.getBooleanExtra("isRefresh", false);
////                    if (isRefresh) {
////                        ISupportFragment topFragment = getTopFragment();
////                        if (topFragment instanceof RPTaskFragment) {
////                            ((RPTaskFragment) topFragment).initData();
////                        }
////                        //刷新
////                        RPTaskListFragment wsTaskListFragment = findFragment(RPTaskListFragment.class);
////                        if (wsTaskListFragment != null) {
////                            wsTaskListFragment.initData();
////                        }
////                    }
////                }
//                break;
//            case LOOK_BOM_DETAIL:
//                break;
//        }
//
//
//    }
//
//    private void showLoginResult(Intent data) {
//        int type = data.getIntExtra("type", 1);
//        RPPerson person = (RPPerson) data.getSerializableExtra("person");
//        if (type == 1) {
//            //登录显示 最后一个登录的人
//            List<RPPerson> personList = new ArrayList<>();
//            personList.add(person);
//            //登录  1.当前没人 2.已经登录上了
//            if (currentPerson == null) {
//                person.setSelect(true);
//                mAdapterWorkerPhoto.setNewData(personList);
//                currentPerson = person;
//
//            } else if (currentPerson != null) {
//                //判断当前人有没有登录过 没登录 添加进去
//
//
//                currentPerson.setSelect(false);
//                mAdapterWorkerPhoto.notifyItemChanged(currentPerson.getPosition());
//                person.setSelect(true);
//                currentPerson = person;
//                //兼容处理 以防后台出现用户登录不准的情况
//                List<RPPerson> data1 = mAdapterWorkerPhoto.getData();
//                if (data1 != null && !data1.contains(person)) {
//                    mAdapterWorkerPhoto.addData(person);
//                } else {
//                    mAdapterWorkerPhoto.notifyItemChanged(currentPerson.getPosition());
//                }
////                recycleWokerPhoto.scrollToPosition(mAdapterWorkerPhoto.getData().size());
//            }
//
//        } else if (type == 2) {
//            //退出
//            int quitPersonIndex = -1;
//            List<RPPerson> data1 = mAdapterWorkerPhoto.getData();
//            for (int i = 0; i < data1.size(); i++) {
//                RPPerson person1 = data1.get(i);
//                if (person1.getPersonId().equals(person.getPersonId())) {
//                    quitPersonIndex = person1.getPosition();
//                    break;
//                }
//            }
//            if (quitPersonIndex >= 0) {
//                if (quitPersonIndex == currentPerson.getPosition() && data1.size() > 1) {
//                    //退出的是当前操作员工 特殊处理
//                    if (quitPersonIndex == 0) {
//                        currentPerson = data1.get(1);
//                    } else {
//                        currentPerson = data1.get(0);
//                    }
//                    currentPerson.setSelect(true);
//                } else if (data1.size() == 1) {
//                    currentPerson = null;
//                }
//                mAdapterWorkerPhoto.removeItem(quitPersonIndex);
//            }
//        }
//
//        setCurrentPersonName();
//        int loginSize = mAdapterWorkerPhoto.getData().size();
//        if (loginSize <= 1 && type == 2) {
//            currentPresent.getStationAllPerson(true);
//        } else {
//            currentPresent.getStationAllPerson(false);
//        }
//    }
//
//    @Override
//    public void showStation(RPWorkStationVo workStationVo) {
//        if (workStationVo != null) {
//            RPUserManager.getInstance().setWorkStationVo(workStationVo);
//            String newDate = workStationVo.getNewDate();
////            if (!TextUtils.isEmpty(newDate)) {
////                newDate = newDate.split("_")[0];
////            }
////            tvDate.setText(newDate);
//            tvStationPosition.setText(workStationVo.getWorkStationName());
//            SharedPreferencesUitl.saveStringData(SharePreferenceKeyUtil.STATION_NAME, workStationVo.getWorkStationName());
//            SharedPreferencesUitl.saveStringData(SharePreferenceKeyUtil.STATION_ID, workStationVo.getWorkStationId());
//        }
//        currentPresent.getStationAllPerson(true);
//    }
//
//
//
//    @Override
//    public void loginOutResult(boolean result) {
//
//    }
//
//    @Override
//    public void clearTrayResult(boolean result) {
//
//    }
//
//    @Override
//    public void showAllPersons(List<RPPerson> personList) {
//        if (personList != null && personList.size() > 0) {
//            if (currentPerson == null) {
//                currentPerson = personList.get(0);
//                currentPerson.setSelect(true);
//            } else {
//                //推送过来得
//                if (personList.contains(currentPerson)) {
//                    //多pad
//                    int i = personList.indexOf(currentPerson);
//                    currentPerson = personList.get(i);
//                } else {
//                    //多pad -从别的pad退出 选取集合第一个当作选中员工
//                    currentPerson = personList.get(0);
//                }
//                currentPerson.setSelect(true);
//            }
//
//            mAdapterWorkerPhoto.setNewData(personList);
//        } else {
//            currentPerson = null;
//            if (personList != null) {
//                mAdapterWorkerPhoto.setNewData(personList);
//            } else {
//                mAdapterWorkerPhoto.setNewData(new ArrayList<>());
//            }
//        }
//        //移动到末端
//        setCurrentPersonName();
//
//    }
//
//    @Override
//    public void downApkResult(boolean result, String saveUrl) {
//        if (result) {
//            if (wsDownApkProgressDialog != null) {
//                wsDownApkProgressDialog.setInstall();
//            }
//            FileUtil.setupApk(this, new File(saveUrl));
//        }
//    }
//
//
//
//    @Override
//    public void showCheckApkInfo(RPAppUpdate wsAppUpdate) {
//        if (wsAppUpdate != null) {
//            int minimumVersion = 0;
//            int newVersion = 0;
//            RPYesOrNoEnum forceUpdate = wsAppUpdate.getForceUpdate();
//            if (!TextUtils.isEmpty(wsAppUpdate.getMinimumVersion())) {
//                minimumVersion = Integer.parseInt(wsAppUpdate.getMinimumVersion());
//            }
//            if (!TextUtils.isEmpty(wsAppUpdate.getNewestVersion())) {
//                newVersion = Integer.parseInt(wsAppUpdate.getNewestVersion());
//            }
//
//            int versionCode = AppUtils.getVersionCode(RPBaseApplication.getInstance());
//            if ((forceUpdate == RPYesOrNoEnum.YES) && (versionCode < minimumVersion)) {
//                //强制升级 弹框升级
//                showUpdateDialog("发现新版本,请升级...", wsAppUpdate);
//                return;
//            }
//            if (versionCode < newVersion) {
//                //弹框升级提示
//                showUpdateDialog("发现新版本,请升级...", wsAppUpdate);
//            }
//        }
//
//        initData();
//
//
//    }
//
//    private void showUpdateDialog(String title, RPAppUpdate wsAppUpdate) {
//        //升级
//        wsDownApkProgressDialog = RPDialogUtils.showUpdateDialog(this, title, wsAppUpdate, new RPDialogCallBackTwo() {
//            @Override
//            public void OnPositiveClick(Object obj) {
//                //升级
//                if (ContextCompat.checkSelfPermission(RPMainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//                    //
//                    RxPermissions rxPermissions = new RxPermissions(RPMainActivity.this);
//                    rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(new Consumer<Boolean>() {
//                        @Override
//                        public void accept(Boolean aBoolean) throws Exception {
//                            if (aBoolean) {
//                                downAPKStart(wsAppUpdate);
//                            } else {
//                                ToastUtil.showInfoCenterShort("权限拒绝，无法下载");
//                            }
//                        }
//                    });
//                } else {
//                    downAPKStart(wsAppUpdate);
//                }
//
//            }
//
//            @Override
//            public void OnNegativeClick() {
//
//            }
//        });
//    }
//
//    private void downAPKStart(RPAppUpdate wsAppUpdate) {
//        String saveFilePath = SDCardHelper.getSDCardPrivateFilesDir(this, null) + "/updateApk";
//        currentPresent.downLoadApk(RPMainActivity.this, wsAppUpdate.getUrl(), saveFilePath, wsDownApkProgressDialog);
//    }
//
//
//    private void getReciveredTrayInfo(String trayCode) {
//        Map<String, String> params = new HashMap<>();
//        params.put("trayCode", trayCode);
//        currentPresent.getReciveredTrayInfo(params);
//    }





}
