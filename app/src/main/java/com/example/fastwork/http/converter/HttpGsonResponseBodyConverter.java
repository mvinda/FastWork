package com.example.fastwork.http.converter;

import com.example.fastwork.http.Key;
import com.example.fastwork.http.exception.ApiException;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import okhttp3.MediaType;
import okhttp3.ResponseBody;
import org.json.JSONException;
import org.json.JSONObject;
import retrofit2.Converter;

import java.io.*;
import java.nio.charset.Charset;



public class HttpGsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {

    private static final Charset UTF_8 = Charset.forName("UTF-8");
    private final Gson gson;
    private final TypeAdapter<T> adapter;

    HttpGsonResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }

    @Override
    public T convert(ResponseBody responseBody) throws IOException {
        String response = responseBody.string();
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(response);
        } catch (JSONException e) {
            e.printStackTrace();
            throw new ApiException(ApiException.CODE_PARSE_DATA_ERROR);
        }

        if (jsonObject.has(Key.KEY_CODE) && jsonObject.optInt(Key.KEY_CODE) != 200) {
            responseBody.close();
            throw new ApiException(jsonObject.optInt(Key.KEY_CODE), jsonObject.optString(Key.KEY_MESSAGE));
        }

        MediaType contentType = responseBody.contentType();
        Charset charset = contentType != null ? contentType.charset(UTF_8) : UTF_8;
        InputStream inputStream = new ByteArrayInputStream(response.getBytes());
        Reader reader = new InputStreamReader(inputStream, charset);
        JsonReader jsonReader = gson.newJsonReader(reader);

        try {
            return adapter.read(jsonReader);
        } finally {
            responseBody.close();
        }
    }

}
