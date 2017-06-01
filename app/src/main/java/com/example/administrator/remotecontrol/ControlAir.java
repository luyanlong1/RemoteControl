package com.example.administrator.remotecontrol;

import android.app.Dialog;
import android.app.Service;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Vibrator;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ResponseCache;
import java.net.URL;

public class ControlAir extends MainActivity {
    private ImageButton backButton,userButton,airTemperUp,airTemperDown,airStyle,airWindScale,airWindDirection,airSleepButton;
    private ImageButton airSwitch, airClockButton;
    private TextView airStyleView,airWindView,airTemperView,airDegreeView,airWindDirectionView,airNowRoomTemp;
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
        airNowRoomTemp = (TextView) findViewById(R.id.air_now_state);
        airSwitch = (ImageButton) findViewById(R.id.air_off_button);
        airClockButton = (ImageButton)findViewById (R.id.air_clock_button);
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
        String url = "http://192.168.191.1:8080/main?action=airRoomTemper";
        sendHttpRequestByGet(url);
        isCheck = pref.getBoolean("isNotVibrator",false);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ControlAir.this.finish();
            }
        });
        airSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "http://192.168.191.1:8080/main?action=airSwitch";
                sendHttpRequest(url);
            }
        });

        airClockButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = LayoutInflater.from(ControlAir.this);
                View view1  = inflater.inflate(R.layout.view_number_picker_dialog,null);
                final NumberPicker numberPickerHour = (NumberPicker) view1.findViewById(R.id.numberpicker_hour);
                final NumberPicker numberPickerMin = (NumberPicker) view1.findViewById(R.id.numberpicker_minute);
                final String strsHour[]=new String[]{"01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20"};
                final String strsMin[]=new String[]{"00","10","20","30","40","50"};
                numberPickerHour.setDisplayedValues(strsHour);
                numberPickerHour.setMaxValue(strsHour.length - 1);
                numberPickerHour.setMinValue(0);
                numberPickerMin.setDisplayedValues(strsMin);
                numberPickerMin.setMaxValue(strsMin.length - 1);
                numberPickerMin.setMinValue(0);
                Dialog dialog = new AlertDialog.Builder(ControlAir.this)
                        .setTitle("设置时间")
                        .setPositiveButton("设置", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(ControlAir.this,"已定时"+strsHour[  numberPickerHour.getValue()]+"小时"+strsMin[  numberPickerMin.getValue()]+"分钟",Toast.LENGTH_SHORT).show();
                                String url = "http://192.168.191.1:8080/main?action=airClockButton&action2="+strsHour[  numberPickerHour.getValue()]+":"+strsMin[  numberPickerMin.getValue()];
                                sendHttpRequest(url);
//                                fanTimeView.setText(strsHour[  numberPickerHour.getValue()]+":"+strsMin[  numberPickerMin.getValue()]);
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .setView(view1).create();
                dialog.show();
            }
        });
        airSleepButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "http://192.168.191.1:8080/main?action=airSleepButton";
                sendHttpRequest(url);
                isNotVibrator();
            }
        });
        airTemperUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "http://192.168.191.1:8080/main?action=airTemperUp";
                sendHttpRequest(url);
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
                String url = "http://192.168.191.1:8080/main?action=airTemperDown";
                sendHttpRequest(url);
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
                String url = "http://192.168.191.1:8080/main?action=airStyle";
                sendHttpRequest(url);
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
                String url = "http://192.168.191.1:8080/main?action=airWindScale";
                sendHttpRequest(url);
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
                String url = "http://192.168.191.1:8080/main?action=airWindDirection";
                sendHttpRequest(url);
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
    //发送HTTP请求获取当前室温
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
                    Log.d("ControlAir","bufferedReader is "+ responseData);
                    parseJSONWithGSON(responseData);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    //用GSON接下JSON数据包
    private void parseJSONWithGSON(String jsonData){
        Gson gson = new Gson();
        JSONForAirRoomTemper jsonForAirRoomTemper = gson.fromJson(jsonData,JSONForAirRoomTemper.class);
        jsonForAirRoomTemper.getTemp();
        Log.d("ControlAir","解析之后 is "+ jsonForAirRoomTemper.getTemp());
        showResponse(jsonForAirRoomTemper.getTemp());
    }
    //切换到主线程进行UI操作，显示解析后的数据
    private void showResponse(final String response){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                airNowRoomTemp.setText("当前室内温度"+response+".0℃");
            }
        });
    }
}
