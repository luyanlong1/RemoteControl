package com.example.administrator.remotecontrol;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.FrameLayout;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ControlLight extends MainActivity {
    private FrameLayout frame_layout;
    private ImageView lightNowColor;
    private TextView lightBrightness;
    private ImageButton backButton,userButton,lightSwitch;
    private GetPictureColorView mGetPictureColorView;
    private SeekBar lightBrightLevelBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control_light);

        initView();
        init();
    }
    private void initView() {
        frame_layout = (FrameLayout) findViewById(R.id.frame_layout);
        backButton = (ImageButton) findViewById(R.id.back_button);
        userButton = (ImageButton) findViewById(R.id.user_button);
        lightSwitch = (ImageButton) findViewById(R.id.light_off_button);
        lightNowColor = (ImageView) findViewById(R.id.light_now_color_view);
        lightBrightness = (TextView) findViewById(R.id.light_brightness_view);
        lightBrightLevelBar = (SeekBar) findViewById(R.id.light_level_bar);

    }

    private void init() {
        lightBrightLevelBar.setProgress(60);
        lightBrightLevelBar.setOnSeekBarChangeListener(seekListener);
        lightBrightness.setText("60");
        mGetPictureColorView = new GetPictureColorView(ControlLight.this);
        frame_layout.addView(mGetPictureColorView);
        mGetPictureColorView.setOnUpdateColorListener(new GetPictureColorView.OnUpdateColorListener() {
            @Override
            public void changeColor(int color) {
                lightNowColor.setBackgroundColor(color);
                //将int颜色转化为RGB
                int red = (color & 0xff0000) >> 16;
                int green = (color & 0x00ff00) >> 8;
                int blue = (color & 0x0000ff);
                String url = "http://192.168.191.1:8080/main?action=lightColor&action2="+red+","+green+","+blue;
                sendHttpRequest(url);
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ControlLight.this.finish();
            }
        });
        userButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ControlLight.this,PersonalCenter.class);
                startActivity(intent);
                finish();
            }
        });
        lightSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "http://192.168.191.1:8080/main?action=lightSwitch";
                sendHttpRequest(url);
            }
        });
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
    private SeekBar.OnSeekBarChangeListener seekListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
            lightBrightness.setText(""+i);
            String url = "http://192.168.191.1:8080/main?action=LightBrightness&action2="+i;
            sendHttpRequest(url);
        }
        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }
        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };
}
