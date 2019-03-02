package workstation.zjyk.line.modle.request;

import com.zhy.http.okhttp.callback.Callback;
import workstation.zjyk.line.modle.BaseRequest;

import java.util.Map;

/**
 * Created by zjgz on 2017/10/25.
 */

public class ReciverRequest extends BaseRequest {
    public ReciverRequest(Object tag) {
        super(tag);
    }

    /**
     * 请求列表
     * @param map
     * @param callback
     */
    public void requestData(Map<String, String> map, Callback callback) {
//        post(Constants.getHostShanghaiTwoTest() + Constants.REQUEST_NEWS_LIST, map, callback);
    }
}
