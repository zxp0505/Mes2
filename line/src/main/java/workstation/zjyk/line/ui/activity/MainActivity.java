package workstation.zjyk.line.ui.activity;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentationHack;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import cn.com.ethank.mylibrary.resourcelibrary.common_util.AppUtils;
import cn.com.ethank.mylibrary.resourcelibrary.common_util.SerializeUtil;
import cn.com.ethank.mylibrary.resourcelibrary.common_util.SharePreferenceKeyUtil;
import cn.com.ethank.mylibrary.resourcelibrary.common_util.ShellUtil;
import cn.com.ethank.mylibrary.resourcelibrary.common_util.printer.BitmapBean;
import cn.com.ethank.mylibrary.resourcelibrary.common_util.printer.PrinterUtil;
import cn.com.ethank.mylibrary.resourcelibrary.file.FileUtil;
import cn.com.ethank.mylibrary.resourcelibrary.glide.ImageLoader;
import cn.com.ethank.mylibrary.resourcelibrary.intent.StartIntentUtils;
import cn.com.ethank.mylibrary.resourcelibrary.netstatus.NetStatusUtil;
import cn.com.ethank.mylibrary.resourcelibrary.netstatus.NetworkConnectChangedReceiver;
import cn.com.ethank.mylibrary.resourcelibrary.server.MessageEventBean;
import cn.com.ethank.mylibrary.resourcelibrary.server.MyServer;
import cn.com.ethank.mylibrary.resourcelibrary.shareutils.SharedPreferencesUitl;
import cn.com.ethank.mylibrary.resourcelibrary.toast.ToastUtil;
import cn.com.ethank.mylibrary.resourcelibrary.utils.NoDoubleClickUtil;
import cn.com.ethank.mylibrary.resourcelibrary.utils.SDCardHelper;
import cn.com.ethank.mylibrary.resourcelibrary.utils.UICommonUtil;
import cn.com.ethank.mylibrary.resourcelibrary.utils.notify.NotifyBean;
import cn.com.ethank.mylibrary.resourcelibrary.utils.notify.NotifyUtil;
import cn.com.ethank.ui.common.dialog.LibraryDialogUtils;
import cn.com.ethank.ui.common.dialog.callback.ResourceDialogCallBackTwo;
import io.reactivex.functions.Consumer;
import me.yokeyword.fragmentation.ISupportFragment;
import me.yokeyword.fragmentation.SupportFragment;
import workstation.zjyk.line.R;
import workstation.zjyk.line.modle.BaseRequest;
import workstation.zjyk.line.modle.bean.AppTypeEnum;
import workstation.zjyk.line.modle.bean.AppUpdate;
import workstation.zjyk.line.modle.bean.Person;
import workstation.zjyk.line.modle.bean.WorkStationVo;
import workstation.zjyk.line.modle.bean.Wrap;
import workstation.zjyk.line.modle.bean.YesOrNoEnum;
import workstation.zjyk.line.ui.BaseApplication;
import workstation.zjyk.line.ui.adapter.AdapterWorkerPhoto;
import workstation.zjyk.line.ui.fragments.BaseFragment;
import workstation.zjyk.line.ui.fragments.BondOrderFragment;
import workstation.zjyk.line.ui.fragments.DeliveryListFragment;
import workstation.zjyk.line.ui.fragments.ExceptionReportFragment;
import workstation.zjyk.line.ui.fragments.FeedFragment;
import workstation.zjyk.line.ui.fragments.FeedOneFragment;
import workstation.zjyk.line.ui.fragments.InWareHouseFragment;
import workstation.zjyk.line.ui.fragments.InventoryControlFragment;
import workstation.zjyk.line.ui.fragments.UnusalFragment;
import workstation.zjyk.line.ui.present.MainPresent;
import workstation.zjyk.line.ui.views.MainView;
import workstation.zjyk.line.util.Constants;
import workstation.zjyk.line.util.DialogCallBackTwo;
import workstation.zjyk.line.util.URLBuilder;
import workstation.zjyk.line.util.dialog.DeliverySuccessDialog;
import workstation.zjyk.line.util.dialog.DialogUtils;
import workstation.zjyk.line.util.dialog.DownApkProgressDialog;
import workstation.zjyk.workstation.HeartBean;
import workstation.zjyk.workstation.HeartBeatService;
import workstation.zjyk.workstation.IHeart;


public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener, MainView {

    @BindView(R.id.tv_main_time)
    TextView tvMainTime;
    @BindView(R.id.tv_main_station_name)
    TextView tvMainStationName;
    @BindView(R.id.tv_main_station_ip)
    TextView tvMainStationIp;
    @BindView(R.id.tv_main_location)
    TextView tvMainLocation;
    @BindView(R.id.iv_main_user_photo)
    ImageView ivMainUserPhoto;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.tv_main_task_null)
    TextView tvMainTaskNull;
    @BindView(R.id.frame_content)
    FrameLayout frameContent;
    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.draw_layout)
    DrawerLayout drawLayout;
    @BindView(R.id.tv_reciver_text)
    TextView tvReciverText;
    @BindView(R.id.tv_station_position)
    TextView tvStationPosition;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.tv_timer)
    TextView tvTimer;
    @BindView(R.id.img_openDrawer)
    ImageView imgOpenDrawer;
    @BindView(R.id.btn_scan_code_login)
    Button btnScanCodeLogin;
    @BindView(R.id.btn_manual_login)
    Button btnManualLogin;
    @BindView(R.id.tv_worker_name)
    TextView tvWorkerName;
    @BindView(R.id.tv_worker_number)
    TextView tvWorkerNumber;
    @BindView(R.id.tv_login_time_first)
    TextView tvLoginTimeFirst;
    @BindView(R.id.tv_login_time_last)
    TextView tvLoginTimeLast;
    @BindView(R.id.ll_unlogin_status)
    LinearLayout llUnloginStatus;
    @BindView(R.id.ll_login_staus)
    LinearLayout llLoginStaus;
    @BindView(R.id.btn_quit)
    Button btnQuit;

    @BindView(R.id.btn_login_two)
    Button btnLoginTwo;
    @BindView(R.id.btn_quit_two)
    Button btnQuitTwo;
    @BindView(R.id.recycle_woker_photo)
    RecyclerView recycleWokerPhoto;
    @BindView(R.id.tv_worker_name_current)
    TextView tvWorkerNameCurrent;
    private FragmentManager mSupportFragmentManager;
    //    private ReciverFragment mReciverFragment;
    private FeedFragment mFeedFragment;
    int currentFragment = 0;//记录当前的显示的fragment  0  reciverfragment 1:feedFragment
    private FeedOneFragment mFeedOneFragment;
    private MainPresent mMainPresent;
    private static final int UPDATE_TIME = 0;//更新时间
    private static final int SEND_HEART = 1;//发送心跳
    boolean isUpdateTime = true;
    boolean isFirstStart = true;//记录是否是首次启动
    int currentMemuItem = 0;
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
    private TextView tv_nav_username;
    private DeliverySuccessDialog deliverySuccessDialog;
    private AdapterWorkerPhoto mAdapterWorkerPhoto;
    private MyServer myServer;
    private Person currentPerson;
    private ImageView iv_nav_user_photo;
    private StringBuffer personsIdBuffer;
    private NetworkConnectChangedReceiver mNetworkConnectChangedReceiver;
    private IHeart iHeart;
    private DownApkProgressDialog downApkProgressDialog;
    private BondOrderFragment mBondOrderFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMainPresent = new MainPresent(this);
        initView();
//        checkLoginStatus();
        initFragment();
        startTimer();
        startServer();
        startHeart();
        checkNetRequestData();
        registerNetChangeReciver();
        isFirstStart = false;
    }

    private void checkNetRequestData() {
        NotifyBean bean = new NotifyBean();
        if (AppUtils.isNetwork(BaseApplication.getInstance())) {
            if (!isFirstStart) {
                bean.setTitle("网络连接正常");
                bean.setMessage("网络已连接正常!");
                setNotify(bean);
            }
            NetStatusUtil.getInstance().setWifi(true);
            testServer();
//            checkApkVersion();
//            refreshWrapList();

        } else {
            NetStatusUtil.getInstance().setWifi(false);
            bean.setTitle("网络连接异常");
            bean.setMessage("请检查网络!");
            setNotify(bean);
//            DialogUtils.showNetUneableDialog(this, "当前网络不可用,请设置网络", new DialogCallBackTwo() {
//                @Override
//                public void OnPositiveClick(Object obj) {
//                    AppUtils.toSetWifiSurface(MainActivity.this);
//                }
//
//                @Override
//                public void OnNegativeClick() {
//
//                }
//            });
        }
    }

    private void testServer() {
        HashMap<String, String> params = new HashMap<>();
        params.put("mac", UICommonUtil.getAdresseMAC(this));
        mMainPresent.testServer(params);
    }

    @Override
    public void showTestServerResult(boolean result) {
        if (result) {
            startUi();
        } else {
            showModifiHostUrlDialog();
        }
    }

    private void startUi() {
        checkApkVersion();
        refreshWrapList();
    }

    private void showModifiHostUrlDialog() {
        LibraryDialogUtils.showSetDomainDialog(this, URLBuilder.getDomain(), "请检查服务器地址", new ResourceDialogCallBackTwo() {
            @Override
            public void OnPositiveClick(Object obj) {
                //重启
                if (obj != null && obj instanceof String) {
                    URLBuilder.setDomain((String) obj);
                }
                AppUtils.restartApp(SplashActivity.class);
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

    private void setNotify(NotifyBean bean) {
        NotifyUtil.getInstance().setContext(this).sendMessage(bean);
    }

    private void registerNetChangeReciver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        filter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
        filter.addAction("android.net.wifi.STATE_CHANGE");
        mNetworkConnectChangedReceiver = new NetworkConnectChangedReceiver();
        registerReceiver(mNetworkConnectChangedReceiver, filter);
    }

    @Override
    public void onEvent(MessageEventBean messageEventBean) {
        if (messageEventBean.getType() == 1) {
            switch (messageEventBean.getRefreshType()) {
                case 1:
                    //刷新登录
                    mMainPresent.getStationAllPerson(false);
                    break;
                case 2:
                    //刷新包裹
                    refreshWrapList();
                    break;
                case 3:
                    checkNetRequestData();
                    break;
                case MyServer.ACTION_TO_OFFLINE:
                    //下线推送
                    //1.获取在线人员信息
                    showPushOneDialog("登录超时,请重新登录...", MyServer.ACTION_TO_OFFLINE);
                    mMainPresent.getStationAllPerson(false);
                    break;
                case MyServer.ACTION_LOGIN_CHANG:
                    //异地登录
                    notifyLoginChange(messageEventBean);
                    break;
                case MyServer.ACTION_TASK_COME:
                    //置顶 加急
                    notifyOrderList();
                    break;
            }
        }

    }

    private void notifyOrderList() {
        BondOrderFragment bondOrderFragment = findFragment(BondOrderFragment.class);
        if (bondOrderFragment != null) {
            bondOrderFragment.pushToRefreshCurrent();
        }

    }

    private void notifyLoginChange(MessageEventBean messageEventBean) {
        NotifyBean notifyBean = new NotifyBean();
        notifyBean.setMessage(messageEventBean.getMessage());
        notifyBean.setTitle("下线通知");
        NotifyUtil.getInstance().setContext(this).sendMessage(notifyBean);
    }


    private void showPushOneDialog(String title, int code) {
        DialogUtils.showPushOneDialog(this, title, code, new DialogCallBackTwo() {
            @Override
            public void OnPositiveClick(Object obj) {

            }

            @Override
            public void OnNegativeClick() {

            }
        });
    }

    private void refreshWrapList() {
        FeedFragment feedFragment = findFragment(FeedFragment.class);
        if (feedFragment != null) {
            feedFragment.refreshData();
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

    @Deprecated
    private void checkLoginStatus() {
//        Person person = (Person) SerializeUtil.read(getFilesDir() + SerializeUtil.PATH_PERSON);
//        if (person != null && !TextUtils.isEmpty(person.getPersonId())) {
////            showLogin(person);
//        }
    }

    private void startTimer() {
        //开启定时更新时间

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                String currentTimer = getCurrentTimer();
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
//        Map<String, String> params = new HashMap<>();
//        params.put("personsId", getPersonsId());
//        mMainPresent.requestHeart(params);
//        handler.sendEmptyMessageDelayed(SEND_HEART, 30 * 1000);
        Intent intent = new Intent(this, HeartBeatService.class);
        bindService(intent, connection, Context.BIND_AUTO_CREATE);
    }

    ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            iHeart = IHeart.Stub.asInterface(service);


        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            iHeart = null;
            startHeart();
        }
    };

    private void updateHeartBean() {
        if (iHeart != null) {
            try {
                HeartBean heartBean = new HeartBean();
                heartBean.setClientIP(Constants.getClientIp());
                heartBean.setPersonsId(getPersonsId());
                heartBean.setPersonId(SharedPreferencesUitl.getStringData(SharePreferenceKeyUtil.PERSON_ID));
                String workstationId = SharedPreferencesUitl.getStringData(SharePreferenceKeyUtil.STATION_ID);
                heartBean.setWorkStationId(workstationId);
                iHeart.updateHeartBean(heartBean);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    private String getPersonsId() {
        personsIdBuffer.setLength(0);
        List<Person> data = mAdapterWorkerPhoto.getData();
        for (int i = 0; i < data.size(); i++) {
            Person person = data.get(i);
            personsIdBuffer.append(person.getPersonId());
            personsIdBuffer.append(",");
        }
        if (personsIdBuffer.length() > 0) {
            personsIdBuffer.deleteCharAt(personsIdBuffer.length() - 1);
        }
        return personsIdBuffer.toString();
    }

    private String getCurrentTimer() {
        long sysTime = System.currentTimeMillis();
        String currentTimer = DateFormat.format("hh:mm", sysTime) + "";
        GregorianCalendar ca = new GregorianCalendar();
        if (ca.get(GregorianCalendar.AM_PM) == 0) {
            currentTimer = "上午: " + currentTimer;
        } else {
            currentTimer = "下午: " + currentTimer;
        }
        return currentTimer;
    }

    private String getNowDate() {
        StringBuffer buffer = new StringBuffer();
        Calendar now = Calendar.getInstance();
        buffer.append(now.get(Calendar.YEAR) + "年").append((now.get(Calendar.MONTH) + 1) + "月")
                .append(now.get(Calendar.DAY_OF_MONTH) + "日");
        return buffer.toString();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    SupportFragment[] supportFragments = new SupportFragment[2];

    private void initFragment() {
//        mFeedFragment = findFragment(FeedFragment.class);
//        if (mFeedFragment == null) {
//            supportFragments[0] = FeedFragment.newInstance();
//            supportFragments[1] = ExceptionReportFragment.newInstance();
//            loadMultipleRootFragment(R.id.frame_content, 0, supportFragments[0], supportFragments[1]);
//        } else {
//            supportFragments[0] = mFeedFragment;
//            supportFragments[1] = findFragment(FeedFragment.class);
//        }

        if (Constants.isReciver) {
            //收料
            mFeedFragment = findFragment(FeedFragment.class);
            if (mFeedFragment == null) {
                mFeedFragment = FeedFragment.newInstance();
                loadRootFragment(R.id.frame_content, mFeedFragment);
            }
        } else {
            //分料
            mBondOrderFragment = findFragment(BondOrderFragment.class);
            if (mBondOrderFragment == null) {
                mBondOrderFragment = BondOrderFragment.newInstance();
                loadRootFragment(R.id.frame_content, mBondOrderFragment);
            }

        }


    }

    private FragmentManager getMyFragmentManager() {
        if (mSupportFragmentManager == null) {
            mSupportFragmentManager = getSupportFragmentManager();
        }
        return mSupportFragmentManager;

    }

    private void initView() {
        personsIdBuffer = new StringBuffer();
        View headerView = navView.getHeaderView(0);
        tv_nav_username = headerView.findViewById(R.id.iv_main_user_name);
        iv_nav_user_photo = headerView.findViewById(R.id.iv_nav_user_photo);
        tvTimer.setText(getCurrentTimer());
        tvDate.setText(getNowDate());
        navView.setNavigationItemSelectedListener(this);

        recycleWokerPhoto.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mAdapterWorkerPhoto = new AdapterWorkerPhoto();
        recycleWokerPhoto.setAdapter(mAdapterWorkerPhoto);
        mAdapterWorkerPhoto.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                Person person = (Person) adapter.getData().get(position);
                if (currentPerson != null && currentPerson.getPersonId() != person.getPersonId()) {
                    DialogUtils.showChangeWorkerDialog(MainActivity.this, "确定要切换为" + person.getName() + "用户吗?", new DialogCallBackTwo() {
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

    private void initData() {
        mMainPresent.getStationName();

    }

    private void checkApkVersion() {
        Map<String, String> params = new HashMap<>();
        params.put("type", AppTypeEnum.ANDROID_LINE + "");
        mMainPresent.checkApkVersion(params);
    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {

        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {

        }
    }

    @OnClick({R.id.tv_timer, R.id.img_openDrawer, R.id.tv_reciver_text, R.id.btn_scan_code_login, R.id.btn_manual_login, R.id.btn_quit, R.id.tv_station_position, R.id.btn_quit_two, R.id.btn_login_two})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.img_openDrawer:
                drawLayout.openDrawer(Gravity.LEFT);
                break;
            case R.id.tv_reciver_text:
                if (currentFragment == 0) {
                    StartIntentUtils.startIntentUtilsFromResult(MainActivity.this, ReciverMakeSureDialogActivity.class, Constants.START_FEED);
                } else {
                    StartIntentUtils.startIntentUtilsFromResult(MainActivity.this, MaterailBillActivity.class, Constants.WAREHOUSE);
                }
                break;
            case R.id.btn_login_two:
            case R.id.btn_scan_code_login:
                //扫码登陆
                Bundle bundle = new Bundle();
                bundle.putInt("loginType", 0);
                StartIntentUtils.startIntentUtilsFromResult(MainActivity.this, ActivityLogin.class, Constants.LOGIN_ACTIVITY, bundle);
                break;
            case R.id.btn_manual_login:
                //手动登陆
                Bundle bundle1 = new Bundle();
                bundle1.putInt("loginType", 1);
                StartIntentUtils.startIntentUtilsFromResult(MainActivity.this, ActivityLogin.class, Constants.LOGIN_ACTIVITY, bundle1);
                break;
            case R.id.btn_quit_two:
            case R.id.btn_quit:
                //扫码退出
                if (Constants.isLogin) {
                    Bundle bundle2 = new Bundle();
                    bundle2.putInt("loginType", 2);
                    StartIntentUtils.startIntentUtilsFromResult(MainActivity.this, ActivityLogin.class, Constants.LOGIN_ACTIVITY, bundle2);
                } else {
                    ToastUtil.showCenterShort("没有可退出的人员!", true);
                }


//                DialogUtils.showLoginOutDialog(MainActivity.this, new DialogCallBackTwo() {
//                    @Override
//                    public void OnPositiveClick(Object obj) {
//                        Map<String, String> params = new HashMap<>();
//                        params.put("personId", SharedPreferencesUitl.getStringData(SharePreferenceKeyUtil.PERSON_ID));
//                        mMainPresent.loginOut(params);
//                        loginOut();
//                    }
//
//                    @Override
//                    public void OnNegativeClick() {
//
//                    }
//                });

                break;
            case R.id.tv_station_position:
//                getSupportDelegate().showFragmentStackHierarchyView();
                break;

        }
    }


    private void testPrinter() {
        BitmapBean bean = new BitmapBean();
        List<BitmapBean.MaterBean> materBeans = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            BitmapBean.MaterBean materBean = new BitmapBean.MaterBean();
            materBean.setName("螺丝");
            materBean.setNumber(i);
            materBeans.add(materBean);
        }
        bean.setMaterDatas(materBeans);
        PrinterUtil.getInstance().printerPhoto(bean, this);
    }

    public void opreation(int currentFragment, Wrap wrap) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("data", wrap);
        showDialogActivity(currentFragment, bundle);
    }

    public void opreationMaterDetailDialogActivity() {
        showDialogActivity(1, materDetailBundle);
    }

    Bundle materDetailBundle = null;//用于异常 fragment 点击pop后 跳转到物料清单界面

    private void showDialogActivity(int currentFragment, Bundle bundle) {
        if (currentFragment == 0) {
            //收料
            StartIntentUtils.startIntentUtilsFromResult(MainActivity.this, ReciverMakeSureDialogActivity.class, Constants.RECIVER_DIALOG, bundle);
        } else if (currentFragment == 1) {
            //分料
            materDetailBundle = bundle;
            StartIntentUtils.startIntentUtilsFromResult(MainActivity.this, MaterailBillActivity.class, Constants.FEED_DIALOG, bundle);
        } else if (currentFragment == 2) {
            //投递
            StartIntentUtils.startIntentUtilsFromResult(MainActivity.this, DeliveryDialogActivity.class, Constants.DELIVERY_DIALOG, bundle);
        } else if (currentFragment == 3) {
            //异常投递
            StartIntentUtils.startIntentUtilsFromResult(MainActivity.this, ExceptionDeliveryDialogActivity.class, Constants.DELIVERY_EXCEPTION_DIALOG, bundle);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        ISupportFragment topFragment1 = getTopFragment();
        switch (item.getItemId()) {
            case R.id.item_out:
                if (!(topFragment1 instanceof FeedFragment)) {
                    int exceptionReportFragmentIndex = 10;
                    int inventoryControlFragmentIndex = 10;
                    int deliveryListFragmentIndex = 10;
                    ExceptionReportFragment exceptionReportFragment = findFragment(ExceptionReportFragment.class);
                    InventoryControlFragment inventoryControlFragment = findFragment(InventoryControlFragment.class);
                    DeliveryListFragment deliveryListFragment = findFragment(DeliveryListFragment.class);
                    ISupportFragment topFragment = getTopFragment();
                    if (exceptionReportFragment != null || inventoryControlFragment != null || deliveryListFragment != null) {
                        List<Fragment> activeFragments = FragmentationHack.getActiveFragments(getSupportFragmentManager());
                        exceptionReportFragmentIndex = activeFragments.indexOf(exceptionReportFragment);
                        inventoryControlFragmentIndex = activeFragments.indexOf(inventoryControlFragment);
                        deliveryListFragmentIndex = activeFragments.indexOf(deliveryListFragment);
                        if (exceptionReportFragmentIndex < 0) {
                            exceptionReportFragmentIndex = 10;
                        }
                        if (inventoryControlFragmentIndex < 0) {
                            inventoryControlFragmentIndex = 10;
                        }
                        if (deliveryListFragmentIndex < 0) {
                            deliveryListFragmentIndex = 10;
                        }
                        //取出最小数
                        Integer[] numbers = {exceptionReportFragmentIndex, inventoryControlFragmentIndex, deliveryListFragmentIndex};
                        Integer min = Collections.min(Arrays.asList(numbers));
                        if (min == exceptionReportFragmentIndex) {
                            popTo(ExceptionReportFragment.class, true);
                        } else if (min == inventoryControlFragmentIndex) {
                            popTo(InventoryControlFragment.class, true);
                        } else if (min == deliveryListFragmentIndex) {
                            popTo(DeliveryListFragment.class, true);
                        }
//                        if (exceptionReportFragmentIndex > inventoryControlFragmentIndex) {
//                            popTo(InventoryControlFragment.class, true);
//                        } else {
//                            popTo(ExceptionReportFragment.class, true);
//                        }
                    } else {
                        pop();
                    }
                }
                currentMemuItem = 0;
                break;
            case R.id.item_search:
                if (!Constants.isLogin) {
                    ToastUtil.showCenterShort("您尚未登陆,请先登陆", true);
                    break;
                }
                if (!(topFragment1 instanceof ExceptionReportFragment)) {
                    final ExceptionReportFragment fragment = findFragment(ExceptionReportFragment.class);
                    if (fragment != null) {
                        popTo(ExceptionReportFragment.class, false, new Runnable() {
                            @Override
                            public void run() {
                                fragment.initData();
                            }
                        });
                    } else {
                        start(ExceptionReportFragment.newInstance());
                    }

                }
                currentMemuItem = 1;
                break;
            case R.id.item_inventory_control:
                if (!Constants.isLogin) {
                    ToastUtil.showCenterShort("您尚未登陆,请先登陆", true);
                    break;
                }
                if (!(topFragment1 instanceof InventoryControlFragment)) {
                    final InventoryControlFragment fragment = findFragment(InventoryControlFragment.class);
                    if (fragment != null) {
                        popTo(InventoryControlFragment.class, false, new Runnable() {
                            @Override
                            public void run() {
                                fragment.initData();
                            }
                        });
                    } else {
                        start(InventoryControlFragment.newInstance());
                    }
                }
                currentMemuItem = 2;
                break;
            case R.id.item_delivery_tray:
                //未投递托盘
                if (!Constants.isLogin) {
                    ToastUtil.showCenterShort("您尚未登陆,请先登陆", true);
                    break;
                }
                if (!(topFragment1 instanceof DeliveryListFragment)) {
                    final DeliveryListFragment fragment = findFragment(DeliveryListFragment.class);
                    if (fragment != null) {
                        popTo(DeliveryListFragment.class, false, new Runnable() {
                            @Override
                            public void run() {
                                fragment.initData();
                            }
                        });
                    } else {
                        start(DeliveryListFragment.newInstance());
                    }
                }
                currentMemuItem = 2;
                break;

        }
        item.setCheckable(true);
        drawLayout.closeDrawers();
        return false;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Constants.RECIVER_DIALOG:
                //收料dialog关闭
                if (data != null) {
                    int feed_result = data.getIntExtra(Constants.RESULT_KEY, Constants.RECIVER_MAKE_SURE);
                    switch (feed_result) {
                        case Constants.RECIVER_MAKE_SURE:
                            //确认收料
                            addScanResult(data);
                            break;
                        case Constants.RECIVER_AND_FEED_FRAGMENT:
                            //确认收料并立即分料  去掉了
//                            addScanResult(data);
//                            start(FeedOneFragment.newInstance());
                            break;
                    }
                }
                break;
            case Constants.FEED_DIALOG:
                //分料dialog关闭
                if (data != null) {
                    int feed_result = data.getIntExtra(Constants.RESULT_KEY, Constants.START_FEED);
                    switch (feed_result) {
                        case Constants.START_FEED:
                            //开始分料 wrapid
                            start(FeedOneFragment.newInstance(data.getExtras()));
                            break;
                        case Constants.WAREHOUSE:
                            //入仓
                            Bundle extras = data.getExtras();
                            start(InWareHouseFragment.newInstance(extras));
                            break;
                        case Constants.UNUSAL:
                            //异常
//                            Bundle bundle = new Bundle();
                            Bundle bundle = data.getExtras();
                            bundle.putBoolean("isReport", false);
                            start(UnusalFragment.newInstance(bundle));
                            break;
                    }

                }
                //入仓
                break;
            case Constants.DELIVERY_DIALOG:
                //投递 由于修改业务---此处不予回调
                if (data != null && data.getIntExtra(Constants.RESULT_KEY, 1) == 0) {
                    final String code = data.getStringExtra("code");
                    deliverySuccessDialog = DialogUtils.showDelivvarySucessDialog(MainActivity.this, "投递成功", new DialogCallBackTwo() {
                        @Override
                        public void OnPositiveClick(Object obj) {
                            //  打印
                            Map<String, String> params = new HashMap<>();
                            params.put("code", code);
                            mMainPresent.printDeliveryInfo(params);
                        }

                        @Override
                        public void OnNegativeClick() {
                            //点击关闭的时候清空托盘
//                            Map<String, String> params = new HashMap<>();
//                            params.put("code", code);
//                            mMainPresent.clearTray(params);
                        }
                    });
                }
                break;
            case Constants.LOGIN_ACTIVITY:
                //登陆界面过来的
                if (data != null) {
                    showLoginResult(data);
//                    Person person = (Person) data.getSerializableExtra("person");
//                    SerializeUtil.save(person, getFilesDir() + SerializeUtil.PATH_PERSON);
//                    showLogin(person);
                }
                break;
        }
    }

    @Deprecated
    private void showLogin(Person person) {
        SharedPreferencesUitl.saveStringData(SharePreferenceKeyUtil.PERSON_ID, person.getPersonId());
        Constants.isLogin = true;
        ImageLoader.loadImage(this, person.getUserPic(), R.drawable.ic_main_user, ivMainUserPhoto);
        llLoginStaus.setVisibility(View.VISIBLE);
        llUnloginStatus.setVisibility(View.GONE);
        tvWorkerName.setText(person.getName());
        tvWorkerNumber.setText(person.getWorkerNumber());
        tvLoginTimeFirst.setText(person.getCurrentDate());
        tvLoginTimeLast.setText(person.getRecentDate());
        tv_nav_username.setText(person.getName());
    }

    private void showLoginResult(Intent data) {
        int type = data.getIntExtra("type", 1);
        Person person = (Person) data.getSerializableExtra("person");
        if (type == 1) {
            //登录显示 最后一个登录的人
            List<Person> personList = new ArrayList<>();
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
                List<Person> data1 = mAdapterWorkerPhoto.getData();
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
            List<Person> data1 = mAdapterWorkerPhoto.getData();
            for (int i = 0; i < data1.size(); i++) {
                Person person1 = data1.get(i);
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
        mMainPresent.getStationAllPerson(false);
    }


    private void loginOut() {
        Constants.isLogin = false;
        SerializeUtil.deleteFile(getFilesDir() + SerializeUtil.PATH_PERSON);
        Constants.releaseToken();
        SharedPreferencesUitl.saveStringData(SharePreferenceKeyUtil.PERSON_ID, "");
        ivMainUserPhoto.setImageResource(R.drawable.ic_main_user);
        llLoginStaus.setVisibility(View.GONE);
        llUnloginStatus.setVisibility(View.VISIBLE);
        popTo(FeedFragment.class, false);
        tv_nav_username.setText("未登录");

    }

    private void addScanResult(Intent data) {//getSerializableExtra
        Wrap wrap = data.getParcelableExtra(Constants.RESULT_DATA);
        FeedFragment feedFragment = findFragment(FeedFragment.class);
        feedFragment.addScanResult(wrap);
    }

    /**
     * 分料出现异常情况刷新页面 并退出到Feedfragment
     */
    public void showFeedError() {
        FeedFragment feedFragment = findFragment(FeedFragment.class);
        if (feedFragment != null) {
            feedFragment.refreshData();
            popTo(FeedFragment.class, false);
        }
    }

    /**
     * 分料成功后 显示
     */
    public void showFeedSucessPop(String station, final int resultInt, final String currentScanResult) {
        if (!TextUtils.isEmpty(station)) {
            FeedFragment feedFragment = findFragment(FeedFragment.class);
            if (feedFragment != null) {
                feedFragment.refreshData();
            }
            DialogUtils.showStationSuccessDialog(this, tvMainTime, station, new DialogCallBackTwo() {
                @Override
                public void OnPositiveClick(Object obj) {
                    popTo(FeedFragment.class, false, new Runnable() {
                        @Override
                        public void run() {
                            if (resultInt == 1) {
                                //还有剩余的物料
                                onScanResult(currentScanResult);
                            }
                        }
                    });
                }

                @Override
                public void OnNegativeClick() {

                }
            });
        }

    }

    /**
     * 物料清单-异常报告-异常打印-物料清单/
     * 异常管理--打印-输出打印--dialog--pop
     * resultInt   1:异常输出 0:异常管理
     * status :异常输出根据这个状态来判断是否还有物料
     */
    public void showExceptionPrinterSucessPop(String station, final int resultInt, final String currentScanResult, String recordId, int status) {
        if (!TextUtils.isEmpty(station)) {
            printExceptionInfo(recordId);
            DialogUtils.showDelivvarySucessDialog(this, station, new DialogCallBackTwo() {
                @Override
                public void OnPositiveClick(Object obj) {
                    //TODO 打印
                    printExceptionInfo(recordId);
                }

                @Override
                public void OnNegativeClick() {
                    if (resultInt == 1) {
                        popTo(FeedFragment.class, false, new Runnable() {
                            @Override
                            public void run() {
                                if (status == 1) {
                                    //还有剩余的物料
                                    onScanResult(currentScanResult);
                                } else {
                                    FeedFragment feedFragment = findFragment(FeedFragment.class);
                                    if (feedFragment != null) {
                                        feedFragment.refreshData();
                                    }
                                }
                            }
                        });
                    } else {
                        //异常管理
                        pop();
                    }


                }
            });
        }

    }

    public void setCurrentFragment(Class currentCls) {

    }

    private void printExceptionInfo(String recordId) {
        Map<String, String> params = new HashMap<>();
        params.put("recordId", recordId);
        mMainPresent.printExceptionInfo(params);
    }

    @Override
    public void onScanResult(String scanResult) {
        super.onScanResult(scanResult);
        ISupportFragment topFragment = getTopFragment();
        if (topFragment != null && topFragment instanceof BaseFragment) {
            BaseFragment baseFragment = (BaseFragment) topFragment;
            baseFragment.setScanResult(scanResult);
        }
    }

    @Override
    public void showStation(WorkStationVo workStationVo) {
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
        mMainPresent.getStationAllPerson(true);
    }

    private void addWorkerPhoto() {

    }

    @Override
    public void loginOutResult(boolean result) {
        if (result) {
//            loginOut();
        }
    }

    @Override
    public void clearTrayResult(boolean result) {
        if (result) {
            //不要自动关闭 让其手动关闭
            if (deliverySuccessDialog != null && deliverySuccessDialog.isShowing()) {
//                deliverySuccessDialog.dismiss();
            }
        }
    }

    @Override
    public void showAllPersons(List<Person> personList) {
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

    private void setCurrentPersonName() {
        if (currentPerson != null) {
            tvWorkerNameCurrent.setText(currentPerson.getName());
            SharedPreferencesUitl.saveStringData(SharePreferenceKeyUtil.PERSON_ID, currentPerson.getPersonId());
            Constants.isLogin = true;
            tv_nav_username.setText("已登录");
            ImageLoader.loadImage(this, currentPerson.getUserPic(), R.drawable.ic_main_user, ivMainUserPhoto);
            ImageLoader.loadImage(this, currentPerson.getUserPic(), R.drawable.ic_main_user, iv_nav_user_photo);
        } else {
            tvWorkerNameCurrent.setText("请先登录");
            ivMainUserPhoto.setImageResource(R.drawable.ic_main_user);
            iv_nav_user_photo.setImageResource(R.drawable.ic_main_user);
            SharedPreferencesUitl.saveStringData(SharePreferenceKeyUtil.PERSON_ID, "");
            quitFeedFragment();
            Constants.isLogin = false;
            tv_nav_username.setText("未登录");
        }
        if (mAdapterWorkerPhoto.getData() != null && mAdapterWorkerPhoto.getData().size() > 1) {
            recycleWokerPhoto.setVisibility(View.VISIBLE);
        } else {
            recycleWokerPhoto.setVisibility(View.INVISIBLE);
        }
        updateHeartBean();
    }

    private void quitFeedFragment() {
        if (!NoDoubleClickUtil.doubleClick(800)) {
            //防止快速pop 出现异常
            return;
        }
        ISupportFragment topFragment = getTopFragment();
        if (!(topFragment instanceof FeedFragment)) {
            popTo(FeedFragment.class, false);
        }
    }

    @Override
    protected void onDestroy() {
        if (connection != null) {
            unbindService(connection);
        }
        handler.removeCallbacksAndMessages(null);
        handler = null;
        if (myServer != null) {
            myServer.stop();
        }
        unregisterReceiver(mNetworkConnectChangedReceiver);
        killHeartProcess();
        super.onDestroy();
    }

    String[] commads = new String[]{"adb shell", "su", "am force-stop workstation.zjyk.workstation"};//杀死服务进程 添加 :HeartService无效

    private void killHeartProcess() {
        ShellUtil.CommandResult commandResult = ShellUtil.execCommand(commads, true, true);
    }

    @Override
    public void showCheckApkInfo(AppUpdate wsAppUpdate) {
        if (wsAppUpdate != null) {
            int minimumVersion = 0;
            int newVersion = 0;
            YesOrNoEnum forceUpdate = wsAppUpdate.getForceUpdate();
            if (!TextUtils.isEmpty(wsAppUpdate.getMinimumVersion())) {
                minimumVersion = Integer.parseInt(wsAppUpdate.getMinimumVersion());
            }
            if (!TextUtils.isEmpty(wsAppUpdate.getNewestVersion())) {
                newVersion = Integer.parseInt(wsAppUpdate.getNewestVersion());
            }

            int versionCode = AppUtils.getVersionCode(BaseApplication.getInstance());
            if ((forceUpdate == YesOrNoEnum.YES) && (versionCode < minimumVersion)) {
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

    @Override
    public void downApkResult(boolean result, String saveUrl) {
        if (result) {
            if (downApkProgressDialog != null) {
                downApkProgressDialog.setInstall();
            }
            FileUtil.setupApk(this, new File(saveUrl));
        }
    }

    private void showUpdateDialog(String title, AppUpdate wsAppUpdate) {
        //升级
        //升级
//
        downApkProgressDialog = DialogUtils.showUpdateDialog(this, title, wsAppUpdate, new DialogCallBackTwo() {
            @Override
            public void OnPositiveClick(Object obj) {
                //升级
                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    //
                    RxPermissions rxPermissions = new RxPermissions(MainActivity.this);
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

    private void downAPKStart(AppUpdate wsAppUpdate) {
        String saveFilePath = SDCardHelper.getSDCardPrivateFilesDir(this, null) + "/updateApk";
        mMainPresent.downLoadApk(MainActivity.this, wsAppUpdate.getUrl(), saveFilePath, downApkProgressDialog);
    }
}
