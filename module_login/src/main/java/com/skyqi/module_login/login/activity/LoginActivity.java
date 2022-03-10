package com.skyqi.module_login.login.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.skyqi.module_base.http.HttpCode;
import com.skyqi.module_base.listener.SingleClickListener;
import com.skyqi.module_base.model.ApiResponse;
import com.skyqi.module_base.model.UserModel;
import com.skyqi.module_base.model.view_model.ApplicationViewModel;
import com.skyqi.module_base.route.RouteBase;
import com.skyqi.module_base.route.RouteManager;
import com.skyqi.module_base.view.activity.BaseViewModelActivity;
import com.skyqi.module_login.R;
import com.skyqi.module_login.databinding.ActivityLoginBinding;
import com.skyqi.module_login.login.constant.LoginType;
import com.skyqi.module_login.login.view_model.LoginViewModel;

@Route(path = RouteBase.LOGIN)
public class LoginActivity extends BaseViewModelActivity {

    private LoginViewModel mLoginViewModel;

    private ActivityLoginBinding mActivityLoginBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityLoginBinding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(mActivityLoginBinding.getRoot());
        mLoginViewModel.getLoginType().observe(this, loginType -> {
            if (loginType == LoginType.MESSAGE) {
                mActivityLoginBinding.tvLoginSwitch.setText(com.skyqi.module_base.R.string.login_password_switch_text);
            } else if (loginType == LoginType.PASSWORD) {
                mActivityLoginBinding.tvLoginSwitch.setText(com.skyqi.module_base.R.string.login_phone_switch_text);
            }
        });
        mLoginViewModel.postLoginType(LoginType.MESSAGE);
        mActivityLoginBinding.tvLoginSwitch.setOnClickListener(new SingleClickListener() {
            @Override
            protected void onSingleClick(View v) {
                LoginType loginType = mLoginViewModel.getLoginType().getValue();
                if (loginType == LoginType.MESSAGE) {
                    mActivityLoginBinding.edtLoginPassword.setVisibility(View.INVISIBLE);
                    mActivityLoginBinding.tvLoginHintText.setVisibility(View.VISIBLE);
                    mLoginViewModel.postLoginType(LoginType.PASSWORD);
                } else if (loginType == LoginType.PASSWORD) {
                    mActivityLoginBinding.edtLoginPassword.setVisibility(View.VISIBLE);
                    mActivityLoginBinding.tvLoginHintText.setVisibility(View.INVISIBLE);
                    mLoginViewModel.postLoginType(LoginType.MESSAGE);
                }
            }
        });
        mActivityLoginBinding.btnLoginNext.setOnClickListener(new SingleClickListener() {
            @Override
            protected void onSingleClick(View v) {
                if (mActivityLoginBinding.ckbLoginAgreement.isChecked()) {
                    LiveData<ApiResponse<UserModel>> liveData = mLoginViewModel.loginAction(mActivityLoginBinding.edtLoginPhone.getText().toString().trim(), mActivityLoginBinding.edtLoginPassword.getText().toString().trim());
                    if (liveData != null) {
                        liveData.observe(LoginActivity.this, new Observer<ApiResponse<UserModel>>() {
                            @Override
                            public void onChanged(ApiResponse<UserModel> userModelApiResponse) {
                                if (userModelApiResponse != null && HttpCode.SUCCESS == userModelApiResponse.getCode()) {
                                    mLoginViewModel.saveData(LoginActivity.this, getApplication(), userModelApiResponse.getData());
                                    RouteManager.getInstance().navigateTo(RouteBase.HOME);
                                    finish();
                                } else {
                                    /// TODO 请求失败
                                }
                            }
                        });
                    }
                } else {
                    /// TODO 不点击同意处理
                }
            }
        });
    }

    @Override
    protected void initViewModel() {
        mLoginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
    }
}