package com.jiandanbaoxian.util;

import android.util.Log;

import com.google.gson.Gson;

import java.lang.reflect.Type;


/**
 * Created by sooglejay on 16/3/5.
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

    public static <T> T getSerializedObject(Object obj, Class<T> type) {
        T bean = null;
        try {
            String json = com.jiandanbaoxian.util.JsonUtil.toJson(obj);
            bean = com.jiandanbaoxian.util.JsonUtil.fromJson(json, type);
        } catch (Exception e) {
            Log.e("qw", "出错啦！！！!");
        }
        return bean;
    }
}
