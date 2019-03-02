package cn.com.ethank.mylibrary.resourcelibrary.common_util;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by hanxue on 17/7/14.
 */

public class JsonUtil {
    private JsonUtil() {
    }

    /**
     * 对象转换成json字符串
     *
     * @param obj
     * @return
     */
    public static String toJson(Object obj) {
        Gson gson = new Gson();
        return gson.toJson(obj);
    }

    /**
     * json字符串转成对象
     *
     * @param str
     * @param type
     * @return
     */
    public static <T> T fromJson(String str, Type type) {
        Gson gson = new Gson();
        return gson.fromJson(str, type);
    }

    /**
     * json字符串转成对象
     *
     * @param str
     * @param type
     * @return
     */
    public static <T> T fromJson(String str, Class<T> type) {
        Gson gson = new Gson();
        return gson.fromJson(str, type);
    }

    /**
     * @param json
     * @param clazz
     * @return
     * @author I321533
     */
    public static <T> List<T> jsonToList(String json, Class<T[]> clazz) {
        Gson gson = new Gson();
        T[] array = gson.fromJson(json, clazz);
        return Arrays.asList(array);
    }

    /**
     * @param json
     * @param clazz
     * @return
     */
    public static <T> ArrayList<T> jsonToArrayList(String json, Class<T> clazz) {
        Type type = new TypeToken<ArrayList<JsonObject>>() {
        }.getType();
        ArrayList<JsonObject> jsonObjects = new Gson().fromJson(json, type);

        ArrayList<T> arrayList = new ArrayList<>();
        for (JsonObject jsonObject : jsonObjects) {
            arrayList.add(new Gson().fromJson(jsonObject, clazz));
        }
        return arrayList;
    }

    /**
     * 将Map转化为Json
     *
     * @param map
     * @return String
     */
    public static <T> String mapToJson(Map<String, T> map) {
        Gson gson = new Gson();
        String jsonStr = gson.toJson(map);
        return jsonStr;
    }

    /**
     * GSON解析json数据
     *
     * @param json
     * @param cls
     * @return
     * @author zjh
     */

    public static <T> T parse(String json, Type cls) {
        try {
            GsonBuilder builder = new GsonBuilder();

            // Register an adapter to manage the date types as long values
            builder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
                public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                    return new Date(json.getAsJsonPrimitive().getAsLong());
                }
            });

            Gson gson = builder.create();
            return gson.fromJson(json, cls);
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("", "数据解析异常--" + e.getMessage());
        }
        return null;
    }

}
