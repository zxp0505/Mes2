package workstation.zjyk.workstation.modle.interceptor;

import android.text.TextUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;

/**
 * 检查参数拦截器
 */
public class CheckParamInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        RequestBody body = request.body();
        Buffer buffer = new Buffer();
        body.writeTo(buffer);
        Buffer useBuffer = new Buffer();
        buffer.copyTo(useBuffer, 0, buffer.size());
        String paramsStr = useBuffer.readString(Charset.forName("UTF-8"));
        String[] splitOne = paramsStr.split("&");
        Map<String, String> paramsMap = new HashMap<>();
        for (int i = 0; i < splitOne.length; i++) {
            String param = splitOne[i];
            String[] splitParam = param.split("=");
            if (splitParam.length > 1) {
                paramsMap.put(splitParam[0], splitParam[1]);
            } else {
                paramsMap.put(splitParam[0], "");
            }
        }
        Response response = chain.proceed(request);
        if(paramsMap.containsKey("wId")  ){
            String wId = paramsMap.get("wId");
            if(TextUtils.isEmpty(wId)){

            }
        }


        return response;
    }
}
