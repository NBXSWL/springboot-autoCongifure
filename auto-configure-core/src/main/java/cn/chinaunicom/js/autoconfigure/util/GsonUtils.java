package cn.chinaunicom.js.autoconfigure.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

/**
 * 
 * @Description: 基于com.google.code.gson封装的json转换工具类
 * @author lgs
 * @date 2018年5月30日
 *
 */
public class GsonUtils {
    private static Gson gson = null;
    static {
        if (gson == null) {
            gson = new GsonBuilder().serializeNulls().create();
        }
    }

    private GsonUtils() {}

    /**
     * 对象转成json
     *
     * @param object
     * @return json
     */
    public static JsonObject jsonStringToObject(String jsonStr) {
        JsonObject json = null;
        if (gson != null) {
            json = gson.fromJson(jsonStr, JsonObject.class);
        }
        if (json == null) {
            json = new JsonObject();
        }
        return json;
    }

    /**
     * 对象转成json
     *
     * @param object
     * @return json
     */
    public static String gsonString(Object object) {
        String gsonString = null;
        if (gson != null) {
            gsonString = gson.toJson(object);
        }
        return gsonString;
    }

    /**
     * Json转成对象
     *
     * @param gsonString
     * @param cls
     * @return 对象
     */
    public static <T> T gsonToBean(String gsonString, Class<T> cls) {
        T t = null;
        if (gson != null) {
            t = gson.fromJson(gsonString, cls);
        }
        return t;
    }

    /**
     * json转成list<T>
     *
     * @param gsonString
     * @param cls
     * @return list<T>
     */
    public static <T> List<T> gsonToList(String gsonString, Class<T> cls) {
        List<T> list = null;
        if (gson != null) {
            list = gson.fromJson(gsonString, new TypeToken<List<T>>() {}.getType());
        }
        return list;
    }

    /**
     * json转成list中有map的
     *
     * @param gsonString
     * @return List<Map<String, T>>
     */
    public static <T> List<Map<String, T>> gsonToListMaps(String gsonString) {
        List<Map<String, T>> list = null;
        if (gson != null) {
            list = gson.fromJson(gsonString, new TypeToken<List<Map<String, T>>>() {}.getType());
        }
        return list;
    }

    /**
     * json转成map的
     *
     * @param gsonString
     * @return Map<String, T>
     */
    public static <T> Map<String, T> gsonToMaps(String gsonString) {
        Map<String, T> map = null;
        if (gson != null) {
            map = gson.fromJson(gsonString, new TypeToken<Map<String, T>>() {}.getType());
        }
        return map;
    }

    /**
     * 将json字符串转为List
     * 
     * @param json
     *            json字符串
     * @param clazz
     *            List中的类对象
     * @return List
     */
    protected <T> List<T> jsonString2List(String json, Class<?> clazz) {
        Type type = new ParameterizedTypeImpl(clazz);
        List<T> list = new Gson().fromJson(json, type);
        return list;
    }

    /**
     * 获取泛型实际类型
     * 
     * @author chenkexiao
     *
     */
    private class ParameterizedTypeImpl implements ParameterizedType {
        Class<?> clazz;

        public ParameterizedTypeImpl(Class<?> clz) {
            clazz = clz;
        }

        @Override
        public Type[] getActualTypeArguments() {
            return new Type[] {clazz};
        }

        @Override
        public Type getRawType() {
            return List.class;
        }

        @Override
        public Type getOwnerType() {
            return null;
        }
    }
}
