package workstation.zjyk.line.modle;

import com.alibaba.fastjson.JSON;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import cn.com.ethank.yunge.app.crypt.AbstractCoding;
import cn.com.ethank.yunge.app.crypt.Base64CryptoCoding;
//import cn.com.ethank.yungePad.verticalNew.utils.VerCommonMethod;

/**
 * Created by User on 2016/5/12.
 */
public class BaseRequest {
    private AbstractCoding coding = new Base64CryptoCoding();
    private Object tag;

    public BaseRequest(Object tag) {
        this.tag = tag;
    }

    /**
     * 针对项目的post请求(异步)
     *
     * @param url
     * @param params
     * @param callback
     */
    public void post(String url, Map<String, String> params, Callback callback) {
        try {
            Map<String, String> map = new HashMap<String, String>();
//            map.put("token", Constants.getToken());//URLEncoder.encode(Constants.getToken(), "utf-8")
//            map.put("uuid",Constants.getUuid());
//            map.put("userid",Constants.getUserid());
            setMapToGetTest(params, map);
//		map.put("v", "1.0");
//		map.put("param", coding.encodeUTF8ToUTF8(JSON.toJSONString(params)));
            OkHttpUtils.post().url(url).params(map).tag(tag).build().execute(callback);
//		VerCommonMethod.setDebugLog("post", url, params);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 电影资源请求不需要加密
     *
     * @param url
     * @param map
     * @param callback
     */
    public void postFilm(String url, Map<String, String> map, Callback callback) {
        OkHttpUtils.post().url(url).params(map).tag(tag).build().execute(callback);
    }


    /**
     * 针对项目的get请求(异步)
     *
     * @param url
     * @param params
     * @param callback
     */
    public void get(String url, Map<String, String> params, Callback callback) {
        try {
            Map<String, String> map = new HashMap<String, String>();
//            map.put("token", Constants.getToken());
//            map.put("uuid",Constants.getUuid());
//            map.put("userid",Constants.getUserid());
            //        map.put("uid", Constants.getUid());
            setMapToGetTest(params, map);
//		map.put("v", "1.0");
//		String getParams = setMapToGet(params);
//		map.put("param", coding.encodeUTF8ToUTF8(getParams));
            OkHttpUtils.get().url(url).params(map).tag(tag).build().execute(callback);
//		VerCommonMethod.setDebugLog("get", url, params);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void getLogin(String url, Map<String, String> params, Callback callback) {
        OkHttpUtils.get().url(url).params(params).tag(tag).build().execute(callback);
    }

    /**
     * 电影资源请求不需要加密
     *
     * @param url
     * @param map
     * @param callback
     */
    public void getFilm(String url, Map<String, String> map, Callback callback) {
        OkHttpUtils.get().url(url).params(map).tag(tag).build().execute(callback);
    }

    /**
     * 将map转换成get形式的url
     *
     * @param hashMap
     * @return
     */
    private String setMapToGet(Map<String, String> hashMap) {
        StringBuilder sb = new StringBuilder();
        if (hashMap != null && !hashMap.isEmpty()) {
            for (String key : hashMap.keySet()) {
                sb.append(key).append("=").append(hashMap.get(key)).append("&");
            }
        } else {
            return "";
        }

        sb = sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    private void setMapToGetTest(Map<String, String> hashMap, Map<String, String> map) {
        try {
            if (hashMap != null && !hashMap.isEmpty()) {
                for (String key : hashMap.keySet()) {
                    map.put(key, hashMap.get(key));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 针对项目的get请求(同步)
     *
     * @param url
     * @param params
     */
    public void getSyc(String url, Map<String, String> params) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("v", "1.0");
        String getParams = setMapToGet(params);
        map.put("param", coding.encodeUTF8ToUTF8(getParams));
        try {
            OkHttpUtils.get().url(url).params(map).tag(tag).build().execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 针对项目的post请求(同步)
     *
     * @param url
     * @param params
     */
    public void postSyc(String url, Map<String, String> params) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("v", "1.0");
        map.put("param", coding.encodeUTF8ToUTF8(JSON.toJSONString(params)));
        try {
            OkHttpUtils.post().url(url).params(map).tag(tag).build().execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 原生的post请求
     *
     * @param url
     * @param params
     * @param callback
     */
    public void postPrimary(String url, Map<String, String> params, Callback callback) {
        OkHttpUtils.post().url(url).params(params).tag(tag).build().execute(callback);
    }

    /**
     * 原生的get请求
     *
     * @param url
     * @param params
     * @param callback
     */
    public void getPrimary(String url, Map<String, String> params, Callback callback) {
        OkHttpUtils.get().url(url).params(params).tag(tag).build().execute(callback);
    }

    /**
     * 取消tag的请求
     */
    public void cancelRequest() {
        OkHttpUtils.getInstance().cancelTag(tag);
    }

    /**
     * 文件下载
     *
     * @param url
     * @param callback
     */
    public void downLoad(String url, Callback callback) {
        OkHttpUtils.get().url(url).build().execute(callback);
    }

    /**
     * 文件上传
     */
    public void upLoad(String url, String fileName, File file, Callback callback) {
        OkHttpUtils.post()//
                .addFile("mFile", fileName, file)//
                //  .addFile("mFile", "test1.txt", file2)//可多文件上传
                .url(url)
                //.params(params)
                //.headers(headers)//
                .build()//
                .execute(callback);
    }
}
