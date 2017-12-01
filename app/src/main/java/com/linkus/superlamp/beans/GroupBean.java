package com.linkus.superlamp.beans;

/**
 * Created by 54757 on 9/25/2017.
 */

public class GroupBean extends SuperBean {
    public String groupName;
    public String groupId;
    public String groupType;

    /**
     * 设备开关是否打开
     */
    public boolean isDeviceOpen ;

    /**
     * 闹铃开关是否打开
     */
    public boolean isAlarmOpen;

    public GroupBean(String groupName, String groupId, String groupType) {
        this.groupName = groupName;
        this.groupId = groupId;
        this.groupType = groupType;
    }

    public boolean isDeviceOpen() {
        return isDeviceOpen;
    }

    public void setDeviceOpen(boolean deviceOpen) {
        isDeviceOpen = deviceOpen;
    }

    public boolean isAlarmOpen() {
        return isAlarmOpen;
    }

    public void setAlarmOpen(boolean alarmOpen) {
        isAlarmOpen = alarmOpen;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupType() {
        return groupType;
    }

    public void setGroupType(String groupType) {
        this.groupType = groupType;
    }

    @Override
    public String toString() {
        return "GroupBean[" +
                "groupName='" + groupName + '\'' +
                ", sceneId='" + groupId + '\'' +
                ", sceneType='" + groupType + '\'' +
                ']';
    }
}
