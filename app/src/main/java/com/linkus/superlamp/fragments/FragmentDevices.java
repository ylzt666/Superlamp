package com.linkus.superlamp.fragments;

import android.content.Intent;
import android.content.IntentSender;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.linkus.superlamp.BaseFragment;
import com.linkus.superlamp.Constant;
import com.linkus.superlamp.R;
import com.linkus.superlamp.adapters.GroupListAdapter;
import com.linkus.superlamp.beans.GroupItemBean;
import com.linkus.superlamp.logger.Logger;
import com.linkus.superlamp.utils.Utils;
import com.linkus.superlamp.view.AddDeviceActivity;
import com.linkus.superlamp.view.GroupListActivity;
import com.linkus.superlamp.view.LampBulbActivity;
import com.linkus.superlamp.view.MainActivity;
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

public class FragmentDevices extends BaseFragment {
    @BindView(R.id.tv_link)
    TextView tvLink;

    @BindView(R.id.rv_list)
    RecyclerView rvList;

    @BindView(R.id.srl_layout)
    SmartRefreshLayout srlLayout;
    private boolean isFirst = true;

    @Override
    protected void beforeShow() {

    }

    @Override
    public View inflateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_devcies,null);
    }

    @Override
    public void findAllViews(View rootView) {
        Utils.stripUnderlines(tvLink);
    }

    @Override
    public void setAllListeners() {
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        rvList.setLayoutManager(manager);
        GroupListAdapter groupAdapter = new GroupListAdapter(GroupListAdapter.TYPE_DEVICES_LIST);
        rvList.setItemAnimator(new DefaultItemAnimator());
        RecyclerViewDivider divder = new RecyclerViewDivider(true);
        divder.setColor(Color.parseColor("#efeff4")); // 分割线颜色
//        divder.setMargin(this, 0, 15, 0, 15);  // 分割线边距dp
        divder.setDividerHeight(getContext(), 3);// 分割线宽度dp
        rvList.addItemDecoration(divder);
        rvList.setAdapter(groupAdapter);
        List<GroupItemBean> groupList = new ArrayList<>();
        for (int i = 0; i < 7; i++) {//for test
            if (i== 0){
                GroupItemBean e = new GroupItemBean("lamp item" + i, "", "" + Constant.GROUP_WHITE);
                e.setAlarmOpen(true);
                e.setDeviceOpen(true);
                e.setCloud(true);
                e.setWifi(true);
                groupList.add(e);
            }
            if (i== 1){
                GroupItemBean e = new GroupItemBean("lamp item" + i, "", "" + Constant.GROUP_COLOUR);
                e.setAlarmOpen(true);
                e.setDeviceOpen(true);
                e.setCloud(true);
                e.setWifi(true);
                groupList.add(e);
            }
            if (i == 2){
                GroupItemBean e = new GroupItemBean("lamp item" + i, "", "" + Constant.GROUP_WHITE);
                e.setCloud(false);
                e.setWifi(true);
                e.setAlarmOpen(false);
                e.setDeviceOpen(true);
                groupList.add(e);
            }
            if (i == 3){
                GroupItemBean e = new GroupItemBean("lamp item" + i, "", "" + Constant.GROUP_SWITCH);
                e.setAlarmOpen(true);
                e.setDeviceOpen(true);
                e.setCloud(true);
                e.setWifi(false);
                groupList.add(e);
            }
            if (i == 4){
                GroupItemBean itemBean = new GroupItemBean("lamp item" + i, "", "" + Constant.GROUP_SWITCH);
                itemBean.setCloud(true);
                itemBean.setWifi(true);
                itemBean.setAlarmOpen(true);
                itemBean.setDeviceOpen(false);
                groupList.add(itemBean);
            }
            if (i == 5){
                GroupItemBean itemBean = new GroupItemBean("lamp item" + i, "", "" + Constant.GROUP_SWITCH);
                itemBean.setCloud(false);
                itemBean.setWifi(true);
                itemBean.setAlarmOpen(true);
                itemBean.setDeviceOpen(true);
                groupList.add(itemBean);
            }
            if (i == 6){
                GroupItemBean itemBean = new GroupItemBean("lamp item" + i, "", "" + Constant.GROUP_SWITCH);
                itemBean.setCloud(false);
                itemBean.setWifi(false);
                itemBean.setAlarmOpen(true);
                itemBean.setDeviceOpen(false);
                groupList.add(itemBean);
            }
        }
        groupAdapter.addData(groupList);


        groupAdapter.setOnItemClickListener(new GroupListAdapter.OnItemClickListener() {
            @Override
            public void click(View itemView, int position, GroupItemBean data) {
                if (srlLayout.isRefreshing()|| srlLayout.isLoading()){
                    return;
                }
                Logger.i(TAG, "itemView : " + itemView + "position : " + position + "data : " + data.toString());
                Intent intent = new Intent(getContext(),LampBulbActivity.class);
                intent.putExtra("data",data);
                startActivity(intent);
//                showToast(data.toString());
            }
        });

        srlLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        srlLayout.finishRefresh();
                    }
                },1500);
            }
        });

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

    @Override
    public void doProgress() {
        rvList.setVisibility(View.VISIBLE);
    }

    public void addDevice() {
        showToast("add clicked");
        Intent intent = new Intent(getContext(), AddDeviceActivity.class);
        startActivityForResult(intent, MainActivity.ADD_DEVICES);
    }


}
