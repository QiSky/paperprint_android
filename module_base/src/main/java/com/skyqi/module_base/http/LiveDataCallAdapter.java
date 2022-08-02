package com.skyqi.module_base.http;///

import android.util.Log;

import androidx.lifecycle.LiveData;

import com.skyqi.module_base.BuildConfig;
import com.skyqi.module_base.model.ApiResponse;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;
import java.util.concurrent.atomic.AtomicBoolean;

import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Callback;
import retrofit2.Response;

/// * @ProjectName: paperprint
/// * @Author: qifanxin
/// * @CreateDate: 2022/3/1 6:44 下午
/// * @Description: 文件说明
///
public class LiveDataCallAdapter<T> implements CallAdapter<T, LiveData<T>>, HttpCode {

    private final String TAG = "httpTag";

    private final Type mResponseType;

    public LiveDataCallAdapter(Type mResponseType) {
        this.mResponseType = mResponseType;
    }

    @NotNull
    @Override
    public Type responseType() {
        return mResponseType;
    }


    @Override
    public LiveData<T> adapt(Call<T> call) {
        return new LiveData<T>() {
            private final AtomicBoolean started = new AtomicBoolean(false);

            @Override
            protected void onActive() {
                super.onActive();
                if (started.compareAndSet(false, true)) {
                    call.enqueue(new Callback<T>() {
                        @Override
                        public void onResponse(Call<T> call, Response<T> response) {
                            postValue(response.body());
                        }

                        @Override
                        public void onFailure(Call<T> call, Throwable t) {
                            if (BuildConfig.DEBUG) {
                                Log.d(TAG, t.getStackTrace().toString());
                            }
                            postValue(null);
                        }
                    });
                }
            }
        };
    }

}
