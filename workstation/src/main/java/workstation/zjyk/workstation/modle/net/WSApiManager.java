package workstation.zjyk.workstation.modle.net;

import android.nfc.Tag;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.trello.rxlifecycle2.LifecycleTransformer;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import cn.com.ethank.mylibrary.resourcelibrary.common_util.FileUtils;
import cn.com.ethank.mylibrary.resourcelibrary.common_util.SharePreferenceKeyUtil;
import cn.com.ethank.mylibrary.resourcelibrary.common_util.log.LoggerUtil;
import cn.com.ethank.mylibrary.resourcelibrary.shareutils.SharedPreferencesUitl;
import cn.com.ethank.mylibrary.resourcelibrary.toast.ToastUtil;
import cn.com.ethank.mylibrary.resourcelibrary.utils.SDCardHelper;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import workstation.zjyk.workstation.modle.bean.WSBaseResultCommon;
import workstation.zjyk.workstation.modle.bean.enumpackage.ResultEnum;
import workstation.zjyk.workstation.modle.download.DownloadProgressInterceptor;
import workstation.zjyk.workstation.modle.download.DownloadProgressListener;
import workstation.zjyk.workstation.modle.interceptor.CheckParamInterceptor;
import workstation.zjyk.workstation.modle.interceptor.CheckResponInterceptor;
import workstation.zjyk.workstation.ui.manager.WSUserManager;
import workstation.zjyk.workstation.util.WSConstants;
import workstation.zjyk.workstation.util.WSURLBuilder;

//import rx.Observable;
//import rx.Subscription;
//import rx.functions.Action0;

/**
 * Created by zjgz on 2017/11/2.
 */

public class WSApiManager {

    private final WSApiService apiService;
    private final DownloadProgressInterceptor downloadProgressInterceptor;
    private final CheckResponInterceptor mCheckResponInterceptor;

    public static class Holder {
        public static final WSApiManager apiManager = new WSApiManager();
    }

    public static WSApiManager getInstance() {
        return Holder.apiManager;
    }

    private WSApiManager() {
        mCheckResponInterceptor = new CheckResponInterceptor();
        downloadProgressInterceptor = new DownloadProgressInterceptor();
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
                            .addQueryParameter("token", WSConstants.getToken())
                            .addQueryParameter("personId", WSUserManager.getInstance().getPersonId())//SharedPreferencesUitl.getStringData(SharePreferenceKeyUtil.PERSON_ID)
                            .addQueryParameter("clientIP", WSConstants.getClientIp())
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
                                .addEncoded("token", WSConstants.getToken())
                                .addEncoded("personId", WSUserManager.getInstance().getPersonId())
                                .addEncoded("clientIP", WSConstants.getClientIp())
                                .build();

                        request = request.newBuilder().post(formBody).build();
                    }
                }


                return chain.proceed(request);
            }
        });
        OkHttpClient okHttpClient = builder
//                .addInterceptor(mCheckResponInterceptor)
//                .addInterceptor(new CheckParamInterceptor())
                .addInterceptor(logging)
                .addInterceptor(downloadProgressInterceptor)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(100, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();
//        Gson gson = new GsonBuilder().registerTypeHierarchyAdapter(WSBaseResultCommon.class, new ResultJsonDeser())
//                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
//                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(WSURLBuilder.getHostUrl())
                .build();
        apiService = retrofit.create(WSApiService.class);
    }

    public WSApiService getApiService() {
        return apiService;
    }

//    private Subscription toSubscribe(Observable observable, final CommonBaseHttpResultSubscriber subscriber) {
//        return observable.subscribeOn(rx.schedulers.Schedulers.io())
//                .doOnSubscribe(new Action0() {
//                    @Override
//                    public void call() {
//                        subscriber._showLoadingDialog("");
//                    }
//                })
//                .unsubscribeOn(rx.schedulers.Schedulers.io())
//                .subscribeOn(rx.android.schedulers.AndroidSchedulers.mainThread())
//                .observeOn(rx.android.schedulers.AndroidSchedulers.mainThread())
//                .subscribe(subscriber);
//    }

    private void toSubscribe(Observable observable, LifecycleTransformer lifecycleTransformer, final WSBaseObsever subscriber) {
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


    public void get(String url, Map<String, String> params, LifecycleTransformer lifecycleTransformer, WSBaseObsever subscriber) {

        toSubscribe(apiService.executeGet(url, params), lifecycleTransformer, subscriber);
    }

    public void post(String url, Map<String, String> params, LifecycleTransformer lifecycleTransformer, WSBaseObsever subscriber) {

        toSubscribe(apiService.executePost(url, params), lifecycleTransformer, subscriber);
    }


    public void downFile(String pdfUrl, String saveUrl, LifecycleTransformer lifecycleTransformer, WSBaseObsever subscriber, DownloadProgressListener downloadProgressListener) {
        downloadProgressListener.getObsever(subscriber);
        downloadProgressInterceptor.setDownloadProgressListener(downloadProgressListener);
        toSubscribeDownFile(apiService.downloadFile(pdfUrl), saveUrl, lifecycleTransformer, subscriber);
    }

    private void toSubscribeDownFile(Observable<ResponseBody> responseBodyObservable, String saveUrl, LifecycleTransformer lifecycleTransformer, WSBaseObsever subscriber) {
        responseBodyObservable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .map(new Function<ResponseBody, InputStream>() {
                    @Override
                    public InputStream apply(ResponseBody responseBody) throws Exception {
//                        //判断sd卡空间
//                        long available = responseBody.contentLength();
//                        long sdCardAvailableSize = SDCardHelper.getSDCardAvailableSize()*1024*1024;
//                        File file = new File(saveUrl);
//                        File parentFile = file.getParentFile();
//                        if(true){//sdCardAvailableSize<available
//                            //sd空间不足
//                            SDCardHelper.deleteDir(parentFile);
//                            long sdCardAvailableSizeNow = SDCardHelper.getSDCardAvailableSize()*1024*1024;
//                            if(true){//sdCardAvailableSize<available
//                                ToastUtil.showInfoCenterShort("内存空间不足，请清理sd卡空间，然后下载");
//                                subscriber.disposable.dispose();
//                            }
//                        }
//
//                        Log.e("manager","available:"+available+".....sdCardAvailableSize:"+sdCardAvailableSize);
                        return responseBody.byteStream();
                    }
                })
                .observeOn(Schedulers.computation())
                .map(new Function<InputStream, WSBaseResultCommon<String>>() {
                    @Override
                    public WSBaseResultCommon apply(InputStream inputStream) throws Exception {
                        String saveFilePath = FileUtils.writeFile(inputStream, new File(saveUrl));
                        WSBaseResultCommon baseResultCommon = new WSBaseResultCommon();
                        baseResultCommon.setResult(ResultEnum.OK);
                        baseResultCommon.setData(saveFilePath);
                        return baseResultCommon;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .compose(lifecycleTransformer)
                .subscribe(subscriber);
    }

//    public void upLoadImage(String url, Map<String, String> parameters, File file, String fileName, CommonBaseHttpResultSubscriber subscriber) {
//// 创建 RequestBody，用于封装构建RequestBody
//        RequestBody requestFile =
//                RequestBody.create(MediaType.parse("multipart/form-data"), file);
//        toSubscribe(apiService.uploadImage(url, parameters, fileName, requestFile), subscriber);
//    }


}
