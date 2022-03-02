package com.skyqi.paperprint.splash.view_model;///

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.skyqi.module_base.http.HttpCode;
import com.skyqi.module_base.http.HttpManager;
import com.skyqi.module_base.http.service.LoginService;
import com.skyqi.module_base.model.ApiResponse;
import com.skyqi.module_base.model.UserModel;
import com.skyqi.module_base.route.RouteBase;
import com.skyqi.module_base.route.RouteManager;
import com.tencent.mmkv.MMKV;

/// * @ProjectName: paperprint
/// * @Author: qifanxin
/// * @CreateDate: 2022/3/1 4:52 下午
/// * @Description: 文件说明
///
public class SplashViewModel extends ViewModel {

    public final String USERSTOREFLAG = "user_login";

    private LoginService mLoginService;

    public final MMKV mmkv;

    public SplashViewModel() {
        mLoginService = HttpManager.getInstance().getRetrofit().create(LoginService.class);
        mmkv = MMKV.defaultMMKV();
    }

    public void init(LoginTokenCallBack loginTokenCallBack) {
        if (mmkv.containsKey(USERSTOREFLAG)) {
            UserModel userModel = mmkv.decodeParcelable(USERSTOREFLAG, UserModel.class);
            loginTokenCallBack.getLiveData(mLoginService.loginToken());
        } else {
            RouteManager.getInstance().navigateTo(RouteBase.LOGIN);
        }
    }

    interface LoginTokenCallBack {

        void getLiveData(LiveData<ApiResponse<UserModel>> liveData);
    }
}
