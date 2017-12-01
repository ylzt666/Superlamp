package com.linkus.superlamp.fragments;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import com.linkus.superlamp.BaseFragment;
import com.linkus.superlamp.Constant;
import com.linkus.superlamp.R;
import com.linkus.superlamp.adapters.GroupAdapter;
import com.linkus.superlamp.beans.GroupBean;
import com.linkus.superlamp.beans.GroupItemBean;
import com.linkus.superlamp.logger.Logger;
import com.linkus.superlamp.view.GroupListActivity;
import com.linkus.superlamp.view.LampBulbActivity;
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

public class FragmentGroups extends BaseFragment {
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.no_groups)
    LinearLayout noGroups;

    @BindView(R.id.srl_layout)
    SmartRefreshLayout srlLayout;

    private GroupAdapter mGroupAdapter;
    private boolean isFirst = true;


    @Override
    protected void beforeShow() {

    }

    @Override
    public View inflateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_groups,null);
    }

    @Override
    public void findAllViews(View rootView) {
        noGroups.setVisibility(View.GONE);
    }

    @Override
    public void setAllListeners() {
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        rvList.setLayoutManager(manager);
        mGroupAdapter = new GroupAdapter();
        rvList.setItemAnimator(new DefaultItemAnimator());
        RecyclerViewDivider divder = new RecyclerViewDivider(true);
        divder.setColor(Color.parseColor("#efeff4")); // 分割线颜色
//        divder.setMargin(getActivity(), 0, 15, 0, 15);  // 分割线边距dp
        divder.setDividerHeight(getActivity(), 3);// 分割线宽度dp
        rvList.addItemDecoration(divder);
        rvList.setAdapter(mGroupAdapter);
        List<GroupBean> groupList = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            if (i== 1){
                GroupBean groupBean = new GroupBean("lamp item" + i, "", "" + Constant.GROUP_COLOUR);
                groupBean.isAlarmOpen =  false;
                groupBean.isDeviceOpen=  false;
                groupList.add(groupBean);
            }
            if (i== 2){
                GroupBean groupBean = new GroupBean("lamp item" + i, "", "" + Constant.GROUP_COLOUR);
                groupBean.isAlarmOpen =  true;
                groupBean.isDeviceOpen=  false;
                groupList.add(groupBean);
            }
            if (i== 3){
                GroupBean groupBean = new GroupBean("lamp item" + i, "", "" + Constant.GROUP_COLOUR);
                groupBean.isAlarmOpen =  false;
                groupBean.isDeviceOpen=  true;
                groupList.add(groupBean);
            }
            if (i == 4){
                GroupBean e = new GroupBean("lamp item" + i, "", "" + Constant.GROUP_WHITE);
                e.isAlarmOpen =  true;
                e.isDeviceOpen=  false;
                groupList.add(e);
            }
            if (i == 5){
                GroupBean e = new GroupBean("lamp item" + i, "", "" + Constant.GROUP_WHITE);
                e.isAlarmOpen =  false;
                e.isDeviceOpen=  true;
                groupList.add(e);
            }
            if (i>6){
                groupList.add(new GroupBean("lamp item" + i, "", ""+ Constant.GROUP_SWITCH));
            }
        }
        mGroupAdapter.addData(groupList);
        mGroupAdapter.setOnItemClickListener(new GroupAdapter.OnItemClickListener() {
            @Override
            public void click(View itemView, int position, GroupBean data) {
                if (srlLayout.isRefreshing()|| srlLayout.isLoading()){
                    return;
                }
                Logger.i(TAG, "itemView : " + itemView + "position : " + position + "data : " + data.toString());
                Intent intent = new Intent(getContext(), LampBulbActivity.class);
                intent.putExtra("data", data);
                getContext().startActivity(intent);
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
                },3000);
            }
        });
    }

    @Override
    public void doProgress() {

    }

    public void addGroup() {
        final Dialog dialog = new Dialog(getContext(),R.style.Dialog_FULL_SCREEN);
        dialog.setContentView(R.layout.dialog_add_group_name);
        dialog.show();
        final View llEditGroupName = dialog.findViewById(R.id.ll_edit_group_name);
        final View llGroupDevicesType = dialog.findViewById(R.id.ll_group_devices_type);
        llEditGroupName.setVisibility(View.VISIBLE);
        View btnClose = dialog.findViewById(R.id.btn_close);
        View btnClose2 = dialog.findViewById(R.id.btn_close2);
        View btnNextStep = dialog.findViewById(R.id.btn_next_step);
        View btnOk = dialog.findViewById(R.id.btn_ok);
        final EditText etGroupName = (EditText) dialog.findViewById(R.id.et_group_name);
        final RadioButton cbOne = (RadioButton) dialog.findViewById(R.id.cb_one);
        RadioButton cbTwo = (RadioButton) dialog.findViewById(R.id.cb_two);
        RadioButton cbThree = (RadioButton) dialog.findViewById(R.id.cb_three);
        final ArrayList<RadioButton> cbs = new ArrayList<>();
        cbs.add(cbOne);
        cbs.add(cbTwo);
        cbs.add(cbThree);
        CompoundButton.OnCheckedChangeListener listener1 = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                if (checked) {
                    for (RadioButton cb :
                            cbs) {
                        if (cb != compoundButton && cb.isChecked()) {
                            cb.setChecked(false);
                        }
                    }
                }
            }
        };

        for (RadioButton c : cbs) {
            c.setOnCheckedChangeListener(listener1);
        }


        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               switch (view.getId()){
                   case R.id.btn_close://closeDialog
                       dialog.dismiss();
                       break;
                   case R.id.btn_close2://closeDialog
                       dialog.dismiss();
                       break;
                   case R.id.btn_next_step:
                       String groupName = etGroupName.getText().toString();
                       if (TextUtils.isEmpty(groupName)) {
                           showToast("group name is empty");
                           return;
                       }
                       llGroupDevicesType.setVisibility(View.VISIBLE);
                       llEditGroupName.setVisibility(View.GONE);
                       break;
                   case R.id.btn_ok:
                       String groupType = "";
                       for (RadioButton cb: cbs) {
                           if (cb.isChecked()){
                               groupType = cb.getTag().toString();
                           }
                       }
                       if (TextUtils.isEmpty(groupType)){
                           showToast("please check a type ");
                           return;
                       }
                       addAGroup(etGroupName.getText().toString(), groupType);
                       dialog.dismiss();
                       break;
               }
            }
        };
        btnClose.setOnClickListener(listener);
        btnNextStep.setOnClickListener(listener);
        btnClose2.setOnClickListener(listener);
        btnOk.setOnClickListener(listener);
    }

    /**
     *
     * @param groupName 分组名称
     * @param groupType 分组类型
     */
    private void addAGroup(String groupName, String groupType) {
        Logger.i(TAG, "groupName is " + groupName + " sceneType is " + groupType);
        switch (groupType) {
            case "彩灯":
                groupType = Constant.GROUP_COLOUR;
                break;
            case "白灯":
                groupType = Constant.GROUP_WHITE;
                break;
            case "开关":
                groupType = Constant.GROUP_SWITCH;
                break;
        }
        mGroupAdapter.addDataItem(new GroupBean(groupName,"",groupType));
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
