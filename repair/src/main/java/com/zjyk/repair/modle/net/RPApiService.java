package com.zjyk.repair.modle.net;

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

import com.zjyk.repair.modle.bean.RPBaseResultCommon;
import com.zjyk.repair.modle.bean.RPBaseResultCommon;

/**
 * Created by zjgz on 2017/11/2.
 */

public interface RPApiService {
    @POST()
    @FormUrlEncoded
    Observable<RPBaseResultCommon> executePost(
            @Url() String url,
            @FieldMap Map<String, String> maps);

    @POST("{url}")
    Observable<RPBaseResultCommon> executePostBody(
            @Path("url") String url,
            @Body Object object);

    @GET()
    Observable<RPBaseResultCommon> executeGet(
            @Url String url,
            @QueryMap Map<String, String> maps);

    @DELETE()
    Observable<RPBaseResultCommon> executeDelete(
            @Url String url,
            @QueryMap Map<String, Object> maps);

    @PUT()
    Observable<RPBaseResultCommon> executePut(
            @Url String url,
            @FieldMap Map<String, Object> maps);

    @Multipart
    @POST()
    Observable<RPBaseResultCommon> upLoadImage(
            @Url() String url,
            @Part("image\"; filename=\"image.jpg") RequestBody requestBody);

    @Multipart
    @POST()
    Observable<RPBaseResultCommon> uploadFlie(
            @Url String fileUrl,
            @Part("description") RequestBody description,
            @Part("image\"; filename=\"image.jpg") MultipartBody.Part file);

    @POST()
    Observable<RPBaseResultCommon> uploadFiles(
            @Url() String url,
            @Body Map<String, RequestBody> maps);

    @Multipart
    @POST()
    Observable<RPBaseResultCommon> uploadFlieWithPart(
            @Url String fileUrl,
            @Part() MultipartBody.Part file);

    @Multipart
    @POST()
    Observable<ResponseBody> uploadFlieWithPartList(
            @Url String fileUrl,
            @Part List<MultipartBody.Part> list);


    @Multipart
    @POST()
    Observable<RPBaseResultCommon> uploadFlieWithPartMap(
            @Url String fileUrl,
            @PartMap Map<String, MultipartBody.Part> maps);


    @POST()
    Observable<RPBaseResultCommon> uploadFile(
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
    Observable<ResponseBody> downloadFile(@Url String fileUrl);


    @GET
    Observable<RPBaseResultCommon> downloadSmallFile(@Url String fileUrl);


    @GET
    <T> Observable<RPBaseResultCommon> getTest(@Url String fileUrl,
                                               @QueryMap Map<String, Object> maps);

    @FormUrlEncoded
    @POST()
    <T> Observable<RPBaseResultCommon> postForm(
            @Url() String url,
            @FieldMap Map<String, Object> maps);

    @POST()
    Observable<RPBaseResultCommon> postRequestBody(
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
    Observable<RPBaseResultCommon> uploadImage(@Path(value = "url", encoded = true) String url, @QueryMap Map<String, String> maps, @Part("fileName") String description,
                                               @Part("file\"; filename=\"image.png\"") RequestBody img);
}
