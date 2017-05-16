package com.example.administrator.remotecontrol;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class RegisterActivity extends MainActivity {
    private ImageButton backButton;
    private Button userServerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        backButton = (ImageButton) findViewById(R.id.back_button);
        userServerButton = (Button) findViewById(R.id.user_server_button);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegisterActivity.this.finish();
            }
        });

        userServerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("http://www.cnblogs.com/luyanlong/p/6683599.html"));
                startActivity(intent);
            }
        });
    }
}
