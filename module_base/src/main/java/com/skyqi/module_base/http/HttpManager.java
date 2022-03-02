package com.skyqi.module_base.http;///

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.skyqi.module_base.model.view_model.ApplicationViewModel;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/// * @ProjectName: paperprint
/// * @Author: qifanxin
/// * @CreateDate: 2022/3/1 10:58 上午
/// * @Description: 文件说明
///
public class HttpManager {

    private final ConcurrentHashMap<String, Retrofit> mRetrofitConcurrentHashMap;

    private final long mConnectTime = 8000;

    private String mBaseUrl = null;

    private Gson mDataFormat;

    ApplicationViewModel mApplicationViewModel;

    private static final class Single {
        private static final HttpManager INSTANCE = new HttpManager();
    }

    private HttpManager() {
        mRetrofitConcurrentHashMap = new ConcurrentHashMap<>();
        mDataFormat = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();
    }

    public static HttpManager getInstance() {
        return Single.INSTANCE;
    }

    public void initUrl(String baseUrl,  ApplicationViewModel applicationViewModel) {
        this.mBaseUrl = baseUrl;
        this.mApplicationViewModel = applicationViewModel;
    }

    public Retrofit getRetrofit() {
        Retrofit retrofit = mRetrofitConcurrentHashMap.get(mBaseUrl);
        if (retrofit == null) {
            throw new RuntimeException("baseUrl为空");
        }
        return retrofit;
    }

    public Retrofit getRetrofit(String baseUrl) {
        Retrofit retrofit = mRetrofitConcurrentHashMap.get(baseUrl);
        if (retrofit == null) {
            retrofit = createRetrofit(baseUrl);
            mRetrofitConcurrentHashMap.putIfAbsent(baseUrl, retrofit);
        }
        return retrofit;
    }

    private Retrofit createRetrofit(String baseUrl) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(getOkHttpBuilder().build())
                .addCallAdapterFactory(new LiveDataCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create(mDataFormat)).build();
    }

    private OkHttpClient.Builder getOkHttpBuilder() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(mConnectTime, TimeUnit.MILLISECONDS)
                .readTimeout(mConnectTime, TimeUnit.MILLISECONDS)
                .writeTimeout(mConnectTime, TimeUnit.MILLISECONDS);
        builder.addInterceptor((chain) -> {
            Request original = chain.request();
            Request.Builder requestBuilder = original.newBuilder()
                    .header("Accept-Language", "zh-CN,zh;q=0.8")
                    .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*")
                    .header("Connection", "Keep-Alive");
            if(mApplicationViewModel.getUserModel() != null && mApplicationViewModel.getUserModel().getToken() != null) {
                requestBuilder.addHeader("token", mApplicationViewModel.getUserModel().getToken());
            }
            requestBuilder.addHeader();
            Request request = requestBuilder.build();
            return chain.proceed(request);
        });
        builder.addInterceptor(new HttpLoggingInterceptor());
        return builder;
    }
}
