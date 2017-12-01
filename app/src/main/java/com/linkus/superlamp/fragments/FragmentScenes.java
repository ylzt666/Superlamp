package com.linkus.superlamp.fragments;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.linkus.superlamp.BaseFragment;
import com.linkus.superlamp.R;
import com.linkus.superlamp.adapters.GroupAdapter;
import com.linkus.superlamp.adapters.ScenesAdapter;
import com.linkus.superlamp.beans.GroupBean;
import com.linkus.superlamp.beans.SceneBean;
import com.linkus.superlamp.logger.Logger;
import com.linkus.superlamp.view.GroupListActivity;
import com.linkus.superlamp.widget.RecyclerViewDivider;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/9/25 0025.
 */

public class FragmentScenes extends BaseFragment {
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.no_sences)
    LinearLayout no_sences;
    private ScenesAdapter scenesAdapter;

    @BindView(R.id.srl_layout)
    SmartRefreshLayout srlLayout;
    private boolean isFirst = true;

    @Override
    protected void beforeShow() {

    }

    @Override
    public View inflateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
          return inflater.inflate(R.layout.fragment_sences,null);
    }

    @Override
    public void findAllViews(View rootView) {

    }

    @Override
    public void setAllListeners() {
        srlLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        srlLayout.finishRefresh();
                    }
                },3000);
            }
        });
    }

    @Override
    public void doProgress() {


        GridLayoutManager manager = new GridLayoutManager(getContext(),2, LinearLayoutManager.VERTICAL,false);
        rvList.setLayoutManager(manager);
        scenesAdapter = new ScenesAdapter();
        rvList.setItemAnimator(new DefaultItemAnimator());

        RecyclerViewDivider divder = new RecyclerViewDivider(true);
        divder.setColor(Color.parseColor("#efeff4")); // 分割线颜色
        divder.setDividerHeight(getActivity(), 3);// 分割线宽度dp
        rvList.addItemDecoration(divder);

        RecyclerViewDivider divderHori = new RecyclerViewDivider(false);
        divderHori.setDividerHeight(getActivity(), 3);// 分割线宽度dp
        divderHori.setColor(Color.parseColor("#efeff4")); // 分割线颜色
        rvList.addItemDecoration(divderHori);


        rvList.setAdapter(scenesAdapter);
        List<SceneBean> groupList = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            if (i == 0) {
                groupList.add(new SceneBean("清晨", "" + i, "" + i, R.drawable.icon_moring, R.drawable.sence_morning_bg));
            } else if (i == 1) {
                groupList.add(new SceneBean("夕阳", "" + i, "" + i, R.drawable.icon_sunset, R.drawable.sence_sunset_bg));
            } else if (i == 2) {
                groupList.add(new SceneBean("夜色", "" + i, "" + i, R.drawable.icon_night, R.drawable.sence_night_bg));
            } else if (i == 3) {
                groupList.add(new SceneBean("天空", "" + i, "" + i, R.drawable.icon_sky, R.drawable.sence_sky_bg));
            } else if (i == 4) {
                groupList.add(new SceneBean("浪漫", "" + i, "" + i, R.drawable.icon_romance, R.drawable.sence_roman_bg));
            } else if (i == 5) {
                groupList.add(new SceneBean("电影", "" + i, "" + i, R.drawable.icon_film, R.drawable.sence_film_bg));
            }
        }

        scenesAdapter.addData(groupList);
        scenesAdapter.setOnItemClickListener(new ScenesAdapter.OnItemClickListener() {
            @Override
            public void click(View itemView, int position, SceneBean data) {
                if (srlLayout.isRefreshing()|| srlLayout.isLoading()){
                    return;
                }
                Logger.i(TAG, "itemView : " + itemView + "position : " + position + "data : " + data.toString());
//                showToast(data.toString());
//                Intent intent = new Intent(getContext(), GroupListActivity.class);
//                intent.putExtra("data", data);
//                getContext().startActivity(intent);

                Intent intent = new Intent();
                intent.putExtra("isControlScene","true");
                intent.putExtra("groupScenes",true);
                intent.setClass(getContext(),GroupListActivity.class);
                startActivity(intent);
            }
        });
    }

    public void addScene() {
        final Dialog addDialog = new Dialog(getContext(),R.style.Dialog_FULL_SCREEN);
        addDialog.setContentView(R.layout.dialog_add_sence);
        addDialog.show();
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (isFirst){
            if (srlLayout != null){
                srlLayout.autoRefresh(1500);
            }
            isFirst = false;
        }
    }
}
