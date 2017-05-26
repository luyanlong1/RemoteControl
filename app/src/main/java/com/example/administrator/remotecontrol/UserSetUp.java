package com.example.administrator.remotecontrol;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;

public class UserSetUp extends AppCompatActivity {
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private ImageButton backButton;
    private Button checkUpdata;
    private Switch isNotVibrator;
    Boolean isCheck ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_set_up);
        backButton = (ImageButton) findViewById(R.id.back_button);
        checkUpdata = (Button) findViewById(R.id.check_updata);
        isNotVibrator = (Switch) findViewById(R.id.is_shake_switch);
        editor = getSharedPreferences("isNotVibrator",MODE_APPEND).edit();
        pref = getSharedPreferences("isNotVibrator",MODE_APPEND);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserSetUp.this.finish();
            }
        });
        checkUpdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(UserSetUp.this,"当前为最新版本",Toast.LENGTH_SHORT).show();
            }
        });
        Boolean isCheck2 = pref.getBoolean("isNotVibrator",false);
        Log.d("UserSetUp","isCheck2值为："+isCheck2);
        isNotVibrator.setChecked(isCheck2);
        isNotVibrator.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    isCheck = true;
                }else{
                    isCheck = false;
                }
                Log.d("UserSetUp","isCheck值为："+isCheck);
                editor.putBoolean("isNotVibrator",isCheck);
                editor.apply();
            }
        });

    }
}
