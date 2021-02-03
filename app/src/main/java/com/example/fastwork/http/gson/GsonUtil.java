package com.example.fastwork.http.gson;

import com.example.fastwork.http.Key;
import com.google.gson.*;
import com.google.gson.annotations.Expose;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;



public class GsonUtil {

    private static Gson sGson;

    public static Gson createGson() {
        return new GsonBuilder()
                .registerTypeAdapter(Integer.class, new IntegerDefaultAdapter())
                .registerTypeAdapter(int.class, new IntegerDefaultAdapter())
                .registerTypeAdapter(Float.class, new FloatDefaultAdapter())
                .registerTypeAdapter(float.class, new FloatDefaultAdapter())
                .registerTypeAdapter(Long.class, new LongDefaultAdapter())
                .registerTypeAdapter(long.class, new LongDefaultAdapter())
                .registerTypeAdapter(Double.class, new DoubleDefaultAdapter())
                .registerTypeAdapter(double.class, new DoubleDefaultAdapter())
                .registerTypeAdapter(String.class, new StringTypeAdapter())
                .create();
    }

    public static Gson getDefault() {
        if (sGson == null)
            sGson = createGson();
        return sGson;
    }

    private static Map<Class, List<Field>> sSerializeFields = new HashMap<>();

    public static List<Field> getSerializeFields(Class clazz) {
        Class cls = clazz;
        List<Field> clsFields = sSerializeFields.get(clazz);
        if (clsFields == null) {
            sSerializeFields.put(clazz, clsFields = new ArrayList<Field>());
        } else {
            return clsFields;
        }
        while (cls != null && cls != Object.class) {

            Field[] fields = cls.getDeclaredFields();
            for (Field field :
                    fields) {
                field.setAccessible(true);
                int modifiers = field.getModifiers();
                if (Modifier.isStatic(modifiers) || Modifier.isTransient(modifiers)) continue;
                Expose annotation = field.getAnnotation(Expose.class);
                if (annotation != null && !annotation.serialize()) {
                    continue;
                }
                clsFields.add(field);

            }

            cls = cls.getSuperclass();

        }
        return clsFields;

    }

    /**
     * @param result
     * @param clazz
     * @return 返回data数组列表
     * @数据格式 {"code":200, "message":"请求成功", "data":[{"thumb":"http://img1.maka.im/public/subject/201706231446278318.jpg",...},{ ...}]}
     */
    public static <T> List<T> getDataList(String result, Class<T[]> clazz) {
        if (StringUtil.isEmpty(result)) {
            return null;
        }
        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(result);
        JsonObject object = element.getAsJsonObject();
        JsonArray jsonArray = object.getAsJsonArray(Key.KEY_DATA);
        T[] arr = getDefault().fromJson(jsonArray.toString(), clazz);
        return new ArrayList<>(Arrays.asList(arr));
    }

    /**
     * @param result
     * @param clazz
     * @param <T>
     * @return 返回data对象里面的dataList列表
     * @数据格式 {"code":200, "message":"请求成功", "data":[{"thumb":"",dataList[]}]}
     */
    public static <T> List<T> getDataInnerList(String result, Class<T[]> clazz) {
        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(result);
        JsonObject object = element.getAsJsonObject();
        JsonObject data = object.getAsJsonObject(Key.KEY_DATA);
        JsonArray dataList = data.getAsJsonArray(Key.KEY_DATALIST);
        T[] arr = getDefault().fromJson(dataList.toString(), clazz);
        return new ArrayList<>(Arrays.asList(arr));
    }

    public static JsonObject getDataObject(String result) {
        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(result);
        JsonObject object = element.getAsJsonObject();
        return object.getAsJsonObject("data");
    }
}
