package com.example.administrator.remotecontrol;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ControlTV extends MainActivity {
    private ImageButton backButton,userButton,numberButton,moreButton,tvSwitch;
    private ImageButton tvInputButton,tvHomeButton,tvMenuButton,tvBackButton,tvSilentButton;
    private ImageButton tvChUP,tvVolUp,tvChDown,tvVolDown;
    private ImageButton tvOkButton,tvLeftButton,tvRightButton,tvAboveButton,tvBelowButton;
    private SharedPreferences pref;
    Boolean isCheck;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control_tv);
        initView();
        init();
    }
    private void initView() {
        backButton = (ImageButton) findViewById(R.id.back_button);
        userButton = (ImageButton) findViewById(R.id.user_button);
        numberButton = (ImageButton) findViewById(R.id.tv_number_button);
        moreButton = (ImageButton) findViewById(R.id.tv_more_button);
        tvInputButton = (ImageButton) findViewById(R.id.tvput_button);
        tvHomeButton = (ImageButton) findViewById(R.id.tvhome_button);
        tvMenuButton = (ImageButton) findViewById(R.id.tvmenu_button);
        tvBackButton = (ImageButton) findViewById(R.id.tvback_button);
        tvSilentButton = (ImageButton) findViewById(R.id.tvsilent_button);
        tvChUP = (ImageButton) findViewById(R.id.tv_ch_up_button);
        tvVolUp = (ImageButton) findViewById(R.id.tv_vol_up_button);
        tvChDown = (ImageButton) findViewById(R.id.tv_ch_down_button);
        tvVolDown = (ImageButton) findViewById(R.id.tv_vol_down_button);
        tvOkButton = (ImageButton) findViewById(R.id.tv_ok_button);
        tvLeftButton = (ImageButton) findViewById(R.id.tv_left_button);
        tvRightButton = (ImageButton) findViewById(R.id.tv_right_button);
        tvAboveButton = (ImageButton) findViewById(R.id.tv_above_button);
        tvBelowButton = (ImageButton) findViewById(R.id.tv_below_button);
        tvSwitch = (ImageButton) findViewById(R.id.tvoff_button);
        pref = getSharedPreferences("isNotVibrator",MODE_APPEND);
    }
    private void init() {
        isCheck = pref.getBoolean("isNotVibrator",false);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ControlTV.this.finish();
            }
        });
        userButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ControlTV.this,PersonalCenter.class);
                startActivity(intent);
                finish();
            }
        });
        numberButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ControlTV.this,TvNumbePopupWindow.class);
                startActivity(intent);
            }
        });
        moreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ControlTV.this,TvMorePopupWindow.class);
                startActivity(intent);
            }
        });
        tvSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "http://192.168.191.1:8080/main?action=tvSwitch";
                sendHttpRequest(url);
            }
        });
        tvInputButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "http://192.168.191.1:8080/main?action=tvInputButton";
                sendHttpRequest(url);
                isNotVibrator();
            }
        });
        tvHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "http://192.168.191.1:8080/main?action=tvHomeButton";
                sendHttpRequest(url);
                isNotVibrator();
            }
        });
        tvMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "http://192.168.191.1:8080/main?action=tvMenuButton";
                sendHttpRequest(url);
                isNotVibrator();
            }
        });
        tvBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "http://192.168.191.1:8080/main?action=tvBackButton";
                sendHttpRequest(url);
                isNotVibrator();
            }
        });
        tvSilentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "http://192.168.191.1:8080/main?action=tvSilentButton";
                sendHttpRequest(url);
                isNotVibrator();
            }
        });
        tvChUP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "http://192.168.191.1:8080/main?action=tvChUP";
                sendHttpRequest(url);
                isNotVibrator();
            }
        });
        tvVolUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "http://192.168.191.1:8080/main?action=tvVolUp";
                sendHttpRequest(url);
                isNotVibrator();
            }
        });
        tvChDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "http://192.168.191.1:8080/main?action=tvChDown";
                sendHttpRequest(url);
                isNotVibrator();
            }
        });
        tvVolDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "http://192.168.191.1:8080/main?action=tvVolDown";
                sendHttpRequest(url);
                isNotVibrator();
            }
        });
        tvOkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "http://192.168.191.1:8080/main?action=tvOkButton";
                sendHttpRequest(url);
                isNotVibrator();
            }
        });
        tvLeftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "http://192.168.191.1:8080/main?action=tvVolDown";
                sendHttpRequest(url);
                isNotVibrator();
            }
        });
        tvRightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "http://192.168.191.1:8080/main?action=tvVolUp";
                sendHttpRequest(url);
                isNotVibrator();
            }
        });
        tvBelowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "http://192.168.191.1:8080/main?action=tvChDown";
                sendHttpRequest(url);
                isNotVibrator();
            }
        });
        tvAboveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "http://192.168.191.1:8080/main?action=tvChUP";
                sendHttpRequest(url);
                isNotVibrator();
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
    //发送HTTP请求方法
    private void sendHttpRequest(final String s){
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                try{
                    URL url = new URL(s);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    InputStream inputStream = connection.getInputStream();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
