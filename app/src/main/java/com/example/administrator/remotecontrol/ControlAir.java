package com.example.administrator.remotecontrol;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ControlAir extends MainActivity {
    private ImageButton backButton,userButton,airTemperUp,airTemperDown,airStyle,airWindScale,airWindDirection,airSleepButton;
    private TextView airStyleView,airWindView,airTemperView,airDegreeView,airWindDirectionView;
    private ImageView ariStyleIcon;
    private SharedPreferences pref;
    Boolean isCheck;

    double item = 23.0;
    int flagStyle = 0;
    int flagWind = 0;
    int flagWindDirection = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control_air);
        initView();
        init();
    }
    private void initView() {
        backButton = (ImageButton) findViewById(R.id.back_button);
        userButton = (ImageButton) findViewById(R.id.user_button);
        airTemperUp = (ImageButton) findViewById(R.id.air_temper_up);
        airTemperDown = (ImageButton) findViewById(R.id.air_temper_down);
        airStyle = (ImageButton) findViewById(R.id.air_style_button);
        airWindScale = (ImageButton) findViewById(R.id.air_wind_button);
        airWindDirection = (ImageButton) findViewById(R.id.air_weed_direction_button);
        airStyleView = (TextView) findViewById(R.id.air_style_view);
        airWindView = (TextView) findViewById(R.id.air_wind_view);
        airTemperView = (TextView) findViewById(R.id.air_now_temper);
        airDegreeView = (TextView) findViewById(R.id.air_degree);
        airWindDirectionView = (TextView)findViewById(R.id.air_weed_direction_view);
        ariStyleIcon = (ImageView) findViewById(R.id.ari_style_icon);
        airSleepButton = (ImageButton) findViewById(R.id.air_sleep_button);
        pref = getSharedPreferences("isNotVibrator",MODE_APPEND);
    }
    private void init() {
        isCheck = pref.getBoolean("isNotVibrator",false);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ControlAir.this.finish();
            }
        });
        airSleepButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isNotVibrator();
            }
        });
        airTemperUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isNotVibrator();
                if (item < 30) {
                    item += 1;
                    airTemperView.setText(""+item);
                } else {
                    Toast.makeText(ControlAir.this,"已达到温度上限",Toast.LENGTH_SHORT).show();
                }
            }
        });
        airTemperDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isNotVibrator();
                if (item > 17) {
                    item -= 1;
                    airTemperView.setText(""+item);
                } else {
                    Toast.makeText(ControlAir.this,"已达到温度下限",Toast.LENGTH_SHORT).show();
                }
            }
        });
        airStyle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isNotVibrator();
                if (flagStyle==0){
                    flagStyle = 1;
                    airStyleView.setText("制热");
                    airTemperView.setTextColor(0xFFfaf206);
                    airDegreeView.setTextColor(0xFFfaf206);
                    ariStyleIcon.setBackgroundResource(R.mipmap.air_style_hot);
                } else if(flagStyle == 1) {
                    flagStyle = 2;
                    airStyleView.setText("制冷");
                    airTemperView.setTextColor(0xFF54defd);
                    airDegreeView.setTextColor(0xFF54defd);
                    ariStyleIcon.setBackgroundResource(R.mipmap.air_style_cool);
                }else if(flagStyle == 2) {
                    flagStyle = 3;
                    airStyleView.setText("除湿");
                    airTemperView.setTextColor(0xFF8ffa99);
                    airDegreeView.setTextColor(0xFF8ffa99);
                    ariStyleIcon.setBackgroundResource(R.mipmap.air_style_dry);
                }else if(flagStyle == 3) {
                    flagStyle = 0;
                    airStyleView.setText("送风");
                    airTemperView.setTextColor(0xFF8ffa99);
                    airDegreeView.setTextColor(0xFF8ffa99);
                    ariStyleIcon.setBackgroundResource(R.mipmap.air_style_fan);
                }

            }
        });
        airWindScale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isNotVibrator();
                if (flagWind == 0){
                    flagWind = 1;
                    airWindView.setText("微风");
                } else if (flagWind == 1){
                    flagWind = 2;
                    airWindView.setText("弱风");
                }else if (flagWind == 2) {
                    flagWind = 3;
                    airWindView.setText("强风");
                }else if (flagWind == 3){
                    flagWind = 0;
                    airWindView.setText("自动");
                }
            }
        });
        airWindDirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isNotVibrator();
                if (flagWindDirection == 0){
                    flagWindDirection = 1;
                    airWindDirectionView.setText("上下扫风");
                } else if (flagWindDirection == 1){
                    flagWindDirection = 2;
                    airWindDirectionView.setText("左右扫风");
                }else if (flagWindDirection == 2) {
                    flagWindDirection = 0;
                    airWindDirectionView.setText("停止扫风");
                }
            }
        });
        userButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ControlAir.this,PersonalCenter.class);
                startActivity(intent);
                finish();
            }
        });
    }
    //按键震动
    private void isNotVibrator(){
        if (isCheck){
            Vibrator vibrator = (Vibrator)getSystemService(Service.VIBRATOR_SERVICE);
            vibrator.vibrate(new long[]{0,100},-1);
        }
    }
}
