package com.DevR.servertime;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class PopupGuideActivity extends Activity {

    //TextView btn1;
    Button btn1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);      //상단 액션바 제거
        setContentView(R.layout.popup_guide_activity);
        btn1 = (Button) findViewById(R.id.mOnClose);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}