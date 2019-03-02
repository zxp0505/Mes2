package workstation.zjyk.line.modle.net;

import cn.com.ethank.mylibrary.resourcelibrary.common_util.SharePreferenceKeyUtil;
import cn.com.ethank.mylibrary.resourcelibrary.shareutils.SharedPreferencesUitl;
import workstation.zjyk.line.util.Constants;
import workstation.zjyk.line.util.URLBuilder;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import rx.Observable;
import rx.Subscription;
import rx.functions.Action0;

/**
 * Created by zjgz on 2017/11/2.
 */

public class ApiManager {

    private final ApiService apiService;

    public static class Holder {
        public static final ApiManager apiManager = new ApiManager();
    }

    public static ApiManager getInstance() {
        return Holder.apiManager;
    }

    private ApiManager() {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                if (request.method().equals("GET")) {
                    //添加公共参数
                    HttpUrl httpUrl = request.url()
                            .newBuilder()
                            .addQueryParameter("token", Constants.getToken())
                            .addQueryParameter("personId", SharedPreferencesUitl.getStringData(SharePreferenceKeyUtil.PERSON_ID))
                            .addQueryParameter("clientIP", Constants.getClientIp())
                            .build();
                    request = request.newBuilder().url(httpUrl).build();

                } else if (request.method().equals("POST")) {
                    if (request.body() instanceof FormBody) {
                        FormBody.Builder bodyBuilder = new FormBody.Builder();
                        FormBody formBody = (FormBody) request.body();

                        //把原来的参数添加到新的构造器，（因为没找到直接添加，所以就new新的）
                        for (int i = 0; i < formBody.size(); i++) {
                            bodyBuilder.addEncoded(formBody.encodedName(i), formBody.encodedValue(i));
                        }

                        formBody = bodyBuilder
                                .addEncoded("token", Constants.getToken())
                                .addEncoded("personId", SharedPreferencesUitl.getStringData(SharePreferenceKeyUtil.PERSON_ID))
                                .addEncoded("clientIP", Constants.getClientIp())
                                .build();

                        request = request.newBuilder().post(formBody).build();
                    }
                }


                return chain.proceed(request);
            }
        });
        OkHttpClient okHttpClient = builder
                .addInterceptor(logging)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(100,TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(URLBuilder.getHostUrl())
                .build();
        apiService = retrofit.create(ApiService.class);
    }

    private Subscription toSubscribe(Observable observable, final BaseHttpResultSubscriber subscriber) {
        return observable.subscribeOn(rx.schedulers.Schedulers.io())
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        subscriber._showLoadingDialog("");
                    }
                })
                .unsubscribeOn(rx.schedulers.Schedulers.io())
                .subscribeOn(rx.android.schedulers.AndroidSchedulers.mainThread())
                .observeOn(rx.android.schedulers.AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    public Subscription post(String url, Map<String, String> params, BaseHttpResultSubscriber subscriber) {
        return toSubscribe(apiService.executePost(url, params), subscriber);
    }

    public Subscription get(String url, Map<String, String> params, BaseHttpResultSubscriber subscriber) {
        return toSubscribe(apiService.executeGet(url, params), subscriber);
    }

    public Subscription upLoadImage(String url, Map<String, String> parameters, File file, String fileName, BaseHttpResultSubscriber subscriber) {
// 创建 RequestBody，用于封装构建RequestBody
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);
        return toSubscribe(apiService.uploadImage(url, parameters, fileName, requestFile), subscriber);
    }


}
