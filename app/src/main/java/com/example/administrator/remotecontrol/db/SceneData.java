package com.example.administrator.remotecontrol.db;

import org.litepal.crud.DataSupport;

/**
 * Created by Administrator on 2017/3/20 0020.
 */

public class SceneData extends DataSupport {
    private int _id;
    private String sceneDataName;

    public int getId(){
        return _id;
    }
    public void setId(int _id){
        this._id = _id;
    }

    public String getSceneDataName(){
        return sceneDataName;
    }
    public void setSceneDataName(String sceneDataName){
        this.sceneDataName = sceneDataName;
    }

}
