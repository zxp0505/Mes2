package workstation.zjyk.line.modle.callback;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zhy.http.okhttp.callback.Callback;
import cn.com.ethank.mylibrary.resourcelibrary.common_util.log.LoggerUtil;
import workstation.zjyk.line.modle.bean.BaseBean;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import cn.com.ethank.mylibrary.resourcelibrary.toast.ToastUtil;
import cn.com.ethank.yunge.app.crypt.AbstractCoding;
import cn.com.ethank.yunge.app.crypt.Base64CryptoCoding;
import okhttp3.Response;

/**
 * 自定义泛型callback 剥离了最外层 直接传入相应的泛型即可
 */
public abstract class DataCallback<T> extends Callback<BaseBean<T>> {
    private static final String TAG = "DataCallback";
    private AbstractCoding coding = new Base64CryptoCoding();
    public Type mType;
    private static final boolean DEBUG = true;

    public DataCallback() {
        mType = getSuperclassTypeParameter(getClass());
    }

    static Type getSuperclassTypeParameter(Class<?> subclass) {
        Type superclass = subclass.getGenericSuperclass();
        if (superclass instanceof Class) {
            throw new RuntimeException("Missing type parameter.");
        }
        ParameterizedType parameterized = (ParameterizedType) superclass;
        return parameterized.getActualTypeArguments()[0];
    }


    int code = 0;

    @Override
    public BaseBean parseNetworkResponse(Response response, int id) throws Exception {

        // 解密

        String json = response.body().string();
        if (DEBUG) {
            LoggerUtil.json(json);
        }
        JSONObject jsonObject = (JSONObject) JSONObject.parse(json);
        if (jsonObject.containsKey("errorCode")) {
            code = jsonObject.getInteger("errorCode");
        } else {
            code = jsonObject.getInteger("code");
        }
        if (code == 0) {
            String str_data = "";
            if (jsonObject.containsKey("data")) {
                str_data = jsonObject.getString("data");
            } else if (jsonObject.containsKey("dayreport")) {
                str_data = jsonObject.getString("dayreport");
            } else if (jsonObject.containsKey("RateDetail")) {
                str_data = jsonObject.getString("RateDetail");
            }

            if (str_data.startsWith("[")) {
                //jsonarray
                BaseBean<List<T>> data = new BaseBean<List<T>>();
                data.setCode(code);
                List<T> list = JSON.parseArray(str_data, (Class<T>) mType);
                data.setData(list);
                if (jsonObject.containsKey("msg")) {//message
                    data.setMessage(jsonObject.getString("msg"));
                }
                return data;
            } else {
                //jsonobject
                BaseBean<T> data = new BaseBean<T>();
                data.setCode(code);
                JSONObject jsonObject_data = jsonObject.getJSONObject("data");

                if (jsonObject_data != null && jsonObject_data.containsKey("shop") && jsonObject_data.containsKey("user")) {
                    //首次登陆特殊解析
                } else {
                    data.setData(JSON.parseObject(str_data, mType));
                }

                if (jsonObject.containsKey("msg")) {
                    data.setMessage(jsonObject.getString("msg"));
                }
                return data;
            }
        } else {
            String requestUrl = response.request().url().toString();
            if (requestUrl.endsWith("getRoomStatus")) {
//针对查询房间状态查询失败 后台返回的 code -1  作此兼容
                BaseBean<List<T>> data = new BaseBean<List<T>>();
                List<T> list = new ArrayList<T>();
                data.setData(list);
                return data;
            }

            if (requestUrl.endsWith("authrize")) {
//登陆
                ToastUtil.showShort(jsonObject.getString("msg"));
            }
            if (DEBUG) {
                ToastUtil.showShort("errorcode:" + code + "msg:" + jsonObject.getString("msg"));
            }
            throw new IllegalArgumentException("The code is not 'zero' , return code is " + jsonObject.getInteger("code") + ". error message:" + jsonObject.getString("message"));
        }
    }
}
