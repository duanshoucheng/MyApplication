package com.example.administrator.imgcomposition;

import android.app.Activity;
import android.os.Bundle;


public class GestureActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TouchImageView img = new TouchImageView(this);
        setContentView(img);
//        setContentView(R.layout.activity_gesture);
    }
}
