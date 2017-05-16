package com.example.administrator.remotecontrol;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.remotecontrol.db.SceneData;
import com.example.administrator.remotecontrol.db.SceneSonData;
import com.example.administrator.remotecontrol.scene.Scene;
import com.example.administrator.remotecontrol.scene.SceneAdapter;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

public class ChooseSonScene extends MainActivity {

    private List<Scene> sceneList = new ArrayList<>();
    private TextView titleText;
    private ImageButton backButton;
    private ImageButton userButton;
    private Button addNewEquipment;
    private SceneAdapter adapter;
    private ListView listView;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private List<String> temp = new ArrayList<String>();
    private List<String> saveKey = new ArrayList<String>();

    String sceneName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_scene);
        Intent intent = getIntent();
        sceneName = intent.getStringExtra("scene_name");
        titleText = (TextView) findViewById(R.id.title_text);
        backButton = (ImageButton) findViewById(R.id.back_button);
        userButton = (ImageButton) findViewById(R.id.user_button);
        addNewEquipment = (Button) findViewById(R.id.add_new_scene);
        editor = getSharedPreferences("newEquipment",MODE_APPEND).edit();
        pref = getSharedPreferences("newEquipment",MODE_APPEND);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChooseSonScene.this.finish();
            }
        });
        //设置标题栏
        titleText.setText(sceneName);
        backButton.setVisibility(View.VISIBLE);

        adapter = new SceneAdapter(ChooseSonScene.this,R.layout.scene_item,sceneList);
        listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(adapter);
        //初始化场景数
        initScene();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> parent,View view,int position,long id){
                Scene scene = sceneList.get(position);
                if (scene.getSceneName().contains("电视")){
                    Intent intent = new Intent(ChooseSonScene.this,ControlTV.class);
                    startActivity(intent);
                } else if (scene.getSceneName().contains("风扇")){
                    Intent intent = new Intent(ChooseSonScene.this,ControlFan.class);
                    startActivity(intent);
                } else if (scene.getSceneName().contains("灯")){
                    Intent intent = new Intent(ChooseSonScene.this,ControlLight.class);
                    startActivity(intent);
                } else if (scene.getSceneName().contains("空调")){
                    Intent intent = new Intent(ChooseSonScene.this,ControlAir.class);
                    startActivity(intent);
                }

            }
        });

        userButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChooseSonScene.this,PersonalCenter.class);
                startActivity(intent);
            }
        });


        //给该场景添加设备
        addNewEquipment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //包含所有设备的集合
                List<SceneSonData> sceneSonDatas = DataSupport.findAll(SceneSonData.class);
                //创建List数组来过渡
                final List<String> itemList = new ArrayList<String>();
                for(SceneSonData sceneSonData:sceneSonDatas){
                    itemList.add(sceneSonData.getSceneSonName());
                }
                //再将List数组转为String数组
                final String[] items = new String[itemList.size()];
                itemList.toArray(items);

                //向场景添加新设备时记录所需的list
                final List<String> newList = new ArrayList<String>();
                final AlertDialog.Builder addNewEquipmentDialog =new  AlertDialog.Builder(ChooseSonScene.this);
                addNewEquipmentDialog.setTitle("选择设备：");
                addNewEquipmentDialog.setCancelable(true);
                //参数1：弹出框的信息集合，一般为字符串集合
                //参数2：被默认选中的，一个布尔类型的数组
                //参数3：勾选事件监听
                addNewEquipmentDialog.setMultiChoiceItems(items, null, new DialogInterface.OnMultiChoiceClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int which, boolean isChecked) {
                        // dialog：不常使用，弹出框接口  which：勾选或取消的是第几个  isChecked：是否勾选
                        if (isChecked) {
                            // 选中
                            newList.add(items[which]);
                            Toast.makeText(ChooseSonScene.this, "选中" + items[which], Toast.LENGTH_SHORT).show();
                        } else {
                            // 取消选中
                            newList.remove(items[which]);
                            Toast.makeText(ChooseSonScene.this, "取消选中" + items[which], Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                addNewEquipmentDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        for (int j = 0; j < newList.size(); j++) {
                            List<SceneSonData> sceneSonDatas = DataSupport.where("sceneSonName = ?", newList.get(j)).find(SceneSonData.class);
                            for (SceneSonData sceneSonData : sceneSonDatas) {   //场景从数据库中读出数据添加到ListView
                                Scene scene = new Scene(sceneSonData.getSceneSonName(), R.drawable.scene);
                                sceneList.add(scene);
                            }
                        }
                        adapter.notifyDataSetChanged();
                        Log.d("ChooseSonScene", "选择项为 " + newList.toString());
                        temp.addAll(newList);

                        //去重复
                        temp = removeDuplicateWithOrder(temp);

                        Log.d("ChooseSonScene", "A为 " + temp.toString());
                        //此时应该将现有的设备一起存起来
                        //将上面的list转换为String数组方便储存
                        String[] newStringList = new String[temp.size()];
                        temp.toArray(newStringList);
                        //再转换为字符串，用，分割
                        StringBuffer result1 = new StringBuffer();
                        for(int a =0; a < newStringList.length;a++){
                            if (a == 0){
                                result1.append(newStringList[a]);
                            }else {
                                result1.append(","+newStringList[a]);
                            }
                        }
                        String result2 = result1.toString();
                        //将该字符串存起来，
                        editor.putString(sceneName,result2);

                        //将键值也存在来，方便后面做比较
                        saveKey.add(sceneName);
                        String[] saveKey1 = new String[saveKey.size()];
                        saveKey.toArray(saveKey1);
                        StringBuffer saveKey2 = new StringBuffer();
                        for(int b =0; b < saveKey1.length;b++){
                            if (b == 0){
                                saveKey2.append(saveKey1[b]);
                            }else {
                                saveKey2.append(","+saveKey1[b]);
                            }
                        }
                        String saveKey3 = saveKey2.toString();
                        editor.putString("saveKey",saveKey3);

                        editor.apply();
                        Log.d("ChooseSonScene", "result2为 " + result2);
                        updatelistview();
                    }
                });
                addNewEquipmentDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                addNewEquipmentDialog.show();
            }
        });
    }
    private void initScene(){
        //默认场景
        if(sceneName.contains("全部设备")){
            addNewEquipment.setVisibility(View.GONE);
            List<SceneSonData> sceneSonDatas = DataSupport.findAll(SceneSonData.class);
            for (SceneSonData sceneSonData:sceneSonDatas){   //场景从数据库中读出数据添加到ListView
                Scene scene = new Scene(sceneSonData.getSceneSonName(),R.drawable.scenesontv);
                if (scene.getSceneName().contains("电视")){
                    scene.setImageId(R.drawable.scenesontv);
                }else if(scene.getSceneName().contains("空调")){
                    scene.setImageId(R.drawable.scenesonair);
                }else if(scene.getSceneName().contains("灯")){
                    scene.setImageId(R.drawable.scenesonlight);
                }else if(scene.getSceneName().contains("风扇")){
                    scene.setImageId(R.drawable.scenesonfan);
                }
                sceneList.add(scene);
            }
        } else {
            updatelistview();
        }
    }

    //更新列表函数
    public void updatelistview(){
        sceneList.clear();
//        editor.remove("saveKey");
//        editor.commit();
        //读取存储的键值处理
        String readKey1 = pref.getString("saveKey","");
        String[] readKey2 = readKey1.split("\\,");
        List<String> readKey3 = new ArrayList<String>();
        Collections.addAll(readKey3, readKey2);
        saveKey.addAll(readKey3);
        readKey3 = removeDuplicateWithOrder(readKey3);
        Log.d("ChooseSonScene", "saveKey为: " + readKey3);

        //读取存储的设备字符串
        String readResult = pref.getString(sceneName,"");
        //再将读出来的内容转换为String数组
        String[] readResult2 = readResult.split("\\,");
        //继续转换为List
        List<String> readResult3 = new ArrayList<String>();
        Collections.addAll(readResult3, readResult2);
        temp.addAll(readResult3);
        Log.d("ChooseSonScene", "readResult3为 " + readResult3.toString());
        for (int j = 0; j < readResult3.size(); j++) {
            List<SceneSonData> sceneSonDatas = DataSupport.where("sceneSonName = ?", readResult3.get(j)).find(SceneSonData.class);
            for (SceneSonData sceneSonData : sceneSonDatas) {   //场景从数据库中读出数据添加到ListView
                Scene scene = new Scene(sceneSonData.getSceneSonName(), R.drawable.scene);
                if (scene.getSceneName().contains("电视")){
                    scene.setImageId(R.drawable.scenesontv);
                }else if(scene.getSceneName().contains("空调")){
                    scene.setImageId(R.drawable.scenesonair);
                }else if(scene.getSceneName().contains("灯")){
                    scene.setImageId(R.drawable.scenesonlight);
                }else if(scene.getSceneName().contains("风扇")){
                    scene.setImageId(R.drawable.scenesonfan);
                }
                sceneList.add(scene);
            }
        }
        adapter.notifyDataSetChanged();
    }
    //List去重函数
    public List<String> removeDuplicateWithOrder(List<String> list){
        Set set =  new  HashSet();
        List newList =  new  ArrayList();
        for(Iterator iter = list.iterator();iter.hasNext();){
            Object element = iter.next();
            if  (set.add(element))
                newList.add(element);
        }
        list.clear();
        list.addAll(newList);
        return list;
    }

}
