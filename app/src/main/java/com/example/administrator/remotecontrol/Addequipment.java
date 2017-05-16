package com.example.administrator.remotecontrol;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Addequipment extends MainActivity {
    private Button addEquipButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addequipment);
        addEquipButton = (Button) findViewById(R.id.addEquip);
        addEquipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(Addequipment.this,GetInfo.class);
                startActivity(intent);
            }
        });
    }

}
