package com.example.administrator.remotecontrol;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class PersonalCenter extends AppCompatActivity {
    private ImageButton backButton;
    private Button quitLogin;
    private ImageButton toUserSetButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_center);

        backButton = (ImageButton) findViewById(R.id.back_button);
        quitLogin = (Button) findViewById(R.id.quit_login_button);
        toUserSetButton = (ImageButton)findViewById(R.id.next_to_user_set);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PersonalCenter.this.finish();
            }
        });
        quitLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PersonalCenter.this,LoginActivity.class);
                startActivity(intent);
            }
        });
        toUserSetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PersonalCenter.this,UserSetUp.class);
                startActivity(intent);
            }
        });
    }
}
