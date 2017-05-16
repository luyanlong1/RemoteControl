package com.example.administrator.remotecontrol.db;

import android.support.annotation.IntegerRes;

import org.litepal.crud.DataSupport;

/**
 * Created by Administrator on 2017/3/20 0020.
 */

public class SceneSonData extends DataSupport {
    private int id;
    private String sceneSonName;
    private int sceneId;

    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }
    public String getSceneSonName(){
        return sceneSonName;
    }
    public void setSceneSonName(String sceneSonName){
        this.sceneSonName = sceneSonName;
    }
    public int getSceneId(){
        return sceneId;
    }
    public void setSceneId(int sceneId){
        this.sceneId = sceneId;
    }
}
