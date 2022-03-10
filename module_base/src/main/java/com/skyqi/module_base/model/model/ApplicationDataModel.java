package com.skyqi.module_base.model.model;///

import com.skyqi.module_base.model.UserModel;

/// * @ProjectName: paperprint
/// * @Author: qifanxin
/// * @CreateDate: 2022/3/9 2:08 下午
/// * @Description: 文件说明
///
public class ApplicationDataModel {

    private UserModel mUserModel;

    public UserModel getUserModel() {
        return mUserModel;
    }

    public void setUserModel(UserModel userModel) {
        mUserModel = userModel;
    }
}
