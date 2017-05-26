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
public class ControlLight extends MainActivity {
    private FrameLayout frame_layout;
    private ImageView lightNowColor;
    private TextView lightBrightness;
    private ImageButton backButton;
    private ImageButton userButton;
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
    }
    private SeekBar.OnSeekBarChangeListener seekListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
            lightBrightness.setText(""+i);
        }
        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }
        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };
}
