package com.linkus.superlamp.view;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.linkus.superlamp.BaseActivity;
import com.linkus.superlamp.Constant;
import com.linkus.superlamp.R;
import com.linkus.superlamp.adapters.GroupListAdapter;
import com.linkus.superlamp.beans.GroupItemBean;
import com.linkus.superlamp.logger.Logger;
import com.linkus.superlamp.widget.RecyclerViewDivider;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by 54757 on 9/25/2017.
 */

public class GroupListActivity extends BaseActivity {


    public static final String WHITE = "@white";
    public static final String SWITCH = "switch";
    @BindView(R.id.tv_page_name)
    TextView pageName;
    @BindView(R.id.back_left)
    ImageView backLeft;
    @BindView(R.id.rv_list)
    RecyclerView rvList;

    @BindView(R.id.iv_top_right)
    ImageView topRight;

    @BindView(R.id.btn_apply)
    Button btnApply;

    public static final String COLOR = "@color";
    private String mShowType = Constant.GROUP_NONE;
    private GroupListAdapter groupAdapter;
    private Boolean isControlScene;

    @Override
    protected void beforeShow(Bundle savedInstanceState) {

    }

    @Override
    public void bindView() {
        setContentView(R.layout.activity_group_list);
    }

    @Override
    public void findAllViews() {
        backLeft.setVisibility(View.VISIBLE);
        pageName.setVisibility(View.VISIBLE);

        Intent intent = getIntent();

        String isControlScene = intent.getStringExtra("isControlScene");
        this.isControlScene = Boolean.valueOf(isControlScene);
        if (!TextUtils.isEmpty(isControlScene) && this.isControlScene){
            pageName.setText("设备选择");
            btnApply.setVisibility(View.VISIBLE);
//            topRight.setImageResource(R.drawable.nav_addition);
//            topRight.setImageResource(R.drawable.nav_addition);
//            topRight.setScaleType(ImageView.ScaleType.FIT_XY);
//            topRight.setPadding(0,0,0,0);

        }else {
            Serializable data1 = intent.getSerializableExtra("data");
            pageName.setText("undefined");
        }

    }



    @Override
    public void setAllListeners() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        rvList.setLayoutManager(manager);
        groupAdapter = new GroupListAdapter(GroupListAdapter.TYPE_DEVICES_NORMAL);
        rvList.setItemAnimator(new DefaultItemAnimator());
        RecyclerViewDivider divder = new RecyclerViewDivider(true);
        divder.setColor(Color.parseColor("#efeff4")); // 分割线颜色
//        divder.setMargin(this, 0, 15, 0, 15);  // 分割线边距dp
        divder.setDividerHeight(this, 3);// 分割线宽度dp
        rvList.addItemDecoration(divder);
        rvList.setAdapter(groupAdapter);
        final List<GroupItemBean> groupList = new ArrayList<>();
        //test
        groupList.add(new GroupItemBean("lamp item" + 0, "", ""+ Constant.GROUP_COLOUR));
        groupList.add(new GroupItemBean("lamp item" + 1, "", ""+ Constant.GROUP_COLOUR));
        groupList.add(new GroupItemBean("lamp item" + 2, "", ""+ Constant.GROUP_COLOUR));
        groupList.add(new GroupItemBean("lamp item" + 3, "", ""+ Constant.GROUP_WHITE));
        groupList.add(new GroupItemBean("lamp item" + 3, "", ""+ Constant.GROUP_WHITE));
        groupList.add(new GroupItemBean("lamp item" + 3, "", ""+ Constant.GROUP_WHITE));
        groupList.add(new GroupItemBean("lamp item" + 3, "", ""+ Constant.GROUP_WHITE));
        groupList.add(new GroupItemBean("lamp item" + 3, "", ""+ Constant.GROUP_WHITE));
        groupList.add(new GroupItemBean("lamp item" + 3, "", ""+ Constant.GROUP_WHITE));
        groupList.add(new GroupItemBean("lamp item" + 4, "", ""+ Constant.GROUP_SWITCH));
        groupList.add(new GroupItemBean("lamp item" + 4, "", ""+ Constant.GROUP_SWITCH));
        groupList.add(new GroupItemBean("lamp item" + 4, "", ""+ Constant.GROUP_SWITCH));
        groupList.add(new GroupItemBean("lamp item" + 4, "", ""+ Constant.GROUP_SWITCH));
        groupList.add(new GroupItemBean("lamp item" + 4, "", ""+ Constant.GROUP_SWITCH));
        //test
        groupAdapter.setOnItemClickListener(new GroupListAdapter.OnItemClickListener() {
            @Override
            public void click(View itemView, int position, GroupItemBean data) {
                Logger.i(TAG, "itemView : " + itemView + "position : " + position + "data : " + data.toString());
//                Intent intent = new Intent(GroupListActivity.this,LampBulbActivity.class);
//                intent.putExtra("data",data);
//                startActivity(intent);
            }
        });

        groupAdapter.addData(groupList);

//        rvList.setVisibility(View.GONE);
        btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<GroupItemBean> bindingList = groupAdapter.getBindingList();
                int size = bindingList.size();
                boolean hasCheckOne  = false;
                for (int i = 0; i < size; i++) {
                    GroupItemBean groupItemBean = bindingList.get(i);
                    if (groupItemBean.isChecked()){
                        hasCheckOne = true;
                    }
                }
                if (!hasCheckOne){
                    showToast(R.string.choose_one);
                }else {//

                    List<GroupItemBean> checkedItemList = groupAdapter.getBindingList();
                    for (int i = 0; i < size; i++) {
                        GroupItemBean groupItemBean = bindingList.get(i);
                        if (groupItemBean.isChecked()){
                            checkedItemList.add(groupItemBean);
                        }
                    }
                    //todo 选中的设备添加场景成功
                    showToast("添加场景成功");
                    finish();
                }
            }
        });
    }

    @Override
    public void doProcess() {
        if (isControlScene){
            showFullScreenChooseDialog();
        }
    }

    @OnClick({R.id.back_left})
    public void onViewClicked(View v) {
        switch (v.getId()){
            case R.id.back_left:
                finish();
                break;

        }
    }


    private void showFullScreenChooseDialog() {
        final Dialog dialog = new Dialog(this,R.style.Dialog_FULL_SCREEN);
        dialog.setContentView(R.layout.dialog_choose_sense_type);
        if (dialog.isShowing()){
            return;
        }
        dialog.show();

        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.use_colour:
                        mShowType = Constant.GROUP_COLOUR;
                        filterDevices();
                        break;
                    case R.id.use_white:
                        mShowType = Constant.GROUP_WHITE;
                        filterDevices();
                        break;
                    case R.id.use_switch:
                        mShowType = Constant.GROUP_SWITCH;
                        filterDevices();
                        break;
                }
                if (dialog.isShowing()){
                    dialog.dismiss();
                }
            }
        };
        dialog.findViewById(R.id.use_colour).setOnClickListener(clickListener);
        dialog.findViewById(R.id.use_switch).setOnClickListener(clickListener);
        dialog.findViewById(R.id.use_white).setOnClickListener(clickListener);
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                if (Constant.GROUP_NONE.equals(mShowType)) {
                    finish();
                }
            }
        });
    }

    private void filterDevices() {
        List<GroupItemBean> bindingList = groupAdapter.getBindingList();//form network
        List<GroupItemBean> filterList = new ArrayList<>();
        for (GroupItemBean item :
                bindingList) {
            if (mShowType.equals(item.getGroupType())){
                filterList.add(item);
            }
        }

        groupAdapter.addData(filterList,true);

    }
}
