package com.skyqi.module_base.model;///

import android.os.Parcel;
import android.os.Parcelable;

import com.hannesdorfmann.parcelableplease.annotation.Bagger;
import com.hannesdorfmann.parcelableplease.annotation.ParcelablePlease;
import com.skyqi.module_base.bagger.IntegerBagger;

import java.util.List;

/// * @ProjectName: paperprint
/// * @Author: qifanxin
/// * @CreateDate: 2022/3/1 3:57 下午
/// * @Description: 文件说明
///

@ParcelablePlease
public class UserModel implements Parcelable {

    public String token;

    public String phone;

    public int[] permission;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int[] getPermission() {
        return permission;
    }

    public void setPermission(int[] permission) {
        this.permission = permission;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        UserModelParcelablePlease.writeToParcel(this, dest, flags);
    }

    public static final Creator<UserModel> CREATOR = new Creator<UserModel>() {
        public UserModel createFromParcel(Parcel source) {
            UserModel target = new UserModel();
            UserModelParcelablePlease.readFromParcel(target, source);
            return target;
        }

        public UserModel[] newArray(int size) {
            return new UserModel[size];
        }
    };
}
