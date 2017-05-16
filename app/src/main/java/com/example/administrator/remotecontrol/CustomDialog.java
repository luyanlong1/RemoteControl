package com.example.administrator.remotecontrol;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class CustomDialog extends Dialog {
    private TextView title;
    private EditText newSceneName;
    private Button positiveButton, negativeButton;

    public CustomDialog(Context context) {
        super(context);
        setCustomDialog();
    }

    private void setCustomDialog() {
        View mView = LayoutInflater.from(getContext()).inflate(R.layout.custom_dialog, null);
        newSceneName = (EditText) mView.findViewById(R.id.input_sence_name);
        positiveButton = (Button) mView.findViewById(R.id.positive_button);
        negativeButton = (Button) mView.findViewById(R.id.negative_button);
        title = (TextView) mView.findViewById(R.id.title_dialog);
        super.setContentView(mView);
    }

    public View getSceneName(){
        return newSceneName;
    }

    @Override
    public void setContentView(int layoutResID) {
    }

    @Override
    public void setContentView(View view, LayoutParams params) {
    }

    @Override
    public void setContentView(View view) {
    }


    public void setOnPositiveListener(View.OnClickListener listener){
        positiveButton.setOnClickListener(listener);
    }

    public void setOnNegativeListener(View.OnClickListener listener){
        negativeButton.setOnClickListener(listener);
    }
}
