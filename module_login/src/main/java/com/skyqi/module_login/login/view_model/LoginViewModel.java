package com.skyqi.module_login.login.view_model;///

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.skyqi.module_base.MainApplication;
import com.skyqi.module_base.data.DataConstant;
import com.skyqi.module_base.http.HttpManager;
import com.skyqi.module_base.http.service.LoginService;
import com.skyqi.module_base.model.ApiResponse;
import com.skyqi.module_base.model.UserModel;
import com.skyqi.module_base.model.view_model.ApplicationViewModel;
import com.skyqi.module_base.route.RouteBase;
import com.skyqi.module_base.route.RouteManager;
import com.skyqi.module_base.utils.DataValidator;
import com.skyqi.module_base.utils.ToastUtil;
import com.skyqi.module_login.login.constant.LoginType;
import com.tencent.mmkv.MMKV;

/// * @ProjectName: paperprint
/// * @Author: qifanxin
/// * @CreateDate: 2022/3/4 3:07 下午
/// * @Description: 文件说明
///
public class LoginViewModel extends ViewModel {

    private MutableLiveData<LoginType> loginTypeLiveData;

    private LoginService mLoginService;

    private final String USERSTOREFLAG = "user_login";

    public final MMKV mmkv;

    public LoginViewModel() {
        mLoginService = HttpManager.getInstance().createService(LoginService.class);
        mmkv = MMKV.defaultMMKV();
        if (loginTypeLiveData == null) {
            loginTypeLiveData = new MutableLiveData<LoginType>();
        }
    }

    ///自动登录
    public void autoLogin(LoginTokenCallBack loginTokenCallBack) {
        if (mmkv.containsKey(USERSTOREFLAG)) {
            UserModel userModel = mmkv.decodeParcelable(USERSTOREFLAG, UserModel.class);
            loginTokenCallBack.getLiveData(mLoginService.loginToken());
        } else {
            RouteManager.getInstance().navigateTo(RouteBase.HOME, Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        }
    }

    public void postLoginType(LoginType loginType) {
        loginTypeLiveData.postValue(loginType);
    }

    public MutableLiveData<LoginType> getLoginType() {
        return loginTypeLiveData;
    }

    ///密码、短信登录
    public LiveData<ApiResponse<UserModel>> loginAction(@NonNull String phone, @NonNull String info) {
        if (DataValidator.validPhone(phone)) {
            if (loginTypeLiveData.getValue() == LoginType.PASSWORD) {
                return mLoginService.login(phone, info, LoginType.PASSWORD.ordinal());
            } else {
                Bundle bundle = new Bundle();
                bundle.putString(DataConstant.LOGIN_PHONE_KEY, phone);
                RouteManager.getInstance().navigateTo(RouteBase.LOGIN_SMS, bundle);
                return null;
            }
        } else {
            ToastUtil.showToast(MainApplication.instance, "手机号格式错误");
            return null;
        }
    }

    public LiveData<ApiResponse<UserModel>> loginSmsAction(@NonNull String phone, @NonNull String code) {
        return mLoginService.login(phone, code, LoginType.MESSAGE.ordinal());
    }

    public LiveData<ApiResponse<Object>> getSmsAction(@NonNull String phone) {
        return mLoginService.sendSMS(phone);
    }

    public void saveData(ViewModelStoreOwner viewModelStoreOwner, Application application, UserModel userModel) {
        mmkv.encode(USERSTOREFLAG, userModel);
        new ViewModelProvider(viewModelStoreOwner, new ViewModelProvider.AndroidViewModelFactory(application)).get(ApplicationViewModel.class).setUserModel(userModel);
    }

    public interface LoginTokenCallBack {

        void getLiveData(LiveData<ApiResponse<UserModel>> liveData);
    }

}

