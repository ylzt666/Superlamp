package com.linkus.superlamp.beans;

/**
 * Created by 54757 on 9/25/2017.
 */

public class SceneBean extends SuperBean {
    public String sceneName;
    public String sceneId;
    public String sceneType;
    public int sceneIcon;
    public int sceneBg;



    public SceneBean(String sceneName, String sceneId, String sceneType, int sceneIcon, int sceneBg) {
        this.sceneName = sceneName;
        this.sceneId = sceneId;
        this.sceneType = sceneType;
        this.sceneIcon = sceneIcon;
        this.sceneBg = sceneBg;
    }

    public String getSceneName() {
        return sceneName;
    }

    public void setSceneName(String groupName) {
        this.sceneName = groupName;
    }

    public String getSceneId() {
        return sceneId;
    }

    public void setSceneId(String sceneId) {
        this.sceneId = sceneId;
    }

    public String getSceneType() {
        return sceneType;
    }

    public void setSceneType(String sceneType) {
        this.sceneType = sceneType;
    }


    public int getSceneIcon() {
        return sceneIcon;
    }

    public void setSceneIcon(int sceneIcon) {
        this.sceneIcon = sceneIcon;
    }

    public int getSceneBg() {
        return sceneBg;
    }

    public void setSceneBg(int sceneBg) {
        this.sceneBg = sceneBg;
    }

    @Override
    public String toString() {
        return "SceneBean[" +
                "sceneName='" + sceneName + '\'' +
                ", sceneId='" + sceneId + '\'' +
                ", sceneType='" + sceneType + '\'' +
                ", sceneIcon=" + sceneIcon +
                ", sceneBg=" + sceneBg +
                ']';
    }
}
