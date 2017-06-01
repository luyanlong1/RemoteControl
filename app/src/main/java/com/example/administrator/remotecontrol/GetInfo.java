package com.example.administrator.remotecontrol;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.administrator.remotecontrol.db.SceneData;
import com.example.administrator.remotecontrol.db.SceneSonData;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.util.List;

public class GetInfo extends MainActivity {
    private EditText accountEdit,passwordEdit,ipAddress;
    private ImageButton backButton;
    private Button netLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_info);
        accountEdit = (EditText) findViewById(R.id.netAccount);
        passwordEdit = (EditText) findViewById(R.id.netPassword);
        ipAddress = (EditText) findViewById(R.id.ipAddress);
        netLogin = (Button) findViewById(R.id.netLogin);
        backButton = (ImageButton) findViewById(R.id.back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GetInfo.this.finish();
            }
        });
        netLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String account = accountEdit.getText().toString();
                String password = passwordEdit.getText().toString();
                if (account.equals("admin") && password.equals("123456")) {
                    //链接数据库
                    Connector.getDatabase();
                    //发送Http请求
                    String url = "http://192.168.191.1:8080/main?action=admin";
                    sendHttpRequestByGet(url);

                    //每次先清空表
                    //表SceneData
                    DataSupport.deleteAll(SceneData.class);
                    SceneData sceneData1 = new SceneData();
                    sceneData1.setId(1);
                    sceneData1.setSceneDataName("全部设备");
                    sceneData1.save();

//                    //表SceneSonData数据模拟
//                    DataSupport.deleteAll(SceneSonData.class);
//                    SceneSonData sceneSonData1 = new SceneSonData();
//                    sceneSonData1.setId(1);
//                    sceneSonData1.setSceneSonName("客厅电视");
//                    sceneSonData1.setSceneId(2);
//                    sceneSonData1.save();
//                    SceneSonData sceneSonData2 = new SceneSonData();
//                    sceneSonData2.setId(2);
//                    sceneSonData2.setSceneSonName("客厅风扇");
//                    sceneSonData2.setSceneId(2);
//                    sceneSonData2.save();
//                    SceneSonData sceneSonData3 = new SceneSonData();
//                    sceneSonData3.setId(3);
//                    sceneSonData3.setSceneSonName("客厅灯光");
//                    sceneSonData3.setSceneId(2);
//                    sceneSonData3.save();
//                    SceneSonData sceneSonData4 = new SceneSonData();
//                    sceneSonData4.setId(4);
//                    sceneSonData4.setSceneSonName("客厅空调");
//                    sceneSonData4.setSceneId(2);
//                    sceneSonData4.save();
//                    SceneSonData sceneSonData5 = new SceneSonData();
//                    sceneSonData5.setId(5);
//                    sceneSonData5.setSceneSonName("厨房电视");
//                    sceneSonData5.setSceneId(3);
//                    sceneSonData5.save();
//                    SceneSonData sceneSonData6 = new SceneSonData();
//                    sceneSonData6.setId(6);
//                    sceneSonData6.setSceneSonName("厨房风扇");
//                    sceneSonData6.setSceneId(3);
//                    sceneSonData6.save();
//                    SceneSonData sceneSonData7 = new SceneSonData();
//                    sceneSonData7.setId(7);
//                    sceneSonData7.setSceneSonName("卧室电视");
//                    sceneSonData7.setSceneId(4);
//                    sceneSonData7.save();

                    List<SceneSonData> sceneDatas = DataSupport.findAll(SceneSonData.class);
                    for (SceneSonData sceneDataTest:sceneDatas){   //场景从数据库中读出数据添加到ListView
                        Log.d("ChooseSonScene","id is "+ sceneDataTest.getId());
                        Log.d("ChooseSonScene","name is "+ sceneDataTest.getSceneSonName());
                        Log.d("ChooseSonScene","scene id is "+ sceneDataTest.getSceneId());
                    }

                    Intent intent =new Intent(GetInfo.this,ChooseScene.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(GetInfo.this,"帐号或密码不正确",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
    //发送HTTP请求获取服务器上存储的设备列表
    private void sendHttpRequestByGet(final String s){
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                BufferedReader bufferedReader = null;
                try{
                    URL url = new URL(s);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    InputStream inputStream = connection.getInputStream();
                    bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine())!= null){
                        response.append(line);
                    }
                    String responseData = response.toString();
                    Log.d("GetInfo","bufferedReader is "+ responseData);
                    parseJSONWithGSON(responseData);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    //使用GSON解析JSON数组
    private void parseJSONWithGSON(String jsonData){

        //表SceneSonData数据模拟
        //每次先清空表
        DataSupport.deleteAll(SceneSonData.class);

        //借助TypeToken将期望解析成的数据类型传入到fromJson()方法中
        Gson gson = new Gson();
        List <JSONForEquipmentList> jsonForEquipmentList = gson.fromJson(jsonData,new TypeToken<List<JSONForEquipmentList>>(){}.getType());
        for (JSONForEquipmentList jsonForEquipment : jsonForEquipmentList){
            //根据约定的映射将解析的数据存储到数据库中
            if (jsonForEquipment.getName().contains("ketingTV")){
                SceneSonData sceneSonData1 = new SceneSonData();
                sceneSonData1.setId(1);
                sceneSonData1.setSceneSonName("客厅电视");
                sceneSonData1.setSceneId(2);
                sceneSonData1.save();
            }else if(jsonForEquipment.getName().contains("ketingFAN")){
                SceneSonData sceneSonData2 = new SceneSonData();
                sceneSonData2.setId(2);
                sceneSonData2.setSceneSonName("客厅风扇");
                sceneSonData2.setSceneId(2);
                sceneSonData2.save();
            }else if(jsonForEquipment.getName().contains("ketingLIGHT")){
                SceneSonData sceneSonData3 = new SceneSonData();
                sceneSonData3.setId(3);
                sceneSonData3.setSceneSonName("客厅灯光");
                sceneSonData3.setSceneId(2);
                sceneSonData3.save();
            }else if(jsonForEquipment.getName().contains("ketingAIR")){
                SceneSonData sceneSonData4 = new SceneSonData();
                sceneSonData4.setId(4);
                sceneSonData4.setSceneSonName("客厅空调");
                sceneSonData4.setSceneId(2);
                sceneSonData4.save();
            }else if(jsonForEquipment.getName().contains("chufangTV")){
                SceneSonData sceneSonData5 = new SceneSonData();
                sceneSonData5.setId(5);
                sceneSonData5.setSceneSonName("厨房电视");
                sceneSonData5.setSceneId(3);
                sceneSonData5.save();
            }else if(jsonForEquipment.getName().contains("chufangFAN")){
                SceneSonData sceneSonData6 = new SceneSonData();
                sceneSonData6.setId(6);
                sceneSonData6.setSceneSonName("厨房风扇");
                sceneSonData6.setSceneId(3);
                sceneSonData6.save();
            }else if(jsonForEquipment.getName().contains("woshiTV")){
                SceneSonData sceneSonData7 = new SceneSonData();
                sceneSonData7.setId(7);
                sceneSonData7.setSceneSonName("卧室电视");
                sceneSonData7.setSceneId(4);
                sceneSonData7.save();
            }
            Log.d("GetInfo","id is"+jsonForEquipment.getId());
            Log.d("GetInfo","name is"+jsonForEquipment.getName());
        }
    }
}
