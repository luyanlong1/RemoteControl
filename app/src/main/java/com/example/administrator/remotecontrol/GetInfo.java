package com.example.administrator.remotecontrol;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.administrator.remotecontrol.db.SceneData;
import com.example.administrator.remotecontrol.db.SceneSonData;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.sql.Connection;
import java.util.List;

public class GetInfo extends MainActivity {
    private ImageButton backButton;
    private Button netLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_info);
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
                Connector.getDatabase();
                //模拟数据储存在数据库中
                //每次先清空表
                //表SceneData
                DataSupport.deleteAll(SceneData.class);

                SceneData sceneData1 = new SceneData();
                sceneData1.setId(1);
                sceneData1.setSceneDataName("全部设备");
                sceneData1.save();


                //表SceneSonData数据模拟
                DataSupport.deleteAll(SceneSonData.class);
                SceneSonData sceneSonData1 = new SceneSonData();
                sceneSonData1.setId(1);
                sceneSonData1.setSceneSonName("客厅电视");
                sceneSonData1.setSceneId(2);
                sceneSonData1.save();
                SceneSonData sceneSonData2 = new SceneSonData();
                sceneSonData2.setId(2);
                sceneSonData2.setSceneSonName("客厅风扇");
                sceneSonData2.setSceneId(2);
                sceneSonData2.save();
                SceneSonData sceneSonData3 = new SceneSonData();
                sceneSonData3.setId(3);
                sceneSonData3.setSceneSonName("客厅灯光");
                sceneSonData3.setSceneId(2);
                sceneSonData3.save();
                SceneSonData sceneSonData4 = new SceneSonData();
                sceneSonData4.setId(4);
                sceneSonData4.setSceneSonName("客厅空调");
                sceneSonData4.setSceneId(2);
                sceneSonData4.save();
                SceneSonData sceneSonData5 = new SceneSonData();
                sceneSonData5.setId(5);
                sceneSonData5.setSceneSonName("厨房电视");
                sceneSonData5.setSceneId(3);
                sceneSonData5.save();
                SceneSonData sceneSonData6 = new SceneSonData();
                sceneSonData6.setId(6);
                sceneSonData6.setSceneSonName("厨房风扇");
                sceneSonData6.setSceneId(3);
                sceneSonData6.save();
                SceneSonData sceneSonData7 = new SceneSonData();
                sceneSonData7.setId(7);
                sceneSonData7.setSceneSonName("卧室电视");
                sceneSonData7.setSceneId(4);
                sceneSonData7.save();

                List<SceneSonData> sceneDatas = DataSupport.findAll(SceneSonData.class);
                for (SceneSonData sceneDataTest:sceneDatas){   //场景从数据库中读出数据添加到ListView
                    Log.d("ChooseSonScene","id is "+ sceneDataTest.getId());
                    Log.d("ChooseSonScene","name is "+ sceneDataTest.getSceneSonName());
                    Log.d("ChooseSonScene","scene id is "+ sceneDataTest.getSceneId());
                }

                Intent intent =new Intent(GetInfo.this,ChooseScene.class);
                startActivity(intent);
            }
        });

    }
}
