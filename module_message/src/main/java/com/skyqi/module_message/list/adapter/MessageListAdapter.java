package com.skyqi.module_message.list.adapter;///

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.skyqi.module_base.data.DataConstant;
import com.skyqi.module_base.listener.SingleClickListener;
import com.skyqi.module_base.model.MessageChannelListModel;
import com.skyqi.module_base.route.RouteBase;
import com.skyqi.module_base.route.RouteManager;
import com.skyqi.module_base.utils.DataUtil;
import com.skyqi.module_message.R;
import com.skyqi.module_message.databinding.ItemAdapterMessageListBinding;

import java.util.ArrayList;
import java.util.List;

/// * @ProjectName: paperprint
/// * @Author: qifanxin
/// * @CreateDate: 2022/3/25 1:34 下午
/// * @Description: 文件说明
///
public class MessageListAdapter extends RecyclerView.Adapter<MessageListAdapter.MessageListViewHolder> {

    private List<MessageChannelListModel> data;

    public MessageListAdapter(List<MessageChannelListModel> data) {
        if (data == null) {
            this.data = new ArrayList<>();
        } else {
            this.data = data;
        }
    }

    public void setData(List<MessageChannelListModel> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public void setData() {
        notifyDataSetChanged();
    }

    public void reset() {
        data.clear();
        notifyDataSetChanged();
    }

    public List<MessageChannelListModel> getData() {
        return data;
    }

    @NonNull
    @Override
    public MessageListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemAdapterMessageListBinding binding = ItemAdapterMessageListBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MessageListViewHolder(binding.getRoot(), binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageListViewHolder holder, int position) {
        holder.mBinding.tvItemAdapterMessageListTitle.setText(data.get(position).getNickName());
        holder.mBinding.tvItemAdapterMessageListContent.setText(data.get(position).getContent());
        holder.mBinding.tvItemAdapterMessageListTime.setText(DataUtil.handleDate(data.get(position).getTime()));
        if(data.get(position).isRead()) {
            holder.mBinding.muvItemAdapterMessageListTag.setVisibility(View.INVISIBLE);
        } else {
            holder.mBinding.muvItemAdapterMessageListTag.setVisibility(View.VISIBLE);
        }
        holder.itemView.setOnClickListener(new SingleClickListener() {
            @Override
            protected void onSingleClick(View v) {
                ///点击跳转
                Bundle bundle = new Bundle();
                bundle.putLong(DataConstant.MESSAGE_CHANNEL_ID, data.get(position).getId());
                RouteManager.getInstance().navigateTo(RouteBase.MESSAGE_DETAIL, bundle);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class MessageListViewHolder extends RecyclerView.ViewHolder {

        public ItemAdapterMessageListBinding mBinding;

        public MessageListViewHolder(@NonNull View itemView, ItemAdapterMessageListBinding binding) {
            super(itemView);
            mBinding = binding;
        }

    }
}
