package com.skyqi.paperprint.splash.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
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
import com.skyqi.module_base.view.activity.BaseViewModelActivity;
import com.skyqi.module_login.login.view_model.LoginViewModel;

@Route(path = RouteBase.SPLASH)
public class SplashActivity extends BaseViewModelActivity {

    private LoginViewModel mSplashViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSplashViewModel.autoLogin(new LoginViewModel.LoginTokenCallBack() {
            @Override
            public void getLiveData(LiveData<ApiResponse<UserModel>> liveData) {
                liveData.observe(SplashActivity.this, new Observer<ApiResponse<UserModel>>() {
                    @Override
                    public void onChanged(ApiResponse<UserModel> userModelApiResponse) {
                        if (userModelApiResponse != null && HttpCode.SUCCESS == userModelApiResponse.getCode()) {
                            mSplashViewModel.saveData(SplashActivity.this, getApplication(), userModelApiResponse.getData());
                        }
                        RouteManager.getInstance().navigateTo(RouteBase.HOME);
                        finish();
                    }
                });
            }
        });
    }

    @Override
    protected void initViewModel() {
        mSplashViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
    }
}