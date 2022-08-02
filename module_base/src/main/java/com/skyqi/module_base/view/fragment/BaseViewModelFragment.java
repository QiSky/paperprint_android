package com.skyqi.module_base.view.fragment;///

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/// * @ProjectName: paperprint
/// * @Author: qifanxin
/// * @CreateDate: 2022/3/25 3:46 下午
/// * @Description: 文件说明
///
public abstract class BaseViewModelFragment extends Fragment {

    protected abstract void initViewModel();

}
