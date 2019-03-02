package workstation.zjyk.workstation.modle.interceptor;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import workstation.zjyk.workstation.util.WSUploadNetWorkUtil;

/**
 * 查看响应时间的拦截器
 */
public class CheckResponInterceptor implements Interceptor {
    private static final long OVERTIME = 40;//设置超时时间

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        long startTime = System.nanoTime();
        Response response = null;
        try {
            response = chain.proceed(request);
        } catch (Exception e) {
            throw e;
        }
        long tookNos = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime);//请求时间
        ResponseBody body = response.body();
        long contentLength = body.contentLength();
        if (tookNos > OVERTIME) {
            WSUploadNetWorkUtil.UpLoadNetBean upLoadNetBean = new WSUploadNetWorkUtil.UpLoadNetBean();
            upLoadNetBean.setOverTime(tookNos + "");
            upLoadNetBean.setUrl(response.request().url() + "");
            WSUploadNetWorkUtil.getInstance().uploadOverTimeRunnable(upLoadNetBean);
        }


        return response;
    }
}
