package com.skyqi.module_base.utils;///

import android.content.Context;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

/// * @ProjectName: paperprint
/// * @Author: qifanxin
/// * @CreateDate: 2022/3/11 11:47 上午
/// * @Description: 文件说明
///
public class ToastUtil {

    public static void showToast(@NotNull Context context, String content) {
        Toast.makeText(context, content, Toast.LENGTH_LONG).show();
    }
}
