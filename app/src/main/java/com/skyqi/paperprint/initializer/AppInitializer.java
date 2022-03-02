package com.skyqi.paperprint.initializer;///

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.startup.Initializer;

import com.alibaba.android.arouter.launcher.ARouter;
import com.tencent.mmkv.MMKV;

import java.util.ArrayList;
import java.util.List;

/// * @ProjectName: paperprint
/// * @Author: qifanxin
/// * @CreateDate: 2022/3/1 5:16 下午
/// * @Description: 文件说明
///
public class AppInitializer implements Initializer<Object> {

    @NonNull
    @Override
    public Object create(@NonNull Context context) {
        MMKV.initialize(context);
        return null;
    }

    @NonNull
    @Override
    public List<Class<? extends Initializer<?>>> dependencies() {
        return new ArrayList<>();
    }
}
