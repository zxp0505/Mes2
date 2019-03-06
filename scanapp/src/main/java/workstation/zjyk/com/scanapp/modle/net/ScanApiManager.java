package workstation.zjyk.com.scanapp.modle.net;

import com.trello.rxlifecycle2.LifecycleTransformer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import workstation.zjyk.com.scanapp.util.ScanConstants;
import workstation.zjyk.com.scanapp.util.ScanURLBuilder;
import workstation.zjyk.com.scanapp.util.ScanUserManager;

//import rx.Observable;
//import rx.Subscription;
//import rx.functions.Action0;

/**
 * Created by zjgz on 2017/11/2.
 */

public class ScanApiManager {

    private final ScanApiService apiService;

    public static class Holder {
        public static final ScanApiManager apiManager = new ScanApiManager();
    }

    public static ScanApiManager getInstance() {
        return Holder.apiManager;
    }

    private ScanApiManager() {
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
//                            .addQueryParameter("token", WSConstants.getToken())
                            .addQueryParameter("personId", ScanUserManager.getInstance().getPersonId())
//                            .addQueryParameter("clientIP", WSConstants.getClientIp())
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
                        if (!ScanConstants.isWarn()) {
                            formBody = bodyBuilder
//                                .addEncoded("token", WSConstants.getToken())
                                    .addEncoded("personId", ScanUserManager.getInstance().getPersonId())
//                                .addEncoded("clientIP", WSConstants.getClientIp())
                                    .build();
                        }


                        request = request.newBuilder().post(formBody).build();
                    }
                }


                return chain.proceed(request);
            }
        });
        OkHttpClient okHttpClient = builder
                .addInterceptor(logging)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(100, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(ScanURLBuilder.getHostUrl())
                .build();
        apiService = retrofit.create(ScanApiService.class);
    }

    public ScanApiService getApiService() {
        return apiService;
    }


    private void toSubscribe(Observable observable, LifecycleTransformer lifecycleTransformer, final ScanBaseObsever subscriber) {
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


    public void get(String url, Map<String, String> params, LifecycleTransformer lifecycleTransformer, ScanBaseObsever subscriber) {

        toSubscribe(apiService.executeGet(url, params), lifecycleTransformer, subscriber);
    }

    public void post(String url, Map<String, String> params, LifecycleTransformer lifecycleTransformer, ScanBaseObsever subscriber) {

        toSubscribe(apiService.executePost(url, params), lifecycleTransformer, subscriber);
    }

    public void uploadImages(String url, Map<String, String> params, List<String> imageUrls, LifecycleTransformer lifecycleTransformer, ScanBaseObsever subscriber) {
        MultipartBody.Part[] part = new MultipartBody.Part[imageUrls.size()];
        for (int i = 0; i < imageUrls.size(); i++) {
            File file = new File(imageUrls.get(i));
            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            part[i] = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
        }
        toSubscribe(apiService.uploadImagesWithText(url, part, params), lifecycleTransformer, subscriber);


    }


}
