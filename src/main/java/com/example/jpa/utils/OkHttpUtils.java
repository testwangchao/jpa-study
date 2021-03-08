package com.example.jpa.utils;

import okhttp3.*;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * https://blog.csdn.net/knight1996/article/details/77995547
 * https://www.cnblogs.com/whoislcj/p/5526431.html
 */
public class OkHttpUtils {

    private static final String BASE_URL = "http://127.0.0.1:9999";
    private final OkHttpClient mOkHttpClient = client();

    public static OkHttpClient client() {
        return new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .build();
    }

    public Response doGet(String actionUrl, HashMap<String, String> paramsMap) {
        StringBuilder tempParams = new StringBuilder();
        try {
            //处理参数
            int pos = 0;
            for (String key : paramsMap.keySet()) {
                if (pos > 0) {
                    tempParams.append("&");
                }
                //对参数进行URLEncoder
                tempParams.append(String.format("%s=%s", key, URLEncoder.encode(paramsMap.get(key), "utf-8")));
                pos++;
            }
            //补全请求地址
            String requestUrl = String.format("%s/%s?%s", BASE_URL, actionUrl, tempParams.toString());
            //创建一个请求
            Request request = addHeaders().url(requestUrl).build();
            //创建一个Call
            final Call call = mOkHttpClient.newCall(request);
            //执行请求
            final Response response = call.execute();
            return response;
        } catch (Exception e) {
           e.printStackTrace();
        }
        return null;
    }


    public  Response postByJson(String actionUrl, HashMap<String, Object> map) throws IOException {
        //补全请求地址
        String requestUrl = String.format("%s/%s", BASE_URL, actionUrl);
        String postBody = JsonUtils.objectToJson(map);
        RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8"), postBody);
        Request request = new Request.Builder()
                .url(requestUrl)//请求的url
                .post(requestBody)
                .build();
        Call call = mOkHttpClient.newCall(request);
        Response response=null;
        try {
            response = call.execute();
            return response;
        } catch (Exception e){
            e.printStackTrace();
        }
        return response;
    }

    public static Response postForm(Map<String, Object> map) throws IOException {
        OkHttpClient okHttpClient = client();
        FormBody.Builder builder = new FormBody.Builder();
        for (String key : map.keySet()) {
            builder.add(key, map.get(key).toString());
        }
        Request request = new Request.Builder()
                .url("http://127.0.0.1:9999/form")//请求的url
                .post(builder.build())
                .build();
        Call call = okHttpClient.newCall(request);
        Response resp = call.execute();
        return resp;
    }

    private Request.Builder addHeaders(){
        Request.Builder builder = new Request.Builder()
                .addHeader("Connection", "keep-alive");
        return builder;
    }

    public void doPost(String actionUrl, HashMap<String, String> paramsMap){
        try {
            //处理参数
            StringBuilder tempParams = new StringBuilder();
            int pos = 0;
            for (String key : paramsMap.keySet()) {
                if (pos > 0) {
                    tempParams.append("&");
                }
                tempParams.append(String.format("%s=%s", key, URLEncoder.encode(paramsMap.get(key), "utf-8")));
                pos++;
            }
            //补全请求地址
            String requestUrl = String.format("%s/%s", BASE_URL, actionUrl);
            //生成参数
            String params = tempParams.toString();
            //创建一个请求实体对象 RequestBody
            RequestBody body = RequestBody.create(MediaType.parse("application/x-www-form-urlencoded; charset=utf-8"), params);
            //创建一个请求
            final Request request = addHeaders().url(requestUrl).post(body).build();
            //创建一个Call
            final Call call = mOkHttpClient.newCall(request);
            //执行请求
            Response response = call.execute();
            //请求执行成功
            if (response.isSuccessful()) {
                //获取返回数据 可以是String，bytes ,byteStream
                System.out.println(response.body().string());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}