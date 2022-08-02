package com.skyqi.module_home.adapter;///

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.List;

/// * @ProjectName: paperprint
/// * @Author: qifanxin
/// * @CreateDate: 2022/3/11 3:21 下午
/// * @Description: 文件说明
///
public class HomeContainerAdapter extends FragmentStateAdapter {

    final List<Fragment> mFragmentList = new ArrayList<>();

    public HomeContainerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    public void setFragmentList(List<Fragment> list) {
        mFragmentList.clear();
        mFragmentList.addAll(list);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getItemCount() {
        return mFragmentList.size();
    }
}
