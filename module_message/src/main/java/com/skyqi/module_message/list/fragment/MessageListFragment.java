package com.skyqi.module_message.list.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.skyqi.module_base.http.HttpCode;
import com.skyqi.module_base.model.ApiResponse;
import com.skyqi.module_base.model.MessageChannelListModel;
import com.skyqi.module_base.view.fragment.BaseViewModelFragment;
import com.skyqi.module_message.R;
import com.skyqi.module_message.databinding.FragmentMessageListBinding;
import com.skyqi.module_message.list.adapter.MessageListAdapter;
import com.skyqi.module_message.list.view_model.MessageListViewModel;

import java.util.ArrayList;
import java.util.List;

public class MessageListFragment extends BaseViewModelFragment {

    private FragmentMessageListBinding mFragmentMessageListBinding;

    private MessageListViewModel mMessageListViewModel;

    private MessageListAdapter mMessageListAdapter;

    public MessageListFragment() {
        // Required empty public constructor
    }

    public static MessageListFragment newInstance() {
        MessageListFragment fragment = new MessageListFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mFragmentMessageListBinding = FragmentMessageListBinding.inflate(inflater);
        initViewModel();
        mMessageListAdapter = new MessageListAdapter(null);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mFragmentMessageListBinding.rvMessageListContainer.setLayoutManager(linearLayoutManager);
        mFragmentMessageListBinding.rvMessageListContainer.setAdapter(mMessageListAdapter);
        getData(false, false);
        mFragmentMessageListBinding.srlMessageListLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mMessageListViewModel.page = 0;
                mMessageListAdapter.reset();
                getData(true, false);
            }
        });
        mFragmentMessageListBinding.srlMessageListLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mMessageListViewModel.page++;
                getData(false, true);
            }
        });
        return mFragmentMessageListBinding.getRoot();
    }

    private void getData(boolean isRefreshing, boolean isLoading) {
        mMessageListViewModel.getMessageChannelAction().observe(getActivity(), new Observer<ApiResponse<List<MessageChannelListModel>>>() {
            @Override
            public void onChanged(ApiResponse<List<MessageChannelListModel>> listApiResponse) {
                if (listApiResponse != null && HttpCode.SUCCESS == listApiResponse.getCode()) {
                    if (listApiResponse.getData() != null) {
                        mMessageListAdapter.getData().addAll(listApiResponse.getData());
                        mMessageListAdapter.setData();
                        if (listApiResponse.getData().isEmpty()) {
                            if (isLoading) {
                                mFragmentMessageListBinding.srlMessageListLayout.finishLoadMoreWithNoMoreData();
                            }
                        }
                        if (isRefreshing) {
                            mFragmentMessageListBinding.srlMessageListLayout.finishRefresh();
                        }
                    } else {
                        if (isRefreshing) {
                            mFragmentMessageListBinding.srlMessageListLayout.finishRefresh();
                        }
                        if (isLoading) {
                            mFragmentMessageListBinding.srlMessageListLayout.finishLoadMoreWithNoMoreData();
                        }
                    }
                } else {
                    MessageChannelListModel model = new MessageChannelListModel();
                    model.setContent("123");
                    model.setNickName("123");
                    model.setRead(true);
                    model.setTime(1111L);
                    mMessageListAdapter.getData().add(model);
                    mMessageListAdapter.setData();
                }
            }
        });
    }

    @Override
    protected void initViewModel() {
        mMessageListViewModel = new ViewModelProvider(getActivity()).get(MessageListViewModel.class);
    }
}