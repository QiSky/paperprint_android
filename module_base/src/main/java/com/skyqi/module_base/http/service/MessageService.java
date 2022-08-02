package com.skyqi.module_base.http.service;///

import androidx.lifecycle.LiveData;

import com.skyqi.module_base.http.ApiManager;
import com.skyqi.module_base.model.ApiResponse;
import com.skyqi.module_base.model.MessageChannelListModel;

import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/// * @ProjectName: paperprint
/// * @Author: qifanxin
/// * @CreateDate: 2022/3/25 2:10 下午
/// * @Description: 文件说明
///
public interface MessageService extends ApiManager {

    @FormUrlEncoded
    @POST(REST_MESSAGE_CHANNEL)
    LiveData<ApiResponse<List<MessageChannelListModel>>> getMessageList(@Field("page") int page, @Field("limit") int limit);
}
