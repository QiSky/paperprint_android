package com.skyqi.module_base.route.interceptor;///

import android.content.Context;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Interceptor;
import com.alibaba.android.arouter.facade.callback.InterceptorCallback;
import com.alibaba.android.arouter.facade.template.IInterceptor;
import com.skyqi.module_base.data.DataManager;
import com.skyqi.module_base.route.RouteBase;
import com.skyqi.module_base.route.RouteManager;

/// * @ProjectName: paperprint
/// * @Author: qifanxin
/// * @CreateDate: 2022/3/9 11:47 上午
/// * @Description: 登录拦截器
///
@Interceptor(priority = 1)
public class LoginInterceptor implements IInterceptor {

    @Override
    public void process(Postcard postcard, InterceptorCallback callback) {
        if(postcard.getPath().contains("/app/splash") && postcard.getPath().contains("/app/login")) {
            if(DataManager.userModel != null) {
                callback.onContinue(null);
            } else {
                callback.onInterrupt(null);
                RouteManager.getInstance().navigateTo(RouteBase.LOGIN);
            }
        } else {
            callback.onContinue(null);
        }
    }

    @Override
    public void init(Context context) {

    }
}
