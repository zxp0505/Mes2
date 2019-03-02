package com.zjyk.quality.modle.net;

import com.zjyk.quality.modle.bean.QABaseResultCommon;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Created by zjgz on 2017/11/2.
 */

public interface QAApiService {
    @POST()
    @FormUrlEncoded
    Observable<QABaseResultCommon> executePost(
            @Url() String url,
            @FieldMap Map<String, String> maps);

    @POST("{url}")
    Observable<QABaseResultCommon> executePostBody(
            @Path("url") String url,
            @Body Object object);

    @GET()
    Observable<QABaseResultCommon> executeGet(
            @Url String url,
            @QueryMap Map<String, String> maps);

    @DELETE()
    Observable<QABaseResultCommon> executeDelete(
            @Url String url,
            @QueryMap Map<String, Object> maps);

    @PUT()
    Observable<QABaseResultCommon> executePut(
            @Url String url,
            @FieldMap Map<String, Object> maps);

    @Multipart
    @POST()
    Observable<QABaseResultCommon> upLoadImage(
            @Url() String url,
            @Part("image\"; filename=\"image.jpg") RequestBody requestBody);

    @Multipart
    @POST()
    Observable<QABaseResultCommon> uploadFlie(
            @Url String fileUrl,
            @Part("description") RequestBody description,
            @Part("image\"; filename=\"image.jpg") MultipartBody.Part file);

    @POST()
    Observable<QABaseResultCommon> uploadFiles(
            @Url() String url,
            @Body Map<String, RequestBody> maps);

    @Multipart
    @POST()
    Observable<QABaseResultCommon> uploadFlieWithPart(
            @Url String fileUrl,
            @Part() MultipartBody.Part file);

    @Multipart
    @POST()
    Observable<ResponseBody> uploadFlieWithPartList(
            @Url String fileUrl,
            @Part List<MultipartBody.Part> list);


    @Multipart
    @POST()
    Observable<QABaseResultCommon> uploadFlieWithPartMap(
            @Url String fileUrl,
            @PartMap Map<String, MultipartBody.Part> maps);


    @POST()
    Observable<QABaseResultCommon> uploadFile(
            @Url() String url,
            @Body RequestBody file);

    @Multipart
    @POST
    Observable<ResponseBody> uploadFileWithPartMap(
            @Url() String url,
            @PartMap() Map<String, RequestBody> partMap,
            @Part() MultipartBody.Part file);

    @Streaming
    @GET
    Observable<QABaseResultCommon> downloadFile(@Url String fileUrl);


    @GET
    Observable<QABaseResultCommon> downloadSmallFile(@Url String fileUrl);


    @GET
    <T> Observable<QABaseResultCommon> getTest(@Url String fileUrl,
                                               @QueryMap Map<String, Object> maps);

    @FormUrlEncoded
    @POST()
    <T> Observable<QABaseResultCommon> postForm(
            @Url() String url,
            @FieldMap Map<String, Object> maps);

    @POST()
    Observable<QABaseResultCommon> postRequestBody(
            @Url() String url,
            @Body RequestBody Body);

    /**
     * 上传一张图片
     *
     * @param description
     * @param img
     * @return
     */
    @Multipart
    @POST("{url}")
    Observable<QABaseResultCommon> uploadImage(@Path(value = "url", encoded = true) String url, @QueryMap Map<String, String> maps, @Part("fileName") String description,
                                               @Part("file\"; filename=\"image.png\"") RequestBody img);
}
