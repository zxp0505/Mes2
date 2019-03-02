package com.zjyk.quality.modle.net;

import com.trello.rxlifecycle2.LifecycleTransformer;
import com.zjyk.quality.util.QAConstants;
import com.zjyk.quality.util.QAURLBuilder;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import cn.com.ethank.mylibrary.resourcelibrary.common_util.SharePreferenceKeyUtil;
import cn.com.ethank.mylibrary.resourcelibrary.shareutils.SharedPreferencesUitl;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

//import rx.Observable;
//import rx.Subscription;
//import rx.functions.Action0;

/**
 * Created by zjgz on 2017/11/2.
 */

public class QAHeartApiManager {

    private final QAApiService apiService;

    public static class Holder {
        public static final QAHeartApiManager apiManager = new QAHeartApiManager();
    }

    public static QAHeartApiManager getInstance() {
        return Holder.apiManager;
    }

    private QAHeartApiManager() {

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
                            .addQueryParameter("token", QAConstants.getToken())
                            .addQueryParameter("personId", SharedPreferencesUitl.getStringData(SharePreferenceKeyUtil.PERSON_ID))
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
                                .addEncoded("token", QAConstants.getToken())
                                .addEncoded("personId", SharedPreferencesUitl.getStringData(SharePreferenceKeyUtil.PERSON_ID))
                                .build();

                        request = request.newBuilder().post(formBody).build();
                    }
                }


                return chain.proceed(request);
            }
        });
        OkHttpClient okHttpClient = builder
                .addInterceptor(logging)
                .readTimeout(3, TimeUnit.SECONDS)
                .writeTimeout(3, TimeUnit.SECONDS)
                .connectTimeout(3, TimeUnit.SECONDS)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(QAURLBuilder.getHostUrl())
                .build();
        apiService = retrofit.create(QAApiService.class);
    }

    public QAApiService getApiService() {
        return apiService;
    }


    private void toSubscribe(Observable observable, LifecycleTransformer lifecycleTransformer, final QABaseObsever subscriber) {
        observable.subscribeOn(Schedulers.io())
//                .doOnSubscribe(new Consumer<Disposable>() {
//                    @Override
//                    public void accept(Disposable disposable) throws Exception {
//                        //使用rx2.0没任何卵用 适用于rx1.0
////                        subscriber._showLoadingDialog("");
//                        LoggerUtil.e("doOnSubscribe:---" + Thread.currentThread().getName());
//                    }
//                })
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(lifecycleTransformer)
                .subscribe(subscriber);
    }


    public void get(String url, Map<String, String> params, LifecycleTransformer lifecycleTransformer, QABaseObsever subscriber) {

        toSubscribe(apiService.executeGet(url, params), lifecycleTransformer, subscriber);
    }

    public void post(String url, Map<String, String> params, LifecycleTransformer lifecycleTransformer, QABaseObsever subscriber) {

        toSubscribe(apiService.executePost(url, params), lifecycleTransformer, subscriber);
    }




}
