package com.example.administrator.remotecontrol;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class UserSetUp extends AppCompatActivity {
    private ImageButton backButton;
    private Button checkUpdata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_set_up);
        backButton = (ImageButton) findViewById(R.id.back_button);
        checkUpdata = (Button) findViewById(R.id.check_updata);

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
    }
}
