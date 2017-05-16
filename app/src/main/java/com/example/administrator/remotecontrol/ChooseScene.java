package com.example.administrator.remotecontrol;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.opengl.GLSurfaceView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.remotecontrol.db.SceneData;
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

public class ChooseScene extends MainActivity {
    private List<Scene> sceneList = new ArrayList<>();
    private TextView titleText;
    private ImageButton backButton;
    private ImageButton userButton;
    private Button addNewScene;
    private AlertDialog alertDialog1;
    private SceneAdapter adapter;
    private ListView listView;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private List<String> saveKey = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_scene);
        pref = getSharedPreferences("newEquipment",MODE_APPEND);
        editor = getSharedPreferences("newEquipment",MODE_APPEND).edit();
        titleText = (TextView) findViewById(R.id.title_text);
        backButton = (ImageButton) findViewById(R.id.back_button);
        userButton = (ImageButton) findViewById(R.id.user_button);
        addNewScene = (Button) findViewById(R.id.add_new_scene);

        adapter = new SceneAdapter(ChooseScene.this,R.layout.scene_item,sceneList);
        listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(adapter);

        //初始化场景数据
        initScene();
        //函数顺序写反了，然后数据库改变了 notif函数没用，更新不了listview列表

        //点击listview跳转
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> parent,View view,int position,long id){
                Scene scene = sceneList.get(position);
                Intent intent = new Intent(ChooseScene.this,ChooseSonScene.class);
                intent.putExtra("scene_name",scene.getSceneName());
                startActivity(intent);

                Toast.makeText(ChooseScene.this,scene.getSceneName(),Toast.LENGTH_SHORT).show();
            }
        });
        userButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChooseScene.this,PersonalCenter.class);
                startActivity(intent);
            }
        });

        //listview长按监听
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(final AdapterView<?> adapterView, View view, final int i, long l) {
                final Scene scene = sceneList.get(i);
                Log.d("ChooseScene","当前获取的到item是"+scene.getSceneName());

                final String[] items = {"重命名","删除","取消"};
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(ChooseScene.this);
                alertBuilder.setTitle("操作选项");
                alertBuilder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int index) {
                        switch (index){

                            //选择重命名
                            case 0:
                                final CustomDialog dialog = new  CustomDialog(ChooseScene.this);
                                final EditText editText = (EditText) dialog.getSceneName();
                                dialog.show();
                                dialog.setOnPositiveListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        String newSceneName = editText.getText().toString();
                                        Log.d("ChooseScene","新名字是"+newSceneName);

                                        ContentValues values = new ContentValues();
                                        values.put("sceneDataName",newSceneName);
                                        DataSupport.updateAll(SceneData.class,values, "sceneDataName = ?",scene.getSceneName());
                                        Log.d("ChooseScene","保存到数据库");
                                        //每修改一个场景名称,清空list数据，再重新从数据库读取添加一次
                                        updatelistview();

                                        List<SceneData> sceneDatas = DataSupport.findAll(SceneData.class);
                                        //数据库打印测试
                                        for (SceneData sceneDataTest:sceneDatas){   //场景从数据库中读出数据添加到ListView
                                            Log.d("ChooseScene","id is "+ sceneDataTest.getId());
                                            Log.d("ChooseScene","name is "+ sceneDataTest.getSceneDataName());
                                        }
                                        dialog.cancel();

                                    }
                                });
                                dialog.setOnNegativeListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        dialog.cancel();
                                    }
                                });
                                break;
                            //删除
                            case 1:
                                AlertDialog.Builder dialog1 = new AlertDialog.Builder(ChooseScene.this);
                                dialog1.setTitle("确认删除"+scene.getSceneName()+"吗?");
                                dialog1.setCancelable(true);
                                dialog1.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        DataSupport.deleteAll(SceneData.class, "sceneDataName = ?", scene.getSceneName());

                                        //每删除一个场景名称,清空list数据，再重新从数据库读取添加一次
                                        deleteNonexistentScene();
                                        updatelistview();

                                    }
                                });
                                dialog1.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                    }
                                });
                                dialog1.show();
                                break;

                            //取消
                            case 2:
                                break;
                        }
                        alertDialog1.dismiss();
                    }
                });
                alertDialog1 = alertBuilder.create();
                alertDialog1.show();
                return true;
            }

        });

        //添加场景按钮  弹出对话框
        addNewScene.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final CustomDialog dialog = new  CustomDialog(ChooseScene.this);
                final EditText editText = (EditText) dialog.getSceneName();

                dialog.show();
                dialog.setOnPositiveListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String newSceneName = editText.getText().toString();
                        //trim()去掉前导空白和后导空
                        if (newSceneName.trim().isEmpty()){
                            Toast.makeText(ChooseScene.this,"场景名称不能为空",Toast.LENGTH_SHORT).show();
                        } else {
                            Log.d("ChooseScene",newSceneName);
                            SceneData sceneData = new SceneData();
                            sceneData.setSceneDataName(newSceneName);
                            sceneData.save();
                            Log.d("ChooseScene","保存到数据库");
                            //每增加一个场景,清空list数据，再重新从数据库读取添加一次
                            updatelistview();
                        }

                        //数据库打印测试
                        List<SceneData> sceneDatas = DataSupport.findAll(SceneData.class);
                        for (SceneData sceneDataTest:sceneDatas){   //场景从数据库中读出数据添加到ListView
                            Log.d("ChooseScene","id is "+ sceneDataTest.getId());
                            Log.d("ChooseScene","name is "+ sceneDataTest.getSceneDataName());
                        }
                        dialog.cancel();

                    }
                });
                dialog.setOnNegativeListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.cancel();
                    }
                });
            }
        });
    }
    private void initScene(){
        titleText.setText("我的场景");
        backButton.setVisibility(View.GONE);
        userButton.setVisibility(View.VISIBLE);
        //外部场景修改之后都要进行判断，除了重命名
        deleteNonexistentScene();
        updatelistview();

    }
    public void updatelistview(){
        sceneList.clear();
        List<SceneData> sceneDatas = DataSupport.findAll(SceneData.class);
        for (SceneData sceneData:sceneDatas){   //场景从数据库中读出数据添加到ListView
            Scene scene = new Scene(sceneData.getSceneDataName(),R.drawable.scene);
            sceneList.add(scene);
        }
        adapter.notifyDataSetChanged();
    }
    //List去重函数
    public List<String> removeDuplicateWithOrder(List<String> list){
        Set set =  new HashSet();
        List newList =  new  ArrayList();
        for(Iterator iter = list.iterator(); iter.hasNext();){
            Object element = iter.next();
            if  (set.add(element))
                newList.add(element);
        }
        list.clear();
        list.addAll(newList);
        return list;
    }

    //saveList和场景列表对比，没有的场景所对应的SharedPreferences删除掉
    public void deleteNonexistentScene(){
        //读出savaList
        String readKey1 = pref.getString("saveKey","");
        String[] readKey2 = readKey1.split("\\,");
        List<String> readKey3 = new ArrayList<String>();
        Collections.addAll(readKey3, readKey2);
        saveKey.addAll(readKey3);
        Log.d("ChooseSonScene", "场景界面显示的saveKey为: " + readKey3);
        readKey3 = removeDuplicateWithOrder(readKey3);
        String[] readKey4 = new String[readKey3.size()];
        readKey3.toArray(readKey4);

        //读出当前数据库中的所有场景
        List<SceneData> sceneDatas = DataSupport.findAll(SceneData.class);
        List<String> sceneDataItemList = new ArrayList<String>();
        for(SceneData sceneData:sceneDatas){
            sceneDataItemList.add(sceneData.getSceneDataName());
        }
        String[] sceneDataItems = new String[sceneDataItemList.size()];
        sceneDataItemList.toArray(sceneDataItems);
        Log.d("ChooseSonScene", "sceneDataItemList: " + sceneDataItemList);

        //然后和存储的键值做对比，找出哪些是当前不存在的场景
        for (int i =0;i<readKey3.size();i++){
            if(!(sceneDataItemList.contains(readKey4[i]))){
                editor.remove(readKey4[i]);
                editor.commit();
                Log.d("ChooseSonScene", "不存在的是: " + readKey4[i]);

            }
        }
    }
}
