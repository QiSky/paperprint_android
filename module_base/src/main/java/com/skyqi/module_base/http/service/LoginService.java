package com.skyqi.module_base.http.service;///

import androidx.lifecycle.LiveData;

import com.skyqi.module_base.http.ApiManager;
import com.skyqi.module_base.model.ApiResponse;
import com.skyqi.module_base.model.UserModel;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/// * @ProjectName: paperprint
/// * @Author: qifanxin
/// * @CreateDate: 2022/3/1 3:42 下午
/// * @Description: 文件说明
///
public interface LoginService extends ApiManager {

    @FormUrlEncoded
    @POST(REST_LOGIN_CODE)
    LiveData<ApiResponse<UserModel>> login(@Field("phone") String phone, @Field("code") String code, @Field("type") int type);

    @FormUrlEncoded
    @POST(REST_LOGIN_TOKEN)
    LiveData<ApiResponse<UserModel>> loginToken();

    @FormUrlEncoded
    @POST(REST_LOGIN_SMS)
    LiveData<ApiResponse<Object>> sendSMS(@Field("phone") String phone);

}
