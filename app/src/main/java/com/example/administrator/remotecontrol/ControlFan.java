package com.example.administrator.remotecontrol;

import android.accounts.NetworkErrorException;
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
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class ControlFan extends MainActivity {
    private SharedPreferences pref;
    private ImageButton backButton,userButton;
    private ImageButton fanNormalWindButton,fanNatureWindButton,fanSilentWindButton,fanSleepWindButton;
    private ImageView fanTypeView;
    private ImageButton fanShakeHead,fanClockButton;
    private SeekBar fanWindLevelBar;
    private TextView fanWindLevelView,fanTimeView;
    Boolean isCheck;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control_fan);
        initView();
        init();
    }
    private void initView() {
        backButton = (ImageButton) findViewById(R.id.back_button);
        userButton = (ImageButton) findViewById(R.id.user_button);
        fanNormalWindButton = (ImageButton) findViewById(R.id.fan_normal_wind_button);
        fanNatureWindButton = (ImageButton) findViewById(R.id.fan_nature_wind_button);
        fanSilentWindButton = (ImageButton) findViewById(R.id.fan_silent_wind_button);
        fanSleepWindButton = (ImageButton) findViewById(R.id.fan_sleep_wind_button);
        fanTypeView = (ImageView) findViewById(R.id.fan_wind_type_view);
        fanShakeHead = (ImageButton) findViewById(R.id.fan_shake_button);
        fanClockButton = (ImageButton) findViewById(R.id.fan_clock_button);
        fanWindLevelBar = (SeekBar) findViewById(R.id.fan_wind_level_bar);
        fanWindLevelView = (TextView) findViewById(R.id.fan_wind_level_view);
        fanTimeView = (TextView) findViewById(R.id.fan_time_view);
        pref = getSharedPreferences("isNotVibrator",MODE_APPEND);
    }
    private void init() {
        fanWindLevelBar.setProgress(6);
        fanWindLevelBar.setOnSeekBarChangeListener(seekListener);
        fanWindLevelView.setText("6");
        isCheck = pref.getBoolean("isNotVibrator",false);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ControlFan.this.finish();
            }
        });
        userButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ControlFan.this,PersonalCenter.class);
                startActivity(intent);
                finish();
            }
        });
        fanNormalWindButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fanTypeView.setBackgroundResource(R.mipmap.fan_normal_icon);
                isNotVibrator();
            }
        });
        fanNatureWindButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fanTypeView.setBackgroundResource(R.mipmap.fan_nature_icon);
                isNotVibrator();
            }
        });
        fanSilentWindButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fanTypeView.setBackgroundResource(R.mipmap.fan_silent_icon);
                isNotVibrator();
            }
        });
        fanSleepWindButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fanTypeView.setBackgroundResource(R.mipmap.fan_sleep_icon);
                isNotVibrator();
            }
        });
        fanShakeHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isNotVibrator();
            }
        });
        fanClockButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = LayoutInflater.from(ControlFan.this);
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

                Dialog dialog = new AlertDialog.Builder(ControlFan.this)
                        .setTitle("设置时间")
                        .setPositiveButton("设置", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                fanTimeView.setText(strsHour[  numberPickerHour.getValue()]+":"+strsMin[  numberPickerMin.getValue()]);
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
    }
    //按键震动
    private void isNotVibrator(){
        if (isCheck){
            Vibrator vibrator = (Vibrator)getSystemService(Service.VIBRATOR_SERVICE);
            vibrator.vibrate(new long[]{0,100},-1);
        }
    }
    private SeekBar.OnSeekBarChangeListener seekListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
            fanWindLevelView.setText(""+i);
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };

}
