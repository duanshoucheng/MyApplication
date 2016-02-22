package com.example.administrator.imgcomposition;

import android.app.Activity;
import android.os.Bundle;

/**
 * GestureActivity的升级
 */
public class Gesture2Activity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new HSuperImageView(this));
//        setContentView(R.layout.activity_gesture2);
    }
}
