package com.skyqi.module_login.login.view_model;///

import androidx.lifecycle.MutableLiveData;

/// * @ProjectName: paperprint
/// * @Author: qifanxin
/// * @CreateDate: 2022/3/10 2:17 下午
/// * @Description: 文件说明
///
public class SmsViewModel {

    private final MutableLiveData<boolean> isPostLiveData = new MutableLiveData<boolean>();

    private final MutableLiveData<int> secondsLiveData = new MutableLiveData<int>();

    public MutableLiveData<boolean> getIsPostLiveData() {
        return isPostLiveData;
    }

    public void setIsPostLiveData(boolean result) {
        isPostLiveData.postValue(result);
    }

    public MutableLiveData<int> getSecondsLiveData {
        return secondsLiveData;
    }

    public void setSecondsLiveData(int result) {
        secondsLiveData.postValue(result);
    }


}
