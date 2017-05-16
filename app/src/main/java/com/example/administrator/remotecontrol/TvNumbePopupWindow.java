package com.example.administrator.remotecontrol;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class TvNumbePopupWindow extends Activity implements OnClickListener{

    private Button number0,number1, number2, number3,number4,number5,number6,number7,number8,number9,number10;
    private LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv_numbe_popup_window);
        number0 = (Button) this.findViewById(R.id.tv_number0_button);
        number1 = (Button) this.findViewById(R.id.tv_number1_button);
        number2 = (Button) this.findViewById(R.id.tv_number2_button);
        number3 = (Button) this.findViewById(R.id.tv_number3_button);
        number4 = (Button) this.findViewById(R.id.tv_number4_button);
        number5 = (Button) this.findViewById(R.id.tv_number5_button);
        number6 = (Button) this.findViewById(R.id.tv_number6_button);
        number7 = (Button) this.findViewById(R.id.tv_number7_button);
        number8 = (Button) this.findViewById(R.id.tv_number8_button);
        number9 = (Button) this.findViewById(R.id.tv_number9_button);
        number10 = (Button) this.findViewById(R.id.tv_number10_button);

        layout=(LinearLayout)findViewById(R.id.pop_layout);

        //不再执行onTouchEvent()函数，点击其他地方时执行onTouchEvent()函数销毁Activity
        layout.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                Toast.makeText(getApplicationContext(), "提示：点击窗口外部关闭窗口！",
                        Toast.LENGTH_SHORT).show();
            }
        });

        number0.setOnClickListener(this);
        number1.setOnClickListener(this);
        number2.setOnClickListener(this);
        number3.setOnClickListener(this);
        number4.setOnClickListener(this);
        number5.setOnClickListener(this);
        number6.setOnClickListener(this);
        number7.setOnClickListener(this);
        number8.setOnClickListener(this);
        number9.setOnClickListener(this);
        number10.setOnClickListener(this);

    }

    //实现onTouchEvent触屏函数但点击屏幕时销毁本Activity
    @Override
    public boolean onTouchEvent(MotionEvent event){
        TvNumbePopupWindow.this.finish();
        return true;
    }

    public void onClick(View v) {

    }

}
