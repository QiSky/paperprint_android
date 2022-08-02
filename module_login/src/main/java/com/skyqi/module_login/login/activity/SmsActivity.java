package com.skyqi.module_login.login.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.skyqi.module_base.data.DataConstant;
import com.skyqi.module_base.http.HttpCode;
import com.skyqi.module_base.model.ApiResponse;
import com.skyqi.module_base.model.UserModel;
import com.skyqi.module_base.route.RouteBase;
import com.skyqi.module_base.route.RouteManager;
import com.skyqi.module_base.utils.ToastUtil;
import com.skyqi.module_base.view.VerifyEditText;
import com.skyqi.module_base.view.activity.BaseViewModelActivity;
import com.skyqi.module_login.databinding.ActivitySmsBinding;
import com.skyqi.module_login.login.view_model.LoginViewModel;
import com.skyqi.module_login.login.view_model.SmsViewModel;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

@Route(path = RouteBase.LOGIN_SMS)
public class SmsActivity extends BaseViewModelActivity {

    private ActivitySmsBinding mActivitySmsBinding;

    private LoginViewModel mLoginViewModel;

    private SmsViewModel mSmsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivitySmsBinding = ActivitySmsBinding.inflate(getLayoutInflater());
        setContentView(mActivitySmsBinding.getRoot());
        String phone = getIntent().getStringExtra(DataConstant.LOGIN_PHONE_KEY);
        mActivitySmsBinding.tvSmsTips.setText("验证码已发送至:" + phone.substring(0, 3) + "*******");
        mSmsViewModel.getSecondsLiveData().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer i) {
                mActivitySmsBinding.tvSmsResend.setText("重新发送(" + i + ")");
                mActivitySmsBinding.tvSmsResend.setTextColor(com.skyqi.module_base.R.color.second_font_color);
            }
        });
        mSmsViewModel.getIsPostLiveData().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean b) {
                if (b) {
                    mActivitySmsBinding.tvSmsResend.setText("发送验证码");
                    mActivitySmsBinding.tvSmsResend.setTextColor(com.skyqi.module_base.R.color.primaryColor);
                } else {
                    mSmsViewModel.getSecondsLiveData().postValue(60);
                }
            }
        });
        mActivitySmsBinding.vedtSmsCode.setInputListener(content -> mLoginViewModel.loginSmsAction(phone, content).observe(SmsActivity.this, new Observer<ApiResponse<UserModel>>() {
            @Override
            public void onChanged(ApiResponse<UserModel> userModelApiResponse) {
                if (userModelApiResponse != null && HttpCode.SUCCESS == userModelApiResponse.getCode()) {
                    mLoginViewModel.saveData(SmsActivity.this, getApplication(), userModelApiResponse.getData());
                    RouteManager.getInstance().navigateTo(RouteBase.HOME);
                    finish();
                } else {
                    ToastUtil.showToast(getApplicationContext(), "登录失败");
                }
            }
        }));
        mActivitySmsBinding.tvSmsResend.setOnClickListener(v -> {
            if (mSmsViewModel.getIsPostLiveData().getValue()) {
                getCode(phone);
            }
        });
        getCode(phone);
    }

    private void getCode(String phone) {
        mLoginViewModel.getSmsAction(phone).observe(this, new Observer<ApiResponse<Object>>() {
            @Override
            public void onChanged(ApiResponse<Object> objectApiResponse) {
                mSmsViewModel.setIsPostLiveData(false);
                mSmsViewModel.startTimeCount(SmsActivity.this);
            }
        });
    }

    @Override
    protected void initViewModel() {
        mLoginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        mSmsViewModel = new ViewModelProvider(this).get(SmsViewModel.class);
    }

}