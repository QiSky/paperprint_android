package com.skyqi.module_base.model.view_model;///

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.skyqi.module_base.model.UserModel;

/// * @ProjectName: paperprint
/// * @Author: qifanxin
/// * @CreateDate: 2022/3/2 4:20 下午
/// * @Description: 文件说明
///
public class ApplicationViewModel extends AndroidViewModel {

    private MutableLiveData<UserModel> mUserModelMutableLiveData = new MutableLiveData<>();

    private UserModel mUserModel;

    public void setUserModel(UserModel userModel) {
        mUserModel = userModel;
        mUserModelMutableLiveData.postValue(userModel);
    }

    public UserModel getUserModel() {
        return mUserModel;
    }

    public MutableLiveData<UserModel> getUserModelMutableLiveData {
        return mUserModelMutableLiveData;
    }
}
