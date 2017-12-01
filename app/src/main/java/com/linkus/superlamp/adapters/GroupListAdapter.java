package com.linkus.superlamp.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.linkus.superlamp.Constant;
import com.linkus.superlamp.R;
import com.linkus.superlamp.beans.GroupItemBean;

import java.util.List;

/**
 * Created by 54757 on 9/25/2017.
 */

public class GroupListAdapter extends RecyclerView.Adapter implements View.OnClickListener {
    private List<GroupItemBean> groupBeanList;
    private OnItemClickListener onItemClickListener;
    public static int TYPE_DEVICES_NORMAL = 1;
    public static int TYPE_DEVICES_LIST = 2;

    private  int TYPE = TYPE_DEVICES_NORMAL;

    public GroupListAdapter(int TYPE) {
        this.TYPE = TYPE;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_group_detail,parent, false);
        final MyViewHolder viewHolder = new MyViewHolder(inflate);
        viewHolder.itemView.setOnClickListener(this);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        GroupItemBean groupItemBean = groupBeanList.get(position);
        String text = groupBeanList == null ? "undefind" : groupItemBean.getGroupName();
        MyViewHolder viewHolder = (MyViewHolder) holder;
        viewHolder.tvGroupName.setText(text);
        if (groupBeanList == null){
            viewHolder.itemIcon.setImageResource(R.drawable.group_icon_white);//icon_error
        }else {
            String groupType = groupItemBean.getGroupType();
            if (groupType == Constant.GROUP_COLOUR){
                viewHolder.itemIcon.setImageResource(R.drawable.group_icon_colour);
            }else if (groupType  == Constant.GROUP_WHITE){
                viewHolder.itemIcon.setImageResource(R.drawable.group_icon_white);
            }else if (groupType  == Constant.GROUP_SWITCH){
                viewHolder.itemIcon.setImageResource(R.drawable.group_icon_switch);//need switch drawable
            }
        }
        holder.itemView.setTag(position);
        if (TYPE == TYPE_DEVICES_LIST){
            viewHolder.itemIconOne.setVisibility(View.GONE);
            viewHolder.itemIconTwo.setVisibility(View.GONE);
            viewHolder.cbCheck.setVisibility(View.GONE);


            if (groupItemBean.isBothConnType()){//wifi 和云都连接上
                viewHolder.itemWifi.setVisibility(View.GONE);
                viewHolder.itemSmallWifi.setVisibility(View.VISIBLE);
                viewHolder.itemSmallCloud.setVisibility(View.VISIBLE);
            }else {
                viewHolder.itemSmallWifi.setVisibility(View.GONE);
                viewHolder.itemSmallCloud.setVisibility(View.GONE);
                viewHolder.itemWifi.setVisibility(View.VISIBLE);
                if (groupItemBean.isCloud()){//云连接上
                    viewHolder.itemWifi.setImageResource(R.drawable.cloud_blue);
                } else if (groupItemBean.isWifi){// wifi连接上
                    viewHolder.itemWifi.setImageResource(R.drawable.wifi_blue);
                }else {//两个都未链接上
                    viewHolder.itemWifi.setImageResource(R.drawable.wifi_gray);
                }
            }
        }else {
            viewHolder.itemIconOne.setVisibility(View.GONE);
            viewHolder.itemIconTwo.setVisibility(View.GONE);
            viewHolder.itemWifi.setVisibility(View.GONE);
            viewHolder.cbCheck.setVisibility(View.VISIBLE);

            if (groupBeanList.get(position).isChecked()){
                viewHolder.cbCheck.setChecked(true);
            }else {
                viewHolder.cbCheck.setChecked(false);
            }
            viewHolder.cbCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked){
                        groupBeanList.get(position).setChecked(true);
                    }else {
                        groupBeanList.get(position).setChecked(false);
                    }
                }
            });
        }
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
        public CheckBox cbCheck;
        public ImageView itemIconOne;
        public ImageView itemIconTwo;
        public ImageView itemIcon;
        public ImageView itemWifi;
        public ImageView itemSmallWifi;
        public ImageView itemSmallCloud;
        public MyViewHolder(final View itemView) {
            super(itemView);
            tvGroupName = (TextView) itemView.findViewById(R.id.tv_group_name);
            cbCheck = (CheckBox) itemView.findViewById(R.id.cb_check);
            itemIcon = (ImageView) itemView.findViewById(R.id.item_icon);
            itemIconOne = (ImageView) itemView.findViewById(R.id.icon_one);
            itemIconTwo = (ImageView) itemView.findViewById(R.id.icon_two);
            itemWifi = (ImageView) itemView.findViewById(R.id.icon_wifi);
            itemSmallWifi = (ImageView) itemView.findViewById(R.id.icon_small_wifi);
            itemSmallCloud = (ImageView) itemView.findViewById(R.id.icon_small_cloud);
        }
    }

    public void addData(List<GroupItemBean> groupBeanList) {
        if (groupBeanList == null) {
            return;
        }
        this.groupBeanList = groupBeanList;
        notifyDataSetChanged();
    }

    public void addData(List<GroupItemBean> groupBeanList,boolean clear) {
        if (clear){
            if (this.groupBeanList != null){
                this.groupBeanList.clear();
            }
        }
        this.groupBeanList = groupBeanList;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.onItemClickListener = listener;
    }

    public interface OnItemClickListener {
        void click(View itemView, int position, GroupItemBean data);
    }

    public List<GroupItemBean>  getBindingList(){
        return this.groupBeanList;
    }
}
