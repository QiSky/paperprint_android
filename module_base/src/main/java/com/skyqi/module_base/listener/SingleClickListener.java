package com.skyqi.module_base.listener;///

import android.view.View;

import java.util.Calendar;

/// * @ProjectName: paperprint
/// * @Author: qifanxin
/// * @CreateDate: 2022/3/4 4:58 下午
/// * @Description: 文件说明
///
public abstract class SingleClickListener implements View.OnClickListener {

    public static final int MIN_CLICK_DELAY_TIME = 1000;//这里设置不能超过多长时间

    private long lastClickTime = 0;

    protected abstract void onSingleClick(View v);

    @Override
    public void onClick(View v) {
        long currentTime = Calendar.getInstance().getTimeInMillis();
        if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {
            lastClickTime = currentTime;
            onSingleClick(v);
        }
    }
}
