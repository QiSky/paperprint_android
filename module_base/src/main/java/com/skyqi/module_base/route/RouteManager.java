package com.skyqi.module_base.route;///


import android.app.Activity;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.callback.NavigationCallback;
import com.alibaba.android.arouter.launcher.ARouter;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/// * @ProjectName: paperprint
/// * @Author: qifanxin
/// * @CreateDate: 2022/3/2 3:30 下午
/// * @Description: 文件说明
///
public class RouteManager {

    public final List<WeakReference<Activity>> activityList = new ArrayList<>();

    private static final class Single {
        private static final RouteManager INSTANCE = new RouteManager();
    }

    private RouteManager() {
    }

    public static RouteManager getInstance() {
        return RouteManager.Single.INSTANCE;
    }

    public void navigateTo(String path) {
        ARouter.getInstance().build(path).navigation();
    }

    public void navigateTo(String path, Bundle bundle) {
        ARouter.getInstance().build(path).with(bundle).navigation();
    }

    public void navigateTo(String path, int flags) {
        ARouter.getInstance().build(path).withFlags(flags).navigation();
    }

    public void navigateTo(String path, Bundle bundle, int flags) {
        ARouter.getInstance().build(path).with(bundle).withFlags(flags).navigation();
    }

    public void navigateTo(String path, Bundle bundle, int requestCode, Activity mContext) {
        ARouter.getInstance().build(path).with(bundle).navigation(mContext, requestCode);
    }

    public void clearAllActivity() {
        activityList.forEach(item -> {
            item.get().finish();
        });
        activityList.clear();
    }
}
