package com.skyqi.paperprint.initializer;///

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.multidex.MultiDexApplication;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.skyqi.module_base.http.ApiManager;
import com.skyqi.module_base.http.HttpManager;
import com.skyqi.module_base.route.RouteManager;
import com.skyqi.paperprint.BuildConfig;

import java.lang.ref.WeakReference;
import java.util.List;

/// * @ProjectName: paperprint
/// * @Author: qifanxin
/// * @CreateDate: 2022/3/2 3:28 下午
/// * @Description: 文件说明
///
public class MainApplication extends MultiDexApplication implements Application.ActivityLifecycleCallbacks {

    @Override
    public void onCreate() {
        super.onCreate();
        ARouter.init(this);
        if (BuildConfig.DEBUG) {
            ARouter.openLog();
            ARouter.openDebug();
        }
        HttpManager.getInstance().initUrl(ApiManager.REST_URL);
        registerActivityLifecycleCallbacks(this);
    }

    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
        RouteManager.getInstance().activityList.add(new WeakReference<Activity>(activity));
    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {

    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {

    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {

    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {
//        Route r = activity.getClass().getAnnotation(Route.class);
        List<WeakReference<Activity>> activityList = RouteManager.getInstance().activityList;
        if (activityList.size() != 0) {
            if (activity.getClass() == activityList.get(activityList.size() - 1).get().getClass()) {
                activityList.remove(activityList.size() - 1);
            }
        }
    }
}
