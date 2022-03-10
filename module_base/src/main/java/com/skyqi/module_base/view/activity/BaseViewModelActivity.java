package com.skyqi.module_base.view.activity;///

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

/// * @ProjectName: paperprint
/// * @Author: qifanxin
/// * @CreateDate: 2022/3/10 2:23 下午
/// * @Description: 文件说明
///
public abstract class BaseViewModelActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViewModel();
    }

    protected abstract void initViewModel();
}
