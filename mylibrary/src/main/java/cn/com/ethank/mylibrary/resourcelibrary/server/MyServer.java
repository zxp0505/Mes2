package cn.com.ethank.mylibrary.resourcelibrary.server;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import cn.com.ethank.mylibrary.resourcelibrary.common_util.log.LoggerUtil;
//import com.coyotelib.core.util.coding.AbstractCoding;
//

public class MyServer extends NanoHTTPD {

    public static final String MIME_DEFAULT_BINARY = "application/octet-stream";


//    ArrayList<UrlAction> actionList = new ArrayList<UrlAction>();
    /**
     * Default Index file names.
     */
    @SuppressWarnings("serial")
    public static final List<String> INDEX_FILE_NAMES = new ArrayList<String>() {

        {
            add("index.html");
            add("index.htm");
        }
    };


    /**
     * Hashtable mapping (String)FILENAME_EXTENSION -> (String)MIME_TYPE
     */
    @SuppressWarnings("serial")
    private static final Map<String, String> MIME_TYPES = new HashMap<String, String>() {

        {
            put("css", "text/css");
            put("htm", "text/html");
            put("html", "text/html");
            put("xml", "text/xml");
            put("java", "text/x-java-source, text/java");
            put("md", "text/plain");
            put("txt", "text/plain");
            put("asc", "text/plain");
            put("gif", "image/gif");
            put("jpg", "image/jpeg");
            put("jpeg", "image/jpeg");
            put("png", "image/png");
            put("mp3", "audio/mpeg");
            put("m3u", "audio/mpeg-url");
            put("mp4", "video/mp4");
            put("ogv", "video/ogg");
            put("flv", "video/x-flv");
            put("mov", "video/quicktime");
            put("swf", "application/x-shockwave-flash");
            put("js", "application/javascript");
            put("pdf", "application/pdf");
            put("doc", "application/msword");
            put("ogg", "application/x-ogg");
            put("zip", "application/octet-stream");
            put("exe", "application/octet-stream");
            put("class", "application/octet-stream");
        }
    };
    private static final Logger LOG = Logger.getLogger(MyServer.class.getName());

    public MyServer() {
        super(8080);
    }

    //---------------------------------------------------------


    private static final String ACTION_LOGIN = "101"; //登录变化
    private static final String ACTION_WRAP_CHAGE = "102"; //包裹变化

    //    private static final String ACTION_QUIT_APP = "104"; //退出应用
    //工位 相关
    public static final int ACTION_UPDATE_WORKSTATION_INFO = 201; //后台更改pad所属工位
    public static final int ACTION_TO_OFFLINE = 202; //下线推送
    public static final int ACTION_TASK_TRASFER = 203; //任务转移推送
    public static final int ACTION_TASK_COME = 204; //任务下发
    public static final int ACTION_ORDER_PAUSE = 205; //订单暂停
    public static final int ACTION_ORDER_END = 206; //订单终止
    public static final int ACTION_ORDER_REAGIN = 207; //订单恢复

    public static final int ACTION_TASK_END = 209; //任务结束
    public static final int ACTION_TASK_START = 210; //任务开始
    public static final int ACTION_TASK_PAUSE = 211; //任务暂停
    public static final int ACTION_TASK_FINISH = 217; //任务已完成
    public static final int ACTION_TASK_OUTPUT = 212; //任务有输出
    public static final int ACTION_TASK_REWORK = 213; //任务有返工
    public static final int ACTION_TASK_REPAIR = 214; //任务有维修
    public static final int ACTION_TASK_EXCEPTION = 215; //任务有异常
    public static final int ACTION_TASK_REPORT_WORK = 216; //任务有报工
    public static final int ACTION_TASK_RECIVER_MATERAIL = 218; //有接收物料
    public static final int ACTION_TASK_UPDATE_FOLLOW = 219; //关注物料变更
    public static final int ACTION_PRODUCT_PROP_CHECK = 220; //产品属性检查
    //-----------------------------------------------

    public static final int ACTION_QUIT_APP = 301; //退出应用
    public static final int ACTION_LOGIN_CHANG = 302; //异地登录
    private static final String URL_LINE = "line";
    private static final String URL_CMPUSH = "cmpush";
    private static final String URL_WORKSTATION = "workstation";
    private static final String URL_WARN = "warn";
    private static final String URL_WARN_NOLOGIN = "warnnologin";
    int status;
    String defaulResponse = "{\"code\":\"1\"}";
    String errorRespones = "{\"code\":\"0\"}";

    Handler handler = new Handler(Looper.getMainLooper());
    List<UrlParam> urlParams = new ArrayList<>();

    @Override
    public Response serve(IHTTPSession session) {

        String uri = session.getUri();
        Map<String, String> body = new HashMap<String, String>();
        try {
            session.parseBody(body);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ResponseException e) {
            e.printStackTrace();
        }
        Map<String, String> parms = session.getParms();
        LoggerUtil.d("server----uri:" + uri + "---currentthread--" + Thread.currentThread().getName());
        LoggerUtil.d(parms);
        if (TextUtils.isEmpty(uri)) {
            return newFixedLengthResponse(errorRespones);
        }
        UrlParam urlParam = new UrlParam(uri, parms);
        distributeFunction(urlParam);
//        handler.post(new Runnable() {
//            @Override
//            public void run() {
//            }
//        });

        return newFixedLengthResponse(defaulResponse);
    }

    private void distributeFunction(UrlParam urlParam) {
        urlParams.add(urlParam);
        handleAction(urlParams);

    }

    private void handleAction(List<UrlParam> urlParams) {
        if (urlParams.size() == 0) {
            return;
        }
        UrlParam urlParam = urlParams.remove(0);
        String url = urlParam.getUrl();
        Map<String, String> params = urlParam.getParams();
        switch (url.substring(1)) {
            case URL_LINE:
                //线边库
                handleLineAction(params);
                break;
            case URL_CMPUSH:
                handleCmpushAction(params);
                break;
            case URL_WORKSTATION:
                handleWorkstationAction(params);
                break;
            case URL_WARN_NOLOGIN:
                handleWarnNologinAction(params);
                break;
            case URL_WARN:
                handleWarnAction(params);
                break;
        }
        handleAction(urlParams);
    }
    private void handleWarnNologinAction(Map<String, String> data) {
        MessageEventBean bean = new MessageEventBean();
        bean.setMessage(JSON.toJSONString(data));
        bean.setType(111);
        EventBus.getDefault().post(bean);
    }
    private void handleWarnAction(Map<String, String> data) {
        MessageEventBean bean = new MessageEventBean();
        bean.setMessage(JSON.toJSONString(data));
        bean.setType(110);
        EventBus.getDefault().post(bean);
    }


    private void handleWorkstationAction(Map<String, String> data) {
        String param = data.get("param");
        if (!TextUtils.isEmpty(param)) {
            int paramInt = Integer.parseInt(param);
            MessageEventBean messageEventBeanLogin = new MessageEventBean(1, paramInt);
            if (data.containsKey("taskId")) {
                messageEventBeanLogin.setMessage(data.get("taskId"));
            } else if (data.containsKey("message")) {
                messageEventBeanLogin.setMessage(data.get("message"));
            }
            EventBus.getDefault().post(messageEventBeanLogin);
        }

    }

    private void handleLineAction(Map<String, String> data) {
        String param = data.get("param");
        switch (param) {
            case ACTION_LOGIN:
                MessageEventBean messageEventBeanLogin = new MessageEventBean(1, 1);
                EventBus.getDefault().post(messageEventBeanLogin);
                break;
            case ACTION_WRAP_CHAGE:
                MessageEventBean messageEventBeanWrap = new MessageEventBean(1, 2);
                EventBus.getDefault().post(messageEventBeanWrap);
                break;
        }

    }

    private void handleCmpushAction(Map<String, String> data) {
        String param = data.get("param");
        MessageEventBean messageEventBeanLogin = new MessageEventBean();
        messageEventBeanLogin.setType(0);
        messageEventBeanLogin.setRefreshType(Integer.parseInt(param));
        if (data.containsKey("type")) {

            String type = data.get("type");
        }
        if (data.containsKey("message")) {

            String message = data.get("message");
            messageEventBeanLogin.setMessage(message);
        }
        EventBus.getDefault().post(messageEventBeanLogin);

    }


    public class UrlParam {
        private String url;
        private Map<String, String> data;

        public UrlParam(String url, Map<String, String> params) {
            this.url = url;
            this.data = params;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public Map<String, String> getParams() {
            return data;
        }

        public void setParams(Map<String, String> params) {
            this.data = params;
        }
    }


}
