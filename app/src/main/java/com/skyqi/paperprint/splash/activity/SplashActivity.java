package com.skyqi.paperprint.splash.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.skyqi.module_base.http.ApiManager;
import com.skyqi.module_base.http.HttpCode;
import com.skyqi.module_base.http.HttpManager;
import com.skyqi.module_base.model.ApiResponse;
import com.skyqi.module_base.model.UserModel;
import com.skyqi.module_base.model.view_model.ApplicationViewModel;
import com.skyqi.module_base.route.RouteBase;
import com.skyqi.module_base.route.RouteManager;
import com.skyqi.paperprint.splash.view_model.SplashViewModel;

@Route(path = RouteBase.SPLASH)
public class SplashActivity extends AppCompatActivity {

    private SplashViewModel mSplashViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ///初始化HttpManager(由于数据存储在全局ViewModel，所以不能在Application中初始化）
        HttpManager.getInstance().initUrl(ApiManager.REST_URL, new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(ApplicationViewModel.class));
        mSplashViewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(SplashViewModel.class);
        mSplashViewModel.init(new SplashViewModel.LoginTokenCallBack() {
            @Override
            public void getLiveData(LiveData<ApiResponse<UserModel>> liveData) {
                liveData.observe(SplashActivity.this, new Observer<ApiResponse<UserModel>>() {
                    @Override
                    public void onChanged(ApiResponse<UserModel> userModelApiResponse) {
                        ///如果根据token且成功登陆，则直接跳转主界面
                        if (userModelApiResponse != null && HttpCode.SUCCESS == userModelApiResponse.getCode()) {
                            UserModel userModel = userModelApiResponse.getData();
                            mSplashViewModel.mmkv.encode(mSplashViewModel.USERSTOREFLAG, userModel);
                            new ViewModelProvider(SplashActivity.this, new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(ApplicationViewModel.class).setUserModel(userModel);
                        } else {
                            RouteManager.getInstance().navigateTo(RouteBase.LOGIN);
                        }
                    }
                });
            }
        });
    }
}