package workstation.zjyk.workstation.modle.net;

/**
 * Created by zjgz on 2017/11/5.
 */

public class WSErrorCode {

    public static final int CODE_NET_EXCEPTION = 0;//网络异常 请检查网络
    public static final int CODE_NET_SOCKET_TIME_OUT = 1;//请求超时
    public static final int CODE_HTTP_EXCEPTION = 2;//服务器异常
    public static final int CODE_UNKNOWN_EXCEPTION = 3;//未知异常
    public static final int CODE_DATA_NULL = 4;//数据为null
    public static final int CODE_RESULT_ERROR = 5;//请求的结果为error 根据后台返回的信息提示用户
    public static final int CODE_HTTP_NOTFOUND = 6;//服务器异常

    public static final String STRING_NET_EXCEPTION ="网络异常,请检查网络";
    public static final String STRING_NET_SOCKET_TIME_OUT = "请求超时,请检查网络";//请求超时
    public static final String STRING_HTTP_EXCEPTION = "服务器异常";//服务器异常
    public static final String STRING_UNKNOWN_EXCEPTION = "未知异常";//未知异常
    public static final String STRING_DATA_NULL = "数据为null";//数据为null
    public static final String STRING_UN_CONNET_NET = "无法连接到代理服务器";



}
