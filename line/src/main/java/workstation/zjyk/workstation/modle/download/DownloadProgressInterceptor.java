package workstation.zjyk.workstation.modle.download;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import workstation.zjyk.line.util.URLBuilder;

public class DownloadProgressInterceptor implements Interceptor {

    private DownloadProgressListener listener;

    public void setDownloadProgressListener(DownloadProgressListener listener) {
        this.listener = listener;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
//        Log.e("tag", "进入DownloadProgressInterceptor拦截器");
        Request request = chain.request();
        HttpUrl url = request.url();
        Response originalResponse = chain.proceed(chain.request());
        if (url.toString().startsWith(URLBuilder.getHostUrl())) {
            return originalResponse;
        } else {
            return originalResponse.newBuilder()
                    .body(new DownloadProgressResponseBody(originalResponse.body(), listener))
                    .build();
        }

    }
}