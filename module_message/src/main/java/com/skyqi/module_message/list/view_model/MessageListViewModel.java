package com.skyqi.module_message.list.view_model;///

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.skyqi.module_base.http.HttpManager;
import com.skyqi.module_base.http.service.MessageService;
import com.skyqi.module_base.model.ApiResponse;
import com.skyqi.module_base.model.MessageChannelListModel;

import java.util.List;

/// * @ProjectName: paperprint
/// * @Author: qifanxin
/// * @CreateDate: 2022/3/25 3:34 下午
/// * @Description: 文件说明
///
public class MessageListViewModel extends ViewModel {

    private MessageService mMessageService;

    public int page = 0;

    private final int pageLimit = 20;

    public MessageListViewModel() {
        mMessageService = HttpManager.getInstance().createService(MessageService.class);;
    }

    public LiveData<ApiResponse<List<MessageChannelListModel>>> getMessageChannelAction() {
       return mMessageService.getMessageList(page, pageLimit);
    }

}
