package com.linkus.superlamp.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.linkus.superlamp.Constant;
import com.linkus.superlamp.R;
import com.linkus.superlamp.beans.SceneBean;
import com.linkus.superlamp.logger.Logger;
import com.linkus.superlamp.utils.ScreenUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 54757 on 9/25/2017.
 */

public class ScenesAdapter extends RecyclerView.Adapter implements View.OnClickListener {
    private String TAG= getClass().getSimpleName();
    private List<SceneBean> sceneBeanList;
    private OnItemClickListener onItemClickListener;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_scene,parent, false);
        final MyViewHolder viewHolder = new MyViewHolder(inflate);
        viewHolder.itemView.setOnClickListener(this);
        Context context = viewHolder.itemView.getContext();
        int screenWidth = ScreenUtil.getScreenWidth(context);
        int itemWith = (screenWidth - 2 * ScreenUtil.dip2px(context, 3)) / 2 ;
        viewHolder.itemView.setLayoutParams(new RecyclerView.LayoutParams(itemWith,itemWith));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        SceneBean sceneBean = sceneBeanList.get(position);
        String text = sceneBeanList == null ? "undefind" : sceneBean.getSceneName();
        ((MyViewHolder) holder).tvSceneName.setText(text);
        ImageView ivSceneBg = ((MyViewHolder) holder).ivSceneBg;
        ImageView ivIcon = ((MyViewHolder) holder).ivIcon;
        Context context = ivSceneBg.getContext();
//        Glide.with(context).load("http://www.qiwen007.com/images/image/2017/0707/6363504853258637901455639.jpg")
//                .placeholder(new ColorDrawable(Color.parseColor("#50ffffff")))
//                .into(ivSceneBg);
        Glide.with(context).load(sceneBean.getSceneBg()).placeholder(new ColorDrawable(Color.parseColor("#50ffffff")))
                .into(ivSceneBg);
        Glide.with(context).load(sceneBean.getSceneIcon()).placeholder(new ColorDrawable(Color.parseColor("#50ffffff")))
            .into(ivIcon);
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return sceneBeanList == null ? 0 : sceneBeanList.size();
    }

    @Override
    public void onClick(View view) {
        int pos = (int) view.getTag();
        if (onItemClickListener != null){
            onItemClickListener.click(view, pos, sceneBeanList.get(pos));
        }
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView ivSceneBg;
        public TextView tvSceneName;
        public ImageView ivIcon;

        public MyViewHolder(final View itemView) {
            super(itemView);
            ivIcon = (ImageView) itemView.findViewById(R.id.iv_icon);
            ivSceneBg = (ImageView) itemView.findViewById(R.id.iv_scene_bg);
            tvSceneName = (TextView) itemView.findViewById(R.id.tv_scenes_name);
        }
    }

    public void addData(List<SceneBean> sceneBeanList) {
        addData(sceneBeanList, false);
    }

    public void addData(List<SceneBean> sceneBeanList, boolean clear) {
        if (sceneBeanList == null) {
            return;
        }
        if (this.sceneBeanList == null) {
            this.sceneBeanList = new ArrayList<>();
        }
        if (clear) {
            this.sceneBeanList.clear();
        }
        this.sceneBeanList.addAll(sceneBeanList);
        notifyDataSetChanged();
    }

    public void addDataItem(SceneBean sceneBean) {
        if (sceneBeanList == null) {
            sceneBeanList = new ArrayList<>();
        }
        sceneBeanList.add(sceneBean);
        notifyDataSetChanged();
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        this.onItemClickListener = listener;
    }

    public interface OnItemClickListener {
        void click(View itemView, int position, SceneBean data);
    }
}
