package workstation.zjyk.workstation.modle.net;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import workstation.zjyk.workstation.modle.bean.WSBaseResultCommon;

public class ResultJsonDeser implements JsonDeserializer<WSBaseResultCommon<?>> {

    @Override
    public WSBaseResultCommon<?> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        WSBaseResultCommon response = new WSBaseResultCommon();
        if (json.isJsonObject()) {
            JsonObject jsonObject = json.getAsJsonObject();
            if (jsonObject.get("code") != null) {
                String code = jsonObject.get("code").getAsString();
                response.setCode(code);
            }
            if (jsonObject.get("message") != null) {
                response.setMessage(jsonObject.get("message").getAsString());
            }
//            if (code != 200){
//                return response;
//            }
            Type itemType = ((ParameterizedType) typeOfT).getActualTypeArguments()[0];
            response.setData(context.deserialize(jsonObject.get("data"), itemType));
            return response;
        }
        return response;
    }
}