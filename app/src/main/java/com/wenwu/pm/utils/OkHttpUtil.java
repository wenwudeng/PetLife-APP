package com.wenwu.pm.utils;


import android.os.Looper;
import android.widget.Toast;

import com.wenwu.pm.activity.MainActivity;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * @author:wenwudeng
 * @date:11:20 AM 3/17/2020
 */
public class OkHttpUtil {
    public static final String USER_PATH = "http://192.168.1.112:8080/api/user/";
    public static final String UPLOAD_IMG_PATH = "http://47.101.171.252:8890/uploadFile";
    public static final String UPLOAD_URL = "http://47.101.171.252:8890/uploadFile?username=";



     /*// 发送GET请求
    public static void sendGetRequest(String url, okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient(); // 创建一个client
        Request request = new Request.Builder().url(url).build(); // 组装一个Request对象
        client.newCall(request).enqueue(callback); // 发送请求
    }*/

    // 发送POST请求
    public static void sendPostRequest(String url, Map<String, Object> map, okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient(); // 创建一个client
        // POST请求的参数需要放在一个RequestBody对象中，它由FormBody.Builder建造者类来建造（建造者模式）
        FormBody.Builder builder = new FormBody.Builder();
        if(map != null){
            // 不能直接把map转为RequestBody，必须遍历map的key，并逐一地往builder中添加对应的value
            Set<String> keys = map.keySet();
            for(String key : keys){ // 注意：RequestBody的参数只能是字符串类型的
                String value = map.get(key).toString();
                System.out.println(key+":"+value);
                builder.add(key, value);

            }
        }
        RequestBody requestBody = builder.build(); // 最后利用builder来生成一个RequestBody实例
        // 组装一个Request对象（这次有额外传入RequestBody）
        Request request = new Request.Builder().url(USER_PATH+url).post(requestBody).build();
        client.newCall(request).enqueue(callback); // 发送请求
    }



    public static void uploadImage(String username, String imagePath,Callback callback) {
        final String imageUrl;
        OkHttpClient okHttpClient = new OkHttpClient();
        File file = new File(imagePath);
        if (!file.exists()) {
            file.mkdir();
        }
        RequestBody image = RequestBody.create(MediaType.parse("image/*"), file);
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", imagePath, image)
                .build();
        Request request = new Request.Builder()
                .url(UPLOAD_URL+username)
                .post(requestBody)
                .build();
        okHttpClient.newCall(request).enqueue(callback);
    }





}
