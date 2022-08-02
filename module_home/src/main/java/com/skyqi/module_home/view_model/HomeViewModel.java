package com.skyqi.module_home.view_model;///

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/// * @ProjectName: paperprint
/// * @Author: qifanxin
/// * @CreateDate: 2022/3/11 2:30 下午
/// * @Description: 文件说明
///
public class HomeViewModel extends ViewModel {

    private MutableLiveData<Integer> menuIndexLiveData = new MutableLiveData<>();

    public MutableLiveData<Integer> getMenuIndexLiveData() {
        return menuIndexLiveData;
    }

    public long clickTime = 0;

    public void postMenuIndex(Integer index) {
        menuIndexLiveData.postValue(index);
    }


}
