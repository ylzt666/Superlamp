package com.linkus.superlamp.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.linkus.superlamp.Constant;
import com.linkus.superlamp.R;
import com.linkus.superlamp.beans.GroupBean;
import com.linkus.superlamp.logger.Logger;
import com.linkus.superlamp.utils.ScreenUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 54757 on 9/25/2017.
 */

public class GroupAdapter extends RecyclerView.Adapter implements View.OnClickListener {
    private List<GroupBean> groupBeanList;
    private OnItemClickListener onItemClickListener;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_group,parent, false);
        final MyViewHolder viewHolder = new MyViewHolder(inflate);
        viewHolder.itemView.setOnClickListener(this);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        String text = groupBeanList == null ? "undefind" : groupBeanList.get(position).getGroupName();
        ((MyViewHolder) holder).tvGroupName.setText(text);
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return groupBeanList == null ? 0 : groupBeanList.size();
    }

    @Override
    public void onClick(View view) {
        int pos = (int) view.getTag();
        if (onItemClickListener != null){
            onItemClickListener.click(view, pos, groupBeanList.get(pos));
        }
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvGroupName;
        public ImageView ivGroupIcon;
        public MyViewHolder(final View itemView) {
            super(itemView);
            tvGroupName = (TextView) itemView.findViewById(R.id.tv_group_name);
            ivGroupIcon = (ImageView) itemView.findViewById(R.id.group_icon);
        }
    }

    public void addData(List<GroupBean> groupBeanList) {
        addData(groupBeanList, false);
    }

    public void addData(List<GroupBean> groupBeanList, boolean clear) {
        if (groupBeanList == null) {
            return;
        }
        if (this.groupBeanList == null) {
            this.groupBeanList = new ArrayList<>();
        }
        if (clear) {
            this.groupBeanList.clear();
        }
        this.groupBeanList.addAll(groupBeanList);
        notifyDataSetChanged();
    }

    public void addDataItem(GroupBean groupBean) {
        if (groupBeanList == null) {
            groupBeanList = new ArrayList<>();
        }
        groupBeanList.add(groupBean);
        notifyDataSetChanged();
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        this.onItemClickListener = listener;
    }

    public interface OnItemClickListener {
        void click(View itemView, int position, GroupBean data);
    }
}
