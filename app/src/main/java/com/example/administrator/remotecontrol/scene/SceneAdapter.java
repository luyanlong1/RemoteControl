package com.example.administrator.remotecontrol.scene;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextClock;
import android.widget.TextView;

import com.example.administrator.remotecontrol.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/19 0019.
 */

public class SceneAdapter extends ArrayAdapter <Scene> {
    private  int resourceId;
    List<Scene> sceneList = new ArrayList<>();
    public SceneAdapter(Context context, int textViewResourceId, List<Scene> objects){
        super(context,textViewResourceId,objects);
        resourceId = textViewResourceId;
    }
    public View getView(int position, View convertView, ViewGroup parent){
        Scene scene = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
        ImageView sceneImage = (ImageView) view.findViewById(R.id.scene_image);
        TextView sceneName = (TextView) view.findViewById(R.id.scene_name);
        sceneImage.setImageResource(scene.getImageId());
        sceneName.setText(scene.getSceneName());
        return view;
    }
}
