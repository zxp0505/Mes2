package workstation.zjyk.workstation.ui;

import android.Manifest;
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
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.tbruyelle.rxpermissions2.RxPermissions;

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
import cn.com.ethank.mylibrary.resourcelibrary.glide.view.MyImageViewTarget;
import cn.com.ethank.mylibrary.resourcelibrary.intent.StartIntentUtils;
import cn.com.ethank.mylibrary.resourcelibrary.keyreciver.HomeWatcherReceiver;
import cn.com.ethank.mylibrary.resourcelibrary.netstatus.NetStatusUtil;
import cn.com.ethank.mylibrary.resourcelibrary.netstatus.NetworkConnectChangedReceiver;
import cn.com.ethank.mylibrary.resourcelibrary.server.MessageEventBean;
import cn.com.ethank.mylibrary.resourcelibrary.server.MyServer;
import cn.com.ethank.mylibrary.resourcelibrary.shareutils.SharedPreferencesUitl;
import cn.com.ethank.mylibrary.resourcelibrary.toast.ToastUtil;
import cn.com.ethank.mylibrary.resourcelibrary.utils.NoDoubleClickUtil;
import cn.com.ethank.mylibrary.resourcelibrary.utils.PingUtil;
import cn.com.ethank.mylibrary.resourcelibrary.utils.SDCardHelper;
import cn.com.ethank.mylibrary.resourcelibrary.utils.TimerUtils;
import cn.com.ethank.mylibrary.resourcelibrary.utils.UICommonUtil;
import cn.com.ethank.mylibrary.resourcelibrary.utils.notify.NotifyBean;
import cn.com.ethank.mylibrary.resourcelibrary.utils.notify.NotifyUtil;
import cn.com.ethank.ui.common.dialog.LibraryDialogUtils;
import cn.com.ethank.ui.common.dialog.callback.ResourceDialogCallBackTwo;
import io.reactivex.functions.Consumer;
import me.yokeyword.fragmentation.ISupportFragment;
import workstation.zjyk.workstation.HeartBean;
import workstation.zjyk.workstation.HeartBeatService;
import workstation.zjyk.workstation.IHeart;
import workstation.zjyk.workstation.R;
import workstation.zjyk.workstation.modle.bean.WSAppUpdate;
import workstation.zjyk.workstation.modle.bean.WSMyMaterielVo;
import workstation.zjyk.workstation.modle.bean.WSMyWrap;
import workstation.zjyk.workstation.modle.bean.WSPerson;
import workstation.zjyk.workstation.modle.bean.WSTrayLoadInfo;
import workstation.zjyk.workstation.modle.bean.WSWorkReportRecordVO;
import workstation.zjyk.workstation.modle.bean.WSWorkStation;
import workstation.zjyk.workstation.modle.bean.WSWorkStationTask;
import workstation.zjyk.workstation.modle.bean.WSWorkStationTrayTaskMainInfoVo;
import workstation.zjyk.workstation.modle.bean.WSWorkStationVo;
import workstation.zjyk.workstation.modle.bean.enumpackage.WSAppTypeEnum;
import workstation.zjyk.workstation.modle.bean.enumpackage.WSTrayLoadTypeEnum;
import workstation.zjyk.workstation.modle.bean.enumpackage.WSYesOrNoEnum;
import workstation.zjyk.workstation.modle.net.WSApiManager;
import workstation.zjyk.workstation.modle.net.WSErrorCode;
import workstation.zjyk.workstation.ui.activity.WSActivityLogin;
import workstation.zjyk.workstation.ui.activity.WSBaseActivity;
import workstation.zjyk.workstation.ui.activity.WSReciverMakingDialogActivity;
import workstation.zjyk.workstation.ui.activity.WSReciverMaterailAndMakingDialogActivity;
import workstation.zjyk.workstation.ui.activity.WSReciverMaterailDialogActivity;
import workstation.zjyk.workstation.ui.activity.WSSplashActivity;
import workstation.zjyk.workstation.ui.activity.WSStationMaterailBillDialogActivity;
import workstation.zjyk.workstation.ui.adapter.WSAdapterWorkerPhoto;
import workstation.zjyk.workstation.ui.application.WSBaseApplication;
import workstation.zjyk.workstation.ui.fragment.WSBaseFragment;
import workstation.zjyk.workstation.ui.fragment.WSClearTrayFragment;
import workstation.zjyk.workstation.ui.fragment.WSExceptionFragment;
import workstation.zjyk.workstation.ui.fragment.WSReportWorkFragment;
import workstation.zjyk.workstation.ui.fragment.WSTaskFragment;
import workstation.zjyk.workstation.ui.fragment.WSTaskListFragment;
import workstation.zjyk.workstation.ui.manager.WSUserManager;
import workstation.zjyk.workstation.ui.pop.WSStationDetailPop;
import workstation.zjyk.workstation.ui.present.WSMainPresent;
import workstation.zjyk.workstation.ui.views.WSMainView;
import workstation.zjyk.workstation.util.WSConstants;
import workstation.zjyk.workstation.util.WSURLBuilder;
import workstation.zjyk.workstation.util.dialog.WSDialogUtils;
import workstation.zjyk.workstation.util.dialog.WSDownApkProgressDialog;
import workstation.zjyk.workstation.util.dialog.WSPushOneDialog;
import workstation.zjyk.workstation.util.dialog.WSUpdateStationDialog;
import workstation.zjyk.workstation.util.dialog.WSUserRecordDetailDialog;
import workstation.zjyk.workstation.util.dialog.callback.WSDialogCallBackTwo;

/**
 * Created by zjgz on 2017/11/30.
 */

public class WSMainActivity extends WSBaseActivity<WSMainPresent> implements WSMainView, NavigationView.OnNavigationItemSelectedListener {
    private static final int UPDATE_TIME = 0;//更新时间
    private static final int SEND_HEART = 1;//发送心跳
    private static final int REQUEST_CODE_RECIVER_MATERAIL = 0; //接收物料
    private static final int REQUEST_CODE_RECIVER_MAKING = 1; //接收在制品
    private static final int REQUEST_CODE_RECIVER_MATERAIL_AND_MAKING = 2; //接收物料和在制品

    public static final int LOOK_BOM_DETAIL = 3; //查看bom详情页
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
    //    @BindView(R.id.nav_view)
//    NavigationView navView;
//nav
//    private ImageView iv_nav_user_photo;
//    private TextView tv_nav_user_name;
    @BindView(R.id.draw_layout)
    DrawerLayout drawLayout;
    @BindView(R.id.iv_logo_or_return)
    ImageView ivLogoOrReturn;
    @BindView(R.id.frame_content)
    FrameLayout frameContent;
    @BindView(R.id.rl_station_position)
    RelativeLayout rlStationPosition;
    @BindView(R.id.tv_person_time)
    TextView tvPersonTime;
    @BindView(R.id.tv_warn)
    TextView tvWarn;
    @BindView(R.id.ll_wran)
    LinearLayout llWran;
    WSPerson currentPerson = null;
    boolean isResume = false; //can not perform this action after onsaveInstanceState
    String[] commads = new String[]{"adb shell", "su", "am force-stop workstation.zjyk.workstation"};//杀死服务进程 添加 :HeartService无效
    private WSAdapterWorkerPhoto mAdapterWorkerPhoto;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case UPDATE_TIME:
                    String currentTimer = msg.getData().getString("currentTimer");
                    Log.e("currentTimer:", currentTimer);
                    tvTimer.setText(currentTimer);
                    WSTaskFragment taskFragment = findFragment(WSTaskFragment.class);
                    if (taskFragment != null) {
                        taskFragment.updateWorkTime(30);
                    }
                    updatePersonTime(30);
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
    private WSTaskListFragment mTaskListFragment;
    private boolean isFirstStart = true;
    private NetworkConnectChangedReceiver mNetworkConnectChangedReceiver;
    private HomeWatcherReceiver mHomeKeyReceiver;
    private IHeart iHeart;
    private WSDownApkProgressDialog wsDownApkProgressDialog;
    private WSPushOneDialog wsPushOneDialog;
    private WSStationDetailPop mStationDetailPop;
    private boolean isBind;
    ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            iHeart = IHeart.Stub.asInterface(service);
            isBind = true;

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            iHeart = null;
            isBind = false;
            startHeart();
        }
    };
    private WSUserRecordDetailDialog mUserRecordDetailDialog;
    private WSUpdateStationDialog wsUpdateStationDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initOnCreate() {
        initView();
        checkNetRequestData();
//        initData();

    }

    @Override
    protected void onEvent(MessageEventBean messageEventBean) {
        super.onEvent(messageEventBean);
        if (messageEventBean.getType() == 1) {
            switch (messageEventBean.getRefreshType()) {
                case 1:
                    //刷新登录
                    currentPresent.getStationAllPerson(false);
                    refreshTaskList();
                    break;
                case 3:
                    if (isFirstStart) {
                        tvTimer.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                WSConstants.getClientIp();
                                checkNetRequestData();
                            }
                        }, 2000);
                    } else {
                        checkNetRequestData();
                    }
                    break;
                case MyServer.ACTION_UPDATE_WORKSTATION_INFO:
                    //后台更改pad所属工位
                    //0.弹框提示1、退到首页 2.刷新工位信息 3.刷新任务列表
                    quitMainActivity();
                    showPushOneDialog("工位变更", MyServer.ACTION_UPDATE_WORKSTATION_INFO);
                    initData();
                    refreshTaskList();
                    break;
                case MyServer.ACTION_TO_OFFLINE:
                    //下线推送
                    //1.获取在线人员信息
                    quitMainActivity();
                    showPushOneDialog("登录超时,请重新登录...", MyServer.ACTION_TO_OFFLINE);
                    currentPresent.getStationAllPerson(true);
                    refreshTaskList();
                    break;
                case MyServer.ACTION_TASK_TRASFER:
                    //任务转移推送
                    refreshTaskList();
                    break;
                case MyServer.ACTION_TASK_COME:
                    //任务下发
                    refreshTaskList();
                    break;
                case MyServer.ACTION_ORDER_REAGIN:
                    //订单恢复
                case MyServer.ACTION_ORDER_PAUSE:
                    //订单暂停
                    opreatTaskFragment(messageEventBean);
                    break;
                case MyServer.ACTION_ORDER_END:
                    //订单终止
                    opreatTaskFragment(messageEventBean);
                    break;
                case MyServer.ACTION_LOGIN_CHANG:
                    //异地登录
//                    notifyLoginChange(messageEventBean);
                    break;
                case MyServer.ACTION_TASK_START:
                    //任务开始
                case MyServer.ACTION_TASK_PAUSE:
                    //任务暂停
                case MyServer.ACTION_TASK_END:
                    //任务结束
                case MyServer.ACTION_TASK_FINISH:
                    //任务已完成
                case MyServer.ACTION_TASK_OUTPUT:
                    //任务存在输出
                case MyServer.ACTION_TASK_REWORK:
                    //任务返工
                case MyServer.ACTION_TASK_REPAIR:
                    //任务维修
                case MyServer.ACTION_TASK_EXCEPTION:
                    //任务异常
                case MyServer.ACTION_TASK_REPORT_WORK:
                    //任务报工
                case MyServer.ACTION_TASK_RECIVER_MATERAIL:
                    //接收物料
                case MyServer.ACTION_TASK_UPDATE_FOLLOW:
                    //关注物料变更
                    synOpreatTaskFragment(messageEventBean);
                    break;
                case MyServer.ACTION_PRODUCT_PROP_CHECK:
                    //显示弹框
                    String message = messageEventBean.getMessage();
                    showPropCheckDialog(message);
                    break;
            }
        }
    }

    private void showPropCheckDialog(String message) {
        WSDialogUtils.showPropCheckDialog(this, "", message, new WSDialogCallBackTwo() {
            @Override
            public void OnPositiveClick(Object obj) {

            }

            @Override
            public void OnNegativeClick() {

            }
        });
    }

    private void refreshTaskList() {
        WSTaskListFragment wsTaskListFragment = findFragment(WSTaskListFragment.class);
        if (wsTaskListFragment != null) {
            wsTaskListFragment.refreshData();
        }

    }

    private void quitMainActivity() {
        AppManager.getAppManager().finishOtherActivity(WSMainActivity.this);
    }

    private void showPushOneDialog(String title, int code) {
        if (wsPushOneDialog != null && wsPushOneDialog.getTitle().equals(title) && wsPushOneDialog.isShowing()) {
            return;
        }
        wsPushOneDialog = WSDialogUtils.showPushOneDialog(this, title, code, new WSDialogCallBackTwo() {
            @Override
            public void OnPositiveClick(Object obj) {

            }

            @Override
            public void OnNegativeClick() {

            }
        });
    }

    private void initData() {
        HashMap<String, String> params = new HashMap<>();
        params.put("mac", UICommonUtil.getAdresseMAC(this));
        currentPresent.getStationName(params);
    }

    private void testServer() {
        HashMap<String, String> params = new HashMap<>();
        params.put("mac", UICommonUtil.getAdresseMAC(this));
        currentPresent.testServer(params);
    }

    @Override
    public void showTestServerResult(boolean result) {
        if (result) {
            startUi();
        } else {
            showModifiHostUrlDialog();
        }
    }

    private void opreatTaskFragment(MessageEventBean messageEventBean) {
        ISupportFragment topFragment = getTopFragment();
        if (topFragment != null && topFragment instanceof WSTaskFragment) {
            WSTaskFragment fragment = (WSTaskFragment) topFragment;
            int code = messageEventBean.getRefreshType();
            if (code == MyServer.ACTION_ORDER_PAUSE || code == MyServer.ACTION_ORDER_REAGIN) {
                fragment.toPauseStatus(messageEventBean.getRefreshType(), messageEventBean.getMessage());
            } else if (code == MyServer.ACTION_ORDER_END) {
                fragment.toEndStatus(messageEventBean.getMessage());
            }
            return;
        }

        if (topFragment != null && topFragment instanceof WSTaskListFragment) {
            refreshTaskList();
        }

    }

    private void synOpreatTaskFragment(MessageEventBean messageEventBean) {
        WSTaskFragment wsTaskFragment = findFragment(WSTaskFragment.class);
        if (wsTaskFragment != null) {
            wsTaskFragment.synTaskOpreat(messageEventBean);
        }
        if (messageEventBean.getType() == MyServer.ACTION_TASK_END) {
            //任务结束刷新任务列表
            WSTaskListFragment wsTaskListFragment = findFragment(WSTaskListFragment.class);
            if (wsTaskListFragment != null) {
                wsTaskListFragment.refreshCurrent();
            }
        }
    }

    @Override
    protected void creatPresent() {
        currentPresent = new WSMainPresent();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.ws_activity_main;
    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    protected void onResume() {
        super.onResume();
        isResume = true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        isResume = false;
    }

    @Override
    protected void onDestroy() {
        if (connection != null) {
            if (isBind) {
                unbindService(connection);
                isBind = false;
            }
        }
        handler.removeCallbacksAndMessages(null);
        handler = null;
        if (myServer != null) {
            myServer.stop();
        }
        if (mNetworkConnectChangedReceiver != null) {
            unregisterReceiver(mNetworkConnectChangedReceiver);
        }
        if (mHomeKeyReceiver != null) {
            unregisterReceiver(mHomeKeyReceiver);
        }
        hideSofe();
        killHeartProcess();
        super.onDestroy();
    }

    @Override
    public void onScanResult(String scanResult) {
        ISupportFragment topFragment = getTopFragment();
        if (topFragment != null && topFragment instanceof WSBaseFragment) {
            WSBaseFragment baseFragment = (WSBaseFragment) topFragment;
            baseFragment.setScanResult(scanResult);
        }
    }

    private void killHeartProcess() {
        ShellUtil.CommandResult commandResult = ShellUtil.execCommand(commads, true, true);
    }

    private void initView() {
        setControlDrawLayout(true);
        ivLogoOrReturn.setTag(0);
        personsIdBuffer = new StringBuffer();
//        navView.setNavigationItemSelectedListener(this);
//        View headerView = navView.getHeaderView(0);
//        iv_nav_user_photo = headerView.findViewById(R.id.iv_nav_user_photo);
//        tv_nav_user_name = headerView.findViewById(R.id.tv_nav_user_name);
        tvDate.setText(TimerUtils.getNowDate());
        tvTimer.setText(TimerUtils.getCurrentTimer24());
        recycleWokerPhoto.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mAdapterWorkerPhoto = new WSAdapterWorkerPhoto();
        recycleWokerPhoto.setAdapter(mAdapterWorkerPhoto);
        mAdapterWorkerPhoto.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                WSPerson person = (WSPerson) adapter.getData().get(position);
                if (currentPerson != null && currentPerson.getPersonId() != person.getPersonId()) {
                    WSDialogUtils.showChangeWorkerDialog(WSMainActivity.this, "确定要切换为" + person.getName() + "用户吗?", new WSDialogCallBackTwo() {
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
        ivLogoOrReturn.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ToastUtil.showInfoCenterShort("当前的版本:" + AppUtils.getVersionCode(WSBaseApplication.getInstance()));
                return false;
            }
        });
        registerNetChangeReciver();
        registerHomeKeyReceiver();
//        RxView.clicks(ivLogoOrReturn).throttleFirst(1000, TimeUnit.MILLISECONDS).subscribe(new Consumer<Object>() {
//            @Override
//            public void accept(Object o) throws Exception {
//                Integer tag = (Integer) ivLogoOrReturn.getTag();
//                if (tag == 1) {
////                    setFragmentBack();
//                }
//            }
//        });
    }

    private void checkNetRequestData() {
        NotifyBean bean = new NotifyBean();
        if (AppUtils.isNetwork(WSBaseApplication.getInstance())) {
            if (!isFirstStart) {
                bean.setTitle("网络连接正常");
                bean.setMessage("网络已连接正常!");
                setNotify(bean);
            }
            NetStatusUtil.getInstance().setWifi(true);
            String[] splitIP = WSURLBuilder.getDomain().split(":");
//            if (!PingUtil.pingIpAddress(splitIP[0])) {
//                showModifiHostUrlDialog();
//            } else {
            testServer();
//            }

//            if (isFirstStart) {
//                initOthers();
//            }
//            checkApkVersion();
        } else {
            NetStatusUtil.getInstance().setWifi(false);
            if (!isFirstStart) {
                bean.setTitle("网络连接异常");
                bean.setMessage("请检查网络!");
                setNotify(bean);
            }
            ToastUtil.showInfoCenterShort("网络连接异常");
        }
    }

    private void startUi() {
        if (isFirstStart) {
            initOthers();
        }
        checkApkVersion();
    }

    private void showModifiHostUrlDialog() {
        LibraryDialogUtils.showSetDomainDialog(this, WSURLBuilder.getDomain(), "请检查服务器地址", new ResourceDialogCallBackTwo() {
            @Override
            public void OnPositiveClick(Object obj) {
                //重启
                if (obj != null && obj instanceof String) {
                    WSURLBuilder.setDomain((String) obj);
                }
                AppUtils.restartApp(WSSplashActivity.class);
                finish();
//                gotoSplash();
            }

            @Override
            public void OnNegativeClick() {
                finish();
            }
        });
        return;
    }

    public void setControlDrawLayout(boolean isClose) {
        if (isClose) {
            //禁止手势滑动
            drawLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        } else {
            //打开手势滑动
//            drawLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            drawLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        }
    }

    private void setCurrentPersonName() {
        if (currentPerson != null) {
            tvWorkerNameCurrent.setText(currentPerson.getName());
            SharedPreferencesUitl.saveStringData(SharePreferenceKeyUtil.PERSON_ID, currentPerson.getPersonId());
            WSUserManager.getInstance().setLoginStatus(true);
//            tv_nav_user_name.setText("已登录");
//            ImageLoader.loadImage(this, currentPerson.getUserPic(), R.drawable.ic_main_user, ivMainUserPhoto);
            ImageLoader.loadImage(this, currentPerson.getUserPic(), R.drawable.ic_main_user, new MyImageViewTarget(ivMainUserPhoto, 115, 115));
            tvPersonTime.setVisibility(View.VISIBLE);
            updatePersonTime(0);
            WSUserManager.getInstance().setCurrentUser(currentPerson);
            WSUserManager.getInstance().setCurrentUserName(currentPerson.getName());
            WSReportWorkFragment wsReportWorkFragment = findFragment(WSReportWorkFragment.class);
            if (wsReportWorkFragment != null) {
                wsReportWorkFragment.setReportName();
            }
        } else {
            WSUserManager.getInstance().setCurrentUser(null);
            tvWorkerNameCurrent.setText("请先登录");
            ivMainUserPhoto.setImageResource(R.drawable.ic_main_user);
            tvPersonTime.setVisibility(View.GONE);
            SharedPreferencesUitl.saveStringData(SharePreferenceKeyUtil.PERSON_ID, "");
            WSUserManager.getInstance().setLoginStatus(false);
//            iv_nav_user_photo.setImageResource(R.drawable.ic_main_user);
//            tv_nav_user_name.setText("未登录");
            WSUserManager.getInstance().setCurrentUserName("");
            quitTaskListFragment();
        }
        if (mAdapterWorkerPhoto.getData() != null && mAdapterWorkerPhoto.getData().size() > 1) {
            recycleWokerPhoto.setVisibility(View.VISIBLE);
        } else {
            recycleWokerPhoto.setVisibility(View.INVISIBLE);
        }

        updateHeartBean();
    }

    private void registerNetChangeReciver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        filter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
        filter.addAction("android.net.wifi.STATE_CHANGE");
        mNetworkConnectChangedReceiver = new NetworkConnectChangedReceiver();
        registerReceiver(mNetworkConnectChangedReceiver, filter);
    }

    private void registerHomeKeyReceiver() {
        mHomeKeyReceiver = new HomeWatcherReceiver();
        final IntentFilter homeFilter = new IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);

        registerReceiver(mHomeKeyReceiver, homeFilter);
    }

    private void setNotify(NotifyBean bean) {
        NotifyUtil.getInstance().setContext(this).sendMessage(bean);
    }

    private void initOthers() {
        initFragment();
        startTimer();
        startServer();
        startHeart();
        isFirstStart = false;
    }

    private void checkApkVersion() {
        Map<String, String> params = new HashMap<>();
        params.put("type", WSAppTypeEnum.ANDROID_WORK_STATION + "");
        currentPresent.checkApkVersion(params);
    }

    private void updatePersonTime(long spaceTime) {
        if (spaceTime > 0) {
            List<WSPerson> data = mAdapterWorkerPhoto.getData();
            if (data != null && data.size() > 0) {
                for (int i = 0; i < data.size(); i++) {
                    WSPerson wsPerson = data.get(i);
                    wsPerson.setSecondsDate(wsPerson.getSecondsDate() + spaceTime);
                }
            }
        }
        if (currentPerson != null && tvPersonTime.getVisibility() == View.VISIBLE) {
            tvPersonTime.setText(TimerUtils.getThreeStringTime(currentPerson.getSecondsDate()));
        }

    }

    private void quitTaskListFragment() {
        if (NoDoubleClickUtil.doubleClick(800)) {
            //防止快速pop 出现异常
            return;
        }
        ISupportFragment topFragment = getTopFragment();
        if (!(topFragment instanceof WSTaskListFragment)) {
            if (isResume) {
                popTo(WSTaskListFragment.class, false);
            }
        }
    }

    private void updateHeartBean() {
        if (iHeart != null) {
            try {
                HeartBean heartBean = new HeartBean();
                heartBean.setClientIP(WSConstants.getClientIp());
                heartBean.setPersonsId(getPersonsId());
                heartBean.setPersonId(WSUserManager.getInstance().getPersonId());
                if (WSUserManager.getInstance().getWorkStationVo() != null) {
                    heartBean.setWorkStationId(WSUserManager.getInstance().getWorkStationVo().getWorkStationId());
                }
                iHeart.updateHeartBean(heartBean);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    private void initFragment() {
        mTaskListFragment = findFragment(WSTaskListFragment.class);
        if (mTaskListFragment == null) {
            mTaskListFragment = WSTaskListFragment.newInstance();
            loadRootFragment(R.id.frame_content, mTaskListFragment);
        } else {
            mTaskListFragment = findFragment(WSTaskListFragment.class);
        }
    }

    private void startTimer() {
        //开启定时更新时间

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                String currentTimer = TimerUtils.getCurrentTimer24();
                Message obtain = Message.obtain();
                Bundle bundle = new Bundle();
                bundle.putString("currentTimer", currentTimer);
                obtain.setData(bundle);
                obtain.what = UPDATE_TIME;
                handler.sendMessage(obtain);
            }
        }, 30 * 1000);
    }

    private void startServer() {
        try {
            myServer = new MyServer();
            myServer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送心跳
     */
    private void startHeart() {
        Intent intent = new Intent(this, HeartBeatService.class);
        bindService(intent, connection, Context.BIND_AUTO_CREATE);
////        if (WSUserManager.getInstance().getLoginStatus()) {
//        Map<String, String> params = new HashMap<>();
//        params.put("personsId", getPersonsId());
//        currentPresent.requestHeart(params);
////        }
//        handler.sendEmptyMessageDelayed(SEND_HEART, 10 * 1000);
    }

    private String getPersonsId() {
        personsIdBuffer.setLength(0);
        List<WSPerson> data = mAdapterWorkerPhoto.getData();
        for (int i = 0; i < data.size(); i++) {
            WSPerson person = data.get(i);
            personsIdBuffer.append(person.getPersonId());
            personsIdBuffer.append(",");
        }
        if (personsIdBuffer.length() > 0) {
            personsIdBuffer.deleteCharAt(personsIdBuffer.length() - 1);
        }
        return personsIdBuffer.toString();
    }

    private void notifyLoginChange(MessageEventBean messageEventBean) {
        NotifyBean notifyBean = new NotifyBean();
        notifyBean.setMessage(messageEventBean.getMessage());
        notifyBean.setTitle("下线通知");
        NotifyUtil.getInstance().setContext(this).sendMessage(notifyBean);
    }

    // tag 0: logo 1:返回
    public void setActivityLogoOrBack(int tag) {
//        Integer ivTag = (Integer) ivLogoOrReturn.getTag();
//        if (ivTag == tag) {
//            return;
//        }
//        switch (tag) {
//            case 0:
//                ivLogoOrReturn.setImageResource(R.drawable.elco_logo2);
//                break;
//            case 1:
//                ivLogoOrReturn.setImageResource(R.drawable.selector_back);
//                break;
//        }
//
//        ivLogoOrReturn.setTag(tag);

    }

    @OnClick({R.id.btn_login_two, R.id.btn_quit_two, R.id.tv_station_position, R.id.rl_station_position, R.id.ll_wran, R.id.tv_warn, R.id.iv_main_user_photo})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_login_two:
                //扫码登录
                Bundle bundle = new Bundle();
                bundle.putInt("loginType", 0);
                StartIntentUtils.startIntentUtilsFromResult(WSMainActivity.this, WSActivityLogin.class, WSConstants.LOGIN_ACTIVITY, bundle);
                break;
            case R.id.btn_quit_two:
                //扫码退出
                if (WSUserManager.getInstance().getLoginStatus()) {
                    Bundle bundle2 = new Bundle();
                    bundle2.putInt("loginType", 2);
                    StartIntentUtils.startIntentUtilsFromResult(WSMainActivity.this, WSActivityLogin.class, WSConstants.LOGIN_ACTIVITY, bundle2);
                } else {
                    ToastUtil.showCenterShort(getString(R.string.login_out_un), true);
                }
                break;
            case R.id.rl_station_position:
            case R.id.tv_station_position:
//                getSupportDelegate().showFragmentStackHierarchyView();
                getStationDetailPopup().showPopupWindow(true);
                break;
            case R.id.tv_warn:
                toWran();
                break;
            case R.id.iv_main_user_photo:
                if (WSUserManager.getInstance().getLoginStatus()) {
                    requestUserReportDetail();
                } else {
                    ToastUtil.showCenterShort(getString(R.string.text_un_login), true);
                }
                break;
        }
    }

    private void requestUserReportDetail() {
        Map<String, String> params = new HashMap<>();
        params.put("workStationId", WSUserManager.getInstance().getWorkStationId());
        currentPresent.requestUserReportDetail(params);
    }

    @Override
    public void showUserReportDetail(List<WSWorkReportRecordVO> wsWorkReportRecordVOList) {
        if (wsWorkReportRecordVOList != null) {
            if (wsWorkReportRecordVOList.size() > 0) {
                showUserRecordDetailDialog(wsWorkReportRecordVOList);
            } else {
                ToastUtil.showInfoCenterShort("暂无记录");
            }
        }
    }

    private void showUserRecordDetailDialog(List<WSWorkReportRecordVO> wsWorkReportRecordVOList) {
        if (mUserRecordDetailDialog == null) {

            mUserRecordDetailDialog = WSDialogUtils.showUserRecordDetailDialog(this, WSUserManager.getInstance().getCurrentPerson().getName() + "的报工记录", new WSDialogCallBackTwo() {
                @Override
                public void OnPositiveClick(Object obj) {

                }

                @Override
                public void OnNegativeClick() {

                }
            });
        }
        mUserRecordDetailDialog.setData(WSUserManager.getInstance().getCurrentPerson().getName() + "的报工记录", wsWorkReportRecordVOList);
        mUserRecordDetailDialog.show();
    }

    private WSStationDetailPop getStationDetailPopup() {
        if (mStationDetailPop == null) {
            mStationDetailPop = new WSStationDetailPop(this, new WSDialogCallBackTwo() {
                @Override
                public void OnPositiveClick(Object obj) {
//                    if (WSUserManager.getInstance().getCurrentPerson() != null) {
                    getStationList();
//                    } else {
//                        ToastUtil.showInfoCenterShort(getResources().getString(R.string.text_un_login));
//                    }
                }

                @Override
                public void OnNegativeClick() {

                }
            });
        }
        mStationDetailPop.setData(WSUserManager.getInstance().getWorkStationVo());
        return mStationDetailPop;
    }

    /**
     * 获取工位列表
     */
    private void getStationList() {
        currentPresent.getStationList(new HashMap<>());
    }

    private void updateStation(WSWorkStation wsWorkStation) {
        Map<String, String> params = new HashMap<>();
        params.put("id", wsWorkStation.getId());

        //dhc
        WSTaskFragment taskF = findFragment(WSTaskFragment.class);

        if(taskF!=null)
        {
            taskF.quitRefresh();
        }

        currentPresent.updateStation(params);

    }

    @Override
    public void showStationList(List<WSWorkStation> wsWorkStations) {
        showUpdateStationDialog(wsWorkStations);
    }

    @Override
    public void showUpdateStationResult(boolean result) {
        if (result) {
            ToastUtil.showInfoCenterShort("工位变更成功");
            if (wsUpdateStationDialog != null) {
                wsUpdateStationDialog.dismiss();
            }
            if (mStationDetailPop != null) {
                mStationDetailPop.dismiss();
            }
            quitMainActivity();
            initData();
            refreshTaskList();

        }
    }

    private void showUpdateStationDialog(List<WSWorkStation> wsWorkStations) {
        if (wsWorkStations != null) {
            if (wsWorkStations.size() == 0) {
                ToastUtil.showInfoCenterShort("没有可更换的工位");
                return;
            }
            if (wsUpdateStationDialog == null) {
                wsUpdateStationDialog = WSDialogUtils.showUpdateStationDialog(this, "请选择更换的工位", new WSDialogCallBackTwo() {
                    @Override
                    public void OnPositiveClick(Object obj) {
                        if (obj != null && obj instanceof WSWorkStation) {
                            WSWorkStation wsWorkStation = (WSWorkStation) obj;
                            updateStation(wsWorkStation);
                        }
                    }

                    @Override
                    public void OnNegativeClick() {

                    }
                });
            }
            wsUpdateStationDialog.setData(wsWorkStations);
            wsUpdateStationDialog.show();
        }
    }

    private void toWran() {
        ISupportFragment topFragment = getTopFragment();
        if (topFragment != null && topFragment instanceof WSTaskFragment) {
            ((WSTaskFragment) topFragment).startWran();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);//!!!!!!
        switch (requestCode) {
            case WSConstants.LOGIN_ACTIVITY:
                //登陆界面过来的
                if (data != null) {
                    showLoginResult(data);
                }
                break;
            case REQUEST_CODE_RECIVER_MATERAIL:
            case REQUEST_CODE_RECIVER_MAKING:
            case REQUEST_CODE_RECIVER_MATERAIL_AND_MAKING:
                if (data != null) {
                    boolean isRefresh = data.getBooleanExtra("isRefresh", false);
                    boolean isToDetail = data.getBooleanExtra("isToDetail", false);
                    String trayCode = data.getStringExtra("trayCode");
                    String taskId = data.getStringExtra("taskId");
                    if (isRefresh) {
                        //1.当前在任务详情界面 如果是同一订单 刷新
                        //如果不是同一订单 进入新的任务详情
                        //2.当前在任务列表页面 直接进入任务详情
                        ISupportFragment topFragment = getTopFragment();
                        if (topFragment instanceof WSTaskFragment) {
                            if (((WSTaskFragment) topFragment).isSameOrder(taskId)) {
                                ((WSTaskFragment) topFragment).initData();
                            } else {
                                //根据托盘码查询物料 然后进入任务详情
                                if (isToDetail) {
                                    getReciveredTrayInfo(trayCode, WSErrorCode.CODE_RECIVER_M_TO_ENTRY_DETAIL);
                                }
                            }
                        } else if (topFragment instanceof WSTaskListFragment) {
                            if (isToDetail) {
                                getReciveredTrayInfo(trayCode, WSErrorCode.CODE_RECIVER_M_TO_ENTRY_DETAIL);
                            }
                        }

                        //刷新
//                        WSTaskListFragment wsTaskListFragment = findFragment(WSTaskListFragment.class);
//                        if (wsTaskListFragment != null) {
//                            wsTaskListFragment.initData();
//                        }
                    }
                }
                break;
            case LOOK_BOM_DETAIL:
                if (data != null) {
                    boolean isRefresh = data.getBooleanExtra("isRefresh", false);
                    if (isRefresh) {
                        ISupportFragment topFragment = getTopFragment();
                        if (topFragment != null && topFragment instanceof WSTaskFragment) {
                            ((WSTaskFragment) topFragment).getFollowData(true);
                        }
                    }
                }
                break;
        }


    }

    private void showLoginResult(Intent data) {
        int type = data.getIntExtra("type", 1);
        WSPerson person = (WSPerson) data.getSerializableExtra("person");
        if (type == 1) {
            //登录显示 最后一个登录的人
            List<WSPerson> personList = new ArrayList<>();
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
                List<WSPerson> data1 = mAdapterWorkerPhoto.getData();
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
            List<WSPerson> data1 = mAdapterWorkerPhoto.getData();
            for (int i = 0; i < data1.size(); i++) {
                WSPerson person1 = data1.get(i);
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
        int loginSize = mAdapterWorkerPhoto.getData().size();
        if (loginSize <= 1 && type == 2) {
            currentPresent.getStationAllPerson(true);
        } else {
            currentPresent.getStationAllPerson(false);
        }
    }

    @Override
    public void showStation(WSWorkStationVo workStationVo) {
        if (workStationVo != null) {
            WSUserManager.getInstance().setWorkStationVo(workStationVo);
            String newDate = workStationVo.getNewDate();
//            if (!TextUtils.isEmpty(newDate)) {
//                newDate = newDate.split("_")[0];
//            }
//            tvDate.setText(newDate);
            tvStationPosition.setText(workStationVo.getWorkStationName());
            SharedPreferencesUitl.saveStringData(SharePreferenceKeyUtil.STATION_NAME, workStationVo.getWorkStationName());
            SharedPreferencesUitl.saveStringData(SharePreferenceKeyUtil.STATION_ID, workStationVo.getWorkStationId());
        }
        currentPresent.getStationAllPerson(true);
    }

    @Override
    public void showReciveredTrayInfo(WSWorkStationTrayTaskMainInfoVo wsWorkStationTrayTaskMainInfoVo, String code) {
        if (wsWorkStationTrayTaskMainInfoVo != null) {
            showReciveredTrayDialog(wsWorkStationTrayTaskMainInfoVo, code);
        }
    }

    private void showReciveredTrayDialog(WSWorkStationTrayTaskMainInfoVo wsWorkStationTrayTaskMainInfoVo, String code) {
        if (!TextUtils.isEmpty(code) && code.equals(WSErrorCode.CODE_RECIVERED)) {
            //扫描工位标签
            WSDialogUtils.showReciveredTrayDialog(this, "托盘详情", wsWorkStationTrayTaskMainInfoVo, new WSDialogCallBackTwo() {
                @Override
                public void OnPositiveClick(Object obj) {
                    if (obj != null && obj instanceof WSWorkStationTask) {
                        WSWorkStationTask wsWorkStationTask = (WSWorkStationTask) obj;
                        WSTaskListFragment fragment = findFragment(WSTaskListFragment.class);
                        if (fragment != null) {
                            fragment.goTaskFragment(wsWorkStationTask);
                        }
                    }

                }

                @Override
                public void OnNegativeClick() {

                }
            });
        } else if (!TextUtils.isEmpty(code) && code.equals(WSErrorCode.CODE_RECIVER_M_TO_ENTRY_DETAIL)) {
            //接收物料 进入任务详情
            ISupportFragment topFragment = getTopFragment();
            if (topFragment instanceof WSTaskFragment) {
                ((WSTaskFragment) topFragment).pop();
            }
            WSWorkStationTask wsWorkStationTask = wsWorkStationTrayTaskMainInfoVo.getTask();
            WSTaskListFragment fragment = findFragment(WSTaskListFragment.class);
            if (fragment != null) {
                fragment.goTaskFragment(wsWorkStationTask);
            }
        }

    }

    @Override
    public void loginOutResult(boolean result) {

    }

    @Override
    public void clearTrayResult(boolean result) {

    }

    @Override
    public void showAllPersons(List<WSPerson> personList) {
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

    @Override
    public void downApkResult(boolean result, String saveUrl) {
        if (result) {
            if (wsDownApkProgressDialog != null) {
                wsDownApkProgressDialog.setInstall();
            }
            FileUtil.setupApk(this, new File(saveUrl));
        }
    }

    @Override
    public void showCheckApkInfo(WSAppUpdate wsAppUpdate) {
        if (wsAppUpdate != null) {
            int minimumVersion = 0;
            int newVersion = 0;
            WSYesOrNoEnum forceUpdate = wsAppUpdate.getForceUpdate();
            if (!TextUtils.isEmpty(wsAppUpdate.getMinimumVersion())) {
                minimumVersion = Integer.parseInt(wsAppUpdate.getMinimumVersion());
            }
            if (!TextUtils.isEmpty(wsAppUpdate.getNewestVersion())) {
                newVersion = Integer.parseInt(wsAppUpdate.getNewestVersion());
            }

            int versionCode = AppUtils.getVersionCode(WSBaseApplication.getInstance());
            if ((forceUpdate == WSYesOrNoEnum.YES) && (versionCode < minimumVersion)) {
                //强制升级 弹框升级
                showUpdateDialog("发现新版本,请升级...", wsAppUpdate);
                return;
            }
            if (versionCode < newVersion) {
                //弹框升级提示
                showUpdateDialog("发现新版本,请升级...", wsAppUpdate);
            }
        }

        initData();


    }

    private void showUpdateDialog(String title, WSAppUpdate wsAppUpdate) {
        //升级
        wsDownApkProgressDialog = WSDialogUtils.showUpdateDialog(this, title, wsAppUpdate, new WSDialogCallBackTwo() {
            @Override
            public void OnPositiveClick(Object obj) {
                //升级
                if (ContextCompat.checkSelfPermission(WSMainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    //
                    RxPermissions rxPermissions = new RxPermissions(WSMainActivity.this);
                    rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(new Consumer<Boolean>() {
                        @Override
                        public void accept(Boolean aBoolean) throws Exception {
                            if (aBoolean) {
                                downAPKStart(wsAppUpdate);
                            } else {
                                ToastUtil.showInfoCenterShort("权限拒绝，无法下载");
                            }
                        }
                    });
                } else {
                    downAPKStart(wsAppUpdate);
                }

            }

            @Override
            public void OnNegativeClick() {

            }
        });
    }

    private void downAPKStart(WSAppUpdate wsAppUpdate) {
        String saveFilePath = SDCardHelper.getSDCardPrivateFilesDir(this, null) + "/updateApk";
        currentPresent.downLoadApk(WSMainActivity.this, wsAppUpdate.getUrl(), saveFilePath, wsDownApkProgressDialog);
    }

    public void opreationScanResult(WSTrayLoadInfo trayLoadInfo) {
        String code = trayLoadInfo.getCode();
        if (!TextUtils.isEmpty(code) && code.equals(WSErrorCode.CODE_PRODUCT)) {
            //扫描生产编号进行搜索
            String oneDemensionCode = trayLoadInfo.getTray().getOneDemensionCode();
            WSTaskListFragment fragment = findFragment(WSTaskListFragment.class);
            if (fragment != null) {
                fragment.getDataBySearchTask(oneDemensionCode);
            }
            return;
        }
        if (!TextUtils.isEmpty(code) && code.equals(WSErrorCode.CODE_RECIVERED)) {
            //已接收托盘 //扫描生产编号进入任务详情
            String oneDemensionCode = trayLoadInfo.getTray().getOneDemensionCode();
            getReciveredTrayInfo(oneDemensionCode, code);
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putParcelable("data", trayLoadInfo);
        WSTrayLoadTypeEnum type = trayLoadInfo.getType();
        if (type != null) {
            switch (type) {
                case MATERIAL:
                    //物料
                    showDialogActivity(0, bundle);
                    break;
                case WIP:
                    //在制品
                    showDialogActivity(1, bundle);
                    break;
                case WIPANDMATERIAL:
                    //在制品和物料  辅助 或者返工 TODO
                    showDialogActivity(2, bundle);
                    break;
            }
        }


    }

    private void getReciveredTrayInfo(String trayCode, String code) {
        Map<String, String> params = new HashMap<>();
        params.put("trayCode", trayCode);
        currentPresent.getReciveredTrayInfo(params, code);
    }

    public void showDialogActivity(int showType, Bundle bundle) {
        switch (showType) {
            case 0:
                //接收物料
                StartIntentUtils.startIntentUtilsFromResult(WSMainActivity.this, WSReciverMaterailDialogActivity.class, REQUEST_CODE_RECIVER_MATERAIL, bundle);
                break;
            case 1:
                //接收在制品
                StartIntentUtils.startIntentUtilsFromResult(WSMainActivity.this, WSReciverMakingDialogActivity.class, REQUEST_CODE_RECIVER_MAKING, bundle);
                break;
            case 2:
                //接收物料和在制品
                StartIntentUtils.startIntentUtilsFromResult(WSMainActivity.this, WSReciverMaterailAndMakingDialogActivity.class, REQUEST_CODE_RECIVER_MATERAIL_AND_MAKING, bundle);
                break;
            case 3:
                //任务清单
                StartIntentUtils.startIntentUtilsFromResult(WSMainActivity.this, WSStationMaterailBillDialogActivity.class, 3, bundle);
                break;
        }
    }

    private WSMyWrap creatWrap() {
        WSMyWrap wsMyWrap = new WSMyWrap();
        List<WSMyMaterielVo> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            WSMyMaterielVo wsMyMaterielVo = new WSMyMaterielVo();
            wsMyMaterielVo.setCount(i);
            wsMyMaterielVo.setName("名称" + i);
            wsMyMaterielVo.setModel("编号" + i);
            list.add(wsMyMaterielVo);
        }
        wsMyWrap.setMateriels(list);
        return wsMyWrap;
    }

    private void setFragmentBack() {
        ISupportFragment topFragment = getTopFragment();
        if (topFragment != null && topFragment instanceof WSBaseFragment) {
            WSBaseFragment baseFragment = (WSBaseFragment) topFragment;
//            baseFragment.setFragmentBack();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        ISupportFragment topFragment1 = getTopFragment();
        switch (item.getItemId()) {
            case R.id.item_ws_list:
                if (!(topFragment1 instanceof WSTaskListFragment)) {
                    if (topFragment1 instanceof WSClearTrayFragment) {
                        BackRunnumble backRunnumble = new BackRunnumble();
                        popTo(WSClearTrayFragment.class, true, backRunnumble);
                    }
//                    int reportWorkFragmentIndex = 10;
//                    int exceptionFragmentIndex = 10;
//                    int clearTrayFragmentIndex = 10;
//                    WSReportWorkFragment reportWorkFragment = findFragment(WSReportWorkFragment.class);
//                    WSExceptionFragment exceptionFragment = findFragment(WSExceptionFragment.class);
//                    WSClearTrayFragment clearTrayFragment = findFragment(WSClearTrayFragment.class);
//                    ISupportFragment topFragment = getTopFragment();
//                    if (reportWorkFragment != null || exceptionFragment != null || clearTrayFragment != null) {
//                        List<Fragment> activeFragments = FragmentationHack.getActiveFragments(getSupportFragmentManager());
//                        reportWorkFragmentIndex = activeFragments.indexOf(reportWorkFragment);
//                        exceptionFragmentIndex = activeFragments.indexOf(exceptionFragment);
//                        clearTrayFragmentIndex = activeFragments.indexOf(clearTrayFragment);
//                        if (reportWorkFragmentIndex < 0) {
//                            reportWorkFragmentIndex = 10;
//                        }
//                        if (exceptionFragmentIndex < 0) {
//                            exceptionFragmentIndex = 10;
//                        }
//                        if (clearTrayFragmentIndex < 0) {
//                            clearTrayFragmentIndex = 10;
//                        }
//                        BackRunnumble backRunnumble = new BackRunnumble();
//                        //取出最小数
//                        Integer[] numbers = {reportWorkFragmentIndex, exceptionFragmentIndex, clearTrayFragmentIndex};
//                        Integer min = Collections.min(Arrays.asList(numbers));
//                        if (min == reportWorkFragmentIndex) {
////                            popTo(WSReportWorkFragment.class, true,backRunnumble);
//                        } else if (min == exceptionFragmentIndex) {
////                            popTo(WSExceptionFragment.class, true,backRunnumble);
//                        } else if (min == clearTrayFragmentIndex) {
//                            popTo(WSClearTrayFragment.class, true,backRunnumble);
//                        }
//                    }
                }
                break;
            case R.id.item_report_work:
                if (!WSUserManager.getInstance().getLoginStatus()) {
                    ToastUtil.showCenterShort(getString(R.string.text_un_login), true);
                    break;
                }
                if (!(topFragment1 instanceof WSReportWorkFragment)) {
                    final WSReportWorkFragment fragment = findFragment(WSReportWorkFragment.class);
                    if (fragment != null) {
                        popTo(WSReportWorkFragment.class, false, new Runnable() {
                            @Override
                            public void run() {
                                fragment.initData();
                            }
                        });
                    } else {
//                        start(WSReportWorkFragment.newInstance());
                    }

                }

                break;
            case R.id.item_exception:
                if (!WSUserManager.getInstance().getLoginStatus()) {
                    ToastUtil.showCenterShort("您尚未登陆,请先登陆", true);
                    break;
                }
                if (!(topFragment1 instanceof WSExceptionFragment)) {
                    final WSExceptionFragment fragment = findFragment(WSExceptionFragment.class);
                    if (fragment != null) {
                        popTo(WSExceptionFragment.class, false, new Runnable() {
                            @Override
                            public void run() {
                                fragment.initData();
                            }
                        });
                    } else {
//                        start(WSExceptionFragment.newInstance());
                    }

                }
                break;
            case R.id.item_clear_tray:
                if (!WSUserManager.getInstance().getLoginStatus()) {
                    ToastUtil.showCenterShort("您尚未登陆,请先登陆", true);
                    break;
                }
                if (!(topFragment1 instanceof WSClearTrayFragment)) {
                    final WSClearTrayFragment fragment = findFragment(WSClearTrayFragment.class);
                    if (fragment != null) {
                        popTo(WSClearTrayFragment.class, false, new Runnable() {
                            @Override
                            public void run() {
                                fragment.initData();
                            }
                        });
                    } else {
                        start(WSClearTrayFragment.newInstance());
                    }

                }
                break;
        }
        drawLayout.closeDrawers();
        return false;
    }

    class BackRunnumble implements Runnable {

        @Override
        public void run() {
            ISupportFragment topFragment = getTopFragment();
            if (topFragment != null && topFragment instanceof WSBaseFragment) {
                WSBaseFragment baseFragment = (WSBaseFragment) topFragment;
                baseFragment.setActivityLogoOrBack();
            }
        }
    }

}
