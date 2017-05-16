package com.example.administrator.remotecontrol.scene;

import android.graphics.Paint;
import android.text.method.HideReturnsTransformationMethod;

/**
 * Created by Administrator on 2017/3/19 0019.
 */

public class Scene {
    private String sceneName;
    private int imageId;
    public Scene(String sceneName,int imageId){
        this.sceneName = sceneName;
        this.imageId = imageId;

    }
    public String getSceneName(){
        return sceneName;
    }
    public  int getImageId(){
        return imageId;
    }
    public void setImageId(int imageId){
        this.imageId = imageId;
    }
}
