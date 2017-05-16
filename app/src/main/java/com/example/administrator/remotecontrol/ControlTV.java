package com.example.administrator.remotecontrol;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class ControlTV extends MainActivity {
    private ImageButton backButton,userButton,numberButton,moreButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control_tv);

        backButton = (ImageButton) findViewById(R.id.back_button);
        userButton = (ImageButton) findViewById(R.id.user_button);
        numberButton = (ImageButton) findViewById(R.id.tv_number_button);
        moreButton = (ImageButton) findViewById(R.id.tv_more_button);
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
    }
}
